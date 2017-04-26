
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
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
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
							<li role="separator" class="divider"></li>
							<li><a href="#">One more separated link</a></li>
						</ul></li>
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">Administrator<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="#">View Users </a></li>
							<li><a href="#">New Posts</a></li>
							<li role="separator" class="divider"></li>
							<li><a href="#">One more separated link</a></li>
						</ul></li>
				</sec:authorize>
			</ul>
			
			
			
			
			<form class="navbar-form navbar-left" method="post" action="${search}">
				<div class="form-group">
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" /> 
					<input type="text" class="form-control" name="s"
						placeholder="Search">
				</div>
				<button id="search-button" type="submit" class="btn btn-default">Submit</button>
			</form>
			
			
			
			
			<sec:authorize access="isAuthenticated()">
				<ul class="nav navbar-nav navbar-right">
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
</body>
</html>