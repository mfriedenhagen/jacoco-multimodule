<%@page import="com.github.mfriedenhagen.jmm.core.App,java.util.Date" %>
<html>
<body>
<h2>Hello World!</h2>
<%
    App app = new App();
    Date date = app.getDate();
%>
<p>Date: XXX <%= app.getDate() %></p>
</body>
</html>
