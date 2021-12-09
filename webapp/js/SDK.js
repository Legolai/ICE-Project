const baseURL = "http://localhost:8888/api";


const SDK = {
    login: (data) => {
        $.post( baseURL+"/login", JSON.stringify(data), {
            xhrFields: { withCredentials: true },
            crossDomain: true}
        )
        .done(() => {

            $(".form .input").addClass('success-input')
            $(".form label").addClass('success-label')
            $(".btn-submit").addClass('submit-sucess')
            setInterval(() => {
                $(".container").addClass('animate__fadeOutDown')
            }, 1000)
            setInterval(() => {
                window.location = "index.html"
            }, 1750)
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
    getAll: () => {
        $.ajax(baseURL+"/getAll",
            {
                method: "get",
                xhrFields: { withCredentials: true },
                crossDomain: true}
        )
        .done((result) => {
            console.log(result)
            //return result
        }).fail(() => {
            //window.location = "login.html"
        })

    }
}
