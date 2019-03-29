

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('BROTHERHOOD')">

<display:table pagesize="5" name="linkRecords" id="row"
requestURI="linkRecord/brotherhood/list.do?historyId=${history.id}" >


<display:column>
	<a href="linkRecord/brotherhood/edit.do?linkRecordId=${row.id}&historyId=${history.id}" ><spring:message code="linkRecord.edit" /></a>
</display:column>
<display:column property="title" titleKey="linkRecord.title"/>
<display:column property="description" titleKey="linkRecord.description" />
<display:column property="brotherhood.title" titleKey="linkRecord.brotherhood" />
</display:table>

<a href="linkRecord/brotherhood/create.do?historyId=${history.id}" ><spring:message code="linkRecord.create" /></a>
</security:authorize>



<security:authorize access="isAnonymous()">

<display:table pagesize="5" name="linkRecords" id="row"
requestURI="linkRecord/list.do?historyId=${history.id}" >

<display:column property="title" titleKey="linkRecord.title"/>
<display:column property="description" titleKey="linkRecord.description" />
<display:column property="brotherhood.title" titleKey="linkRecord.brotherhood" />


</display:table>

<a href="history/list.do?idBrotherhood=${brotherhood.id }"><spring:message code="periodRecord.back" /></a>


</security:authorize>


