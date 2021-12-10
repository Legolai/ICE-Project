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
            crossDomain: true},
        )
            .done((result,statusText, xhr) => {
                switch (xhr.status) {
                    case 202:
                        $(".form .input").addClass('warning-input')
                        $(".form label").addClass('warning-label animate__shakeX')
                            .on('animationend webkitAnimationEnd oAnimationEnd MSAnimationEnd', () => {
                                $(".form label").removeClass('warning-label animate__shakeX')
                                $(".form .input").removeClass('warning-input')
                            })

                        break;
                    default:
                        $(".form .input").addClass('success-input')
                        $(".form label").addClass('success-label')
                        $(".btn-submit").addClass('submit-success')
                        $(".container").addClass('animate__bounceOutDown animate__delay-1s')
                            .on('animationend webkitAnimationEnd oAnimationEnd MSAnimationEnd', () => {
                                window.location = "index.html"
                            })
                        break;
                }
            })
            .fail(() => {
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
            if (endpoint === endpoints.getall) {
                result.forEach((item) => {
                    $("#fav-items").append("<div id='' class='fav-item'>" +
                        "<h2 class='sub-header'>"+item.name+"</h2>" +
                        "<p>"+item.rating+"</p>" +
                        "<div class='flex-row'>"+(item.genres.map((g) => ("<div class='genre'>" + g + "</div>") ))+"</div>" +
                        "<div class='flex-row'>"+(item.tags.map((t) => ("<div class='tag'>" + t + "</div>")))+"</div>" +
                        "<p>"+item.description+"</p>" +
                        "<a href=''>"+item.url+"</a>" +
                        "<p>"+item.media+"</p>" +
                        "<p class='status'>"+item.status+"</p>" +
                        "</div>")
                })
            }

        })
        .fail(() => {
            if (endpoint === endpoints.checkSession){
                //window.location = "login.html"
            }
        })
    }
}
