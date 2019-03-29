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

<b><spring:message code="periodRecord.title" /> : </b> <jstl:out value="${periodRecord.title}"></jstl:out> <br/>
<b><spring:message code="periodRecord.description" /> : </b> <jstl:out value="${periodRecord.description}"></jstl:out><br/>
<b><spring:message code="periodRecord.startYear" /> : </b> <jstl:out value="${periodRecord.starYear}"></jstl:out><br/>
<b><spring:message code="periodRecord.endYear" /> : </b> <jstl:out value="${periodRecord.endYear}"></jstl:out><br/>

<br/>
<input type="button" name="cancel" value="<spring:message code="periodRecord.cancel" />"
			onclick="javascript: relativeRedir('periodRecord/brotherhood/list.do?historyId=${history.id}');" />
			
<input type="button" name="create" value="<spring:message code="periodRecord.picture" />"
			onclick="javascript: relativeRedir('picture/brotherhood/picturesPeriodRecord.do?periodRecordId=${periodRecord.id}');" />

</security:authorize>
