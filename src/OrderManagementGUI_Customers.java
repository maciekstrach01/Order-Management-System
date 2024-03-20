import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class OrderManagementGUI_Customers extends OrderManagementGUI_Utils {
    private final JFrame mainFrame;
    private final OrderManagementSystem orderManagementSystem;

    private JTextField customerIdField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField companyField;
    private JTextField nipField;
    private JTextField addressStreetField;
    private JTextField addressBuildingNumberField;
    private JTextField addressApartmentNumberField;
    private JTextField addressCityField;
    private JTextField addressPostalCodeField;
    private JTextField addressVoivodeshipField;
    private JTextField addressCountryField;

    private JButton addCustomerButton;
    private JButton removeCustomerButton;
    private JButton displayCustomersButton;


    public OrderManagementGUI_Customers(JFrame mainFrame, OrderManagementSystem orderManagementSystem) {
        this.mainFrame = mainFrame;
        this.orderManagementSystem = orderManagementSystem;



        // Inicjalizacja komponentów GUI
        this.initializeComponents();

        // Obsługa zdarzeń
        this.configureEventHandlers();
    }

    private void initializeComponents() {

        // Inicjalizacja komponentów GUI dla klientów
        this.customerIdField = new JTextField();
        this.firstNameField = new JTextField();
        this.lastNameField = new JTextField();
        this.companyField = new JTextField();
        this.nipField = new JTextField();
        this.addressStreetField = new JTextField();
        this.addressBuildingNumberField = new JTextField();
        this.addressApartmentNumberField = new JTextField();
        this.addressPostalCodeField = new JTextField();
        this.addressCityField = new JTextField();
        this.addressVoivodeshipField = new JTextField();
        this.addressCountryField = new JTextField();
        this.addCustomerButton = new JButton("Dodaj Klienta");
        this.removeCustomerButton = new JButton("Usuń Klienta");
        this.displayCustomersButton = new JButton("Wyświetl Klientów");
    }

    private void configureEventHandlers() {
        // Obsługa zdarzeń dla klientów
        this.addCustomerButton.addActionListener(e -> this.addCustomer());
        this.removeCustomerButton.addActionListener(e -> this.removeCustomer());
        this.displayCustomersButton.addActionListener(e -> this.displayCustomers());
    }

    public JPanel createPanel() {
        JPanel customerPanel = new JPanel(new GridLayout(14, 2));


        // Stworzenie i dostosowanie JLabel z jasnopopielatym tłem i białą czcionką
        JLabel labelCustomerId = createStyledLabel("ID Klienta:");
        customerPanel.add(labelCustomerId);
        customerPanel.add(createTextFieldWithClearButton(this.customerIdField));

        JLabel labelFirstName = createStyledLabel("Imię:");
        customerPanel.add(labelFirstName);
        customerPanel.add(createTextFieldWithClearButton(this.firstNameField));

        JLabel labelLastName = createStyledLabel("Nazwisko:");
        customerPanel.add(labelLastName);
        customerPanel.add(createTextFieldWithClearButton(this.lastNameField));

        JLabel labelCompany = createStyledLabel("Firma:");
        customerPanel.add(labelCompany);
        customerPanel.add(createTextFieldWithClearButton(this.companyField));

        JLabel labelNip = createStyledLabel("NIP:");
        customerPanel.add(labelNip);
        customerPanel.add(createTextFieldWithClearButton(this.nipField));

        JLabel labelAddressStreet = createStyledLabel("Adres (ulica):");
        customerPanel.add(labelAddressStreet);
        customerPanel.add(createTextFieldWithClearButton(this.addressStreetField));

        JLabel labelAddressBuildingNumber = createStyledLabel("Adres (nr budynku):");
        customerPanel.add(labelAddressBuildingNumber);
        customerPanel.add(createTextFieldWithClearButton(this.addressBuildingNumberField));

        JLabel labelAddressApartmentNumber = createStyledLabel("Adres (nr mieszkania):");
        customerPanel.add(labelAddressApartmentNumber);
        customerPanel.add(createTextFieldWithClearButton(this.addressApartmentNumberField));

        JLabel labelAddressPostalCode = createStyledLabel("Adres (kod pocztowy):");
        customerPanel.add(labelAddressPostalCode);
        customerPanel.add(createTextFieldWithClearButton(this.addressPostalCodeField));

        JLabel labelAddressCity = createStyledLabel("Adres (miasto):");
        customerPanel.add(labelAddressCity);
        customerPanel.add(createTextFieldWithClearButton(this.addressCityField));

        JLabel labelAddressVoivodeship = createStyledLabel("Adres (województwo):");
        customerPanel.add(labelAddressVoivodeship);
        customerPanel.add(createTextFieldWithClearButton(this.addressVoivodeshipField));

        JLabel labelAddressCountry = createStyledLabel("Adres (kraj):");
        customerPanel.add(labelAddressCountry);
        customerPanel.add(createTextFieldWithClearButton(this.addressCountryField));

        customerPanel.add(this.addCustomerButton);
        customerPanel.add(this.removeCustomerButton);

        // Dodanie przycisku "Wyświetl Klientów"
        customerPanel.add(createActionButton("Wyświetl Klientów", e -> {
            // Tutaj możesz dodać dodatkowe opcje dla wyświetlania klientów
            String[] options = {"Wszyscy", "W określonym okresie", "Z określoną minimalną wartością zamówienia"};
            int choice = JOptionPane.showOptionDialog(null, "Wybierz opcję:",
                    "Wybór opcji", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, options, options[0]);

            switch (choice) {
                case 0:
                    // Wyświetl wszystkich klientów
                    this.displayCustomers();
                    break;
                case 1:
                    // Wyświetl klientów w określonym okresie
                    this.displayCustomersByOrderDateRange();
                    break;
                case 2:
                    // Wyświetl klientów z określoną minimalną wartością zamówienia
                    // displayCustomersByOrderMinValue();
                    this.displayCustomersByTotalOrderValue();
                    break;
            }
        }));

        return customerPanel;
    }


    private void addCustomer() {
        // Pobranie danych z pól tekstowych
        String customerIdText = this.customerIdField.getText();
        String firstName = this.firstNameField.getText();
        String lastName = this.lastNameField.getText();
        String company = this.companyField.getText();
        String nip = this.nipField.getText();
        String addressStreet = this.addressStreetField.getText();
        String addressBuildingNumber = this.addressBuildingNumberField.getText();
        String addressApartmentNumber = this.addressApartmentNumberField.getText();
        String addressPostalCode = this.addressPostalCodeField.getText();
        String addressCity = this.addressCityField.getText();
        String addressVoivodeship = this.addressVoivodeshipField.getText();
        String addressCountry = this.addressCountryField.getText();

        // Sprawdzenie, czy wszystkie pola są wypełnione
        if (firstName.isEmpty() || lastName.isEmpty() || company.isEmpty() || nip.isEmpty() || addressStreet.isEmpty()
                || addressBuildingNumber.isEmpty() || addressPostalCode.isEmpty() || addressCity.isEmpty()
                || addressVoivodeship.isEmpty() || addressCountry.isEmpty()) {
            JOptionPane.showMessageDialog(this.mainFrame, "Wprowadź wszystkie dane klienta. Nr mieszkania nie jest wymagany.", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Parsowanie danych i sprawdzenie istnienia klienta o podanym ID
        int customerId;

        try {
            customerId = Integer.parseInt(customerIdText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this.mainFrame, "ID Klienta powinno zawierać tylko cyfry.", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (this.orderManagementSystem.getCustomerById(customerId) != null) {
            JOptionPane.showMessageDialog(this.mainFrame, "Klient o podanym ID już istnieje w systemie.", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Walidacja Imienia i Nazwiska (czy składają się tylko z liter)
        if (!isValidName(firstName) || !isValidName(lastName)) {
            JOptionPane.showMessageDialog(this.mainFrame, "Imię i Nazwisko powinny składać się tylko z liter.", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Walidacja NIP (czy składa się z 10 cyfr)
        if (!isValidNIP(nip)) {
            JOptionPane.showMessageDialog(this.mainFrame, "NIP powinien składać się z 10 cyfr.", "Błąd", JOptionPane.ERROR_MESSAGE);
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

        // Tworzenie klienta i dodawanie do systemu
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setCompany(company);
        customer.setNip(nip);

        Address customerAddress = new Address();
        customerAddress.setStreet(addressStreet);
        customerAddress.setBuildingNumber(addressBuildingNumber);
        customerAddress.setApartmentNumber(addressApartmentNumber);
        customerAddress.setCity(addressCity);
        customerAddress.setPostalCode(addressPostalCode);
        customerAddress.setVoivodeship(addressVoivodeship);
        customerAddress.setCountry(country);

        customer.setAddress(customerAddress);

        this.orderManagementSystem.addCustomer(customer);

        // Wyczyszczenie pól tekstowych po dodaniu klienta
        clearTextFields();
        JOptionPane.showMessageDialog(this.mainFrame, "Klient dodany pomyślnie", "Sukces", JOptionPane.INFORMATION_MESSAGE);
    }

    private void removeCustomer() {
        try {
            int customerId = Integer.parseInt(this.customerIdField.getText());

            // Usunięcie klienta z systemu
            if (orderManagementSystem.removeCustomerById(customerId)) {
                // Wyczyszczenie pól tekstowych po usunięciu klienta
                clearTextFields();
                JOptionPane.showMessageDialog(this.mainFrame, "Klient usunięty pomyślnie", "Sukces", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this.mainFrame, "Klient o podanym ID nie istnieje w systemie.", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this.mainFrame, "Błąd podczas usuwania klienta. Sprawdź poprawność danych.", "Błąd", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void displayCustomers() {
        // Wyświetlanie wszystkich klientów w systemie
        List<Customer> customers = this.orderManagementSystem.getAllCustomers();

        if (customers.isEmpty()) {
            JOptionPane.showMessageDialog(this.mainFrame, "Brak klientów w systemie.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JTable table = this.createTable(customers);

            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(400, 300));
            JOptionPane.showMessageDialog(this.mainFrame, scrollPane, "Lista Klientów", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void clearTextFields() {
        this.customerIdField.setText("");
        this.firstNameField.setText("");
        this.lastNameField.setText("");
        this.companyField.setText("");
        this.nipField.setText("");
        this.addressStreetField.setText("");
        this.addressBuildingNumberField.setText("");
        this.addressApartmentNumberField.setText("");
        this.addressPostalCodeField.setText("");
        this.addressCityField.setText("");
        this.addressVoivodeshipField.setText("");
        this.addressCountryField.setText("");
    }

    private void displayCustomersByOrderDateRange() {
        try {
            // Wprowadź datę od i datę do
            String startDateText = JOptionPane.showInputDialog("Podaj datę początkową (format yyyy-MM-dd):");
            String endDateText = JOptionPane.showInputDialog("Podaj datę końcową (format yyyy-MM-dd):");

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);

            Date startDate = dateFormat.parse(startDateText);
            Date endDate = dateFormat.parse(endDateText);

            // Wyświetl klientów w określonym okresie
            List<Customer> customersInRange = this.orderManagementSystem.getCustomersByOrderDateRange(startDate, endDate);

            if (customersInRange.isEmpty()) {
                JOptionPane.showMessageDialog(this.mainFrame, "Brak klientów w określonym okresie.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JTable table = this.createTable(customersInRange);

                JScrollPane scrollPane = new JScrollPane(table);
                scrollPane.setPreferredSize(new Dimension(400, 300));
                JOptionPane.showMessageDialog(this.mainFrame, scrollPane, "Klienci w określonym okresie", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this.mainFrame, "Nieprawidłowy format daty. Wprowadź datę w formacie yyyy-MM-dd.", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayCustomersByTotalOrderValue() {
        try {
            String valueString = JOptionPane.showInputDialog(this, "Podaj minimalną łączną wartość zamówień:");
            if (valueString != null && !valueString.isEmpty()) {
                double minValue = Double.parseDouble(valueString);

                int sortOption = JOptionPane.showConfirmDialog(this.mainFrame, "Czy chcesz sortować od największej wartości?", "Opcja sortowania", JOptionPane.YES_NO_OPTION);
                boolean sortByDescending = (sortOption == JOptionPane.YES_OPTION);

                // Wywołanie metody z OrderManagementSystem
                List<Customer> customers = orderManagementSystem.getCustomersByTotalOrderValue(minValue, sortByDescending);
                JTable table = this.createTable(customers);

                JScrollPane scrollPane = new JScrollPane(table);
                scrollPane.setPreferredSize(new Dimension(1200, 500));
                JOptionPane.showMessageDialog(this.mainFrame, scrollPane, "Klienci z zamówieniami o minimalnej wartości: " + minValue, JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this.mainFrame, "Błąd parsowania wartości. Wprowadź poprawną liczbę.", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JTable createTable(List<Customer> customers) {
        // Definiowanie nagłówków dla kolumn tabeli
        String[] columnNames = {"ID", "Imię", "Nazwisko", "Firma", "NIP", "Adres", "Łączna wartość zamówień"};

        // Tworzenie modelu tabeli z danymi klientów
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Customer customer : customers) {
            double totalValue = orderManagementSystem.getOrderValueForCustomer(customer);

            model.addRow(new Object[]{customer.getId(), customer.getFirstName(), customer.getLastName(),
                    customer.getCompany(), customer.getNip(), customer.getAddress(), totalValue});
        }

        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);

        return table;
    }

    // Metoda sprawdzająca poprawność Imienia i Nazwiska
    private boolean isValidName(String name) {
        return name.matches("[a-zA-Z]+");
    }

    // Metoda sprawdzająca poprawność NIP (czy składa się z 10 cyfr)
    private boolean isValidNIP(String nip) {
        return nip.matches("\\d{10}");
    }
}
