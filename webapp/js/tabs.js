$(document).ready(() => {
    SDK.post(endpoints.session, data = {Session: "validate"});
    let entrance = 'animate__zoomIn'
    let exit = 'animate__zoomOut'
    console.log("logging bookmarks")
    SDK.get(endpoints.getall).then((bookmarks) => {

        bookmarks.forEach((item, i) => {
            $("#fav-items").append("<div id='"+ ("fav-item-" + i) +"' class='fav-item'>" +
                "<h2 class='sub-header'>"+item.name+"</h2>" +
                "<p class='rating'>"+item.rating+"</p>" +
                "<div class='flex-row genres'>"+(item.genres.map((g) => ("<div class='genre'>" + g + "</div>") ))+"</div>" +
                "<div class='flex-row tags'>"+(item.tags.map((t) => ("<div class='tag'>" + t + "</div>")))+"</div>" +
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
    });


    const showItem = (element) => {
        /*
        $(element).addClass(entrance);
        $(element).removeClass(exit);
         */
        $(element).show(0);
    }
    const hideItem = (element) => {
        /*
        $(element).removeClass(entrance);
        $(element).addClass(exit)
         */
        $(element).hide(0);
    }

    $("#search").on("keyup", function() {
        const value = $(this).val().toLowerCase();
        $(".fav-item").filter(function() {
            if($(this).find(".status").text().toLowerCase() == $(".tab-active").text().toLowerCase() || $(".tab-active").text().toLowerCase() == "all")
                $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });

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
                    const search = $(this).text().toLowerCase().indexOf($("#search").val().toLowerCase()) > -1;
                    if(show && search) {
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
        modal.removeClass("animate__fadeIn animate__fast");
        modal.children().removeClass("animate__bounceInDown animate__faster");
        modal.children().toggleClass("animate__bounceOutUp animate__faster");
        modal.addClass("animate__fadeOut animate__fast")
            .on('animationend webkitAnimationEnd oAnimationEnd MSAnimationEnd', () => {
                modal.css("display","none");
                modal.empty();
            })
    }
})







