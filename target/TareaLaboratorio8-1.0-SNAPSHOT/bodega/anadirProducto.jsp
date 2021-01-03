<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- validaciones de input -->
<%
    boolean validStock = request.getAttribute("validStock") != null ?
            ((boolean) request.getAttribute("validStock")) : true;

    boolean validPrecioUnitario = request.getAttribute("validPrecioUnitario") != null ?
            ((boolean) request.getAttribute("validPrecioUnitario")) : true;

    boolean validNombreProducto = request.getAttribute("validNombreProducto") != null ?
            ((boolean) request.getAttribute("validNombreProducto")) : true;

    boolean validDescripcion = request.getAttribute("validDescripcion") != null ?
            ((boolean) request.getAttribute("validDescripcion")) : true;

    boolean fotoVal = request.getAttribute("fotoVal") == null ?
            true : (Boolean) request.getAttribute("fotoVal");
%>

<html>
<head>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
            integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV"
            crossorigin="anonymous"></script>
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
    </style>

    <title>Agregar Producto</title>

</head>

<body>

<!-- todo:  corregir el espaciado entre Mi Bodega, Pedidos y Productos -->
<header>
    <jsp:include page="includes/headerBodega.jsp"/>
</header>

<p></p>
<main class="my-form">
    <div class="cotainer" style="margin-left: 5%; margin-right: 5%">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header">Registrar Producto</div>
                    <div class="card-body">

                        <!-- FORMULARIO -->
                        <% System.out.println(request.getContextPath()); %>
                        <form method="POST" action="<%=request.getContextPath()%>/BodegaServlet?accion=guardar"
                              enctype="multipart/form-data"
                        >

                            <!-- NOMBRE -->
                            <div class="form-group row">
                                <label for="nombreProducto"
                                       class="col-md-4 col-form-label text-md-right">Producto</label>
                                <div class="col-md-6">
                                    <input type="text" class="form-control <%=validNombreProducto?"":"is-invalid"%>"
                                           name="nombreProducto"
                                           id="nombreProducto"
                                           aria-describedby="validationServer05Feedback"
                                        <%=request.getParameter("nombreProducto")==null?"":"value='"+request.getParameter("nombreProducto")+"'"%>
                                           >
                                    <div id="validationServer05Feedback" class="invalid-feedback">
                                        No puede estar vacío
                                    </div>

                                </div>
                            </div>

                            <!-- DESCRIPCION -->
                            <div class="form-group row">
                                <label for="Descripcion"
                                       class="col-md-4 col-form-label text-md-right">Descripción</label>
                                <div class="col-md-6">
                                    <textarea type="text" id="Descripcion" class="form-control <%=validDescripcion?"":"is-invalid"%>"
                                              name="descripcion" maxlength="150"
                                              placeholder="Ingrese una descripcion"
                                              aria-describedby="validationFeedback"
                                    ><%=request.getParameter("descripcion")==null?"":request.getParameter("descripcion")%></textarea>
                                    <div id="validationFeedback" class="invalid-feedback">
                                        La descripción no debe exceder el límite de 150 caracteres.
                                    </div>
                                </div>
                            </div>

                            <!-- STOCK -->
                            <div class="form-group row">
                                <label for="Stock" class="col-md-4 col-form-label text-md-right">Stock</label>
                                <div class="col-md-6">
                                    <input type="text" class="form-control <%=validStock?"":"is-invalid"%>" name="stock"
                                           id="Stock"
                                           aria-describedby="validationServer03Feedback"
                                        <%=request.getParameter("stock")==null?"":"value='"+request.getParameter("stock")+"'"%>
                                           >
                                    <div id="validationServer03Feedback" class="invalid-feedback">
                                        Debe ser un número válido
                                    </div>
                                </div>
                            </div>

                            <!-- PRECIO UNITARIO -->
                            <div class="form-group row">
                                <label for="Precio" class="col-md-4 col-form-label text-md-right">Precio
                                    Unitario</label>
                                <div class="col-md-6">
                                    <input type="text" class="form-control <%=validPrecioUnitario?"":"is-invalid"%>"
                                           name="precioProducto"
                                           id="Precio"
                                        <%=request.getParameter("precioProducto")==null?"":"value='"+request.getParameter("precioProducto")+"'"%>
                                           aria-describedby="validationServer04Feedback" >
                                    <div id="validationServer04Feedback" class="invalid-feedback">
                                        Debe ser un número válido
                                    </div>
                                </div>

                            </div>

                            <!--imagenes -->
                            <div class="form-group row">
                                <label class="col-md-4 col-form-label text-md-right ">Imagen:</label>
                                <div class="col-md-6">
                                    <input type="file" name="foto">
                                </div>
                            </div>

                            <!-- BOTONES CONFIRMAR Y CANCELAR <-->
                            <div class="col-md-6 offset-md-4">
                                <a href="<%=request.getContextPath()%>/BodegaServlet?accion=listar">
                                    <input type="submit" value="Confirmar" class="btn btn-outline-secondary">
                                </a>
                                <a class="btn btn-outline-danger" href="<%=request.getContextPath()%>/BodegaServlet?accion=listar">
                                    Cancelar
                                </a>
                            </div>
                        </form>
                    </div>
                </div>

            </div>
            <%if(!fotoVal){%>
            <div class="col-sm-3">
                <div class="alert alert-danger" role="alert">
                    No se ha ingresado una fotografia o el formato es invalido.
                </div>
            </div>
            <%}%>
        </div>
    </div>


</main>
</body>
</html>
