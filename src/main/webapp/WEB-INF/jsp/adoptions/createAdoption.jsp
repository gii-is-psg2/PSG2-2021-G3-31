<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="crearSolicitudAdopcion">
        <h2>
         	Crear solicitud de adopción de mascota
    	</h2>
    	
        <form:form modelAttribute="adopcion" class="form-horizontal" id="add-clase-form">
	        <div class="form-group has-feedback">
	        	<div>
	            	<label class="control-label">¿Qué mascota desea dar en adopción? </label>
	            </div>
      		</div>
      		
			<div class="form-group">
	       		<button class="btn btn-default" type="submit">Crear solicitud</button>
	       		
	       		<spring:url value="/" var="volverUrl"></spring:url>
    			<a href="${fn:escapeXml(volverUrl)}" class="btn btn-default"><c:out value="Volver"/></a>
	        </div>
        </form:form>
        
        
    
    
</petclinic:layout>