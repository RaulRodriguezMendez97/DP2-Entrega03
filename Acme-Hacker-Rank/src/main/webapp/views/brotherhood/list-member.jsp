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

<security:authorize access="hasRole('MEMBER')">

<display:table pagesize="5" name="brotherhoods" id="row"
requestURI="brotherhood/member/list-member.do" >

<display:column titleKey="brotherhood.title.list">
<jstl:out value="${row.title}"></jstl:out>
</display:column>

<display:column titleKey="brotherhood.email.list">
<jstl:out value="${row.email}"></jstl:out>
</display:column>


<display:column titleKey="brotherhood.phone.list">
<jstl:out value="${row.phone}"></jstl:out>
</display:column>

<display:column titleKey="brotherhood.address.list">
<jstl:out value="${row.address}"></jstl:out>
</display:column>

<display:column titleKey="brotherhood.pictures.list"> 

	<c:forEach items="${row.pictures}" var="item">
	<img src="${item.url}" width="130px" height="80px"><br/>
	</c:forEach>
	</display:column>


<display:column titleKey="brotherhood.establishmentDate.list">
<jstl:out value="${row.establishmentDate}"></jstl:out>
</display:column>
</display:table>
<jstl:if test="${belong eq true}">
<a href="brotherhood/member/list-member-belonged.do"><spring:message code="Brotherhood.belonged" /></a>
</jstl:if>
<jstl:if test="${belong eq false}">

<a href="brotherhood/member/list-member-belongs.do"><spring:message code="Brotherhood.back" /></a>

</jstl:if>



</security:authorize>
