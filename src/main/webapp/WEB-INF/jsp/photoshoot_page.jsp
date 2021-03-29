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
    
    <fmt:message bundle="${loc}" key="button.logout" var="logout"/>
    <fmt:message bundle="${loc}" key="button.en" var="en"/>
    <fmt:message bundle="${loc}" key="button.ru" var="ru"/>
    <fmt:message bundle="${loc}" key="button.delete" var="delete"/>
    <fmt:message bundle="${loc}" key="button.edit_photoshoot" var="edit"/>
    <fmt:message bundle="${loc}" key="button.save" var="save"/>
    <fmt:message bundle="${loc}" key="button.add_photoshoot" var="add"/>
    <fmt:message bundle="${loc}" key="column.photographer" var="ph_col"/>
    <fmt:message bundle="${loc}" key="column.type" var="type_col"/>
    <fmt:message bundle="${loc}" key="column.price" var="price_col"/>
    
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
	
	<c:out value="${message }"/>
	
	<div class="container">
	<table class="table table-primary">
		<thead>
    		<tr>
      			<th scope="col">${ph_col }</th>
      			<th scope="col">${type_col }</th>
      			<th scope="col">${price_col }</th>
      			<th scope="col"></th>
      			<th scope="col"></th>
    		</tr>
  		</thead>
  		<c:forEach var = "option" items="${photoshoot_options}">
  		<tbody>
    		<tr>
    		
    			<c:choose>
    			<c:when test="${option.id == option_to_edit}">
    				<td>${option.photographer }</td>
    				<form action="Controller" method="post">
    				<td>
    				<select name="photoshoot_type" class="form-select form-select-sm" aria-label="Default select example">
  						<option selected>${type_col }</option>
  						<c:forEach var="ph_type" items="${photoshoot_types }">
  							<option value="${ph_type.id }">${ph_type.type }</option>
  						</c:forEach>
					</select>
					</td>
					<td><input type="text" name="price" class="form-control" id="formGroupExampleInput" placeholder="${price_col }"></td>
    				<td>
      				<form action="Controller" method="post">
      					<button name="command" value="savephotoshootedit" class="btn btn-primary">${save }</button>
      				</form>
      				</td>
    				</form>
    			</c:when>
    			<c:otherwise>
    				<td>${option.photographer }</td>
      				<td>${option.type }</td>
      				<td>${option.price }</td>
      				<td>
      					<form action="Controller" method="post">
      						<input type="hidden" name="photoshoot_option" value="${option.id }"/>
      						<button type="submit" name="command" value="editphotoshoot" class="btn btn-primary">${edit }</button>
      					</form>
      				</td>
      				<td>
      					<form action="Controller" method="post">
      						<input type="hidden" name="photoshoot_option" value="${option.id }"/>
      						<button type="submit" name="command" value="deletephotoshoot" class="btn btn-danger">${delete }</button>
      					</form>
      			</td>
    			</c:otherwise>
    			</c:choose>
    		</tr>
    		</c:forEach>
    		<c:if test="${add_photoshoot == true }">
    		<form action="Controller" method="post">
    			<tr>
    				<td>${user.username }</td>
    				<td>
    				<select name="photoshoot_type" class="form-select form-select-sm" aria-label="Default select example">
  						<option selected>${type_col }</option>
  						<c:forEach var="ph_type" items="${photoshoot_types }">
  							<option value="${ph_type.id }">${ph_type.type }</option>
  						</c:forEach>
					</select>
					</td>
    				<td><input type="text" name="price" class="form-control" id="formGroupExampleInput" placeholder="${price_col }"></td>
    				<td>
      				<form action="Controller" method="post">
      					<button name="command" value="savephotoshoot" class="btn btn-primary">${save }</button>
      				</form>
      				</td>
    			</tr>
    		</form>
    		</c:if>
  		</tbody>
	</table>
	
	<form action="Controller" method="post" class="add-form">
		<button type="submit" name="command" value="addphotoshoot" class="btn btn-outline-primary add-btn">${add }</button>
	</form>
	
	</div>
</div>
</body>
</html>