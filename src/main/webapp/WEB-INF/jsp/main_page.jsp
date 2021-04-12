<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Photobook</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
    <link href="css/main.css" rel="stylesheet" type="text/css">
    
    <fmt:setLocale value="${sessionScope.locale }"/>
    <fmt:setBundle basename="locale" var="loc"/>
    
    <fmt:message bundle="${loc}" key="button.sign_in" var="sign_in"/>
    <fmt:message bundle="${loc}" key="button.sign_up" var="sign_up"/>
    <fmt:message bundle="${loc}" key="button.logout" var="logout"/>
    <fmt:message bundle="${loc}" key="button.profile" var="profile"/>
    <fmt:message bundle="${loc}" key="button.en" var="en"/>
    <fmt:message bundle="${loc}" key="button.ru" var="ru"/>
    <fmt:message bundle="${loc}" key="button.my_orders" var="my_orders"/>
    <fmt:message bundle="${loc}" key="button.photoshoots" var="photoshoots"/>
</head>
<body>
	<div class="main-container">
	
		<jsp:include page="navbar-main.jsp"/>
		
            <form action="Controller" method="get" class="btn-group local" role="group" aria-label="Basic outlined example">
  				<button type="submit" name="command" value="en" class="btn btn-outline-primary">${en }</button>
  				<button type="submit" name="command" value="ru" class="btn btn-outline-primary">${ru }</button>
			</form>
            <div class="name-main">PhotoBook</div>
            <div>
            
            	<c:if test="${message != null }">
            		<fmt:message bundle="${loc}" key="${message }" var="mess"/>
            		${mess }
            	</c:if>
            </div>
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
     </div>
</body>
</html>