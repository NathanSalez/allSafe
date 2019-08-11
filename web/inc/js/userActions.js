$(document).ready(function() {


    // insert selected user in modal's input
    $(".allsafe-update").click(
        function()
        {
            var line = $(this).parent().parent();
            var pseudo = $(".allsafe-pseudo",line).html();
            console.log("Current user want to update " + pseudo);
            $("#pseudo").val(pseudo);
        }
    );

    $("#allsafe-update-user").click(
        function()
        {
            var data = {
                pseudo: $("#pseudo").val(),
                newRole: $("#role-select option:selected").val()
            };
            console.log("Sending AJAX request with following parameters :");
            console.log(data);
            // TODO : create response to ajax request for updating user.
        }
    )
});