<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:url var="loginurl" value="/login" />
<c:url var="registerurl" value="/register" />
<div class="back">
	<div class="row">
		<div class="col-md-6 col-md-offset-3 register-prompt">

			Please log in or <a href="${registerurl}"> Click here to create
				an account </a>. It's free.
		</div>
	</div>

	<c:if test="${param.error != null}">
		<div class="login-error">Incorrect user name or password</div>
	</c:if>
	<div class="wrapper">
		<form action="${loginurl}" method="post" class="form-signin">
			<h3 class="form-signin-heading">Welcome Back! Please Sign In</h3>
			<hr class="colorgraph">

			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" /> <br> <input type="text"
				class="form-control sign" id="username" name="username"
				placeholder="E-mail" required /> <input type="password"
				class="form-control sign" id="password" name="password"
				placeholder="Password" required />
			<button class="btn btn-lg btn-primary btn-block">Login</button>
		</form>
	</div>
</div>

<style>
	body
{
    background: url('${contextRoot}/img/front.jpg') fixed;
    
}
</style>
