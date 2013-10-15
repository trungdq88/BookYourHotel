<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : view
    Created on : Oct 8, 2013, 6:07:35 PM
    Author     : ThuHoa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>View Hotels</title>
  </head>
  <body>
  <center>
    <h1>List Hotels</h1>
    [<a href="HotelManager?action=insert">Insert</a>]
    <table>
      <tr>
        <th>Hotel Name</th>
        <th>Hotel Address</th>
        <th>Update</th>
        <th>Delete</th>
      </tr>
      <c:forEach var="hotel" items="${requestScope.hotels}">
        <tr>
          <td>${hotel.hotelName}</td>
          <td>${hotel.address}</td>
          <td>
            <a href="HotelManager?action=update&id=${hotel.hotelID}">Edit</a>
          </td>
          <td>
            <a href="HotelManager?action=delete&id=${hotel.hotelID}">Delete</a>
          </td>

        </tr>
      </c:forEach> 
    </table>
  </center>
  </body>
</html>
