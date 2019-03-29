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

	<b><spring:message code="inceptionRecord.title"/></b>: <jstl:out value=" ${inceptionRecord.title}"></jstl:out><br>
	<b><spring:message code="inceptionRecord.description"/>:</b> <jstl:out value="${inceptionRecord.description}"></jstl:out><br>

<input type="button" name="create" value="<spring:message code="inceptionRecord.edit" />"
			onclick="javascript: relativeRedir('inception-record/brotherhood/edit.do?inceptionRecordId=${inceptionRecord.id}');" />
<input type="button" name="create" value="<spring:message code="inceptionRecord.pictures" />"
			onclick="javascript: relativeRedir('picture/brotherhood/picturesInceptionRecord.do?inceptionRecordId=${inceptionRecord.id}&historyId=${historyId}');" />
<input type="button" name="create" value="<spring:message code="procession.back" />"
			onclick="javascript: relativeRedir('history/brotherhood/list.do');" />
	<a href="inception-record/brotherhood/delete.do?inceptionRecordId=${inceptionRecord.id}"><spring:message code="request.delete" /></a>
</security:authorize>

<security:authorize access="isAnonymous()">

	<b><spring:message code="inceptionRecord.title"/></b>: <jstl:out value=" ${inceptionRecord.title}"></jstl:out><br>
	<b><spring:message code="inceptionRecord.description"/>:</b> <jstl:out value="${inceptionRecord.description}"></jstl:out><br>


<a href="history/list.do?idBrotherhood=${brotherhood.id}"><spring:message code="inception.back" /></a>

</security:authorize>