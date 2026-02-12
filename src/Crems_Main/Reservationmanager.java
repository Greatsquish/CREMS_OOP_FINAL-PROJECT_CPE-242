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

// Manages all reservations in the system
public class Reservationmanager {
    
    private ArrayList<Reservation> reservations;
    private int nextId;
    
    public Reservationmanager() {
        reservations = new ArrayList<>();
        nextId = 1;
    }
    
    // create a new reservation
    public Reservation createReservation(Resource resource, Student student, int duration) {
        // validation happens in Reservation constructor
        String resId = generateReservationId();
        Reservation reservation = new Reservation(resId, resource, student, duration);
        
        // only add if creation was successful
        if(reservation.getStatus() != null && reservation.getStatus().equals("Active")) {
            reservations.add(reservation);
            return reservation;
        }
        
        return null;
    }
    
    // cancel a reservation
    public boolean cancelReservation(String reservationId) {
        Reservation res = findReservationById(reservationId);
        if(res != null) {
            return res.cancel();
        }
        return false;
    }
    
    // complete a reservation
    public void completeReservation(String reservationId) {
        Reservation res = findReservationById(reservationId);
        if(res != null) {
            res.complete();
        }
    }
    
    // find reservation by ID
    public Reservation findReservationById(String reservationId) {
        for(Reservation r : reservations) {
            if(r.getReservationId().equals(reservationId)) {
                return r;
            }
        }
        System.out.println("ERROR: Reservation " + reservationId + " not found");
        return null;
    }
    
    // get all active reservations
    public ArrayList<Reservation> getActiveReservations() {
        ArrayList<Reservation> active = new ArrayList<>();
        for(Reservation r : reservations) {
            if(r.getStatus().equals("Active")) {
                active.add(r);
            }
        }
        return active;
    }
    
    // get reservations for a specific student
    public ArrayList<Reservation> getStudentReservations(Student student) {
        ArrayList<Reservation> studentRes = new ArrayList<>();
        for(Reservation r : reservations) {
            if(r.getStudent().equals(student)) {
                studentRes.add(r);
            }
        }
        return studentRes;
    }
    
    // get reservations for a specific resource
    public ArrayList<Reservation> getResourceReservations(Resource resource) {
        ArrayList<Reservation> resourceRes = new ArrayList<>();
        for(Reservation r : reservations) {
            if(r.getResource().equals(resource)) {
                resourceRes.add(r);
            }
        }
        return resourceRes;
    }
    
    // check for overdue reservations
    public ArrayList<Reservation> getOverdueReservations() {
        ArrayList<Reservation> overdue = new ArrayList<>();
        for(Reservation r : reservations) {
            if(r.isOverdue()) {
                overdue.add(r);
                System.out.println("WARNING: Reservation " + r.getReservationId() + 
                                 " is overdue!");
            }
        }
        return overdue;
    }
    
    // display all reservations
    public void displayAllReservations() {
        if(reservations.isEmpty()) {
            System.out.println("No reservations in the system");
            return;
        }
        
        System.out.println("\n===== ALL RESERVATIONS =====");
        for(Reservation r : reservations) {
            System.out.println(r);
        }
        System.out.println("Total: " + reservations.size() + " reservations");
    }
    
    // display active reservations only
    public void displayActiveReservations() {
        ArrayList<Reservation> active = getActiveReservations();
        
        if(active.isEmpty()) {
            System.out.println("No active reservations");
            return;
        }
        
        System.out.println("\n===== ACTIVE RESERVATIONS =====");
        for(Reservation r : active) {
            System.out.println(r + " | Remaining: " + r.getRemainingMinutes() + " mins");
        }
        System.out.println("Total: " + active.size() + " active");
    }
    
    // get all reservations
    public ArrayList<Reservation> getAllReservations() {
        return reservations;
    }
    
    // generate next reservation ID
    private String generateReservationId() {
        return "RSV" + String.format("%03d", nextId++);
    }
}