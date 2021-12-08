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
            window.location.replace("index.html");
        }
    })
}


