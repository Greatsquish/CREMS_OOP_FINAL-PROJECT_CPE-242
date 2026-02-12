/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Crems_Main;

/**
 *
 * @author NIEL
 */
    // Made this abstract so Admin and Student can extend it
public abstract class User {
    
    // user info
    private String userId;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String role;
    private boolean isLoggedIn;
    
    // Constructor
    public User(String userId, String username, String password, String fullName, String email, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.isLoggedIn = false;
    }
    
    // getters and setters
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public boolean isLoggedIn() {
        return isLoggedIn;
    }
    
    public void setLoggedIn(boolean loggedIn) {
        this.isLoggedIn = loggedIn;
    }
    
    // check if username and password match
    public boolean authenticate(String username, String password) {
        if(this.username.equals(username) && this.password.equals(password)) {
            return true;
        }
        return false;
    }
    
    // login method
    public void login() {
        this.isLoggedIn = true;
        System.out.println(fullName + " logged in successfully");
    }
    
    // logout
    public void logout() {
        this.isLoggedIn = false;
        System.out.println(fullName + " logged out");
    }
    
    // abstract methods - subclasses need to implement these
    public abstract void displayDashboard();
    
    public abstract String getPermissions();
    
    public abstract boolean hasPermission(String action);
    
    @Override
    public String toString() {
        return "User: " + fullName + " (" + role + ")";
    }
}

