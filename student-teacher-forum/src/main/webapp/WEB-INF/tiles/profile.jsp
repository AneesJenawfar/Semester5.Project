<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:url var="profilePhoto" value="/profile-photo/${userId}" />
<c:url var="editProfile" value="/edit-profile" />
<c:url var="saveInterest" value="/save-interest" />
<c:url var="deleteInterest" value="/delete-interest" />
<div class="row">
	<div class="col-md-10 col-md-offset-1">
		<div id="profile-status"></div>

		<div id="interest">
			<ul id="interestList">
				<c:choose>
					<c:when test="${empty profile.interests} }">
						<li>Add Your interest here</li>
					</c:when>
					<c:otherwise>
						<c:forEach var="interest" items="${profile.interests}">
							<li>${interest.name}</li>
						</c:forEach>

					</c:otherwise>
				</c:choose>
			</ul>


		</div>

		<div class="profile-about">
			<div class="profile-image">
				<div>
					<img id="profileImage" src="${profilePhoto}">
				</div>
				<div class="text-center">
					<c:if test="${owner== true}">
						<a href="#" id="uploadLink">Upload Photo</a>
					</c:if>
				</div>
			</div>
			<div class="profile-text">
				<c:choose>
					<c:when test="${profile.about==null}">A mischievous student wants to break
				into a computer file, which is password-protected. Assume that there
				are n passwords only one of which is correct, and that the student
				tries possible passwords in a random order. Let N be the number of
				trials required to break into the file. Determine the pmf ofmaster
				file consists of 150,000 records. When a transaction file is run
				against the master file, approximately 12,000 records are
				updated.</c:when>
					<c:otherwise>
						${profile.about}
					</c:otherwise>
				</c:choose>

			</div>
		</div>
		<div class="profile-edit">
			<c:if test="${owner== true}">
				<a href="${editProfile}">edit</a>
			</c:if>

		</div>

		<c:url value="/upload-photo" var="uploadPhoto" />
		<form method="post" enctype="multipart/form-data" id="photoUploadForm"
			action="${uploadPhoto}">
			<input type="file" accept="image/*" name="file" id="fileInput" /> <input
				type="submit" value="upload" /> <input type="hidden"
				name="${_csrf.parameterName}" value="${_csrf.token}" />
		</form>
	</div>
</div>

<script>
	function setStatusText(text) {
		$("#profile-status").text(text);
		window.setTimeout(function() {
			$("#profile-status").text("");
		}, 2000);
	}
	function uploadSuccess(data) {
		$("#profileImage").attr("src", "${profilePhoto};time=" + new Date());
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

	function saveInterest(text) {
		editInterest(text, "${saveInterest}");
	}

	function deleteInterest(text) {
		editInterest(text, "${deleteInterest}");
	}

	function editInterest(text, actionUrl) {
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");

		$.ajaxPrefilter(function(options, originalOptions, jqXHR) {
			jqXHR.setRequestHeader(header, token);
		});

		$.ajax({
			'url' : actionUrl,
			data : {
				'name' : text
			},
			type : 'POST',
			success : function() {
				//alert("ok");
			},
			error : function() {
				//alert("error");
			}
		});
	}

	$(document).ready(function() {

		$("#interestList").tagit({

			afterTagRemoved : function(event, ui) {
				deleteInterest(ui.tagLabel);
			},
			afterTagAdded : function(event, ui) {
				if (ui.duringInitialization != true)
					saveInterest(ui.tagLabel);
			},
			caseSensitive : false,
			allowSpaces : true,
			tagLimit: 10,
			readOnly: '${owner}' == 'false'
		});

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