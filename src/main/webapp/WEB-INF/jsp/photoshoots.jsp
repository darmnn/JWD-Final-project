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
    <link href="css/calendar.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    
    <fmt:setLocale value="${sessionScope.locale }"/>
    <fmt:setBundle basename="locale" var="loc"/>
    
    <fmt:message bundle="${loc}" key="button.logout" var="logout"/>
    <fmt:message bundle="${loc}" key="button.en" var="en"/>
    <fmt:message bundle="${loc}" key="button.ru" var="ru"/>
    <fmt:message bundle="${loc}" key="button.add_photoshoot" var="add"/>
    <fmt:message bundle="${loc}" key="button.order" var="order"/>
    <fmt:message bundle="${loc}" key="column.photographer" var="ph_col"/>
    <fmt:message bundle="${loc}" key="column.type" var="type_col"/>
    <fmt:message bundle="${loc}" key="column.price" var="price_col"/>
    <fmt:message bundle="${loc}" key="calendar.text" var="calendar_text"/>
    
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
		<table class="table table-primary">
		<thead>
    		<tr>
      			<th scope="col">${ph_col }</th>
      			<th scope="col">${type_col }</th>
      			<th scope="col">${price_col }</th>
      			<th scope="col"></th>
    		</tr>
  		</thead>
  		<c:forEach var = "option" items="${photoshoot_options}">
  		<tbody>
    		<tr>
    			<td><a href="Controller?command=loaduserpage&user=${option.photographer}">${option.photographer }</a></td>
      			<td>${option.type }</td>
      			<td>${option.price }</td>
      			<td>
      				<c:choose>
      				<c:when test="${option.id == option_to_order }">
      					<form action="Controller" method="post">
      					<input type="hidden" name="photoshoot_option" value="${option.id }"/>
      					<input type="hidden" name="photographer" value="${photographer }"/>
      					<input type="date" name="date"/>
      					<button type="submit" name="command" value="saveorder" class="btn btn-primary">${order }</button>
      				</form>
      				</c:when>
      				<c:otherwise>
      					<form action="Controller" method="post">
      					<input type="hidden" name="photoshoot_option" value="${option.id }"/>
      					<input type="hidden" name="photographer" value="${photographer }"/>
      					<button type="submit" name="command" value="orderphotoshoot" class="btn btn-primary">${order }</button>
      				</form>
      				</c:otherwise>
      				</c:choose>
      			</td>
    		</tr>
    	</tbody>
    	</c:forEach>
  		</table>
  		
   <div id="carouselExampleIndicators" class="carousel slide carousel-dark" data-ride="carousel">
    <ol class="carousel-indicators">
        <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
        <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
        <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
    </ol>
    <div class="carousel-inner">
        <div class="carousel-item active">
            <div class="calendar">
			<div class="col rightCol">
				<div class="content">
					<h2 class="year">${timetable[0].date.month } ${timetable[0].date.year }</h2>
					<div class="clearfix"></div>
					<ul class="weekday"> 
						<li><a href="#" title="Mon" data-value="1">Mon</a></li>
						<li><a href="#" title="Tue" data-value="2">Tue</a></li>
						<li><a href="#" title="Wed" data-value="3">Wed</a></li>
						<li><a href="#" title="Thu" data-value="4">Thu</a></li>
						<li><a href="#" title="Fri" data-value="5">Fri</a></li>
						<li><a href="#" title="Say" data-value="6">Sat</a></li>
						<li><a href="#" title="Sun" data-value="7">Sun</a></li>
					</ul>
					<div class="clearfix"></div>
					<ul class="days">
						<c:forEach var="i" begin="2" end="${timetable[0].firstDayOfMonth.dayOfWeek.value }">
							<li><a href="#" ></a></li>
						</c:forEach>
						<c:forEach var="i" items="${ days[0]}">
							<c:if test="${timetable[0].date.dayOfMonth == i.key }">
								<li><a href="#" class="selected" >${i.key }</a></li>
							</c:if>
							<c:choose>
								<c:when test="${i.value != null }">
									<li><a href="#" class="event">${i.key }</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="#" >${i.key }</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</ul>
					<div class="clearfix"></div>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
		<p>${calendar_text }</p>
        </div>
        <div class="carousel-item">
            <div class="calendar">
			<div class="col rightCol">
				<div class="content">
					<h2 class="year">${timetable[1].date.month } ${timetable[1].date.year }</h2>
					<div class="clearfix"></div>
					<ul class="weekday"> 
						<li><a href="#" title="Mon" data-value="1">Mon</a></li>
						<li><a href="#" title="Tue" data-value="2">Tue</a></li>
						<li><a href="#" title="Wed" data-value="3">Wed</a></li>
						<li><a href="#" title="Thu" data-value="4">Thu</a></li>
						<li><a href="#" title="Fri" data-value="5">Fri</a></li>
						<li><a href="#" title="Say" data-value="6">Sat</a></li>
						<li><a href="#" title="Sun" data-value="7">Sun</a></li>
					</ul>
					<div class="clearfix"></div>
					<ul class="days">
						<c:forEach var="i" begin="2" end="${timetable[1].firstDayOfMonth.dayOfWeek.value }">
							<li><a href="#" ></a></li>
						</c:forEach>
						<c:forEach var="i" items="${ days[1]}">
							<c:choose>
								<c:when test="${i.value != null }">
									<li><a href="#" class="event">${i.key }</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="#" >${i.key }</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</ul>
					<div class="clearfix"></div>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
		<p>${calendar_text }</p>
        </div>
        <div class="carousel-item">
            <div class="calendar">
			<div class="col rightCol">
				<div class="content">
					<h2 class="year">${timetable[2].date.month } ${timetable[2].date.year }</h2>
					<div class="clearfix"></div>
					<ul class="weekday"> 
						<li><a href="#" title="Mon" data-value="1">Mon</a></li>
						<li><a href="#" title="Tue" data-value="2">Tue</a></li>
						<li><a href="#" title="Wed" data-value="3">Wed</a></li>
						<li><a href="#" title="Thu" data-value="4">Thu</a></li>
						<li><a href="#" title="Fri" data-value="5">Fri</a></li>
						<li><a href="#" title="Say" data-value="6">Sat</a></li>
						<li><a href="#" title="Sun" data-value="7">Sun</a></li>
					</ul>
					<div class="clearfix"></div>
					<ul class="days">
						<c:forEach var="i" begin="2" end="${timetable[2].firstDayOfMonth.dayOfWeek.value }">
							<li><a href="#" ></a></li>
						</c:forEach>
						<c:forEach var="i" items="${ days[2]}">
							<c:choose>
								<c:when test="${i.value != null }">
									<li><a href="#" class="event">${i.key }</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="#" >${i.key }</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</ul>
					<div class="clearfix"></div>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
		<p>${calendar_text }</p>
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
</div>
</body>
</html>