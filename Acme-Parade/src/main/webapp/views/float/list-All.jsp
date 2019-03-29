

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<display:table pagesize="5" name="floats" id="row"
requestURI="float/list-All.do?idBrotherhood=${idBrotherhood }" >

<display:column property="title" titleKey="float.title"/>
<display:column property="description" titleKey="float.description" />

<display:column property="procession.title" titleKey="float.procession" />

<display:column titleKey="float.pictures"> 

	<c:forEach items="${row.pictures}" var="item">
	<img src="${item.url}" width="130px" height="80px"><br/>
	</c:forEach>
	</display:column>



</display:table>

<a href="brotherhood/list.do"><spring:message code="float.back" /></a>






