const baseURL = "http://localhost:8888/api";
const form = document.querySelector("form");
form.addEventListener("submit", (event) => {
	// submit event detected
	event.preventDefault();
	// const request = new XMLHttpRequest();
	// request.open("POST", baseURL + "/login");
	console.log(event.username);
	// const data = '${e}'
	// request.send('{"username": ${e} }')
});
