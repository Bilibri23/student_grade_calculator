package com.studentgradecalculator.studentgradecalculator;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class StudentgradecalculatorControllerTest {
    private StudentgradecalculatorController controller;

    @BeforeEach
    void setUp() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/test resources/StudentgradecalculatorTest.fxml"));
            Parent root = loader.load();
            controller = loader.getController();
            Platform.runLater(() -> {
                controller.txt_name = new TextField();
                controller.txt_course = new TextField();
                controller.txt_mark = new TextField();
                controller.txt_CA = new TextField();
                controller.txt_attendance = new TextField();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void add() {
        StudentgradecalculatorController controller = new StudentgradecalculatorController();
        controller.txt_name = new TextField();
        controller.txt_course = new TextField();
        controller.txt_mark = new TextField();
        controller.txt_CA = new TextField();
        controller.txt_attendance = new TextField();

        // Set text for input fields
        controller.txt_name.setText("Samuel");
        controller.txt_course.setText("Mathematics");
        controller.txt_mark.setText("60");
        controller.txt_CA.setText("20");
        controller.txt_attendance.setText("9");

        // Mock action event
        ActionEvent event = Mockito.mock(ActionEvent.class);

        // Call the method to add student
        controller.Add(event);

        // Assert that student is added to the list
        assertEquals(1, controller.studentlist.size(), "Student should be added to the list");
    }

    @Test
    void update() {
        // Assuming there's at least one student in the list for testing
        // Set up controller and populate the list
        StudentgradecalculatorController controller = new StudentgradecalculatorController();
        controller.studentlist.add(new student_grade(0, "mary", "civics", 15, 8, 9,12.4, "F"));

        // Set text for input fields
        controller.txt_name.setText("Updated Name");
        controller.txt_course.setText("Updated Course");
        controller.txt_mark.setText("90");
        controller.txt_CA.setText("85");
        controller.txt_attendance.setText("92");

        // Mock action event
        ActionEvent event = Mockito.mock(ActionEvent.class);

        // Set the index of the student to be updated
        controller.index = 0;

        // Call the method to update student
        controller.Update(event);

        // Assert that student information is updated
        assertEquals("Updated Name", controller.studentlist.get(0).getStudent_name(), "Student name should be updated");
        assertEquals("Updated Course", controller.studentlist.get(0).getCourse_name(), "Course name should be updated");
        assertEquals(90, controller.studentlist.get(0).getExam_mark(), "Mark should be updated");
        assertEquals(85, controller.studentlist.get(0).getCA_mark(), "CA should be updated");
        assertEquals(92, controller.studentlist.get(0).getAttendance_mark(), "Attendance should be updated");
    }

    @Test
    void delete() {
        // Assuming there's at least one student in the list for testing
        // Set up controller and populate the list
        StudentgradecalculatorController controller = new StudentgradecalculatorController();
        controller.studentlist.add(new student_grade(0, "mary", "civics", 15, 8, 9,12.4, "F"));

        // Mock action event
        ActionEvent event = Mockito.mock(ActionEvent.class);

        // Set the index of the student to be deleted
        controller.index = 0;

        // Get initial size of student list
        int initialSize = controller.studentlist.size();

        // Call the method to delete student
        controller.Delete(event);

        // Assert that student is deleted from the list
        assertEquals(initialSize - 1, controller.studentlist.size(), "Student should be deleted from the list");
    }
    }
