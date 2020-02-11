<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 09.02.2020
  Time: 20:10
  To change this template use File | Settings | File Templates.
<%@page import="ru.javawebinar.topjava.model.MealTo"%>
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<table border=1>
    <thead>
    <tr>
        <th>User Id</th>
        <th>Дата/Время</th>
        <th>Описание</th>
        <th>Калории</th>

        <th colspan=2>Action</th>
    </tr>
    </thead>

    <tbody>
    <%--  <jsp:useBean id="meals" scope="request" type="java.util.List<ru.javawebinar.topjava.model.MealTo>"/> --%>
    <c:forEach items="${meals}" var="meal">
        <tr bgcolor="${meal.excess == true ? "red": "" }">
            <td><c:out value="${meal.ID}"/></td>
            <fmt:parseDate value="${ meal.dateTime }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
            <td> <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }"/></td>
            <td><c:out value="${meal.description}"/></td>
            <td><c:out value="${meal.calories}"/></td>
            <td><a href="meals?action=edit&Id=<c:out value="${meal.ID}"/>">Update</a></td>
            <td><a href="meals?action=delete&Id=<c:out value="${meal.ID}"/>">Delete</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<p><a href="meals?action=add">Add meal</a></p>
</body>

</body>
</html>
