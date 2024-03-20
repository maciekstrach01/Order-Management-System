import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class OrderManagementSystem {
    private List<Customer> customers;
    private List<Product> products;
    private List<Order> orders;

    private static final String CUSTOMERS_FILE = "customers.txt";
    private static final String PRODUCTS_FILE = "products.txt";
    private static final String ORDERS_FILE = "orders.txt";
    private Product[] productList;

    public OrderManagementSystem() {
        this.customers = new ArrayList<>();
        this.products = new ArrayList<>();
        this.orders = new ArrayList<>();

        loadCustomers();
        loadProducts();
        loadOrders();
    }

    public List<Customer> getCustomersByTotalOrderValue(double minimumTotalOrderValue, boolean sortByDescending) {
        Map<Integer, Double> customerTotalValues = new HashMap<>();

        // Obliczanie łącznej wartości zamówień dla każdego klienta
        for (Order order : orders) {
            customerTotalValues.merge(order.getCustomerId(), order.getOrderGrossValue(), Double::sum);
        }

        // Filtrowanie klientów spełniających kryterium minimalnej wartości zamówienia
        List<Customer> filteredCustomers = customers.stream()
                .filter(customer -> customerTotalValues.getOrDefault(customer.getId(), 0.0) >= minimumTotalOrderValue)
                .collect(Collectors.toList());

        // Sortowanie klientów według łącznej wartości zamówień
        filteredCustomers.sort(Comparator.comparingDouble(customer -> customerTotalValues.get(customer.getId())));

        if (sortByDescending) {
            Collections.reverse(filteredCustomers);
        }

        return filteredCustomers;
    }

    public Customer getCustomerById(int customerId) {
        for (Customer customer : customers) {
            if (customer.getId() == customerId) {
                return customer;
            }
        }
        return null;
    }

    public Product getProductById(int productId) {
        for (Product product : products) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    public List<Customer> getCustomersByOrderDateRange(Date startDate, Date endDate) {
        List<Customer> matchingCustomers = new ArrayList<>();
        for (Order order : orders) {
            if (order.getOrderDate().after(startDate) && order.getOrderDate().before(endDate)) {
                Customer customer = getCustomerById(order.getCustomerId());
                if (customer != null && !matchingCustomers.contains(customer)) {
                    matchingCustomers.add(customer);
                }
            }
        }
        return matchingCustomers;
    }

    public List<Product> getProductsBySearchString(String searchString) {
        List<Product> matchingProducts = new ArrayList<>();

        for (Product product : productList) {
            if (product.getName().toLowerCase().contains(searchString.toLowerCase())) {
                matchingProducts.add(product);
            }
        }

        return matchingProducts;
    }

    public double getOrderValueForCustomer(Customer customer) {
        double orderValue = 0;
        for (Order order : orders) {
            if (order.getCustomerId() == customer.getId()) {
                orderValue += order.getOrderGrossValue();
            }
        }
        return orderValue;
    }

    private void saveCustomers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CUSTOMERS_FILE))) {
            oos.writeObject(customers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveProducts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PRODUCTS_FILE))) {
            oos.writeObject(products);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveOrders() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ORDERS_FILE))) {
            oos.writeObject(orders);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Odczytywanie danych z plików
    @SuppressWarnings("unchecked")
    private void loadCustomers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CUSTOMERS_FILE))) {
            customers = (List<Customer>) ois.readObject();
        } catch (FileNotFoundException ignored) {
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadProducts() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PRODUCTS_FILE))) {
            products = (List<Product>) ois.readObject();
            productList = products.toArray(new Product[0]);
        } catch (FileNotFoundException ignored) {
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadOrders() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ORDERS_FILE))) {
            orders = (List<Order>) ois.readObject();
        } catch (FileNotFoundException ignored) {
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Funkcja dodająca klienta do systemu
    public void addCustomer(Customer customer) {
        customers.add(customer);
        System.out.println("Klient dodany do systemu: " + customer.toString());
        saveCustomers();
    }

    public boolean removeCustomerById(int customerId) {
        Iterator<Customer> iterator = customers.iterator();
        while (iterator.hasNext()) {
            Customer currentCustomer = iterator.next();
            if (currentCustomer.getId() == customerId) {
                iterator.remove();
                saveCustomers();
                return true;
            }
        }
        return false;
    }

    // Funkcja zwracająca wszystkich klientów w systemie
    public List<Customer> getAllCustomers() {
        return customers;
    }

    // Funkcja dodająca produkt do systemu
    public void addProduct(Product product) {
        products.add(product);
        productList = products.toArray(new Product[0]);
        System.out.println("Produkt dodany do systemu: " + product.toString());
        saveProducts();
    }

    public boolean removeProduct(int productId) {
        Iterator<Product> iterator = products.iterator();
        while (iterator.hasNext()) {
            Product currentProduct = iterator.next();
            if (currentProduct.getId() == productId) {
                iterator.remove();
                productList = products.toArray(new Product[0]);
                saveProducts();
                return true;
            }
        }
        return false;
    }

    // Funkcja zwracająca wszystkie produkty w systemie
    public List<Product> getAllProducts() {
        return products;
    }

    // Funkcja zwracająca wszystkie produkty zawierające określony ciąg znaków w nazwie
    public List<Product> getProductsByName(String searchName) {
        List<Product> matchingProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getName().contains(searchName)) {
                matchingProducts.add(product);
            }
        }
        return matchingProducts;
    }

    // Funkcja dodająca zamówienie do systemu
    public void addOrder(Order order) {
        orders.add(order);
        System.out.println("Zamówienie dodane do systemu: " + order.toString());
        saveOrders();
    }

    public boolean orderExists(int orderId) {
        for (Order order : orders) {
            if (order.getId() == orderId) {
                return true;
            }
        }
        return false;
    }

    // Metoda usuwająca zamówienie ze systemu na podstawie ID zamówienia
    public boolean removeOrder(int orderId) {
        Iterator<Order> iterator = orders.iterator();
        while (iterator.hasNext()) {
            Order currentOrder = iterator.next();
            if (currentOrder.getId() == orderId) {
                iterator.remove();
                saveOrders();
                return true;
            }
        }
        return false;
    }

    // Funkcja zwracająca wszystkie zamówienia w systemie
    public List<Order> getAllOrders() {
        return orders;
    }

    public List<Order> getOrdersInDateRange(Date startDate, Date endDate) {
        List<Order> matchingOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getOrderDate().after(startDate) && order.getOrderDate().before(endDate)) {
                matchingOrders.add(order);
            }
        }
        return matchingOrders;
    }

    public List<Order> getOrdersByCustomer(int customerId) {
        List<Order> matchingOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getCustomerId() == customerId) {
                matchingOrders.add(order);
            }
        }
        return matchingOrders;
    }

    public List<Order> getOrdersByValueRange(double minValue, double maxValue) {
        List<Order> matchingOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getOrderGrossValue() >= minValue && order.getOrderGrossValue() <= maxValue) {
                matchingOrders.add(order);
            }
        }
        return matchingOrders;
    }
}
