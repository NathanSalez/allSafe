<%--
TODO : the data table shows 20 users per part.
TODO : users can change part.
TODO : create new type of tag, sees https://www.roseindia.net/jsp/body-tag-support-example.shtml and https://pastebin.com/cGxeF1qA for rights
TODO : users can update other user status
TODO : users can delete other user
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
                <c:if test="${sessionScope.userSession.role == 'ADMIN' || sessionScope.userSession.role == 'MODERATOR' }">
                    <th>Update</th>
                    <c:if test="${sessionScope.userSession.role == 'ADMIN'}">
                    <th>Delete</th>
                    </c:if>
                </c:if>
            </tr>
        </thead>
        <tbody>
        <c:if test="${feedback!='ko'}">
            <c:forEach items="${requestScope.users}" var="user">
                <tr class="<c:if test="${user.logged == true}">table-success</c:if>"  >
                    <td>${user.id}</td>
                    <td>${user.pseudo}</td>
                    <td>***********</td>
                    <td>${user.role}</td>
                    <td>${user.registerDate}</td>
                    <c:if test="${sessionScope.userSession.role == 'ADMIN' || ( sessionScope.userSession.role == 'MODERATOR' && user.role != 'ADMIN' )}">
                        <td><input type="button" class="btn-success" value="Update" data-toggle="modal" data-target="#updateModal" /></td>
                    </c:if>
                    <c:if test="${sessionScope.userSession.role == 'ADMIN'}">
                        <td><input type="button" class="btn-danger" value="Delete" /></td>
                    </c:if>
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
                <c:if test="${sessionScope.userSession.role == 'ADMIN' || sessionScope.userSession.role == 'MODERATOR' }">
                    <td></td>
                    <c:if test="${sessionScope.userSession.role == 'ADMIN'}">
                        <td></td>
                    </c:if>
                </c:if>
            </tr>
        </tfoot>
    </table>

    <!-- Modal -->
    <div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="updateModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Update an user</h5>
                </div>
                <div class="modal-body">
                    <form>
                        <div class="form-group">
                            <label for="pseudo" class="col-form-label" >Pseudo</label>
                            <!-- TODO : insert selected pseudo in modal's input -->
                            <input type="text" class="form-control allsafe-input" disabled id="pseudo">
                        </div>
                        <div class="form-group">
                            <label for="role-select" class="col-form-label">Role</label>
                            <select id="role-select" class="custom-select">
                                <c:forEach items="${requestScope.roles}" var="role">
                                <option value="${role}">${role.description}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal">Save changes</button>
                </div>
            </div>
        </div>
    </div>
</div>
<c:if test="${feedback=='ko'}">
    <p class="error alert alert-danger">
        Users table not found, please contact webmaster. Error message : ${form.errors['databaseError']}
    </p>
</c:if>