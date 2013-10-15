<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:if test="${not sessionScope.member.user}">
  <c:redirect url="login.jsp" />
</c:if>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Shopping Cart</title>
    <link rel="stylesheet" type="text/css" href="images/samples.css">
  </head>
  <body>
    <div id="divcenter">
      Welcome <b>${sessionScope.member.username}</b> [<a href="LogoutServlet">Logout</a>]
      <h1>Shopping Cart</h1>
      [<a href="ShowServlet">Buy more</a>]
      <c:choose>
        <c:when test="${empty sessionScope.cart.contents}">
          <h2>Cart is empty! Buy something!</h2>
        </c:when>
        <c:otherwise>
          [<a href="CartServlet?action=out">Check out</a>]
          <table id="hor-minimalist-b">
            <tr>
              <th>No</th>
              <th>Name</th>
              <th>Price</th>
              <th>Quantity</th>
              <th>Remove</th>
            </tr>
            <c:forEach var="book" items="${sessionScope.cart.contents}" varStatus="i">
              <tr>
                <td class="center">${i.count}</td>
                <td>${book.name}</td>
                <td class="center">${book.price}</td>
                <td class="center">${book.quantity}</td>
                <td class="center"><a href="CartServlet?action=remove&id=${book.id}">Remove</a></td>
              </tr>
            </c:forEach>
            <tr>
              <td colspan="5" class="center">
                Total = <fmt:formatNumber maxFractionDigits="2" value="${sessionScope.cart.total}" />
              </td>
            </tr>
          </table>
        </c:otherwise>
      </c:choose>
    </div>
    <jsp:include page="footer.jsp" />
  </body>
</html>
