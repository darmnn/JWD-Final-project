<%@ page language="java" contentType="text/html;
    charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
 <link href="css/auth1.css" rel="stylesheet" type="text/css">
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Photobook : error</title>
</head>
<body>
		<nav class="navbar navbar-light">
        	<a href = "index.jsp" class = "loadmain">PhotoBook</a>
        </nav>
        <div class="container error-msg">
        	<h2>Error occured!!</h2>
			<c:out value="${message}" />
        </div>
</body>
</html>