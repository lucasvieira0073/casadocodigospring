<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ attribute name="bodyClass" required="false"%>
<%@ attribute name="titulo" required="true"%>

<%@ attribute name="extraScripts" fragment="true"%> <!--  caso queira adicionar extras -->

<!DOCTYPE html>
<html>
<head>
	<c:url value="/resources/css/" var="cssPath"/>
		<c:url value="/resources/js/" var="jsPath"/>
		<c:url value="/resources/img/" var="imgPath"/>
	  <meta charset="utf-8"/>
	  
	  
	  <title>${titulo} - Casa do Código</title>
	  
		 <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
		  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"/>
		<link rel="icon"
			href="//cdn.shopify.com/s/files/1/0155/7645/t/177/assets/favicon.ico?11981592617154272979"
			type="image/ico" />
		<link href="https://plus.googlecom/108540024862647200608"
			rel="publisher"/>
		
		<link href="${cssPath}cssbase-min.css"
			rel="stylesheet" type="text/css" media="all" />
		<link href='http://fonts.googleapis.com/css?family=Droid+Sans:400,700'
			rel='stylesheet'/>
		<link href="${cssPath}fonts.css"
			rel="stylesheet" type="text/css" media="all" />
		<link href="${cssPath}fontello-ie7.css"
			rel="stylesheet" type="text/css" media="all" />
		<link href="${cssPath}fontello-embedded.css"
			rel="stylesheet" type="text/css" media="all" />
		<link href="${cssPath}fontello.css"
			rel="stylesheet" type="text/css" media="all" />
		<link href="${cssPath}style.css"
			rel="stylesheet" type="text/css" media="all" />
		<link href="${cssPath}layout-colors.css"
			rel="stylesheet" type="text/css" media="all" />
		<link href="${cssPath}responsive-style.css"
			rel="stylesheet" type="text/css" media="all" />
		<link href="${cssPath}guia-do-programador-style.css" 
			rel="stylesheet" type="text/css"  media="all"  />
	    <link href="${cssPath}produtos.css" 
	    	rel="stylesheet" type="text/css"  media="all"  />
		<link rel="canonical" href="http://www.casadocodigo.com.br/" />	
		<link href="${cssPath}book-collection.css"
				rel="stylesheet" type="text/css" media="all" />
</head>
<body class="${bodyClass}">

<%@include file="/WEB-INF/views/cabecalho.jsp" %>


<jsp:doBody/>

<%@include file="/WEB-INF/views/rodape.jsp" %>

<jsp:invoke fragment="extraScripts"/><!-- é aberta esta tag para na view que a utilizar poder colocar extras -->



</body>
</html>
