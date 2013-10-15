$(document).ready(function () {
	//UI FORM ELEMENTS
	var spinner = $('.spinner input').spinner({ min: 0 });
	
	$('.datepicker-wrap input').datepicker({
		showOn: 'button',
		buttonImage: 'images/ico/calendar.png',
		buttonImageOnly: true
	});
	
	$( "#slider" ).slider({
		range: "min",
		value:1,
		min: 0,
		max: 10,
		step: 1
	});
	
	//CUSTOM FORM ELEMENTS
	$("input[type=radio],select, input[type=checkbox]").uniform();
	
	//SCROLL TO TOP BUTTON
	$('.scroll-to-top').click(function () {
		$('body,html').animate({
			scrollTop: 0
		}, 800);
		return false;
	});
	
	//HEADER RIBBON NAVIGATION
	$('.ribbon li').hide();
	$('.ribbon li.active').show();
	$(".ribbon li a").click(function() {
		$(".ribbon li").hide();
		if ($(this).parent().parent().hasClass('open'))
			$(this).parent().parent().removeClass('open');
		else {
			$(".ribbon ul").removeClass('open');
			$(this).parent().parent().addClass('open');
		}
		$(this).parent().siblings().each(function() {
			$(this).removeClass('active');
		});
		$(this).parent().attr('class', 'active'); 
		$('.ribbon li.active').show();
		$('.ribbon ul.open li').show();
		return false;
	});
	
	//LIGHTBOX
	$("a[rel^='prettyPhoto']").prettyPhoto({animation_speed:'normal',theme:'light_square'});
	
	//TABS
	$(".tab-content").hide();
	$(".tab-content:first").show();
	$(".inner-nav li:first").addClass("active");

	$(".inner-nav a").click(function(){
		$(".inner-nav li").removeClass("active");
		$(this).parent().addClass("active");
		var currentTab = $(this).attr("href");
		if (currentTab == "#location")
		initialize();
		$(".tab-content").hide();
		$(currentTab).show();
		return false;
	});
	
	
	//CSS
	$('.top-right-nav li:last-child,.social li:last-child,.twins .f-item:last-child,.ribbon li:last-child,.room-types li:last-child,.three-col li:nth-child(3n),.reviews li:last-child,.three-fourth .deals .one-fourth:nth-child(3n),.full .deals .one-fourth:nth-child(4n),.locations .one-fourth:nth-child(3n),.pager span:last-child,.get_inspired li:nth-child(5n)').addClass('last');
	$('.bottom nav li:first-child,.pager span:first-child').addClass('first');
	
	//ROOM TYPES MORE BUTTON
	$(".more-information").slideUp();
	$(".more-info").click(function() {
		var moreinformation = $(this).closest("li").find(".more-information");
		var txt = moreinformation.is(':visible') ? '+ more info' : ' - less info';
		$(this).text(txt);
		moreinformation.stop(true, true).slideToggle("slow");
	});
	
	//MAIN SEARCH 
	$("input[name=radio]").change(function() {
		var showForm = $(this).val();
		$(".form").hide();
		$("#"+showForm).show();
	}); 
	
	$(".f-item .radio").click(function() {
		$(".f-item").removeClass("active");
		$(this).parent().addClass("active");
	});	
	
	// LIST AND GRID VIEW TOGGLE
	$('.view-type li:first-child').addClass('active');
		
	$('.grid-view').click(function() {
		$(".deals article").attr("class", "one-fourth");
		$(".deals article:nth-child(3n)").addClass("last");
		$(".view-type li").removeClass("active");
		$(this).addClass("active");
	});
	
	$('.list-view').click(function() {
		$(".deals article").attr("class", "full-width");
		$(".view-type li").removeClass("active");
		$(this).addClass("active");
	});
	
});
