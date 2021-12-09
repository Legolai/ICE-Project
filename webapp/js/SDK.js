const baseURL = "http://localhost:8888/api";
const endpoints = {
    login: "/login",
    signup: "/signUp",
    getall: "/getAll",
    checkSession: "/checkSession"
}

const SDK = {
    post: (endpoint, data) => {
        $.ajax( baseURL+endpoint, {
            method: "POST",
            data: JSON.stringify(data),
            xhrFields: { withCredentials: true },
            crossDomain: true}
        )
            .done(() => {
                $(".form .input").addClass('success-input')
                $(".form label").addClass('success-label')
                $(".btn-submit").addClass('submit-success')
                setInterval(() => {
                    $(".container").addClass('animate__bounceOutDown')
                        .on('animationend', () => {
                            window.location = "index.html"
                        })
                }, 1000)
            })
            .fail(() => {
                $(".form .input").addClass('warning-input animate__shakeX')
                $(".form label").addClass('warning-label')
                setInterval(() => {
                    $(".form .input").removeClass('warning-input animate__shakeX')
                    $(".form label").removeClass('warning-label')
                },2000)
            })
    },
    get: (endpoint) => {
        $.ajax(baseURL + endpoint,
{
            method: "GET",
            xhrFields: {withCredentials: true},
            crossDomain: true
        })
        .done((result) => {

        })
        .fail(() => {
            if (endpoint === endpoints.checkSession){
                //window.location = "login.html"
            }
        })
    }
}
