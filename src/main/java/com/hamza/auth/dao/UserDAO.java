package com.hamza.auth.dao;


import  com.hamza.auth.config.DatabaseConnection;
import  com.hamza.auth.models.User;

import java.sql.*;

public class UserDAO {

    public boolean createUser(User user) {
        String query = "INSERT INTO users (full_name, email, birth_date, password, validation_token, validated) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, user.getFullName());
            stmt.setString(2, user.getEmail());
            stmt.setDate(3, new java.sql.Date(user.getBirthDate().getTime()));
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getValidationToken());
            stmt.setBoolean(6, false);  // Utilisateur non validé initialement

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User getUserByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setFullName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setBirthDate(rs.getDate("birth_date"));
                user.setPassword(rs.getString("password"));
                user.setValidationToken(rs.getString("validation_token"));
                user.setValidated(rs.getBoolean("validated"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean validateUser(String email) {
        String query = "UPDATE users SET validated = true WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public User validateUser(String email, String password) {
        User user = null;
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User(
                    resultSet.getInt("id"),
                    resultSet.getString("full_name"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    resultSet.getDate("birth_date")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}
