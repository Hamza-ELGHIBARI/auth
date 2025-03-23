<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.hamza.auth.models.User" %>
<%@ page session="true" %>

<html>
<head>
    <title>Dashboard</title>
</head>
<body>
    <%
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
        }
    %>
    
    <h2>Welcome, <%= user.getFullName() %>!</h2>
    <p>Your email: <%= user.getEmail() %></p>
    <a href="logout">Logout</a>
</body>
</html>
