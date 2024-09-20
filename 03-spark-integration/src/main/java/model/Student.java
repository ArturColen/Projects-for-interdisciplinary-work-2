package model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Student {
    private int id;
    private String name;
    private String email;
    private String phoneNumber;
    private LocalDateTime enrollmentDate;
    private String course;

    public Student() {
        this.id = -1;
        this.name = "";
        this.email = "";
        this.phoneNumber = "";
        this.enrollmentDate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        this.course = "";
    }

    public Student(int id, String name, String email, String phoneNumber, LocalDateTime enrollmentDate, String course) {
        setId(id);
        setName(name);
        setEmail(email);
        setPhoneNumber(phoneNumber);
        setEnrollmentDate(enrollmentDate);
        setCourse(course);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDateTime enrollmentDate) {
        LocalDateTime now = LocalDateTime.now();

        if (now.compareTo(enrollmentDate) >= 0) {
            this.enrollmentDate = enrollmentDate;
        }
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "Student: " + name + "   E-mail: " + email + "   Phone: " + phoneNumber +
                "   Enrollment Date: " + enrollmentDate + "   Course: " + course;
    }

    @Override
    public boolean equals(Object obj) {
        return (this.getId() == ((Student) obj).getId());
    }
}