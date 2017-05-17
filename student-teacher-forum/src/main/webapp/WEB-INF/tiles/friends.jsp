<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="pgn"%>
<c:set var="url" value="/all" />

<div class="row">
	<div class="col-md-12 results-noresult">
		<c:if test="${empty page.content}"> 
    		No Users
		</c:if>
	</div>
	<div class="row">
		<div class="col-md-12">
			<pgn:pagination url="${url}" page="${page}" size="10" />
		</div>
	</div>

	<c:forEach var="result" items="${page.content}">
		<c:url var="profilePhoto" value="/profile-photo/${result.user.id}" />
		<c:url var="profileLink" value="/profile/${result.user.id}" />

		<div class="list-group">
			<div class="list-group-item clearfix">
				<div class="profile-teaser-left">
					<div class="profile-img">
						<a href="${profileLink}"><img src="${profilePhoto}" /></a>
					</div>
				</div>
				<div class="profile-teaser-main">
					<h2 class="profile-name">
						<a href="${profileLink}"><c:out value="${result.user.firstname}" />
							<c:out value="${result.user.surname}" /></a>
					</h2>
					<div class="profile-info">
						<div class="info">
							Interests:
							<c:forEach var="interest" items="${result.interests}"
								varStatus="status">
								<c:url var="interestLink" value="/search?s=${interest.name}" />

								<a href="${interestLink}"><c:out value="${interest.name}" /></a>

								<c:if test="${!status.last}">|</c:if>
							</c:forEach>
						</div>
						<br>
						<div class="info">
							Personal Statement: <span>
								<ul>${result.about}
								</ul>
							</span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</c:forEach>
</div>
