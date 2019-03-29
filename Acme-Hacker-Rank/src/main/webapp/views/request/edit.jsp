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
<spring:message code="request.brotherhood"/>
<select id="select-prueba" name="brotherhoodId">
	<option value="-1">---</option>
    <jstl:forEach var="item" items="${brotherhoods}">		
		<option value="${item.id}">${item.title}</option>
    </jstl:forEach>
</select>

<form:form action=" request/member/edit.do" modelAttribute="request">
<form:hidden path="id"/>
<form:hidden path="version"/>

<form:label path="procession">
<spring:message code="request.procession"/>:
</form:label>
<form:select id="rellenarme" path="procession">
</form:select>
<jstl:if test="${exception eq 'e'}">
	<b style="color:red;"><spring:message code="procession.NotValid"/></b><br>
</jstl:if>
<br/>
<input type="submit" name="save" 
	value="<spring:message code="position.save" />" />
	

<input type="button" name="cancel" value="<spring:message code="request.cancel" />"
			onclick="javascript: relativeRedir('request/member/list.do');" />
</form:form>

<script type="text/javascript">
	$(document).ready(function(){
		$('#select-prueba').change(function(){
			var brotherhoodId = $('#select-prueba option:selected').attr('value');
			$.ajax({
				type:'GET',
				url:'parade/member/list.do?brotherhoodId='+brotherhoodId,
				success: function(res) {
					var procesiones = res.split(';');
					var i;
					var injectar = "";
					for (i = 0; i < procesiones.length; i++) { 
						p = procesiones[i].split(':');
						injectar += '<option value="'+p[1]+'">'+p[0]+'</option>';
					}
					document.getElementById("rellenarme").innerHTML =injectar;
			       /*  console.log(injectar);
			        alert(injectar); */
			    }
			});
		});
	});
</script>
</security:authorize>


<security:authorize access="hasRole('BROTHERHOOD')">

<form:form action="request/brotherhood/edit.do?status=${status}" modelAttribute="request">
<form:hidden path="id"/>
<form:hidden path="version"/>

<jstl:if test="${exception eq 0}">
	<b style="color:red"><spring:message code="exception.rowAndColumn"/></b>
</jstl:if>
<jstl:if test="${exception eq 2}">
	<b style="color:red"><spring:message code="exception.description.request"/></b>
</jstl:if>
<jstl:if test="${status eq 0}">
	<acme:textbox code="request.row" path="row"/>
	<acme:textbox code="request.columna" path="columna"/>
</jstl:if>
<p>**<spring:message code="position.full-procession" /></p>
<jstl:if test="${status eq 2}">
	<acme:textbox code="request.description" path="description"/>
</jstl:if>
<input type="submit" name="save" 
	value="<spring:message code="position.save" />" />
	

<input type="button" name="cancel" value="<spring:message code="request.cancel" />"
			onclick="javascript: relativeRedir('parade/brotherhood/list.do');" />
</form:form>

</security:authorize>

