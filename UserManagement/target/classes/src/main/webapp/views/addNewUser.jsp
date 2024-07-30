<html>
<head>add new user</head>
<body>
<form method="post" action="<%= request.getContextPath() %>/insert">
name:
<input type="text" name="name">
email:
<input type="text" name="email">
country:
<input type="text" name="country">
<input type ="submit" value="save">


</form>
</body>
</html>