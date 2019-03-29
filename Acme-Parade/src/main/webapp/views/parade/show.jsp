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

<security:authorize access="hasRole('BROTHERHOOD')">

<spring:message code="procession.title"/>:<jstl:out value=" ${procession.title}"></jstl:out><br>
<spring:message code="procession.moment"/>: <jstl:out value="${procession.moment}"></jstl:out><br>
<spring:message code="procession.description"/>: <jstl:out value="${procession.description}"></jstl:out><br>
<spring:message code="procession.rows"/>: <jstl:out value="${procession.maxRows}"></jstl:out><br>
<spring:message code="procession.columns"/>: <jstl:out value="${procession.maxColumns}"></jstl:out><br>
<spring:message code="procession.draftMode"/>: 
<jstl:if test="${procession.draftMode eq 1 }">
<spring:message code="procession.draftMode.Yes"/>
</jstl:if>
<jstl:if test="${procession.draftMode eq 0 }">
<spring:message code="procession.draftMode.No"/>
</jstl:if>
<br>
<input type="button" name="create" value="<spring:message code="procession.back" />"
			onclick="javascript: relativeRedir('parade/brotherhood/list.do');" />

</security:authorize>