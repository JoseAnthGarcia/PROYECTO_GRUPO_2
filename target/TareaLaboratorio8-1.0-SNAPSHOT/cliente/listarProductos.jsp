<%@ page import="beans.ProductoBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="listaProductos" type="java.util.ArrayList<dtos.ProductosClienteDTO>" scope="request"/>
<jsp:useBean id="paginaAct" type="java.lang.Integer" scope="request"/>
<jsp:useBean id="cantPag" type="java.lang.Integer" scope="request"/>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/bootstrapRepository.jsp"/>
    <jsp:include page="/includes/utf8Cod.jsp"/>
    <!-- para los iconos como botones -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
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

    <title>Productos disponibles</title>
</head>
<body>

<!-- todo:  corregir el espaciado entre Mi Bodega, Pedidos y Productos -->
<header>
    <jsp:include page="/cliente/includes/headerClient.jsp"/>
</header>
<div class="container" style="margin-top: 20px">
    <h1>Productos disponibles</h1>
</div>


<div class="container" style="margin-top: 30px">
    <!-- Presentacion de productos -->
    <% int cant = 0;
    for(int i=0; i<2; i++){
    %>
    <div class="row">
        <% int min = i*4;
            int max = (i+1)*4;
        for(int j=min; j<max; j++){
            if(cant < listaProductos.size()){
        %>
                <div class="col-sm-3"> <!-- Probar medidas "sm-3"? -->
                    <img src="<%=request.getContextPath()%>/ImagenServlet?idProducto=<%=listaProductos.get(j).getIdProducto()%>" width="80px" height="80px"  class="img-thumbnail">
                    <p class="mb-1"><b>Producto: </b> <a href="<%=request.getContextPath()%>/UsuarioServlet?accion=detalleProducto2&productSelect=<%=listaProductos.get(j).getIdProducto()%>"><%=listaProductos.get(j).getNombreProducto()%></a></p>
                    <p class="mb-0"><b>Precio: </b>S/. <%=listaProductos.get(j).getPrecio()%> </p>
                    <p class="mb-3"><b>Bodega: </b> <%=listaProductos.get(j).getBodega()%> </p>
                </div>
            <% } else{ %>
                <div class="col-sm-3"> <!-- Probar medidas "sm-3"? -->
                </div>
            <%}%>
        <% cant++;
            } %>
    </div>
    <% } %>

    <!-- paginacion -->
    <div class="row mt-5">

        <a href="<%=request.getContextPath()%>/UsuarioServlet?accion=Home" class="btn btn-outline-danger">Regresar</a>

        <nav aria-label="Page navigation example" class = "mx-auto"> <!-- Recordar centro !! -->
            <ul class="pagination justify-content-center">
                <%if(paginaAct==1){%>
                <li class="page-item disabled">
                    <span class="page-link">Anterior</span>
                </li>
                <%}else{%>
                <li class="page-item">
                    <a class="page-link" href="<%=request.getContextPath()%>/UsuarioServlet?accion=productosDisponibles&pag2=<%=paginaAct-1%>">Anterior</a>
                </li>
                <%}%>

                <% for(int k=1; k<=cantPag; k++){
                    if(k==paginaAct){%>
                <li class="page-item active">
                          <span class="page-link"><%=k%><span class="sr-only">(current)</span>
                          </span>
                </li>
                <%      }else{%>
                <li class="page-item"><a class="page-link" href="<%=request.getContextPath()%>/UsuarioServlet?accion=productosDisponibles&pag2=<%=k%>"><%=k%></a></li>
                <%      }
                } %>


                <%if(paginaAct==cantPag){%>
                <li class="page-item disabled">
                    <span class="page-link">Siguiente</span>
                </li>
                <%}else{%>
                <li class="page-item">
                    <a class="page-link" href="<%=request.getContextPath()%>/UsuarioServlet?accion=productosDisponibles&pag2=<%=paginaAct+1%>">Siguiente</a>
                </li>
                <%}%>

            </ul>
        </nav>

        <a href="<%=request.getContextPath()%>/UsuarioServlet?accion=escogerBodega1" class="btn btn-outline-success">Realizar un pedido</a>

    </div>




</body>
</html>