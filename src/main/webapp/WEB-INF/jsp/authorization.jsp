<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Photobook</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    <link href="css/auth1.css" rel="stylesheet" type="text/css">
</head>
<body>
<div>

            <nav class="navbar navbar-light">
                	<a href = "index.jsp" class = "loadmain">PhotoBook</a>
            </nav>
            
            <div>
            	<c:out value = "${message }"/>
            </div>
            
            <form id="form" action="Controller" method="get">
                <p class="display-5">Sign in to Photobook</p>
                <div class="container p-4 my-3 border">
                    <div class="form-group">
                        <label for="inputUsername">Username</label>
                        <input name="username" value="" type="name" class="form-control" placeholder="Username"/>
                    </div>
                    <div class="form-group">
                        <label for="inputPassword">Password</label>
                        <input name="password" value="" type="password" class="form-control" id = "passw" placeholder="Enter password"/>
                    </div>
                    <div class="form-group">
                        <div class="button-box">
                            <button type="submit" name="command" value="authorization" class="btn btn-primary sign-in">Sign In</button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
</body>
</html>