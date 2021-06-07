<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Add meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Add meal</h2>
<br>
<br>


  <form action = "/meals" method="post">
    <table>
      <td>Date Time:</td>
      <td><input required type="datetime-local"
                 name="dateTime"
                 placeholder="dateTime"
                 value="${meal.dateTime}"></td>
    </table>

    <table>
      <td>Description:</td>
      <td><input required type="text"
                 name="description"
                 placeholder="description"
                 value="${meal.description}"></td>
    </table>

    <table>
      <td>Calories:</td>
      <td><input required type="text"
                 name="calories"
                 placeholder="calories"
                 value="${meal.calories}"></td>
    </table>

    <input type="submit" value="Save">
    <a href="index.html"><input type="button"  value="Cancel"></a>

  </form>

</body>
</html>
