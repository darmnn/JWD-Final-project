<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Photobook</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link href="css/profile.css" rel="stylesheet" type="text/css">
    <link href="css/main.css" rel="stylesheet" type="text/css">
    <link href="css/photoshoot.css" rel="stylesheet" type="text/css">
    
    <fmt:setLocale value="${sessionScope.locale }"/>
    <fmt:setBundle basename="locale" var="loc"/>
    
    <fmt:message bundle="${loc}" key="button.en" var="en"/>
    <fmt:message bundle="${loc}" key="button.ru" var="ru"/>
    <fmt:message bundle="${loc}" key="button.add_photoshoot" var="add"/>
    <fmt:message bundle="${loc}" key="button.order" var="order"/>
    <fmt:message bundle="${loc}" key="button.cancel" var="cancel"/>
    <fmt:message bundle="${loc}" key="column.photographer" var="ph_col"/>
    <fmt:message bundle="${loc}" key="column.type" var="type_col"/>
    <fmt:message bundle="${loc}" key="column.price" var="price_col"/>
    <fmt:message bundle="${loc}" key="column.date" var="date_col"/>
    <fmt:message bundle="${loc}" key="column.status" var="status_col"/>
    <fmt:message bundle="${loc}" key="label.orders" var="orders_message"/>
    
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
	
	<div>
            	<c:if test="${message != null }">
            		<fmt:message bundle="${loc}" key="${message }" var="mess"/>
            		${mess }
            	</c:if>
            </div>
	
	<div class="container">
		<h5>${orders_message }</h5>
		
		<table class="table table-primary">
			<thead>
    		<tr>
      			<th scope="col">${ph_col }</th>
      			<th scope="col">${type_col }</th>
      			<th scope="col">${price_col }</th>
      			<th scope="col">${date_col }</th>
      			<th scope="col">${status_col }</th>
      			<th scope="col"></th>
    		</tr>
  			</thead>
  			<tbody>
  				<c:forEach var="order" items="${orders }">
  				<tr>
  					<td><a href="Controller?command=loaduserpage&user=${order.phOption.photographer}">${order.phOption.photographer }</a></td>
  					<td>${order.phOption.type }</td>
  					<td>${order.phOption.price }</td>
  					<td>${order.date }</td>
  					<td>${order.status }</td>
  					<td>
  						<c:if test="${order.status == \"Не обработано\" }">
  						<form action="Controller" method="post">
  							<input type="hidden" name="order" value="${order.id }"/>
  							<button type="submit" name="command" value="cancelorder" class="btn btn-success">${cancel }</button>
  						</form>
  						</c:if>
  					</td>
  				</tr>
  				</c:forEach>
  			</tbody>
		</table>
	</div>
</div>
</body>
</html>