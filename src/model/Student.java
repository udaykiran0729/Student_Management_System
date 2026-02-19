package model;

public class Student {

    private int rollNo;
    private String name;
    private String email;
    private String course;
    private double marks;
    private double attendance;

    public Student(int rollNo, String name, String email,
                   String course, double marks, double attendance) {
        this.rollNo = rollNo;
        this.name = name;
        this.email = email;
        this.course = course;
        this.marks = marks;
        this.attendance = attendance;
    }

    public int getRollNo() { return rollNo; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getCourse() { return course; }
    public double getMarks() { return marks; }
    public double getAttendance() { return attendance; }
}
