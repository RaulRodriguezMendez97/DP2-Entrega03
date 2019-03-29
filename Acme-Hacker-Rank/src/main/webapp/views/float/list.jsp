

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>



<display:table pagesize="5" name="floats" id="row"
requestURI="float/brotherhood/list.do" >


<display:column>
	<a href="float/brotherhood/edit.do?floatId=${row.id}" ><spring:message code="float.edit" /></a>
</display:column>
<display:column property="title" titleKey="float.title"/>
<display:column property="description" titleKey="float.description" />
<display:column property="procession.title" titleKey="float.procession" />

<display:column>
	<a href="float/brotherhood/show.do?floatId=${row.id}" ><spring:message code="float.show" /></a>
</display:column>
<display:column>
	<a href="picture/brotherhood/picturesFloat.do?floatId=${row.id}" ><spring:message code="float.pictures.show" /></a>
</display:column>

</display:table>

<form action="float/brotherhood/create.do">
	<acme:submit name="create" code="float.create"/>
</form>






