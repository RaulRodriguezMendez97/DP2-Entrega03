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

<display:table pagesize="5" name="processions" id="row"
requestURI="parade/brotherhood/list.do" >

<display:column property="ticker" titleKey="procession.ticker" />
<display:column property="moment" titleKey="procession.moment" />
<display:column property="description" titleKey="procession.description" />
<display:column property="title" titleKey="procession.title" />

<jstl:if test="${not empty exception}">
		<p style="color:red;font-weight: bold;"> <spring:message code="procession.error.foreignKey" /> </p>
</jstl:if>
<display:column>
	<a href="parade/brotherhood/show.do?paradeId=${row.id}"><spring:message code="procession.show" /></a>
</display:column>
<jstl:if test="${row.draftMode eq 1 }">
	<display:column>
		<a href="parade/brotherhood/edit.do?paradeId=${row.id}"><spring:message code="procession.edit" /></a>
	</display:column>
	<display:column>
		<a href="parade/brotherhood/delete.do?paradeId=${row.id}"><spring:message code="procession.delete" /></a>
	</display:column>
</jstl:if>
<jstl:if test="${row.draftMode eq 0 }">
	<display:column>-</display:column>
	<display:column>-</display:column>
</jstl:if>

<display:column>
	<a href="request/brotherhood/list.do?paradeId=${row.id}"><spring:message code="procession.requests" /></a>
</display:column>

</display:table>

<input type="button" name="create" value="<spring:message code="procession.create" />"
			onclick="javascript: relativeRedir('parade/brotherhood/create.do');" />

</security:authorize>