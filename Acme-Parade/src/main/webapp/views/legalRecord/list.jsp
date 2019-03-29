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
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<security:authorize access="hasRole('BROTHERHOOD')">

<display:table pagesize="5" name="legalRecords" id="row"
requestURI="legalRecord/brotherhood/list.do?historyId=${history.id}" >

<display:column titleKey="legalRecord.title.list">
<jstl:out value="${row.title}"></jstl:out>
</display:column>

<display:column titleKey="legalRecord.description.list">
<jstl:out value="${row.description}"></jstl:out>
</display:column>

<display:column titleKey="legalRecord.legalName.list">
<jstl:out value="${row.legalName}"></jstl:out>
</display:column>

<display:column titleKey="legalRecord.VATNumber.list">
<jstl:out value="${row.VATNumber}"></jstl:out>
</display:column>


<display:column titleKey="legalRecord.laws.list"> 

	<c:forEach items="${row.laws}" var="law">
	<jstl:out value="${law}"></jstl:out>
	</c:forEach>
	</display:column>
	
	<display:column>
	<a href="legalRecord/brotherhood/edit.do?legalRecordId=${row.id}&historyId=${history.id}" ><spring:message code="legalRecord.edit" /></a>
</display:column>
	

</display:table>

<a href="history/brotherhood/list.do?idBrotherhood=${brotherhood.id }"><spring:message code="legalRecord.back" /></a>

<a href="legalRecord/brotherhood/create.do?historyId=${history.id}" ><spring:message code="legalRecord.create" /></a>


</security:authorize>

<security:authorize access="isAnonymous()">

<display:table pagesize="5" name="legalRecords" id="row"
requestURI="legalRecord/list.do?historyId=${history.id}" >

<display:column titleKey="legalRecord.title.list">
<jstl:out value="${row.title}"></jstl:out>
</display:column>

<display:column titleKey="legalRecord.description.list">
<jstl:out value="${row.description}"></jstl:out>
</display:column>

<display:column titleKey="legalRecord.legalName.list">
<jstl:out value="${row.legalName}"></jstl:out>
</display:column>

<display:column titleKey="legalRecord.VATNumber.list">
<jstl:out value="${row.VATNumber}"></jstl:out>
</display:column>


<display:column titleKey="legalRecord.laws.list"> 

	<c:forEach items="${row.laws}" var="law">
	<jstl:out value="${law}"></jstl:out>
	</c:forEach>
	</display:column>
	
	
</display:table>

<a href="history/list.do?idBrotherhood=${brotherhood.id }"><spring:message code="legalRecord.back" /></a>



</security:authorize>
