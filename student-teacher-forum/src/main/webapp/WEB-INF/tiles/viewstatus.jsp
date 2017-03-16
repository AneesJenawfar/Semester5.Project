<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="pgn" %>
<c:set var="url" value="/viewstatus"/>

<div class="row">
    <div class="col-md-8 col-md-offset-2 text-center">
		
		<pgn:pagination  url ="${url}" page="${page}" size="5"/>
		<c:forEach var = "status" items="${page.content}">
			<c:url var="edit" value="/editstatus?id=${status.id}"/>
			<c:url var="delete" value="/deletestatus?id=${status.id}"/>
			<div class="well">
		    	<ul class="list-unstyled ui-sortable">
		        <strong class="pull-left primary-font">Status Updatetd on <fmt:formatDate pattern = "EEEE d MMMM y"
		         value="${status.updated}"/></strong>
		        <small class="pull-right text-muted">
		           <span class="glyphicon glyphicon-time"></span><fmt:formatDate pattern = "'at' HH:mm:s"
		         value="${status.updated}"/></small>
		        </br>
		        <br/>
		        <li class="ui-state-default">${status.text }</li>
		        </br>
				<div class="edit-links pull-right">
					<a href="${edit}">edit</a>|<a onclick="return confirm('This status is going to be deleted. Are you sure?');"
					href="${delete}">delete</a>
				</div>
		    	</ul>
		    </div>
		</c:forEach>
	</div>
</div>