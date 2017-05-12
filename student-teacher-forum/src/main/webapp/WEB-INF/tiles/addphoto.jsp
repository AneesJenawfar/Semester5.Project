<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:url var="postPhoto" value="/post-photo/${post.id}" />
<c:url var="viewposts" value="/viewpost" />

<div class="row">
	<div id="post-status"></div>
	<div class="col-md-6 col-md-offset-3">

		<h3>${post.title }</h3>

		<p class="lead">by ${post.user.firstname} ${post.user.surname}</p>
		<p>
			<small> <span class="glyphicon glyphicon-time"></span> Posted
				on <fmt:formatDate pattern="EEEE d MMMM y" value="${post.updated}" />
				at <fmt:formatDate pattern="'at' HH:mm:s" value="${post.updated}" /></small>
		</p>
		<p>${post.text }</p>

		<div class='postimage'>

			<div class="col-md-10 col-md-offset-2">
				<img class="img-rounded img-responsive " id="postImage"
					style="display: none;" src="${postPhoto}" />
			</div>
			<div class="text-center">
				<a href="#" id="uploadLink">Link a Photo</a>
			</div>
		</div>
		<button type="button" class="btn btn-success pull-left"
			onclick="location.href='${viewposts}'">Post</button>
	</div>
	<c:url value="/upload-post-photo/${post.id}" var="uploadPhoto" />
	<form method="post" enctype="multipart/form-data" id="photoUploadForm"
		action="${uploadPhoto}">
		<input type="file" accept="image/*" name="file" id="fileInput" /> <input
			type="submit" value="upload" /> <input type="hidden"
			name="${_csrf.parameterName}" value="${_csrf.token}" />
	</form>
</div>



<script>
	function setStatusText(text) {
		$("#post-status").text(text);
		window.setTimeout(function() {
			$("#post-status").text("");
		}, 2000);
	}
	function uploadSuccess(data) {
		var $yourUl = $("#postImage");
		$yourUl.css("display", $yourUl.css("display") === 'none' ? '' : 'none');
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