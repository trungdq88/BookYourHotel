<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:if test="${not sessionScope.member.admin}">
  <c:redirect url="login.jsp" />
</c:if>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Create Book</title>
    <link rel="stylesheet" type="text/css" href="images/samples.css">
    <script type="text/javascript" language="JavaScript">
      function isRealNum(strText) {
        var RealPattern = /^\d+(\.\d+)?$/i;
        return (RealPattern.test(strText));
      }

      function trim(strText) {
        while (strText.substring(0, 1) === ' ')
          strText = strText.substring(1, strText.length);
        while (strText.substring(strText.length - 1, strText.length) === ' ')
          strText = strText.substring(0, strText.length - 1);
        return strText;
      }

      function validate(f) {
        var name = trim(f.name.value);
        var price = trim(f.price.value);
        if (name.length < 3) {
          alert("Invalid book title");
          f.name.focus();
          return false;
        }
        if (!isRealNum(price)) {
          alert("Invalid book price");
          f.price.focus();
          return false;
        }
        return true;
      }
    </script>
  </head>
  <body>
    <div id="divcenter">
      Welcome <b>${sessionScope.member.username}</b> [<a href="LogoutServlet">Logout</a>]
      <h1>Create Book</h1>
      <form action="InsertServlet" method="post" onSubmit="return validate(this);">
        <table>
          <tr>
            <td>Title:</td>
            <td><input type="text" name="name" value="" /></td>
          </tr>
          <tr>
            <td>Price:</td>
            <td><input type="text" name="price" value="" /></td>
          </tr>
          <tr>
            <td colspan="2" class="center">
              <input type="submit" value="Create" /> 
              <input type="button" value="Cancel" onclick="history.back();" />
            </td>
          </tr>
        </table>
      </form>
    </div>
    <jsp:include page="footer.jsp" />
  </body>
</html>
