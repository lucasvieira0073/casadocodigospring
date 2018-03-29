<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<c:url value="/resources/css/" var="cssPath" />
<c:url value="/resources/js/" var="jsPath" />
<link rel="stylesheet" href="${cssPath}bootstrap.min.css" />
<script src="${jsPath}jquery-3.3.1.min.js"></script>
<script src="${jsPath}popper.min.js"></script>
<script src="${jsPath}bootstrap.min.js"></script>
<title>Casa do codigo</title>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <a class="navbar-brand" href="${s:mvcUrl('HC#index').build()}">Navbar</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
    <div class="navbar-nav">
      <a class="nav-item nav-link" href="${s:mvcUrl('PC#listar').build()}">Lista de Produtos</a>
      <a class="nav-item nav-link" href="${s:mvcUrl('PC#form').build()}">Cadastro de Produtos</a>
    </div>
  </div>
  <div class="nav navbar-nav navbar-right">
    <div class="navbar-nav">
      <a class="nav-item nav-link" href="#">
      	<security:authentication property="principal.username"/><!-- ou usar <security:authentication property="principal" var="usuario"/>  e pegar com EL-->
      </a>
    </div>
  </div>
</nav>
<br/>

	<div class="container">

		<h1>Lista de produtos</h1>


		<div>${sucesso}</div>
		<div>${falha}</div>

		<table class="table table-bordered table-striped table-hover">
			<tr>
				<th>Titulo</th>
				<th>Descricao</th>
				<th>Preços</th>
				<th>Paginas</th>
			</tr>

			<c:forEach items="${produtos}" var="produto">
				<tr>
					<td><a
						href="${s:mvcUrl('PC#detalhe').arg(0,produto.id).build()}">${produto.titulo}</a>
					</td>
					<td>${produto.descricao}</td>
					<td>${produto.precos}</td>
					<td>${produto.paginas}</td>
				</tr>
			</c:forEach>

		</table>
	</div>
</body>
</html>