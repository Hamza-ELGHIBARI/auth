package com.hamza.auth.servlets;

import com.hamza.auth.config.DatabaseConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/reset-password")
public class ResetPasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");
        String newPassword = request.getParameter("password");

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Check if token is valid
            PreparedStatement stmt = conn.prepareStatement("SELECT user_id FROM password_reset_tokens WHERE token = ? AND expiration > NOW()");
            stmt.setString(1, token);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                response.getWriter().println("Invalid or expired token.");
                return;
            }

            int userId = rs.getInt("user_id");

            // Update the password
            PreparedStatement updateStmt = conn.prepareStatement("UPDATE users SET password = ? WHERE id = ?");
            updateStmt.setString(1, newPassword);  // ⚠ Hash the password in real applications!
            updateStmt.setInt(2, userId);
            updateStmt.executeUpdate();

            // Delete the token after use
            PreparedStatement deleteStmt = conn.prepareStatement("DELETE FROM password_reset_tokens WHERE token = ?");
            deleteStmt.setString(1, token);
            deleteStmt.executeUpdate();

            response.getWriter().println("Password reset successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error occurred!");
        }
    }
}
