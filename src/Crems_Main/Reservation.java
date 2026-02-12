/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Crems_Main;

/**
 *
 * @author NIEL
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Reservation class - links a student to a resource
public class Reservation {
    
    private String reservationId;
    private Resource resource;
    private Student student;
    private int duration; // in minutes
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status; // "Active", "Completed", "Cancelled"
    
    // constructor
    public Reservation(String reservationId, Resource resource, Student student, int duration) {
        // validation
        if(resource == null) {
            System.out.println("ERROR: Resource cannot be null");
            return;
        }
        if(student == null) {
            System.out.println("ERROR: Student cannot be null");
            return;
        }
        if(duration <= 0) {
            System.out.println("ERROR: Duration must be positive");
            return;
        }
        
        // check if resource is available
        if(!resource.isAvailable()) {
            System.out.println("ERROR: Resource " + resource.getName() + " is not available");
            return;
        }
        
        // check if student can make more reservations
        if(!student.canReserve()) {
            System.out.println("ERROR: Student has reached maximum reservations (3)");
            return;
        }
        
        this.reservationId = reservationId;
        this.resource = resource;
        this.student = student;
        this.duration = duration;
        this.startTime = LocalDateTime.now();
        this.endTime = startTime.plusMinutes(duration);
        this.status = "Active";
        
        // update resource status
        resource.reserve();
        
        // add to student's reservations
        student.makeReservation(reservationId);
        
        System.out.println("Reservation created successfully!");
    }
    
    // getters
    public String getReservationId() {
        return reservationId;
    }
    
    public Resource getResource() {
        return resource;
    }
    
    public Student getStudent() {
        return student;
    }
    
    public int getDuration() {
        return duration;
    }
    
    public LocalDateTime getStartTime() {
        return startTime;
    }
    
    public LocalDateTime getEndTime() {
        return endTime;
    }
    
    public String getStatus() {
        return status;
    }
    
    // mark as in use
    public void startUsing() {
        if(status.equals("Active")) {
            resource.markInUse();
            System.out.println("Resource is now being used");
        } else {
            System.out.println("ERROR: Reservation is not active");
        }
    }
    
    // complete the reservation
    public void complete() {
        if(status.equals("Active")) {
            this.status = "Completed";
            resource.release();
            student.cancelReservation(reservationId);
            System.out.println("Reservation completed. Resource released.");
        } else {
            System.out.println("ERROR: Cannot complete - reservation is " + status);
        }
    }
    
    // cancel reservation
    public boolean cancel() {
        if(status.equals("Active")) {
            // check if resource is already in use
            if(resource.getStatus().equals("In Use")) {
                System.out.println("ERROR: Cannot cancel - resource is already in use");
                return false;
            }
            
            this.status = "Cancelled";
            resource.release();
            student.cancelReservation(reservationId);
            System.out.println("Reservation cancelled successfully");
            return true;
        } else {
            System.out.println("ERROR: Can only cancel active reservations");
            return false;
        }
    }
    
    // check if reservation is overdue
    public boolean isOverdue() {
        return LocalDateTime.now().isAfter(endTime) && status.equals("Active");
    }
    
    // get remaining time in minutes
    public long getRemainingMinutes() {
        if(status.equals("Active")) {
            LocalDateTime now = LocalDateTime.now();
            if(now.isBefore(endTime)) {
                return java.time.Duration.between(now, endTime).toMinutes();
            }
        }
        return 0;
    }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return "Reservation #" + reservationId + " - " + resource.getName() + 
               " by " + student.getFullName() + 
               " | " + duration + " mins | " + status +
               " | Start: " + startTime.format(formatter);
    }
}
