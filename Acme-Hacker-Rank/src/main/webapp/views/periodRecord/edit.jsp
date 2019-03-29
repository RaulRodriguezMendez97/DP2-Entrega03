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

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<security:authorize access="hasRole('BROTHERHOOD')">
<form:form action="periodRecord/brotherhood/edit.do?historyId=${history.id }" modelAttribute="periodRecord">

<jstl:if test="${not empty exception}">
		<p style="color:red"> <spring:message code="periodRecord.error" /> </p>
</jstl:if>

<form:hidden path="id"/>
<form:hidden path="version"/>
<form:hidden path="pictures"/>

<acme:textbox code="periodRecord.title" path="title"/>
<acme:textarea code="periodRecord.description" path="description"/>
<acme:textbox code="periodRecord.startYear" path="starYear"/>
<acme:textbox code="periodRecord.endYear" path="endYear"/>


<br/>
<input type="submit" name="save" 
	value="<spring:message code="periodRecord.save" />" />
	
	<jstl:if test="${periodRecord.id ne 0 }">
<input type="submit" name="delete" 
	value="<spring:message code="periodRecord.delete" />" />
</jstl:if>
<input type="button" name="cancel" value="<spring:message code="notification.cancel" />"
			onclick="javascript: relativeRedir('periodRecord/brotherhood/list.do?historyId=${history.id}');" />
</form:form>

</security:authorize>
