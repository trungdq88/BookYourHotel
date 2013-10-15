<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Books</title>
    <link rel="stylesheet" type="text/css" href="images/samples.css">
    <script type="text/javascript" language="JavaScript">
      function yesno(id) {
        answer = confirm("Sure?");
        if (answer)
          window.location = "DeleteServlet?id=" + id;
      }
    </script>
  </head>
  <body>
    <div id="divcenter">
      <c:choose>
        <c:when test="${empty sessionScope.member}">
          Welcome <b>Guest</b> [<a href="login.jsp">Login</a>]
        </c:when>
        <c:otherwise>
          Welcome <b>${sessionScope.member.username}</b> [<a href="LogoutServlet">Logout</a>]
        </c:otherwise>
      </c:choose>
      <h1>Books</h1>
      <c:if test="${sessionScope.member.admin}">
        [<a href="insert.jsp">Create Book</a>]
      </c:if>
      <c:if test="${not empty sessionScope.member}">
        [<a href="search.jsp">Search Book</a>]
      </c:if>
      <c:if test="${sessionScope.member.user}">
        [<a href="CartServlet?action=view">View Cart</a>]
      </c:if>
      <br/>
      <table id="hor-minimalist-b">
        <!-- header -->
        <tr>
          <th class="center">No</th>
          <th class="center">ID</th>
          <th>Title</th>
          <th class="center">Price</th>
            <c:if test="${sessionScope.member.admin}">
            <th class="center">Update</th>
            <th class="center">Delete</th>
            </c:if>
            <c:if test="${sessionScope.member.user}">
            <th class="center">Buy</th>
            </c:if>
        </tr>
        <!-- body -->
        <c:forEach var="book" items="${requestScope.books}" varStatus="index">
          <tr>
            <td class="center">${index.count}</td>
            <td class="center">${book.id}</td>
            <td>${book.name}</td>
            <td class="center">${book.price}</td>
            <c:if test="${sessionScope.member.admin}">
              <td class="center"><a href="update.jsp?id=${book.id}&name=${book.name}&price=${book.price}">Update</a></td>
              <td class="center"><a class="delete" href="javascript:void(0)" onclick="yesno('${book.id}');">Delete</a></td>
            </c:if>
            <c:if test="${sessionScope.member.user}">
              <td class="center"><a href="CartServlet?action=add&id=${book.id}">Buy</a></td>
            </c:if>
          </tr>
        </c:forEach>  
      </table>
    </div>
    <jsp:include page="footer.jsp" />
  </body>
</html>
