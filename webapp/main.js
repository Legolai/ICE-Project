const baseURL = "http://localhost:8888/api";

$("#login").submit((event) => {
	event.preventDefault();
	const data = $("#login :input").serializeArray();
	$.ajax(baseURL+"/login").method("POST")
});
