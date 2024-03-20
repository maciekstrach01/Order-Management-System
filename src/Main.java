import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            OrderManagementSystem orderManagementSystem = new OrderManagementSystem();
            new OrderManagementGUI(orderManagementSystem);
        });
    }
}


