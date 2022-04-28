<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<c:set var="basket" value="${basket}"></c:set>
<h1>[Your Basket]</h1><br>
<c:forEach var="f" items="${basket.getFoods()}">
    <c:if test="${f.getAmount() != 0}" var="testResult">
        name: <c:out value="${f.getName()}"></c:out><br>
        Amount: <c:out value="${f.getAmount()}"></c:out><br>
        Price: <c:out value="${f.getPrice()}"></c:out><br>
        --------------------<br>
    </c:if>
</c:forEach>

[Total: <c:out value="${basket.getSumPrice()}"></c:out>]<br>
<a href='/logout'>logout</a>
</body>
</html>
