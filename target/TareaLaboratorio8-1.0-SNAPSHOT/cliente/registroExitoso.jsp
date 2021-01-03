<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="codigo" scope="request" type="java.lang.String"/>

<html>
<head>
    <jsp:include page="/bootstrapRepository.jsp"/>
    <!-- para los iconos como botones -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <title>Mi Marca</title>
    <style>
        .izquierda1{
            left: 20px;
        }
        .izquierda2{
            left: 15px;
        }
        .izq{
            position: absolute;
            left: 15% ;
        }
        .der{
            position: absolute;
            right: 10% ;
        }
        .btn {
            background-color: #343a40;
            border: none;
            color: white;
            padding: 12px 16px;
            font-size: 15px;
            cursor: pointer;
        }
        .container-fluid{

            padding: 3% 15% ;
        }

        /* Darker background on mouse-over */
        .btn:hover {
            background-color: #767676;
        }
    </style>
</head>

<body>
<header>
    <jsp:include page="/cliente/includes/headerClient.jsp"/>
    <jsp:include page="/includes/utf8Cod.jsp"/>
</header>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3"></div>
        <div class="col-sm-6">
            <h1>¡Pedido con codigo <%=codigo%> realizado exitosamente!</h1>
            <h4>Recuerde que puede cancelar su pedido solo una hora antes de la fecha ingresada de recojo.</h4>
            <a class="btn btn-outline-success der mt-5" href="<%=request.getContextPath()%>/UsuarioServlet?accion=Home" >OK</a>
        </div>
        <div class="col-sm-3"></div>
    </div>
</div>

</body>
</html>
