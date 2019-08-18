<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="as" uri="/WEB-INF/rightsTag.tld" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>AllSafe</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="<c:url value="/inc/css/style.css" />">
</head>
<body>
<input type="hidden" value="<c:url value="/disconnect" />" id="urlDisconnect" />
<input type="hidden" value="<c:url value="/users/" />" id="urlUsers"/>
<input type="hidden" value="<c:url value="/roles/" />" id="urlRoles"/>

<%-- TODO : make a responsive navbar --%>
<nav class="navbar navbar-expand-lg navbar-light bg-secondary">
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item <c:if test="${requestScope.view == 'home'}">active</c:if>">
                <a class="nav-link" href="<c:url value="/welcome" />">Home</a>
            </li>
            <c:choose>
                <c:when test="${empty sessionScope.userSession}">
                    <li class="nav-item <c:if test="${requestScope.view == 'register'}">active</c:if>">
                        <a class="nav-link" href="<c:url value="/accessible/register" />">Register</a>
                    </li>
                    <li class="nav-item <c:if test="${requestScope.view == 'login'}">active</c:if>">
                        <a class="nav-link" href="<c:url value="/accessible/login" />">Log in</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="nav-item <c:if test="${requestScope.view == 'usersTable'}">active</c:if> ">
                        <a class="nav-link" href="<c:url value="/restrictive/users/management" />">Users table</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/disconnect" />">Log out</a>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</nav>
