$('#testimonial').carousel({
	interval: 10000
})
$('#quotes').carousel({
	interval: 6000
})
$('#myCarousel').carousel({
	interval: 3000
})


// Apply Bootstrap Scrollspy to show active navigation link based on page scrolling
$('.navbar').scrollspy();

// Scroll page with easing effect
$('.navbar ul li a').bind('click', function(e) {
	e.preventDefault();
	target = this.hash;
	document.querySelector(target).scrollIntoView({ behavior: "smooth" });
	$(".btn-navbar").click();
});


// Show/Hide Sticky "Go top" button
$(window).scroll(function() {
	if ($(this).scrollTop() > 200) {
		$(".go-top").fadeIn(200);
	}
	else {
		$(".go-top").fadeOut(200);
	}
});

// Scroll Page to Top when clicked on "go top" button
$(".go-top").click(function(event) {
	event.preventDefault();
	document.querySelector('#carouselSection').scrollIntoView({ behavior: "smooth" });
});


$('body').tooltip({
	selector: '[rel=tooltip]',
	placement: 'bottom'
});



