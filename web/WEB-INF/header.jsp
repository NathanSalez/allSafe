<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>AllSafe</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="<c:url value="/inc/css/style.css" />">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script type="text/javascript" src="<c:url value="/inc/js/autoLogOff.js" />"></script>
    <script type="text/javascript" src="<c:url value="/inc/js/countConnectedUsers.js" />"></script>
</head>
<body>
<input type="hidden" value="<c:url value="/disconnect" />" id="urlDisconnect" />
<input type="hidden" value="<c:url value="/users/countLoggedUsers" />" id="urlCountUsers"/>

<%-- TODO : make a responsive navbar --%>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
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
