<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<html>
<head>
    <title>Photobook</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    <link href="css/reg.css" rel="stylesheet" type="text/css">
</head>
<body>
<div>
            <nav class="navbar navbar-light" style="background-color: #e3f2fd;">
                <div class="header">
                    <div class="name">PhotoBook</div>
                    <button class="btn btn-outline-primary sign-up">Sign In</button>
                </div>
            </nav>
            <form id="form" action="Controller" method="post">
                <p class="display-4">Create your account</p>
                <div class="container p-4 my-3 border">
                    <div class="form-group">
                        <label for="inputUsername">Username</label>
                        <input class="form-control" placeholder="Username" name="username"/>
                    </div>
                    <div class="form-group">
                        <label for="inputEmail">Email address</label>
                        <input type="email" class="form-control" placeholder="Enter email" name="email"/>
                    </div>
                    <div class="form-group">
                        <label for="inputPassword">Password</label>
                        <input value="" type="password" class="form-control" id = "passw" placeholder="Enter password" name="password"/>
                    </div>
                    <div class = "form-group">
                    	<input type="checkbox" name="checkbox">I'm a photographer</input>
                    </div>
                    <div class="form-group">
                        <button type="submit" name="command" value="registration" class="btn btn-outline-primary sign-up">Sign Up</button>
                    </div>
                </div>
            </form>
        </div>
</body>
</html>