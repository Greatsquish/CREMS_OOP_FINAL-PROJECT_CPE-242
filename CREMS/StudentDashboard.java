package project_oop;

import javax.swing.*;
import java.util.ArrayList;

// Dashboard for students to reserve resources
public class StudentDashboard extends JFrame {

    // Shared list of resources (simulation only)
    static ArrayList<Resource> resources = new ArrayList<>();

    public StudentDashboard(Student student) {
        setTitle("Student Dashboard");
        setSize(400, 200);

        // Add sample resources only once
        if (resources.isEmpty()) {
            resources.add(new Resource("R1", "Oscilloscope", "Equipment"));
            resources.add(new Resource("R2", "Lab Room 1", "Room"));
        }

        JComboBox<Resource> resourceBox =
                new JComboBox<>(resources.toArray(new Resource[0]));
        JButton reserveBtn = new JButton("Reserve Resource");

        // Handles resource reservation
        reserveBtn.addActionListener(e -> {
            Resource selected = (Resource) resourceBox.getSelectedItem();

            // Prevent double reservation
            if (!selected.getStatus().equals("Available")) {
                JOptionPane.showMessageDialog(this,
                        "Resource is not available.");
            } else {
                new Reservation(selected, student, 60);
                JOptionPane.showMessageDialog(this,
                        "Resource reserved successfully!");
            }
        });

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(resourceBox);
        add(reserveBtn);
        setVisible(true);
    }
}
