<p>Hello there.</p>
<c:if test="${!empty sessionScope.userSession.pseudo}">
<p>You are logged in with following account : ${sessionScope.userSession.pseudo}</p>
</c:if>