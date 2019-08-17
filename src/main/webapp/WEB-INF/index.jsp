<p>Hello there.</p>
<!-- TODO : solve problem with utf-8 characters -->
<c:if test="${!empty sessionScope.userSession.pseudo}">
<p>You are logged in with following account : ${sessionScope.userSession.pseudo}</p>
</c:if>