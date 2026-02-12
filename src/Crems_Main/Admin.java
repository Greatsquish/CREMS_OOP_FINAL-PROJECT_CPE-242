/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Crems_Main;

/**
 *
 * @author NIEL
 */
public class Admin extends User {
    
    private String department;
    private String position;
    
    // constructor
    public Admin(String userId, String username, String password, String fullName, 
                 String email, String department, String position) {
        super(userId, username, password, fullName, email, "ADMIN");
        this.department = department;
        this.position = position;
    }
    
    // getters and setters
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public String getPosition() {
        return position;
    }
    
    public void setPosition(String position) {
        this.position = position;
    }
    
    @Override
    public void displayDashboard() {
        System.out.println("========== ADMIN DASHBOARD ==========");
        System.out.println("Welcome back, " + getFullName());
        System.out.println("Position: " + position);
        System.out.println("Department: " + department);
        System.out.println("\nWhat you can do:");
        System.out.println("- Add, Edit, Delete Resources");
        System.out.println("- View All Reservations");
        System.out.println("- Mark Resources Under Maintenance");
        System.out.println("- View Reports");
        System.out.println("=====================================");
    }
    
    @Override
    public String getPermissions() {
        return "Full Access - Can do everything";
    }
    
    // admin has all permissions
    @Override
    public boolean hasPermission(String action) {
        return true; // admins can do anything
    }
    
    // methods for managing resources
    public void addResource() {
        System.out.println("Admin is adding a new resource...");
        // TODO: implement actual resource adding logic
    }
    
    public void editResource(String resourceId) {
        System.out.println("Editing resource: " + resourceId);
        // TODO: connect to resource manager
    }
    
    public void deleteResource(String resourceId) {
        System.out.println("Deleting resource: " + resourceId);
        // TODO: add confirmation dialog
    }
    
    public void markUnderMaintenance(String resourceId) {
        System.out.println("Resource " + resourceId + " marked as UNDER MAINTENANCE");
    }
    
    public void viewAllReservations() {
        System.out.println("Viewing all reservations in the system...");
    }
    
    public void generateReport() {
        System.out.println("Generating usage report...");
    }
    
    @Override
    public String toString() {
        return "Admin: " + getFullName() + " - " + position + " at " + department;
    }
}