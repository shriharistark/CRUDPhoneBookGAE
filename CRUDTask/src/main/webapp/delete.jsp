<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<a href = "/index.html">Go Home</a><br><br>
<h3>Delete - Contacts</h3><br>
<form action="deleteserv">
<input type = "text" name = "number" placeholder = "Enter a number to delete"><br>
<button type = "submit">Delete</button>
</form>
<%if(request.getAttribute("status") != null){ 
if((boolean)request.getAttribute("status") == (true)){%>
<br><p><%= request.getAttribute("removed").toString() %> is removed!</p>
<%} else {%>
<br><p><%= request.getAttribute("removed").toString() %></p>
<%} %>
<%} %>
</body>
</html>