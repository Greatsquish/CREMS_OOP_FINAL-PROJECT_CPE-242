/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Crems_Main;

/**
 *
 * @author NIEL
 */
public class Equipment extends Resource {
    
    private String manufacturer;
    private String serialNumber;
    
    public Equipment(String resourceId, String name, int maxUsageTime, 
                     String manufacturer, String serialNumber) {
        super(resourceId, name, "Equipment", maxUsageTime);
        this.manufacturer = manufacturer;
        this.serialNumber = serialNumber;
    }
    
    public String getManufacturer() {
        return manufacturer;
    }
    
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
    
    public String getSerialNumber() {
        return serialNumber;
    }
    
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
    
    @Override
    public String toString() {
        return getName() + " [Equipment] - " + getStatus() + " (S/N: " + serialNumber + ")";
    }
}
