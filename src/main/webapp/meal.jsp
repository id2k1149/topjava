<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Add meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Add or edit meal</h2>
<br>
<br>

<form action = "/topjava/meals" method="post">
  <input type="hidden" name = "id" value="${param.id}"><br>

  <label>Date Time: <input type="datetime-local"
                           name="dateTime"
                           value="${param.dateTime}"
                           placeholder="${param.dateTime}"></label><br><br>

  <label>Description: <input type="text"
                             name="description"
                             value="${param.description}"
                             placeholder="${param.description}"></label><br><br>

  <label>Calories: <input type="text"
                          name="calories"
                          value="${param.calories}"
                          placeholder="${param.calories}"></label><br><br>

  <input type="hidden" name="_method" value="put">
  <input type="submit" value="Save">
  <a href="index.html"><input type="button"  value="Cancel"></a>
</form>

</body>
</html>
