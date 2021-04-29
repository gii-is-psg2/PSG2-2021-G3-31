<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="solicitarAdopcion">
    <h2>
        Solicitar adopción de <c:out value="${pet}"/>
   	</h2>
  
  	<c:out value=""/>
  
	<form:form modelAttribute="adoption" class="form-horizontal" id="add-clase-form">
    
        <div class="form-group has-feedback">
	        <div>
	        	
	        	<petclinic:inputField label="A continuación deberá detallar cómo cuidará de la mascota:" name="description"/>
	        </div>
    	    
      	</div>
        
        	
		<div class="form-group">
       		<button class="btn btn-default" type="submit">Solicitar adopción</button>
       		
       		<spring:url value="/adoptions/allAdoptionsList" var="volverUrl"></spring:url>
    		<a href="${fn:escapeXml(volverUrl)}" class="btn btn-default"><c:out value="Volver"/></a>
        </div>
    </form:form>
    
</petclinic:layout>