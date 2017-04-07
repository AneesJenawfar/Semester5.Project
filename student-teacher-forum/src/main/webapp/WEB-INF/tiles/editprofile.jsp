<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="row">
	<div class="col-md-8 col-md-offset-2 text-center">
		<div class="errors">
			<form:errors path="about" />
		</div>
		<div class="box">
			<div class="box-content">
				<h1 class="tag-title">Edit Your Profile</h1>
				<form:form modelAttribute="profile">

					<div class="form-group">
						<form:textarea path="about" class="form-control" name="about"
							rows="10"></form:textarea>
					</div>
					<div class="form-group">
						<button class="btn btn-primary " name="submit" type="submit">Save</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>
<script src="https://cloud.tinymce.com/stable/tinymce.min.js"></script>
<script>
	tinymce.init({
		selector : 'textarea',
		plugins : "link"
	});
</script>
