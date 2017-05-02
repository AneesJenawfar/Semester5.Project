<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<div class="row">
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

<script src="https://cloud.tinymce.com/stable/tinymce.min.js"></script>
<%-- <script src="${contextRoot}/js/tinymce.min.js"></script> --%>
<script>
	tinymce.init({
		selector : 'textarea',
		plugins : "link"
	});
</script>