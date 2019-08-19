
var urlRolesController = "";
var urlUsersController ="";
var selectRolesDOM = undefined;
var notificationTemplate = undefined;
var notificationContainerDOM = undefined;
var dataTableDOM = undefined;

/**
 *
 * @param level a string in the set {"success","warning","danger"}
 * @param message the string contained in the new notification. Can contain HTML text.
 */
var addNotification = function(level,message)
{
    var newNotificationDOM = notificationTemplate.clone(true);
    if( level === undefined || typeof level !== "string")
    {
        level = "danger";
    }
    if( message === undefined || typeof message !== "string")
    {
        message = "Warning ! No message found.";
    }
    newNotificationDOM
        .addClass("alert-" + level)
        .removeClass("allsafe-modele");
    $(".allsafe-message",newNotificationDOM).html(message);
    notificationContainerDOM.prepend(newNotificationDOM);
};

$(document).ready(function() {
    urlUsersController = $("#urlUsers").val();
    urlRolesController = $("#urlRoles").val();
    selectRolesDOM = $("#role-select");
    notificationTemplate = $("#notificationTemplate");
    dataTableDOM = $("#usersTable");

    $("#notificationTemplate button").click(
        function() // delete notification
        {
            $(this).parent().remove();
        }
    );
    notificationContainerDOM = $("#allsafe-notification-container");

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
                // TODO : add current role of the pseudo
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
                            var userPseudo = response.update.affectedUser;
                            var message = "User <strong>" + userPseudo + "</strong> successfully updated";
                            addNotification("success",message);
                            var lineToUpdateDOM = $("td:contains(" + userPseudo + ")",dataTableDOM).parent();
                            var fieldToUpdateDOM = $("td:nth-child(4)",lineToUpdateDOM);
                            fieldToUpdateDOM.html(response.update.newRole);
                        }
                        else
                        {
                            var message = "User <strong>Nathan</strong> not updated, check your rights.";
                            addNotification("warning",message);
                        }
                    }

                }
            )
        }
    )
});