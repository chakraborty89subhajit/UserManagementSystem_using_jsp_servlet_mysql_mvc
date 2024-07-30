<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.demo.user.model.User" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit User Information</title>
</head>
<body>
    <h2>Edit User Information</h2>
    <%
        User user = (User) request.getAttribute("user");
        if (user != null) {
    %>
    <form method="post" action="<%= request.getContextPath() %>/update">
        <input type="hidden" name="id" value="<%= user.getId() %>">
        Name:
        <input type="text" name="name" value="<%= user.getName() %>"><br>
        Email:
        <input type="text" name="email" value="<%= user.getEmail() %>"><br>
        Country:
        <input type="text" name="country" value="<%= user.getCountry() %>"><br>
        <input type="submit" value="Update">
    </form>
    <%
        } else {
    %>
        <p>User not found.</p>
    <%
        }
    %>
</body>
</html>
