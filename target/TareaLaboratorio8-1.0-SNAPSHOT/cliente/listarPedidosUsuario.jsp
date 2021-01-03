<%@ page import="beans.PedidoBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id= "listaPedidos" scope="request" type="java.util.ArrayList<beans.PedidoBean>" />
<jsp:useBean id="cantPag" scope="request" type="java.lang.Integer"/>
<jsp:useBean id="paginaAct" scope="request" type="java.lang.Integer"/>
<html>
<head>
    <jsp:include page="/bootstrapRepository.jsp"/>
    <jsp:include page="/includes/utf8Cod.jsp"/>
    <!-- para los iconos como botones -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
        .btn {
            background-color: #d6d2c4;
            border: none;
            color: black;
            padding: 12px 16px;
            font-size: 15px;
            cursor: pointer;
        }
        /* Darker background on mouse-over */
        .btn:hover {
            background-color: #f05454;
        }
        .margen{
            margin-top: 2%;
        }
        .container-fluid{
            text-align: center;
            padding: 3% 15% ;
        }
        .page-item .page-link {
            color: #343a40;
            border-color: #343a40;
        }
        .page-item.active .page-link {
            border-color: #343a40;
            background-color: #343a40;
        }
    </style>
    <title>Mis pedidos</title>
</head>
<body>
<header>
    <jsp:include page="/cliente/includes/headerClient.jsp"/>
</header>
<div class ='container'>
    <div class="row mt-5">
        <div class="col-3">
            <h1 class="margen">Mis pedidos</h1>
        </div>
        <div class="col-9">
            <p class="margen">Obs: Solo se puede cancelar el pedido hasta  una hora antes de la fecha de recojo.</p>
        </div>
    </div>


    <%if(request.getSession().getAttribute("errorCancelarPedido")!=null){%>
    <div align="center" class="alert alert-danger" role="alert">
        No es posible cancelar este pedido porque el tiempo límite de cancelación se ha cumplido.
    </div>
    <%request.getSession().removeAttribute("errorCancelarPedido");}%>

    <%if(request.getSession().getAttribute("pedidoCancelado")!=null){%>
    <div align="center" class="alert alert-success" role="alert">
        ¡Pedido cancelado con éxito!
    </div>
    <%request.getSession().removeAttribute("pedidoCancelado");}%>


    <div class="container-fluid">
        <table class="table container-fluid table-hover">
            <tr>
                <th>Código </th>
                <th>Costo total</th>
                <th>Fecha de registro</th>
                <th>Fecha de recojo</th>
                <th>Estado</th>
                <th>Cancelar pedido</th>
            </tr>
            <% for (PedidoBean pedido: listaPedidos){%>
            <tr>
                <td><a href="<%=request.getContextPath()%>/UsuarioServlet?accion=verDetallesPedido&codigoPedido=<%=pedido.getCodigo()%>" ><%=pedido.getCodigo()%></a> </td>
                <td>S/. <%=pedido.getTotalApagar()%></td>
                <td> <%=pedido.getFecha_registro()%></td>
                <td> <%=pedido.getFecha_recojo()%></td>
                <td><%=pedido.getEstado()%></td>
                <% if(pedido.getEstado().equalsIgnoreCase("Pendiente")){%>
                    <td>
                    <a onclick="return confirm('¿Estas seguro que deseas cancelar tu pedido?')"
                       href="<%=request.getContextPath()%>/UsuarioServlet?accion=cancelarPedido&codigoPedido=<%=Integer.parseInt(pedido.getCodigo())%>"
                       class="btn btn-danger">Cancelar</a></td>
                <% }else{ %>
                <td></td>
                <% }%>
            </tr>
            <% } %>
        </table>
    </div>
    <div class="row mb-5">
        <a href="<%=request.getContextPath()%>/UsuarioServlet?accion=Home" class="btn btn-outline-danger" style="background-color: #343a40; color: white;" >Regresar</a>
        <nav aria-label="Page navigation example" class = "mx-auto" > <!-- Recordar centro !! -->
            <ul class="pagination justify-content-center">
                <%if(paginaAct==1){%>
                <li class="page-item disabled">
                    <span class="page-link">Anterior</span>
                </li>
                <%}else{%>
                <li class="page-item">
                    <a class="page-link" href="<%=request.getContextPath()%>/UsuarioServlet?accion=listar&pag=<%=paginaAct-1%>">Anterior</a>
                </li>
                <%}%>





                <% for(int k=1; k<=cantPag; k++){
                    if(k==paginaAct){%>
                <li class="page-item active">
                          <span class="page-link"><%=k%><span class="sr-only">(current)</span>
                          </span>
                </li>
                <%      }else{%>
                <li class="page-item"><a class="page-link" href="<%=request.getContextPath()%>/UsuarioServlet?accion=listar&pag=<%=k%>"><%=k%></a></li>
                <%      }
                } %>


                <%if(paginaAct==cantPag){%>
                <li class="page-item disabled">
                    <span class="page-link">Siguiente</span>
                </li>
                <%}else{%>
                <li class="page-item">
                    <a class="page-link" href="<%=request.getContextPath()%>/UsuarioServlet?accion=listar&pag=<%=paginaAct+1%>">Siguiente</a>
                </li>
                <%}%>

            </ul>
        </nav>
    </div>



</div>

</body>
</html>
