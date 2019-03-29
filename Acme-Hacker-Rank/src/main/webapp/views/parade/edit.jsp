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

<security:authorize access="hasRole('BROTHERHOOD')">

<form:form action="parade/brotherhood/edit.do" modelAttribute="procession">
<form:hidden path="id"/>
<form:hidden path="version"/>

<acme:textbox code="procession.description" path="description"/>
<acme:textbox code="procession.title" path="title"/>
<jstl:if test="${fechaPasada eq 'fechaPasada'}">
	<b style="color:red;"><spring:message code="procession.PastDate"/></b><br>
</jstl:if>
<acme:textbox code="procession.moment" path="moment"/>
<acme:textbox code="procession.rows" path="maxRows" />
<acme:textbox code="procession.columns" path="maxColumns"/>
<spring:message code="procession.draftMode"/>
<form:select path="draftMode">
		<form:option value="1" ><spring:message code="procession.draftMode.Yes"/></form:option>	
		<form:option value="0"><spring:message code="procession.draftMode.No"/></form:option>	
	</form:select>
	<form:errors path="draftMode"/>
<br/>

<input type="submit" name="save" 
	value="<spring:message code="position.save" />" />
</form:form>

<input type="button" name="cancel" value="<spring:message code="request.cancel" />"
			onclick="javascript: relativeRedir('parade/brotherhood/list.do');" />
			
<!-- <script type="text/javascript">
$(document).ready(function(){
	$("#filas, #columnas").change(function(){
		var filas = document.getElementById("filas").value;
		var columnas = document.getElementById("columnas").value;
		var gente= filas * columnas;
		document.getElementById("prueba").innerHTML = gente;
		var matrix = [filas,columnas];
		
		document.getElementById("get-position").value = matrix;
	});
});
</script>-->

</security:authorize>

