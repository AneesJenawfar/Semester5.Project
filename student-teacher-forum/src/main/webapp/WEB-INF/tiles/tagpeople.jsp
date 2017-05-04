<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:url var="postPhoto" value="/post-photo/${postId}" />


<div class='col-md-6'>
	<div class="col-md-10 col-md-offset-2">
		<img class="img-rounded img-responsive postimage" id="postImage"
			src="${postPhoto}" />
	</div>

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