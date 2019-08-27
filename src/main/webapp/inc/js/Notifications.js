var notificationTemplate = undefined;
var notificationContainerDOM = undefined;
var buttonUpdateTemplate = undefined;
var buttonDeleteTemplate = undefined;
var updateButtonNumberColumn = 7;
var deleteButtonNumberColumn = 8;

/**
 * Add a notification.
 * @param level a string in the set {"success","warning","danger"}
 * @param message the string contained in the new notification. Can contain HTML text.
 */
var addNotification = function(level,message)
{
    var newNotificationDOM = notificationTemplate.clone(true).removeClass("allsafe-modele");
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

/**
 * Update a line of data user table.
 * @param lineToUpdateDOM The line to actualize.
 * @param actionList a string array that may contain following values : "update", "delete".
 */
var actualizesActionButtons = function(lineToUpdateDOM,actionList)
{
    var addUpdateButton = function(lineToUpdateDOM)
    {
        var buttonContainer = $("td:nth-child(" + updateButtonNumberColumn + ")",lineToUpdateDOM);
        if( buttonContainer === undefined)
        {
            throw Error("addUpdateButton - " + updateButtonNumberColumn +"th child of line to update not found.");
        } else
        {
            if( !buttonContainer.html().includes("<input"))
                buttonContainer.append(buttonUpdateTemplate.clone(true).removeClass("allsafe-modele"));
        }
    };

    var addDeleteButton = function(lineToUpdateDOM)
    {
        var buttonContainer = $("td:nth-child(" + deleteButtonNumberColumn + ")",lineToUpdateDOM);
        if( buttonContainer === undefined)
        {
            throw Error("addUpdateButton - " + deleteButtonNumberColumn + "th child of line to update not found.");
        } else
        {
            if( !buttonContainer.html().includes("<input"))
                buttonContainer.append(buttonDeleteTemplate.clone(true).removeClass("allsafe-modele"));
        }
    };

    var removeUpdateButton = function(lineToUpdateDOM)
    {
        $("td:nth-child(" + updateButtonNumberColumn + ")",lineToUpdateDOM).empty();
    };

    var removeDeleteButton = function(lineToUpdateDOM)
    {
        $("td:nth-child(" + deleteButtonNumberColumn + ")",lineToUpdateDOM).empty();
    };

    if(actionList.includes("update"))
    {
        addUpdateButton(lineToUpdateDOM);
    } else
    {
        removeUpdateButton(lineToUpdateDOM)
    }
    if(actionList.includes("delete"))
    {
        addDeleteButton(lineToUpdateDOM);
    } else
    {
        removeDeleteButton(lineToUpdateDOM);
    }
};

$(document).ready(function()
{
    notificationTemplate = $("#notificationTemplate");
    buttonDeleteTemplate = $("#buttonDeleteTemplate");
    buttonUpdateTemplate = $("#buttonUpdateTemplate");
    $("#notificationTemplate button").click(
        function() // delete notification
        {
            $(this).parent().remove();
        }
    );
    notificationContainerDOM = $("#allsafe-notification-container");
    console.log("Notifications.js downloaded.");
});