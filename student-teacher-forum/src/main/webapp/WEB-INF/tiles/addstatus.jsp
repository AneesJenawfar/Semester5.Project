<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row">
	<div class="col-md-8 col-md-offset-2 text-center">
		<div class="box">
			<div class="box-content">
				<h1 class="tag-title">Add New Status</h1>
				
				<form:form modelAttribute="statusUpdate">
					<div class="errors">
						<form:errors path="text"/>
					</div>
					<div class="form-group">
						<form:textarea path="text" class="form-control" name="text"
							rows="10"></form:textarea>
					</div>
					<div class="form-group">
						<button class="btn btn-primary " name="submit" type="submit">Update</button>
					</div>
				</form:form>
			</div>
		</div>
		<div class="box">
			<div class="box-content">
				<h3 class="tag-title">Last Status on <fmt:formatDate pattern = "EEEE d MMMM y 'at' HH:mm:s" value="${latest.updated}"/></h3>
				<c:out value="${latest.text }"/>
				
				
			</div>
		</div>
	</div>
</div>

