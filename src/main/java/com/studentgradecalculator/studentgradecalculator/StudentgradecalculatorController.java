package com.studentgradecalculator.studentgradecalculator;

import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.Comparator;
import java.util.ResourceBundle;

public class StudentgradecalculatorController implements Initializable {

    @FXML
    public Button btn_add;

    @FXML
    public Button btn_calc;

    @FXML
    public Button btn_clear;

    @FXML
    public Button btn_update;

    @FXML
    public Button btn_delete;
    @FXML
    public TextField txtavg;

    @FXML
    public TextField txtclassAv;

    @FXML
    public TextField txtsearch;

    @FXML
    public TableColumn<student_grade, Integer> tablecolumnCA;

    @FXML
    public TableColumn<student_grade, String> tablecolumnGPA;

    @FXML
    public TableColumn<student_grade, Integer> tablecolumnID;

    @FXML
    public TableColumn<student_grade, String> tablecolumnStudent;

    @FXML
    public TableColumn<student_grade, Integer> tablecolumnattendance;

    @FXML
    public TableColumn<student_grade, Double> tablecolumnaverage;

    @FXML
    public TableColumn<student_grade, String> tablecolumncourse;

    @FXML
    public TableColumn<student_grade, Integer> tablecolumnexam;

    @FXML
    public TableView<student_grade> tablestudent;

    @FXML
    public TextField txt_CA;

    @FXML
    public TextField txtgrade;

    @FXML
    public TextField txt_attendance;

    @FXML
    public TextField txt_course;

    @FXML
    public TextField txt_mark;

    @FXML
    public TextField txt_name;

    ObservableList<student_grade> studentlist;

    int index = -1;
    Connection con;
    ResultSet rs;
    PreparedStatement ps;


