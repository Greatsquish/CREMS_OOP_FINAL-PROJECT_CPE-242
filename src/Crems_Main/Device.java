/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Crems_Main;

/**
 *
 * @author NIEL
 */
public class Device extends Resource {
    
    private String deviceType; // "Computer", "Tablet", "Laptop"
    private String specs; // basic specs
    
    public Device(String resourceId, String name, int maxUsageTime, 
                  String deviceType, String specs) {
        super(resourceId, name, "Device", maxUsageTime);
        this.deviceType = deviceType;
        this.specs = specs;
    }
    
    public String getDeviceType() {
        return deviceType;
    }
    
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
    
    public String getSpecs() {
        return specs;
    }
    
    public void setSpecs(String specs) {
        this.specs = specs;
    }
    
    @Override
    public String toString() {
        return getName() + " [" + deviceType + "] - " + getStatus() + " (" + specs + ")";
    }
}
