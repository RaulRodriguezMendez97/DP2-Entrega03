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


<security:authorize access="hasRole('BROTHERHOOD')">
<display:table pagesize="5" name="histories" id="row"
requestURI="history/brotherhood/list.do?idBrotherhood=${row.brotherhood.id}" >


<display:column titleKey="history.brotherhood.list">
<jstl:out value="${row.brotherhood.title}"></jstl:out>
</display:column>

<display:column titleKey="history.inceptionRecord.list">

<a href="inception-record/brotherhood/show.do?historyId=${row.id}"><spring:message code="history.inceptionRecord.list" /></a>

</display:column>


<display:column titleKey="history.periodRecord.list"> 

<a href="periodRecord/brotherhood/list.do?historyId=${row.id}"><spring:message code="history.periodRecord.list" /></a>

	</display:column>


<display:column titleKey="history.legalRecord.list"> 

	<a href="legalRecord/brotherhood/list.do?historyId=${row.id}"><spring:message code="history.legalRecord.list" /></a>
	
	</display:column>


<display:column titleKey="history.linkRecord.list"> 

	<a href="linkRecord/brotherhood/list.do?historyId=${row.id}"><spring:message code="history.linkRecord.list" /></a>

	</display:column>
	
	
	<display:column titleKey="history.miscellaneousRecord.list"> 
	
	<a href="miscellaneousRecord/brotherhood/list.do?historyId=${row.id}"><spring:message code="history.miscellaneousRecord.list" /></a>

	</display:column>



</display:table>

<input type="button" name="create" value="<spring:message code="inceptionRecord.create" />"
			onclick="javascript: relativeRedir('inception-record/brotherhood/create.do');" /><br>

</security:authorize>

<security:authorize access="isAnonymous()">
<display:table pagesize="5" name="histories" id="row"
requestURI="history/list.do?idBrotherhood=${row.brotherhood.id}" >

<display:column titleKey="history.brotherhood.list">
<jstl:out value="${row.brotherhood.title}"></jstl:out>
</display:column>

<display:column titleKey="history.inceptionRecord.list">


<a href="inception-record/show.do?historyId=${row.id}"><spring:message code="history.inceptionRecord.list" /></a>


</display:column>


<display:column titleKey="history.periodRecord.list"> 

<a href="periodRecord/list.do?historyId=${row.id}"><spring:message code="history.periodRecord.list" /></a>

	</display:column>


<display:column titleKey="history.legalRecord.list"> 

	<a href="legalRecord/list.do?historyId=${row.id}"><spring:message code="history.legalRecord.list" /></a>
	
	</display:column>
	

<display:column titleKey="history.linkRecord.list"> 

	<a href="linkRecord/list.do?historyId=${row.id}"><spring:message code="history.linkRecord.list" /></a>

	</display:column>
	
	<display:column titleKey="history.miscellaneousRecord.list"> 
	
	<a href="miscellaneousRecord/list.do?historyId=${row.id}"><spring:message code="history.miscellaneousRecord.list" /></a>


	</display:column>
	
	</display:table>


<a href="brotherhood/list.do"><spring:message code="History.back" /></a>
</security:authorize>




