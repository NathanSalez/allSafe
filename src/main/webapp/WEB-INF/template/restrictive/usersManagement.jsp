<%--
TODO : users can search user by their name, role, register date, state with jquery datatzble
 --%>
<h1>Users Table</h1>
<%-- TODO : make a responsive notification container --%>
<div id="allsafe-notifications">
    <h2>Notifications</h2>
    <div id="allsafe-notification-container">
        <div id="notificationTemplate" class="alert alert-success allsafe-modele" role="alert">
            <button type="button" class="close" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <span class="allsafe-message"></span>
        </div>
    </div>
</div>
<div id="usersTable">
    <input type="button" id="buttonUpdateTemplate" class="btn-success allsafe-update allsafe-modele" value="Update"
           data-toggle="modal"
           data-target="#updateModal"/>
    <input type="button" id="buttonDeleteTemplate" class="btn-danger allsafe-delete allsafe-modele" value="Delete"
           data-toggle="modal"
           data-target="#deleteModal"/>
    <table id="dataTableUser" class="table table-bordered">
        <thead class="thead-dark">
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Password</th>
            <th>Role</th>
            <th>Register date</th>
            <th>State</th>
            <as:hasRight executorRole="${sessionScope.userSession.role.code}" action="update">
                <th>Update</th>
            </as:hasRight>
            <as:hasRight executorRole="${sessionScope.userSession.role.code}" action="delete">
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
                    <as:hasRight executorRole="${sessionScope.userSession.role.code}" action="update">
                        <td>
                            <as:hasRight executorRole="${sessionScope.userSession.role.code}" action="update"
                                         affectedRole="${user.role.code}">
                                <input type="button" class="btn-success allsafe-update" value="Update"
                                       data-toggle="modal"
                                       data-target="#updateModal"/>
                            </as:hasRight>
                        </td>
                    </as:hasRight>
                    <as:hasRight executorRole="${sessionScope.userSession.role.code}" action="delete">
                        <td>
                            <as:hasRight executorRole="${sessionScope.userSession.role.code}" action="delete"
                                         affectedRole="${user.role.code}">
                                <input type="button" class="btn-danger allsafe-delete" value="Delete"
                                       data-toggle="modal"
                                       data-target="#deleteModal"/>
                            </as:hasRight>
                        </td>
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
            <as:hasRight executorRole="${sessionScope.userSession.role.code}" action="update">
                <td></td>
            </as:hasRight>
            <as:hasRight executorRole="${sessionScope.userSession.role.code}" action="delete">
                <td></td>
            </as:hasRight>
        </tr>
        </tfoot>
    </table>

    <c:import url="../../modal/updateModal.jsp"/>
    <c:import url="../../modal/deleteModal.jsp"/>
</div>
<c:if test="${feedback=='ko'}">
    <p class="error alert alert-danger">
        Users table not found, please contact webmaster. Error message : ${form.errors['databaseError']}
    </p>
</c:if>