    @FXML
    void Add(ActionEvent event) {
        con = mysqlconnect.ConnectionDb();
        String sql = "insert into studentgrade(student_name, course_name, exam_mark,CA_mark,attendance_mark, average, Grade)values(?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, txt_name.getText());
            ps.setString(2, txt_course.getText());
            ps.setString(3, txt_mark.getText());
            ps.setString(4, txt_CA.getText());
            ps.setString(5, txt_attendance.getText());
            ps.setString(6, txtavg.getText());
            ps.setString(7, txtgrade.getText());
            ps.execute();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("student info added");
            alert.showAndWait();
            //refresh tableview after adding a new student
            populateTable();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    void Update(ActionEvent event) {
        student_grade selected_student = tablestudent.getSelectionModel().getSelectedItem();
        if(selected_student != null){
            con = mysqlconnect.ConnectionDb();
            String sql ="UPDATE studentgrade SET student_name =?, course_name =?, exam_mark = ?, CA_mark = ?," +
                    "attendance_mark = ?, average =?, Grade = ? WHERE ID = ?";
            try{
               ps = con.prepareStatement(sql);
               ps.setString(1, txt_name.getText());
               ps.setString(2, txt_course.getText());
               ps.setString(3, txt_mark.getText());
               ps.setString(4, txt_CA.getText());
               ps.setString(5, txt_attendance.getText());
               ps.setString(6, txtavg.getText());
               ps.setString(7, txtgrade.getText());
               ps.setInt(8, selected_student.getID());
               ps.executeUpdate();
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setContentText("student info updated");
               alert.showAndWait();
               // refresh table view with data from database
                populateTable();

            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("pls select a student from tableview to update");
            alert.showAndWait();
        }
    }


    @FXML
    void Delete(ActionEvent event) {
        student_grade selected_student = tablestudent.getSelectionModel().getSelectedItem();
        if(selected_student != null){
            try{
                con = mysqlconnect.ConnectionDb();
                String sql = "DELETE FROM studentgrade WHERE ID = ?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, selected_student.getID());
                ps.executeUpdate();
                ps.close();
                con.close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("delete info added");
                alert.showAndWait();
                populateTable();
            }catch(SQLException e){
                System.out.println(e.getMessage());}
    }
    else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("pls select student to delete");
            alert.showAndWait();
        }
    }

    @FXML
    void calculation(ActionEvent event) {
        // your average will determine your grade. where the average will be set to 20
        int mark1, mark2, mark3, total;
        double average;

        // I can handle exceptions here using alert boxes, where the user might forget to input data.

        if (txt_mark.getText().isEmpty() || txt_CA.getText().isEmpty() || txt_attendance.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("warning");
            alert.setHeaderText(null);
            alert.setContentText("All  fields are required");
            alert.showAndWait();
            return; // exit the method

        }

        mark1 = Integer.parseInt(txt_mark.getText()); // taking the mark from the text box and casting it to integer
        mark2 = Integer.parseInt(txt_CA.getText());
        mark3 = Integer.parseInt(txt_attendance.getText());
        total = mark1 + mark2 + mark3;
        average = ((double)total / 100) * 20;
        txtavg.setText(String.valueOf(average));

        if (average >= 17) {
            txtgrade.setText("A");
        } else if (average < 17 && average >= 15) {
            txtgrade.setText("B");
        } else if (average < 15 && average >= 13) {
            txtgrade.setText("C");
        } else if (average < 13 && average >= 10) {
            txtgrade.setText("D");
        } else if (average < 10 && average >= 7) {
            txtgrade.setText("E");
        } else if (average < 7) {
            txtgrade.setText("F");
        }

    }

    @FXML
    void clear(ActionEvent event) {
        txt_name.setText("");
        txt_course.setText("");
        txt_mark.setText("");
        txt_CA.setText("");
        txt_attendance.setText("");
        txtavg.setText("");
        txtgrade.setText("");
        txt_name.requestFocus();

    }

    @FXML
    void classaverage(ActionEvent event) {
        double totalAverage = 0.0;
        int totalStudents = studentlist.size();
        System.out.println(totalStudents);

        for(student_grade student : studentlist){
            totalAverage += student.getAverage();

        }

        double  classAverage = totalAverage/totalStudents;

        txtclassAv.setText(String.valueOf(classAverage));
    }


    @FXML
    void Search(ActionEvent event) {
        con = mysqlconnect.ConnectionDb();
        FilteredList<student_grade> studentdata = new FilteredList<>(studentlist, b -> true);

        txtsearch.textProperty().addListener((observable, oldvalue, newvalue) -> {
            studentdata.setPredicate(studentGrade -> {
                //if not found display all current records
                if (newvalue.isEmpty() || newvalue.isBlank() || newvalue == null) {
                    return true;
                }
                String searchkeyword = newvalue.toLowerCase();

                if (studentGrade.getStudent_name().toLowerCase().indexOf(searchkeyword) > -1) {
                    return true;// we found a match in student name

                } else if (studentGrade.getCourse_name().toLowerCase().indexOf(searchkeyword) > -1) {
                    return true;
                } else
                    return false;


            });

        });
        SortedList<student_grade> sortedData = new SortedList<>(studentdata);
        //bind sorted result with table view
        sortedData.comparatorProperty().bind(tablestudent.comparatorProperty());
        tablestudent.setItems(sortedData);
    }

    @FXML
    void showStudentsBelowThreshold(ActionEvent event) {
        int threshold = 10; // Define your threshold here
        ObservableList<student_grade> belowThresholdStudents = FXCollections.observableArrayList();

        for(student_grade student : studentlist) {
            if(student.getAverage() < threshold) {
                belowThresholdStudents.add(student);
            }
        }

        tablestudent.setItems(belowThresholdStudents);
            }

// Similarly, implement event handlers for other report generation buttons

    @FXML
    void sortByAverages(ActionEvent event) {
        // Sort the studentlist by averages
        studentlist.sort(Comparator.comparingDouble(student_grade::getAverage));

    }

    @FXML
    void showTopPerformingStudents(ActionEvent event) {
        int topN = 5; // Define how many top-performing students you want to display
        ObservableList<student_grade> topStudents = FXCollections.observableArrayList();

        // Sort the studentlist by averages in descending order
        studentlist.sort(Comparator.comparingDouble(student_grade::getAverage).reversed());

        // Get the top N students
        for(int i = 0; i < topN && i < studentlist.size(); i++) {
            topStudents.add(studentlist.get(i));
        }

        tablestudent.setItems(topStudents);
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateTable();
        // populating the table view
        tablestudent.getSelectionModel().selectedItemProperty().addListener((obs, oldselection, newselection)->{
            if(newselection!= null){
                txt_name.setText(newselection.getStudent_name());
                txt_course.setText(newselection.getCourse_name());
                txt_mark.setText(String.valueOf(newselection.getExam_mark()));
                txt_CA.setText(String.valueOf(newselection.getCA_mark()));
                txt_attendance.setText(String.valueOf(newselection.getCA_mark()));
                txtavg.setText(String.valueOf(newselection.getAverage()));
                txtgrade.setText(newselection.getGrade());
            }
        });
    }
    private void populateTable() {
    try {
            con = mysqlconnect.ConnectionDb();
            if (con != null) {
                studentlist = FXCollections.observableArrayList();
                String query = "SELECT * FROM studentgrade";
                ps = con.prepareStatement(query);
                rs = ps.executeQuery();
                while(rs.next()){
                    student_grade student  = new student_grade(rs.getInt("ID"),
                            rs.getString("student_name"),
                            rs.getString("course_name"),
                            rs.getInt("exam_mark"),
                            rs.getInt("CA_mark"),
                            rs.getInt("attendance_mark"),
                            rs.getDouble("average"),
                            rs.getString("Grade"));
                    studentlist.add(student);


                }

                tablecolumnID.setCellValueFactory(new PropertyValueFactory<student_grade, Integer>("ID"));
                tablecolumnStudent.setCellValueFactory(new PropertyValueFactory<student_grade, String>("student_name"));
                tablecolumncourse.setCellValueFactory(new PropertyValueFactory<student_grade, String>("course_name"));
                tablecolumnexam.setCellValueFactory(new PropertyValueFactory<student_grade, Integer>("exam_mark"));
                tablecolumnCA.setCellValueFactory(new PropertyValueFactory<student_grade, Integer>("CA_mark"));
                tablecolumnattendance.setCellValueFactory(new PropertyValueFactory<student_grade, Integer>("attendance_mark"));
                tablecolumnaverage.setCellValueFactory(new PropertyValueFactory<student_grade, Double>("average"));
                tablecolumnGPA.setCellValueFactory(new PropertyValueFactory<student_grade, String>("Grade"));

                tablestudent.setItems(studentlist);
                rs.close();
                ps.close();
                con.close();
            } else {
                System.out.println("connection is null");
            }
        } catch (Exception e) {
            System.out.println("error initialising connection" + e.getMessage());
        }
    }
}


