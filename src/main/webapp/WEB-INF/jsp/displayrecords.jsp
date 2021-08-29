<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html >
    <body>
        <table>
            <tr><th>Id</th><th>Name</th></tr>
            <c:forEach items="${patientList}" var="patient">
               <tr>
               <td>${patient.patientId}</td>
               <td>${patient.patientName}</td>
               </tr>
            </c:forEach>
        </table>