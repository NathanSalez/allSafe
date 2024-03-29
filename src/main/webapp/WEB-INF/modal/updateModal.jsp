<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                    <input type="text" class="form-control text-center allsafe-input" disabled id="pseudo" />
                </div>
                <div class="form-group">
                    <label for="role-select" class="col-form-label">Role</label>
                    <select id="role-select" class="custom-select text-center">
                    </select>
                </div>
                <input type="hidden" id="securityToken" value="<c:out value="${sessionScope.token}" />" />
            </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-primary" id="allsafe-update-user" data-dismiss="modal">Save changes</button>
            </div>
        </div>
    </div>
</div>