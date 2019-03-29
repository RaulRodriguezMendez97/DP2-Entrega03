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

<security:authorize access="hasRole('MEMBER')">
<jstl:if test="${exception eq 'e'}">
	<b style="color:red;"><spring:message code="request.cant.delete"/></b><br>
</jstl:if>
<display:table pagesize="5" name="requests" id="row"
requestURI="request/member/list.do" >

<display:column sortable="true" class="color" titleKey="enrolment.status">

<jstl:if test="${row.status eq 1 }">
	<spring:message code="enrolment.status.pending" />
</jstl:if>

<jstl:if test="${row.status eq 0 }">
	<spring:message code="enrolment.status.accepted" />
</jstl:if>

<jstl:if test="${row.status eq 2 }">
	<spring:message code="enrolment.status.cancel" />
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

<security:authorize access="hasRole('BROTHERHOOD')">

<display:table pagesize="5" name="requests" id="row"
requestURI="request/brotherhood/list.do" >

<display:column sortable="true" titleKey="enrolment.status">

<jstl:if test="${row.status eq 1 }">
	<spring:message code="enrolment.status.pending" />
</jstl:if>

<jstl:if test="${row.status eq 0 }">
	<spring:message code="enrolment.status.accepted" />
</jstl:if>

<jstl:if test="${row.status eq 2 }">
	<spring:message code="enrolment.status.cancel" />
</jstl:if>
</display:column>
<display:column property="member.name" titleKey="request.member" />
<display:column property="columna" titleKey="request.columna" />
<display:column property="row" titleKey="request.row" />
<display:column property="description" titleKey="request.description" />
<display:column >
<a href="request/brotherhood/show.do?requestId=${row.id}"><spring:message code="request.show" /></a>
</display:column>
<display:column>
	<jstl:if test="${row.status eq 1 }">
		<a href="request/brotherhood/edit.do?processionId=${row.procession.id}&requestId=${row.id}&status=0"><spring:message code="request.aceptar" /></a>
	</jstl:if>
	<jstl:if test="${row.status eq 0 or row.status eq 2 }">
	-
	</jstl:if>
</display:column>

<display:column>
	<jstl:if test="${row.status eq 1 }">
		<a href="request/brotherhood/edit.do?processionId=${row.procession.id}&requestId=${row.id}&status=2"><spring:message code="request.rechazar" /></a>
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