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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<security:authorize access="hasRole('BROTHERHOOD')">

<display:table pagesize="5" name="pictures" id="row"
requestURI="picture/brotherhood/picturesPeriodRecord.do?periodRecord=${periodRecord.id }" >
<display:column>
	<a href="picture/brotherhood/editPicturePeriodRecord.do?pictureId=${row.id}&periodRecordId=${periodRecord.id}" ><spring:message code="picture.edit" /></a>
</display:column>
<display:column titleKey="picture.url"> <img src="${row.url}" width="130px" height="80px">  </display:column>
</display:table>
<input type="button" name="cancel" value="<spring:message code="periodRecord.create" />"
			onclick="javascript: relativeRedir('picture/brotherhood/createPicturePeriodRecord.do?periodRecordId=${periodRecord.id}');" />
	
	<input type="button" name="cancel" value="<spring:message code="periodRecord.cancel" />"
			onclick="javascript: relativeRedir('periodRecord/brotherhood/show.do?periodRecordId=${periodRecord.id}');" />

</security:authorize>





