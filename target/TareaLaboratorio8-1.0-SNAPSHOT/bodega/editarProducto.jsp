<%@ page import="beans.ProductoBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="producto" scope="request" type="beans.ProductoBean" />

<!-- validaciones de input -->
<%
    boolean validStock = request.getAttribute("validStock") != null ?
            ((boolean) request.getAttribute("validStock")) : true;

    boolean validPrecioUnitario = request.getAttribute("validPrecioUnitario") != null ?
            ((boolean) request.getAttribute("validPrecioUnitario")) : true;

    //boolean validDescr = request.getAttribute("validDescr") != null ?
    //        ((boolean) request.getAttribute("validDescr")) : true;

    boolean esFoto = request.getAttribute("esFoto") != null ? ((boolean) request.getAttribute("esFoto")) : true;
%>

<html>
<head>
    <jsp:include page="/includes/utf8Cod.jsp"/>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>


    <!-- para los iconos como botones -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
        /* Darker background on mouse-over */
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
            background-color: #f05454;
        }
    </style>

    <title>Editar Producto</title>

</head>

<body>

<header>
    <jsp:include page="includes/headerBodega.jsp" />
</header>

<p></p>
<main class="my-form">
    <div class="cotainer" style="margin-left: 5%; margin-right: 5%">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header">Editar Producto: <%=producto.getNombreProducto()%></div>
                    <div class="card-body">

                        <!-- FORMULARIO -->
                        <% System.out.println(request.getContextPath()); %>
                        <form method="POST" action="<%=request.getContextPath()%>/BodegaServlet?accion=actualizar" enctype="multipart/form-data">
                                <input hidden name="idProducto" value="<%=producto.getId()%>">
                            <!-- DESCRIPCIO -->
                            <div class="form-group row">
                                <label for="Descripcion" class="col-md-4 col-form-label text-md-right">Descripción</label>
                                <div class="col-md-6">
                                    <textarea  type="text" id="Descripcion" class="form-control"  name="descripcion"><%=producto.getDescripcion()%></textarea>
                                </div>
                            </div>

                            <!-- STOCK -->
                            <div class="form-group row">
                                <label for="Stock" class="col-md-4 col-form-label text-md-right">Stock</label>
                                <div class="col-md-6">
                                    <input type="text" class="form-control <%=validStock?"":"is-invalid"%>" name="stock" value="<%=producto.getStock()%>"
                                           id="Stock"
                                           aria-describedby="validationServer03Feedback" required>
                                    <div id="validationServer03Feedback" class="invalid-feedback">
                                        Debe ser un número positivo
                                    </div>
                                </div>
                            </div>

                            <!-- PRECIO UNITARIO -->
                            <div class="form-group row">
                                <label for="Precio" class="col-md-4 col-form-label text-md-right">Precio Unitario</label>
                                <div class="col-md-6">
                                    <input type="text" class="form-control <%=validPrecioUnitario?"":"is-invalid"%>" name="precioProducto" value="<%=producto.getPrecioProducto()%>"
                                           id="Precio"
                                           aria-describedby="validationServer04Feedback" required>
                                    <div id="validationServer04Feedback" class="invalid-feedback">
                                        Debe ser un número positivo
                                    </div>
                                </div>

                            </div>
                            <!-- FOTO -->
                            <div class="form-group row">
                            <label for="foto" class="col-md-4 col-form-label text-md-right">Foto</label>
                            <div class="col-sm-2">
                                <input type="file" name="foto" id="foto" accept="image/*">
                            </div>
                            </div>


                            <!-- BOTONES CONFIRMAR Y CANCELAR <-->
                            <div class="col-md-6 offset-md-4">
                                <a href="<%=request.getContextPath()%>/BodegaServlet?accion=listar">
                                    <input type="submit" onclick="return confirm('¿Esta seguro de quiere editar este producto?')" value="Confirmar" class="btn btn-outline-secondary">
                                </a>
                                <a class="btn btn-outline-danger" href="<%=request.getContextPath()%>/BodegaServlet?accion=listar">
                                    Cancelar
                                </a>
                            </div>
                        </form>
                    </div>
                </div>

            </div>
        </div>
    </div>
</main>
<div class="container">
<div class="col-md-auto">
        <%if(!esFoto){%>
    <div class="alert alert-danger" role="alert">
        El formato de la foto es invalido.
    </div>
        <%}%>
    </div>
</div>
</body>
</html>
