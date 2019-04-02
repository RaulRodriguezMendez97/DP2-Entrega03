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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<security:authorize access="hasRole('HACKER')">

<strong><spring:message code="positionData.title"/></strong>: <jstl:out value=" ${positionData.title}"></jstl:out><br>
<strong><spring:message code="positionData.description"/></strong>: <jstl:out value=" ${positionData.description}"></jstl:out><br>
<strong><spring:message code="positionData.startDate"/></strong>: <jstl:out value=" ${positionData.startDate}"></jstl:out><br>
<strong><spring:message code="positionData.endDate"/></strong>: <jstl:out value=" ${positionData.endDate}"></jstl:out><br>

<acme:cancel url="curricula/hacker/list.do" code="positionData.cancel"/>

</security:authorize>





