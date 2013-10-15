<%-- 
    Document   : register
    Created on : Oct 9, 2013, 3:20:40 PM
    Author     : Minh LTN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Register</title>
  </head>
  <body>
  <center>
    <h1>Register</h1>
    <form action="Register" method="POST">
      
    <table>
      <tr>
        <td>Username</td>
        <td><input type="text" name="Username" value="" /></td>
      </tr>
      <tr>
        <td>Password</td>
        <td><input type="password" name="Password" value="" /></td>
      </tr>
      <tr>
        <td>Re-Password</td>
        <td><input type="password" name="RePassword" value="" /></td>
      </tr>
      <tr>
        <td>Email</td>
        <td><input type="text" name="Email" value="" /></td>
      </tr>
      <tr>
        <td colspan="2"><input type="submit" value="OK" /> <input type="button" value="Cancel" onclick="history.back()"/></td>
      </tr>
    </table>
    </form>
  </center>
  </body>
</html>
