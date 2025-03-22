package com.hamza.auth.servlets;

import com.hamza.auth.dao.UserDAO;
import com.hamza.auth.models.User;
import com.hamza.auth.utils.EmailSender;
import com.hamza.auth.utils.TokenGenerator;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.Date;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/register.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Date birthDate = new Date(request.getParameter("birthDate")); // Vous pouvez choisir un format de date adéquat

        // Générer un token de validation
        String validationToken = TokenGenerator.generateToken();

        // Créer un nouvel utilisateur
        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(password); // Vous devrez peut-être hacher le mot de passe ici
        user.setBirthDate(birthDate);
        user.setValidationToken(validationToken);

        if (userDAO.createUser(user)) {
            // Envoyer l'email de validation
            String validationLink = "http://localhost:8080/validate?token=" + validationToken;
            String subject = "Email Validation";
            String body = "Please validate your email by clicking on the following link: " + validationLink;

            EmailSender.sendEmail(email, subject, body);

            response.sendRedirect("checkEmail.jsp");  // Page pour informer l'utilisateur de vérifier son email
        } else {
            request.setAttribute("error", "An error occurred during registration.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/register.jsp");
            dispatcher.forward(request, response);
        }
    }
}