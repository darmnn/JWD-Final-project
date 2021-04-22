<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="mytag" uri="/WEB-INF/tld/taglib.tld" %>
<html>
<head>
    <title>Photobook</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    <link href="css/auth1.css" rel="stylesheet" type="text/css">
    <link href="css/main.css" rel="stylesheet" type="text/css">
    
    <fmt:setLocale value="${sessionScope.locale }"/>
    <fmt:setBundle basename="locale" var="loc"/>
    
    <fmt:message bundle="${loc}" key="sign_in.message" var="sign_in_message"/>
    <fmt:message bundle="${loc}" key="input.username" var="username"/>
    <fmt:message bundle="${loc}" key="input.password" var="password"/>
    <fmt:message bundle="${loc}" key="button.sign_in" var="sign_in"/>
    <fmt:message bundle="${loc}" key="button.en" var="en"/>
    <fmt:message bundle="${loc}" key="button.ru" var="ru"/>
</head>
<body>
<div>

            <nav class="navbar navbar-light">
                	<a href = "index.jsp" class = "loadmain">PhotoBook</a>
            </nav>
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
            
            <!--<mytag:welcome/>-->
            
            <form id="form" action="Controller" method="get">
                <p class="display-5">${sign_in_message }</p>
                <div class="container p-4 my-3 border">
                    <div class="form-group">
                        <label for="inputUsername">${username }</label>
                        <input name="username" value="" type="name" class="form-control" placeholder="${username }"/>
                    </div>
                    <div class="form-group">
                        <label for="inputPassword">${password }</label>
                        <input name="password" value="" type="password" class="form-control" id = "passw" placeholder="${password }"/>
                    </div>
                    <div class="form-group">
                        <div class="button-box">
                            <button type="submit" name="command" value="authorization" class="btn btn-primary sign-in">${sign_in }</button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
</body>
</html>