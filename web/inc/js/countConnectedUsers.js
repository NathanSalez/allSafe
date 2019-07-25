/*
* countConnectedUsers.js
*
* every 30 seconds, does an ajax request to get number of connected users.
*/
var urlCountUsers = "";
var displayBlock = undefined;

var getNbLoggedUsers = function()
{
    $.ajax(
        {
            url: urlCountUsers,
            dataType : "json",
            success : function(response)
            {
                if( response.feedback === "ok")
                {
                    var contentParagraph = response.nbConnectedUsers + " connected users.";
                    displayBlock.html(contentParagraph);
                }
                else
                {
                    alert("The server is not able to count connected users.");
                    console.log(response);
                }
            }
        }
    );
};

$(document).ready(function() {
    urlCountUsers = $("#urlCountUsers").val();
    displayBlock = $("#nbConnectedUsers");

    setInterval(
        getNbLoggedUsers,
        10000
    );

    getNbLoggedUsers();
});