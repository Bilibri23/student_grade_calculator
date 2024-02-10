package com.studentgradecalculator.studentgradecalculator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class mysqlconnect {
    public static Connection ConnectionDb() {// static method used so that it can be accessed without creating any instance of the class
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");// loads driver
            System.out.println("Driver is loaded");

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentgradecalculator", "root", "");
            System.out.println("connection is established");
        } catch (ClassNotFoundException e) {
            System.out.println("driver class not found " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Sql exception " + e.getMessage());

        }
        return con;
    }
}
