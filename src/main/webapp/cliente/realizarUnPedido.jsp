<%@ page import="beans.ProductoBean" %>
<%@ page import="beans.BodegaBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="listaProductos" type="java.util.ArrayList<beans.ProductoBean>" scope="request"/>

<% int cantPag = request.getAttribute("cantPag") == null ? -1 : (int) request.getAttribute("cantPag");%>
<% int paginaAct = request.getAttribute("paginaAct") == null ? -1 : (int) request.getAttribute("paginaAct");%>
<% String busqueda= request.getAttribute("busqueda")==null? "": (String) request.getAttribute("busqueda");%>
<!DOCTYPE html>

<html>
<head>
    <jsp:include page="/bootstrapRepository.jsp"/>
    <jsp:include page="/includes/utf8Cod.jsp"/>
    <!-- para los iconos como botones -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <title>Realizar pedido</title>
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
    <jsp:include page="/cliente/includes/headerClient.jsp"/>
</header>

<div class="container" style=" margin: auto;margin-top: 3%;">

    <div class="row">
        <div class="col-sm-8">
            <h1>Realiza un pedido</h1>
            <%BodegaBean bodegaSeleccionada = (BodegaBean) session.getAttribute("bodegaEscogida");%>
            <h3>Bodega seleccionada: <%=bodegaSeleccionada.getNombreBodega()%></h3>
        </div>
        <div class="col-sm-4">
            <div class="row">
                <a class="btn btn-secondary" href="<%=request.getContextPath()%>/UsuarioServlet?accion=eliminarBodegaEscogida">Cancelar y elegir otra bodega</a>
            </div>
            <div class="row">
                <%if(session.getAttribute("productoExistente")!=null){%>
                <div class="alert alert-danger" role="alert">
                    El producto ya se encuentra seleccionado
                </div>
                <%session.removeAttribute("productoExistente");
                }%>
                <%if(session.getAttribute("productoAgregado")!=null){%>
                <div class="alert alert-success" role="alert">
                   Producto agregado satisfactoriamente
                </div>
                <%session.removeAttribute("productoAgregado");
                }%>
                <%if(session.getAttribute("errorStock")!=null){%>
                <div class="alert alert-danger" role="alert">
                    Ocurrió un problema al intentar realizar el pedido.
                </div>
                <%session.removeAttribute("errorStock");
                }%>
                <%if(session.getAttribute("errorAgregarCarrito")!=null){%>
                <div class="alert alert-danger" role="alert">
                    Ocurrió un problema al intentar seleccionar el producto.
                </div>
                <%session.removeAttribute("errorAgregarCarrito");
                }%>
                <%if(session.getAttribute("errorVerDetalles1")!=null){%>
                <div class="alert alert-danger" role="alert">
                    Ocurrió un problema al intentar ver detalles del producto.
                </div>
                <%session.removeAttribute("errorVerDetalles1");
                }%>
            </div>

        </div>

    </div>

    <form method="post" action="<%=request.getContextPath()%>/UsuarioServlet?accion=buscar">
        <div class="form-group row">
            <div class="col-10">
                <input class="form-control" type="text" placeholder="Buscar producto"
                       name="textoBuscar" value= "<%=busqueda%>" />
            </div>
            <div class="col-2">
                <a class="btn btn-danger"
                   href="<%= request.getContextPath()%>/UsuarioServlet?accion=realizarPedido">Limpiar</a>
            </div>
        </div>
    </form>


    <div class="row" style="text-align: center">
        <%//Listar productos de cierta bodega%>
        <%if(listaProductos.size()!=0){%>
        <table class="table table-hover">
            <thead>
            <tr>
                <th scope="col"></th>
                <th scope="col">Producto</th>
                <th scope="col">Precio Unitario</th>
                <th scope="col">
                </th>
            </tr>
            </thead>
            <tbody>
            <%for (ProductoBean producto : listaProductos) {%>

            <tr>
                <td><img src="<%=request.getContextPath()%>/ImagenServlet?idProducto=<%=producto.getId()%>" height="50px"></td>
                <td><a href="<%=request.getContextPath()%>/UsuarioServlet?accion=detalleProducto&productSelect=<%=producto.getId()%>"><%=producto.getNombreProducto()%></a>
                </td>
                <td>S/.<%=producto.getPrecioProducto()%>
                </td>
                <td>
                    <a class="btn btn-secondary" href="<%=request.getContextPath()%>/UsuarioServlet?accion=agregarCarrito&productSelect=<%=producto.getId()%>">Seleccionar</a>
                </td>
            </tr>
            <%}%>
            </tbody>
        </table>
    </div>

    <div class="row mt-5 mb-5">

        <a href="<%=request.getContextPath()%>/UsuarioServlet?accion=Home" class="btn btn-outline-danger <%=listaProductos.size()!=0?"izq":""%>">Volver a inicio</a>
        <%if (cantPag != -1) {%>
        <!-- paginacion -->
        <nav aria-label="Page navigation example" class="mx-auto"> <!-- Recordar centro !! -->
            <ul class="pagination justify-content-center mx-auto">
                <%if (paginaAct == 1) {%>
                <li class="page-item disabled">
                    <span class="page-link">Anterior</span>
                </li>
                <%} else {%>
                <li class="page-item">
                    <a class="page-link"
                       href="<%=request.getContextPath()%>/UsuarioServlet?accion=realizarPedido&pag=<%=paginaAct-1%>">Anterior</a>
                </li>
                <%}%>

                <% for (int k = 1; k <= cantPag; k++) {
                    if (k == paginaAct) {%>
                <li class="page-item active">
                          <span class="page-link"><%=k%><span class="sr-only">(current)</span>
                          </span>
                </li>
                <% } else {%>
                <li class="page-item"><a class="page-link"
                                         href="<%=request.getContextPath()%>/UsuarioServlet?accion=realizarPedido&pag=<%=k%>"><%=k%>
                </a></li>
                <% }
                } %>


                <%if (paginaAct == cantPag) {%>
                <li class="page-item disabled">
                    <span class="page-link">Siguiente</span>
                </li>
                <%} else {%>
                <li class="page-item">
                    <a class="page-link"
                       href="<%=request.getContextPath()%>/UsuarioServlet?accion=realizarPedido&pag=<%=paginaAct+1%>">Siguiente</a>
                </li>
                <%}%>

            </ul>
        </nav>
        <%}%>
        <a href="<%=request.getContextPath()%>/UsuarioServlet?accion=verCarrito" class="btn btn-outline-success <%=listaProductos.size()!=0?"der":""%>">Realizar pedido</a>
        <%}else{%>
        <div class="row">
            <div class="izquierda1 col-12 alert alert-danger ml-2" role="alert">
                No se ha encontrado ningún producto.
            </div>
            <div class="izquierda2 col-2" >
                <a href="<%=request.getContextPath()%>/UsuarioServlet?accion=Home" class="btn btn-outline-danger">Regresar</a>
            </div>
        </div>


        <%}%>
    </div>


</div>




</body>
</html>