/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Crems_Main;

import java.util.ArrayList;

/**
 *
 * @author NIEL
 */
public class Student extends User {
    
    private String studentNumber;
    private String program;
    private int yearLevel;
    private ArrayList<String> myReservations; // stores reservation IDs
    private int totalReservations;
    
    public Student(String userId, String username, String password, String fullName, 
                   String email, String studentNumber, String program, int yearLevel) {
        super(userId, username, password, fullName, email, "STUDENT");
        this.studentNumber = studentNumber;
        this.program = program;
        this.yearLevel = yearLevel;
        this.myReservations = new ArrayList<>();
        this.totalReservations = 0;
    }
    
    // getters and setters
    public String getStudentNumber() {
        return studentNumber;
    }
    
    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }
    
    public String getProgram() {
        return program;
    }
    
    public void setProgram(String program) {
        this.program = program;
    }
    
    public int getYearLevel() {
        return yearLevel;
    }
    
    public void setYearLevel(int yearLevel) {
        this.yearLevel = yearLevel;
    }
    
    public ArrayList<String> getMyReservations() {
        return myReservations;
    }
    
    public int getTotalReservations() {
        return totalReservations;
    }
    
    @Override
    public void displayDashboard() {
        System.out.println("========== STUDENT DASHBOARD ==========");
        System.out.println("Hello, " + getFullName() + "!");
        System.out.println("Student No: " + studentNumber);
        System.out.println("Program: " + program);
        System.out.println("Year: " + yearLevel);
        System.out.println("");
        System.out.println("Current Reservations: " + myReservations.size());
        System.out.println("Total Reservations Made: " + totalReservations);
        System.out.println("");
        System.out.println("Available Actions:");
        System.out.println("- Browse Available Resources");
        System.out.println("- Make a Reservation");
        System.out.println("- View My Reservations");
        System.out.println("- Cancel Reservation");
        System.out.println("=======================================");
    }
    
    @Override
    public String getPermissions() {
        return "Limited Access - Can only view and reserve resources";
    }
    
    // students have limited permissions
    @Override
    public boolean hasPermission(String action) {
        // check what action the student wants to do
        if(action.equals("view_resources")) {
            return true;
        }
        if(action.equals("reserve_resource")) {
            return true;
        }
        if(action.equals("cancel_own_reservation")) {
            return true;
        }
        if(action.equals("view_own_reservations")) {
            return true;
        }
        // students can't do admin stuff
        if(action.equals("add_resource") || action.equals("edit_resource") || 
           action.equals("delete_resource") || action.equals("view_all_reservations")) {
            return false;
        }
        return false;
    }
    
    // add a reservation
    public void makeReservation(String reservationId) {
        if(!myReservations.contains(reservationId)) {
            myReservations.add(reservationId);
            totalReservations++;
            System.out.println("Reservation " + reservationId + " added!");
        } else {
            System.out.println("You already have this reservation");
        }
    }
    
    // cancel reservation
    public boolean cancelReservation(String reservationId) {
        if(myReservations.contains(reservationId)) {
            myReservations.remove(reservationId);
            System.out.println("Reservation " + reservationId + " cancelled");
            return true;
        } else {
            System.out.println("Reservation not found");
            return false;
        }
    }
    
    public void viewAvailableResources() {
        System.out.println("Showing available resources...");
        // will connect to ResourceManager later
    }
    
    // show student's reservations
    public void showMyReservations() {
        System.out.println("Your Reservations:");
        if(myReservations.size() == 0) {
            System.out.println("  You have no active reservations");
        } else {
            for(int i = 0; i < myReservations.size(); i++) {
                System.out.println("  " + (i+1) + ". " + myReservations.get(i));
            }
        }
    }
    
    // check if student can make more reservations
    public boolean canReserve() {
        int maxReservations = 3; // max 3 at a time
        if(myReservations.size() < maxReservations) {
            return true;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return "Student: " + getFullName() + " (" + studentNumber + ") - " + 
               program + " Year " + yearLevel;
    }
}