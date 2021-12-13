$(document).ready(() => {
    SDK.post(endpoints.session, data = {Session: "validate"});
    let entrance = 'animate__zoomIn'
    let exit = 'animate__zoomOut'

    SDK.get(endpoints.profile).then((result) => {
        console.log(result);
    });



})
