<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:url var="loginurl" value="/login" />
<c:url var="registerurl" value="/register" />
<div class="row">
	<div class="col-md-6 col-md-offset-3 register-prompt">
	
	Please log in or <a href="${registerurl}"> Click here to create an account </a>. It's free.
	</div>
</div>
<div class="col-md-6 col-md-offset-3">
	<div class="container">
		<div class="row">
			<c:if test="${param.error != null}">
				<div class="login-error">Incorrect user name or password</div>
			</c:if>
			<div class="span12">
				<form class="form-horizontal" action="${loginurl}" method="POST">
					<fieldset>
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
						<div id="legend">
							<legend>Login</legend>
						</div>
						<div class="control-group">
							<label class="control-label" for="username">Username</label>
							<div class="controls">
								<input type="text" id="username" name="username" placeholder=""
									class="input-xlarge">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="password">Password</label>
							<div class="controls">
								<input type="password" id="password" name="password"
									placeholder="" class="input-xlarge">
							</div>
						</div>
						<div class="control-group">
							<div class="controls">
								<button class="btn btn-success">Login</button>
							</div>
						</div>
					</fieldset>
				</form>
			</div>
		</div>
	</div>
</div>