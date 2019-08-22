
var urlRolesController = "";
var urlUsersController ="";
var selectRolesDOM = undefined;
var notificationTemplate = undefined;
var notificationContainerDOM = undefined;
var dataTableDOM = undefined;
var buttonUpdateTemplate = undefined;
var buttonDeleteTemplate = undefined;

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
        .addClass("alert-" + level);
    $(".allsafe-message",newNotificationDOM).html(message);
    notificationContainerDOM.prepend(newNotificationDOM);
};

var addUpdateButton = function(lineToUpdateDOM)
{
    var buttonContainer = $("td:nth-child(6)",lineToUpdateDOM);
    if( buttonContainer === undefined)
    {
        throw Error("addUpdateButton - 6th child of line to update not found.");
    } else
    {
        buttonContainer.html(buttonUpdateTemplate.clone(true).html());
    }
};

var addDeleteButton = function(lineToUpdateDOM)
{
    var buttonContainer = $("td:nth-child(7)",lineToUpdateDOM);
    if( buttonContainer === undefined)
    {
        throw Error("addUpdateButton - 7th child of line to update not found.");
    } else
    {
        buttonContainer.html(buttonDeleteTemplate.clone(true).html());
    }
};

var removeUpdateButton = function(lineToUpdateDOM)
{
    $("td:nth-child(6)",lineToUpdateDOM).empty();
};

var removeDeleteButton = function(lineToUpdateDOM)
{
    $("td:nth-child(7)",lineToUpdateDOM).empty();
};

var actualizesActionButtons = function(lineToUpdateDOM,actionList)
{
    if(actionList.contains("update"))
    {
        addUpdateButton(lineToUpdateDOM);
    } else
    {
        removeUpdateButton(lineToUpdateDOM)
    }
    if(actionList.contains("delete"))
    {
        addDeleteButton(lineToUpdateDOM);
    } else
    {
        removeDeleteButton(lineToUpdateDOM);
    }
};

$(document).ready(function() {
    urlUsersController = $("#urlUsers").val();
    urlRolesController = $("#urlRoles").val();
    selectRolesDOM = $("#role-select");
    notificationTemplate = $("#notificationTemplate").removeClass("allsafe-modele");
    buttonDeleteTemplate = $("#buttonDeleteTemplate").removeClass("allsafe-modele");
    buttonUpdateTemplate = $("#buttonUpdateTemplate").removeClass("allsafe-modele");
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
                            // TODO : actualizes update and delete fields for affected user
                            var userPseudo = response.update.affectedUser;
                            var message = "User <strong>" + userPseudo + "</strong> successfully updated";
                            addNotification("success",message);
                            var lineToUpdateDOM = $("td:contains(" + userPseudo + ")",dataTableDOM).parent();
                            var roleToUpdateDOM = $("td:nth-child(4)",lineToUpdateDOM);
                            roleToUpdateDOM.html(response.update.newRole);
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
            //TODO : insert selected user in modal's input delete
        }
    );

    // AJAX : delete user
    $("#allsafe-delete-user").click(
        function()
        {
            // TODO : make ajax request to delete user.
        }
    );



});