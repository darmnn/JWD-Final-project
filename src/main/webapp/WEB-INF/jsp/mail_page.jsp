<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Photobook</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    <link href="css/mail.css" rel="stylesheet" type="text/css">
    
    <fmt:setLocale value="${sessionScope.locale }"/>
    <fmt:setBundle basename="locale" var="loc"/>
    
    <fmt:message bundle="${loc}" key="button.en" var="en"/>
    <fmt:message bundle="${loc}" key="button.ru" var="ru"/>
    
</head>
<body>
<div>
	<jsp:include page="navbar.jsp"/>
	<div class="local-container">
		<form action="Controller" method="get" class="btn-group local" role="group" aria-label="Basic outlined example">
  		<button type="submit" name="command" value="en" class="btn btn-outline-primary">${en }</button>
  		<button type="submit" name="command" value="ru" class="btn btn-outline-primary">${ru }</button>
	</form>
	</div>
     
	<div class="container">
		<form class="email-form" action="Controller" method="post">
		<div class="row mb-3">
    <label for="inputEmail3" class="col-sm-2 col-form-label">Subject</label>
    <div class="col-sm-10">
      <input type="text" class="form-control" id="inputEmail3" name="subject">
    </div>
  </div>
  <div class="row mb-3">
    <label for="inputEmail3" class="col-sm-2 col-form-label">Email text</label>
    <div class="col-sm-10">
      <textarea class="form-control" id="exampleFormControlTextarea1" rows="3" name="message"></textarea>
    </div>
  </div>
  <div class="row mb-3">
    <label for="inputPassword3" class="col-sm-2 col-form-label">Password to your email</label>
    <div class="col-sm-10">
      <input type="password" class="form-control" id="inputPassword3" name="password">
    </div>
  </div>
  <button type="submit" class="btn btn-primary" name="command" value="sendemail">Send</button>
</form>
	</div>
	
</div>
</body>
</html>