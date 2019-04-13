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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>



<security:authorize access="isAuthenticated()">

<img src="<jstl:out value='${actor.photo }'/> ">  <br/>
<br/>
<fieldset>
	<legend><spring:message code="profile.personalDatas" /></legend>
<b><spring:message code="profile.action.2.name" /> </b> <jstl:out value="${actor.name }"/> <br/>
<b><spring:message code="profile.action.2.surname" /></b> <jstl:out value="${actor.surnames}"/> <br/>
<b><spring:message code="profile.action.2.vatNumber" /></b> <jstl:out value="${actor.vatNumber }"/> <br/>
<b><spring:message code="profile.action.2.email" /></b> <jstl:out value="${actor.email }"/> <br/>
<b><spring:message code="profile.action.2.phone" /></b> <jstl:out value="${actor.phone }"/> <br/>
<b><spring:message code="profile.action.2.address" /></b> <jstl:out value="${actor.address }"/> <br/>

<security:authorize access="hasRole('COMPANY')">
<b><spring:message code="profile.company.nameCompany" /></b> <jstl:out value="${actor.nameCompany }"/> <br/>
</security:authorize>
</fieldset>

<fieldset>
	 <legend><spring:message code="creditCard.Data" /></legend>
	 <b><spring:message code="profile.creditCard.brandName" /> </b> <jstl:out value="${creditCard.brandName}"/> <br/>
	 <b><spring:message code="profile.creditCard.holderName" /> </b> <jstl:out value="${creditCard.holderName}"/> <br/>
	 <b><spring:message code="profile.creditCard.number" /> </b> <jstl:out value="${creditCard.number}"/> <br/>
	 <b><spring:message code="profile.creditCard.expirationMonth" /> </b> <jstl:out value="${creditCard.expirationMonth}"/> <br/>
	 <b><spring:message code="profile.creditCard.expirationYear" /> </b> <jstl:out value="${creditCard.expirationYear}"/> <br/>
	 <b><spring:message code="profile.creditCard.CW" /> </b> <jstl:out value="${creditCard.CW}"/> <br/>
	 
	 <br />
	</fieldset>




<acme:cancel url="welcome/index.do" code="action1.volver"/>


</security:authorize>

<security:authorize access="hasRole('ADMIN')">
<form action="profile/edit-administrator.do">
    <input type="submit" value="<spring:message code="profile.edit.profile" />" />
</form>
</security:authorize>


<security:authorize access="hasRole('COMPANY')">
<form action="profile/edit-company.do">
    <input type="submit" value="<spring:message code="profile.edit.profile" />" />
</form>
</security:authorize>

<security:authorize access="hasRole('HACKER')">
<form action="profile/edit-hacker.do">
    <input type="submit" value="<spring:message code="profile.edit.profile" />" />
</form>
</security:authorize>


<security:authorize access="isAuthenticated()">
	<button type="button" onclick="exportFunction()"><spring:message code="profile.edit.export" /></button>
	<h2 id="exported"></h2>
	<p id="json" style=""></p>
	
	<script type="text/javascript">
	function exportFunction() {
		$.ajax({
			type:'GET',
			url:'export/json.do',
			success: function(res) {
				document.getElementById("json").innerHTML =res;
				document.getElementById("exported").innerHTML = "JSON";
		    }
		});
	}
</script>
</security:authorize>


