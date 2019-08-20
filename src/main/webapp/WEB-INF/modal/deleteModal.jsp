<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="deleteModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="deleteModalLabel">Delete an user</h5>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <label for="pseudoToDelete" class="col-form-label" >Pseudo</label>
                        <input type="text" class="form-control text-center allsafe-input" disabled id="pseudoToDelete" />
                    </div>
                    <p>Do you really want to delete this user?</p>
                    <input type="hidden" id="securityToken" value="<c:out value="${sessionScope.token}" />" />
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-primary" id="allsafe-delete-user" data-dismiss="modal">Yes</button>
            </div>
        </div>
    </div>
</div>
