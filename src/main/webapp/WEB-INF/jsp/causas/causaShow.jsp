<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="causas">

    <h2>Informacion sobre causa</h2>

    <table class="table table-striped">
        <tr>
            <th>Nombre</th>
            <td><b><c:out value="${causa.nombre}"/></b></td>
        </tr>
        <tr>
            <th>Organizacion</th>
            <td><c:out value="${causa.organizacion}"/></td>
        </tr>
        <tr>
            <th>Cantidad objetivo</th>
            <td><c:out value="${causa.objetivo}"/></td>
        </tr>
        <tr>
            <th>Recaudacion</th>
            <td><c:out value="${causa.recaudacion}"/></td>
        </tr>
        <tr>
            <th>Descripcion</th>
            <td><c:out value="${causa.descr}"/></td>
        </tr>
    </table>
    
    <spring:url value="/causas" var="volverUrl">        	
    </spring:url>
    <a href="${fn:escapeXml(volverUrl)}" class="btn btn-default"><c:out value="Volver"/></a>
    
    <spring:url value="/causas/{causaId}/donaciones" var="donacionUrl">
    <spring:param name="causaId" value="${causa.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(donacionUrl)}" class="btn btn-default"><c:out value="Ver listado de donaciones"/></a>
    
    <c:choose>
    <c:when test="${rol=='owner'}">
    <spring:url value="/causas/{causaId}/donaciones/new" var="creacionDonacionUrl">
    <spring:param name="causaId" value="${causa.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(creacionDonacionUrl)}" class="btn btn-default"><c:out value="Crear nueva donacion"/></a>
	 </c:when>
    <c:otherwise>
    </c:otherwise>
    </c:choose>

</petclinic:layout>