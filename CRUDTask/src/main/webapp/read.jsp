<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 
<%@ page import = "java.util.List" %>
<%@ page import = "com.google.appengine.api.datastore.Entity" %>
<%@ page import = "java.util.Date" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<a href = "/index.html">Home</a><br>
<h3>Read - Contacts</h3>
<form action="readserv" method = "get">
<select name = "criteria">
<option value = "Name">Contact's name</option>
<option value ="Number">Contact's Number</option> 
<option value ="recent">By Time added</option>
</select>
<button id="Add" type = "button">Specify params</button>
    <div id="textboxDiv"></div><br>
    <script>  
        $(document).ready(function() {
            $("#Add").on("click", function() {  
                $("#textboxDiv").append("<div><br><input type='text' name='params' placeholder = 'Filter parameter'/><br></div>");  
            });  
        });
    </script>
<br><br>
<button type = "submit" id = "noni">Filter</button>
</form>
<%if(request.getAttribute("status") != null){ %>
<br>
<%
List<Entity> res = (List<Entity>)request.getAttribute("result");
System.out.println("jsp: "+request.getParameter("params"));
%>
<table>
<tr><th>Name</th><th>Number</th><th>Time added</th></tr>
<%for(Entity e : res) { 
Date date = (Date)e.getProperty("TimeAdded");%>
<tr><td><%= e.getProperty("Name") %></td><td><%= e.getProperty("Number") %></td><td><%= date %></td></tr>
<%} %>
</table>
<%} %>
</body>
</html>