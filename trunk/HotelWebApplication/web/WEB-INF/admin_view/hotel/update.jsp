<%-- 
    Document   : update
    Created on : Oct 9, 2013, 9:52:09 AM
    Author     : ThuHoa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Hotel</title>
    </head>
    <body>
    <center>
        <h1>Update Hotel</h1>
        <form action="HotelManager" method="POST">
            <table>
                <tr>
                    <td>Hotel Id</td>
                    <td>
                        <input type="text" name="websiteName" value="${requestScope.id}"/>
                    </td>
                </tr>
                <tr>
                    <td>Website Name</td>
                    <td>
                        <input type="text" name="websiteName" value=""/>
                    </td>
                </tr>
                <tr>
                    <td>Link Detail</td>
                    <td>
                        <input type="text" name="linkDetail" value=""/>
                    </td>
                </tr>
                <tr>
                    <td>Hotel Name</td>
                    <td>
                        <input type="text" name="hotelName" value=""/>
                    </td>
                </tr>
                <tr>
                    <td>Description</td>
                    <td>
                        <input type="text" name="description" value=""/>
                    </td>
                </tr>
                <tr>
                    <td>Star</td>
                    <td>
                        <input type="text" name="star" value=""/>
                    </td>
                </tr>
                <tr>
                    <td>Image Link</td>
                    <td>
                        <input type="text" name="imageLink" value=""/>
                    </td>
                </tr>
                <tr>
                    <td>Rating</td>
                    <td>
                        <input type="text" name="rating" value=""/>
                    </td>
                </tr>
                <tr>
                    <td>Other Properties</td>
                    <td>
                        <input type="text" name="otherProperties" value=""/>
                    </td>
                </tr>
                <tr>
                    <td>Province Id</td>
                    <td>
                        <input type="text" name="provinceId" value=""/>
                    </td>
                </tr>
                <tr>
                    <td>District Id</td>
                    <td>
                        <input type="text" name="districtId" value=""/>
                    </td>
                </tr>
                <tr>
                    <td>Ward Id</td>
                    <td>
                        <input type="text" name="wardId" value=""/>
                    </td>
                </tr>
                <tr>
                    <td>Address</td>
                    <td>
                        <input type="text" name="address" value=""/>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="submit" name="submit" value="Insert"/>
                    </td>
                </tr>
            </table>
        </form>
    </center>
    <c:if test="${not empty requestScope.check}">
        <c:choose>
            <c:when test="${requestScope.check}">
                <h1 style="color: blue">Update success: ${requestScope.websiteName}</h1>
            </c:when>
            <c:otherwise>
                <h1 style="color: red">Update fail: ${requestScope.error}</h1>
            </c:otherwise>
        </c:choose>
    </c:if>

</body>


</html>
