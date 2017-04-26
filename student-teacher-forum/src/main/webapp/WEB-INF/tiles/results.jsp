<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="pgn"%>


<div class="row">
	<div class="col-md-12 results-noresult">
		<c:if test="${empty page.content}"> 
    No Results 
</c:if>
	</div>
</div>

<c:set var="searchUrl" value="/search?s=${s}" />
<div class="row">
	<div class="col-md-12">
		<pgn:pagination url="${searchUrl}" page="${page}" size="10" />
	</div>
</div>
<c:forEach var="result" items="${page.content}">
	<c:url var="profilePhoto" value="/profile-photo/${result.userId}" />
	<c:url var="profileLink" value="/profile/${result.userId}" />

	<div class="row person-row">
		<div class="col-md-12">
			<div class="result-photo">
				<a href="${profileLink}"><img id="profileImage"
					src="${profilePhoto}"></a>
			</div>
			<div class="result-details">
				<div class="result-name">
					<a href="${profileLink}"><c:out value="${result.firstname}" />
						<c:out value="${result.surname}" /></a>
				</div>

				<div class="result-interest">
					<c:forEach var="interest" items="${result.interests}"
						varStatus="status">
						<c:url var="interestLink" value="/search?s=${interest.name}" />

						<a href="${interestLink}"><c:out value="${interest.name}" /></a>

						<c:if test="${!status.last}">|</c:if>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>

</c:forEach>