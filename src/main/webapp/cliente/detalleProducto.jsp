<%--
  Created by IntelliJ IDEA.
  User: Katherine
  Date: 14/12/2020
  Time: 16:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="producto" type="beans.ProductoBean" scope="request"/>
<jsp:useBean id="vista" type="java.lang.String" scope="request"/>
<html>
<head>
    <title>Detalles de producto</title>
    <jsp:include page="/includes/utf8Cod.jsp"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <jsp:include page="/bootstrapRepository.jsp"/>
    <!-- para los iconos como botones -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <style>
        body {
            height: 100%;
        }

        body {
            text-align: left;
        }
        .btn {
            background-color: #343a40;
            border: none;
            color: white;
            padding: 12px 16px;
            font-size: 15px;
            cursor: pointer;
        }

        /* Darker background on mouse-over */
        .btn:hover {
            background-color: #767676;
        }

        .margen {
            margin-top: 2%;
        }

        .container-fluid {
            text-align: center;
            padding: 15px 15%;
        }

        .centrar{
            align-items: center;
            justify-content: center;
        }
        tr{
            height: 70px;
        }
        th{
            height: 70px;
        }
        .ingresa{
            width: 323px;
        }
        .izquierda{
            position: relative;
            left: 70px;
        }

    </style>
</head>
<body>
<header>
    <jsp:include page="/cliente/includes/headerClient.jsp"/>
</header>
<div class="container-fluid mt-5" style="text-align: left">

    <div class="row">
        <div class="col-lg-6 col-md-12">
            <img src="<%=request.getContextPath()%>/ImagenServlet?idProducto=<%=producto.getId()%>" width="300px" height="300px">
        </div>
        <div class="col-lg-6 col-md-12">
            <h1 class="mb-5"><%=producto.getNombreProducto()%></h1>
            <%if(!producto.getDescripcion().equals("")){%>
            <h3>Descripción: <%=producto.getDescripcion()%></h3>
            <%}%>
            <h3>Precio unitario: S/.<%=producto.getPrecioProducto()%></h3>
        </div>
    </div>
    <div class="row mt-5">
        <%if(vista.equals("realizarPedido")){%>
        <div>
            <a href="<%=request.getContextPath()%>/UsuarioServlet?accion=realizarPedido" class="button btn btn-primary mt-5">Regresar</a>
        </div>
        <%}%>
        <%if(vista.equals("productosDisponibles")){%>
        <div>
            <a href="<%=request.getContextPath()%>/UsuarioServlet?accion=productosDisponibles" class="button btn btn-primary mt-5">Regresar</a>
        </div>
        <%}%>

    </div>


</div>

</body>
</html>
