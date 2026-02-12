package project_oop;

// This class represents a campus resource
// Example: equipment, devices, or rooms
public class Resource {

    // Resource details are kept private for safety
    private String id;
    private String name;
    private String category;
    private String status;

    // When a resource is created, it starts as available
    public Resource(String id, String name, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.status = "Available";
    }

    // Getter methods allow safe access to data
    public String getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getStatus() { return status; }

    // Used to update the current status of the resource
    public void setStatus(String status) {
        this.status = status;
    }

    // Controls how the resource appears in the dropdown list
    public String toString() {
        return name + " (" + status + ")";
    }
}
