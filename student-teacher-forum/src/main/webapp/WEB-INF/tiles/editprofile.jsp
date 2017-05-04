<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class='text-center'>
	<h3>Edit Profile</h3>
</div>
<div class="login-error">
	<form:errors path="profile.*" />
</div>
<form:form class="form-horizontal" modelAttribute="profile">
	<div class='profile'>

		<div class="form-group">
			<label class="col-sm-3 control-label">Address</label>
			<div class="col-sm-9">
				<form:input type="text" name="address" path="address"
					placeholder="Address" class="form-control" />
				<span class="help-block"></span>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label">School</label>
			<div class="col-sm-9">
				<form:input type="text" name="school" path="school"
					placeholder="School Name" class="form-control" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label">Phone Number</label>
			<div class="col-sm-9">
				<form:input type="text" name="phone" path='phone'
					placeholder='Phone' class="form-control" maxlength="12" />
			</div>
		</div>
		<%-- <div class="form-group">
			<label class="col-sm-3 control-label">Date of Birth</label>
			<div class="col-sm-9">
				<form:input type="date" name="dob" path='dob' class="form-control" onchange="alert('fgsdfaszdz');" />
				<form:input type="date" class="datepicker" name="dob" path="dob" style="text-align: center"/>
			</div>
		</div> --%>
		<div class="form-group">
			<label class="col-sm-3 control-label">Personal Statement</label>
			<div class="col-sm-9">
				<form:textarea path="about" class="form-control" name="about"
					rows="5"></form:textarea>
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-3 col-sm-offset-6">
				<button type="submit" class="btn btn-primary btn-block">Register</button>
			</div>
		</div>
	</div>
</form:form>


<script src="https://cloud.tinymce.com/stable/tinymce.min.js"></script>
<script>
	tinymce.init({
		selector : 'textarea',
		plugins : "link"
	});
</script>
