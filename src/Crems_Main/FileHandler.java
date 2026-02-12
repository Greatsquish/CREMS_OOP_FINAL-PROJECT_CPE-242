/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Crems_Main;

/**
 *
 * @author NIEL
 */
import java.io.*;
import java.util.ArrayList;

// Handles saving and loading data to/from CSV files
// This is for data persistence without using a database
public class FileHandler {
    
    // file paths
    private static final String USERS_FILE = "users.csv";
    private static final String RESOURCES_FILE = "resources.csv";
    private static final String RESERVATIONS_FILE = "reservations.csv";
    
    // Save users to CSV file
    public static boolean saveUsers(ArrayList<User> users) {
        try {
            FileWriter writer = new FileWriter(USERS_FILE);
            
            // write header
            writer.write("userId,username,password,fullName,email,role,extraInfo\n");
            
            // write each user
            for(User user : users) {
                String line = "";
                
                if(user instanceof Admin) {
                    Admin admin = (Admin) user;
                    line = user.getUserId() + "," + 
                           user.getUsername() + "," + 
                           user.getPassword() + "," + 
                           user.getFullName() + "," + 
                           user.getEmail() + "," + 
                           user.getRole() + "," + 
                           admin.getDepartment() + "|" + admin.getPosition();
                } else if(user instanceof Student) {
                    Student student = (Student) user;
                    line = user.getUserId() + "," + 
                           user.getUsername() + "," + 
                           user.getPassword() + "," + 
                           user.getFullName() + "," + 
                           user.getEmail() + "," + 
                           user.getRole() + "," + 
                           student.getStudentNumber() + "|" + 
                           student.getProgram() + "|" + 
                           student.getYearLevel();
                }
                
                writer.write(line + "\n");
            }
            
            writer.close();
            System.out.println("Users saved successfully to " + USERS_FILE);
            return true;
            
        } catch(IOException e) {
            System.out.println("ERROR: Could not save users - " + e.getMessage());
            return false;
        }
    }
    
    // Load users from CSV file
    public static ArrayList<User> loadUsers() {
        ArrayList<User> users = new ArrayList<>();
        
        try {
            File file = new File(USERS_FILE);
            if(!file.exists()) {
                System.out.println("No saved users file found. Starting fresh.");
                return users;
            }
            
            BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE));
            
            // skip header
            String line = reader.readLine();
            
            // read each user
            while((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                
                if(parts.length < 7) continue; // skip invalid lines
                
                String userId = parts[0];
                String username = parts[1];
                String password = parts[2];
                String fullName = parts[3];
                String email = parts[4];
                String role = parts[5];
                String extraInfo = parts[6];
                
                User user = null;
                
                if(role.equals("ADMIN")) {
                    String[] info = extraInfo.split("\\|");
                    String department = info[0];
                    String position = info[1];
                    user = new Admin(userId, username, password, fullName, 
                                   email, department, position);
                } else if(role.equals("STUDENT")) {
                    String[] info = extraInfo.split("\\|");
                    String studentNum = info[0];
                    String program = info[1];
                    int yearLevel = Integer.parseInt(info[2]);
                    user = new Student(userId, username, password, fullName,
                                     email, studentNum, program, yearLevel);
                }
                
                if(user != null) {
                    users.add(user);
                }
            }
            
            reader.close();
            System.out.println("Loaded " + users.size() + " users from " + USERS_FILE);
            
        } catch(IOException e) {
            System.out.println("ERROR: Could not load users - " + e.getMessage());
        } catch(Exception e) {
            System.out.println("ERROR: File format error - " + e.getMessage());
        }
        
