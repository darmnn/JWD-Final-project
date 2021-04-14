<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Photobook</title>
   	<link href="css/photo.css" rel="stylesheet" type="text/css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">

	<fmt:setLocale value="${sessionScope.locale }"/>
    <fmt:setBundle basename="locale" var="loc"/>

	<fmt:message bundle="${loc}" key="button.make_complaint" var="make_complaint"/>
    <fmt:message bundle="${loc}" key="button.report" var="report"/>
    <fmt:message bundle="${loc}" key="label.describe" var="describe"/>
    <fmt:message bundle="${loc}" key="placeholder.report" var="report_placeholder"/>

</head>
<body>
			<div style="text-align: center; width: 500px; margin-top:10%; margin-left:30%;" id="popupWin" class="modalwin">
            	<h3> ${describe } </h2>
            		<form action="Controller" method="post" id="get_complaint" class="complaint-form">
            				<input type="hidden" name="photo" value="${photo }"/>
    						<input  type="hidden" name="comment_id" value="${comment_id }"/>
                		<input type="text" name="complaint_text" class="form-control" id="formGroupExampleInput" placeholder="${report_placeholder}">
                		<button type="submit" name="command" value="newcomplaint" class="btn btn-primary btn-sm comp">${make_complaint }</button>
            		</form>
       		</div>
</body>
</html>