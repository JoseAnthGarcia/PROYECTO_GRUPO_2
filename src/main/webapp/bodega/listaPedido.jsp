<%@ page import = "java.util.ArrayList" %>
<%@ page import= "beans.PedidoBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="listaPedidos" scope="request" type="java.util.ArrayList<beans.PedidoBean>"/>
<jsp:useBean id="cantPag" scope="request" type="java.lang.Integer"/>
<jsp:useBean id="paginaAct" scope="request" type="java.lang.Integer"/>
<html>
<head>
    <title>Lista pedidos</title>
    <jsp:include page="../bootstrapRepository.jsp"/>
    <jsp:include page="/includes/utf8Cod.jsp"/>
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
        .btn-activar:hover {
            background-color: #34A100;
            color: white;
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
</head>
<body>

<header>
    <jsp:include page="includes/headerBodega.jsp" />
</header>


    <div class="container">
        <div class="row  mb-5">
            <div class="col-3">
                <h1 class="margen">Mis pedidos</h1>
            </div>
            <div class="col-9 "style="text-align: left">
                <p class="margen">Obs: Solo se puede cancelar el pedido si ha pasado un día de la fecha de recojo.</p>
            </div>
        </div>
        <div class="row">
            <%/*
            <div class="col-sm-6">
                <h1 class="margen">Lista de pedidos</h1>
            </div>
            */%>

            <div class="col-sm-6">
                <%if(session.getAttribute("valCancelar")!=null){
                if(!(boolean)session.getAttribute("valCancelar")){%>
                <div class="alert alert-danger" role="alert">
                    Aún no es posible cancelar este pedido.
                    Recuerde que solo es posible pasado un día de
                    la fecha de recojo.
                </div>
                <%}%>
                <%}%>
            </div>
            <%if(session.getAttribute("estado") != null){
                if(((String)session.getAttribute("estado")).equalsIgnoreCase("cancelado")){
                    if(session.getAttribute("valCancelar")!=null &&
                            (boolean)session.getAttribute("valCancelar")){%>
                    <div class="alert alert-success" role="alert">
                        ¡ Pedido <%=(String)session.getAttribute("estado")%> con éxito !
                    </div>
                    <%}
                        session.removeAttribute("valCancelar");%>
                <%}else{%>
                    <div class="alert alert-success" role="alert">
                        ¡ Pedido <%=(String)session.getAttribute("estado")%> con éxito !
                    </div>
                <%}%>
            <%session.removeAttribute("estado");}%>
        </div>
        <table class="table container-fluid table-hover">
            <tr>
                <th>Codigo</th>
                <th>Costo Total</th>
                <th>Fecha de registro</th>
                <th>Fecha de recojo</th>
                <th>Estado</th>
                <th></th>
                <th></th>
            </tr>
            <%for (PedidoBean pedidos : listaPedidos) { %>
            <tr>
                <td height="70px"><a href="<%=request.getContextPath()%>/BodegaServlet?accion=mostrarPedido&codigo=<%=pedidos.getCodigo() %>"><%=pedidos.getCodigo()%></a>
                </td>
                <td><%=pedidos.getTotalApagar()%></td>
                <td><%=pedidos.getFecha_registro()%></td>
                <td><%=pedidos.getFecha_recojo()%></td>
                <td height="70px"><%=pedidos.getEstado()%></td>
                <td>
                    <% if(pedidos.getEstado().equalsIgnoreCase("Pendiente")){
                    %>
                <a href="<%=request.getContextPath()%>/BodegaServlet?accion=entregarPedido&codigo=<%=pedidos.getCodigo()%>"
                   onclick="return confirm('¿Esta seguro de quiere entregar este pedido?')"
                   class="btn btn-success btn-activar">Pedido Entregado</a>
                <a href="<%=request.getContextPath()%>/BodegaServlet?accion=cancelarPedido&codigo=<%=pedidos.getCodigo()%>"
                   onclick="return confirm('¿Esta seguro de quiere cancelar este pedido?')"
                   class="btn btn-danger">Cancelar Pedido</a>
                    <% } %>
                </td>

            </tr>
            <% } %>
        </table>
        <div class="row">
            <a href="<%=request.getContextPath()%>/BodegaServlet?accion=home" class="btn btn-outline-danger" style="background-color: #343a40; color: white;">Regresar</a>
            <nav aria-label="Page navigation example" class = "mx-auto"> <!-- Recordar centro !! -->
                <ul class="pagination justify-content-center">
                    <%if(paginaAct==1){%>
                    <li class="page-item disabled">
                        <span class="page-link">Anterior</span>
                    </li>
                    <%}else{%>
                    <li class="page-item">
                        <a class="page-link" href="<%=request.getContextPath()%>/BodegaServlet?accion=listarPedidos&pag=<%=paginaAct-1%>">Anterior</a>
                    </li>
                    <%}%>

                    <% for(int k=1; k<=cantPag; k++){
                        if(k==paginaAct){%>
                    <li class="page-item active">
                          <span class="page-link"><%=k%><span class="sr-only">(current)</span>
                          </span>
                    </li>
                    <%      }else{%>
                    <li class="page-item"><a class="page-link" href="<%=request.getContextPath()%>/BodegaServlet?accion=listarPedidos&pag=<%=k%>"><%=k%></a></li>
                    <%      }
                    } %>


                    <%if(paginaAct==cantPag){%>
                    <li class="page-item disabled">
                        <span class="page-link">Siguiente</span>
                    </li>
                    <%}else{%>
                    <li class="page-item">
                        <a class="page-link" href="<%=request.getContextPath()%>/BodegaServlet?accion=listarPedidos&pag=<%=paginaAct+1%>">Siguiente</a>
                    </li>
                    <%}%>

                </ul>
            </nav>
        </div>
    </div>


</body>
</html>
