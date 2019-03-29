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
<b><spring:message code="profile.action.2.name" /> </b> <jstl:out value="${actor.name }"/> <br/>
<b><spring:message code="profile.action.2.middleName" /></b> <jstl:out value="${actor.middleName }"/> <br/>
<b><spring:message code="profile.action.2.surname" /></b> <jstl:out value="${actor.surname }"/> <br/>
<b><spring:message code="profile.action.2.email" /></b> <jstl:out value="${actor.email }"/> <br/>
<b><spring:message code="profile.action.2.phone" /></b> <jstl:out value="${actor.phone }"/> <br/>
<b><spring:message code="profile.action.2.address" /></b> <jstl:out value="${actor.address }"/> <br/>

<security:authorize access="hasRole('BROTHERHOOD')">
<b><spring:message code="profile.brotherhood.title" /></b> <jstl:out value="${actor.title }"/> <br/>
<b><spring:message code="profile.brotherhood.establishmentDate" /></b> <jstl:out value="${actor.establishmentDate }"/> <br/>

<display:table name="${pictures}" id="row">
<display:column titleKey="profile.brotherhood.pictures" ><img src="${row.url}" width="130px" height="80px"></display:column>
</display:table>

</security:authorize>

<br/>
<acme:cancel url="welcome/index.do" code="administrator.cancel"/>


</security:authorize>

<security:authorize access="hasRole('ADMIN')">
<form action="profile/edit-administrator.do">
    <input type="submit" value="<spring:message code="profile.edit.profile" />" />
</form>
</security:authorize>


<security:authorize access="hasRole('BROTHERHOOD')">
<form action="profile/edit-brotherhood.do">
    <input type="submit" value="<spring:message code="profile.edit.profile" />" />
</form>
</security:authorize>

<security:authorize access="hasRole('MEMBER')">
<form action="profile/edit-member.do">
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


