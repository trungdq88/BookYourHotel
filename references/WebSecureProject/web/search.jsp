<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:if test="${empty sessionScope.member}">
  <c:redirect url="login.jsp" />
</c:if>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Search Book</title>
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
        var min = trim(f.min.value);
        var max = trim(f.max.value);
        if (min === '' || (!isRealNum(min)) || max === '' || (!isRealNum(max))) {
          alert("Invalid book price (min or max)");
          f.min.focus();
          return false;
        }
        if (parseFloat(max) < parseFloat(min)) {
          alert("Max price is greater than min price");
          f.min.focus();
          return false;
        }
        return true;
      }

      function send(findby) {
        document.findForm.action = "SearchServlet?action=" + findby;
        if (findby === "By Price") {
          if (validate(document.findForm))
            document.findForm.submit();
        } else
          document.findForm.submit();
      }
    </script>
  </head>
  <body>
    <div id="divcenter">
      Welcome <b>${sessionScope.member.username}</b> [<a href="LogoutServlet">Logout</a>]
      <h1>Search Book</h1>
      [<a href="ShowServlet">Home</a>]
      <form name="findForm" action="" method="post">
        <table>
          <tr>
            <td colspan="2" class="center">Search By Title</td>
            <td colspan="2" class="center">Search By Price</td>
          </tr>
          <tr>
            <td>Keyword:</td><td><input type="text" name="name" value="" /></td>
            <td>Min price:</td><td><input type="text" name="min" value="" /></td>
          </tr>
          <tr>
            <td colspan="2" class="center"><input type="button" value="By Title" name="action" onclick="send('By Title');" /></td>
            <td>Max price:</td><td><input type="text" name="max" value="" /></td>
          </tr>
          <tr>
            <td></td><td></td>
            <td colspan="2" class="center"><input type="button" value="By Price" name="action" onclick="send('By Price');"  /></td>
          </tr>
        </table>
      </form>
    </div>
    <jsp:include page="footer.jsp" />
  </body>
</html>
