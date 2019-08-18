<h1>Register form</h1>
<form method="post" action="<c:url value="/accessible/register" />">
    <fieldset>
        <div class="form-group">
            <label for="pseudo">Pseudo</label>
            <input type="text" id="pseudo" class=" allsafe-input form-control" name="pseudo"
                   value="<c:out value="${newUser.pseudo}"/>" maxlength="19"/>
            <span class="error">${form.errors['pseudo']}</span>
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" class=" allsafe-input form-control" name="password" maxlength="19"/>
            <span class="error">${form.errors['password']}</span>
        </div>

        <div class="form-group">
            <label for="confirmationPassword">Confirm password</label>
            <input type="password" id="confirmationPassword" class=" allsafe-input form-control" name="confirmPassword"
                   maxlength="19"/>
            <span class="error">${form.errors['confirmPassword']}</span>
        </div>
    </fieldset>
    <input type="submit" class="btn btn-outline-dark" value="Register"/>
</form>
<c:if test="${feedback=='ko'}">
    <p class="error alert alert-danger">
        Sent data are not valid, please check errors above.
        ${form.errors['databaseError']}
    </p>
</c:if>