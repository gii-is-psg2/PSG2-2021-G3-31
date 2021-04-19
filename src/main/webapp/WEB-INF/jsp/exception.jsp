<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="error">

    <spring:url value="/resources/images/error2.png" var="petsImage"/>
    <img class="image-error" src="${petsImage}"/>

    <h2 class="error-title">Algo salió mal...</h2>

    <p>${exception.message}</p>

</petclinic:layout>
