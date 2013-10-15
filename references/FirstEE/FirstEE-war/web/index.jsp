<%-- 
    Document   : index
    Created on : Aug 2, 2013, 6:42:02 PM
    Author     : Quang Trung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
  "http://www.w3.org/TR/html4/loose.dtd">

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP Page</title>
  </head>
  <body>
    <h1>Book list</h1>
    <p style="text-align: center;">
      [<a href="insert.jsp">Create Book</a>]
      [<a href="search.jsp">Search Book</a>]
      [<a href="CartServlet?action=view">View Cart</a>]
    </p>
    <table>
      <tr>
        <th>ID</th>
        <th>Title</th>
        <th>Price</th>
        <th>Tools</th>
        <th>Buy</th>
      </tr>
      <c:forEach var="book" items="${requestScope.books}">
        <tr>
          <td>${book.id}</td>
          <td>${book.name}</td>
          <td>${book.price}</td>
          <td>[<a href="update.jsp?id=${book.id}&name=${book.name}&price=${book.price}">Update</a>] [<a onclick="confirm_delete('${book.id}')" href="javascript: void(0);">Delete</a>]</td>
          <td>[<a href="CartServlet?action=add&id=${book.id}">Buy</a>]</td>
        </tr>
      </c:forEach>
    </table>
  </body>
</html>
