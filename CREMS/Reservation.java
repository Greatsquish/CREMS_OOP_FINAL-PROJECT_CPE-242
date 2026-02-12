package project_oop;

// This class links a student to a resource
// It simulates reserving a resource for a period of time
public class Reservation {

    private Resource resource;
    private Student student;
    private int duration; // duration in minutes

    // Once reserved, the resource status changes automatically
    public Reservation(Resource resource, Student student, int duration) {
        this.resource = resource;
        this.student = student;
        this.duration = duration;
        resource.setStatus("Reserved");
    }

    public Resource getResource() {
        return resource;
    }
}

