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
      			<th scope="col">Username</th>
      			<th scope="col">Complaint text</th>
      			<th scope="col">Photo</th>
      			<th scope="col">Comment</th>
      			<th scope="col">State</th>
      			<th scope="col"></th>
    		</tr>
  		</thead>
  		<tbody>
  			<c:forEach var="complaint" items="${complaints }">
  				<tr>
  					<td>${complaint.user }</td>
  					<td>${complaint.text }</td>
  					<td><a href="Controller?command=loadphotopage&photo=${complaint.photo }">Go to photo</a></td>
  					<td><a href="Controller?command=loadphotopage&complaint_comment=${complaint.commentId}&photo=${complaint.photo }">${complaint.commentText }</a></td>
  					<td>
  						<c:choose>
  							<c:when test="${complaint.state == 1 }">
  								Not viewed
  							</c:when>
  							<c:otherwise>
  								Viewed
  							</c:otherwise>
  						</c:choose>
  					</td>
  					<td>
  						<c:if test="${complaint.state == 1 }">
  						<form action="Controller" method="post">
  							<input type="hidden" name="complaint_id" value="${complaint.id }"/>
  							<button type="submit" name="command" value="viewcomplaint" class="btn btn-success">Mark as viewed</button>
  						</form>
  						</c:if>
  					</td>
  				</tr>
  			</c:forEach>
  		</tbody>
  	</table>
  	</div>
</body>
</html>