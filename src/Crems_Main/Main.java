/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Crems_Main;

/**
 *
 * @author NIEL
 */


// Main class - runs the CREMS system in console mode (no GUI)
// This is for testing before we add JavaFX
import java.util.Scanner;

// Main class - CREMS with file handling and error handling
public class Main {
    
    private static UserManager userManager;
    private static ResourceManager resourceManager;
    private static Reservationmanager reservationManager;
    private static Scanner scanner;
    private static User currentUser;
    
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("  CREMS - Campus Resource & Equipment");
        System.out.println("     Management System v2.0");
        System.out.println("    (With File Persistence)");
        System.out.println("========================================\n");
        
        // initialize managers (they auto-load from files)
        userManager = new UserManager();
        resourceManager = new ResourceManager();
        reservationManager = new Reservationmanager();
        scanner = new Scanner(System.in);
        
        // add sample resources only if none exist
        if(resourceManager.getAllResources().isEmpty()) {
            initializeSampleResources();
        }
        
        // login
        if(login()) {
            // show dashboard based on user role
            if(currentUser instanceof Admin) {
                adminMenu();
            } else if(currentUser instanceof Student) {
                studentMenu();
            }
        }
        
        scanner.close();
        System.out.println("\nThank you for using CREMS!");
    }
    
    // initialize sample resources
    private static void initializeSampleResources() {
        System.out.println("Creating sample resources...");
        
        Resource osc = new Equipment("RES001", "Oscilloscope #1", 120, 
                                     "Tektronix", "TEK-001");
        Resource multi = new Equipment("RES002", "Multimeter #1", 60,
                                       "Fluke", "FLU-123");
        
        Resource comp1 = new Device("RES003", "Computer #1", 180,
                                    "Desktop", "i5, 8GB RAM");
        Resource comp2 = new Device("RES004", "Computer #2", 180,
                                    "Desktop", "i7, 16GB RAM");
        
        Resource lab1 = new Room("RES005", "Engineering Lab 1", 240,
                                 "ENG-101", 30);
        Resource lab2 = new Room("RES006", "Computer Lab", 240,
                                 "CS-201", 40);
        
        resourceManager.addResource(osc);
        resourceManager.addResource(multi);
        resourceManager.addResource(comp1);
        resourceManager.addResource(comp2);
        resourceManager.addResource(lab1);
        resourceManager.addResource(lab2);
        
        System.out.println("Sample resources created and saved!\n");
    }
    
    // login function with error handling
    private static boolean login() {
        int attempts = 0;
        final int MAX_ATTEMPTS = 3;
        
        System.out.println("===== LOGIN =====");
        System.out.println("Default accounts:");
        System.out.println("  Admin: username=admin, password=admin123");
        System.out.println("  Student: username=student1, password=pass123");
        System.out.println("           username=student2, password=pass456\n");
        
        while(attempts < MAX_ATTEMPTS) {
            try {
                System.out.print("Username: ");
                String username = scanner.nextLine();
                
                System.out.print("Password: ");
                String password = scanner.nextLine();
                
                currentUser = userManager.login(username, password);
                
                if(currentUser != null) {
                    System.out.println("\nLogin successful!");
                    currentUser.displayDashboard();
                    return true;
                } else {
                    attempts++;
                    if(attempts < MAX_ATTEMPTS) {
                        System.out.println("Login failed. " + (MAX_ATTEMPTS - attempts) + " attempts remaining.\n");
                    }
                }
            } catch(Exception e) {
                System.out.println("ERROR: Login error - " + e.getMessage());
                attempts++;
            }
        }
        
        System.out.println("\nToo many failed attempts. Exiting...");
        return false;
    }
    
    // admin menu with file operations
    private static void adminMenu() {
        while(true) {
            System.out.println("\n===== ADMIN MENU =====");
            System.out.println("1. View All Resources");
            System.out.println("2. Add Resource");
            System.out.println("3. Remove Resource");
            System.out.println("4. Mark Resource Under Maintenance");
            System.out.println("5. View All Reservations");
            System.out.println("6. View Active Reservations");
            System.out.println("7. Check Overdue Reservations");
            System.out.println("8. View Resource Statistics");
            System.out.println("9. Save All Data");
            System.out.println("10. Reload Data from Files");
            System.out.println("11. Logout");
            System.out.print("Choose option: ");
            
            String choice = scanner.nextLine().trim();
            
            try {
                switch(choice) {
                    case "1":
                        resourceManager.displayAllResources();
                        break;
                    case "2":
                        addResourceMenu();
                        break;
                    case "3":
                        removeResourceMenu();
                        break;
                    case "4":
                        markMaintenanceMenu();
                        break;
                    case "5":
                        reservationManager.displayAllReservations();
                        break;
                    case "6":
                        reservationManager.displayActiveReservations();
                        break;
                    case "7":
                        reservationManager.getOverdueReservations();
                        break;
                    case "8":
                        resourceManager.displayStatistics();
                        break;
                    case "9":
                        saveAllData();
                        break;
                    case "10":
                        reloadAllData();
                        break;
                    case "11":
                        currentUser.logout();
                        return;
                    default:
                        System.out.println("Invalid option. Please choose 1-11.");
                }
            } catch(Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }
    
    // student menu
    private static void studentMenu() {
        Student student = (Student) currentUser;
        
        while(true) {
            System.out.println("\n===== STUDENT MENU =====");
            System.out.println("1. View Available Resources");
            System.out.println("2. View Resources by Category");
            System.out.println("3. Reserve a Resource");
            System.out.println("4. View My Reservations");
            System.out.println("5. Cancel a Reservation");
            System.out.println("6. Logout");
            System.out.print("Choose option: ");
            
            String choice = scanner.nextLine().trim();
            
            try {
                switch(choice) {
                    case "1":
                        resourceManager.displayAvailableResources();
                        break;
                    case "2":
                        viewByCategory();
                        break;
                    case "3":
                        makeReservationMenu(student);
                        break;
                    case "4":
                        student.showMyReservations();
                        break;
                    case "5":
                        cancelReservationMenu(student);
                        break;
                    case "6":
                        currentUser.logout();
                        return;
                    default:
                        System.out.println("Invalid option. Please choose 1-6.");
                }
            } catch(Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }
    
    // view resources by category
    private static void viewByCategory() {
        System.out.println("\nCategories: 1=Equipment, 2=Device, 3=Room");
        System.out.print("Choose category: ");
        String choice = scanner.nextLine().trim();
        
        String category = "";
        if(choice.equals("1")) category = "Equipment";
        else if(choice.equals("2")) category = "Device";
        else if(choice.equals("3")) category = "Room";
        else {
            System.out.println("Invalid category");
            return;
        }
        
        resourceManager.displayResourcesByCategory(category);
    }
    
    // add resource with better error handling
    private static void addResourceMenu() {
        System.out.println("\n--- Add New Resource ---");
        System.out.println("Type: 1=Equipment, 2=Device, 3=Room");
        System.out.print("Choose type: ");
        String type = scanner.nextLine().trim();
        
        try {
            System.out.print("Resource Name: ");
            String name = scanner.nextLine().trim();
            if(name.isEmpty()) {
                System.out.println("ERROR: Name cannot be empty");
                return;
            }
            
            System.out.print("Max Usage Time (minutes): ");
            String timeInput = scanner.nextLine().trim();
            int maxTime = Integer.parseInt(timeInput);
            
            if(maxTime <= 0) {
                System.out.println("ERROR: Time must be positive");
                return;
            }
            
            String resId = resourceManager.generateResourceId();
            
            if(type.equals("1")) {
                // Equipment
                System.out.print("Manufacturer: ");
                String mfr = scanner.nextLine().trim();
                System.out.print("Serial Number: ");
                String sn = scanner.nextLine().trim();
                
                Equipment eq = new Equipment(resId, name, maxTime, mfr, sn);
                resourceManager.addResource(eq);
                
            } else if(type.equals("2")) {
                // Device
                System.out.print("Device Type (Desktop/Laptop/Tablet): ");
                String devType = scanner.nextLine().trim();
                System.out.print("Specs: ");
                String specs = scanner.nextLine().trim();
                
                Device dev = new Device(resId, name, maxTime, devType, specs);
                resourceManager.addResource(dev);
                
            } else if(type.equals("3")) {
                // Room
                System.out.print("Room Number: ");
                String roomNum = scanner.nextLine().trim();
                System.out.print("Capacity: ");
                String capInput = scanner.nextLine().trim();
                int cap = Integer.parseInt(capInput);
                
                if(cap <= 0) {
                    System.out.println("ERROR: Capacity must be positive");
                    return;
                }
                
                Room room = new Room(resId, name, maxTime, roomNum, cap);
                resourceManager.addResource(room);
                
            } else {
                System.out.println("Invalid type");
            }
        } catch(NumberFormatException e) {
            System.out.println("ERROR: Invalid number format");
        } catch(Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
    
    // remove resource
    private static void removeResourceMenu() {
        System.out.print("\nEnter Resource ID to remove: ");
        String resId = scanner.nextLine().trim();
        
        if(resId.isEmpty()) {
            System.out.println("ERROR: Resource ID cannot be empty");
            return;
        }
        
        resourceManager.removeResource(resId);
    }
    
    // mark resource under maintenance
    private static void markMaintenanceMenu() {
        System.out.print("\nEnter Resource ID: ");
        String resId = scanner.nextLine().trim();
        
        if(resId.isEmpty()) {
            System.out.println("ERROR: Resource ID cannot be empty");
            return;
        }
        
        Resource resource = resourceManager.findResourceById(resId);
        if(resource != null) {
            resource.markUnderMaintenance();
            resourceManager.saveToFile(); // save changes
        }
    }
    
    // make a reservation with better error handling
    private static void makeReservationMenu(Student student) {
        try {
            if(!student.canReserve()) {
                System.out.println("ERROR: You have reached maximum reservations (3)");
                return;
            }
            
            resourceManager.displayAvailableResources();
            
            if(resourceManager.getAvailableResources().isEmpty()) {
                System.out.println("No resources available at the moment");
                return;
            }
            
            System.out.print("\nEnter Resource ID to reserve: ");
            String resId = scanner.nextLine().trim();
            
            if(resId.isEmpty()) {
                System.out.println("ERROR: Resource ID cannot be empty");
                return;
            }
            
            Resource resource = resourceManager.findResourceById(resId);
            if(resource == null) {
                return;
            }
            
            System.out.print("Duration (minutes): ");
            String durationInput = scanner.nextLine().trim();
            int duration = Integer.parseInt(durationInput);
            
            if(duration <= 0) {
                System.out.println("ERROR: Duration must be positive");
                return;
            }
            
            if(duration > resource.getMaxUsageTime()) {
                System.out.println("WARNING: Duration exceeds max time (" + 
                                 resource.getMaxUsageTime() + " mins)");
                System.out.print("Continue anyway? (yes/no): ");
                String confirm = scanner.nextLine().trim();
                if(!confirm.equalsIgnoreCase("yes")) {
                    System.out.println("Reservation cancelled");
                    return;
                }
            }
            
            Reservation res = reservationManager.createReservation(resource, student, duration);
            if(res != null) {
                System.out.println("Reservation created: " + res.getReservationId());
                resourceManager.saveToFile(); // save resource status change
            }
        } catch(NumberFormatException e) {
            System.out.println("ERROR: Invalid number format");
        } catch(Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
    
    // cancel reservation with validation
    private static void cancelReservationMenu(Student student) {
        student.showMyReservations();
        
        if(student.getMyReservations().isEmpty()) {
            return;
        }
        
        System.out.print("\nEnter Reservation ID to cancel: ");
        String resId = scanner.nextLine().trim();
        
        if(resId.isEmpty()) {
            System.out.println("ERROR: Reservation ID cannot be empty");
            return;
        }
        
        if(reservationManager.cancelReservation(resId)) {
            resourceManager.saveToFile(); // save resource status change
        }
    }
    
    // save all data to files
    private static void saveAllData() {
        System.out.println("\nSaving all data...");
        boolean usersSaved = userManager.saveToFile();
        boolean resourcesSaved = resourceManager.saveToFile();
        
        if(usersSaved && resourcesSaved) {
            System.out.println("All data saved successfully!");
        } else {
            System.out.println("Some data could not be saved. Check errors above.");
        }
    }
    
    // reload all data from files
    private static void reloadAllData() {
        System.out.print("\nReload data from files? This will discard unsaved changes. (yes/no): ");
        String confirm = scanner.nextLine().trim();
        
        if(confirm.equalsIgnoreCase("yes")) {
            userManager.reloadFromFile();
            resourceManager.reloadFromFile();
            System.out.println("Data reloaded from files");
        } else {
            System.out.println("Reload cancelled");
        }
    }
}