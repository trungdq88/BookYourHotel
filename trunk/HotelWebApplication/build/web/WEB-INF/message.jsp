<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : message
    Created on : Oct 15, 2013, 4:50:29 PM
    Author     : ThuHoa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>

<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<script>
  setTimeout(function() {
    $('#link').click();
  }, 3000);
</script>
<!--main-->
<div class="main" role="main">		
    <div class="wrap clearfix">
        <section>
            <div style='width: 500px; margin: 0 auto;text-align: center;'>
                <h1>
                        <p>${requestScope.message}</p>
                        <h2><a id="link" href="${requestScope.link}">${requestScope.linktext}</a></h2>
                </h1>
            </div>
        </section>
    </div>
</div>
<!--//main-->
<%@ include file="footer.jsp" %>