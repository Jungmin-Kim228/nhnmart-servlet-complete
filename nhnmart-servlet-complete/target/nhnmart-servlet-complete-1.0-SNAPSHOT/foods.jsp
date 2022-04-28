<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>FoodStand</title>
</head>
<body>
<c:set var="foods" value="${foodStand}"></c:set>
<h1>FoodStand</h1>
<ol>
    <c:forEach var="f" items="${foods.getFoods()}">
        <li><c:out value="${f.getName()}"></c:out></li>
        <ul>
            <li>Price: <c:out value="${f.getPrice()}"></c:out></li>
            <li>Amount: ${f.getAmount()}</li>
        </ul>
    </c:forEach>
</ol>


<hr>
<h1>Pick Foods You Want</h1>
<form method="post" action="${pageContext.request.contextPath}/cart.do">
    <c:forEach var="f" items="${foods.getFoods()}">
        <input type="number" name="food"> <c:out value="${f.getName()}"></c:out>
        <br><br>
    </c:forEach>
    <input type="submit"/>
</form>
</body>
</html>
