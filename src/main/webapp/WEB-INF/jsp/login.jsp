<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<body>
<h2>Patient Login Form</h2>
<form:form action = "/addPatient" method ="post" modelAttribute = "patient">
    <form:hidden path="patientId" />

    <form:input path = "patientName"/>
    <form:button id="add-person">Add Person</form:button>
</form:form>
</body>
</html>
