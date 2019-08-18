
var urlRolesController = "";
var urlUsersController ="";
var selectRolesDOM = undefined;

$(document).ready(function() {
    urlUsersController = $("#urlUsers").val();
    urlRolesController = $("#urlRoles").val();
    selectRolesDOM = $("#role-select");

    // AJAX : insert selected user in modal's input and select new possible roles.
    $(".allsafe-update").click(
        function()
        {
            selectRolesDOM.empty();
            var line = $(this).parent().parent();
            var pseudo = $(".allsafe-pseudo",line).html();
            console.log("Current user want to update " + pseudo);
            $("#pseudo").val(pseudo);
            console.log("Getting new roles for user " + pseudo);
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
                            alert("The server is not able to get new possible roles. Please check the logs.");
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
                        // TODO : create response to ajax request for updating user (frontend side).
                    }

                }
            )
        }
    )
});