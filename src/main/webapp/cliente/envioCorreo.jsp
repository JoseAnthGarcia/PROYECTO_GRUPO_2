<%--
  Created by IntelliJ IDEA.
  User: Katherine
  Date: 29/11/2020
  Time: 18:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%String correo = (String) request.getAttribute("correo");%>
<%String nombres = (String) request.getAttribute("nombres");%>
<html>
<head>
    <title>Correo de confirmación</title>
    <jsp:include page="/bootstrapRepository.jsp"/>
    <jsp:include page="/includes/utf8Cod.jsp"/>

    <!-- para los iconos como botones -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>

        .container-fluid{
            padding: 7% 15% ;
        }
        h1{
            font-size: 4rem;
        }

        .btn {
            background-color: #343a40;
            border: none;
            color: white;
            padding: 12px 16px;
            font-size: 15px;
            cursor: pointer;
        }

        .tercero{
            position: absolute;
            right: 15%;
        }

    </style>
</head>
<header>
    <div class="collapse bg-dark" id="navbarHeader">
        <div class="container">

        </div>
    </div>
    <div class="navbar navbar-dark bg-dark box-shadow">
        <div class="container d-flex justify-content-between">
            <a href="#" class="navbar-brand d-flex align-items-center">
                <strong>MiMarca.com</strong>
            </a>

        </div>
    </div>
</header>
<body>

<div class="jumbotron jumbotron-fluid">
    <div class="container">
        <h1 class="display-4">Registro exitoso</h1>
        <h3 class="lead"><%=nombres%>, su cuenta con el correo '<%=correo%>' fue exitosamente creada. Muchas gracias por la confianza.</h3>
    </div>
</div>
<a href="<%=request.getContextPath()%>/LoginServlet">
    <button type="button" class="btn btn-success tercero">Entendido</button></a>


</body>
</html>
