<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Read contacts</title>
</head>
<body>
<br>
<a href = "/index.html">Go home</a>
<h2>View All Contacts</h2>
<div>
<%
request.getRequestDispatcher("/readallserv?cursor=&action=next").include(request, response);
%>
</div>
</body>
</html>