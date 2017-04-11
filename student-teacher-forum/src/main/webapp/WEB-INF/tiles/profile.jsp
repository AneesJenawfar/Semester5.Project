<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:url var="profilePhoto" value="/profile-photo" />
<c:url var="editProfile" value="/edit-profile" />

<div class="row">
	<div class="col-md-10 col-md-offset-1">
		<div class="profile-about">
			<div class="profile-image">
				<img src="${profilePhoto}">
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
			<a href="${editProfile}">edit</a>
		</div>
		
		<p> &nbsp;</p>
		<c:url value="/upload-photo" var="uploadPhoto"/>
		<form method="post" enctype="multipart/form-data" action="${uploadPhoto}">
			Select Photo: <input type="file" accept="image/*" name= "file"/>
			<input type="submit" value="upload"/>
			<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}"/>
		</form>
	</div>
</div>