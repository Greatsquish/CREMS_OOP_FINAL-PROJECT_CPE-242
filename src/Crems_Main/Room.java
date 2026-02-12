/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Crems_Main;

/**
 *
 * @author NIEL
 */
public class Room extends Resource {
    
    private String roomNumber;
    private int capacity; // how many people can use it
    
    public Room(String resourceId, String name, int maxUsageTime, 
                String roomNumber, int capacity) {
        super(resourceId, name, "Room", maxUsageTime);
        this.roomNumber = roomNumber;
        this.capacity = capacity;
    }
    
    public String getRoomNumber() {
        return roomNumber;
    }
    
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
    
    public int getCapacity() {
        return capacity;
    }
    
    public void setCapacity(int capacity) {
        if(capacity > 0) {
            this.capacity = capacity;
        } else {
            System.out.println("ERROR: Capacity must be positive");
        }
    }
    
    @Override
    public String toString() {
        return getName() + " [Room " + roomNumber + "] - " + getStatus() + 
               " (Capacity: " + capacity + ")";
    }
}
