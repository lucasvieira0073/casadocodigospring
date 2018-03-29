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
</nav>
<br/>

	<div class="container">

	<h1>Cadastro de produto</h1>

	<!-- build() pois ele constrói uma URL -->
	<!-- mvcUrl   o PC é abreviação para ProdutosController, ele usa as letras maiusculas do controller ou utilizar casadocodigo/produtos -->
	<form:form action="${s:mvcUrl('PC#gravar').build()}" method="POST" commandName="produto" enctype="multipart/form-data">
		<!-- com commandName ele sabe que estamos trabalhando com produto -->
		<div class="form-group">
			<label>Titulo</label> 
			<form:input path="titulo" cssClass="form-control"/>
			<form:errors path="titulo" />
		</div>
		<div class="form-group">
			<label>Descrição</label>
			<form:textarea path="descricao" cssClass="form-control"/>
			<form:errors path="descricao" />
		</div>
		<div class="form-group">
			<label>Páginas</label> 
			<form:input path="paginas" cssClass="form-control"/>
			<form:errors path="paginas" />
		</div>
		<div class="form-group">
			<label>Data de Lançamento</label> 
			<form:input path="dataLancamento" cssClass="form-control"/>
			<form:errors path="dataLancamento" />
		</div>
		<c:forEach items="${tipos}" var="tipoPreco" varStatus="status">
			<div class="form-group">
				<label>${tipoPreco }</label>
				<form:input path="precos[${status.index}].valor" cssClass="form-control"/> 
				<form:hidden path="precos[${status.index}].tipo" value="${tipoPreco}" />
			</div>

		</c:forEach>
		<div class="form-group">
			<label>Sumário</label>
			<input name="sumario" type="file" class="form-control"/>
		</div>
		<button type="submit" class="btn btn-primary">Cadastrar</button>
	</form:form>
	<br/>
</div>
</body>
</html>