package com.hamza.auth.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.hamza.auth.dao.UserDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

@WebServlet("/validate")
public class ValidateEmailServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");

        if (userDAO.validateUser(token)) {
            response.sendRedirect("views/login.jsp");  // Rediriger vers la page de connexion
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid or expired token.");
        }
    }
}

