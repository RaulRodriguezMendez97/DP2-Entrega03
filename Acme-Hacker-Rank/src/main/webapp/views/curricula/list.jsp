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


<security:authorize access="hasRole('HACKER')">
<display:table pagesize="5" name="curriculas" id="row"
requestURI="curricula/hacker/list.do?idHacker=${row.hacker.id}" >


<display:column titleKey="curricula.hacker.list">
<jstl:out value="${row.hacker.name}"></jstl:out>
</display:column>
<display:column titleKey="curricula.isCopy">
<jstl:out value="${row.hacker.name}"></jstl:out>
</display:column>

<display:column titleKey="curricula.personalData">
<a href="personalData/hacker/list.do?curiculaId=${row.id}"><spring:message code="curricula.personalData.list" /></a>
</display:column>


<display:column titleKey="curricula.educationData"> 
<a href="educationData/hacker/list.do?curriculaId=${row.id}"><spring:message code="curricula.educationData.list" /></a>
</display:column>


<display:column titleKey="curricula.positionData"> 
<a href="positionData/hacker/list.do?curriculaId=${row.id}"><spring:message code="curricula.positionData.list" /></a>
</display:column>


<display:column titleKey="curricula.miscellaneousData"> 
<a href="miscellaneousData/hacker/list.do?curriculaId=${row.id}"><spring:message code="curricula.miscellaneousData.list" /></a>
</display:column>

</display:table>

<input type="button" name="create" value="<spring:message code="curricula.create" />"
			onclick="javascript: relativeRedir('curricula/hacker/create.do');" /><br>

</security:authorize>





