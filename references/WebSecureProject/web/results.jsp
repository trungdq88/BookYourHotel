<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:if test="${empty sessionScope.member}">
  <c:redirect url="login.jsp" />
</c:if>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Selected Books</title>
    <link rel="stylesheet" type="text/css" href="images/samples.css">
  </head>
  <body>
    <div id="divcenter">
      Welcome <b>${sessionScope.member.username}</b> [<a href="LogoutServlet">Logout</a>]
      <h1>Selected Books</h1>
      [<a href="ShowServlet">Home</a>][<a href="search.jsp">Search More</a>]
      <c:choose>
        <c:when test="${empty requestScope.results}">
          <h2>Book not found!</h2>
        </c:when>
        <c:otherwise>
          <table id="hor-minimalist-b">
            <tr>
              <th>No</th>
              <th>ID</th>
              <th>Title</th>
              <th>Price</th>
            </tr>
            <c:forEach var="book" items="${requestScope.results}" varStatus="i">
              <tr>
                <td class="center">${i.count}</td>
                <td class="center">${book.id}</td>
                <td>${book.name}</td>
                <td class="center">${book.price}</td>
              </tr>
            </c:forEach>
          </table>
        </c:otherwise>
      </c:choose>
    </div>
    <jsp:include page="footer.jsp" />
  </body>
</html>
