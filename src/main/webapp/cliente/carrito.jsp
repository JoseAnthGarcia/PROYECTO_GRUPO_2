<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="beans.BodegaBean" %>
<%@ page import="java.lang.reflect.Array" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="beans.ProductoBean" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="daos.UsuarioDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>

<html>
<head>
    <jsp:include page="/bootstrapRepository.jsp"/>
    <jsp:include page="/includes/utf8Cod.jsp"/>
    <!-- para los iconos como botones -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <title>Productos seleccionados</title>
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
        .primero{
            position: absolute;
            left: 15%;
        }

        .tercero{
            position: absolute;
            right: 15%;
        }
    </style>

</head>
<body>

<header>
    <jsp:include page="/cliente/includes/headerClient.jsp"/>
</header>

<div class="container" style="margin-top: 30px">
    <div class="row col-12">
        <div class="col-sm-3">
            <h1>Productos seleccionados</h1>
        </div>

        <div class="col-sm-5">
            Obs: Los valores maximos son de acuerdo al stock del producto.
        </div>


        <div class="col-sm-4">
            <img src="https://image.flaticon.com/icons/png/512/116/116356.png" height="50px"/>
        </div>
    </div>
    <form method="POST" action="<%=request.getContextPath()%>/UsuarioServlet?accion=generarPedido">
    <div class="row mt-5">
        <%HttpSession session1 = request.getSession();
            HashMap<Integer, ProductoBean> listaProductos = (HashMap<Integer, ProductoBean>) session1.getAttribute("carrito");
        if(!listaProductos.isEmpty()){%>
        <div class="row mt-5 mx-auto">
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">Producto</th>
                    <th scope="col">Precio Unitario</th>
                    <th scope="col">Cantidad</th>
                    <th scope="col">
                    </th>
                </tr>
                </thead>
                <tbody>
                <%UsuarioDao usuarioDao = new UsuarioDao();
                    for (Map.Entry<Integer, ProductoBean> entry : listaProductos.entrySet()){%>
                <tr>
                    <td><%=entry.getValue().getNombreProducto()%>
                    </td>
                    <td><%=usuarioDao.obtenerProducto(entry.getValue().getId()).getPrecioProducto()%>
                    </td>
                    <td>
                        <%if(usuarioDao.obtenerProducto(entry.getValue().getId()).getStock()==0){%>
                        <input name="<%=entry.getValue().getId()%>" type="number" id="Stock" class="form-control is-invalid">
                        <%}else if(request.getParameter(String.valueOf(entry.getValue().getId()))==null){%>
                        <input name="<%=entry.getValue().getId()%>" type="number" id="Stock" class="form-control" >
                        <%}else{
                        String textValid = request.getParameter(String.valueOf(entry.getValue().getId())).equals("")
                                || Integer.parseInt(request.getParameter(String.valueOf(entry.getValue().getId())))<1
                                || Integer.parseInt(request.getParameter(String.valueOf(entry.getValue().getId())))> usuarioDao.obtenerProducto(entry.getValue().getId()).getStock()
                                ?"is-invalid":"";
                        String textvalue = request.getParameter(String.valueOf(entry.getValue().getId())).equals("")?"":"value='"+request.getParameter(String.valueOf(entry.getValue().getId()))+"'";%>
                        <input name="<%=entry.getValue().getId()%>" type="number" id="Stock" class="form-control <%=textValid%>"
                        <%=textvalue%>>
                        <%}%>
                        <div class="invalid-feedback">
                            <%if(usuarioDao.obtenerProducto(entry.getValue().getId()).getStock()==0){%>
                            Producto agotado
                            <%}else{%>
                            Ingrese una cantidad válida (stock: <%=usuarioDao.obtenerProducto(entry.getValue().getId()).getStock()%>).
                            <%}%>
                        </div>
                    </td>
                    <td>
                        <a class="btn btn-secondary" href="<%=request.getContextPath()%>/UsuarioServlet?accion=eliminarProductCarrito&productSelect=<%=entry.getValue().getId()%>">Eliminar</a>
                    </td>
                </tr>
                <%}%>
                </tbody>
            </table>
        </div>


        <div class="row col-12 mt-5 mb-3">
            <a href="<%=request.getContextPath()%>/UsuarioServlet?accion=realizarPedido" class="btn btn-outline-success primero" >Regresar</a>
            <button type="submit" class="btn btn-outline-success tercero " style="margin-bottom: 80px;">Generar pedido</button>
        </div>
        <%}else{%>
        <div class="alert alert-secondary" role="alert">
            No existe productos seleccionados.
        </div>
        <div class="row col-12 mt-5 mb-3">
        <a href="<%=request.getContextPath()%>/UsuarioServlet?accion=realizarPedido" class="btn btn-outline-success primero " >Regresar</a>
        <%}%>
        </div>

    </div>
    </form>

</div>




</body>
</html>