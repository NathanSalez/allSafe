
var urlRolesController = "";
var urlUsersController ="";
var selectRolesDOM = undefined;
var dataTableDOM = undefined;

$(document).ready(function() {
    urlUsersController = $("#urlUsers").val();
    urlRolesController = $("#urlRoles").val();
    selectRolesDOM = $("#role-select");
    dataTableDOM = $("#dataTableUser");
    //dataTableDOM.DataTable();

    // AJAX : insert selected user in modal's input and select new possible roles.
    $(".allsafe-update").click(
        function()
        {
            selectRolesDOM.empty();
            var line = $(this).parent().parent();
            var pseudo = $(".allsafe-pseudo",line).html();
            var currentRole = $(".allsafe-role",line).html();
            $("#pseudo").val(pseudo);
            $("#currentRole").val(currentRole);
            var input = {
                "affectedRole" : $(".allsafe-role",line).html()
            };
            $.ajax(
                {
                    url: urlRolesController + "?action=getPossibleNewRoles",
                    dataType: "json",
                    data: input,
                    success: function (response) {
                        if( response.feedback === "ok")
                        {
                            for( var i=0; i < response.newPossibleRoles.length ;i++)
                            {
                                var newRole = response.newPossibleRoles[i];
                                var currentOption = new Option(newRole.description,newRole.name);
                                selectRolesDOM.append(currentOption);
                            }
                        }
                        else
                        {
                            addNotification("danger","The server is not able to get new possible roles. Please check the logs.");
                            console.log(response);
                        }
                    }
                });
        }
    );

    // AJAX : update user status
    $("#allsafe-update-user").click(
        function()
        {
            var input = {
                pseudo: $("#pseudo").val(),
                currentRole : $("#currentRole").val(),
                newRole: $("#role-select option:selected").val(),
                token : $("#securityToken").val(),
                action: "updateUser"
            };
            $.ajax(
                {
                    url:urlUsersController,
                    method: "post",
                    dataType:"json",
                    data:input,
                    success : function(response)
                    {
                        console.log(response);
                        if( response.feedback === "ok")
                        {
                            // TODO : if current session user is updated, refresh page.
                            var message = "User <strong>" + input.pseudo + "</strong> successfully updated";
                            addNotification("success",message);
                            var lineToUpdateDOM = $("td:contains(" + input.pseudo + ")",dataTableDOM).parent();
                            var roleToUpdateDOM = $("td:nth-child(4)",lineToUpdateDOM);
                            roleToUpdateDOM.html(input.newRole);
                            actualizesActionButtons(lineToUpdateDOM,response.possibleActionsOnUser);
                        }
                        else
                        {
                            addNotification("warning",response.error);
                        }
                    }

                }
            )
        }
    );

    // AJAX : insert selected user in modal's input delete
    $(".allsafe-delete").click(
        function()
        {
            var line = $(this).parent().parent();
            var pseudo = $(".allsafe-pseudo",line).html();
            $("#pseudoToDelete").val(pseudo);
        }
    );

    // AJAX : delete user
    $("#allsafe-delete-user").click(
        function()
        {
            var input = {
                pseudo: $("#pseudoToDelete").val(),
                token : $("#securityToken").val(),
                action: "deleteUser"
            };
            $.ajax(
                {
                    url:urlUsersController,
                    method: "post",
                    dataType:"json",
                    data:input,
                    success : function(response)
                    {
                        console.log(response);
                        response =
                            {
                                "feedback" : "ok"
                            };
                        if( response.feedback === "ok")
                        {
                            addNotification("success","User <strong>" + input.pseudo + "</strong> deleted.");
                            var lineToDeleteDOM = $("td:contains(" +input.pseudo + ")",dataTableDOM).parent();
                            lineToDeleteDOM.remove();

                        } else
                        {
                            addNotification("warning", response.error);
                        }
                    }

                }
            )
        }
    );
    console.log("userActions.js downloaded.");
});