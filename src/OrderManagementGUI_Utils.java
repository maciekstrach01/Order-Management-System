import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class OrderManagementGUI_Utils {
    public JPanel createTextFieldWithClearButton(JTextField textField) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(textField, BorderLayout.CENTER);

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> textField.setText(""));
        clearButton.setBackground(Color.BLACK);
        clearButton.setForeground(Color.WHITE);
        panel.add(clearButton, BorderLayout.EAST);

        return panel;
    }

    public JPanel createActionButton(String text, ActionListener actionListener) {
        JPanel panel = new JPanel(new BorderLayout());
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        panel.add(button, BorderLayout.CENTER);
        return panel;
    }

    // Metoda tworząca etykietę w jasnopopielatym prostokącie z białą czcionką

    public JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setOpaque(true);
        label.setBackground(new Color(70, 70, 70)); // Jasnopopielaty kolor tła
        label.setForeground(Color.WHITE); // Biała czcionka
        label.setFont(new Font("Arial", Font.PLAIN, 18)); // Ustawienie większej czcionki
        label.setHorizontalAlignment(JLabel.CENTER); // Wyśrodkowanie tekstu
        return label;
    }
}
