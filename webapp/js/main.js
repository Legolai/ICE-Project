$(document).ready(() => {

	$("#login").submit((event) => {
		console.log("submitted")
		event.preventDefault();

		const username = $("#username").val();
		const password = $("#password").val();
		const data = {username: username, password: password}
		SDK.post(endpoints.login, data);
	});

	$("#signup").submit((event) => {
		event.preventDefault();

		//const firstname = $("#firstname").val();
		//const surname = $("#surname").val();
		const username = $("#username").val();
		const password = $("#password").val();
		const passwordRepeat = $("#passwordrepeat").val();
		const email = $("#email").val();


		const data = {
			username: username,
			password: password,
			email: email
		}
		SDK.post(endpoints.signup, data);
	});

	$("#aToLogin").on('click', () => {
		$(".animate__bounceInUp").toggleClass('animate__bounceOutDown animate__bounceInUp')
			.on('animationend webkitAnimationEnd', () => {
				window.location = "login.html"
		})
	})
	$("#aToSignup").on('click', () => {
		$(".animate__bounceInUp").toggleClass('animate__bounceOutDown animate__bounceInUp')
			.on('animationend webkitAnimationEnd', () => {
				window.location = "signup.html"
		})
	})

})



