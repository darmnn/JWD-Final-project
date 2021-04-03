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
    
    <fmt:message bundle="${loc}" key="button.sign_in" var="sign_in"/>
    <fmt:message bundle="${loc}" key="button.sign_up" var="sign_up"/>
    <fmt:message bundle="${loc}" key="button.logout" var="logout"/>
    <fmt:message bundle="${loc}" key="button.profile" var="profile"/>
    <fmt:message bundle="${loc}" key="button.en" var="en"/>
    <fmt:message bundle="${loc}" key="button.ru" var="ru"/>
    <fmt:message bundle="${loc}" key="button.photoshoots" var="photoshoots"/>
    
</head>
<body>
<div>

	<nav class="navbar navbar-light">
    	<c:choose>
  			<c:when test="${sessionScope.auth != null}">
    			<form class="header" action="Controller" method="get">
                	<button class="btn btn-primary profile" type="submit" name = "command" value="loadprofilepage">${profile }</button>
                   	<button class="btn btn-primary sign-up" type="submit" name = "command" value="logout">${logout }</button>
                </form>
  			</c:when>
  			<c:otherwise>
            	<form class="header" action="Controller" method="post">
                	<div class="btn-group buttons" role="group" aria-label="Basic example">
                    	<button class="btn btn-primary sign-in" type="submit" name = "command" value="loadauthpage">${sign_in }</button>
                        <button class="btn btn-primary sign-up" type="submit" name = "command" value="loadregpage">${sign_up }</button>
                    </div>
                </form>
 			</c:otherwise>
		</c:choose>
      </nav>
      <form action="Controller" method="get" class="btn-group local" role="group" aria-label="Basic outlined example">
  				<button type="submit" name="command" value="en" class="btn btn-outline-primary">${en }</button>
  				<button type="submit" name="command" value="ru" class="btn btn-outline-primary">${ru }</button>
			</form>
	<div class="container outer">
    	<c:choose>
    	<c:when test="${user.profilePicPath != null}">
    		<img src="${user.profilePicPath }" class="rounded-circle float-left img"/>
    	</c:when>
    	<c:otherwise>
    		<img src="images/user_pic.png" class="rounded-circle float-left"/>
    	</c:otherwise>
    	</c:choose>
    	<div class="container inner">
    		<p class="username"><c:out value ="${user.username }"/> </p>
    		<div class="card about">
            	<div class="card-body about-body">
                	<p class="card-text">${user.profileDecs }</p>
                </div>
            </div>
            <c:if test = "${user.isPhotographer == true }">
            	<c:forEach var = "i" begin = "1" end = "${user.totalRating }">
                	<img src="images/star.png" class="float-left star"/>
                </c:forEach>
                <c:if test ="${sessionScope.auth != null}">
                <form action="Controller" method="post" class="form-edit">
                	<input type="hidden" name="photographer" value="${user.id}"/>
              		<button type="submit" name="command" value="loadphotoshoots" class="btn btn-primary edit">${photoshoots }</button>
              	</form>
                </c:if>
            </c:if>
    	</div>
    </div>
	
	<c:if test = "${user.isPhotographer == true }">
    	<div class="container all-photos">
    		 <c:if test="${message != null }">
    			<fmt:message bundle="${loc}" key="${message }" var="mess"/>
            		${mess }
    		</c:if> 
            <c:forEach var = "photo" items="${photos}">
            	<form action="Controller" method="get" class="form-photo">
            		<input type="hidden" name="photo" value="${photo }"/>
                        <button type="submit" name="command" value="loadphotopage" style="border: 0; background: transparent">
                        	<img class="img1" src="${photo.imagePath }"/>
                        </button>
                 </form>
            </c:forEach>
     	</div>
    </c:if>
	
</div>
</body>
</html>