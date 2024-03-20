//import javax.swing.*;
//
//public class OrderManagementGUI extends JFrame {
//    private OrderManagementSystem orderManagementSystem;
//
//    // Panel do wyboru między klientami a produktami
//    private JTabbedPane tabbedPane;
//
//    public OrderManagementGUI(OrderManagementSystem orderManagementSystem) {
//        this.orderManagementSystem = orderManagementSystem;
//
//        // Konfiguracja okna
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setSize(800, 600);
//        setTitle("Order Management System");
//
//        // Inicjalizacja komponentów GUI
//        initializeComponents();
//
//        // Dodanie komponentów GUI do kontenera
//        addComponentsToContainer();
//
//        // Wyświetlanie okna
//        setVisible(true);
//    }
//
//    private void initializeComponents() {
//        // Inicjalizacja panelu do wyboru między klientami a produktami
//        tabbedPane = new JTabbedPane();
//    }
//
//    private void addComponentsToContainer() {
//        // Utworzenie zakładek do panelu
//        OrderManagementGUI_Customers customersTab = new OrderManagementGUI_Customers(this, this.orderManagementSystem);
//        OrderManagementGUI_Products productsTab = new OrderManagementGUI_Products(this, this.orderManagementSystem);
//        OrderManagementGUI_Orders ordersTab = new OrderManagementGUI_Orders(this, this.orderManagementSystem);
//
//        // Dodanie zakładek do panelu
//        tabbedPane.addTab("Klienci", customersTab.createPanel());
//        tabbedPane.addTab("Produkty", productsTab.createPanel());
//        tabbedPane.addTab("Zamówienia", ordersTab.createPanel());
//
//        // Dodanie panelu do kontenera
//        add(tabbedPane);
//    }
//}


//////////////////////////////////////////////////////////////
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class OrderManagementGUI extends JFrame {
//    private OrderManagementSystem orderManagementSystem;
//    private JPanel cardPanel;
//    private CardLayout cardLayout;
//
//    private JButton customersButton;
//    private JButton productsButton;
//    private JButton ordersButton;
//
//    public OrderManagementGUI(OrderManagementSystem orderManagementSystem) {
//        this.orderManagementSystem = orderManagementSystem;
//
//        // Konfiguracja okna
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setSize(800, 600);
//        setTitle("Order Management System");
//
//        // Inicjalizacja komponentów GUI
//        initializeComponents();
//
//        // Dodanie komponentów GUI do kontenera
//        addComponentsToContainer();
//
//        // Wyświetlanie okna
//        setVisible(true);
//    }
//
//    private void initializeComponents() {
//        // Inicjalizacja panelu do wyboru między klientami a produktami
//        cardLayout = new CardLayout();
//        cardPanel = new JPanel(cardLayout);
//
//        customersButton = new JButton("Klienci");
//        productsButton = new JButton("Produkty");
//        ordersButton = new JButton("Zamówienia");
//
//        customersButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                cardLayout.show(cardPanel, "Customers");
//            }
//        });
//
//        productsButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                cardLayout.show(cardPanel, "Products");
//            }
//        });
//
//        ordersButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                cardLayout.show(cardPanel, "Orders");
//            }
//        });
//    }
//
//    private void addComponentsToContainer() {
//        // Utworzenie paneli
//        OrderManagementGUI_Customers customersTab = new OrderManagementGUI_Customers(this, this.orderManagementSystem);
//        OrderManagementGUI_Products productsTab = new OrderManagementGUI_Products(this, this.orderManagementSystem);
//        OrderManagementGUI_Orders ordersTab = new OrderManagementGUI_Orders(this, this.orderManagementSystem);
//
//        // Dodanie paneli do panelu kart
//        cardPanel.add(customersTab.createPanel(), "Customers");
//        cardPanel.add(productsTab.createPanel(), "Products");
//        cardPanel.add(ordersTab.createPanel(), "Orders");
//
//        // Dodanie przycisków do górnego panelu
//        JPanel buttonPanel = new JPanel();
//        buttonPanel.add(customersButton);
//        buttonPanel.add(productsButton);
//        buttonPanel.add(ordersButton);
//
//        // Dodanie panelu kart i przycisków do kontenera
//        Container contentPane = getContentPane();
//        contentPane.add(buttonPanel, BorderLayout.NORTH);
//        contentPane.add(cardPanel, BorderLayout.CENTER);
//    }
//}
///////////////////////////////////////


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderManagementGUI extends JFrame {
    private OrderManagementSystem orderManagementSystem;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    private JButton customersButton;
    private JButton productsButton;
    private JButton ordersButton;

    public OrderManagementGUI(OrderManagementSystem orderManagementSystem) {
        this.orderManagementSystem = orderManagementSystem;

        // Konfiguracja okna
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setTitle("Order Management System");

        // Inicjalizacja komponentów GUI
        initializeComponents();

        // Dodanie komponentów GUI do kontenera
        addComponentsToContainer();

        // Wyświetlanie okna
        setVisible(true);
    }

    private void initializeComponents() {
        // Inicjalizacja panelu do wyboru między klientami a produktami
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        customersButton = new JButton("Klienci");
        productsButton = new JButton("Produkty");
        ordersButton = new JButton("Zamówienia");

        // Ustawienie koloru tła na srebrny
        customersButton.setBackground(Color.GRAY);
        productsButton.setBackground(Color.GRAY);
        ordersButton.setBackground(Color.GRAY);

        customersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Customers");
            }
        });

        productsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Products");
            }
        });

        ordersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Orders");
            }
        });
    }

    private void addComponentsToContainer() {
        // Utworzenie paneli
        OrderManagementGUI_Customers customersTab = new OrderManagementGUI_Customers(this, this.orderManagementSystem);
        OrderManagementGUI_Products productsTab = new OrderManagementGUI_Products(this, this.orderManagementSystem);
        OrderManagementGUI_Orders ordersTab = new OrderManagementGUI_Orders(this, this.orderManagementSystem);

        // Dodanie paneli do panelu kart
        cardPanel.add(customersTab.createPanel(), "Customers");
        cardPanel.add(productsTab.createPanel(), "Products");
        cardPanel.add(ordersTab.createPanel(), "Orders");

        // Dodanie przycisków do górnego panelu
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(customersButton);
        buttonPanel.add(productsButton);
        buttonPanel.add(ordersButton);

        // Dodanie panelu kart i przycisków do kontenera
        Container contentPane = getContentPane();
        contentPane.add(buttonPanel, BorderLayout.NORTH);
        contentPane.add(cardPanel, BorderLayout.CENTER);
    }



}
