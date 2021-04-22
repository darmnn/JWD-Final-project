<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Photobook</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
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
    <fmt:message bundle="${loc}" key="button.add_photoshoot_type" var="create_photoshoot_type"/>
    <fmt:message bundle="${loc}" key="button.decline" var="decline"/>
    <fmt:message bundle="${loc}" key="button.accept" var="accept"/>
    <fmt:message bundle="${loc}" key="column.photographer" var="ph_col"/>
    <fmt:message bundle="${loc}" key="column.type" var="type_col"/>
    <fmt:message bundle="${loc}" key="column.price" var="price_col"/>
    <fmt:message bundle="${loc}" key="label.orders" var="orders_label"/>
    <fmt:message bundle="${loc}" key="column.date" var="date_col"/>
    <fmt:message bundle="${loc}" key="column.status" var="status_col"/>
    <fmt:message bundle="${loc}" key="column.client" var="client_col"/>
    <fmt:message bundle="${loc}" key="weekday.monday" var="monday"/>
    <fmt:message bundle="${loc}" key="weekday.tuesday" var="tuesday"/>
    <fmt:message bundle="${loc}" key="weekday.wednesday" var="wednesday"/>
    <fmt:message bundle="${loc}" key="weekday.thursday" var="thursday"/>
    <fmt:message bundle="${loc}" key="weekday.friday" var="friday"/>
    <fmt:message bundle="${loc}" key="weekday.saturday" var="saturday"/>
    <fmt:message bundle="${loc}" key="weekday.sunday" var="sunday"/>
    
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
            	<div class="alert alert-danger" role="alert">
            		<fmt:message bundle="${loc}" key="${message }" var="mess"/>
            		${mess }
            		</div>
            	</c:if>
    </div>
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
    				<input type="hidden" name="photoshoot_option" value="${option.id }"/>
    				<td>
    				<select name="photoshoot_type" class="form-select form-select-sm" aria-label="Default select example">
  						<c:forEach var="type" items="${photoshoot_types}" >
  							<c:if test="${type.type == option.type }">
  								<option value="${type.id }" selected>${option.type }</option>
  							</c:if>
  						</c:forEach>
  						<c:forEach var="ph_type" items="${photoshoot_types }">
  							<option value="${ph_type.id }">${ph_type.type }</option>
  						</c:forEach>
					</select>
					</td>
					<td><input type="text" name="price" value="${option.price }" class="form-control" id="formGroupExampleInput" placeholder="${price_col }"></td>
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
      						<input type="hidden" name="option_to_edit" value="${option.id }"/>
      						<button type="submit" name="command" value="loadphotoshootpage" class="btn btn-primary">${edit }</button>
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
  						<option value="${photoshoot_types[0].id }" selected>${photoshoot_types[0].type}</option>
  						<c:forEach var="ph_type" items="${photoshoot_types }">
  							<c:if test="${ph_type.type !=  photoshoot_types[0].type}"><option value="${ph_type.id }">${ph_type.type }</option></c:if>
  							
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
		<input type="hidden" name="add_photoshoot" value="true"/>
		<button type="submit" name="command" value="loadphotoshootpage" class="btn btn-outline-primary add-btn">${add }</button>
	</form>
	</div>
	
	<div class="container create-container">
		<c:choose>
			<c:when test="${add_type == true }">
				<form action="Controller" method="post" class="create-form">
					<input type="text" name="photoshoot_type" class="form-control" id="formGroupExampleInput" placeholder=""/>
					<button name="command" value="addphotoshoottype" class="btn btn-primary btn-create">${save }</button>
				</form>
			</c:when>
			<c:otherwise>
				<form action="Controller" method="post" class="create-form">
					<input type="hidden" name="add_type" value="true">
					<button type="submit" name="command" value="loadphotoshootpage" class="btn btn-outline-primary create-btn">${create_photoshoot_type }</button>
				</form>
			</c:otherwise>
		</c:choose>
	</div>
	
	<div class="container">
		<p>
  			<button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#collapseExample" aria-expanded="false" aria-controls="collapseExample">
    			${orders_label }
  			</button>
		</p>
		<div class="collapse" id="collapseExample">
 			 <div class="card card-body">
  				<table class="table table-primary">
					<thead>
    					<tr>
      						<th scope="col">${client_col }</th>
      						<th scope="col">${type_col }</th>
      						<th scope="col">${price_col }</th>
      						<th scope="col">${date_col }</th>
      						<th scope="col">${status_col }</th>
      						<th scope="col"></th>
      						<th scope="col"></th>
    					</tr>
  					</thead>
  					<tbody>
  						<c:forEach var="order" items="${orders }">
  							<tr>
  								<td><a href="Controller?command=loaduserpage&user=${order.client}">${order.client }</a></td>
  								<td>${order.phOption.type }</td>
  								<td>${order.phOption.price }</td>
  								<td>${order.date }</td>
  								<td>${order.status }</td>
  								<td>
  									<c:if test="${order.status == \"Не обработано\" }">
  										<form action="Controller" method="post">
  											<input type="hidden" name="order" value="${order.id }"/>
  											<input type="hidden" name="client" value="${order.client }"/>
  											<input type="hidden" name="action" value="decline"/>
  											<button type="submit" name="command" value="processorder" class="btn btn-danger">${decline }</button>
  										</form>
  									</c:if>
  								</td>
  								<td>
  									<c:if test="${order.status == \"Не обработано\" }">
  										<form action="Controller" method="post">
  												<input type="hidden" name="order" value="${order.id }"/>
  												<input type="hidden" name="client" value="${order.client }"/>
  												<input type="hidden" name="action" value="accept"/>
  												<button type="submit" name="command" value="processorder" class="btn btn-success">${accept }</button>
  										</form>
  									</c:if>
  								</td>
  							</tr>
  						</c:forEach>
  					</tbody>
				</table>
  			</div>
		</div>
	</div>

	   <div id="carouselExampleIndicators" class="carousel slide carousel-dark" data-ride="carousel">
    <ol class="carousel-indicators">
        <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
        <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
        <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
    </ol>
    <div class="carousel-inner">
        <div class="carousel-item active">
            <div id="calendar-wrap">
    		<div id="calendar">
    		<h2 class="year">${timetable[0].date.month } ${timetable[0].date.year }</h2>
    			<ul class="weekdays">
    				
    				<li>${ monday}</li>
    				<li>${tuesday}</li>
    				<li>${wednesday}</li>
    				<li>${thursday}</li>
    				<li>${friday}</li>
    				<li>${saturday}</li>
    				<li>${sunday}</li>
    			</ul>


    			<ul class="days">
    				<c:forEach var="i" begin="2" end="${timetable[0].firstDayOfMonth.dayOfWeek.value }">
    					<li class="day other-month"></li>
    				</c:forEach>
    				<c:forEach var="i" items="${ days[0]}">
    					<li class="day">
    						<div class="date">${i.key }</div>  
    						<c:if test="${i.value != null }">
    							<div class="event">
    								<div class="event-desc">
    									<a href="Controller?command=loaduserpage&user=${i.value.client}">${i.value.client }</a>  ${i.value.phOption.type }
    								</div>
    					</div>
    						</c:if>  					
    					</li>
    				</c:forEach>
    			</ul>

    		</div>
    	</div>
        </div>
        <div class="carousel-item">
          <div id="calendar-wrap">
    		<div id="calendar">
    		<h2 class="year">${timetable[1].date.month } ${timetable[0].date.year }</h2>
    			<ul class="weekdays">
    				
    				<li>${ monday}</li>
    				<li>${tuesday}</li>
    				<li>${wednesday}</li>
    				<li>${thursday}</li>
    				<li>${friday}</li>
    				<li>${saturday}</li>
    				<li>${sunday}</li>
    			</ul>


    			<ul class="days">
    				<c:forEach var="i" begin="2" end="${timetable[1].firstDayOfMonth.dayOfWeek.value }">
    					<li class="day other-month"></li>
    				</c:forEach>
    				<c:forEach var="i" items="${ days[1]}">
    					<li class="day">
    						<div class="date">${i.key }</div>  
    						<c:if test="${i.value != null }">
    							<div class="event">
    								<div class="event-desc">
    									<a href="Controller?command=loaduserpage&user=${i.value.client}">${i.value.client }</a>  ${i.value.phOption.type }
    								</div>
    					</div>
    						</c:if>  					
    					</li>
    				</c:forEach>
    			</ul>

    		</div>
    	</div>
        </div>
        <div class="carousel-item">
         	<div id="calendar-wrap">
    		<div id="calendar">
    			<h2 class="year">${timetable[2].date.month } ${timetable[0].date.year }</h2>
    			<ul class="weekdays">
    				
    				<li>${ monday}</li>
    				<li>${tuesday}</li>
    				<li>${wednesday}</li>
    				<li>${thursday}</li>
    				<li>${friday}</li>
    				<li>${saturday}</li>
    				<li>${sunday}</li>
    			</ul>


    			<ul class="days">
    				<c:forEach var="i" begin="2" end="${timetable[2].firstDayOfMonth.dayOfWeek.value }">
    					<li class="day other-month"></li>
    				</c:forEach>
    				<c:forEach var="i" items="${ days[2]}">
    					<li class="day">
    						<div class="date">${i.key }</div>  
    						<c:if test="${i.value != null }">
    							<div class="event">
    								<div class="event-desc">
    									<a href="Controller?command=loaduserpage&user=${i.value.client}">${i.value.client }</a>  ${i.value.phOption.type }
    								</div>
    					</div>
    						</c:if>  					
    					</li>
    				</c:forEach>
    			</ul>

    		</div>
    	</div>
        </div>
    </div>
    <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="sr-only">Previous</span>
    </a>
    <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="sr-only">Next</span>
    </a>
</div>
</div>

<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    	
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>
</body>
</html>