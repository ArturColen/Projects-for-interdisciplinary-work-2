package com.ti2cc;

public class Student {
    private int id;
    private String name;
    private String email;
    private String phoneNumber;
    private String enrollmentDate;
    private String course;

    public Student() {
        this.id = -1;
        this.name = "";
        this.email = "";
        this.phoneNumber = "";
        this.enrollmentDate = "";
        this.course = "";
    }
    
    public Student(int id, String name, String email, String phoneNumber, String enrollmentDate, String course) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.enrollmentDate = enrollmentDate;
        this.course = course;
    }
    
    public Student(String name, String email, String phoneNumber, String enrollmentDate, String course) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.enrollmentDate = enrollmentDate;
        this.course = course;
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

    public String getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(String enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "Student [id= " + id + ", name =" + name + ", email =" + email + 
               ", phoneNumber =" + phoneNumber + ", enrollmentDate =" + enrollmentDate + 
               ", course =" + course + "]";
    }
}