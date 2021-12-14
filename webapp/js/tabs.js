$(document).ready(() => {
    SDK.post(endpoints.session, data = {Session: "validate"});
    let entrance = 'animate__zoomIn'
    let exit = 'animate__zoomOut'
    console.log("logging bookmarks")

    let tags = new Set();
    let genres = new Set();

    SDK.get(endpoints.getall).then((bookmarks) => {
        bookmarks.forEach((item, i) => {
            let htmlTags = "";
            let htmlGenres = "";
            for (const genre of item.genres) {
                genres.add(genre)
                htmlGenres += "<div class='genre'>" + genre + "</div>";
            }
            for (const tag of item.tags) {
                tags.add(tag)
                htmlTags += "<div class='tag'>" + tag + "</div>";
            }

            $("#fav-items").append("<div data-id='"+item.bookmark_id+"' class='fav-item'>" +
                "<h2 class='sub-header'>"+item.name+"</h2>" +
                "<p class='rating'>"+item.rating+"</p>" +
                "<div class='flex-row genres'>"+htmlGenres+"</div>" +
                "<div class='flex-row tags'>"+htmlTags+"</div>" +
                "<p class='description'>"+item.description+"</p>" +
                "<a class='link' href=''>"+item.url+"</a>" +
                "<p class='media'>"+item.media+"</p>" +
                "<p class='status'>"+item.status+"</p>" +
                "</div>")
            console.log(tags)
            console.log(genres)
        })



        $(".fav-item").click( function () {
            const clone = $(this).clone();
            const modal = $(".modal");
            modal.append(clone);
            modal.css("display", "block");
            if(modal.hasClass("animate__fadeOut animate__fast"))
                modal.removeClass("animate__fadeOut animate__fast")
            modal.off("animationend webkitAnimationEnd oAnimationEnd MSAnimationEnd")
            modal.addClass("animate__fadeIn animate__fast");
            clone.addClass('modal-content flex-column animate__animated animate__bounceInDown animate__fast');
            clone.append('<input type="button" class="btn-outlined edit-button" id="editbookmark" value="Edit"> ')
            let data_before_edit = {
                bookmark_name:  $(".modal-content .sub-header").html(),
                description:  $(".modal-content .description").html(),
                url:  $(".modal-content .link").html(),
                media_name:  $(".modal-content .media").html(),
                rating:  $(".modal-content .rating").html(),
                status: $(".modal-content .status").html()
            }

            $("#editbookmark").click(() => {
                if ($("#editbookmark").hasClass('btn-outlined')) {
                    var parent = $('.modal-content').width();
                    $(".modal-content").attr('contenteditable', 'true');
                    $(".modal-content").children().each((index, item) => {
                        if (!($(item).hasClass('edit-button')))
                            $(item).addClass("edible")
                    })
                    $("#editbookmark").toggleClass('btn btn-outlined').val('Stop editing')
                } else {
                    $(".modal-content").attr('contenteditable', 'false');
                    $("#editbookmark").toggleClass('btn btn-outlined').val('Edit')
                    $('.edible').removeClass("edible")
                    let data = {
                        bookmark_name:  $(".modal-content .sub-header").html(),
                        description:  $(".modal-content .description").html(),
                        url:  $(".modal-content .link").html(),
                        media_name:  $(".modal-content .media").html(),
                        rating:  $(".modal-content .rating").html(),
                        status: $(".modal-content .status").html()
                        }
                    SDK.post(endpoints.updateBookmark, data)
                        .then( () => {

                        })
                        .fail(() => {
                        $(".modal-content .sub-header").html(data_before_edit.bookmark_name);
                        $(".modal-content .description").html(data_before_edit.description);
                        $(".modal-content .link").html(data_before_edit.url);
                        $(".modal-content .media").html(data_before_edit.media_name);
                        $(".modal-content .rating").html(data_before_edit.rating);
                        $(".modal-content .status").html(data_before_edit.status);
                    })
                }
            })
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







