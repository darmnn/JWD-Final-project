<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Photobook</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    <link href="css/profile.css" rel="stylesheet" type="text/css">
    <link href="css/main.css" rel="stylesheet" type="text/css">
    
    <fmt:setLocale value="${sessionScope.locale }"/>
    <fmt:setBundle basename="locale" var="loc"/>
    
    <fmt:message bundle="${loc}" key="button.logout" var="logout"/>
    <fmt:message bundle="${loc}" key="button.edit" var="edit"/>
    <fmt:message bundle="${loc}" key="button.save" var="save"/>
    <fmt:message bundle="${loc}" key="button.en" var="en"/>
    <fmt:message bundle="${loc}" key="button.ru" var="ru"/>
    <fmt:message bundle="${loc}" key="button.add_photo" var="add_photo"/>
    
</head>
<body>
<div>
	<nav class="navbar navbar-light">
    	<a href = "index.jsp" class = "loadmain">PhotoBook</a>
    	<form class="header-profile" action="Controller" method="get">
        	<button class="btn btn-primary sign-up" type="submit" name = "command" value="logout">${logout }</button>
         </form>
    </nav>
    <form action="Controller" method="get" class="btn-group local" role="group" aria-label="Basic outlined example">
  				<button type="submit" name="command" value="en" class="btn btn-outline-primary">${en }</button>
  				<button type="submit" name="command" value="ru" class="btn btn-outline-primary">${ru }</button>
	</form>
			
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
    		<c:choose>
    			<c:when test="${requestScope.edit == true }">
    			<form action="Controller" method="post" class="form-edit">
    				<div class="card about">
                        <div class="card-body about-body">
                        	<textarea name="new_profile_desc" class="card-text">${sessionScope.user.profileDecs }</textarea>
                        </div>
                        <button type="submit" name="command" value="savechanges" class="btn btn-primary edit">${save }</button>
             		</div>
             			<input type="file" name="new_profile_pic"></input>
              			<button type="submit" name="command" value="savephotochanges" class="btn btn-primary edit">${save }</button>
              	</form>
    			</c:when>
    			<c:otherwise>
    				<div class="card about">
                    	<div class="card-body about-body">
                            <p class="card-text">${sessionScope.user.profileDecs }</p>
                        </div>
             		</div>
             		<c:if test = "${sessionScope.user.isPhotographer == true }">
                    	<c:forEach var = "i" begin = "1" end = "${sessionScope.user.totalRating }">
                       		<img src="images/star.png" class="float-left star"/>
                       	</c:forEach>
              		</c:if>
              		<form action="Controller" method="post" class="form-edit">
              			<button type="submit" name="command" value="editprofile" class="btn btn-primary edit">${edit }</button>
              		</form>
    			</c:otherwise>
    		</c:choose>
    	</div>
    </div>
    <c:if test = "${sessionScope.user.isPhotographer == true }">
    	<div class="container all-photos">
    		<c:out value="${message }"/>
            <c:forEach var = "photo" items="${photos}">
            	<img src="${photo.imagePath }" />
            </c:forEach>
            <c:choose>
            	<c:when test="${requestScope.add_photo == true }">
            		<form action="Controller" method="post">
            			<input type="file" name="new_photo"></input>
              			<button type="submit" name="command" value="savenewphoto" class="btn btn-primary edit">${save }</button>
            		</form>
            	</c:when>
            	<c:otherwise>
            		<form action="Controller" method="post">
            			<button type="submit" name="command" value="addphoto" class="btn btn-primary">${add_photo }</button>
            		</form>
            	</c:otherwise>
            </c:choose>
     	</div>
    </c:if>
</div>
</body>
</html>