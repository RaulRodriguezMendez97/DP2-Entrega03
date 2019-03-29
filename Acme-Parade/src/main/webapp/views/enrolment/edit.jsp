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

<security:authorize access="hasRole('MEMBER')">

<form:form action="enrolment/member/edit.do" modelAttribute="enrolment">

<jstl:if test="${not empty exception}">
		<p style="color:red"> <spring:message code="enrolment.error" /> </p>
</jstl:if>

<jstl:if test="${enrolment.id eq 0 }">
<form:hidden path="id"/>
<form:hidden path="version"/>

<jstl:if test="${language eq 'en' }">
<acme:select items="${positions }" itemLabel="name" code="enrolment.position" path="position"/>
</jstl:if>
<jstl:if test="${language eq 'es' }">
<acme:select items="${positions }" itemLabel="spanishName" code="enrolment.position" path="position"/>
</jstl:if>
<acme:select items="${brotherhoods }" itemLabel="title" code="enrolment.brotherhood" path="brotherhood"/>
</jstl:if>

<jstl:if test="${enrolment.id ne 0 and enrolment.status ne 3}">
<form:hidden path="id"/>
<form:hidden path="version"/>

<b><spring:message code="enrolment.moment" /></b>: <jstl:out value="${enrolment.moment}" ></jstl:out><br/>
<jstl:if test="${language eq 'en' }">
<b><spring:message code="enrolment.position" /></b>: <jstl:out value="${enrolment.position.name}"></jstl:out><br/>
</jstl:if>

<jstl:if test="${language eq 'en' }">
<b><spring:message code="enrolment.position" /></b>: <jstl:out value="${enrolment.position.spanishName}"></jstl:out><br/>
</jstl:if>

<b><spring:message code="enrolment.brotherhood" /></b>: <jstl:out value="${enrolment.brotherhood.title}"></jstl:out><br/>

<br/>

<form:label path="isOut"><spring:message code="enrolment.isOut" />:</form:label>
<jstl:if test="${enrolment.isOut eq 0 }">

	<form:select path="isOut">
		<form:option value="0" label="No" />	
		<form:option value="1" label="Yes" />	
	</form:select>
	<form:errors path="isOut"/>
</jstl:if>

<jstl:if test="${enrolment.isOut ne 0 }">

	<form:select path="isOut">
		<form:option value="1" label="Yes" />	
	</form:select>
	<form:errors path="isOut"/>
</jstl:if>
</jstl:if>

<jstl:if test="${enrolment.status eq 3 }">
<form:hidden path="id"/>
<form:hidden path="version"/>
<b><spring:message code="enrolment.moment" /></b>: <jstl:out value="${enrolment.moment}" ></jstl:out><br/>
<b><spring:message code="enrolment.position" /></b>: <jstl:out value="${enrolment.position.name}"></jstl:out><br/>
<b><spring:message code="enrolment.brotherhood" /></b>: <jstl:out value="${enrolment.brotherhood.title}"></jstl:out><br/>
<br/>
<form:label path="status"><spring:message code="enrolment.status" />:</form:label>

	<form:select path="status">
		<form:option value="1" label="Accepted" />	
		<form:option value="2" label="Canceled" />	
	</form:select>
	<form:errors path="status"/>

</jstl:if>

<br/>
<input type="submit" name="save" 
	value="<spring:message code="enrolment.save" />" />
	<input type="button" name="cancel" value="<spring:message code="enrolment.cancel" />"
			onclick="javascript: relativeRedir('enrolment/member/list.do');" />
</form:form>

</security:authorize>

<security:authorize access="hasRole('BROTHERHOOD')">

<form:form action="enrolment/brotherhood/edit.do" modelAttribute="enrolment">

<jstl:if test="${not empty exception}">
		<p style="color:red"> <spring:message code="enrolment.error" /> </p>
</jstl:if>
<form:hidden path="id"/>
<form:hidden path="version"/>

<b><spring:message code="enrolment.moment" /></b>: <jstl:out value="${enrolment.moment}" ></jstl:out><br/>
<b><spring:message code="enrolment.brotherhood" /></b>: <jstl:out value="${enrolment.brotherhood.title}"></jstl:out><br/>

<br/>

<jstl:if test="${language eq 'en' }">
<acme:selectWithoutNullOption items="${positions }" itemLabel="name" code="enrolment.position" path="position"/>
</jstl:if>
<jstl:if test="${language eq 'es' }">
<acme:selectWithoutNullOption items="${positions }" itemLabel="spanishName" code="enrolment.position" path="position"/>
</jstl:if>
<form:label path="status"><spring:message code="enrolment.status" />:</form:label>

	<form:select path="status">
		<form:option value="1" label="Accepted" />	
		<form:option value="2" label="Canceled" />	
		<form:option value="3" label="Response" />	
	</form:select>
	<form:errors path="status"/>



<br/>
<br/>
<input type="submit" name="save" 
	value="<spring:message code="enrolment.save" />" />
	
	<input type="button" name="cancel" value="<spring:message code="enrolment.cancel" />"
			onclick="javascript: relativeRedir('enrolment/brotherhood/list.do');" />
</form:form>




</security:authorize>
