<%-- 
    Document   : login
    Created on : Oct 15, 2013, 1:51:17 AM
    Author     : ThuHoa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
    <center>
        <h1>Login</h1>
        <form action="LoginServlet" method="POST">
        <table>
            <tr>
                <td>Username: </td>
                <td>
                    <input type="text" name="username" value=""/>
                </td>
            </tr>
            <tr>
                <td>Password: </td>
                <td>
                    <input type="password" name="password" value=""/>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" name="login" value="Login"/>
                </td>
            </tr>
        </table>
            </form>
        </center>
    </body>
</html>
