<%--
  Created by IntelliJ IDEA.
  User: max
  Date: 6/7/21
  Time: 10:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Delete meal</title>
</head>
<body>
Do you really want to delete meal# ${param.id}?
<br>

<form action="/meals/${param.id}" method="post">
    <input type="hidden" name="id" value="${param.id}">
    <input type="hidden" name="_method" value="delete">
    <input type="submit" value="Delete">
    <a href="index.html"><input type="button"  value="Cancel"></a>
</form>

</body>
</html>
