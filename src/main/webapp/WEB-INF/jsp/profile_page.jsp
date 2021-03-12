<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Photobook</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    <link href="css/main.css" rel="stylesheet" type="text/css">
    <link href="css/profile.css" rel="stylesheet" type="text/css">
</head>
<body>
<div>
	<nav class="navbar navbar-light">
    	<a href = "index.jsp" class = "loadmain">PhotoBook</a>
    	<form class="header-profile" action="Controller" method="get">
        	<button class="btn btn-primary sign-up" type="submit" name = "command" value="logout">Log out</button>
         </form>
    </nav>
    <div class="container outer">
    	<c:choose>
    	<c:when test="${sessionScope.user.profilePicPath != null}">
    	<img src="${sessionScope.user.profilePicPath }" class="rounded-circle float-left"/>
    	</c:when>
    	<c:otherwise>
    	<img src="images/user_pic.png" class="rounded-circle float-left"/>
    	</c:otherwise>
    	</c:choose>
    	<div class="container inner">
    		<p class="username"><c:out value ="${sessionScope.user.username }"/> </p>
    		<div class="card about">
                        <div class="card-body about-body">
                            <p class="card-text">${sessionScope.user.profileDecs }</p>
                            <!-- <textarea class="card-text"></textarea>-->
                        </div>
             </div>
             <c:if test = "${sessionScope.user.isPhotographer == true }">
                        	<c:forEach var = "i" begin = "1" end = "${sessionScope.user.totalRating }">
                        	<img src="images/star.png" class="float-left star"/>
                        	</c:forEach>
              </c:if>
    	</div>
    </div>
    <c:if test = "${sessionScope.user.isPhotographer == true }">
    	<div class="container all-photos">
    		<c:out value="${message }"/>
            <c:forEach var = "photo" items="${photos}">
                        <img src="${photo.imagePath }" />
             </c:forEach>
     	</div>
    </c:if>
</div>
</body>
</html>