package com.beza.quizsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
public boolean registerUser(User user){
    String sql = "INSERT INTO users(email, username,password_hash,role) VALUES( ?, ?, ?, ?)";

    try(Connection conn = DatabaseConfig.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){
        ps.setString(1, user.getEmail());
        ps.setString(2, user.getUsername());
        ps.setString(3, user.getPassword());
        ps.setString(4, user.getRole());
        return ps.executeUpdate()>0;
    }catch(SQLException e){
        e.printStackTrace();
        return false;
    }
}

public User logInUser(String username, String password){
    String sql = "SELECT * FROM users WHERE username = ? AND password_hash = ?";

    try(Connection conn = DatabaseConfig.getConnection();
    PreparedStatement ps = conn.prepareStatement(sql)){
        ps.setString(1,username);
        ps.setString(2,password);

        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            User user = new User();
            user.setUsername(rs.getString("username"));
            user.setRole(rs.getString("role"));
            return user;
        }
    }catch(SQLException e){
        e.printStackTrace();
    }
    return null;
}
}
