<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.demo.user.model.User" %>



<html>
<head>all users list</head>
<body>
<a href="<%=request.getContextPath()%>/new">add new user</a>

<h2>list of all users</h2>
<table border="1">
<thead>
<tr>
<td>id</td>
<td>name</td>
<td>email</td>
<td>country</td>
<td>edit</td>
<td>delete</td>
</tr>
</thead>
 <%
                    List<User> listUser = (List<User>) request.getAttribute("listUser");
                    if (listUser != null) {
                        for (User user : listUser) {
                %>
                <tr>
                    <td><%= user.getId() %></td>
                    <td><%= user.getName() %></td>
                    <td><%= user.getEmail() %></td>
                    <td><%= user.getCountry() %></td>
                    <td>
                        <a href="<%= request.getContextPath() %>/edit?id=<%= user.getId() %>" class="btn btn-primary btn-sm">Edit</a></td>
                       <td> <a href="<%= request.getContextPath() %>/delete?id=<%= user.getId() %>" class="btn btn-danger btn-sm">Delete</a>
                    </td>
                </tr>
                <%
                        }
                    }
                %>
            </tbody>
<tbody>
<tr>
</tr>
</tbody>
</table>
</body>
</html>