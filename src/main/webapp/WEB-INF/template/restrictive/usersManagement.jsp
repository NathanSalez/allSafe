<%--
later : the data table shows 20 users per part.
later : users can change part.
TODO : users can delete other user
TODO : users can search user by their name, role, register date, state
 --%>
<h1>Users Table</h1>
<div id="usersTable">
    <table class="table table-bordered">
        <thead class="thead-dark">
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Password</th>
                <th>Role</th>
                <th>Register date</th>
                <th>State</th>
                <as:hasRight executorRole="${sessionScope.userSession.role}" action="update">
                    <th>Update</th>
                </as:hasRight>
                <as:hasRight executorRole="${sessionScope.userSession.role}" action="delete">
                    <th>Delete</th>
                </as:hasRight>
            </tr>
        </thead>
        <tbody>
        <c:if test="${feedback!='ko'}">
            <c:forEach items="${requestScope.users}" var="user">
                <tr>
                    <td>${user.id}</td>
                    <td class="allsafe-pseudo">${user.pseudo}</td>
                    <td>***********</td>
                    <td class="allsafe-role">${user.role}</td>
                    <td>${user.registerDate}</td>
                    <td><span class="badge
                        <c:choose>
                            <c:when test="${user.logged}">
                                badge-primary">Logged</span>
                            </c:when>
                            <c:otherwise>
                                badge-secondary">Disconnected</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <as:hasRight executorRole="${sessionScope.userSession.role}" action="update" affectedRole="${user.role}">
                        <td><input type="button" class="btn-success allsafe-update" value="Update" data-toggle="modal" data-target="#updateModal" /></td>
                    </as:hasRight>
                    <as:hasRight executorRole="${sessionScope.userSession.role}" action="delete" affectedRole="${user.role}">
                        <td><input type="button" class="btn-danger" value="Delete" /></td>
                    </as:hasRight>
                </tr>
            </c:forEach>
        </c:if>
        </tbody>
        <tfoot>
            <tr class="table-info">
                <td></td>
                <td><input class="form-control" type="text" placeholder="Pseudo"/></td>
                <td></td>
                <td><input class="form-control" type="text" placeholder="Role"/></td>
                <td><input class="form-control" type="text" placeholder="Register date"/></td>
                <td></td>
                <as:hasRight executorRole="${sessionScope.userSession.role}" action="update">
                    <td></td>
                </as:hasRight>
                <as:hasRight executorRole="${sessionScope.userSession.role}" action="delete">
                    <td></td>
                </as:hasRight>
            </tr>
        </tfoot>
    </table>

    <c:import url="../../modal/updateModal.jsp"/>
</div>
<c:if test="${feedback=='ko'}">
    <p class="error alert alert-danger">
        Users table not found, please contact webmaster. Error message : ${form.errors['databaseError']}
    </p>
</c:if>