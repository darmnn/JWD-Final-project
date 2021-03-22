<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Photobook</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
    <link href="css/main.css" rel="stylesheet" type="text/css">
    <link href="css/photo.css" rel="stylesheet" type="text/css">
    
    <fmt:setLocale value="${sessionScope.locale }"/>
    <fmt:setBundle basename="locale" var="loc"/>
    
    <fmt:message bundle="${loc}" key="button.sign_in" var="sign_in"/>
    <fmt:message bundle="${loc}" key="button.sign_up" var="sign_up"/>
    <fmt:message bundle="${loc}" key="button.logout" var="logout"/>
    <fmt:message bundle="${loc}" key="button.en" var="en"/>
    <fmt:message bundle="${loc}" key="button.ru" var="ru"/>
    <fmt:message bundle="${loc}" key="button.new_comment" var="new_comment"/>
    <fmt:message bundle="${loc}" key="placeholder.new_comment" var="new_comment_placeholder"/>
    <fmt:message bundle="${loc}" key="button.rate" var="rate_button"/>
    <fmt:message bundle="${loc}" key="rate.message" var="rate"/>
</head>
<body>
	<div>
		<nav class="navbar navbar-light">
    	<a href = "index.jsp" class = "loadmain">PhotoBook</a>
    	<form class="header-profile" action="Controller" method="get">
    		<c:choose>
    			<c:when test="${sessionScope.auth != null }">
    				<button class="btn btn-primary sign-up" type="submit" name = "command" value="logout">${logout }</button>
    			</c:when>
    			<c:otherwise>
    				<div class="btn-group buttons" role="group" aria-label="Basic example">
                    	<button class="btn btn-primary sign-in" type="submit" name = "command" value="loadauthpage">${sign_in }</button>
                        <button class="btn btn-primary sign-up" type="submit" name = "command" value="loadregpage">${sign_up }</button>
                    </div>
    			</c:otherwise>
    		</c:choose>
         </form>
    	</nav>
    <form action="Controller" method="get" class="btn-group local" role="group" aria-label="Basic outlined example">
  		<button type="submit" name="command" value="en" class="btn btn-outline-primary">${en }</button>
  		<button type="submit" name="command" value="ru" class="btn btn-outline-primary">${ru }</button>
	</form>
	
	<div class="container">
        	<div class="card border-success mb-3 photo-container">
  				<div class="card-header bg-transparent border-success username-container">${requestScope.photo.photographer }</div>
  				<div class="card-body text-success image-container">
    				<img class="photo" src="${requestScope.photo.imagePath }"/>
  				</div>
  				<div class="card-footer bg-transparent border-success footer-container">
  					<c:forEach var = "i" begin = "1" end = "${requestScope.photo.rating }">
         				<img src="images/star.png" class="float-left star"/>
       			 	</c:forEach>
       			 	<c:if test="${sessionScope.auth != null }">
       			 	<form class="rate-form">
       			 	<select name="rating" class="form-select form-select-sm" aria-label="Default select example">
  						<option selected>${rate }</option>
  						<option value="1">1</option>
  						<option value="2">2</option>
  						<option value="3">3</option>
  						<option value="4">4</option>
  						<option value="5">5</option>
					</select>
					<input type="hidden" name="photo" value="${photo}"/>
					<button type="submit" name="command" value="ratephoto" class="btn btn-primary btn-sm">${rate_button }</button>
       			 	</form>
       			 	</c:if>
  					<div class="date">${requestScope.photo.date }</div>
       			 </div>
			</div>
			
			<div class="card comments-container">
			<c:forEach var = "comment" items = "${comments }">
				<div class="card comment">
  				<div class="card-body">
  					<p><img src="${comment.authorPic }" class="rounded-circle float-left author_pic"/>${comment.author }</p>
    				<div class="comment-text">${comment.text }</div>
    				<div class="date-comment">${comment.date }</div>
    			</div>
    			</div>
			</c:forEach>
			<c:if test="${sessionScope.auth != null }">
				<form action="Controller" method="post" class="new-comment">
    				<input type="text" name="comment_text" class="form-control" id="formGroupExampleInput" placeholder="${new_comment_placeholder }">
    				<input type="hidden" name="photo" value="${photo}"/>
    				<button type="submit" name="command" value="newcomment" class="btn btn-primary btn-sm">${new_comment }</button>
    			</form>
			</c:if>
  			</div>
	</div>
</div>
</body>
</html>