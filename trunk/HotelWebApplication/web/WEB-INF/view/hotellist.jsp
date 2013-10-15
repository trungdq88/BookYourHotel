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

<script>
    $(function() {
        $("input#province").autocomplete({
            source: function(request, response) {
                $.ajax({
                    type: "POST",
                    url: "/HotelWebApplication/AJAXServlet",
                    dataType: "text",
                    data: {
                        type: "search_province",
                        province: $("input#province").val()
                    },
                    success: function(data) {
                        data = JSON.parse(data);
                        console.log(data);
                        response($.map(data, function(province) {
                            return {
                                label: province.name,
                                value: province.name,
                                id: province.provinceid
                            };
                        }));
                    }
                });
            },
            select: function(event, ui) {
                $('#province_id').val(ui.item.id);
            }
        });

        $("input#district").autocomplete({
            source: function(request, response) {
                $.ajax({
                    type: "POST",
                    url: "/HotelWebApplication/AJAXServlet",
                    dataType: "text",
                    data: {
                        type: "search_district",
                        province: $("input#province").val()
                    },
                    success: function(data) {
                        data = JSON.parse(data);
                        console.log(data);
                        response($.map(data, function(district) {
                            return {
                                label: district.name,
                                value: district.name,
                                id: district.districtid
                            };
                        }));
                    }
                });
            },
            select: function(event, ui) {
                $('#district_id').val(ui.item.id);
            }
        });

        $("input#ward").autocomplete({
            source: function(request, response) {
                $.ajax({
                    type: "POST",
                    url: "/HotelWebApplication/AJAXServlet",
                    dataType: "text",
                    data: {
                        type: "search_ward",
                        district: $("input#district").val()
                    },
                    success: function(data) {
                        data = JSON.parse(data);
                        console.log(data);
                        response($.map(data, function(ward) {
                            return {
                                label: ward.name,
                                value: ward.name,
                                id: ward.wardid
                            };
                        }));
                    }
                });
            },
            select: function(event, ui) {
                $('#ward_id').val(ui.item.id);
            }
        });

    });
</script> 

<!--slider-->
<section class="slider clearfix">
    <div id="sequence">
        <ul>
            <li>
                <div class="info animate-in">
                    <h2>Last minute Winter escapes</h2><br />
                    <p>January 2013 holidays 40% off! An unique opportunity to realize your dreams</p>
                </div>
                <img class="main-image animate-in" src="images/slider/img3.jpg" alt="" />
            </li>
            <li>
                <div class="info animate-in">
                    <h2>Check out our top weekly deals</h2><br />
                    <p>Save Now. Book Later.</p>
                </div>
                <img class="main-image animate-in" src="images/slider/img1.jpg" alt="" />
            </li>
            <li>
                <div class="info animate-in">
                    <h2>Check out last minute flight, hotel &amp; vacation offers!</h2><br />
                    <p>Save up to 50%!</p>
                </div>
                <img class="main-image animate-in" src="images/slider/img2.jpg" alt="" />
            </li>
        </ul>
    </div>
</section>
<!--//slider-->

<!--search-->
<div class="main-search">
    <form id="main-search" method="get" action="/HotelWebApplication/Search">
        <!--column-->
        <div class="column radios">

        </div>
        <!--//column-->

        <div class="forms">
            <!--form hotel-->
            <div class="form" id="form1" style="display: block;">
                <!--column-->
                <div class="column">
                    <h4>Where?</h4>
                    <div class="f-item">
                        <label for="destination1">Your destination</label>
                        <input style="margin: 5px 0" type="text" placeholder="Enter city / provinces" id="province" />
                        <input style="margin: 5px 0" type="text" placeholder="Enter district" id="district" />
                        <input style="margin: 5px 0 20px" type="text" placeholder="Enter wards" id="ward" />
                        <input id="province_id" value="" name="province" type="hidden"/>
                        <input id="district_id" value="" name="district" type="hidden"/>
                        <input id="ward_id" value="" name="ward" type="hidden"/>
                    </div>
                </div>
                <!--//column-->

                <!--column-->
                <div class="column twins">
                    <h4>When?</h4>
                    <div class="f-item datepicker">
                        <label for="datepicker1">Check-in date</label>
                        <div class="datepicker-wrap"><input name="checkin" type="text" placeholder="" id="datepicker1" name="datepicker1" /></div>
                    </div>
                    <div class="f-item datepicker">
                        <label for="datepicker2">Check-out date</label>
                        <div class="datepicker-wrap"><input name="checkout" type="text" placeholder="" id="datepicker2" name="datepicker2" /></div>
                    </div>
                </div>
                <!--//column-->

                <!--column-->
                <div class="column triplets">

                </div>
                <!--//column-->
            </div>	
            <!--//form hotel-->
        </div>
        <input type="submit" value="SEARCH HOTEL" class="search-submit" id="search-submit" />
    </form>
</div>
<!--//search-->

<!--main-->
<div class="main" role="main">		
    <div class="wrap clearfix">
        <!--deals-->
        <section class="full">
            <h1>Most popular Hotels</h1>
            <div class="mosthotel deals clearfix">

                <c:forEach var="hotel" items="${requestScope.hotels}" varStatus="index">
                    <!--deal-->
                    <article class="one-fourth">
                        <figure><a href="#" title=""><img src="images/slider/img6.jpg" alt="" width="270" height="152" /></a></figure>
                        <div class="details">
                            <h1>${hotel.hotelName}
                                <span class="stars">
                                    <c:forEach begin="1" end="${hotel.star}" varStatus="loop">
                                        <img src="images/ico/star.png" alt="" />
                                    </c:forEach>
                                </span>
                            </h1>
                            <span class="address">${hotel.address}  •  <a href="#">Show on map</a></span>
                            <span class="rating"> ${hotel.rating} /10</span>
                            <span class="price"> <em>Price from: <span  style="color: red;"><fmt:formatNumber type="number" value="${hotel.roomList.get(0).getRoomPriceList().get(0).getPrice()}" /></span> VNĐ</em></span>
                            <div class="description">
                                <p>${fn:substring(hotel.description, 0, 200)}...</p>
                            </div>
                            <a href="HotelView?hotel_id=${hotel.hotelID}" title="Book now" class="gradient-button">More info</a>
                        </div>
                    </article>
                    <!--//deal-->
                </c:forEach>
            </div>
        </section>	
        <!--//deals-->
        <style>
            .destinations .one-fourth img{
                width: 100%;
            }
            .destinations .one-fourth {
                width: 23%
            }
        </style>
        <!--top destinations-->
        <section class="destinations clearfix last">
            <h1>Top destinations</h1>
            <c:forEach var="province" items="${requestScope.provinces}">
                <!--column-->
                <article class="one-fourth">
                    <figure><a href="/HotelWebApplication/Search?province=${province.provinceid}&district=&ward=&checkin=&checkout=&curPage=1" title=""><img src="images/uploads/saint-petersburg.jpg" alt="" width="270" height="152" /></a></figure>
                    <div class="details">
                        <a href="/HotelWebApplication/Search?province=${province.provinceid}&district=&ward=&checkin=<fmt:formatDate pattern="MM/dd/yyyy" value="${requestScope.today}"/>&checkout=&curPage=1" title="View all" class="gradient-button">View all</a>
                        <h5>${province.name}</h5>
                        <span class="count">${province.getHotelList().size()} Hotels</span>

                    </div>
                </article>
                <!--//column-->
            </c:forEach>

        </section>
        <!--//top destinations-->
    </div>
</div>
<!--//main-->
<%@ include file="footer.jsp" %>