<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="col-md-6 col-md-offset-3">
	<div class="login-error">
		<form:errors path="user.*" />
	</div>
	<form:form class="form-horizontal" modelAttribute="user" method="POST">
		<fieldset>
			<div id="legend">
				<legend>Create a new account</legend>
			</div>

			<div class="control-group">
				<label class="control-label" for="firstname">First Name</label>
				<div class="controls">
					<form:input type="text" path="firstname" placeholder="First Name"
						class="input-xlarge" />
				</div>

				<label class="control-label" for="surname">Surname</label>
				<div class="controls">
					<form:input type="text" path="surname" placeholder="SurName"
						class="input-xlarge" />
				</div>

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
					<form:input type="password" path="plainPassword" placeholder=""
						class="input-xlarge" />
				</div>
			</div>
			<div class="control-group">
				<!-- Password -->
				<label class="control-label" for="repeatPassword">Password
					(Confirm)</label>
				<div class="controls">
					<form:input type="password" path="repeatPassword" placeholder=""
						class="input-xlarge" />
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

<%-- <div class='container'>
	<div class="errors">
		<form:errors path="email" />
		<form:errors path="password" />
	</div>

	<div class='panel panel-primary dialog-panel'>
		<div class='panel-heading text-center'>
			<h3>Create New Account</h3>
		</div>
		<div class='panel-body'>
			<form:form class="form-horizontal" modelAttribute="user"
				method="POST">
				<div class='form-group'>
					<div class="row">
						<label class='control-label col-md-2' for='fname'>First
							Name</label>
						<div class='col-md-4'>
							<div class='form-group'>
								<div class='col-md-10'>
									<input class='form-control' id='fname' name="fname"
										placeholder='' type='text' maxlength="100">
								</div>
							</div>
						</div>
						<label class='control-label col-md-2' for='lname'>Last
							Name</label>
						<div class='col-md-4'>
							<div class='form-group'>
								<div class='col-md-10'>
									<input class='form-control' id='lname' name="lname"
										placeholder='' type='text' maxlength="100">
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<label class='control-label col-md-2' for='email'>Email</label>
						<div class='col-md-4'>
							<div class='form-group'>
								<div class='col-md-10'>
									<form:input type="email" path="email" placeholder=""
										class="form-control" />
								</div>
							</div>
						</div>
						<label class='control-label col-md-2' for='phone'>Phone
							Number</label>
						<div class='col-md-4'>
							<div class='form-group internal'>
								<div class='col-md-10'>
									<input type="text" id='phone' name="phone" placeholder=''
										class="form-control bfh-phone" minlength="10"
										data-format="dddddddddd">
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<label class='control-label col-md-2' for='school'>School</label>
						<div class='col-md-4'>
							<div class='form-group'>
								<div class='col-md-10'>
									<input class='form-control' placeholder='' type='text'
										maxlength="100">
								</div>
							</div>
						</div>
						<label class='control-label col-md-2' for='dob'>Date of
							Birth</label>
						<div class='col-md-4'>
							<div class='form-group'>
								<div class='col-md-10'>
									<input class='form-control' placeholder='' type='text'
										maxlength="100">
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<label class='control-label col-md-2' for='password'>Password</label>
						<div class='col-md-4'>
							<div class='form-group'>
								<div class='col-md-10'>
									<form:input type="password" path="password" placeholder=""
										class="form-control" maxlength="60" />
								</div>
							</div>
						</div>
						<label class='control-label col-md-2' for='name'>Password
							(Confirm)</label>
						<div class='col-md-4'>
							<div class='form-group'>
								<div class='col-md-10'>
									<input class='form-control' placeholder='' type='password'
										maxlength="60">
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class='form-group'>
					<div class='col-md-offset-4 col-md-3'>
						<button class='btn-lg btn-primary'>Register</button>
					</div>
				</div>
			</form:form>
		</div>
	</div>

</div> --%>