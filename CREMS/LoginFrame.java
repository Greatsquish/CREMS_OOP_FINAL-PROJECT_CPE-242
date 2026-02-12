package project_oop;

import javax.swing.*;
import java.awt.*;

// This is the first window users see
// It handles login and role selection
public class LoginFrame extends JFrame {

    public LoginFrame() {
        setTitle("CREMS Login");
        setSize(300, 200);
        setLayout(new GridLayout(3, 2));

        JTextField userField = new JTextField();
        JComboBox<String> roleBox =
                new JComboBox<>(new String[]{"Admin", "Student"});
        JButton loginBtn = new JButton("Login");

        add(new JLabel("Username:"));
        add(userField);
        add(new JLabel("Role:"));
        add(roleBox);
        add(loginBtn);

        // Runs when the login button is clicked
        loginBtn.addActionListener(e -> {
            String username = userField.getText();
            String role = roleBox.getSelectedItem().toString();

            // Simple validation to avoid empty usernames
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a username.");
                return;
            }

            // Close login window after successful login
            dispose();

            // Open dashboard based on selected role
            if (role.equals("Admin")) {
                new Admin(username).showDashboard();
            } else {
                new Student(username).showDashboard();
            }
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
