import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class OrderManagementGUI_Products extends OrderManagementGUI_Utils {
    private final JFrame mainFrame;
    private final OrderManagementSystem orderManagementSystem;

    private JTextField productIdField;
    private JTextField productNameField;
    private JTextField productDescriptionField;
    private JTextField productSKUField;
    private JTextField productNetPriceField;
    private JTextField productGrossPriceField;
    private JTextField productLengthDimensionField;
    private JTextField productWidthDimensionField;
    private JTextField productHeightDimensionField;
    private JTextField productWeightField;

    private JButton addProductButton;
    private JButton removeProductButton;
    private JButton displayProductsButton;
    private JButton searchProductsButton;

    public OrderManagementGUI_Products(JFrame mainFrame, OrderManagementSystem orderManagementSystem) {
        this.mainFrame = mainFrame;
        this.orderManagementSystem = orderManagementSystem;

        // Inicjalizacja komponentów GUI
        this.initializeComponents();

        // Obsługa zdarzeń
        this.configureEventHandlers();
    }

    private void initializeComponents() {
        // Inicjalizacja komponentów GUI dla produktów
        this.productIdField = new JTextField();
        this.productNameField = new JTextField();
        this.productDescriptionField = new JTextField();
        this.productSKUField = new JTextField();
        this.productNetPriceField = new JTextField();
        this.productGrossPriceField = new JTextField();
        this.productLengthDimensionField = new JTextField();
        this.productWidthDimensionField = new JTextField();
        this.productHeightDimensionField = new JTextField();
        this.productWeightField = new JTextField();

        this.addProductButton = new JButton("Dodaj Produkt");
        this.removeProductButton = new JButton("Usuń Produkt");
        this.displayProductsButton = new JButton("Wyświetl Wszystkie Produkty");
        this.searchProductsButton = new JButton("Wyszukaj Produkty");
    }

    private void configureEventHandlers() {
        // Obsługa zdarzeń dla produktów
        this.searchProductsButton.addActionListener(e -> this.searchProducts());
        this.addProductButton.addActionListener(e -> this.addProduct());
        this.removeProductButton.addActionListener(e -> this.removeProduct());
        this.displayProductsButton.addActionListener(e -> this.displayProducts());
    }

    public JPanel createPanel() {
        JPanel productPanel = new JPanel(new GridLayout(12, 2));

        productPanel.add(createStyledLabel("ID Produktu:"));
        productPanel.add(createTextFieldWithClearButton(this.productIdField));

        productPanel.add(createStyledLabel("Nazwa Produktu:"));
        productPanel.add(createTextFieldWithClearButton(this.productNameField));

        productPanel.add(createStyledLabel("Opis Produktu:"));
        productPanel.add(createTextFieldWithClearButton(this.productDescriptionField));

        productPanel.add(createStyledLabel("SKU Produktu:"));
        productPanel.add(createTextFieldWithClearButton(this.productSKUField));

        productPanel.add(createStyledLabel("Cena Netto Produktu:"));
        productPanel.add(createTextFieldWithClearButton(this.productNetPriceField));

        productPanel.add(createStyledLabel("Cena Brutto Produktu:"));
        productPanel.add(createTextFieldWithClearButton(this.productGrossPriceField));

        productPanel.add(createStyledLabel("Wymiary Produktu (dł.):"));
        productPanel.add(createTextFieldWithClearButton(this.productLengthDimensionField));

        productPanel.add(createStyledLabel("Wymiary Produktu (szer.):"));
        productPanel.add(createTextFieldWithClearButton(this.productWidthDimensionField));

        productPanel.add(createStyledLabel("Wymiary Produktu (wys.):"));
        productPanel.add(createTextFieldWithClearButton(this.productHeightDimensionField));

        productPanel.add(createStyledLabel("Waga Produktu:"));
        productPanel.add(createTextFieldWithClearButton(this.productWeightField));

        productPanel.add(this.addProductButton);
        productPanel.add(this.removeProductButton);
        productPanel.add(this.displayProductsButton);
        productPanel.add(this.searchProductsButton);

        return productPanel;
    }

    private void searchProducts() {
        String searchString = JOptionPane.showInputDialog("Wprowadź ciąg znaków do wyszukania w nazwach produktów:");

        if (searchString != null && !searchString.isEmpty()) {
            List<Product> matchingProducts = this.orderManagementSystem.getProductsBySearchString(searchString);

            if (matchingProducts.isEmpty()) {
                JOptionPane.showMessageDialog(this.mainFrame, "Brak produktów zawierających wprowadzony ciąg znaków.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JTable table = this.createTable(matchingProducts);

                JScrollPane scrollPane = new JScrollPane(table);
                scrollPane.setPreferredSize(new Dimension(400, 300));
                JOptionPane.showMessageDialog(this.mainFrame, scrollPane, "Wyniki wyszukiwania produktów", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this.mainFrame, "Wprowadź poprawny ciąg znaków.", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addProduct() {
        // Pobranie danych z pól tekstowych
        String productIdText = this.productIdField.getText();
        String productName = this.productNameField.getText();
        String productDescription = this.productDescriptionField.getText();
        String productSKU = this.productSKUField.getText();
        String productNetPriceText = this.productNetPriceField.getText();
        String productGrossPriceText = this.productGrossPriceField.getText();
        String productLengthDimensionText = this.productLengthDimensionField.getText();
        String productWidthDimensionText = this.productWidthDimensionField.getText();
        String productHeightDimensionText = this.productHeightDimensionField.getText();
        String productWeightText = this.productWeightField.getText();

        // Sprawdź, czy pola tekstowe nie są puste
        if (productIdText.isEmpty() || productName.isEmpty() || productSKU.isEmpty() || productNetPriceText.isEmpty()
                || productGrossPriceText.isEmpty()) {
            JOptionPane.showMessageDialog(this.mainFrame, "Wprowadź ID, nazwę, cenę i SKU produktu do dodania. Opis, wymiary i waga produktu nie są wymagane.", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Parsowanie danych i sprawdzenie istnienia produktu o podanym ID
        int productId;

        try {
            productId = Integer.parseInt(productIdText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this.mainFrame, "ID produktu powinno zawierać tylko cyfry.", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (this.orderManagementSystem.getProductById(productId) != null) {
            JOptionPane.showMessageDialog(this.mainFrame, "Produkt o podanym ID już istnieje w systemie.", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Parsowanie cen
        double productNetPrice = 0, productGrossPrice = 0;

        try {
            if (!productNetPriceText.equals("") || !productGrossPriceText.equals("")) {
                productNetPrice = Double.parseDouble(productNetPriceText);
                productGrossPrice = Double.parseDouble(productGrossPriceText);

                // Sprawdzenie, czy przypadkiem podane ceny nie są mniejsze bądź równe 0
                if (productNetPrice <= 0 || productGrossPrice <= 0) {
                    JOptionPane.showMessageDialog(this.mainFrame, "Cena produktu musi być liczbą dodatnią.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this.mainFrame, "Cena produktu powinna być liczbą.", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Parsowanie rozmiarów
        double productLength = 0, productWidth = 0, productHeight = 0;

        try {
            if (!productLengthDimensionText.equals("") || !productWidthDimensionText.equals("")
                    || !productHeightDimensionText.equals("")) {
                productLength = Double.parseDouble(productLengthDimensionText);
                productWidth = Double.parseDouble(productWidthDimensionText);
                productHeight = Double.parseDouble(productHeightDimensionText);

                // Sprawdzenie, czy przypadkiem podane rozmiary nie są mniejsze bądź równe 0
                if (productLength <= 0 || productWidth <= 0 || productHeight <= 0) {
                    JOptionPane.showMessageDialog(this.mainFrame, "Wymiary produktu muszą być liczbami dodatnimi.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this.mainFrame, "Wymiary produktu powinny być liczbami.", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Parsowanie wagi
        double productWeight = 0;

        try {
            if (!productWeightText.equals("")) {
                productWeight = Double.parseDouble(productWeightText);

                // Sprawdzenie, czy przypadkiem podana waga nie jest mniejsza bądź równa 0
                if (productWeight <= 0) {
                    JOptionPane.showMessageDialog(this.mainFrame, "Waga produktu musi być liczbą dodatnią.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this.mainFrame, "Waga produktu powinna być liczbą.", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Tworzenie produktu i dodawanie do systemu
        Product product = new Product();

        product.setId(productId);
        product.setName(productName);

        if (!productDescription.equals("")) {
            product.setDescription(productDescription);
        }

        product.setSku(productSKU);

        if (!productNetPriceText.equals("") || !productGrossPriceText.equals("")) {
            product.setNetPrice(productNetPrice);
            product.setGrossPrice(productGrossPrice);
        }

        if (!productLengthDimensionText.equals("") || !productWidthDimensionText.equals("")
                || !productHeightDimensionText.equals("")) {
            product.setDimensions(new Dimensions(productLength, productWidth, productHeight));
        }

        if (!productWeightText.equals("")) {
            product.setWeight(productWeight);
        }

        this.orderManagementSystem.addProduct(product);

        // Wyczyszczenie pól tekstowych po dodaniu produktu
        this.clearTextFields();

        // Wyświetlanie identyfikatora produktu
        JOptionPane.showMessageDialog(this.mainFrame, "Produkt dodany pomyślnie. ID produktu: " + productId, "Sukces", JOptionPane.INFORMATION_MESSAGE);
    }

    private void removeProduct() {
        try {
            int productId = Integer.parseInt(this.productIdField.getText());

            // Usunięcie produktu z systemu
            if (this.orderManagementSystem.removeProduct(productId)) {
                // Wyczyszczenie pól tekstowych po usunięciu produktu
                this.clearTextFields();
                JOptionPane.showMessageDialog(this.mainFrame, "Produkt usunięty pomyślnie", "Sukces", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this.mainFrame, "Produkt o podanym ID nie istnieje w systemie.", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this.mainFrame, "Błąd podczas usuwania produktu. Sprawdź poprawność danych.", "Błąd", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void displayProducts() {
        List<Product> products = orderManagementSystem.getAllProducts();
        if (products.isEmpty()) {
            JOptionPane.showMessageDialog(this.mainFrame, "Brak produktów w systemie.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JTable table = this.createTable(products);

            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(400, 300));
            JOptionPane.showMessageDialog(this.mainFrame, scrollPane, "Lista Produktów", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private JTable createTable(List<Product> products) {
        // Definiowanie nagłówków dla kolumn tabeli
        String[] columnNames = {"ID", "Nazwa", "Opis", "SKU", "Cena netto", "Cena brutto", "Wymiary (dł.)",
                "Wymiary (szer.)", "Wymiary (wys.)", "Waga"};

        // Tworzenie modelu tabeli z danymi produktów
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Product product : products) {
            model.addRow(new Object[]{product.getId(), product.getName(), product.getDescription(), product.getSku(),
                    product.getNetPrice(), product.getGrossPrice(), product.getDimensions().getLength(),
                    product.getDimensions().getWidth(), product.getDimensions().getHeight(), product.getWeight()});
        }

        JTable table = new JTable(model);
        table.setFillsViewportHeight(true); // Opcjonalnie, aby tabela zajmowała całą dostępną przestrzeń

        return table;
    }

    private void clearTextFields() {
        this.productIdField.setText("");
        this.productNameField.setText("");
        this.productDescriptionField.setText("");
        this.productSKUField.setText("");
        this.productNetPriceField.setText("");
        this.productGrossPriceField.setText("");
        this.productLengthDimensionField.setText("");
        this.productWidthDimensionField.setText("");
        this.productHeightDimensionField.setText("");
        this.productWeightField.setText("");
    }
}
