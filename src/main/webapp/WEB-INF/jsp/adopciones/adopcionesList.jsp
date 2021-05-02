<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="adoptions">
	 <table id="adoptionsTable" class="table table-striped">
        <thead>
        <tr>
        	<th>Nombre de la Mascota</th>
            <th>Tipo de Mascota</th>
            <th>Propietario</th>
            <th>Teléfono Propietario</th>
            <th>Ciudad</th> 
            <th>Solicitar Adopcion</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${adoptionPets}" var="adoptionPets">
            <tr>
           			<td>
						<c:out value="${adoptionPets.name}" /></td>
					<td>
						<c:out value="${adoptionPets.type}"/>
               		<td>
                		<c:out value="${adoptionPets.owner.firstName } ${adoptionPets.owner.lastName } " /></td>
                	<td>
                		<c:out value="${adoptionPets.owner.telephone}" /></td>
                	<td>
                		<c:out value="${adoptionPets.owner.city}" /></td>
					
					<td><c:if test="${adoptionPets.owner != possibleOwner}">
							<spring:url value="/adoptions/{petId}/adoptionForm" var="adoptionForm">
								<spring:param name="petId" value="${adoptionPets.id}" />
							</spring:url>
                    			<a href="${fn:escapeXml(adoptionForm)}"><c:out value="Adopta"/></a>
                    
						</c:if>
						<c:if test="${adoptionPets.owner == possibleOwner}">
							<spring:url value="/adoptions/{petId}/requestList" var="requestList">
								<spring:param name="petId" value="${adoptionPets.id}" />
							</spring:url>
                    			<a href="${fn:escapeXml(requestList)}"><c:out value="Ver listado de solicitudes"/></a>
                    
						</c:if>
				</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>