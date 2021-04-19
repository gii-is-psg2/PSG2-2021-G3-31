<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<petclinic:layout pageName="home">
    <h2 class="welcomeText">Bienvenido al proyecto Petclinic de PSG2-2021-G3-31 </h2>
    <div class="row">
        <div class="col-md-12">
            <spring:url value="/resources/images/pupi3.png" htmlEscape="true" var="petsImage"/>
            <img class="img-responsive imagencita" src="${petsImage}"/>
        </div>
    </div>
</petclinic:layout>
