<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<h3>Create - Book</h3>
<form action = "createserv" method = "get">
Contact's name: <input type = "text" name = "contactname" placeholder ="Contact's name"><br>
Contact's number: <input type = "text" name = "contactnumber" placeholder ="Contact's Number"><br>
Select Group: <select name = "contactgroup">
<option value="School">School</option>
<option value="Colleague">Colleagues</option>
<option value="Others">Others</option>
</select>
<br>
<button type = "submit">Add Contact</button>
</form>

</body>
</html>