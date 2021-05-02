<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<petclinic:layout pageName="adoptions">
    
    <jsp:body>
	    <h2>Solicitar Adopcion</h2>
	    
	    <form:form modelAttribute="adoption" class="form-horizontal">
	        <input type="hidden" name="ownerUser" value="${ownerUser}"/>
	        <input type="hidden" name="possibleOwnerUser" value="${possibleOwnerUser}"/>
	        <petclinic:inputField name="description" label="Descripción" />
	        <div class="form-group">
	            <div>
	            	<button class="btn btn-default" type="submit">Enviar</button>
	            </div>
	        </div>
	    </form:form>
    </jsp:body>
</petclinic:layout>
