<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%-- 
    Document   : hotellist
    Created on : Oct 6, 2013, 7:06:39 PM
    Author     : Huynh Quang Thao
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<script type="text/javascript">

    /**
     * Google map for hotel
     */
    /**
     * Google map for hotel
     */
    var _address = "${hotel.address}".replace('(Hiển thị bản đồ)', '');
    console.log(_address);
    function initialize() {
        $("#map_canvas").gmap3({
            marker: {
                address: _address
            },
            map: {
                options: {
                    zoom: 14
                }
            }
        });
    }
</script>
<!--main-->
<div class="main" role="main">		
    <div class="wrap clearfix">
        <!--main content-->
        <div class="content clearfix">
            <!--breadcrumbs-->
            <nav role="navigation" class="breadcrumbs clearfix">
                <!--crumbs-->
                <ul class="crumbs">
                    <li><a href="/HotelWebApplication" title="Home">Home</a></li>
                    <li><a href="#" title="">${hotel.getProvinceid().getName()}</a></li>
                    <li><a href="#" title="">${hotel.hotelName}</a></li>  
                </ul>
                <!--//crumbs-->

                <!--top right navigation-->
                <ul class="top-right-nav">
                    <li><a href="search_results.html" title="Back to results">Back to results</a></li>
                    <li><a href="#" title="Change search">Change search</a></li>
                </ul>
                <!--//top right navigation-->
            </nav>
            <!--//breadcrumbs-->

            <!--hotel three-fourth content-->
            <section class="three-fourth">
                <!--gallery-->
                <section class="gallery" id="crossfade">
                    <img src="images/slider/img1.jpg" alt="" width="850" height="531" />
                    <img src="images/slider/img2.jpg" alt="" width="850" height="531" />
                    <img src="images/slider/img3.jpg" alt="" width="850" height="531" />
                    <img src="images/slider/img4.jpg" alt="" width="850" height="531" />
                </section>
                <!--//gallery-->

                <!--inner navigation-->
                <nav class="inner-nav">
                    <ul>
                        <li class="availability"><a href="#availability" title="Rooms">Rooms</a></li>
                        <li class="description"><a href="#description" title="Description">Description</a></li>
                        <li class="facilities"><a href="#facilities" title="Facilities">Facilities</a></li>
                        <li class="location"><a href="#location" title="Location">Location</a></li>
                        <li class="reviews"><a href="#reviews" title="Reviews">Reviews</a></li>
                    </ul>
                </nav>
                <!--//inner navigation-->

                <!--availability-->
                <section id="availability" class="tab-content">
                    <article>

                        <h1>Room types</h1>
                        <ul class="room-types">
                            <c:forEach var="room" items="${hotel.roomList}">
                                <!--room-->
                                <li>
                                    <figure class="left"><img src="${room.imageLinks}" alt="" width="270" height="152" /><a href="images/slider/img3.jpg" class="image-overlay" rel="prettyPhoto[gallery1]"></a></figure>
                                    <div class="meta">
                                        <h2>${room.roomName}</h2>
                                        <div class="row" style="font-size: 17px">
                                            <span class="first">Price:</span>
                                            <span style="color: red" class="second"><fmt:formatNumber type="number" value="${room.roomPriceList.get(0).getPrice()}" /> VNĐ</span>
                                        </div>
                                        <p>${room.description}</p>
                                        <a href="javascript:void(0)" title="more info" class="more-info">+ more info</a>
                                    </div>
                                    <div class="room-information">

                                        <div class="row">
                                            <span class="first">Max:</span>
                                            <span class="second">
                                                <c:forEach begin="1" end="${room.capicity}" varStatus="loop">
                                                    <img src="images/ico/person.png" alt="" />
                                                </c:forEach>
                                            </span>
                                        </div>
                                        <!--                                        
                                                                                <div class="row">
                                                                                    <span class="first">Rooms:</span>
                                                                                    <span class="second">01</span>
                                                                                </div>
                                        -->
                                        <a href="#" class="gradient-button" title="Book">Book</a>
                                    </div>
                                    <div class="more-information">
                                        <p>${room.otherProperties}</p>
                                    </div>
                                </li>
                                <!--//room-->
                            </c:forEach>


                        </ul>
                    </article>
                </section>
                <!--//availability-->

                <!--description-->
                <section id="description" class="tab-content">
                    <article>
                        <h1>Description</h1>
                        <div class="text-wrap">	
                            <p>${hotel.description}</p>
                        </div>

                    </article>
                </section>
                <!--//description-->

                <!--facilities-->
                <section id="facilities" class="tab-content">
                    <article>
                        <h1>Facilities (Coming soon)</h1>
                        <div class="text-wrap">	
                            <ul class="three-col">
                                <li>Kitchenette</li>
                                <li>Ironing board</li>
                                <li>Catering services</li>
                                <li>Beachfront</li>
                                <li>Hotspots</li>
                                <li>Exhibition/convention floor</li>
                                <li>Restaurant</li>
                                <li>Room service - full menu</li>
                                <li>Courtyard</li>
                                <li>Lounges/bars</li>
                                <li>Laundry/Valet service</li>
                                <li>Airport Shuttle Service</li>
                                <li>Complimentary breakfast</li>
                                <li>Valet cleaning</li>
                                <li>Car hire</li>
                            </ul>
                        </div>

                        <h1>Activities</h1>
                        <div class="text-wrap">	
                            <p>Tennis court, Sauna, Fitness centre, Massage </p>
                        </div>

                        <h1>Internet</h1>
                        <div class="text-wrap">	
                            <p><strong>Free!</strong> WiFi is available in all areas and is free of charge. </p>
                        </div>

                        <h1>Parking</h1>
                        <div class="text-wrap">	
                            <p>Private parking is possible at a location nearby (reservation is not needed) and costs USD 28.80 per day.</p>
                        </div>
                    </article>
                </section>
                <!--//facilities-->

                <!--location-->
                <section id="location" class="tab-content">
                    <article>
                        <!--map-->
                        <div class="gmap" id="map_canvas"></div>
                        <!--//map-->
                    </article>
                </section>
                <!--//location-->

                <!--reviews-->
                <section id="reviews" class="tab-content">
                    <article>
                        <h1>Hotel Score and Score Breakdown</h1>
                        <div class="score">
                            <span class="achieved">${hotel.rating} </span>
                            <span> / 10</span>
                            <p class="info">Based on 782 reviews</p>  <!-- TODO: user rating here -->
                            <p class="disclaimer">Guest reviews are written by our customers <strong>after their stay</strong> at Hotel Best Ipsum.</p>
                        </div>

                        <dl class="chart">
                            <dt>Clean</dt>
                            <dd><span id="data-one" style="width:80%;">8&nbsp;&nbsp;&nbsp;</span></dd>
                            <dt>Comfort</dt>
                            <dd><span id="data-two" style="width:60%;">6&nbsp;&nbsp;&nbsp;</span></dd>
                            <dt>Location</dt>
                            <dd><span id="data-three" style="width:80%;">8&nbsp;&nbsp;&nbsp;</span></dd>
                            <dt>Staff</dt>
                            <dd><span id="data-four" style="width:100%;">10&nbsp;&nbsp;&nbsp;</span></dd>
                            <dt>Services</dt>
                            <dd><span id="data-five" style="width:70%;">7&nbsp;&nbsp;&nbsp;</span></dd>
                            <dt>Value for money</dt>
                            <dd><span id="data-six" style="width:90%;">9&nbsp;&nbsp;&nbsp;</span></dd>
                        </dl>
                        <hr>
                        <br>
                        <br>
                        <br>
                        <div >
                            <style>
                                #uniform-undefined span{
                                    padding: 0 18px 0 0px;
                                    background-position: -100% 8px;
                                    font-size: 13px;
                                }
                                #uniform-undefined select{

                                    width: 200px;
                                    height: 29px;
                                    top: 1px;
                                }
                                #uniform-undefined{
                                    width: 200px;
                                    margin-top: -32px;
                                }
                            </style>
                        </div>
                        <div align="center">
                            <c:choose>
                                <c:when test="${not empty sessionScope.user}">
                                    <form id="post_comment_form" action = "/HotelWebApplication/Rating" method="POST">
                                        <h1>Rating</h1>
                                        <div class="f-item datepicker" style="width: 250px; display: inline-block; margin-bottom: 6px">
                                            <div class="selector" id="uniform-undefined"> <span>Rate mark</span>
                                                <select name="rating" style="opacity: 0;">
                                                    <option value="0">Rate mark</option>
                                                    <option value="10">10:Paradise</option>
                                                    <option value="9">9: Excellent</option>
                                                    <option value="8">8: Very good</option>
                                                    <option value="7">7: Good </option>
                                                    <option value="6">6: Not bad</option>
                                                    <option value="5">5: It's OK</option>
                                                    <option value="4">4: Average</option>
                                                    <option value="3">3: Bad</option>
                                                    <option value="2">2: Very bad</option>
                                                    <option value="1">1: No idea</option>

                                                </select>
                                            </div>
                                        </div>
                                        <p>
                                            <textarea id="comment_text" style="width:80%" height="500" placeholder="Write your comment ..." name="comment"></textarea>
                                        </p>
                                        <input hidden="true" name="hotelId" value="${hotel.hotelID}"/>
                                        <input id="post_comment" class="gradient-button" type="button" value="Post"/>
                                        <div style="padding: 10px 0;" id="post_status"></div>
                                    </form>
                                    <script>
                                        $(function() {
                                            $('#post_comment').click(function() {
                                                $('#post_status').html('<img style="width: 50px; height: 50px" src="images/loading.gif"/>')
                                                setTimeout(function() {
                                                    $('#post_status').html('');
                                                    var obj = $('<li>' +
                                                            '<figure class="left"><img src="images/uploads/avatar.jpg" alt="avatar" /></figure>' +
                                                            '<address><span>${sessionScope.user.getUsername()}</span><br />${sessionScope.user.getRoleID().getRoleName()}</address>' +
                                                            '<p>' + $('#comment_text').val() + '</p>' +
                                                            '</li>');
                                                    $('#comment_list').prepend(obj);
                                                    $.ajax({
                                                        url: '/HotelWebApplication/Rating',
                                                        type: 'POST',
                                                        data: $('#post_comment_form').serialize(),
                                                    }).done(function(msg) {

                                                    });
                                                }, 2000);
                                            });
                                        });
                                    </script>
                                </c:when>
                                <c:otherwise>
                                    <h2>Please <a href="Login?ref=/HotelWebApplication/HotelView?hotel_id=${hotel.hotelID}">Login</a> to post your comment</h2>
                                </c:otherwise>
                            </c:choose>
                        </div> 
                    </article>

                    <article>
                        <h1>Guest reviews</h1>
                        <ul id="comment_list" class="reviews">
                            <!--review-->


                            <c:forEach var="comment" items="${requestScope.hotel.commentList}">
                                <li>
                                    <figure class="left"><img src="images/uploads/avatar.jpg" alt="avatar" /></figure>
                                    <address><span>${comment.userID.getUsername()}</span><br />${comment.userID.getRoleID().getRoleName()}</address>
                                    <p>${comment.commentText}</p>              
                                </li>
                            </c:forEach>
                            <!--//review-->
                        </ul>
                    </article>
                </section>
                <!--//reviews-->

            </section>
            <!--//hotel content-->

            <!--sidebar-->
            <aside class="right-sidebar">
                <!--hotel details-->
                <article class="hotel-details clearfix">
                    <h1>${hotel.hotelName} 
                        <span class="stars">
                            <c:forEach begin="1" end="${hotel.star}" varStatus="loop">
                                <img src="images/ico/star.png" alt="" />
                            </c:forEach>
                        </span>
                    </h1>
                    <span class="address">${hotel.address}</span>
                    <span class="rating"> ${hotel.rating} /10</span>
                    <div class="description">
                        <p>${fn:substring(hotel.description, 0, 200)}...</p>
                    </div>
                    <div class="tags">
                        <p style="text-align: center;"><a style="height: auto;
                                                          line-height: 1.5em;
                                                          padding: 10px 20px;" target="_blank" href="${hotel.linkDetailURL}" class="gradient-button" title="${hotel.websiteName}">Book now at ${hotel.websiteName}</a></p>
                    </div>
                </article>
                <!--//hotel details-->

                <!--testimonials-->
                <article class="testimonials clearfix">
                    <blockquote>Loved the staff and the location was just amazing... Perfect!” </blockquote>
                    <span class="name">- Jane Doe, Solo Traveller</span>
                </article>
                <!--//testimonials-->

                <!--Need Help Booking?-->
                <article class="default clearfix">
                    <h2>Need Help Booking?</h2>
                    <p>Call our customer services team on the number below to speak to one of our advisors who will help you with all of your holiday needs.</p>
                    <p class="number">1- 555 - 555 - 555</p>
                </article>
                <!--//Need Help Booking?-->

                <!--Why Book with us?-->
                <article class="default clearfix">
                    <h2>Why Book with us?</h2>
                    <h3>Low rates</h3>
                    <p>Get the best rates, or get a refund.<br />No booking fees. Save money!</p>
                    <h3>Largest Selection</h3>
                    <p>140,000+ hotels worldwide<br />130+ airlines<br />Over 3 million guest reviews</p>
                    <h3>We’re Always Here</h3>
                    <p>Call or email us, anytime<br />Get 24-hour support before, during, and after your trip</p>
                </article>
                <!--//Why Book with us?-->

                <!--Popular hotels in the area-->
                <article class="default clearfix">
                    <h2>Popular hotels</h2>
                    <ul class="popular-hotels">
                        <c:forEach var="hotel" items="${requestScope.hotels}">
                            <a href="/HotelWebApplication/HotelView?hotel_id=${hotel.hotelID}">
                                <h3>${hotel.hotelName}
                                    <span class="stars">
                                        <c:forEach begin="1" end="${hotel.star}" varStatus="loop">
                                            <img src="images/ico/star.png" alt="" />
                                        </c:forEach>
                                    </span>
                                </h3>
                                <p>From <span class="price">${hotel.roomList.get(0).getRoomPriceList().get(0).getPrice()} VNĐ <small>/ per night</small></span></p>
                                <span class="rating"> ${hotel.rating} /10</span>
                            </a>
                            </li>
                        </c:forEach>  
                        <li>
                    </ul>
                    <a href="HotelView" title="Show all" class="show-all">Show all</a>
                </article>
                <!--//Popular hotels in the area-->

            </aside>
            <!--//sidebar-->
        </div>
        <!--//main content-->
    </div>
</div>
<!--//main-->

<%@ include file="footer.jsp" %>