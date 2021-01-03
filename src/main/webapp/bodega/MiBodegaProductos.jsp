<%@ page import="java.util.ArrayList" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="beans.ProductoBean" %>
<%@ page import="beans.PedidoBean" %><%--
  Created by IntelliJ IDEA.
  User: Anacleto
  Date: 8/11/2020
  Time: 12:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="listaProductoBodegas" scope="request" type="java.util.ArrayList<beans.ProductoBean>"/>
<jsp:useBean id="cantPag" scope="request" type="java.lang.Integer"/>
<jsp:useBean id="paginaAct" scope="request" type="java.lang.Integer"/>
<jsp:useBean id="productoBusqueda" scope="request" type="java.lang.String"/>

<html>
<head>
    <jsp:include page="/includes/utf8Cod.jsp"/>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>


    <!-- para los iconos como botones -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
        .btn {
            background-color: #ffffff;
            border: none;
            color: black;
            padding: 12px 16px;
            font-size: 15px;
            cursor: pointer;
        }

        .btn2 {
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

        /* Paginacion */
        .page-item .page-link {
            color: #343a40;
            border-color: #343a40;
        }
        .page-item.active .page-link {
            border-color: #343a40;
            background-color: #343a40;
        }
    </style>

    <title>Productos</title>
</head>
<body>

<!-- todo:  corregir el espaciado entre Mi Bodega, Pedidos y Productos -->
<header>
    <jsp:include page="includes/headerBodega.jsp" />
</header>



<div class="container" >

    <!-- botones -->
    <div class="container" style="margin-top: 20px">
        <div class="row">
            <div class="col-sm-8">
                <h1>Productos</h1>

            </div>
            <div class="col-sm-11">
                <% if(session.getAttribute("pedidosConProducto")!=null){
                    ArrayList<PedidoBean> pedidosConProducto = (ArrayList<PedidoBean>) session.getAttribute("pedidosConProducto"); %>
                <div class="alert alert-danger" role="alert">
                    <h6>El producto que desea eliminar se ha encontrado en los siguientes pedidos pendientes:</h6>
                    <%for(PedidoBean pedido: pedidosConProducto){%>
                    <h6>-<%=pedido.getCodigo()%></h6>
                    <%}%>
                </div>
                <%session.removeAttribute("pedidosConProducto");
                }%>

                <%if(session.getAttribute("productoAgregado")!=null){%>
                    <div class="alert alert-success" role="alert">
                        El producto fue agregado.
                    </div>
                <%session.removeAttribute("productoAgregado");}%>

                <%if(session.getAttribute("productoEliminado")!=null){%>
                    <div class="alert alert-danger" role="alert">
                        El producto fue eliminado.
                    </div>
                <%session.removeAttribute("productoEliminado");}%>

                <%if(session.getAttribute("productoActualizado")!=null){%>
                    <div class="alert alert-success" role="alert">
                        El producto fue editado.
                    </div>
                <%session.removeAttribute("productoActualizado");}%>
            </div>

        </div>
    </div>

    <!-- BARRA DE BUSQUEDA -->
    <div class="row">
        <div class="col-10">
            <form method="post" action="<%=request.getContextPath()%>/BodegaServlet?accion=buscar">
                <div class="form-group row">
                    <div class="col-10">
                        <input class="form-control" type="text" placeholder="Buscar producto"
                               name="textoBuscar" value="<%=productoBusqueda%>"/>
                    </div>
                </div>
            </form>
        </div>
        <div class="col-1">
            <a class="btn btn-danger btn2" href="<%=request.getContextPath()%>/BodegaServlet?accion=listar">Limpiar</a>
        </div>

        <div class="col-1">
            <div class="col-sm-1">
                <a href="<%=request.getContextPath()%>/BodegaServlet?accion=agregar">
                    <button class="btn"><i class="fa fa-plus"></i></button>
                </a>
            </div>
        </div>
    </div>

    <%if(listaProductoBodegas.size()==0){%>
    <div class="alert alert-danger" role="alert" align="center">
        No se ha encontrado ningún producto.
    </div>
    <%}else{%>
    <!-- LISTA DE PRODUCTOS -->
    <form>
        <div class="row">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th scope="col"></th>
                    <th scope="col">NOMBRE</th>
                    <th scope="col">DESCRIPCIÓN</th>
                    <th scope="col">STOCK</th>
                    <th scope="col">PRECIO UNITARIO</th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>

                <!-- Inicio del bucle para listar los productos-->
                <%
                    for(ProductoBean producto : listaProductoBodegas){
                %>
                <tr>
                    <td>
                        <img src="<%=request.getContextPath()%>/ImagenServlet?idProducto=<%=producto.getId()%>" height="50px">
                    </td>
                    <td><%=producto.getNombreProducto()%></td>
                    <td><%=producto.getDescripcion()%></td>
                    <td><%=producto.getStock()%></td>
                    <td>S/<%=producto.getPrecioProducto()%></td>
                    <td>
                        <a href="<%=request.getContextPath()%>/BodegaServlet?accion=editarProducto&idProducto=<%=producto.getId()%>">
                            <i class="fa fa-edit btn"></i>
                        </a>
                    </td>
                    <td>
                        <a onclick="return confirm('¿Estas seguro que desea eliminar <%=producto.getNombreProducto()%>')"
                           href="<%=request.getContextPath()%>/BodegaServlet?accion=eliminar&idProducto=<%=producto.getId()%>">
                            <i class="fa fa-trash btn"></i>
                        </a>
                    </td>
                </tr>
                <%
                    }
                %>

                </tbody>
            </table>
        </div>
    </form>


    <!-- paginacion -->
    <div class="row">
        <a href="<%=request.getContextPath()%>/BodegaServlet?accion=home" class="btn btn-danger btn2" >Regresar</a>
        <nav aria-label="Page navigation example" class = "mx-auto"> <!-- Recordar centro !! -->
            <ul class="pagination justify-content-center">
                <%if(paginaAct==1){%>
                <li class="page-item disabled">
                    <span class="page-link">Anterior</span>
                </li>
                <%}else{%>
                <li class="page-item">
                    <a class="page-link" href="<%=request.getContextPath()%>/BodegaServlet?accion=listar&pag=<%=paginaAct-1%>&productoBusqueda=<%=productoBusqueda%>">Anterior</a>
                </li>
                <%}%>

                <% for(int k=1; k<=cantPag; k++){
                    if(k==paginaAct){%>
                <li class="page-item active">
                          <span class="page-link"><%=k%><span class="sr-only">(current)</span>
                          </span>
                </li>
                <%      }else{%>
                <li class="page-item"><a class="page-link" href="<%=request.getContextPath()%>/BodegaServlet?accion=listar&pag=<%=k%>&productoBusqueda=<%=productoBusqueda%>"><%=k%></a></li>
                <%      }
                } %>


                <%if(paginaAct==cantPag){%>
                <li class="page-item disabled">
                    <span class="page-link">Siguiente</span>
                </li>
                <%}else{%>
                <li class="page-item">
                    <a class="page-link" href="<%=request.getContextPath()%>/BodegaServlet?accion=listar&pag=<%=paginaAct+1%>&productoBusqueda=<%=productoBusqueda%>">Siguiente</a>
                </li>
                <%}%>

            </ul>
        </nav>
    </div>
    <%}%>

</div>


</div>


</body>
</html>
