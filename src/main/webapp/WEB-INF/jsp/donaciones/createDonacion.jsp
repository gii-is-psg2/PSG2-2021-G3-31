<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="donaciones">
        <h2>
          Nueva Donacion
    	</h2>
    	
        <form:form modelAttribute="donacion" class="form-horizontal" id="add-clase-form">
        <div class="form-group has-feedback">
        	<petclinic:inputField label="Cantidad a donar" name="cantidadDonada"/>
        </div>
        <div class="form-group">
        <button class="btn btn-default" type="submit">Crear Donacion</button>
        </div>
        </form:form>
        
        
    
    
</petclinic:layout>