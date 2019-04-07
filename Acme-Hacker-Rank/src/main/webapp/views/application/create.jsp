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

<security:authorize access="hasRole('HACKER')">
<!-- <select id="select-prueba" name="brotherhoodId">
	<option value="-1">---</option>
    <jstl:forEach var="item" items="${brotherhoods}">		
		<option value="${item.id}">${item.title}</option>
    </jstl:forEach>
</select>  -->

<form:form action="application/hacker/edit.do" modelAttribute="application">
<form:hidden path="id"/>
<form:hidden path="version"/>

<acme:selectWithoutNullOption items="${curriculas}" itemLabel="id" code="application.curricula" path="curricula"/>

<input type="submit" name="save" 
	value="<spring:message code="application.create" />" />
	
<input type="button" name="cancel" value="<spring:message code="application.cancel" />"
			onclick="javascript: relativeRedir('application/hacker/list.do');" />
</form:form>

<!-- <script type="text/javascript">
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
</script> -->
</security:authorize>

