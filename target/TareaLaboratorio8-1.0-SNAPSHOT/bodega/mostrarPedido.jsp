<%@ page import="dtos.ProductoCantDto" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="beans.PedidoBean" %>
<%@ page import="beans.PedidoHasProductoBean" %>
<%@ page import="daos.UsuarioDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="pedidoProductoLista" scope="request" type="java.util.ArrayList<beans.PedidoHasProductoBean>"/>
<html>
<head>
    <jsp:include page="/bootstrapRepository.jsp"/>
    <jsp:include page="/includes/utf8Cod.jsp"/>
    <title>Ver pedido</title>
    <style>
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
        .container-fluid {
            text-align:left;
            padding: 15px 15%;
        }



    </style>
</head>
<body>
<header>
    <jsp:include page="includes/headerBodega.jsp" />
</header>
<div class="container-fluid " style="margin-top: 30px">
    <div class="row mt-3">
        <div class="col-sm-2"></div>
        <div class="col-sm-8">
            <%PedidoBean pedido = pedidoProductoLista.get(0).getPedido();%>
            <h1>Codigo del pedido: <%=pedido.getCodigo()%></h1>
            <h4>Bodega: <%=pedido.getBodegaBean().getNombreBodega()%></h4>
            <h4>Fecha y hora del registro: <%=pedido.getFecha_registro()%></h4>
            <h4>Fecha y hora de entrega: <%=pedido.getFecha_recojo()%></h4>
            <%UsuarioDao usuarioDao = new UsuarioDao();%>
            <h4>Fecha limite para cancelar el pedido: <%=usuarioDao.obtenerFechaMax(pedido.getCodigo())%></h4>
            <table class="table mt-5" style="text-align: center;">
                <tr>
                    <th>Nombre producto</th>
                    <th>Precio unitario</th>
                    <th>Unidades</th>
                    <th>Costo parcial por producto</th>
                </tr>
                <%for (PedidoHasProductoBean php : pedidoProductoLista){%>
                <tr>
                    <td><%=php.getProducto().getNombreProducto()%></td>
                    <td>S/. <%=php.getPrecioUnitario()%></td>
                    <td><%=php.getCantidad()%></td>
                    <%  BigDecimal cant = new BigDecimal(php.getCantidad());
                        BigDecimal precioParc = php.getPrecioUnitario().multiply(cant);%>
                    <td>S/. <%=precioParc%></td>
                </tr>
                <%}%>
            </table>
            <h1>Total a pagar: S/. <%=pedido.getTotalApagar()%></h1>
        </div>
        <div class="col-sm-2"></div>
    </div>
    <a class="btn btn-outline-danger" href="<%=request.getContextPath()%>/BodegaServlet?accion=listarPedidos">Regresar</a>
</div>

</body>
</html>
