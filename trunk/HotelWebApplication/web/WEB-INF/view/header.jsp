<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : header
    Created on : Oct 13, 2013, 11:01:49 AM
    Author     : Quang Trung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<!--[if IE 7 ]>    <html class="ie7 oldie" lang="en"> <![endif]-->
<!--[if IE 8 ]>    <html class="ie8 oldie" lang="en"> <![endif]-->
<!--[if IE 	 ]>    <html class="ie" lang="en"> <![endif]-->
<!--[if lt IE 9]><script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
<html>
    <head>
        <meta charset="utf-8">
        <title>Hotel Recommender - Hotels</title>
        <link rel="stylesheet" href="css/style.css?333" type="text/css" media="screen,projection,print" />
        <link rel="stylesheet" href="css/prettyPhoto.css" type="text/css" media="screen" />
        <link rel="shortcut icon" href="images/favicon.ico" />
        <link rel="stylesheet" href="css/jquery-ui.css"/>
        
        <script type="text/javascript" src="./ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
        <script type="text/javascript" src="./ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/jquery-ui.min.js"></script>
        <script type="text/javascript" src="js/sequence.jquery-min.js"></script>
	<script type="text/javascript" src="js/googlemap.js"></script>
	<script type="text/javascript" src="js/gmap3.js"></script>
	<script type="text/javascript" src="js/infobox.js"></script>
        <script type="text/javascript" src="js/jquery.uniform.min.js"></script>
        <script type="text/javascript" src="js/jquery.prettyPhoto.js"></script>
        <script type="text/javascript" src="js/sequence.js"></script>
        <script type="text/javascript" src="js/scripts.js"></script>
    </head>
    <body>
        <!--header-->
        <header>
            <div class="wrap clearfix">
                <!--logo-->
                <h1 class="logo"><a href="/HotelWebApplication" title="Book Your Hotel - home"><img src="images/txt/logo.png" alt="Book Your Hotel" /></a></h1>
                <!--//logo-->

                <!--ribbon-->
                <div class="ribbon">
                    <nav>
                        <ul class="profile-nav">
                            <li class="active"><a href="#" title="My Account">Account</a></li>
                                <c:if test="${empty sessionScope.user}">
                                <li><a onclick="window.location = '/HotelWebApplication/Login'" href="#" title="Login">Login</a></li>
                                <li><a onclick="window.location = '/HotelWebApplication/Register'" href="#" title="Register">Register</a></li>
                                </c:if>
                            <li><a href="#" title="Help">Help</a></li>
                        </ul>
                        <ul class="lang-nav">
                            <li class="active"><a href="#" title="Tiếng Việt">Tiếng Việt</a></li>
                            <li><a href="#" title="English">English</a></li>
                        </ul>
                        <ul class="currency-nav">
                            <li class="active"><a href="#" title="VNĐ">VNĐ</a></li>
                            <li><a href="#" title="$US Dollar">$US Dollar</a></li>
                        </ul>
                    </nav>
                </div>
                <!--//ribbon-->
                
                <!--search-->
                <div class="search">
                    <c:if test="${not empty sessionScope.user}">
                        <h6 align="right">You are login as ${sessionScope.user.username} <c:if test="${sessionScope.user.getRoleID().getRoleID() == 2}">
                                [<a href="/HotelWebManager">Admin</a>]
                </c:if> [<a href="Logout">Logout</a>] </h6>
                    </c:if>  
                    <form id="search-form" method="get" action="http://www.kajag.com/themes/book_your_travel/search-form">
                        <label>                      
                            <input type="search" placeholder="Search entire site here" name="site_search" id="site_search" /> 
                        </label>
                        <input type="submit" id="submit-site-search" value="submit-site-search" name="submit-site-search"/>
                    </form>
                </div>
                <!--//search-->

                <!--contact-->
                <div class="contact">
                    <span>24/7 Support number</span>
                    <span class="number">SE- 0765 - 555 - 555</span>
                </div>
                <!--//contact-->
            </div>

            <!--main navigation-->
            <nav class="main-nav" role="navigation">
                <ul class="wrap">
                    <li class="active"><a href="/HotelWebApplication/HotelView" title="Hotels">Hotels</a></li>
                    <li><a href="hot_deals.html" title="Hot deals">Hot deals</a></li>
                    <li><a href="get_inspired.html" title="Get inspired">Get inspired</a></li>
                    <li><a href="#" title="Travel guides">Travel guides</a>
                    </li>
                </ul>
            </nav>
            <!--//main navigation-->
        </header>
        <!--//header-->
