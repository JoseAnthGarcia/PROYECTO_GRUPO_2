<%@ page import="java.math.BigDecimal" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>

<jsp:useBean id="codigoPedido" type="java.lang.String" scope="request"/>
<jsp:useBean id="precioTotal" type="java.math.BigDecimal  " scope="request"/>
<%
    boolean errorFecha = request.getAttribute("errorFecha") == null ? false : (Boolean) request.getAttribute("errorFecha");

%>

<html>
<head>
    <jsp:include page="/bootstrapRepository.jsp"/>
    <jsp:include page="/includes/utf8Cod.jsp"/>
    <!-- para los iconos como botones -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <title>Confirmar su pedido</title>
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

        /* Darker background on mouse-over */
        .btn:hover {
            background-color: #767676;
        }
    </style>

</head>
<body>
<header>
    <jsp:include page="/cliente/includes/headerClient.jsp"/>
</header>

<div class="container" style="margin-top: 65px">
    <div class="row">
        <div class="col-sm-3"></div>
        <div class="col-sm-6">
            <h1>Confirmar pedido:</h1>
            <form method="POST" action="<%=request.getContextPath()%>/UsuarioServlet?accion=confirmarPedido">
                <div class="form-group">
                    <label for="codigo">Codigo del pedido:</label>
                    <input name="codigo" type="text" class="form-control" value="<%=codigoPedido%>" placeholder="<%=codigoPedido%>" id="codigo" readonly>
                </div>
                <div class="form-group">
                    <label>Fecha de recojo:</label>
                    <input class="form-control datetimepicker" id="fecha" name="fecha"
                           type="datetime-local"/>
                    <small class="form-text text-muted">Ingrese la fecha de recojo de su pedido.</small>
                </div>
                <div class="form-group">
                    <label for="precioTotal">Precio total de su pedido:</label>
                    <input name="precioTotal" type="number" class="form-control" value="<%=precioTotal%>" placeholder="<%=precioTotal%>" id="precioTotal" readonly>
                </div>
                <button onclick="return confirm('¿Esta seguro de realizar este pedido?')" type="submit" class="btn btn-outline-success der">Confirmar pedido</button>
                <a href="<%=request.getContextPath()%>/UsuarioServlet?accion=verCarrito" class="btn btn-outline-danger izq>">Regresar</a>
            </form>
        </div>
        <div class="col-sm-3">
            <%if(errorFecha){%>
            <div class="alert alert-danger" role="alert">
                Escoja una fecha válida, por favor.
            </div>
            <%}%>

        </div>
    </div>
</div>





</body>
</html>