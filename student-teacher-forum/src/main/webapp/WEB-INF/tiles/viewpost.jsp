<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="pgn" %>
<c:set var="url" value="/viewpost"/>

<div class="row">
    <div class="col-md-8 col-md-offset-2 text-center">
		
		<pgn:pagination  url ="${url}" page="${page}" size="5"/>
		<c:forEach var = "post" items="${page.content}">
			<c:url var="edit" value="/editpost?id=${post.id}"/>
			<c:url var="delete" value="/deletepost?id=${post.id}"/>
			<div class="well">
		    	<ul class="list-unstyled ui-sortable">
		        <strong class="pull-left primary-font">Post Updated on <fmt:formatDate pattern = "EEEE d MMMM y"
		         value="${post.updated}"/></strong>
		        <small class="pull-right text-muted">
		           <span class="glyphicon glyphicon-time"></span><fmt:formatDate pattern = "'at' HH:mm:s"
		         value="${post.updated}"/></small>
		        </br>
		        <br/>
		        <li class="ui-state-default">${post.text }</li>
		        </br>
				<div class="edit-links pull-right">
					<a href="${edit}">edit</a>|<a onclick="return confirm('This post is going to be deleted. Are you sure?');"
					href="${delete}">delete</a>
				</div>
		    	</ul>
		    </div>
		</c:forEach>
	</div>
</div>