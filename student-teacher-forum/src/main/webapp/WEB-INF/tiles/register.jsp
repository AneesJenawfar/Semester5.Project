<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="login-error">
	<form:errors path="user.*" />
</div>
<div class="col-md-10 col-md-offset-1">
	<div class='panel-primary '>
		<div class='panel-heading text-center'>
			<h3>Create New Account</h3>
		</div>
		<div class='panel-body'>
			<form:form class="form-horizontal" modelAttribute="user"
				method="POST">
				<div class='form-group'>
					<div class="row">
						<label class='control-label col-md-4'>First Name</label>
						<div class='col-md-4'>
							<div class='form-group'>
								<div class='col-md-12'>
									<form:input class='form-control' path="firstname" type='text'
										maxlength="20" placeholder="First Name" />
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<label class='control-label col-md-4'>Surname</label>
						<div class='col-md-4'>
							<div class='form-group'>
								<div class='col-md-12'>
									<form:input class='form-control' path="surname" type='text'
										maxlength="25" placeholder="Surname" />
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<label class='control-label col-md-4'>Email</label>
						<div class='col-md-4'>
							<div class='form-group'>
								<div class='col-md-12'>
									<form:input type="email" path="email" placeholder="E-mail"
										class="form-control" />
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<label class='control-label col-md-4'>Password</label>
						<div class='col-md-4'>
							<div class='form-group'>
								<div class='col-md-12'>
									<form:input type="password" path="plainPassword"
										placeholder="Password" class="form-control" maxlength="60" />
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<label class='control-label col-md-4'>Password (Confirm)</label>
						<div class='col-md-4'>
							<div class='form-group'>
								<div class='col-md-12'>
									<form:input class='form-control' path='repeatPassword'
										placeholder='Password Re-Enter' type='password' maxlength="60" />
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class='form-group'>
					<div class='col-md-offset-6 col-md-3'>
						<button class='btn-lg btn-primary'>Register</button>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>