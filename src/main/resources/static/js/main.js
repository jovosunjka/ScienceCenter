var relativeUrlForPaymentMicroserviceUrl = "/science_center_1/payment/microservice-url";

var currentUserKey = "currentUser";
	
$(document).ready(function() {


	if(isLoggedIn()) {
		initPageForLoggedUser();
	}
	else {
		initLoginPage();
		initPageForLoggedUser();
	}


});

function initLoginPage() {
	saveToken();
}

function initPageForLoggedUser() {
	$("#id_logout_cell").append('<button id="id_logout_button">Logout</button>');

	loadPayLink();

	$("#id_logout_button").click(function(event) {
			event.preventDefault()

			logout();
			$(this).remove();
		}
	);
}

function loadPayLink() {
	var url = getPaymentMicroserviceUrl();
	
	if(url) {
		$("#id_div_pay").append('<a href="' + url + '">***Pay***</a></li>');
	}
	else {
		toastr.error("Not found payment microservice url!");
	}
}


function getPaymentMicroserviceUrl() {
	var url = null;
	
	$.ajax({
		async: false,
		type : "GET",
		url : relativeUrlForPaymentMicroserviceUrl,
		//dataType : "json",
		contentType: "text/plain", //"application/json",
		cache: false,
		success : function(receivedUrl) {
			url = receivedUrl;
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) { 
					toastr.error("Ajax ERROR: " + errorThrown + ", STATUS: " + textStatus); 
					return null;
		}
	});
	
	return url;
}

function isLoggedIn() {
	if (this.getToken() !== '') {
		return true;
	}
	return false;
}

function saveToken() {
	/*localStorage.setItem(currentUserKey, JSON.stringify({username, password,
		roles: this.jwtUtilsService.getRoles(token), token: token}));*/
	localStorage.setItem(currentUserKey, JSON.stringify({token: "my_token"} ));
}

function getToken() {
	const currentUser = JSON.parse(localStorage.getItem(currentUserKey));
	const token = currentUser && currentUser.token;
	return token ? token : '';
}

function getRolesFromToken(token) {
	var jwtData = token.split('.')[1];
	var decodedJwtJsonData = window.atob(jwtData);
	var decodedJwtData = JSON.parse(decodedJwtJsonData);
	return decodedJwtData.roles.map(x => x.authority) || [];
}

function logout() {
	localStorage.removeItem(currentUserKey);
}