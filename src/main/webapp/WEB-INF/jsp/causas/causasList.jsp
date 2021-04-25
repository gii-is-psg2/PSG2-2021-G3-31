<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<petclinic:layout pageName="causas">
    <h2>Causas</h2>

    <table id="causasTable" class="table table-striped">
        <thead>
        <tr>
            <th>Nombre</th>
            <th>Organizacion</th>
            <th>Objetivo</th>
            <th>Recaudacion</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${causas}" var="causa">
            <tr>
                <td>
                    <c:out value="${causa.nombre}"/>
                </td>
                <td>
                    <c:out value="${causa.organizacion}"/>
                </td>
                <td>
                    <c:out value="${causa.objetivo}"/>
                </td>
                <td>
                    <c:out value="${causa.recaudacion}"/>
                </td>
                <td>
                	<spring:url value="/causas/{causaId}" var="causasUrl">
                	<spring:param name="causaId" value="${causa.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(causasUrl)}"><c:out value="Ver detalles de causa"/></a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <c:choose>
    <c:when test="${rol=='admin'}">
    <spring:url value="/causas/new" var="creacionCausaUrl">
    </spring:url>
    <a href="${fn:escapeXml(creacionCausaUrl)}" class="btn btn-default"><c:out value="Crear nueva causa"/></a>
    </c:when>
    <c:otherwise>
    </c:otherwise>
    </c:choose>
</petclinic:layout>