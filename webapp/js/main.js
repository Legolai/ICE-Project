const baseURL = "http://localhost:8888/api";

$("#login").submit((event) => {
	event.preventDefault();

	//const data = $("#login :input").serializeArray();
	const username = $("#username").val();
	const password = $("#password").val();
	const data = {username: username, password: password}
	doPost('/login', data)
});

const doPost = (endpoint, data) => {
    $.ajax(baseURL+endpoint,{
        type: "POST",
        data: JSON.stringify(data)  ,
        success: (result, status, xhr) => {
            //window.location.replace("index.html?token="+result.access_token);
            console.log(result)
        },
        error: () => {
            $(".form .input").addClass('warning-input animate__shakeX animate__animated')
            $(".form label").addClass('warning-label')

            setInterval(() => {
                $(".form .input").removeClass('warning-input animate__shakeX animate__animated')
                $(".form label").removeClass('warning-label')
            },2000)
        }
    })
}

