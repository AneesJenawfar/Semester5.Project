<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:url var="postPhoto" value="/post-photo/${realpost.id}" />

<%-- <div class="row">
	<div class="col-md-8 col-md-offset-2 text-center">
		<div class="box">
			<div class="box-content">
				<h1 class="tag-title">Edit Post</h1>
				<form:form modelAttribute="post">
					<form:input type="hidden" path="id" />
					<form:input type="hidden" path="updated" />
					<div class="errors">
						<form:errors path="text" />
						<form:errors path="id" />
						<form:errors path="updated" />
					</div>
					<div class="form-group">
						<form:textarea path="text" class="form-control" name="text"
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

 --%>

<div class="row">
	<div id="post-status"></div>
	<div class="col-md-6">

		<h1>Edit post</h1>

		<form:form modelAttribute="post">
			<div class="errors">
				<form:errors path="text" />
				<form:errors path="id" />
				<form:errors path="updated" />
				<form:errors path="title" />
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
	<div class='col-md-6 postimage'>
		<c:if test="${realpost.photoName != null}">
			<div class="col-md-10 col-md-offset-2">
				<img class="img-rounded img-responsive " id="postImage"
					src="${postPhoto}" />
			</div>
		</c:if>
		<div class="text-center">
			<a href="#" id="uploadLink">Link a Photo</a>
		</div>
	</div>


	<c:url value="/upload-post-photo/${postId}" var="uploadPhoto" />
	<form method="post" enctype="multipart/form-data" id="photoUploadForm"
		action="${uploadPhoto}">
		<input type="file" accept="image/*" name="file" id="fileInput" /> <input
			type="submit" value="upload" /> <input type="hidden"
			name="${_csrf.parameterName}" value="${_csrf.token}" />
	</form>
</div>

<script src="https://cloud.tinymce.com/stable/tinymce.min.js"></script>
<script>
	tinymce.init({
		selector : 'textarea',
		plugins : "link"
	});
</script>

<script>
	function setStatusText(text) {
		$("#post-status").text(text);
		window.setTimeout(function() {
			$("#post-status").text("");
		}, 2000);
	}
	function uploadSuccess(data) {
		$("#postImage").attr("src", "${postPhoto};time=" + new Date());
		$("#fileInput").val("");
		setStatusText(data.message)
	}

	function uploadPhoto(event) {
		$.ajax({
			url : $(this).attr("action"),
			type : 'POST',
			data : new FormData(this),
			processData : false,
			contentType : false,
			success : uploadSuccess,
			error : function() {
				setStatusText("Server Error.");
			}
		});
		event.preventDefault();
	}

	$(document).ready(function() {

		$("#uploadLink").click(function(event) {
			event.preventDefault();
			$("#fileInput").trigger('click');
		});

		$("#fileInput").change(function() {
			$("#photoUploadForm").submit();
		});

		$("#photoUploadForm").on("submit", uploadPhoto);
	});
</script>