        return users;
    }
    
    // Save resources to CSV file
    public static boolean saveResources(ArrayList<Resource> resources) {
        try {
            FileWriter writer = new FileWriter(RESOURCES_FILE);
            
            // write header
            writer.write("resourceId,name,category,status,maxUsageTime,extraInfo\n");
            
            // write each resource
            for(Resource resource : resources) {
                String extraInfo = "";
                
                if(resource instanceof Equipment) {
                    Equipment eq = (Equipment) resource;
                    extraInfo = eq.getManufacturer() + "|" + eq.getSerialNumber();
                } else if(resource instanceof Device) {
                    Device dev = (Device) resource;
                    extraInfo = dev.getDeviceType() + "|" + dev.getSpecs();
                } else if(resource instanceof Room) {
                    Room room = (Room) resource;
                    extraInfo = room.getRoomNumber() + "|" + room.getCapacity();
                }
                
                String line = resource.getResourceId() + "," + 
                             resource.getName() + "," + 
                             resource.getCategory() + "," + 
                             resource.getStatus() + "," + 
                             resource.getMaxUsageTime() + "," + 
                             extraInfo;
                
                writer.write(line + "\n");
            }
            
            writer.close();
            System.out.println("Resources saved successfully to " + RESOURCES_FILE);
            return true;
            
        } catch(IOException e) {
            System.out.println("ERROR: Could not save resources - " + e.getMessage());
            return false;
        }
    }
    
    // Load resources from CSV file
    public static ArrayList<Resource> loadResources() {
        ArrayList<Resource> resources = new ArrayList<>();
        
        try {
            File file = new File(RESOURCES_FILE);
            if(!file.exists()) {
                System.out.println("No saved resources file found. Starting fresh.");
                return resources;
            }
            
            BufferedReader reader = new BufferedReader(new FileReader(RESOURCES_FILE));
            
            // skip header
            String line = reader.readLine();
            
            // read each resource
            while((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                
                if(parts.length < 6) continue;
                
                String resourceId = parts[0];
                String name = parts[1];
                String category = parts[2];
                String status = parts[3];
                int maxUsageTime = Integer.parseInt(parts[4]);
                String extraInfo = parts[5];
                
                Resource resource = null;
                
                if(category.equals("Equipment")) {
                    String[] info = extraInfo.split("\\|");
                    String manufacturer = info[0];
                    String serialNumber = info[1];
                    resource = new Equipment(resourceId, name, maxUsageTime, 
                                           manufacturer, serialNumber);
                } else if(category.equals("Device")) {
                    String[] info = extraInfo.split("\\|");
                    String deviceType = info[0];
                    String specs = info[1];
                    resource = new Device(resourceId, name, maxUsageTime, 
                                        deviceType, specs);
                } else if(category.equals("Room")) {
                    String[] info = extraInfo.split("\\|");
                    String roomNumber = info[0];
                    int capacity = Integer.parseInt(info[1]);
                    resource = new Room(resourceId, name, maxUsageTime, 
                                      roomNumber, capacity);
                }
                
                if(resource != null) {
                    resource.setStatus(status); // restore status
                    resources.add(resource);
                }
            }
            
            reader.close();
            System.out.println("Loaded " + resources.size() + " resources from " + RESOURCES_FILE);
            
        } catch(IOException e) {
            System.out.println("ERROR: Could not load resources - " + e.getMessage());
        } catch(Exception e) {
            System.out.println("ERROR: File format error - " + e.getMessage());
        }
        
        return resources;
    }
    
    // Save reservations to CSV (simplified version)
    // Note: In real app, you'd need to restore the Student and Resource objects
    public static boolean saveReservationLog(String reservationData) {
        try {
            FileWriter writer = new FileWriter(RESERVATIONS_FILE, true); // append mode
            writer.write(reservationData + "\n");
            writer.close();
            return true;
        } catch(IOException e) {
            System.out.println("ERROR: Could not save reservation log - " + e.getMessage());
            return false;
        }
    }
    
    // Clear all data files (for testing)
    public static void clearAllData() {
        try {
            new File(USERS_FILE).delete();
            new File(RESOURCES_FILE).delete();
            new File(RESERVATIONS_FILE).delete();
            System.out.println("All data files cleared");
        } catch(Exception e) {
            System.out.println("ERROR: Could not clear files - " + e.getMessage());
        }
    }
}