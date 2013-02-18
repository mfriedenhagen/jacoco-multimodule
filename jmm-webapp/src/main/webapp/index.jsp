<%@page import="com.github.mfriedenhagen.jmm.core.App,java.util.Date" %>
<html>
<body>
<%
    final App app = new App();
    final Date date = app.getDate();
    final String nameFromParameter = request.getParameter("name");
    final String name;
    if (nameFromParameter == null) {
        name = "World";
    } else if (nameFromParameter.equals("Peter")) {
        throw new IllegalArgumentException("'Peter' is not an allowed name");
    } else {
        name = nameFromParameter;
    }
%>
<h2>Hello <%= name %>!</h2>
<p>Date: <%= app.getDate() %></p>
</body>
</html>
