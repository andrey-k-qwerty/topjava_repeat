<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 09.02.2020
  Time: 20:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
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
    <c:forEach items="${meals}" var="meal">
        <tr>
<%--            <td><c:out value="${meal.id}" /></td>--%>
<%--            <td><fmt:formatDate pattern="yyyy-MMM-dd" value="${meal.dob}" /></td>--%>
            <td><c:out value="${meal.description}" /></td>
<%--            <td><c:out value="${meal.lastName}" /></td>--%>
<%--            <td><c:out value="${meal.email}" /></td>--%>
<%--            <td><a href="UserController?action=edit&userId=<c:out value="${meal.userid}"/>">Update</a></td>--%>
<%--            <td><a href="UserController?action=delete&userId=<c:out value="${meal.userid}"/>">Delete</a></td>--%>
        </tr>
    </c:forEach>
    </tbody>
</table>
<p><a href="UserController?action=insert">Add User</a></p>
</body>

</body>
</html>
