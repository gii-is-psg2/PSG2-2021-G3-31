<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="donaciones">

    <h2>Informacion sobre donacion</h2>

    <table class="table table-striped">
        <tr>
            <th>Fecha de la donacion</th>
            <td><b><c:out value="${donacion.fechaDonacion}"/></b></td>
        </tr>
        <tr>
            <th>Cantidad Donada</th>
            <td><b><c:out value="${donacion.cantidadDonada}"/></b></td>
        </tr>
        <tr>
            <th>Donante</th>
            <td><b><c:out value="${donacion.donante.firstName} ${donacion.donante.lastName}"/></b></td>
    </table>
    
    <spring:url value="/causas/{causaId}/donaciones" var="volverUrl">
     <spring:param name="causaId" value="${donacion.causa.id}"/>        	
    </spring:url>
    <a href="${fn:escapeXml(volverUrl)}" class="btn btn-default"><c:out value="Volver"/></a>
    


</petclinic:layout>