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
    function validate() {
        
        var username = $("input#username").val();
        var password = $("input#password").val();
        if (username == "" || password == "") {
            $('#message').html('You must fill in all of the fields!');
            return false;
        }
        
    }
    
    $(function() {
        $('#password').keydown(function(e) {
            if (e.keyCode === 13) {
                $('#login_form').submit();
            }
        });
    })
</script>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>

<!--main-->
<div class="main" role="main">		
    <div class="wrap clearfix">
        <section>
            <div style='width: 500px; margin: 0 auto;text-align: center;'>
                
                <form id="login_form" method="post" action="/HotelWebApplication/Login" onsubmit="return validate()">
                    <h1>Login</h1>
                    <p><label>Username: <input type="text" id="username" name="username" placeholder="Username"/></label></p>

                    <p><label>Password: <input type="password" id="password" name="password"/></label></p>
                    <p id="message" class="error" style="color: red">
                        <c:if test="${not empty requestScope.isLoginFalse}">
                            LOL! Your username or password is incorrect...
                        </c:if>
                    </p>
                    <p><a href="images/nho3.png">Forget password !</a></p>
                    <hr>
                    <p><a href="#" class="gradient-button" onclick="$('#login_form').submit()">Login</a></p>
                </form>
                <h5>
                    If you don't have an account, please  <a href="/HotelWebApplication/Register">Register</a>
                </h5>
            </div>
        </section>
    </div>
</div>
<!--//main-->
<%@ include file="footer.jsp" %>