package com.hamza.auth.servlets;

import com.hamza.auth.dao.UserDAO;
import com.hamza.auth.models.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = userDAO.validateUser(email, password);
        
        if (user != null) {
            // Create a session and store user information
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect("dashboard.jsp"); // Redirect to dashboard
        } else {
            request.setAttribute("error", "Invalid email or password");
            request.getRequestDispatcher("views/login.jsp").forward(request, response);
        }
    }
}