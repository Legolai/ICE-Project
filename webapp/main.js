const baseURL = "http://localhost:8888/api";

$("#login").submit((event) => {
	event.preventDefault();

	const data = $("#login :input").serializeArray().map((item) => {return [item.name, item.value];});
	console.log(event.target);
	console.log(data);
});
