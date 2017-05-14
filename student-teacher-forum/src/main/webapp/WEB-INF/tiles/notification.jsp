<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="pgn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="like" value="like" />
<c:set var="share" value="share" />
<c:set var="shared" value="shared" />
<c:set var="comment" value="comment" />

<ul class="sidebarservices">
	<li class="head">Notifications</li>
	<c:forEach var="noti" items="${notifications}">
		<c:url var="postpage" value="/comment?id=${noti.post.id}" />
		<c:if test="${noti.action==like}">
			<li><a href="${postpage}">${noti.attacker.firstname}
					${noti.attacker.surname} has liked your post
					&quot;${noti.post.title}&quot; <small>on <fmt:formatDate
							pattern="d MMMM y" value="${noti.time}" /> at <fmt:formatDate
							pattern="'at' HH:mm:s" value="${noti.time}" /></small>
			</a></li>
		</c:if>
		<c:if test="${noti.action==share}">
			<li><a href="${postpage}">${noti.attacker.firstname}
					${noti.attacker.surname} has shared your post
					&quot;${noti.post.title}&quot; <small>on <fmt:formatDate
							pattern="d MMMM y" value="${noti.time}" /> at <fmt:formatDate
							pattern="'at' HH:mm:s" value="${noti.time}" /></small>
			</a></li>
		</c:if>
		<c:if test="${noti.action==shared}">
			<li><a href="${postpage}">${noti.attacker.firstname}
					${noti.attacker.surname} has shared a post
					&quot;${noti.post.title}&quot; with you <small>on <fmt:formatDate
							pattern="d MMMM y" value="${noti.time}" /> at <fmt:formatDate
							pattern="'at' HH:mm:s" value="${noti.time}" /></small>
			</a></li>
		</c:if>
		<c:if test="${noti.action==comment}">
			<li><a href="${postpage}">${noti.attacker.firstname}
					${noti.attacker.surname} has commented your post
					&quot;${noti.post.title}&quot; <small>on <fmt:formatDate
							pattern="d MMMM y" value="${noti.time}" /> at <fmt:formatDate
							pattern="'at' HH:mm:s" value="${noti.time}" /></small>
			</a></li>
		</c:if>
	</c:forEach>
</ul>

