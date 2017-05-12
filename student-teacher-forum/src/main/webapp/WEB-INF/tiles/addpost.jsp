<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:url var="addPhoto" value="/addphoto" />

<div class="row">
	<div id="post-status"></div>
	<div class="col-md-8 col-md-offset-2">
		<h1>Create post</h1>

		<form:form modelAttribute="post">

			<div class="errors">
				<form:errors path="text" />
			</div>
			<div class="form-group">
				<label>Title </label>
				<form:input path="title" type="text" class="form-control"
					name="title" />
			</div>

			<div class="form-group">
				<label>Description<span class="require">*</span></label>
				<form:textarea path="text" rows="5" class="form-control" name="text"></form:textarea>
			</div>

			<div class="form-group">
				<button type="submit" class="btn btn-primary">Create</button>

			</div>
		</form:form>
	</div>

</div>


<script src="https://cloud.tinymce.com/stable/tinymce.min.js"></script>
<script src="${contextRoot}/js/tinymce.min.js"></script>
<script>
	tinymce.init({
		selector : 'textarea',
		plugins : "link"
	});

</script>
