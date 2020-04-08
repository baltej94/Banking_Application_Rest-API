<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="com.example.bankingApplication.Model.*,com.example.bankingApplication.domain.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add new Customer</title>
</head>
<body>
<form action="/createCustomer" method="post" >
<label for="customerNumber">Customer Number:</label>
<input type="number" name="customerNumber" title="Customer Number"><br>
<label for="firstName">First Name:</label>

<input type="text" name="firstName" title="First Name"><br>

<label for="lastName">Last Name:</label>

<input type="text" name="lastName" title="Last Name"><br>

<label for="email">Email ID:</label>

<input type="text" name="contactDetails.emailId" title="Email ID"><br>

<label for="date">Date of Birth:</label>

<input type="date" name="dob" title="Date of Birth"><br>

<label for="homePhone">Home Phone Number:</label>

<input type="text" name="contactDetails.homePhone" title="Phone Number"><br>

<label for="workPhone">Work Phone Number:</label>

<input type="text" name="contactDetails.workPhone" title="Phone Number"><br>


<label for="status">Status:</label>

<input type="text" name="status" title="Status"><br>


<input type="submit"><br>
</form>


</body>
</html>