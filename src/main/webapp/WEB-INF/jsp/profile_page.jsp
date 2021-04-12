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
    
    <fmt:message bundle="${loc}" key="button.edit" var="edit"/>
    <fmt:message bundle="${loc}" key="button.save" var="save"/>
    <fmt:message bundle="${loc}" key="button.en" var="en"/>
    <fmt:message bundle="${loc}" key="button.ru" var="ru"/>
    <fmt:message bundle="${loc}" key="button.add_photo" var="add_photo"/>
    
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
    	<c:when test="${sessionScope.user.profilePicPath != null}">
    	<img src="${sessionScope.user.profilePicPath }" class="rounded-circle float-left img"/>
    	</c:when>
    	<c:otherwise>
    	<img src="images/user_pic.png" class="rounded-circle float-left"/>
    	</c:otherwise>
    	</c:choose>
    	<div class="container" id="inner">
    		<p id="username"><c:out value ="${sessionScope.user.username }"/> </p>
    		<c:choose>
    			<c:when test="${requestScope.edit == true }">
    			<form action="Controller" method="post" id="form-edit">
    				<div class="card" id="about">
                        <div class="card-body" id="about-body">
                        	<textarea id="textar" name="new_profile_desc" class="card-text">${sessionScope.user.profileDecs }</textarea>
                        </div>
                        <button type="submit" name="command" value="savechanges" class="btn btn-primary" id="edit">${save }</button>
             		</div>
             			<input type="file" name="new_profile_pic"></input>
              			<button type="submit" name="command" value="savephotochanges" class="btn btn-primary" id="edit">${save }</button>
              	</form>
    			</c:when>
    			<c:otherwise>
    				<div class="card" id="about">
                    	<div class="card-body" id="about-body">
                            <p class="card-text">${sessionScope.user.profileDecs }</p>
                        </div>
             		</div>
             		<c:if test = "${sessionScope.user.isPhotographer == true }">
                    	<c:forEach var = "i" begin = "1" end = "${sessionScope.user.totalRating }">
                       		<img src="images/star.png" class="float-left" id="star"/>
                       	</c:forEach>
              		</c:if>
              		<form action="Controller" method="post" id="form-edit">
              			<input type="hidden" name="edit" value="true">
              			<button type="submit" name="command" value="loadprofilepage" class="btn btn-primary" id="edit">${edit }</button>
              		</form>
    			</c:otherwise>
    		</c:choose>
    	</div>
    </div>
    <c:if test = "${sessionScope.user.isPhotographer == true }">
    	<div class="container" id="all-photos">
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
            		<form action="LoadImage" method="post" enctype="multipart/form-data">
            			<input type="file" name="new_photo" ></input>
              			<button type="submit" name="command" value="savenewphoto" class="btn btn-primary" id="edit">${save }</button>
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