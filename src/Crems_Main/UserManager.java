/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Crems_Main;

/**
 *
 * @author NIEL
 */
import java.util.ArrayList;

// Manages all users in the system with file persistence
public class UserManager {
    
    private ArrayList<User> users;
    
    public UserManager() {
        // try to load users from file first
        users = FileHandler.loadUsers();
        
        // if no users found, create default ones
        if(users.isEmpty()) {
            initializeDefaultUsers();
        }
    }
    
    // add default users
    private void initializeDefaultUsers() {
        // default admin
        Admin admin = new Admin("ADM001", "admin", "admin123", "Juan Santos",
                               "admin@crems.edu", "Engineering Lab", "Lab Technician");
        users.add(admin);
        
        // default students
        Student student1 = new Student("STU001", "student1", "pass123", "Pedro Cruz",
                                      "pedro@student.crems.edu", "2022-001", 
                                      "Computer Engineering", 2);
        users.add(student1);
        
        Student student2 = new Student("STU002", "student2", "pass456", "Maria Reyes",
                                      "maria@student.crems.edu", "2022-002",
                                      "Electrical Engineering", 2);
        users.add(student2);
        
        System.out.println("Default users created");
        saveToFile(); // save the defaults
    }
    
    // authenticate user with error handling
    public User login(String username, String password) {
        // validation
        if(username == null || username.trim().isEmpty()) {
            System.out.println("ERROR: Username cannot be empty");
            return null;
        }
        
        if(password == null || password.trim().isEmpty()) {
            System.out.println("ERROR: Password cannot be empty");
            return null;
        }
        
        // search for user
        for(User user : users) {
            try {
                if(user.authenticate(username.trim(), password)) {
                    user.login();
                    return user;
                }
            } catch(Exception e) {
                System.out.println("ERROR: Authentication failed - " + e.getMessage());
                continue;
            }
        }
        
        System.out.println("ERROR: Invalid username or password");
        return null;
    }
    
    // add new user with validation
    public boolean addUser(User user) {
        if(user == null) {
            System.out.println("ERROR: Cannot add null user");
            return false;
        }
        
        // check if username already exists
        for(User u : users) {
            if(u.getUsername().equalsIgnoreCase(user.getUsername())) {
                System.out.println("ERROR: Username '" + user.getUsername() + "' already exists");
                return false;
            }
        }
        
        // check if user ID already exists
        for(User u : users) {
            if(u.getUserId().equals(user.getUserId())) {
                System.out.println("ERROR: User ID already exists");
                return false;
            }
        }
        
        users.add(user);
        System.out.println("User added: " + user.getFullName());
        saveToFile(); // auto-save after adding
        return true;
    }
    
    // remove user
    public boolean removeUser(String userId) {
        if(userId == null || userId.trim().isEmpty()) {
            System.out.println("ERROR: Invalid user ID");
            return false;
        }
        
        for(int i = 0; i < users.size(); i++) {
            if(users.get(i).getUserId().equals(userId)) {
                User removed = users.remove(i);
                System.out.println("User removed: " + removed.getFullName());
                saveToFile(); // auto-save after removing
                return true;
            }
        }
        
        System.out.println("ERROR: User not found");
        return false;
    }
    
    // find user by username with error handling
    public User findUserByUsername(String username) {
        if(username == null || username.trim().isEmpty()) {
            System.out.println("ERROR: Username cannot be empty");
            return null;
        }
        
        for(User u : users) {
            if(u.getUsername().equalsIgnoreCase(username.trim())) {
                return u;
            }
        }
        
        System.out.println("ERROR: User '" + username + "' not found");
        return null;
    }
    
    // get all students
    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> students = new ArrayList<>();
        for(User u : users) {
            if(u instanceof Student) {
                students.add((Student) u);
            }
        }
        return students;
    }
    
    // get all admins
    public ArrayList<Admin> getAllAdmins() {
        ArrayList<Admin> admins = new ArrayList<>();
        for(User u : users) {
            if(u instanceof Admin) {
                admins.add((Admin) u);
            }
        }
        return admins;
    }
    
    // display all users
    public void displayAllUsers() {
        if(users.isEmpty()) {
            System.out.println("No users in the system");
            return;
        }
        
        System.out.println("\n===== ALL USERS =====");
        for(User u : users) {
            System.out.println(u);
        }
        System.out.println("Total: " + users.size() + " users");
    }
    
    // save users to file
    public boolean saveToFile() {
        return FileHandler.saveUsers(users);
    }
    
    // reload users from file
    public void reloadFromFile() {
        ArrayList<User> loaded = FileHandler.loadUsers();
        if(!loaded.isEmpty()) {
            users = loaded;
            System.out.println("Users reloaded from file");
        }
    }
    
    // get all users (for file operations)
    public ArrayList<User> getAllUsers() {
        return users;
    }
}