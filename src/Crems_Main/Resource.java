/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Crems_Main;

/**
 *
 * @author NIEL
 */
public class Resource {
    
    // resource info
    private String resourceId;
    private String name;
    private String category; // "Equipment", "Device", or "Room"
    private String status; // "Available", "Reserved", "In Use", "Under Maintenance"
    private int maxUsageTime; // in minutes
    
    // constructor
    public Resource(String resourceId, String name, String category, int maxUsageTime) {
        this.resourceId = resourceId;
        this.name = name;
        this.category = category;
        this.status = "Available"; // new resources are available by default
        this.maxUsageTime = maxUsageTime;
    }
    
    // getters and setters
    public String getResourceId() {
        return resourceId;
    }
    
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getStatus() {
        return status;
    }
    
    // set status with validation
    public boolean setStatus(String status) {
        // check if status is valid
        if(status.equals("Available") || status.equals("Reserved") || 
           status.equals("In Use") || status.equals("Under Maintenance")) {
            this.status = status;
            return true;
        } else {
            System.out.println("ERROR: Invalid status. Use: Available, Reserved, In Use, or Under Maintenance");
            return false;
        }
    }
    
    public int getMaxUsageTime() {
        return maxUsageTime;
    }
    
    public void setMaxUsageTime(int maxUsageTime) {
        if(maxUsageTime > 0) {
            this.maxUsageTime = maxUsageTime;
        } else {
            System.out.println("ERROR: Max usage time must be positive");
        }
    }
    
    // check if resource is available for reservation
    public boolean isAvailable() {
        return status.equals("Available");
    }
    
    // mark as reserved
    public void reserve() {
        if(isAvailable()) {
            this.status = "Reserved";
            System.out.println(name + " has been reserved");
        } else {
            System.out.println("ERROR: Cannot reserve - resource is " + status);
        }
    }
    
    // mark as in use
    public void markInUse() {
        if(status.equals("Reserved")) {
            this.status = "In Use";
            System.out.println(name + " is now in use");
        } else {
            System.out.println("ERROR: Resource must be reserved first");
        }
    }
    
    // release the resource
    public void release() {
        this.status = "Available";
        System.out.println(name + " is now available");
    }
    
    // mark as under maintenance (admin only)
    public void markUnderMaintenance() {
        this.status = "Under Maintenance";
        System.out.println(name + " marked as UNDER MAINTENANCE");
    }
    
    @Override
    public String toString() {
        return name + " [" + category + "] - " + status;
    }
}