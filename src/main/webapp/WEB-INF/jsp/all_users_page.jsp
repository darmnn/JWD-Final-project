<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Photobook</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link href="css/all_users.css" rel="stylesheet" type="text/css">
    
    <fmt:setLocale value="${sessionScope.locale }"/>
    <fmt:setBundle basename="locale" var="loc"/>
    
    <fmt:message bundle="${loc}" key="button.logout" var="logout"/>
    <fmt:message bundle="${loc}" key="button.en" var="en"/>
    <fmt:message bundle="${loc}" key="button.ru" var="ru"/>
    <fmt:message bundle="${loc}" key="column.username" var="username"/>
    <fmt:message bundle="${loc}" key="column.photographer" var="photographer"/>
    <fmt:message bundle="${loc}" key="column.client" var="client"/>
    <fmt:message bundle="${loc}" key="column.email" var="email"/>
    <fmt:message bundle="${loc}" key="column.role" var="role"/>
    <fmt:message bundle="${loc}" key="column.profile_photo" var="profile_photo"/>
    <fmt:message bundle="${loc}" key="column.total_rating" var="total_rating"/>
    <fmt:message bundle="${loc}" key="column.state" var="state"/>
    <fmt:message bundle="${loc}" key="button.block" var="block"/>
    <fmt:message bundle="${loc}" key="button.unlock" var="unlock"/>
    <fmt:message bundle="${loc}" key="label.blocked" var="blocked"/>
    <fmt:message bundle="${loc}" key="label.not_blocked" var="not_blocked"/>
</head>
<body>
	<jsp:include page="admin_navbar.jsp"/>
	
	<form action="Controller" method="get" class="btn-group local" role="group" aria-label="Basic outlined example">
  				<button type="submit" name="command" value="en" class="btn btn-outline-primary">${en }</button>
  				<button type="submit" name="command" value="ru" class="btn btn-outline-primary">${ru }</button>
	</form>
	
	<div class="container">
	<table class="table table-primary">
		<thead>
    		<tr>
      			<th scope="col">${username}</th>
      			<th scope="col">${email}</th>
      			<th scope="col">${role}</th>
      			<th scope="col">${total_rating}</th>
      			<th scope="col">${profile_photo}</th>
      			<th scope="col">${state}</th>
      			<th scope="col"></th>
    		</tr>
  		</thead>
  		<c:forEach var="us" items="${users}">
  		<tbody>
  			<tr>
  				<c:if test="${us.isAdmin == false }">
  					<td><a href="Controller?command=loaduserpage&user=${us.username}">${us.username}</a></td>
  					<td>${us.email }</td>
  					<c:choose>
  						<c:when test="${us.isPhotographer }">
  							<td>${photographer}</td>
  						</c:when>
  						<c:otherwise>
  							<td>${client}</td>
  						</c:otherwise>
  					</c:choose>
  					<td>${us.totalRating }</td>
  					<c:choose>
  						<c:when test="${us.profilePicPath != null }">
  							<td class="user-pic"><img src="${us.profilePicPath }" class="rounded-circle img"/></td>
  						</c:when>
  						<c:otherwise>
  							<td class="user-pic"><img src="images/user_pic.png" class="rounded-circle img"/></td>
  						</c:otherwise>
  					</c:choose>
  					<c:choose>
  						<c:when test="${us.state == 1 }">
  							<td>${not_blocked }</td>
  							<td>
  								<form action="Controller" method="post">
      								<input type="hidden" name="userid" value="${us.id }"/>
      								<input type="hidden" name="action" value="block"/>
      								<button type="submit" name="command" value="blockunlock" class="btn btn-danger">${block }</button>
      							</form>
      						</td>
  						</c:when>
  						<c:otherwise>
  							<td>${blocked }</td>
  							<td>
  								<form action="Controller" method="post">
      								<input type="hidden" name="userid" value="${us.id }"/>
      								<input type="hidden" name="action" value="unlock"/>
      								<button type="submit" name="command" value="blockunlock" class="btn btn-success">${unlock }</button>
      							</form>
  							</td>
  						</c:otherwise>
  					</c:choose>
  				</c:if>
  			</tr>
  		</tbody>
  		</c:forEach>
  	</table>
  	</div>
</body>
</html>