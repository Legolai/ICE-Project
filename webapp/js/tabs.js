

const tabNames = ["All", "Watching", "Completed", "On Hold", "Dropped", "Plan to Watch"]

const items = [...Array(100).fill(0).map((x, i)=> ({
    name: "Hello",
    description: "Lorem ipsum dolor sit amet," +
        " consectetur adipiscing elit. Nam porta tristique nunc nec sagittis." +
        " Ut vestibulum, lacus semper consectetur ornare, arcu dui laoreet purus," +
        " ut varius velit turpis vel neque. Morbi lobortis rhoncus massa sit amet lacinia." +
        " Curabitur a nunc neque. Praesent mi turpis, consequat vel nibh vitae," +
        " lacinia bibendum magna. Suspendisse semper dui a quam sollicitudin placerat." +
        " Vestibulum porttitor orci quis turpis dictum, et efficitur magna facilisis." +
        " Quisque rhoncus lectus fringilla nunc ornare, sed efficitur risus consectetur." +
        " Suspendisse potenti. Donec cursus dapibus rutrum. Vivamus laoreet aliquet bibendum." +
        " Integer non magna et velit lobortis bibendum eu nec urna.",
    url: "www.exampel.com",
    media: "Song",
    status: tabNames[i % (tabNames.length-1) + 1],
    genres: ["Rock", "Hip-hop", "K-pop"],
    tags: ["Something", "boring", "Yahoo"],
    rating: 6,
}))];



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

items.map((item) => {
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

