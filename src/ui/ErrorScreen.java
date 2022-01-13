package ui;

import javax.swing.*;
import java.awt.*;

public class ErrorScreen extends JFrame {
    public ErrorScreen(String s) {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("Error!");

        JLabel errorLabel = new JLabel("<html>" + s + "</html>", SwingConstants.CENTER);
        errorLabel.setMaximumSize(new Dimension(200, 80));

        this.add(errorLabel);
        this.setMinimumSize(new Dimension(250, 100));
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
