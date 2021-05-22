<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>

<petclinic:layout pageName="healthInfo">
	<div class="health">
	 <c:choose>
    <c:when test="${health == 'UP'}">
     	<button type="button" class="btn btn-success">
			${health}
		</button>
    </c:when>
    <c:otherwise>
		<button type="button" class="btn btn-danger">
			${health}
		</button>
    </c:otherwise>
    </c:choose>
	</div></br></br>
    <h2>El botón situado en la zona posterior de la pantalla indica el estado de la aplicación, si este es verde y se visualiza UP la aplicación está en perfecto funcionamiento, si por el contrario el botón es rojo y se visualiza DOWN la aplicación se encuentra caída en estos momentos.</h2>
</petclinic:layout>