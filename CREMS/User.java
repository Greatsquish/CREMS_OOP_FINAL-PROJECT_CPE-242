package project_oop;

// This is the parent class for all users in the system
// Admin and Student will inherit from this class
public abstract class User {

    // Common details shared by all users
    protected String username;
    protected String role;

    // Constructor to initialize user details
    public User(String username, String role) {
        this.username = username;
        this.role = role;
    }

    // Returns the username of the user
    public String getUsername() {
        return username;
    }

    // Returns the role (Admin or Student)
    public String getRole() {
        return role;
    }

    // Each user will have a different dashboard
    // The child class decides what happens here
    public abstract void showDashboard();
}
