<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:out value="${message}"></c:out>
<!--
Failed URL: <c:out value="${url}"/>
Exception Message: <c:out value="${exception.message}"/>
<c:forEach var="line" items= "${exception.stackTrace }">
	<c:out value="${line}"/>
</c:forEach>
-->