<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 11.02.2020
  Time: 21:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Edit meal</title>
</head>
<body>
<form method="POST" action='meals' name="frmAddMeal" accept-charset="UTF-8">
    <p><label for="id">ID: </label>      <input type="text"  readonly="true" id="id" name="id" value="${meal.ID}" ></p>
    <fmt:parseDate value="${ meal.dateTime }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
    <fmt:formatDate var="myDate" pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }"/>
    <p>Дата:     <input type="datetime-local"   id="date" name="date" value="${myDate}" ></p>
    <p>Описание: <input type="text" name="description" value="${meal.description}" ></p>
    <p>Каллории: <input type="number" min="0" max="10000" name="calories" value="${meal.calories}" ></p>
    <p><input type="submit" value="Отправить"><input type="button" onclick="window.history.back()" value="Отмена"></p>
</form>
</body>
</html>
