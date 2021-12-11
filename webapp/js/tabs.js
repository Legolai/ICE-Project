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

