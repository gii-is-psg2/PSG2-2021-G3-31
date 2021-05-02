<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<petclinic:layout pageName="donaciones">
    <h2>Donaciones</h2>

    <table id="donacionesTable" class="table table-striped">
        <thead>
        <tr>
            <th>Fecha de Donacion</th>
            <th>Cantidad Donada</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${donaciones}" var="donacion">
            <tr>
                <td>
                    <c:out value="${donacion.fechaDonacion}"/>
                </td>
                <td>
                    <c:out value="${donacion.cantidadDonada}"/>
                </td>
                <td>
                	<spring:url value="/causas/{causaId}/donaciones/{donacionId}" var="donacionUrl">
                	<spring:param name="causaId" value="${donacion.causa.id}"/>
                	<spring:param name="donacionId" value="${donacion.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(donacionUrl)}"><c:out value="Ver detalles de donacion"/></a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <spring:url value="/causas" var="volverUrl">        	
    </spring:url>
    <a href="${fn:escapeXml(volverUrl)}" class="btn btn-default"><c:out value="Volver"/></a>
</petclinic:layout>