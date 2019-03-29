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

<security:authorize access="hasRole('ADMIN')">

<display:table pagesize="5" name="positions" id="row"
requestURI="position/administrator/list.do" >

<display:column property="name" titleKey="position.name" />
<display:column property="spanishName" titleKey="position.spanishName" />

<display:column>
	<a href="position/administrator/edit.do?positionId=${row.id}"><spring:message code="position.edit" /></a>
</display:column>

</display:table>

<input type="button" name="cancel" value="<spring:message code="position.create" />"
			onclick="javascript: relativeRedir('position/administrator/create.do');" />

</security:authorize>