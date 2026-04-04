package com.beza.quizsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    static final String USER = "root";
    static final String PASS = "quiz";
    static final String URL = "jdbc:mysql://localhost:3306/quiz_hub";

    public static Connection getConnection() throws SQLException {

        try{
           Class.forName("com.mysql.cj.jdbc.Driver");

           return DriverManager.getConnection(URL,USER,PASS);

        }catch(ClassNotFoundException e){
            throw new SQLException("MYSQL Driver not found, check your pom.xml",e);
        }

    }
}
