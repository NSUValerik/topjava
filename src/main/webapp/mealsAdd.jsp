<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
    <link type="text/css"
          href="WEB-INF/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet"/>
    <script type="text/javascript" src="WEB-INF/js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="WEB-INF/js/jquery-ui-1.8.18.custom.min.js"></script>
    <title>Add new meal</title>
</head>
<body>
<script>
    $(function () {
        $('input[name=dob]').datepicker();
    });
</script>
<c:set var="meal" value="${requestScope.meal}"/>
<form method="POST" action='meals' name="addMeal">
    Meal ID : <input type="text" readonly="readonly" name="id"
                     value="<c:out value="${meal.id}" />"/> <br/>
    <javatime:format value="${meal.dateTime}" pattern="yyyy-MM-dd hh:mm:ss" var="parsedDate"/>
    dateTime : <input type="text" name="dateTime"
                      value="<c:out value="${parsedDate}" />"/> <br/>

    description : <input type="text" name="description"
                         value="<c:out value="${meal.description}" />"/> <br/>
    calories : <input type="text" name="calories"
                      value="<c:out value="${meal.calories}" />"/> <br/>

    <input type="submit" value="Submit"/>
</form>
</body>
</html>