

SDK.get(endpoints.getall);

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
                if(show)
                    $(this).show()
                else
                    $(this).hide()
            })
        }
        else {
            $("#fav-items *").show()
        }
    })
})

