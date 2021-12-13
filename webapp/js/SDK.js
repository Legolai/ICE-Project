const baseURL = "http://localhost:8888/api";
const endpoints = {
    login: "/login",
    signup: "/signUp",
    getall: "/getAll",
    session: "/session",
    profile: "/profile"
}

const SDK = {
    post: (endpoint, data) => {
        $.ajax( baseURL+endpoint, {
            method: "POST",
            data: JSON.stringify(data),
            xhrFields: { withCredentials: true },
            crossDomain: true},
        )
            .then((result,statusText, xhr) => {
                console.log(".done")
                switch (endpoint) {
                    case endpoints.session:
                        console.log("session is valid")
                        if (result.Session === "deleted" ) {
                            $("#selector-panel").toggleClass('animate__bounceInRight animate__bounceOutLeft')
                            $("#favourites").toggleClass('animate__bounceInRight animate__bounceOutRight')
                            $("body nav").toggleClass('animate__bounceOutUp animate__bounceInDown')
                                .on('animationend webkitAnimationEnd', () => {
                                    window.location.replace("login.html")
                                })
                        }
                        break;
                    default:
                        switch (xhr.status) {
                            case 202:
                                $(".form #username, .form #email").addClass('warning-input')
                                $(".form label[for=username],.form label[for=email]").addClass('warning-label animate__shakeX')
                                    .one('animationend webkitAnimationEnd', () => {
                                        $(".form label[for=username],.form label[for=email]").removeClass('warning-label animate__shakeX')
                                        $(".form #username, .form #email").removeClass('warning-input')
                                    })

                                break;
                            default:
                                $(".form .input").addClass('success-input')
                                $(".form label").addClass('success-label')
                                $(".btn-submit").addClass('submit-success')
                                $(".container").toggleClass('animate__bounceInUp animate__bounceOutDown animate__delay-1s')
                                $('body>main').one('animationend', () => {
                                    console.log("aniEnd")
                                    window.location.replace("index.html");
                                })
                                break;
                        }
                        break;
                }
            })
            .fail(() => {
                if (endpoint === endpoints.session) {
                    window.location.replace("login.html")
                }
                if (endpoint === endpoints.login) {
                    $(".form label").addClass('warning-label ')
                    $(".form .input").addClass('warning-input animate__shakeX')
                        .one('animationend webkitAnimationEnd', () => {
                            $(".form .input").removeClass('warning-input animate__shakeX')
                            $(".form label").removeClass('warning-label')
                        })
                }
            })
    },
    get: (endpoint) => {
        return $.ajax(baseURL + endpoint,
{
            method: "GET",
            xhrFields: {withCredentials: true},
            crossDomain: true
        })
        .fail(() => {
            console.log(endpoint)
            console.log("failed")
        })
    }
}

$('#logout').click((event) => {
    event.preventDefault();
    const data = {Session: "delete"}
    SDK.post(endpoints.session, data);
})
