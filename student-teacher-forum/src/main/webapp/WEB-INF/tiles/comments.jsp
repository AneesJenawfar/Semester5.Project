<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:url var="postPhoto" value="/post-photo/${post.id}" />
<c:url var="likepath" value="/like" />
<c:url var="dislikepath" value="/dislike" />


<%-- <div class="row">
	<div class="col-md-8 col-md-offset-2 text-center">
		<div class="well">
			<ul class="list-unstyled ui-sortable">
				<strong class="pull-left primary-font">Post Updated on <fmt:formatDate
						pattern="EEEE d MMMM y" value="${post.updated}" /></strong>
				<small class="pull-right text-muted"> <span
					class="glyphicon glyphicon-time"></span> <fmt:formatDate
						pattern="'at' HH:mm:s" value="${post.updated}" /></small>
				<br />
				<br />
				<li class="ui-state-default">${post.text }</li>
				<br />
			</ul>
		</div>
		<c:forEach var="com" items="${comments}">
			<li class="ui-state-default">${com.text }</li>
		</c:forEach>
	</div>
	
	<div class="col-md-8 col-md-offset-2 text-center">
		<div class="box">
			<div class="box-content">
				<h1 class="tag-title">Add a Comment</h1>
				<form:form modelAttribute="comment">
					<div class="errors">
						<form:errors path="text" />
					</div>
					<div class="form-group">
						<form:textarea path="text" class="form-control" name="text"
							rows="5"></form:textarea>
					</div>
					<div class="form-group">
						<button class="btn btn-primary " name="submit" type="submit">Add</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>
 --%>

<div class="row">

	<!-- Blog Entries Column -->
	<div class="col-md-6 col-md-offset-3">
		<h3>${post.title }</h3>
		<p class="lead">
			by <a href="${profileLink}">${post.user.firstname}
				${post.user.surname}</a>
		</p>
		<p>
			<small> <span class="glyphicon glyphicon-time"></span> Posted
				on <fmt:formatDate pattern="EEEE d MMMM y" value="${post.updated}" />
				<fmt:formatDate pattern="'at' HH:mm:s" value="${post.updated}" /></small>
		</p>

		<p>${post.text }</p>
		<c:if test="${post.photoName!= null}">
			<hr>
			<img class="img-responsive" src="${postPhoto}" alt="" />
			<hr>
		</c:if>

		<c:set var="hideLike"
			value="${fn:contains(post.likes, user) ? 'none': 'unset'}" />
		<c:set var="hideDislike"
			value="${fn:contains(post.likes, user) ? 'unset': 'none'}" />
		<div class="edit-links pull-left">
			<a style="display: ${hideLike}" href="#" id="l${post.id}"
				onclick="like(this.id)">Like</a><a style="display: ${hideDislike}"
				href="#" id="d${post.id}" onclick="dislike(this.id)">Dislike</a>
		</div>
		<br />

		<div class="widget-area no-padding blank">
			<div class="status-upload">
				<form:form modelAttribute="comment">
					<form:textarea path="text" class="form-control" name="text"
						rows="5"></form:textarea>
					<button type="submit" class="btn btn-success green">
						<i class="fa fa-share"></i> Comment
					</button>
				</form:form>
			</div>
		</div>

		<c:forEach var="com" items="${comments}">
			<h4>
				<i class="fa fa-comment"></i> ${com.user.firstname}
				${com.user.surname} says: <small> <fmt:formatDate
						pattern="HH:mm:s" value="${com.added}" /> on <fmt:formatDate
						pattern="EEEE d MMMM y" value="${com.added}" />
				</small>
			</h4>
			<p>${com.text}</p>
		</c:forEach>
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
</script>








<script src="https://cloud.tinymce.com/stable/tinymce.min.js"></script>
<%-- <script src="${contextRoot}/js/tinymce.min.js"></script> --%>
<script>
	tinymce.init({
		selector : 'textarea',
		plugins : "link"
	});
</script>