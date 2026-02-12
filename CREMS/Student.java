package project_oop;

// Student is also a type of User
// Students can view and reserve resources
public class Student extends User {

    public Student(String username) {
        super(username, "Student");
    }

    // Opens the student dashboard after login
    public void showDashboard() {
        new StudentDashboard(this);
    }
}