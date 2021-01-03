<%@ page import="beans.BodegaBean" %>
<%@ page import="beans.DistritoBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    boolean rucExis = request.getAttribute("rucExis") == null ? true : (Boolean) request.getAttribute("rucExis");
%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="../bootstrapRepository.jsp"/>
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

            .margen {
                margin-top: 2%;
            }

            .container-fluid {
                text-align: center;
                padding: 3% 15%;
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

        <title>Recuperar contraseña</title>
    </head>
    <body>

        <!-- todo:  corregir el espaciado entre Mi Bodega, Pedidos y Productos -->
        <header>
            <div class="collapse bg-dark" id="navbarHeader">
                <div class="container">

                </div>
            </div>
            <div class="navbar navbar-dark bg-dark box-shadow">
                <div class="container d-flex justify-content-between">
                    <a  class="navbar-brand d-flex align-items-center">
                        <strong>MiMarca.com</strong>
                    </a>
                </div>
            </div>
        </header>


        <div class="container" style="margin-top: 30px">
            <div class="row">
                <div class="col-sm-3">
                </div>
                <div class="col-sm-6">
                    <h1>Recuperar contraseña</h1>
                    <form method="POST" action="<%=request.getContextPath()%>/LoginBodega?accion=actualizarContraCorreo">
                        <div class="form-group row">
                            <label for="inputRuc" class="col-sm-2 col-form-label">RUC:</label>
                            <div class="col-sm-10">
                                <input type="input" class="form-control <%=rucExis?"":"is-invalid"%>"
                                       aria-describedby="inputRucFeedback"
                                       name="ruc" id="inputRuc" <%=request.getParameter("ruc")==null?"":"value='"+request.getParameter("ruc")+"'"%>>
                                <div id="inputRucFeedback" class="invalid-feedback">
                                    El ruc no esta registrado.
                                </div>
                            </div>
                        </div>
                        <a href="<%=request.getContextPath()%>/LoginBodega" class="btn btn-success pull-left">Regresar</a>
                        <button type="submit" class="btn btn-success pull-right">Enviar</button>
                    </form>
                </div>
            </div>
        </div>


    </body>
</html>