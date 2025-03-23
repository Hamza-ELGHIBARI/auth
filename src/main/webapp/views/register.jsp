<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<c:if test="${not empty erreur}">
    <div class="bg-red-500 text-white p-2 rounded-md mb-4">
        ${erreur}
    </div>
</c:if>
<form action="${pageContext.request.contextPath}/register" method="post">
    <input type="text" name="fullName" placeholder="Full Name" required>
    <input type="email" name="email" placeholder="Email" required>
    <input type="date" name="birthDate" required>
    <input type="password" name="password" placeholder="Password" required>
    <button type="submit">Register</button>
</form>
</body>
</html>