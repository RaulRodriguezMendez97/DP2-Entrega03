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

<display:table pagesize="5" name="members" id="row"
requestURI="member/brotherhood/list.do" >

<display:column titleKey="member.name">
<jstl:out value="${row.name}"></jstl:out>
</display:column>

<display:column titleKey="member.surname">
<jstl:out value="${row.surname}"></jstl:out>
</display:column>

<display:column titleKey="member.email">
<jstl:out value="${row.email}"></jstl:out>
</display:column>


<display:column titleKey="member.phone">
<jstl:out value="${row.phone}"></jstl:out>
</display:column>

<display:column titleKey="member.address">
<jstl:out value="${row.address}"></jstl:out>
</display:column>


<display:column>
	<a href="enrolment/brotherhood/listAccepted.do?idMember=${row.id}"><spring:message code="brotherhood.enrolment" /></a>
</display:column>

</display:table>


</security:authorize>
