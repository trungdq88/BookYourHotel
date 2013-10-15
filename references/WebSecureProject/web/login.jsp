<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="images/samples.css">
  </head>
  <body>
    <div id="divcenter">
      <h1>Login</h1>
      <form action="LoginServlet" method="post">
        <table>
          <tr>
            <td>User name:</td>
            <td><input type="text" name="username" value="" /></td>
          </tr>
          <tr>
            <td>Password:</td>
            <td><input type="password" name="password" value="" /></td>
          </tr>
          <tr>
            <td colspan="2" class="center">
              <input type="submit" value="Login" /> 
              <input type="button" value="Cancel" onclick="history.back();" /> 
            </td>
          </tr>
        </table>
      </form>
    </div>
    <jsp:include page="footer.jsp" />
  </body>
</html>
