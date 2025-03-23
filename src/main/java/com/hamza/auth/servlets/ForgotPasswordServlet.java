package com.hamza.auth.servlets;


import com.hamza.auth.config.DatabaseConnection;
import com.hamza.auth.utils.EmailSender;

import java.io.IOException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/forgot-password")
public class ForgotPasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Check if user exists
            PreparedStatement stmt = conn.prepareStatement("SELECT id FROM users WHERE email = ?");
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                response.getWriter().println("User not found");
                return;
            }

            int userId = rs.getInt("id");
            // Generate reset token
            String token = generateToken();
            Timestamp expiration = new Timestamp(System.currentTimeMillis() + 15 * 60 * 1000); // 15 min validity

            // Store token in the database
            PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO password_reset_tokens (user_id, token, expiration) VALUES (?, ?, ?)");
            insertStmt.setInt(1, userId);
            insertStmt.setString(2, token);
            insertStmt.setTimestamp(3, expiration);
            insertStmt.executeUpdate();

            // Send email with reset link
            String resetLink = "http://localhost:8082/auth/reset-password?token=" + token;
            EmailSender.sendEmail(email, "Password Reset", "Click the link to reset your password: " + resetLink);

            response.getWriter().println("Reset link sent!");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error occurred!");
        }
    }

    private String generateToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[24];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
