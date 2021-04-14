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
    
    <fmt:message bundle="${loc}" key="button.en" var="en"/>
    <fmt:message bundle="${loc}" key="button.ru" var="ru"/>
    <fmt:message bundle="${loc}" key="button.photoshoots" var="photoshoots"/>
    <fmt:message bundle="${loc}" key="button.send_email" var="send_email"/>
    <fmt:message bundle="${loc}" key="button.block" var="block"/>
    
</head>
<body>
<div>

	<c:choose>
		<c:when test="${sessionScope.user.isAdmin == true }">
			<jsp:include page="admin_navbar.jsp"/>
		</c:when>
		<c:otherwise>
			<jsp:include page="navbar.jsp"/>
		</c:otherwise>
	</c:choose>
      <form action="Controller" method="get" class="btn-group local" role="group" aria-label="Basic outlined example">
  				<button type="submit" name="command" value="en" class="btn btn-outline-primary">${en }</button>
  				<button type="submit" name="command" value="ru" class="btn btn-outline-primary">${ru }</button>
		</form>
	<div class="container" id="outer">
    	<c:choose>
    	<c:when test="${this_user.profilePicPath != null}">
    		<img src="${this_user.profilePicPath }" class="rounded-circle float-left img"/>
    	</c:when>
    	<c:otherwise>
    		<img src="images/user_pic.png" class="rounded-circle float-left"/>
    	</c:otherwise>
    	</c:choose>
    	<div class="container" id="inner">
    		<p id="username"><c:out value ="${this_user.username }"/> </p>
    		<div class="card" id="about">
            	<div class="card-body" id="about-body">
                	<p class="card-text">${this_user.profileDecs }</p>
                </div>
            </div>
            <c:if test = "${this_user.isPhotographer == true }">
            	<c:forEach var = "i" begin = "1" end = "${this_user.totalRating }">
                	<img src="images/star.png" class="float-left" id="star"/>
                </c:forEach>
                <c:if test ="${sessionScope.auth != null && sessionScope.user != this_user}">
                <c:choose>
                	<c:when test="${sessionScope.user.isAdmin == true }">
                		<form action="Controller" method="post" id="form-edit">
      						<input type="hidden" name="userid" value="${this_user.id }"/>
      						<input type="hidden" name="action" value="block"/>
      						<button type="submit" name="command" value="blockunlock" class="btn btn-danger">${block }</button>
      					</form>
                	</c:when>
                	<c:otherwise>
                		<form action="Controller" method="post" id="form-edit">
                			<input type="hidden" name="photographer" value="${this_user.id}"/>
              				<button type="submit" name="command" value="loadphotoshoots" class="btn btn-primary" id="edit">${photoshoots }</button>
              			</form>
                	</c:otherwise>
                </c:choose>
              	<form action="mailto:${this_user.email }" method="post" id="form-edit">
              		<button type="submit" class="btn btn-primary" id="edit">${send_email }</button>
              	</form>
                </c:if>
            </c:if>
    	</div>
    </div>
	    		 <c:if test="${message != null }">
    			<fmt:message bundle="${loc}" key="${message }" var="mess"/>
            		${mess }
    		</c:if> 
	<c:if test = "${this_user.isPhotographer == true }">
    	<div class="container" id="all-photos">
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