<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="now" scope="page" class="java.util.Date" />
<p align="center" style="font:8.5pt Tahoma;">
  <img src="images/cpr.gif" width="60" height="53" alt="copyright" /><br />
  © Copyright by <b>${initParam.copyright}</b><br />
  <fmt:formatDate value="${now}" pattern="EEE, MMM dd, yyyy"/>
</p>