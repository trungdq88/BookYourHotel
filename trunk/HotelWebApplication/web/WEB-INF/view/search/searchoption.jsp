<%-- 
    Document   : searchoption
    Created on : Oct 10, 2013, 4:26:45 PM
    Author     : Quang Trung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search</title>
    </head>
    <body>
        <div style="text-align: center; margin: 10% auto;">
            <h3>Enter keyword:</h3>
            <form action="Search" method="get">
                <input type="text" name="keyword" placeholder="Khách sạn ngàn sao..."/>
                <input type="submit" value="Search"/>
            </form>
        </div>
    </body>
</html>
