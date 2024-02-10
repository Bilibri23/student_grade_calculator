module com.studentgradecalculator.studentgradecalculator {
    requires javafx.controls;
    requires javafx.fxml;
    requires  javafx.base;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mysql.connector.java;


    opens com.studentgradecalculator.studentgradecalculator to javafx.fxml;
    exports com.studentgradecalculator.studentgradecalculator;
}