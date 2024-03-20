import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class OrderManagementGUI_Orders extends OrderManagementGUI_Utils {
    private final JFrame mainFrame;
    private final OrderManagementSystem orderManagementSystem;

    private final HashMap<Integer, OrderItem> orderItems = new HashMap<>();

    private JTextField orderIdField;
    private JTextField orderDateField;
    private JTextField orderCustomerField;
    private JTextField addressStreetField;
    private JTextField addressBuildingNumberField;
    private JTextField addressApartmentNumberField;
    private JTextField addressCityField;
    private JTextField addressPostalCodeField;
    private JTextField addressVoivodeshipField;
    private JTextField addressCountryField;

    private JButton addProductsButton;
    private JButton addOrderButton;
    private JButton removeOrderButton;
    private JButton displayOrdersButton;

    public OrderManagementGUI_Orders(JFrame mainFrame, OrderManagementSystem orderManagementSystem) {
        this.mainFrame = mainFrame;
        this.orderManagementSystem = orderManagementSystem;

        // Inicjalizacja komponentów GUI
        this.initializeComponents();

        // Obsługa zdarzeń
        this.configureEventHandlers();
    }

    private void initializeComponents() {
        // Inicjalizacja komponentów GUI dla zamówień
        this.orderIdField = new JTextField();
        this.orderDateField = new JTextField();
        this.orderCustomerField = new JTextField();
        this.addressStreetField = new JTextField();
        this.addressBuildingNumberField = new JTextField();
        this.addressApartmentNumberField = new JTextField();
        this.addressPostalCodeField = new JTextField();
        this.addressCityField = new JTextField();
        this.addressVoivodeshipField = new JTextField();
        this.addressCountryField = new JTextField();

        this.addProductsButton = new JButton("Dodaj produkty");
        this.addOrderButton = new JButton("Dodaj Zamówienie");
        this.removeOrderButton = new JButton("Usuń Zamówienie");
        this.displayOrdersButton = new JButton("Wyświetl Zamówienia");

    }

    private void configureEventHandlers() {
        // Obsługa zdarzeń dla zamówień
        addProductsButton.addActionListener(e -> this.addProducts());

        this.addOrderButton.addActionListener(e -> this.addOrder());
        this.removeOrderButton.addActionListener(e -> this.removeOrder());
        this.displayOrdersButton.addActionListener(e -> this.displayOrders());
    }

    public JPanel createPanel() {
        JPanel orderPanel = new JPanel(new GridLayout(13, 2));

        orderPanel.add(createStyledLabel("ID Zamówienia:"));
        orderPanel.add(createTextFieldWithClearButton(this.orderIdField));

        orderPanel.add(createStyledLabel("Data Zamówienia (yyyy-MM-dd):"));
        orderPanel.add(createTextFieldWithClearButton(this.orderDateField));

        orderPanel.add(createStyledLabel("ID Klienta:"));
        orderPanel.add(createTextFieldWithClearButton(this.orderCustomerField));

        orderPanel.add(createStyledLabel("Produkty:"));
        orderPanel.add(this.addProductsButton);

        orderPanel.add(createStyledLabel("Adres dostawy (ulica):"));
        orderPanel.add(createTextFieldWithClearButton(this.addressStreetField));

        orderPanel.add(createStyledLabel("Adres dostawy (nr budynku):"));
        orderPanel.add(createTextFieldWithClearButton(this.addressBuildingNumberField));

        orderPanel.add(createStyledLabel("Adres dostawy (nr mieszkania):"));
        orderPanel.add(createTextFieldWithClearButton(this.addressApartmentNumberField));

        orderPanel.add(createStyledLabel("Adres dostawy (kod pocztowy):"));
        orderPanel.add(createTextFieldWithClearButton(this.addressPostalCodeField));

        orderPanel.add(createStyledLabel("Adres dostawy (miasto):"));
        orderPanel.add(createTextFieldWithClearButton(this.addressCityField));

        orderPanel.add(createStyledLabel("Adres dostawy (województwo):"));
        orderPanel.add(createTextFieldWithClearButton(this.addressVoivodeshipField));

        orderPanel.add(createStyledLabel("Adres dostawy (kraj):"));
        orderPanel.add(createTextFieldWithClearButton(this.addressCountryField));

        // Dodanie przycisków w ostatnim wierszu
        orderPanel.add(createActionButton("Dodaj Zamówienie", e -> this.addOrder()));
        orderPanel.add(createActionButton("Usuń Zamówienie", e -> this.removeOrder()));
        orderPanel.add(createActionButton("Wyświetl Zamówienia", e -> {
            // Tutaj możesz dodać dodatkowe opcje dla wyświetlania zamówień
            String[] options = {"Wszystkie", "W określonym okresie", "Dla określonego klienta", "Wartość w podanym przedziale"};
            int choice = JOptionPane.showOptionDialog(null, "Wybierz opcję:", "Wybór opcji", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0:
                    // Wyświetl wszystkie zamówienia
                    this.displayAllOrders();
                    break;
                case 1:
                    // Wyświetl zamówienia w określonym okresie
                    this.displayOrdersInDateRange();
                    break;
                case 2:
                    // Wyświetl zamówienia dla określonego klienta
                    this.displayOrdersByCustomer();
                    break;
                case 3:
                    // Wyświetl zamówienia o wartości w podanym przedziale
                    this.displayOrdersByValueRange();
                    break;
                default:
                    // Domyślna opcja
                    break;
            }
        }));

        return orderPanel;
    }

    private void addProducts() {
        String[] columnNames = {"ID produktu", "Ilość", "Rabat (%)"};

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        List<Product> products = this.orderManagementSystem.getAllProducts();
        for (Product product : products) {
            int quantity = 0;
            double discount = 0;

            for (Map.Entry<Integer, OrderItem> orderItemEntry : this.orderItems.entrySet()) {
                int productId = orderItemEntry.getKey();
                OrderItem orderItem = orderItemEntry.getValue();

                if (productId == product.getId()) {
                    quantity = orderItem.getQuantity();
                    discount = orderItem.getDiscount();
                }
            }

            model.addRow(new Object[]{product.getId(), quantity, discount});
        }

        JTable table = new JTable(model) {
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(500, 300));

        JPanel productsPanel = new JPanel();
        productsPanel.add(scrollPane);

        JOptionPane.showMessageDialog(null, productsPanel, "Dodawanie produktów do zamówienia", JOptionPane.INFORMATION_MESSAGE);

        Vector modelDataVector = model.getDataVector();

        Enumeration dataRows = modelDataVector.elements();
        while (dataRows.hasMoreElements()) {
            Vector dataRow = (Vector) dataRows.nextElement();

            int productId = (int) dataRow.elementAt(0);
            OrderItem orderItem = this.orderItems.getOrDefault(productId, null);

            try {
                int productQuantity = Integer.parseInt(dataRow.elementAt(1).toString());
                double productDiscount = Double.parseDouble(dataRow.elementAt(2).toString());

                if (productQuantity < 0) {
                    JOptionPane.showMessageDialog(null, "Ilość produktu o ID " + productId + " nie może być mniejsza od 0.");
                    return;
                }

                if (productDiscount < 0 || productDiscount > 1) {
                    JOptionPane.showMessageDialog(null, "Rabat dla produktu o ID " + productId + " nie może być mniejszy od 0 lub większy od 1.");
                    return;
                }

                if (orderItem != null) {
                    if (productQuantity == 0) {
                        this.orderItems.remove(productId);
                    } else {
                        orderItem.setQuantity(productQuantity);
                        orderItem.setDiscount(productDiscount);
                    }
                } else {
                    orderItem = new OrderItem();

                    Product product = this.orderManagementSystem.getProductById(productId);

                    orderItem.setProduct(product);
                    orderItem.setQuantity(productQuantity);
                    orderItem.setDiscount(productDiscount);

                    this.orderItems.put(productId, orderItem);
                }
            } catch (NumberFormatException | ClassCastException ex) {
                JOptionPane.showMessageDialog(null, "Błąd parsowania danych dla produktu o ID " + productId + ".");
            }
        }
    }

    private void displayAllOrders() {
        java.util.List<Order> allOrders = this.orderManagementSystem.getAllOrders();
        displayOrdersList("Wszystkie zamówienia:", allOrders);
    }

    private void displayOrdersInDateRange() {
        try {
            JTextField startDateField = new JTextField();
            startDateField.setColumns(15);
            JPanel startDatePanel = new JPanel();
            startDatePanel.add(new JLabel("Data początkowa (yyyy-MM-dd):"));
            startDatePanel.add(startDateField);

            JTextField endDateField = new JTextField();
            endDateField.setColumns(15);
            JPanel endDatePanel = new JPanel();
            endDatePanel.add(new JLabel("Data końcowa (yyyy-MM-dd):"));
            endDatePanel.add(endDateField);

            JPanel dateRangePanel = new JPanel(new GridLayout(5, 3));
            dateRangePanel.add(startDatePanel);
            dateRangePanel.add(endDatePanel);

            int result = JOptionPane.showConfirmDialog(this.mainFrame, dateRangePanel, "Wprowadź zakres dat", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date startDate = dateFormat.parse(startDateField.getText());
                Date endDate = dateFormat.parse(endDateField.getText());

                List<Order> matchingOrders = this.orderManagementSystem.getOrdersInDateRange(startDate, endDate);
                displayOrderListInTable(matchingOrders);
            }
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Błąd parsowania daty. Wprowadź datę w formacie yyyy-MM-dd.");
        }
    }

    private void displayOrdersByCustomer() {
        try {
            JTextField customerIdField = new JTextField();
            customerIdField.setColumns(15);

            JPanel customerIdPanel = new JPanel();
            customerIdPanel.add(new JLabel("ID Klienta:"));
            customerIdPanel.add(customerIdField);

            JPanel customerPanel = new JPanel(new GridLayout(3, 2));
            customerPanel.add(customerIdPanel);

            int result = JOptionPane.showConfirmDialog(this.mainFrame, customerPanel, "Wprowadź ID klienta", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                int customerId = Integer.parseInt(customerIdField.getText());
                List<Order> matchingOrders = this.orderManagementSystem.getOrdersByCustomer(customerId);
                displayOrderListInTable(matchingOrders);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Błąd parsowania ID klienta. Wprowadź poprawne ID.");
        }
    }

    private void displayOrdersByValueRange() {
        try {
            JTextField minValueField = new JTextField();
            JTextField maxValueField = new JTextField();
            minValueField.setColumns(15);
            maxValueField.setColumns(15);

            JPanel minValuePanel = new JPanel();
            minValuePanel.add(new JLabel("Wartość minimalna:"));
            minValuePanel.add(minValueField);

            JPanel maxValuePanel = new JPanel();
            maxValuePanel.add(new JLabel("Wartość maksymalna:"));
            maxValuePanel.add(maxValueField);

            JPanel valueRangePanel = new JPanel(new GridLayout(5, 3));
            valueRangePanel.add(minValuePanel);
            valueRangePanel.add(maxValuePanel);

            int result = JOptionPane.showConfirmDialog(this.mainFrame, valueRangePanel, "Wprowadź przedział wartości", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                double minValue = Double.parseDouble(minValueField.getText());
                double maxValue = Double.parseDouble(maxValueField.getText());

                List<Order> matchingOrders = this.orderManagementSystem.getOrdersByValueRange(minValue, maxValue);
                displayOrderListInTable(matchingOrders);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Błąd parsowania wartości. Wprowadź poprawne liczby.");
        }
    }

    private void displayOrdersList(String title, List<Order> orders) {
        if (orders.isEmpty()) {
            JOptionPane.showMessageDialog(null, title + "\nBrak zamówień.");
        } else {
            JTable table = this.createTable(orders);

            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(500, 300));
            JOptionPane.showMessageDialog(null, scrollPane, title, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void addOrder() {
        try {
            // Pobierz dane z pól tekstowych
            String orderIdText = this.orderIdField.getText();
            String orderDateText = this.orderDateField.getText();
            String customerIdText = this.orderCustomerField.getText();
            String addressStreet = this.addressStreetField.getText();
            String addressBuildingNumber = this.addressBuildingNumberField.getText();
            String addressApartmentNumber = this.addressApartmentNumberField.getText();
            String addressPostalCode = this.addressPostalCodeField.getText();
            String addressCity = this.addressCityField.getText();
            String addressVoivodeship = this.addressVoivodeshipField.getText();
            String addressCountry = this.addressCountryField.getText();

            // Sprawdź, czy pola tekstowe nie są puste
            if (orderIdText.isEmpty() || orderDateText.isEmpty() || customerIdText.isEmpty() || addressStreet.isEmpty()
                    || addressBuildingNumber.isEmpty() || addressPostalCode.isEmpty() || addressCity.isEmpty()
                    || addressVoivodeship.isEmpty() || addressCountry.isEmpty()) {
                JOptionPane.showMessageDialog(this.mainFrame, "Wprowadź wszystkie wymagane dane. Nr mieszkania nie jest wymagany.", "Błąd", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!customerIdText.matches("\\d+")) {
                JOptionPane.showMessageDialog(this.mainFrame, "ID Klienta powinno składać się tylko z cyfr.", "Błąd", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Sprawdź, czy są dodane jakieś produkty do zamówienia
            boolean areAnyProducts = false;

            for (Map.Entry<Integer, OrderItem> orderItemEntry : this.orderItems.entrySet()) {
                OrderItem orderItem = orderItemEntry.getValue();

                if (orderItem.getQuantity() > 0) {
                    areAnyProducts = true;
                    break;
                }
            }

            if (!areAnyProducts) {
                JOptionPane.showMessageDialog(this.mainFrame, "Do zamówienia należy dodać produkty.", "Błąd", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Walidacja kraju
            List<String> countries = Arrays.stream(Country.values()).map(Country::getPolishCountryName).collect(Collectors.toList());

            Country country;
            try {
                country = Country.getCountryFromPolishName(addressCountry);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this.mainFrame, "Niepoprawny kraj! Dozwolone państwa: " + countries, "Błąd", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Parsuj dane
            int orderId = Integer.parseInt(orderIdText);
            int customerId = Integer.parseInt(customerIdText);

            // Sprawdzenie, czy zamówienie o podanym ID już istnieje
            if (this.orderManagementSystem.orderExists(orderId)) {
                JOptionPane.showMessageDialog(this.mainFrame, "Zamówienie o podanym ID już istnieje w systemie.", "Błąd", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Sprawdzenie poprawności daty
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);

            Date orderDate;
            try {
                orderDate = dateFormat.parse(orderDateText);
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this.mainFrame, "Nieprawidłowy format daty. Wprowadź datę w formacie yyyy-MM-dd.", "Błąd", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Sprawdzenie zakresów daty
            Calendar cal = Calendar.getInstance();
            cal.setTime(orderDate);
            int year = cal.get(Calendar.YEAR);

            if (year < 2000 || year > 2100) {
                JOptionPane.showMessageDialog(this.mainFrame, "Rok zamówienia powinien być w zakresie od 2000 do 2100.", "Błąd", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Stwórz obiekt Order i przypisz dane
            Order order = new Order();

            order.setId(orderId);
            order.setCustomerId(customerId);
            order.setOrderDate(orderDate);
            order.setOrderItems(this.orderItems);

            Address deliveryAddress = new Address();
            deliveryAddress.setStreet(addressStreet);
            deliveryAddress.setBuildingNumber(addressBuildingNumber);
            deliveryAddress.setApartmentNumber(addressApartmentNumber);
            deliveryAddress.setPostalCode(addressPostalCode);
            deliveryAddress.setCity(addressCity);
            deliveryAddress.setVoivodeship(addressVoivodeship);
            deliveryAddress.setCountry(country);

            order.setDeliveryAddress(deliveryAddress);

            // Dodaj zamówienie do systemu
            this.orderManagementSystem.addOrder(order);

            // Wyczyść pola tekstowe po dodaniu zamówienia
            this.clearTextFields();

            // Informacja o sukcesie
            JOptionPane.showMessageDialog(this.mainFrame, "Zamówienie dodane pomyślnie", "Sukces", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            // Obsługa błędów
            JOptionPane.showMessageDialog(this.mainFrame, "Błąd podczas dodawania zamówienia. Sprawdź poprawność danych.", "Błąd", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public void removeOrder() {
        try {
            int orderId = Integer.parseInt(this.orderIdField.getText());

            // Usunięcie zamówienia z systemu
            if (this.orderManagementSystem.removeOrder(orderId)) {
                this.clearTextFields();
                JOptionPane.showMessageDialog(this.mainFrame, "Zamówienie usunięte pomyślnie", "Sukces", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this.mainFrame, "Zamówienie o podanym ID nie istnieje w systemie.", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this.mainFrame, "Błąd podczas usuwania zamówienia. Sprawdź poprawność danych.", "Błąd", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void displayOrders() {
        List<Order> orders = this.orderManagementSystem.getAllOrders();
        if (orders.isEmpty()) {
            JOptionPane.showMessageDialog(this.mainFrame, "Brak zamówień w systemie.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder orderList = new StringBuilder("Wszystkie zamówienia w systemie:\n");
            for (Order order : orders) {
                orderList.append(order.toString()).append("\n");
            }
            JTextArea textArea = new JTextArea(orderList.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 300));
            JOptionPane.showMessageDialog(this.mainFrame, scrollPane, "Lista Zamówień",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void displayOrderListInTable(List<Order> orders) {
        if (orders.isEmpty()) {
            JOptionPane.showMessageDialog(this.mainFrame, "Brak zamówień w określonym okresie.",
                    "Informacja", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JTable table = this.createTable(orders);

            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(500, 300));
            JOptionPane.showMessageDialog(this.mainFrame, scrollPane, "Zamówienia w określonym okresie",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private JTable createTable(List<Order> orders) {
        // Definiowanie nagłówków dla kolumn tabeli
        String[] columnNames = {"ID Zamówienia", "Data Zamówienia", "ID Klienta", "ID produktów",
                "Wartość Zamówienia Brutto", "Adres Dostawy"};

        // Tworzenie modelu tabeli z danymi zamówień
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Order order : orders) {
            List<String> productsIds = order.getOrderItems().values().stream()
                    .map(orderItem -> Integer.toString(orderItem.getProduct().getId())).collect(Collectors.toList());

            model.addRow(new Object[]{order.getId(), order.getOrderDate(), order.getCustomerId(), productsIds,
                    order.getOrderGrossValue(), order.getDeliveryAddress()});
        }

        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);

        return table;
    }

    private void clearTextFields() {
        this.orderIdField.setText("");
        this.orderDateField.setText("");
        this.orderCustomerField.setText("");
        this.addressStreetField.setText("");
        this.addressBuildingNumberField.setText("");
        this.addressApartmentNumberField.setText("");
        this.addressPostalCodeField.setText("");
        this.addressCityField.setText("");
        this.addressVoivodeshipField.setText("");
        this.addressCountryField.setText("");
    }
}
