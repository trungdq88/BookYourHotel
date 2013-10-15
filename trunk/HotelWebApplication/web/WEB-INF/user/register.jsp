
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%-- 
    Document   : register
    Created on : Oct 9, 2013, 3:20:40 PM
    Author     : Minh LTN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<head>
    <script>
        function trim(strText) {
            while (strText.substring(0, 1) === ' ')
                strText = strText.substring(1, strText.length);
            while (strText.substring(strText.length - 1, strText.length) === ' ')
                strText = strText.substring(0, strText.length - 1);
            return strText;
        }

        function validate(f) {
            var name = trim(f.username.value);
            var pass = f.password.value;
            var repass = f.repassword.value;
            if (name.length < 3) {
                $('#message').html('Invalid name (must be longer than 3 character)!');
                //  alert("Invalid name (must be longer than 3 character)");
                f.name.focus();
                return false;
            }
            if (pass.length < 3) {
                $('#message').html('Invalid password (must be longer than 3 character)');
                f.pass.focus();
                return false;
            }

            if (pass !== repass) {
                $('#message').html('Invalid repassword!!');
                f.repass.focus();
                return false;
            }
            return true;
        }
    </script>
</head>

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
                <form id="register_form" method="post" action="/HotelWebApplication/Register"  onSubmit="return validate(this);">
                    <div align="center">
                        <h1>Register</h1>
                        <c:if test="${not empty requestScope.isError}">
                            <h2>Register is failed!</h2>
                        </c:if>



                        <style>
                            .nicetable td {
                                padding: 4px 5px;
                            }
                        </style>

                        <table class="nicetable">
                            <tr>
                                <td align="left"><h5>Username </h5></td>
                                <td>
                                    <input type="text" name="username" placeholder="Username"/>
                                </td>
                                <td>
                                    <c:if test="${not empty requestScope.checkUsername}">
                                        <c:if test="${requestScope.checkUsername eq false}">
                                            <p style="color: red">Username was exit!</p>
                                        </c:if>
                                    </c:if>
                                </td>
                            </tr>



                            <tr>
                                <td align="left"><h5>Password:</h5></td>
                                <td><input type="password" name="password" placeholder="Enter your Password"/></td>
                            </tr>

                            <tr>
                                <td align="left"><h5>Re-Password:<h5></td>
                                            <td><input type="password" name="repassword" placeholder="Re-enter your password"/></td>
                                            </tr>

                                            <tr>
                                                <td align="left"><h5>Email:</h5></td>
                                                <td><input type="text" name="email" placeholder="Email address"/></td>
                                                <td>
                                                   <c:if test="${not empty requestScope.checkEmail}">
                                                        <c:if test="${requestScope.checkEmail eq false}">
                                                    <p style="color: red">Email was exit!</p>
                                                </c:if>
                                            </c:if> 
                                                </td>
                                                    
                                            </tr>

                                            <p id="message" class="error" style="color: red"></p>

                                            </table>
                                            <hr>
                                            <a href="javascript:;" class="gradient-button" onclick="$('#register_form').submit()">Register</a>
                                            </div> 


                                            <p></p>
                                            </form>

                                            </div>
                                            </section>
                                            </div>
                                            </div>
                                            <!--//main-->
                                            <%@ include file="footer.jsp" %>