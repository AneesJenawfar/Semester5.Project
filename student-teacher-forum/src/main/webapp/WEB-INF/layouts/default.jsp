
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>

<title><tiles:insertAttribute name="title" /></title>
<c:set var="contextRoot" value="${pageContext.request.contextPath}" />
<c:set var="search" value="/search" />
<c:url var="notify" value="/notify" />
<c:url var="comment" value="${contextRoot}/comment" />
<c:url var="notification" value="${contextRoot}/notifications" />

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">

<link href="${contextRoot}/css/bootstrap.min.css" rel="stylesheet">
<link href="${contextRoot}/css/design.css" rel="stylesheet">

<!-- This is for javascript tagging on the profile -->
<link href="${contextRoot}/css/jquery.tagit.css" rel="stylesheet">

<script src="${contextRoot}/js/jquery-ui.min.js"></script>
<script src="${contextRoot}/js/tag-it.min.js"></script>
</head>


<body>
	<nav class="navbar CC">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="#">AccaForum</a>
		</div>

		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li class="active"><a href="${contextRoot}/">HOME <span
						class="sr-only">(current)</span></a></li>
				<li><a href="${contextRoot}/about">About</a></li>

				<sec:authorize access="isAuthenticated()">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">Post <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="${contextRoot}/viewpost">View Posts </a></li>
							<li><a href="${contextRoot}/addpost">Create Post</a></li>
						</ul></li>
				</sec:authorize>

				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">Administrator<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="#">View Users </a></li>
							<li><a href="#">New Posts</a></li>
						</ul></li>
				</sec:authorize>
			</ul>

			<form class="navbar-form navbar-left" method="post"
				action="${search}">
				<div class="form-group">
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" /> <input type="text" class="form-control"
						name="s" placeholder="Search">
				</div>
				<button id="search-button" type="submit" class="btn btn-default">Submit</button>
			</form>

			<sec:authorize access="isAuthenticated()">
				<ul class="nav navbar-nav navbar-right">



					<li class="dropdown"><a id="not-link" href="#"
						class="dropdown-toggle" data-toggle="dropdown" role="button">Notifications<span
							id="notification_count">3</span></a>
						<ul id="notify-drop" class="dropdown-menu">

						</ul></li>

					<li><a href="${contextRoot}/profile">Profile</a></li>
					<li><a href="javascript:$('#logoutform').submit();">Logout</a></li>
				</ul>
			</sec:authorize>

			<sec:authorize access="!isAuthenticated()">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="${contextRoot}/login">Login</a></li>
					<li><a href="${contextRoot}/register">Register</a></li>
				</ul>
			</sec:authorize>
		</div>
	</div>
	</nav>

	<c:url var="logoutlink" value="/logout" />
	<form id="logoutform" action="${logoutlink}" method="post">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>

	<div class="container">
		<tiles:insertAttribute name="content" />
	</div>
	<script src="${contextRoot}/js/bootstrap.min.js"></script>




	<script>
		function getnotify(actionurl) {

			$.ajax({
				type : "GET",
				url : actionurl,
				success : function(data) {
					result = data;
					printx(result);
				}
			});

		}
		
		function dateFormat(d){
			var myDate = ("00" + (d.getMonth() + 1)).slice(-2) + "/"
			+ ("00" + d.getDate()).slice(-2) + "/"
			+ d.getFullYear() + " "
			+ ("00" + d.getHours()).slice(-2) + ":"
			+ ("00" + d.getMinutes()).slice(-2) + ":"
			+ ("00" + d.getSeconds()).slice(-2);
			return myDate
		}

		function printx(x) {
			$("#notify-drop").empty();
			x.forEach(function(item) {
				var url = "${comment}?id=" + item.post.id;

				if (item.action == "like") {

					var d = new Date(item.time);
					var myDate = dateFormat(d);
					var element = $('<li><a href="'+url+'">'
							+ item.attacker.firstname + '&nbsp;'
							+ item.attacker.surname
							+ '&nbsp; has liked your post &nbsp;' + '&quot;'
							+ item.post.title
							+ '&quot; &nbsp; <small>on &nbsp;' + myDate
							+ '</small></a></li>');
					$("#notify-drop").append(element);

				} else if (item.action == "share") {

					var d = new Date(item.time);
					var myDate = dateFormat(d);
					var element = $('<li><a href="'+url+'">'
							+ item.attacker.firstname + '&nbsp;'
							+ item.attacker.surname
							+ '&nbsp; has shared your post &nbsp;' + '&quot;'
							+ item.post.title
							+ '&quot; &nbsp; <small>on &nbsp;' + myDate
							+ '</small></a></li>');
					$("#notify-drop").append(element);
				} else if (item.action == "shared") {

					var d = new Date(item.time);
					var myDate = dateFormat(d);
					var element = $('<li><a href="'+url+'">'
							+ item.attacker.firstname + item.attacker.surname
							+ 'has shared a post' + '&quot;' + item.post.title
							+ '&quot; with you &nbsp; <small>on &nbsp;'
							+ myDate + '</small></a></li>');
					$("#notify-drop").append(element);

				} else if (item.action == "shared") {

					var d = new Date(item.time);
					var myDate = dateFormat(d);
					var element = $('<li><a href="'+url+'">'
							+ item.attacker.firstname + '&nbsp;'
							+ item.attacker.surname
							+ '&nbsp; has commented on your post &nbsp;'
							+ '&quot;' + item.post.title
							+ '&quot;&nbsp; <small>on &nbsp;' + myDate
							+ '</small></a></li>');
					$("#notify-drop").append(element);
				}
			});
			var all = "${notification}";
			var element = $('<li><a id="notificationFooter" href="'+all+'">See All</a></li>');
			$("#notify-drop").append(element);
		}

		$(document).ready(function() {

			$("#not-link").click(function(event) {
				event.preventDefault();
				getnotify('${notify}');

			});
		});
	</script>

</body>
</html>