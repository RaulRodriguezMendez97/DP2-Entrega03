<%--
 * action-2.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<security:authorize access="hasRole('BROTHERHOOD')">


<p><spring:message code="float.show.title"/></p>
<fieldset>
<legend><spring:message code="float.datas" /></legend>
<spring:message code="float.title" />: <jstl:out value="${paso.title }"/><br/>
<spring:message code="float.description" />: <jstl:out value="${paso.description }"/> <br/>
<spring:message code="float.procession" />: <jstl:out value="${paso.procession.title }"/><br/>
</fieldset>
<br />

<p><spring:message code="float.show.pictures"/></p>
<c:forEach items="${paso.pictures}" var="item">
	<img src="${item.url}"><br/>
	<br />
</c:forEach>
<acme:cancel url="float/brotherhood/list.do" code="float.cancel"/>
</security:authorize>