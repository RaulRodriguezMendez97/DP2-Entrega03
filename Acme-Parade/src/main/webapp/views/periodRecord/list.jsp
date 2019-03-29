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

<display:table pagesize="5" name="periodRecords" id="row"
requestURI="periodRecord/brotherhood/list.do?historyId=${history.id}" >

<display:column>
	<a href="periodRecord/brotherhood/edit.do?periodRecordId=${row.id}&historyId=${history.id}"><spring:message code="periodRecord.edit" /></a>
</display:column>

<display:column titleKey="periodRecord.title">
<jstl:out value="${row.title}"></jstl:out>
</display:column>

<display:column titleKey="periodRecord.description">
<jstl:out value="${row.description}"></jstl:out>
</display:column>

<display:column>
	<a href="periodRecord/brotherhood/show.do?periodRecordId=${row.id}"><spring:message code="periodRecord.moreDetails" /></a>
</display:column>

</display:table>

<input type="button" name="cancel" value="<spring:message code="periodRecord.create" />"
			onclick="javascript: relativeRedir('periodRecord/brotherhood/create.do?historyId=${history.id}');" />

</security:authorize>

<security:authorize access="isAnonymous()">

<display:table pagesize="5" name="periodRecords" id="row"
requestURI="periodRecord/list.do?historyId=${history.id}" >



<display:column titleKey="periodRecord.title">
<jstl:out value="${row.title}"></jstl:out>
</display:column>

<display:column titleKey="periodRecord.description">
<jstl:out value="${row.description}"></jstl:out>
</display:column>



</display:table>

<a href="history/list.do?idBrotherhood=${brotherhood.id }"><spring:message code="periodRecord.back" /></a>


</security:authorize>
