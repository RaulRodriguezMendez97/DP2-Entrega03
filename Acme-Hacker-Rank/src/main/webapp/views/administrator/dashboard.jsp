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
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<security:authorize access="hasRole('ADMIN')">

<fieldset>
<legend><spring:message code="administrator.position.company" /></legend>
<b><spring:message code="administrator.avg" /></b>: <fmt:formatNumber type="number" maxIntegerDigits = "3" value ="${getAvgPositionByCompany}"></fmt:formatNumber><br/>
<b><spring:message code="administrator.min" /></b>: <jstl:out value="${getMinPositionByCompany}"></jstl:out><br/>
<b><spring:message code="administrator.max" /></b>: <jstl:out value="${getMaxPositionByCompany}"></jstl:out><br/>
<b><spring:message code="administrator.desv" /></b>: <fmt:formatNumber type="number" maxIntegerDigits = "3" value ="${getDesvPositionByCompany}"></fmt:formatNumber>
</fieldset>

<fieldset>
<legend><spring:message code="administrator.application.hacker" /></legend>
<b><spring:message code="administrator.avg" /></b>: <fmt:formatNumber type="number" maxIntegerDigits = "3" value ="${getAvgAppByHackers}"></fmt:formatNumber> <br/>
<b><spring:message code="administrator.min" /></b>: <jstl:out value="${getMinAppByHackers}"></jstl:out><br/>
<b><spring:message code="administrator.max" /></b>: <jstl:out value="${getMaxAppByHackers}"></jstl:out><br/>
<b><spring:message code="administrator.desv" /></b>: <fmt:formatNumber type="number" maxIntegerDigits = "3" value ="${getDesvAppByHackers}"></fmt:formatNumber>
</fieldset>

<fieldset>
<b><spring:message code="administrator.company.positions" /></b>:
<br/>
<jstl:if test="${fn:length(getCompaniesWithMorePositions) ne 0}">
<jstl:forEach var="item" items="${getCompaniesWithMorePositions}">
<jstl:out value="${item}"></jstl:out>
<br/>
</jstl:forEach>
</jstl:if>
</fieldset>

<fieldset>
<b><spring:message code="administrator.hacker.applications" /></b>:
<br/>
<jstl:if test="${fn:length(getHackersWithMoreApplications) ne 0}">
<jstl:forEach var="item" items="${getHackersWithMoreApplications}">
<jstl:out value="${item}"></jstl:out>
<br/>
</jstl:forEach>
</jstl:if>
</fieldset>

<fieldset>
<legend><spring:message code="administrator.position.salary" /></legend>
<b><spring:message code="administrator.avg" /></b>: <fmt:formatNumber type="number" maxIntegerDigits = "3" value ="${getAvgSalaryOfPositions}"></fmt:formatNumber> <br/>
<b><spring:message code="administrator.min" /></b>: <jstl:out value="${getMinSalaryOfPositions}"></jstl:out><br/>
<b><spring:message code="administrator.max" /></b>: <jstl:out value="${getMaxSalaryOfPositions}"></jstl:out><br/>
<b><spring:message code="administrator.desv" /></b>: <fmt:formatNumber type="number" maxIntegerDigits = "3" value ="${getDesvSalaryOfPositions}"></fmt:formatNumber>
</fieldset>

<fieldset>
<legend><spring:message code="administrator.salary" /></legend>
<b><spring:message code="administrator.best" /></b>: <jstl:out value="${getPositionWithBestSalary}"></jstl:out><br/>
<b><spring:message code="administrator.worst" /></b>: <jstl:out value="${getPositionWithWorstSalary}"></jstl:out><br/>
</fieldset>

<fieldset>
<legend><spring:message code="administrator.curricula-finder" /></legend>
<b><spring:message code="administrator.curricula-finder.curricula" /></b>: <jstl:out value="${getPositionWithBestSalary}"></jstl:out><br/>
<b><spring:message code="administrator.curricula-finder.finder1" /></b>: <jstl:out value="${getPositionWithWorstSalary}"></jstl:out><br/>
<b><spring:message code="administrator.curricula-finder.finder2" /></b>: <jstl:out value="${getPositionWithWorstSalary}"></jstl:out><br/>
</fieldset>

</security:authorize>
