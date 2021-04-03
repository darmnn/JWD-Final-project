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
    <fmt:message bundle="${loc}" key="button.photoshoots" var="photoshoots"/>
    <fmt:message bundle="${loc}" key="button.add_photo" var="add_photo"/>
    <fmt:message bundle="${loc}" key="button.my_orders" var="my_orders"/>
    
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
	<form action="Controller" method="get">
		<button type="submit" name="command" value="loadorderspage" class="btn btn-primary">${my_orders }</button>
	</form>
			
    <div class="container outer">
    	<c:choose>
    	<c:when test="${sessionScope.user.profilePicPath != null}">
    	<img src="${sessionScope.user.profilePicPath }" class="rounded-circle float-left img"/>
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
              			<input type="hidden" name="edit" value="true">
              			<button type="submit" name="command" value="loadprofilepage" class="btn btn-primary edit">${edit }</button>
              		</form>
              		<c:if test = "${sessionScope.user.isPhotographer == true }">
              		<form action="Controller" method="post" class="form-edit">
              			<button type="submit" name="command" value="loadphotoshootpage" class="btn btn-primary edit">${photoshoots }</button>
              		</form>
              		</c:if>
    			</c:otherwise>
    		</c:choose>
    	</div>
    </div>
    <c:if test = "${sessionScope.user.isPhotographer == true }">
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
            <c:choose>
            	<c:when test="${requestScope.add_photo == true }">
            		<form action="Controller" method="post">
            			<input type="file" name="new_photo"></input>
              			<button type="submit" name="command" value="savenewphoto" class="btn btn-primary edit">${save }</button>
            		</form>
            	</c:when>
            	<c:otherwise>
            		<form action="Controller" method="post">
            			<input type="hidden" name="add_photo" value="true">
            			<button type="submit" name="command" value="loadprofilepage" class="btn btn-primary">${add_photo }</button>
            		</form>
            	</c:otherwise>
            </c:choose>
     	</div>
    </c:if>
</div>
</body>
</html>