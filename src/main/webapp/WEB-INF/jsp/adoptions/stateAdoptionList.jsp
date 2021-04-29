<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<petclinic:layout pageName="adopciones">
    <h2>Adopciones</h2>
        
	<table id="causasTable" class="table table-striped">
	     <thead>
	     <tr>
	     	<th>Nombre</th>
	     	<th>Tipo</th>
	        <th>Propietario</th>
	        <th>Estado</th>
	        <th></th>
	     </tr>
	     </thead>
	     <tbody>
	     <c:forEach items="${adoptions}" var="adoption">
	         <tr>
	             <td>
	                 <c:out value="${adoption.pet}"/>
	             </td>
	             <td>
	                 <c:out value="${adoption.pet.type}"/>
	             </td>
	             <td>
	                 <c:out value="${adoption.owner.firstName} ${adoption.owner.lastName}"/>
	             </td>
	             <td>
	                 <c:out value="${adoption.estadoAdopcion}"/>
	             </td>
	             <td>
                    <c:choose>
					    <c:when test="${possibleOwner.firstName == adoption.owner.firstName && 
					    				possibleOwner.lastName == adoption.owner.lastName}">
			 				<c:out value="${adoption.description}"/>
			 				<br>
			 				
			 				<spring:url value="/adoptions/accept/{adoptionId}" var="aceptarSolicitudUrl">
			 				<spring:param name="adoptionId" value="${adoption.id}"/> </spring:url>
			                <a href="${fn:escapeXml(aceptarSolicitudUrl)}"><c:out value="Aceptar"/></a>
			                
			                
			                <spring:url value="/adoptions/deny/{adoptionId}" var="rechazarSolicitudUrl">
			                <spring:param name="adoptionId" value="${adoption.id}"/> </spring:url>
			                <a href="${fn:escapeXml(rechazarSolicitudUrl)}"><c:out value="Rechazar"/></a>
			                
						</c:when>
					    <c:otherwise>
					    	<spring:url value="/adoptions/{petId}/applicationForm" var="solicitarAdopcionsUrl">
			               	<spring:param name="petId" value="${adoption.pet.id}"/>  
			                    </spring:url>
			                <a href="${fn:escapeXml(solicitarAdopcionsUrl)}"><c:out value="Solicitar adopción"/></a>
					    </c:otherwise>
				    </c:choose>
	             </td>
	             
	         </tr>
	     </c:forEach>
	</table>



</petclinic:layout>