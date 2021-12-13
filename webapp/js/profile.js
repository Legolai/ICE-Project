$(document).ready(() => {
    SDK.post(endpoints.session, data = {Session: "validate"});
    let entrance = 'animate__zoomIn'
    let exit = 'animate__zoomOut'

    SDK.get(endpoints.profile).then((result) => {
        const user = result.user;
        $("#firstname").attr("placeholder", user.firstname);
        $("#surname").attr("placeholder", user.surname);
        $("#username").attr("placeholder", user.username);
        $("#email").attr("placeholder", user.email);
    });


    $("#cancel").click(() => {
        $(".input").val('');
    })

    $("#profile-edit").submit((event)=>{
        event.preventDefault();
        const data = $("#profile-edit").serializeArray();
        for (const set of data) {
            if(set.value !== "")
                SDK.post(endpoints.updateProfile, {"key": set.name, "value": set.value})
        }
    })



})
