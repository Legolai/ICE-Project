const baseURL = "http://localhost:8888/api";
const endpoints = {
    login: "/login",
    signup: "/signUp",
    getall: "/getAll",
    session: "/session"
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
                switch (endpoint) {
                    case endpoints.session:
                        console.log("session is valid")
                        if (result.Session === "deleted" ) {
                            $("body nav").toggleClass('animate__bounceOutUp animate__bounceInDown')
                            $("#selector-panel").toggleClass('animate__bounceInRight animate__bounceOutLeft')
                            $("#favourites").toggleClass('animate__bounceInRight animate__bounceOutRight')
                                .on('animationend webkitAnimationEnd', () => {
                                    window.location.replace("login.html")
                                })
                        } else
                            SDK.get(endpoints.getall);
                        break;
                    default:
                        switch (xhr.status) {
                            case 202:
                                $(".form #username, .form #email").addClass('warning-input')
                                $(".form label[for=username],.form label[for=email]").addClass('warning-label animate__shakeX')
                                    .one('animationend webkitAnimationEnd', () => {
                                        $(".form label[for=username],.form label[for=email").removeClass('warning-label animate__shakeX')
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
        $.ajax(baseURL + endpoint,
{
            method: "GET",
            xhrFields: {withCredentials: true},
            crossDomain: true
        })
        .done((result) => {
            if (endpoint === endpoints.getall) {
                result.forEach((item, i) => {
                    $("#fav-items").append("<div id='"+ ("fav-item-" + i) +"' class='fav-item'>" +
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

                $(".fav-item").click(function () {
                    const clone = $(this).clone();
                    const modal = $(".modal");
                    modal.append(clone);
                    modal.css("display", "block");
                    if(modal.hasClass("animate__fadeOut animate__fast"))
                        modal.removeClass("animate__fadeOut animate__fast")
                    modal.off("animationend webkitAnimationEnd oAnimationEnd MSAnimationEnd")
                    modal.addClass("animate__fadeIn animate__fast");
                    clone.addClass('modal-content animate__animated animate__bounceInDown animate__fast');
                })

            }
        })
        .fail(() => {
            console.log(endpoint)
            console.log("failed")
        })
    }
}
