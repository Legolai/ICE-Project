$("#login").submit((event) => {
	event.preventDefault();

	//const data = $("#login :input").serializeArray();
	const username = $("#username").val();
	const password = $("#password").val();
	const data = {username: username, password: password}
	SDK.login(data);
});


