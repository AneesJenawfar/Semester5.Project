<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:url var="profilePhoto" value="/profile-photo/${user.id}" />
<c:url var="editProfile" value="/edit-profile" />
<c:url var="saveInterest" value="/save-interest" />
<c:url var="deleteInterest" value="/delete-interest" />

<c:url var="friendStatus" value="/friend-status/${user.id}" />
<c:url var="sendRequest" value="/send-request" />
<c:url var="acceptRequest" value="/accept-request" />
<c:url var="disconnect" value="/unfriend" />

<div class="row">
	<div id="profile-status"></div>
	<div class="col-md-6 col-md-offset-3 profilebox">
		<div class="row">
			<div class="col-md-10 col-md-offset-2">
				<img class="img-rounded img-responsive profileimage"
					id="profileImage" src="${profilePhoto}" />
			</div>
			<div class="text-center">
				<c:if test="${owner== true}">
					<a href="#" id="uploadLink">Upload Photo</a>
				</c:if>
			</div>

			<br />
			<h4>${user.firstname}	${user.surname}</h4>

			<c:if test="${owner!= true}">
				<button onclick="requestController(this.id);" id="request" type="button"
					class="btn btn-secondary request"></button>
				<button onclick="rejectRequest(this.id);" style="display: none;" id="reject" type="button" class="btn btn-danger reject">Reject</button>
			</c:if>
			
			<c:if test="${profile.address!= null}">
				<small><i class="glyphicon glyphicon-map-marker"> </i>${profile.address}
				</small>
			</c:if>
			<p>
				<i class="glyphicon glyphicon-envelope"></i>${user.email}
				<c:if test="${profile.phone!= null}">
					<i class="glyphicon glyphicon-phone"></i>${profile.phone}<br />
				</c:if>
				<c:if test="${profile.school!= null}">
					<i class="glyphicon glyphicon-book"></i>${profile.school }
				</c:if>
				<i class="glyphicon glyphicon-calendar"></i>June 02, 1988<br />
			</p>
			<strong><i>Interests </i><br>
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
				</div></strong><br /> <strong><i>Personal Statement </i> </strong><br />
			<div class="profile-text">
				<c:choose>
					<c:when test="${profile.about==null}">A mischievous student wants to break
				into a computer file, which is password-protected. Assume that there
				are n passwords only one of which is correct, and that the student
				tries possible passwords in a random order.</c:when>
					<c:otherwise>
						${profile.about}
					</c:otherwise>
				</c:choose>
			</div>
			<c:if test="${owner== true}">
				<div class="btn-group pull-right">
					<button type="button" class="btn btn-primary editprofile"
						onclick="location.href='${editProfile}'">Edit</button>
				</div>
			</c:if>

			<c:url value="/upload-photo" var="uploadPhoto" />
			<form method="post" enctype="multipart/form-data"
				id="photoUploadForm" action="${uploadPhoto}">
				<input type="file" accept="image/*" name="file" id="fileInput" /> <input
					type="submit" value="upload" /> <input type="hidden"
					name="${_csrf.parameterName}" value="${_csrf.token}" />
			</form>
		</div>
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

	function getFriendStatus() {
		var result = $.ajax({
			type : "GET",
			url : "${friendStatus}",
			data : {
				"id" : "${user.id}"
			},
			success : function(data) {
				$("#request").text(data);
				if (data=="Accept Request"){
					$("#reject").toggle();
				}
			}
		});
	}

	function editRequest(id, actionUrl) {
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");

		$.ajaxPrefilter(function(options, originalOptions, jqXHR) {
			jqXHR.setRequestHeader(header, token);
		});
		$.ajax({
			'url' : actionUrl,
			data : {
				'id' : id
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

	function rejectRequest(id) {
		event.preventDefault();
		var el = document.getElementById(id);
		editRequest("${user.id}", "${disconnect}");
		$("#reject").toggle();
		el.value     = "Connect";
	    el.innerHTML = "Connect";
	}
	
	function requestController(id) {
		event.preventDefault();
		var status = $("#request").text();
		var el = document.getElementById(id);
		if (status == "Connect") {
			editRequest("${user.id}", "${sendRequest}");
			el.value     = "Request Sent";
		    el.innerHTML = "Request Sent";

		} else if (status == "Accept Request") {
			editRequest("${user.id}", "${acceptRequest}");
			$("#reject").toggle();
			el.value     = "Connected";
		    el.innerHTML = "Connected";

		} else if (status == "Request Sent") {
			editRequest("${user.id}", "${disconnect}");
			el.value     = "Connect";
		    el.innerHTML = "Connect";

		} else if (status == "Connected") {
			editRequest("${user.id}", "${disconnect}");
			el.value     = "Connect";
		    el.innerHTML = "Connect";
		}
	}

	$(document).ready(function() {
		getFriendStatus();

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
			tagLimit : 10,
			readOnly : '${owner}' == 'false'
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