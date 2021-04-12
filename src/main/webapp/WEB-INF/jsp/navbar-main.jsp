<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Photobook</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
    <link href="css/navbar-main.css" rel="stylesheet" type="text/css">
    
    <fmt:setLocale value="${sessionScope.locale }"/>
    <fmt:setBundle basename="locale" var="loc"/>
    
    <fmt:message bundle="${loc}" key="button.sign_in" var="sign_in"/>
    <fmt:message bundle="${loc}" key="button.sign_up" var="sign_up"/>
    <fmt:message bundle="${loc}" key="button.logout" var="logout"/>
    <fmt:message bundle="${loc}" key="button.profile" var="profile"/>
    <fmt:message bundle="${loc}" key="button.my_orders" var="my_orders"/>
    <fmt:message bundle="${loc}" key="button.my_photoshoots" var="my_photoshoots"/>
    <fmt:message bundle="${loc}" key="button.search" var="search"/>
    <fmt:message bundle="${loc}" key="placeholder.search" var="placeholder_search"/>
    <fmt:message bundle="${loc}" key="button.all_users" var="all_users"/>
    <fmt:message bundle="${loc}" key="button.complaints" var="complaints"/>
</head>
<body>

	<c:choose>
  		<c:when test="${sessionScope.auth != null}">
    		<nav class="navbar navbar-light navbar-expand-lg" >
      		<div class="container-fluid">
    		<div class="collapse navbar-collapse" >
      			<ul class="navbar-nav">
      				<c:choose>
      					<c:when test="${sessionScope.user.isAdmin == true }">
      						<li class="nav-item">
          						<a class="nav-link" id="navlink" href="Controller?command=loadprofilepage">${profile }</a>
        					</li>
        					<li class="nav-item">
          						<a class="nav-link" id="navlink" href="Controller?command=loadalluserspage">${all_users }</a>
        					</li>
              				<li class="nav-item">
          						<a class="nav-link" id="navlink" href="Controller?command=loadcomplaintspage">${complaints }</a>
        					</li>
      					</c:when>
      					<c:otherwise>
      						<li class="nav-item">
          						<a class="nav-link" id="navlink" href="Controller?command=loadprofilepage">${profile }</a>
        					</li>
        					<li class="nav-item">
          						<a class="nav-link" id="navlink" href="Controller?command=loadorderspage">${my_orders }</a>
        					</li>
       						<c:if test = "${sessionScope.user.isPhotographer == true }">
              				<li class="nav-item">
          						<a class="nav-link" id="navlink" href="Controller?command=loadphotoshootpage">${my_photoshoots }</a>
        					</li>
              				</c:if>
      					</c:otherwise>
      				</c:choose>
       				
      			</ul>
    		</div>
  			</div>
  			  	<form class="search" action="Controller" method="get">
        		<input name="user_to_search" class="form-control me-2" type="search" placeholder="${placeholder_search }" aria-label="Search">
        		<button class="btn btn-outline-primary" type="submit" name="command" value="searchuser">${search }</button>
     	 	</form>
  			<form class="header" action="Controller" method="get">
        		<button class="btn btn-primary sign-up" type="submit" name = "command" value="logout">${logout }</button>
    		</form>
   			 </nav>			
  		</c:when>
  		<c:otherwise>
  			<nav class="navbar navbar-light navbar-expand-lg" >
            	<form class="header" action="Controller" method="post">
                    <div class="btn-group buttons" role="group" aria-label="Basic example">
                    	<button class="btn btn-primary sign-in" type="submit" name = "command" value="loadauthpage">${sign_in }</button>
                        <button class="btn btn-primary sign-up" type="submit" name = "command" value="loadregpage">${sign_up }</button>
                    </div>
                </form>
            </nav>
 		</c:otherwise>
	</c:choose>


</body>
</html>