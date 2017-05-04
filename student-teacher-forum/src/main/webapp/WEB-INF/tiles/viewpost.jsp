<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="pgn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="url" value="/viewpost" />
<c:url var="likepath" value="/like" />
<c:url var="dislikepath" value="/dislike" />


<%-- <div class="row">
	<div class="col-md-8 col-md-offset-2 text-center">

		<pgn:pagination url="${url}" page="${page}" size="5" />
		
		
		<c:forEach var="post" items="${page.content}">
			<c:url var="edit" value="/editpost?id=${post.id}" />
			<c:url var="delete" value="/deletepost?id=${post.id}" />
			<c:url var="comments" value="/comment?id=${post.id}" />
			<div class="well">
				<ul class="list-unstyled ui-sortable">
					<strong class="pull-left primary-font">Post Updated on <fmt:formatDate
							pattern="EEEE d MMMM y" value="${post.updated}" /></strong>
					<small class="pull-right text-muted"> <span
						class="glyphicon glyphicon-time"></span> <fmt:formatDate
							pattern="'at' HH:mm:s" value="${post.updated}" /></small>
					</br>
					<br />
					<li class="ui-state-default"><a href="${comments}">${post.text }</a>
					</li>
					</br>
					<div class="edit-links pull-right">
						<a href="${edit}">edit</a>|<a
							onclick="return confirm('This post is going to be deleted. Are you sure?');"
							href="${delete}">delete</a>
					</div>
					
					
					<c:set var="hideLike" value="${fn:contains(post.likes, user) ? 'none': 'unset'}"/>
					<c:set var="hideDislike" value="${fn:contains(post.likes, user) ? 'unset': 'none'}"/>
					
					<div class="edit-links pull-left">
						<a style="display: ${hideLike}" href="#" id="l${post.id}" onclick="like(this.id)">Like</a>
						<a style="display: ${hideDislike}" href="#" id="d${post.id}" onclick="dislike(this.id)">Dislike</a>

					</div>
				</ul>
			</div>
		</c:forEach>
	</div>
</div>
 --%>

<div class="row">

	<!-- Blog Entries Column -->
	<div class="col-md-8">

		<h1 class="page-header">Posts</h1>

		<pgn:pagination url="${url}" page="${page}" size="5" />


		<c:forEach var="post" items="${page.content}">
			<c:url var="edit" value="/editpost?id=${post.id}" />
			<c:url var="delete" value="/deletepost?id=${post.id}" />
			<c:url var="comments" value="/comment?id=${post.id}" />
			<c:url var="profileLink" value="/profile/${post.user.id}" />
			<c:url var="postPhoto" value="/post-photo/${post.id}" />
			<h3>
				<a href="${comments}">${post.title }</a>
			</h3>
			<p class="lead">
				by <a href="${profileLink}">${post.user.firstname}
					${post.user.surname}</a>
			</p>
			<p>
				<small> <span class="glyphicon glyphicon-time"></span>
					Posted on <fmt:formatDate pattern="EEEE d MMMM y"
						value="${post.updated}" /> at <fmt:formatDate
						pattern="'at' HH:mm:s" value="${post.updated}" /></small>
			</p>

			<p>
				<a href="${comments}">${post.text }</a>
			</p>
			<c:if test="${post.photoName!= null}">
				<hr>
				<img class="img-responsive" src="${postPhoto}" alt="" />
				<hr>
			</c:if>
			<br />
			<br />
			<c:choose>
				<c:when test="${post.user==user}">
					<div class="edit-links-delete pull-right">
						<a href="${edit}">edit</a> | <a
							onclick="return confirm('This post is going to be deleted. Are you sure?');"
							href="${delete}">delete</a>
					</div>
				</c:when>
			</c:choose>
			<c:set var="hideLike"
				value="${fn:contains(post.likes, user) ? 'none': 'unset'}" />
			<c:set var="hideDislike"
				value="${fn:contains(post.likes, user) ? 'unset': 'none'}" />

			<div class="edit-links pull-left">
				<a style="display: ${hideLike}" href="#" id="l${post.id}"
					onclick="like(this.id)">Like</a> <a style="display: ${hideDislike}"
					href="#" id="d${post.id}" onclick="dislike(this.id)">Dislike</a> |
				<a href="${comments}">Comment</a>
			</div>

			<hr />
		</c:forEach>


	</div>

	<!-- Blog Sidebar Widgets Column -->
	<div class="col-md-4">

		<!-- Blog Search Well -->
		<div class="well">
			<h4>Blog Search</h4>
			<div class="input-group">
				<input type="text" class="form-control"> <span
					class="input-group-btn">
					<button class="btn btn-default" type="button">
						<span class="glyphicon glyphicon-search"></span>
					</button>
				</span>
			</div>
			<!-- /.input-group -->
		</div>

		<!-- Blog Categories Well -->
		<div class="well">
			<h4>Blog Categories</h4>
			<div class="row">
				<div class="col-lg-6">
					<ul class="list-unstyled">
						<li><a href="#">Category Name</a></li>
						<li><a href="#">Category Name</a></li>
						<li><a href="#">Category Name</a></li>
						<li><a href="#">Category Name</a></li>
					</ul>
				</div>

			</div>
			<!-- /.row -->
		</div>
	</div>

</div>


<script>
	function like(lid) {
		$('#'.concat(lid)).toggle(1000);
		var id = lid.substring(lid.lastIndexOf("l") + 1);
		var n = Number(id);
		editLikes(n, "${likepath}");
		var did = 'd' + id;
		$('#'.concat(did)).toggle(1000);
	}

	function dislike(did) {
		$('#'.concat(did)).toggle(1000);
		var id = did.substring(did.lastIndexOf("d") + 1);
		var n = Number(id);
		editLikes(n, "${dislikepath}");
		var lid = 'l' + id;
		$('#'.concat(lid)).toggle(1000);
	}

	function editLikes(id, actionUrl) {
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

	$(document).ready(function() {

	});
</script>