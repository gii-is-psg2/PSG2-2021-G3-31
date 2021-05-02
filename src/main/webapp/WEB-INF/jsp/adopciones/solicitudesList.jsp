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
        	<th>Candidato</th>
            <th>Descripción</th>
            <th>Estado</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requestList}" var="requestList">
            <tr>
           			<td>
						<c:out value="${requestList.possibleOwner.firstName} ${requestList.possibleOwner.lastName}" /></td>
					<td>
						<c:out value="${requestList.description}"/></td>
					<td>
						<c:out value="${requestList.adoptionStatus}"/></td>
					<td>
					<c:if test="${requestList.adoptionStatus == pendiente}">
					<spring:url value="/adoptions/{petId}/requestList/{adopcionId}/accept" var="acept">
								<spring:param name="petId" value="${requestList.pet.id}" />
								<spring:param name="adopcionId" value="${requestList.id}" />
							</spring:url>
                    			<a href="${fn:escapeXml(acept)}"><c:out value="Aceptar"/></a>
                    <spring:url value="/adoptions/{petId}/requestList/{adopcionId}/reject" var="reject">
								<spring:param name="petId" value="${requestList.pet.id}" />
								<spring:param name="adopcionId" value="${requestList.id}" />
							</spring:url>
                    			<a href="${fn:escapeXml(reject)}"><c:out value="Rechazar"/></a>
                    </c:if>
                    </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>