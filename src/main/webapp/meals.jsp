<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru">
<head>
    <title>Meals</title>
</head>

<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>

<a href="addMeal.jsp">Add Meal</a>

<br>
<br>

<table border=1>
    <thead>
        <tr>
            <th>Id</th>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
    </thead>

    <tbody>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
            <tr style="color:${meal.excess ? "red" : "green"}">
                <td>${meal.id}</td>
                <td>${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}</td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meal?id=${meal.id}">Update</a></td>
                <td>
                    <form action="deleteMeal.jsp" method="post">
                    <input type="hidden" name="id" value="${meal.getId()}">
                    <input type="submit" value="Delete">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

</body>
</html>