<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:if test="${not sessionScope.member.user}">
  <c:redirect url="login.jsp" />
</c:if>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Invoice</title>
    <link rel="stylesheet" type="text/css" href="images/samples.css">
  </head>
  <body>
    <div id="divcenter">
      Welcome <b>${sessionScope.member.username}</b> [<a href="LogoutServlet">Logout</a>]
      <h1>Invoice</h1>
      [<a href="ShowServlet">Home</a>]
      <table>
        <tr><td>Code</td><td>:</td><td>${param.code}</td>
        <tr><td>Name</td><td>:</td><td>${sessionScope.member.fullname}</td>
        <tr><td>Phone</td><td>:</td><td>${sessionScope.member.phone}</td>
        </tr>
      </table>
      <table id="hor-minimalist-b">
        <tr>
          <th>No</th>
          <th>Title</th>
          <th>Price</th>
          <th>Quantity</th>
          <th>Total Line</th>
        </tr>
        <c:set var="total" value="0" />
        <c:forEach var="book" items="${requestScope.books}" varStatus="i">
          <tr>
            <td class="center">${i.count}</td>
            <td>${book.name}</td>
            <td class="center">${book.price}</td>
            <td class="center">${book.quantity}</td>
            <td class="center"><fmt:formatNumber maxFractionDigits="2" value="${book.totalLine}" /></td>
          </tr>
          <c:set var="total" value="${total + book.totalLine}" />
        </c:forEach>
        <tr>
          <td colspan="5" class="center">
            Total = <fmt:formatNumber maxFractionDigits="2" value="${total}" />
          </td>
        </tr>
      </table>
    </div>
    <jsp:include page="footer.jsp" />
  </body>
</html>
