<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update - phonebook</title>
</head>
<body>
<p>Enter the phone number: </p>
<form action = "updateserv">
<input type = "text" name = "number">
<button type = "submit">Find</button>
</form>
<%if(request.getAttribute("status") != null) {%>
<%if(request.getAttribute("status").equals("true")){ %>
<h4>------Update here-------</h4><br>
<form action = "createserv" method = "get">
Contact's name: <input type = "text" name = "contactname" value = <%= request.getAttribute("name") %> placeholder = <%= request.getAttribute("name") %>><br>
Contact's number: <input type = "text" name = "contactnumber" value = <%= request.getAttribute("number") %> placeholder =<%= request.getAttribute("number") %>><br>
Select Group: <select name = "contactgroup">
<%String[] grp = {"School","Colleague","Others"};
for(int i = 0 ; i < 3 ; i++){
%>
<%if(request.getAttribute("group").equals(grp[i])){ %>
<option selected = "selected" value= <%=grp[i] %>><%=grp[i] %></option>
<%} else { %>
<option value= <%=grp[i] %>><%=grp[i] %></option>
<%} %>
<%} %>
</select>
<br>
<button type = "submit">Update Contact</button>
</form>
<%} else {%>
<br><p><%= request.getAttribute("status").toString() %></p>
<%} %>
<%} %>
</body>
</html>