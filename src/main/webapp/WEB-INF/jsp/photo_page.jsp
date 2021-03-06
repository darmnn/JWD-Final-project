<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Photobook</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
    <link href="css/main.css" rel="stylesheet" type="text/css">
    <link href="css/photo.css" rel="stylesheet" type="text/css">
    
    <fmt:setLocale value="${sessionScope.locale }"/>
    <fmt:setBundle basename="locale" var="loc"/>
    
    <fmt:message bundle="${loc}" key="button.sign_in" var="sign_in"/>
    <fmt:message bundle="${loc}" key="button.sign_up" var="sign_up"/>
    <fmt:message bundle="${loc}" key="button.logout" var="logout"/>
    <fmt:message bundle="${loc}" key="button.en" var="en"/>
    <fmt:message bundle="${loc}" key="button.ru" var="ru"/>
    <fmt:message bundle="${loc}" key="button.new_comment" var="new_comment"/>
    <fmt:message bundle="${loc}" key="placeholder.new_comment" var="new_comment_placeholder"/>
    <fmt:message bundle="${loc}" key="placeholder.report" var="report_placeholder"/>
    <fmt:message bundle="${loc}" key="button.rate" var="rate_button"/>
    <fmt:message bundle="${loc}" key="button.delete" var="delete"/>
    <fmt:message bundle="${loc}" key="button.make_complaint" var="make_complaint"/>
    <fmt:message bundle="${loc}" key="button.report" var="report"/>
    <fmt:message bundle="${loc}" key="label.describe" var="describe"/>
    <fmt:message bundle="${loc}" key="rate.message" var="rate"/>
    
    <style>
            .modalwin { 
            	background-color: white;
                height: 220px;  
                width: 350px;
                top: 20%;
                right: 0;
                left: 0;
                font-size: 14px; 
                margin: 0 auto;
                z-index:2;
                display: none; 
                position: fixed;
                padding: 15px;
            }
            #shadow { 
                position: fixed;
                width:100%;
                height:100%;
                z-index:1; 
                background:#000;
                opacity: 0.5; 
                left:0;
                top:0;
            }
        </style>
        
        <script type="text/javascript">
            function showModalWin() {
 
                var darkLayer = document.createElement('div');
                darkLayer.id = 'shadow'; 
                document.body.appendChild(darkLayer); 
 
                var modalWin = document.getElementById('popupWin'); 
                modalWin.style.display = 'block';
 
                darkLayer.onclick = function () {  
                    darkLayer.parentNode.removeChild(darkLayer); 
                    modalWin.style.display = 'none'; 
                    return false;
                };
            }
        </script>
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
        	<div class="card border-success mb-3 photo-container">
  				<div class="card-header bg-transparent border-success username-container">
  				<a href="Controller?command=loaduserpage&user=${photo.photographer}">${requestScope.photo.photographer }</a>
  				<c:if test="${photo.photographer == sessionScope.user.username || sessionScope.user.isAdmin}">
  					<form class="delete" action="Controller" method="post">
  						<input type="hidden" name="photo_id" value="${photo.id }"/>
  						<button type="submit" name="command" value="deletephoto" class="btn btn-danger btn-sm">${delete}</button>
  					</form>
  				</c:if>
  				</div>
  				
  				<div class="card-body text-success image-container">
    				<img class="photo" src="${requestScope.photo.imagePath }"/>
  				</div>
  				
  				<div class="card-footer bg-transparent border-success footer-container">
  					<c:forEach var = "i" begin = "1" end = "${requestScope.photo.rating }">
         				<img src="images/star.png" class="float-left star"/>
       			 	</c:forEach>
       			 	<c:if test="${sessionScope.auth != null }">
       			 	<form class="rate-form">
       			 	<select name="rating" class="form-select form-select-sm" aria-label="Default select example">
  						<option selected>${rate }</option>
  						<option value="1">1</option>
  						<option value="2">2</option>
  						<option value="3">3</option>
  						<option value="4">4</option>
  						<option value="5">5</option>
					</select>
					<input type="hidden" name="photo" value="${photo}"/>
					<button type="submit" name="command" value="ratephoto" class="btn btn-primary btn-sm">${rate_button }</button>
       			 	</form>
       			 	<c:if test="${sessionScope.user.isAdmin == false }">
       			 	<form class="complaint-btn">
       			 		<input form="get_complaint" type="hidden" name="photo" value="${photo }"/>
            			<input type="button" value="${report }" onclick="showModalWin()" class="complaint">
        			</form>
        			</c:if>
       			 	</c:if>
       			 
  					<div class="date">${requestScope.photo.date }</div>
       			 </div>
			</div>
			
			<div style="text-align: center" id="popupWin" class="modalwin">
            	<h3> ${describe } </h3>
            		<form action="Controller" method="post" id="get_complaint" class="complaint-form">
                		<input type="text" name="complaint_text" class="form-control" id="formGroupExampleInput" placeholder="${report_placeholder }">
                		<button type="submit" name="command" value="newcomplaint" class="btn btn-primary btn-sm comp">${make_complaint }</button>
            		</form>
       		</div>
			
			<div class="card comments-container">
			<c:forEach var = "comment" items = "${comments }">
				<c:choose>
					<c:when test="${comment.id == complaint_comment }">
									<div class="card comment">
  				<div style="border: 3px solid red;" class="card-body">
  					<p>
  					<c:choose>
    				<c:when test="${comment.authorPic != null}">
    					<img src="${comment.authorPic }" class="rounded-circle float-left author_pic"/>
    				</c:when>
    				<c:otherwise>
    					<img src="images/user_pic.png" class="rounded-circle float-left author_pic"/>
    				</c:otherwise>
    				</c:choose>
    				<c:if test="${comment.author == sessionScope.user.username || sessionScope.user.isAdmin}">
  						<button type="button" onclick="window.location.href='Controller?command=deletecomment&photo=${photo }&comment_id=${comment.id}'" class="btn-close" data-bs-dismiss="modal" aria-label="Close">
  						</button>
  					</c:if>
  					<a href="Controller?command=loaduserpage&user=${comment.author}">${comment.author }</a></p>
    				<div class="comment-text">${comment.text }</div>
    				<c:if test="${sessionScope.auth != null && sessionScope.user.isAdmin == false}">
    					<form action="Controller" class="complaint-btn">
    						<input type="hidden" name="photo" value="${photo }"/>
    						<input  type="hidden" name="comment_id" value="${comment.id }"/>
            				<button type="submit" name="command" value="loadreportpage" class="complaint">${report }</button>
        				</form>
        			</c:if>
    				<div class="date-comment">${comment.date }</div>
    			</div>
    			</div>
				</c:when>
				<c:otherwise>
					<div class="card comment">
  				<div class="card-body">
  					<p>
  					<c:choose>
    				<c:when test="${comment.authorPic != null}">
    					<img src="${comment.authorPic }" class="rounded-circle float-left author_pic"/>
    				</c:when>
    				<c:otherwise>
    					<img src="images/user_pic.png" class="rounded-circle float-left author_pic"/>
    				</c:otherwise>
    				</c:choose>
    				<c:if test="${comment.author == sessionScope.user.username || sessionScope.user.isAdmin}">
  						<button type="button" onclick="window.location.href='Controller?command=deletecomment&photo=${photo }&comment_id=${comment.id}'" class="btn-close" data-bs-dismiss="modal" aria-label="Close">
  						</button>
  					</c:if>
  					<a href="Controller?command=loaduserpage&user=${comment.author}">${comment.author }</a></p>
    				<div class="comment-text">${comment.text }</div>
    				<c:if test="${sessionScope.auth != null && sessionScope.user.isAdmin == false}">
    					<form action="Controller" class="complaint-btn">
    						<input type="hidden" name="photo" value="${photo }"/>
    						<input  type="hidden" name="comment_id" value="${comment.id }"/>
            				<button type="submit" name="command" value="loadreportpage" class="complaint">${report }</button>
        				</form>
        			</c:if>
    				<div class="date-comment">${comment.date }</div>
    			</div>
    			</div>
				</c:otherwise>
				</c:choose>
			</c:forEach>
			<c:if test="${sessionScope.auth != null }">
				<form action="Controller" method="post" class="new-comment">
    				<input type="text" name="comment_text" class="form-control" id="formGroupExampleInput" placeholder="${new_comment_placeholder }">
    				<input type="hidden" name="photo" value="${photo}"/>
    				<button type="submit" name="command" value="newcomment" class="btn btn-primary btn-sm">${new_comment }</button>
    			</form>
			</c:if>
  			</div>
	</div>
</div>
</body>
</html>