<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Photobook</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    <link href="css/main.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div class="main-container">
            <nav class="navbar navbar-light">
                <form class="header" action="Controller" method="post">
                    <div class="btn-group buttons" role="group" aria-label="Basic example">
                    	<button class="btn btn-primary sign-in" type="submit" name = "command" value="loadauthpage">Sign in</button>
                        <button class="btn btn-primary sign-up" type="submit" name = "command" value="loadregpage">Sign up</button>
                    </div>
                </form>
            </nav>
            <div class="name-main">PhotoBook</div>
            <div class="container all-photos">
            <c:forEach var = "photo" items="${photos}">
                        <img src="${photo.imagePath }" />
                </c:forEach>
            </div>
        </div>
</body>
</html>