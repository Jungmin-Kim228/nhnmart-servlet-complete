<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath}/login.do">
  ID: <input type="text" name="id" /><br>
  PW: <input type="password" name="pw" /><br>
  <input type="submit" value="Login" />
</form>
</body>
</html>
