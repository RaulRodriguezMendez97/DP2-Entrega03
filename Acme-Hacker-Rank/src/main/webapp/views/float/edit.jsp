<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>



</head>
<body>

<security:authorize access="hasRole('BROTHERHOOD')">
<form:form action="float/brotherhood/edit.do" modelAttribute="paso">

	<form:hidden path="id" />
	<form:hidden path="version" />

 
 	<acme:textbox code="float.title" path="title"/>
	<br />
	<acme:textbox code="float.description" path="description"/>
	<br />
	<acme:select items="${processions}" itemLabel="title" code="float.procession" path="procession"/>
	<br />
	
	<acme:submit name="save" code="float.save"/>	
	<jstl:if test="${paso.id ne 0 }">
		<acme:submit name="delete" code="float.delete"/>
	</jstl:if>
	<acme:cancel url="float/brotherhood/list.do" code="float.cancel"/>
	<br />
</form:form>
</security:authorize>
</body>
</html>