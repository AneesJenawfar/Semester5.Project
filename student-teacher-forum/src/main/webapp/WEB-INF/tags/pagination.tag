<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!-- ATRIBUTES -->
<!-- Spring page object -->
<%@ attribute name="page" required = "true" type="org.springframework.data.domain.Page"%>
<!-- base url of page -->
<%@ attribute name="url" required = "true" %>
<%@ attribute name="size" required = "false" %>

<c:set var="paramListSep" value="${fn:contains(url, '?') ? '&': '?'}"/>

<c:set var ="size" value="${empty size ? 10: size}"/>
<c:set var ="block" value="${empty param.b ? 0: param.b}"/>
<c:set var="startpage" value="${block * size +1 }"/>
<c:set var="endpage" value="${(block+1) * size}"/>
<c:set var="endpage" value="${endpage > page.totalPages ? page.totalPages: endpage}"/>

<c:if test="${page.totalPages != 1}">
	<div class="pagination">
		<c:if test="${block != 0}">
			<li class="previous"><a href="${url}${paramListSep}b=${block-1}&p=${(block-1)*size + 1}">&larr; Older</a></li>
		</c:if>
		<c:forEach var="pagenumber" begin="${startpage}" end="${endpage}">
			<c:choose>
				<c:when test="${page.number != pagenumber-1}">
					<a href="${url}${paramListSep}p=${pagenumber}&b=${block}"><c:out value="${pagenumber}"/></a>
				</c:when>
				<c:otherwise>
					<strong><c:out value="${pagenumber}"/></strong>
				</c:otherwise>
			</c:choose>				
			<c:if test="${pagenumber != endpage}">
				|
			</c:if>
		</c:forEach>
		<c:if test="${endpage != page.totalPages}">
			<li class="next"><a href="${url}${paramListSep}b=${block+1}&p=${(block+1)*size + 1}">Newer &rarr;</a></li>
		</c:if>
	</div>
</c:if>