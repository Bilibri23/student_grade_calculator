package com.studentgradecalculator.studentgradecalculator;

public class student_grade {
    private int ID;
    private String student_name;
    private String course_name;
    private int exam_mark;
    private int CA_mark;
    private int attendance_mark;
    private double average;
    private String Grade;


    public student_grade(int ID,String student_name, String course_name, int exam_mark, int CA_mark, int attendance_mark, double average, String Grade) {
        this.ID = ID;
        this.student_name = student_name;
        this.course_name = course_name;
        this.exam_mark = exam_mark;
        this.CA_mark = CA_mark;
        this.attendance_mark = attendance_mark;
        this.average = average;
        this.Grade = Grade;
    }

    public int getID(){return ID;}

    public double getAverage(){return average;}

    public String getStudent_name() {
        return student_name;
    }

    public String getCourse_name() {
        return course_name;
    }

    public int getExam_mark() {
        return exam_mark;
    }

    public int getCA_mark() {
        return CA_mark;
    }

    public int getAttendance_mark() {
        return attendance_mark;
    }
    public String getGrade(){
        return Grade;
    }

    public void setID(int ID){
        this.ID = ID;
    }
    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public void setExam_mark(int exam_mark) {
        this.exam_mark = exam_mark;
    }

    public void setCA_mark(int CA_mark) {
        this.CA_mark = CA_mark;
    }

    public void setAttendance_mark(int attendance_mark) {
        this.attendance_mark = attendance_mark;
    }
    public void setAverage(double average){
        this.average = average;}

    public void setGrade(String Grade){
        this.Grade = Grade;
    }
}
