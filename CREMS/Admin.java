package project_oop;

// Admin is a type of User
// This class represents lab technicians or instructors
public class Admin extends User {

    public Admin(String username) {
        super(username, "Admin");
    }

    // Opens the admin dashboard when the admin logs in
    public void showDashboard() {
        new AdminDashboard(this);
    }
}
