<%--
 * action-1.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('COMPANY')">
<jstl:if test="${exception eq 'e'}">
	<b style="color:red;"><spring:message code="application.cant.delete"/></b><br>
</jstl:if>
<display:table pagesize="5" name="applications" id="row"
requestURI="application/company/list.do" >

<display:column sortable="true" class="color" titleKey="application.status">

<jstl:if test="${row.status eq 0 }">
	<spring:message code="application.status.pending" />
</jstl:if>

<jstl:if test="${row.status eq 2 }">
	<spring:message code="application.status.accepted" />
</jstl:if>

<jstl:if test="${row.status eq 3 }">
	<spring:message code="application.status.cancel" />
</jstl:if>
<jstl:if test="${row.status eq 1 }">
	<spring:message code="application.status.submitted" />
</jstl:if>
</display:column>

<display:column class="color" property="columna" titleKey="request.columna" />
<display:column class="color" property="row" titleKey="request.row" />
<display:column class="color" property="description" titleKey="request.description" />
<display:column class="color" property="procession.title" titleKey="request.procession" />

<display:column class="color">
<a href="request/member/show.do?requestId=${row.id}"><spring:message code="request.show" /></a>
</display:column>

<display:column class="color">
	<jstl:if test="${row.status eq 1 }">
		<a href="request/member/delete.do?requestId=${row.id}"><spring:message code="request.delete" /></a>
	</jstl:if>
	<jstl:if test="${row.status eq 0 or row.status eq 2 }">
	-
	</jstl:if>
</display:column>

</display:table>

<input type="button" name="create" value="<spring:message code="request.create" />"
			onclick="javascript: relativeRedir('request/member/create.do');" />

</security:authorize>

<security:authorize access="hasRole('HACKER')">

<display:table pagesize="5" name="application" id="row"
requestURI="application/hacker/list.do" >

<display:column sortable="true" titleKey="application.status">

<jstl:if test="${row.status eq 0 }">
	<spring:message code="application.status.pending" />
</jstl:if>

<jstl:if test="${row.status eq 1 }">
	<spring:message code="application.status.submitted" />
</jstl:if>

<jstl:if test="${row.status eq 2 }">
	<spring:message code="application.status.accepted" />
</jstl:if>

<jstl:if test="${row.status eq 3 }">
	<spring:message code="application.status.cancel" />
</jstl:if>
</display:column>
<display:column property="curricula" titleKey="application.curricula" />
<display:column property="moment" titleKey="application.moment" />
<display:column property="explication" titleKey="application.explication" />
<display:column property="urlCode" titleKey="application.urlCode" />
<display:column property="submitMoment" titleKey="application.submitMoment" />
<display:column >
<a href="application/hacker/show.do?applicationId=${row.id}"><spring:message code="application.show" /></a>
</display:column>
<display:column>
	<jstl:if test="${row.status eq 1 }">
		<a href="application/hacker/edit.do?papplicationId=${row.id}"><spring:message code="application.aceptar" /></a>
	</jstl:if>
	<jstl:if test="${row.status eq 0 or row.status eq 2 }">
	-
	</jstl:if>
</display:column>

<display:column>
	<jstl:if test="${row.status eq 1 }">
		<a href="application/hacker/edit.do?appicationId=${row.procession.id}"><spring:message code="application.rechazar" /></a>
	</jstl:if>
	<jstl:if test="${row.status eq 0 or row.status eq 2 }">
	-
	</jstl:if>
</display:column>

</display:table>

</security:authorize>

<script type="text/javascript">
var x = document.getElementsByClassName("color");
var i = 0;
while (i < x.length) {
	var estado = x[i].innerHTML;
	
	if(estado.trim() === "Accepted" || estado.trim() === "Aceptada"){
		var casilla = 0;
		for (casilla ; casilla< 7; casilla++) {
			document.getElementsByClassName("color")[i+casilla].style.background='green';
		}
	}else if(estado.trim() === "Pending" || estado.trim() === "Pendiente"){
		var casilla = 0;
		for (casilla ; casilla< 7; casilla++) {
			document.getElementsByClassName("color")[i+casilla].style.background='grey';
		}
	}else{
		var casilla = 0;
		for (casilla ; casilla< 7; casilla++) {
			document.getElementsByClassName("color")[i+casilla].style.background='orange';
		}
	}
	
	i= i+7;
}

</script>