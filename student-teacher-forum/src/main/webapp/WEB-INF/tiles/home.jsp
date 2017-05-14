<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="pgn"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<c:set var="url" value="/viewpost" />

<sec:authorize access="!isAuthenticated()">
	<div class="row">
		<div class="col-md-8 col-md-offset-2 text-center">Welcome to
			AccaForum</div>
	</div>
</sec:authorize>
<div id="notify-drop"></div>
<%-- <sec:authorize access="isAuthenticated()">
	<div class="row">
		<div class="col-md-8 col-md-offset-2 text-center">

			<pgn:pagination url="${url}" page="${page}" size="5" />
			<c:forEach var="post" items="${page.content}">
				<c:url var="edit" value="/editpost?id=${post.id}" />
				<c:url var="delete" value="/deletepost?id=${post.id}" />
				<div class="well">
					<ul class="list-unstyled ui-sortable">
						<strong class="pull-left primary-font">Post Updated on <fmt:formatDate
								pattern="EEEE d MMMM y" value="${post.updated}" /></strong>
						<small class="pull-right text-muted"> <span
							class="glyphicon glyphicon-time"></span> <fmt:formatDate
								pattern="'at' HH:mm:s" value="${post.updated}" /></small>
						</br>
						<br />
						<li class="ui-state-default">${post.text }</li>
						</br>
						<div class="edit-links pull-right">
							<a href="${edit}">edit</a>|<a
								onclick="return confirm('This post is going to be deleted. Are you sure?');"
								href="${delete}">delete</a>
						</div>
					</ul>
				</div>
			</c:forEach>
		</div>
	</div>
</sec:authorize> --%>

<script type="text/javascript">
		function print(data) {
			for (var i = 0; i < data.length; i++) {
				$('#notify-drop').append($('<li><a href="#">' + data[0].firstArray.firstKey
						+ '</a></li>'));
				$('#notify-drop').append($('<li><a href="#">' + data[0].secondArray.secondKey
						+ '</a></li>'));
			}
		}

		$(document).ready(function() {

			var data = {
				"array" : [ {
					"firstArray" : {
						"firstKey" : "firstValue",
						"secondKey" : "secondValue"
					}
				}, {
					"secondArray" : {
						"firstKey" : "firstValue",
						"secondKey" : "secondValue"
					}
				} ]
			};
			print(data);
		});
	</script>
