<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<table>
    <c:set var="meals" value="${requestScope.meals}"/>
    <c:forEach items="${meals}" var="meal">
        <c:set var="color" value="${meal.exceed ? '#ff0000' : '#008000'}"/>
        <tr bgcolor=${color}>
            <javatime:format value="${meal.dateTime}" pattern="yyyy-MM-dd hh:mm" var="parsedDate"/>
            <td><c:out value="${parsedDate}"/></td>
            <td><c:out value="${meal.description}"/></td>
            <td><c:out value="${meal.calories}"/></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>