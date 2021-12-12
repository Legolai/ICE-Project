$(document).ready(() => {
    SDK.get(endpoints.checkSession);
    let entrance = 'animate__zoomIn'
    let exit = 'animate__zoomOut'

    const showItem = (element) => {
        $(element).addClass(entrance);
        $(element).removeClass(exit);
    }
    const hideItem = (element) => {
        $(element).removeClass(entrance);
        $(element).addClass(exit)
    }

    const tabNames = ["All", "Watching", "Completed", "On Hold", "Dropped", "Plan to Watch"]
    tabNames.forEach((tabName) => {
        const tabId = tabName.replaceAll(" ", "");
        $("#tabs").append("<button id='"+tabId+"' class='tab sub-header"+ (tabName === tabNames[0] ? " tab-active" : "") +"'>" + tabName + "</button>")
        const tab = $("#" + tabId);
        tab.click(() => {
            $(".tab-active").removeClass("tab-active");
            tab.addClass("tab-active");
            if(tabName.toLowerCase() != "all"){
                $(".fav-item").filter(function() {
                    const show = $(this).find(".status").text().toLowerCase() == tabName.toLowerCase();
                    if(show) {
                        if ($(this).hasClass(exit))
                        showItem($(this));
                    } else {
                        hideItem($(this))

                    }
                })
            }
            else {
                $("#fav-items *").each((i, item) => {
                    showItem(item)
                    })
            }
        })
    })
})

$(this).click(function (event) {
    const modal = $(".modal");
    if(event.target == modal[0]) {

        modal.removeClass("animate__fadeIn");
        modal.children().removeClass("animate__jackInTheBox animate__delay-1s");
        modal.children().toggleClass("animate__hinge");
        modal.addClass("animate__fadeOut animate__delay-2s")
            .on('animationend webkitAnimationEnd oAnimationEnd MSAnimationEnd', () => {
                modal.css("display","none");
                modal.empty();
            })
    }
})

$(document).ready(function(){
    $("#search").on("keyup", function() {
        const value = $(this).val().toLowerCase();
        $(".fav-item").filter(function() {
            if($(this).find(".status").text().toLowerCase() == $(".tab-active").text().toLowerCase() || $(".tab-active").text().toLowerCase() == "all")
                $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });
});






