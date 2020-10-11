var mybutton = document.getElementById("button-scroll");

// When the user scrolls down 20px from the top of the document, show the button
window.onscroll = function() {
	scrollFunction()
};

function scrollFunction() {
	if (document.body.scrollTop > 20
		|| document.documentElement.scrollTop > 20) {
		mybutton.style.display = "block";
	} else {
		mybutton.style.display = "none";
	}
}

// When the user clicks on the button, scroll to the top of the document
function topFunction() {
	window.scrollTo({ top: 0, behavior: 'smooth' });
}

// Click change user for Login
function openUser(evt, username) {
	var i, x, tablinks;
	x = document.getElementsByClassName("user");
	for (i = 0; i < x.length; i++) {
		x[i].style.display = "none";
	}
	tablinks = document.getElementsByClassName("tablink");
	for (i = 0; i < x.length; i++) {
		tablinks[i].className = tablinks[i].className.replace(
			" w3-white", "");
	}
	document.getElementById(username).style.display = "block";
	evt.currentTarget.className += " w3-white";
}