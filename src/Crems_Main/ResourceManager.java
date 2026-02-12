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

// Manages all resources in the system with file persistence
public class ResourceManager {
    
    private ArrayList<Resource> resources;
    private int nextId;
    
    public ResourceManager() {
        // try to load resources from file first
        resources = FileHandler.loadResources();
        
        // calculate next ID based on existing resources
        if(!resources.isEmpty()) {
            int maxId = 0;
            for(Resource r : resources) {
                String id = r.getResourceId();
                if(id.startsWith("RES")) {
                    try {
                        int num = Integer.parseInt(id.substring(3));
                        if(num > maxId) maxId = num;
                    } catch(Exception e) {
                        // ignore invalid IDs
                    }
                }
            }
            nextId = maxId + 1;
        } else {
            nextId = 1;
        }
    }
    
    // add a new resource with validation
    public boolean addResource(Resource resource) {
        if(resource == null) {
            System.out.println("ERROR: Cannot add null resource");
            return false;
        }
        
        // validate resource ID
        if(resource.getResourceId() == null || resource.getResourceId().trim().isEmpty()) {
            System.out.println("ERROR: Resource ID cannot be empty");
            return false;
        }
        
        // validate resource name
        if(resource.getName() == null || resource.getName().trim().isEmpty()) {
            System.out.println("ERROR: Resource name cannot be empty");
            return false;
        }
        
        // check if ID already exists
        for(Resource r : resources) {
            if(r.getResourceId().equals(resource.getResourceId())) {
                System.out.println("ERROR: Resource ID '" + resource.getResourceId() + "' already exists");
                return false;
            }
        }
        
        // validate max usage time
        if(resource.getMaxUsageTime() <= 0) {
            System.out.println("ERROR: Max usage time must be positive");
            return false;
        }
        
        resources.add(resource);
        System.out.println("Resource added: " + resource.getName());
        saveToFile(); // auto-save after adding
        return true;
    }
    
    // remove a resource with validation
    public boolean removeResource(String resourceId) {
        if(resourceId == null || resourceId.trim().isEmpty()) {
            System.out.println("ERROR: Resource ID cannot be empty");
            return false;
        }
        
        for(int i = 0; i < resources.size(); i++) {
            if(resources.get(i).getResourceId().equals(resourceId)) {
                Resource removed = resources.remove(i);
                System.out.println("Resource removed: " + removed.getName());
                saveToFile(); // auto-save after removing
                return true;
            }
        }
        
        System.out.println("ERROR: Resource '" + resourceId + "' not found");
        return false;
    }
    
    // find resource by ID with error handling
    public Resource findResourceById(String resourceId) {
        if(resourceId == null || resourceId.trim().isEmpty()) {
            System.out.println("ERROR: Resource ID cannot be empty");
            return null;
        }
        
        for(Resource r : resources) {
            if(r.getResourceId().equalsIgnoreCase(resourceId.trim())) {
                return r;
            }
        }
        
        System.out.println("ERROR: Resource '" + resourceId + "' not found");
        return null;
    }
    
    // get all available resources
    public ArrayList<Resource> getAvailableResources() {
        ArrayList<Resource> available = new ArrayList<>();
        for(Resource r : resources) {
            try {
                if(r.isAvailable()) {
                    available.add(r);
                }
            } catch(Exception e) {
                System.out.println("WARNING: Error checking resource status - " + e.getMessage());
            }
        }
        return available;
    }
    
    // get resources by category with validation
    public ArrayList<Resource> getResourcesByCategory(String category) {
        if(category == null || category.trim().isEmpty()) {
            System.out.println("ERROR: Category cannot be empty");
            return new ArrayList<>();
        }
        
        ArrayList<Resource> result = new ArrayList<>();
        for(Resource r : resources) {
            if(r.getCategory().equalsIgnoreCase(category.trim())) {
                result.add(r);
            }
        }
        return result;
    }
    
    // get resources by status
    public ArrayList<Resource> getResourcesByStatus(String status) {
        if(status == null || status.trim().isEmpty()) {
            System.out.println("ERROR: Status cannot be empty");
            return new ArrayList<>();
        }
        
        ArrayList<Resource> result = new ArrayList<>();
        for(Resource r : resources) {
            if(r.getStatus().equalsIgnoreCase(status.trim())) {
                result.add(r);
            }
        }
        return result;
    }
    
    // get all resources
    public ArrayList<Resource> getAllResources() {
        return resources;
    }
    
    // display all resources
    public void displayAllResources() {
        if(resources.isEmpty()) {
            System.out.println("No resources in the system");
            return;
        }
        
        System.out.println("\n===== ALL RESOURCES =====");
        for(Resource r : resources) {
            System.out.println(r);
        }
        System.out.println("Total: " + resources.size() + " resources");
    }
    
    // display available resources only
    public void displayAvailableResources() {
        ArrayList<Resource> available = getAvailableResources();
        
        if(available.isEmpty()) {
            System.out.println("No available resources at the moment");
            return;
        }
        
        System.out.println("\n===== AVAILABLE RESOURCES =====");
        for(Resource r : available) {
            System.out.println(r);
        }
        System.out.println("Total: " + available.size() + " available");
    }
    
    // display resources by category
    public void displayResourcesByCategory(String category) {
        ArrayList<Resource> categoryRes = getResourcesByCategory(category);
        
        if(categoryRes.isEmpty()) {
            System.out.println("No " + category + " resources found");
            return;
        }
        
        System.out.println("\n===== " + category.toUpperCase() + " RESOURCES =====");
        for(Resource r : categoryRes) {
            System.out.println(r);
        }
        System.out.println("Total: " + categoryRes.size());
    }
    
    // generate next resource ID
    public String generateResourceId() {
        return "RES" + String.format("%03d", nextId++);
    }
    
    // save resources to file
    public boolean saveToFile() {
        return FileHandler.saveResources(resources);
    }
    
    // reload resources from file
    public void reloadFromFile() {
        ArrayList<Resource> loaded = FileHandler.loadResources();
        if(!loaded.isEmpty()) {
            resources = loaded;
            System.out.println("Resources reloaded from file");
        }
    }
    
    // get statistics
    public void displayStatistics() {
        System.out.println("\n===== RESOURCE STATISTICS =====");
        System.out.println("Total Resources: " + resources.size());
        System.out.println("Available: " + getAvailableResources().size());
        System.out.println("Reserved: " + getResourcesByStatus("Reserved").size());
        System.out.println("In Use: " + getResourcesByStatus("In Use").size());
        System.out.println("Under Maintenance: " + getResourcesByStatus("Under Maintenance").size());
        System.out.println("\nBy Category:");
        System.out.println("  Equipment: " + getResourcesByCategory("Equipment").size());
        System.out.println("  Devices: " + getResourcesByCategory("Device").size());
        System.out.println("  Rooms: " + getResourcesByCategory("Room").size());
    }
}