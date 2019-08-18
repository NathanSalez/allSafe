<h1>Log in form</h1>
<form method="post" action="<c:url value="/accessible/login" />">
    <fieldset>
        <div class="form-group">
            <label for="pseudo">Pseudo</label>
            <input type="text" id="pseudo" name="pseudo" class="allsafe-input form-control"
                   value="<c:if test="${input != null}"><c:out value="${input.pseudo}"/></c:if>" maxlength="19"/>
            <span class="error">${form.errors['pseudo']}</span>
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" class=" allsafe-input form-control" name="password" maxlength="19"/>
            <span class="error">${form.errors['password']}</span>
        </div>

        <div class="custom-control custom-checkbox">
            <input type="checkbox" class="custom-control-input" id="stayLogged" name="stayLogged">
            <label class="custom-control-label" for="stayLogged">Stay logged</label>
        </div>
    </fieldset>
    <input type="submit" class="btn btn-outline-dark" value="Login"/>
</form>
<c:if test="${feedback=='ko'}">
    <p class="error alert alert-danger">
        Couple pseudo/password invalid. ${form.errors['databaseError']}
    </p>
</c:if>