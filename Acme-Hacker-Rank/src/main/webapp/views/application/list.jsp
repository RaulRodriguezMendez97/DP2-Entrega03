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

<!-- Poner aqui lo de company -->

</security:authorize>

<security:authorize access="hasRole('HACKER')">

<display:table pagesize="5" name="applications" id="row"
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
<display:column property="curricula.id" titleKey="application.curricula" />
<display:column property="moment" titleKey="application.moment" />
<display:column property="explication" titleKey="application.explication" />
<display:column property="urlCode" titleKey="application.urlCode" />
<display:column property="submitMoment" titleKey="application.submitMoment" />
<display:column >
<a href="application/hacker/show.do?applicationId=${row.id}"><spring:message code="application.show" /></a>
</display:column>
<display:column>
	<jstl:if test="${row.status eq 0 }">
		<a href="application/hacker/edit.do?applicationId=${row.id}"><spring:message code="application.edit" /></a>
	</jstl:if>
	<jstl:if test="${row.status eq 1 or row.status eq 2 or row.status eq 3 }">
	-
	</jstl:if>
</display:column>

</display:table>
<input type="button" name="create" value="<spring:message code="application.create" />"
			onclick="javascript: relativeRedir('application/hacker/create.do');" />

</security:authorize>

