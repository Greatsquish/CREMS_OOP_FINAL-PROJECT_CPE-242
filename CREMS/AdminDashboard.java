package project_oop;

import javax.swing.*;

// Simple admin dashboard
// This can be expanded later with buttons and tables
public class AdminDashboard extends JFrame {

    public AdminDashboard(User admin) {
        setTitle("Admin Dashboard");
        setSize(400, 300);

        JTextArea info = new JTextArea();
        info.setText(
                "Welcome, " + admin.getUsername() + "\n\n" +
                "Admin Responsibilities:\n" +
                "- Manage resources\n" +
                "- Monitor reservations\n" +
                "- Handle maintenance"
        );

        info.setEditable(false);
        add(info);
        setVisible(true);
    }
}
