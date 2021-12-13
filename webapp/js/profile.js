$(document).ready(() => {
    SDK.post(endpoints.session, data = {Session: "validate"});
    let entrance = 'animate__zoomIn'
    let exit = 'animate__zoomOut'

    const userInfo = SDK.get(endpoints.profile);



})
