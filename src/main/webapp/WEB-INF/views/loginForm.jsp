<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<c:url value="/resources/css/" var="cssPath"/>
	<c:url value="/resources/js/" var="jsPath"/>
	<link rel="stylesheet" href="${cssPath}bootstrap.min.css"/>
	<script src="${jsPath}jquery-3.3.1.min.js"></script>
	<script src="${jsPath}popper.min.js"></script>
	<script src="${jsPath}bootstrap.min.js"></script>
	<title>Casa do codigo</title>
</head>
<body>


	<div class="container">

		<h1>Login da Casa do código</h1>

		<form:form servletRelativeAction="/login" method="POST">
			<div class="form-group">
				<label>E-mail</label> 
				<input name="username" type="text" class="form-control"/>
			</div>
			<div class="form-group">
				<label>Senha</label>
				<input type="password" name="password" class="form-control"/>
			</div>
			<button type="submit" class="btn btn-primary">Logar</button>
		</form:form>
		
		<br/>
	
	</div>
</body>
</html>