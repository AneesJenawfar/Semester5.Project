<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="col-md-6 col-md-offset-3">
	<div class="errors">
		<form:errors path="email" />
		<form:errors path="password" />
	</div>
	<form:form class="form-horizontal" modelAttribute="user" method="POST">
		<fieldset>
			<div id="legend">
				<legend class="">Create a new account</legend>
			</div>
			<div class="control-group">
				<!-- E-mail -->
				<label class="control-label" for="email">E-mail</label>
				<div class="controls">
					<form:input type="text" path="email" placeholder="Email"
						class="input-xlarge" />
				</div>
			</div>
			<div class="control-group">
				<!-- Password-->
				<label class="control-label" for="password">Password</label>
				<div class="controls">
					<form:input type="password" path="password" placeholder=""
						class="input-xlarge" />
				</div>
			</div>
			<div class="control-group">
				<!-- Password -->
				<label class="control-label" for="password_confirm">Password
					(Confirm)</label>
				<div class="controls">
					<input type="password" name="password_confirm" placeholder=""
						class="input-xlarge">
				</div>
			</div>
			<div class="control-group">
				<!-- Button -->
				<div class="controls">
					<button class="btn btn-success">Register</button>
				</div>
			</div>
		</fieldset>
	</form:form>
</div>