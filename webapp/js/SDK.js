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
                console.log(".done")
                switch (xhr.status) {
                    case 202:
                        $(".form .input").addClass('warning-input')
                        $(".form label").addClass('warning-label animate__shakeX')
                            .one('animationend webkitAnimationEnd', () => {
                                $(".form label").removeClass('warning-label animate__shakeX')
                                $(".form .input").removeClass('warning-input')
                            })

                        break;
                    default:
                        $(".form .input").addClass('success-input')
                        $(".form label").addClass('success-label')
                        $(".btn-submit").addClass('submit-success')
                        $(".container").toggleClass('animate__bounceInUp animate__bounceOutDown animate__delay-1s')
                        $('body main').one('animationend webkitAnimationEnd', () => {
                            console.log("aniEnd")
                            window.location.replace("index.html");
                        })
                        break;
                }
            })
            .fail(() => {
                if (endpoint === endpoints.checkSession) {
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
        $.ajax(baseURL + endpoint,
{
            method: "GET",
            xhrFields: {withCredentials: true},
            crossDomain: true
        })
        .done((result) => {
            if (endpoint === endpoints.checkSession) {
                console.log("session is valid")
                SDK.get(endpoints.getall);
            }
            if (endpoint === endpoints.getall) {
                result.forEach((item) => {
                    $("#fav-items").append("<div id='' class='fav-item animate__animated animate__jackInTheBox animate__faster'>" +
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
                $(".fav-item").each((i, item) => {
                    $(item).css({
                    "-webkit-animation-delay" : 1.5+i*0.10+"s",
                    "animation-delay" : 1.5+i*0.10+"s"
                    }).on('animationend webkitAnimationEnd', () => {
                        $(item).css({
                            "-webkit-animation-delay" : 0+"s",
                            "animation-delay" : 0+"s"
                        })
                        $(item).removeClass('animate__jackInTheBox');
                    })
                })

            }
        })
        .fail(() => {
            console.log(endpoint)
            console.log("failed")
            if (endpoint === endpoints.checkSession){
                window.location.replace("login.html")
            }
        })
    }
}
