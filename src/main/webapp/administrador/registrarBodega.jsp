<%@ page import="beans.BodegaBean" %>
<%@ page import="beans.DistritoBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="listaDistritos" scope="request" type="java.util.ArrayList<beans.DistritoBean>"/>

<%
    boolean rucB = request.getAttribute("rucB") == null ? true : (Boolean) request.getAttribute("rucB");
    boolean direccionB = request.getAttribute("direccionB") == null ? true : (Boolean) request.getAttribute("direccionB");
    boolean nombreBodegaB = request.getAttribute("nombreBodegaB") == null ? true : (Boolean) request.getAttribute("nombreBodegaB");
    boolean correoB = request.getAttribute("correoB") == null ? true : (Boolean) request.getAttribute("correoB");
    boolean distritoSelected = request.getAttribute("distritoSelected") == null ? true : (Boolean) request.getAttribute("distritoSelected");
    boolean rucExis = request.getAttribute("rucExis") == null ? false : (Boolean) request.getAttribute("rucExis");
    boolean fotoVal = request.getAttribute("fotoVal") == null ? true : (Boolean) request.getAttribute("fotoVal");
    boolean correoExis = request.getAttribute("correoExis") == null ? false : (Boolean) request.getAttribute("correoExis");
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

        <title>Registrarse</title>
        <% if( rucB && direccionB && nombreBodegaB && correoB && distritoSelected && !rucExis){ %>
        <script>
            $(document).ready(function () {
                $("#exampleModal").modal("show");
            });
        </script>
        <% } %>
    </head>
    <body>

        <!-- todo:  corregir el espaciado entre Mi Bodega, Pedidos y Productos -->
        <header>
            <% if( rucB && direccionB && nombreBodegaB && correoB && distritoSelected && rucExis && correoExis && !fotoVal){ %>
            <!-- Modal -->
            <div class="modal fade in" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            La bodega se registró exitosamente
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        </div>
                    </div>
                </div>
            </div>
            <% } %>
            <div class="collapse bg-dark" id="navbarHeader">
                <div class="container">

                </div>
            </div>
            <div class="navbar navbar-dark bg-dark box-shadow">
                <div class="container d-flex justify-content-between">
                    <div class="container d-flex justify-content-between">
                        <a onclick="return confirm('¿Estas seguro que deseas salir del registro de la bodega? ' +
                         '')"
                           href="<%=request.getContextPath()%>/AdminServlet?accion=miPerfil" class="navbar-brand d-flex align-items-center">
                            <strong>Administración</strong>
                        </a>
                        <a href="<%=request.getContextPath()%>/AdminServlet?accion=registrar" class="navbar-brand d-flex align-items-center">
                            <strong>Registrar bodega</strong>
                        </a>
                        <a onclick="return confirm('¿Estas seguro que deseas salir del registro de la bodega? ' +
                         '')"
                                href="<%=request.getContextPath()%>/AdminServlet?accion=listar" class="navbar-brand d-flex align-items-center">
                            <strong>Lista de bodegas</strong>
                        </a>
                        <div class="card">
                            <a href="<%=request.getContextPath()%>/LoginAdmin?accion=logout"><img src="https://www.flaticon.com/svg/static/icons/svg/1828/1828479.svg" height="30px"/></a>
                        </div>
                    </div>

                </div>
            </div>
        </header>
        <div class="modal" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Modal title</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <p>Modal body text goes here.</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary">Save changes</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="container" style="margin-top: 30px">
            <div class="row">
                <div class="col-sm-3">
                </div>
                <div class="col-sm-6">
                    <h1>Registro de bodega</h1>
                    <form method="POST" action="<%=request.getContextPath()%>/AdminServlet?accion=registrar" enctype="multipart/form-data">
                        <div class="form-group row">
                            <label for="nombreBodega" class="col-sm-2 col-form-label">Nombre:</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control <%=nombreBodegaB?"":"is-invalid"%>"
                                       aria-describedby="inputNameFeedback"
                                       name="nombreBodega"
                                       id="nombreBodega" <%=request.getParameter("nombreBodega")==null?"":"value='"+request.getParameter("nombreBodega")+"'"%>>
                                <div id="inputNameFeedback" class="invalid-feedback">
                                    El nombre ingresado no es válido.
                                </div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="direccion" class="col-sm-2 col-form-label">Dirección:</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control <%=direccionB?"":"is-invalid"%>"
                                       aria-describedby="inputdireccionFeedback"
                                       name="direccion"
                                       id="direccion" <%=request.getParameter("direccion")==null?"":"value='"+request.getParameter("direccion")+"'"%>>
                                <div id="inputdireccionFeedback" class="invalid-feedback">
                                    La dirección ingresada no es válida.
                                </div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="inputruc" class="col-sm-2 col-form-label">RUC:</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control <%=rucB?"":"is-invalid"%>"
                                       aria-describedby="inputrucFeedback"
                                       name="ruc"
                                       id="inputruc" <%=request.getParameter("ruc")==null?"":"value='"+request.getParameter("ruc")+"'"%>>
                                <div id="inputrucFeedback" class="invalid-feedback">
                                    El RUC ingresado no es válido.
                                </div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="inputEmail" class="col-sm-2 col-form-label">Correo:</label>
                            <div class="col-sm-10">
                                <input type="email" class="form-control <%=correoB?"":"is-invalid"%>"
                                       aria-describedby="inputEmailFeedback"
                                       name="correo"
                                       id="inputEmail" <%=request.getParameter("correo")==null?"":"value='"+request.getParameter("correo")+"'"%>>
                                <div id="inputEmailFeedback" class="invalid-feedback">
                                    El correo ingresado no es válido.
                                </div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="selectDistrict" class="col-sm-2 col-form-label">Distrito</label>
                            <div class="col-sm-10">
                                <% if (request.getParameter("idDistrito") == null) {%>
                                <select class="form-control" id="selectDistrict" name="idDistrito">
                                        <% }else{ %>
                                    <select class="form-control
                            <%=((request.getParameter("idDistrito")).equals("0") || distritoSelected==false)?"is-invalid":""%>"
                                            aria-describedby="idDistritoFeedback" id="selectDistrict" name="idDistrito">
                                        <% } %>
                                        <option selected value="0">Elija un distrito</option>
                                        <%for (DistritoBean distrito : listaDistritos) {%>
                                        <option value="<%=distrito.getId()%>"
                                                <%=(request.getParameter("idDistrito")!=null &&
                                                        request.getParameter("idDistrito").equals(String.valueOf(distrito.getId())))?"selected":""%>><%=distrito.getNombre()%>
                                        </option>
                                        <% } %>
                                    </select>
                                    <div id="idDistritoFeedback" class="invalid-feedback">
                                        Seleccione una opcion válida, por favor.
                                    </div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label  class="col-sm-2 col-form-label">Imagen:</label>
                            <div class="col-sm-10">
                                <input type="file" name="foto">
                            </div>
                        </div>
                        <a  onclick="return confirm('¿Estas seguro que deseas salir del registro de la bodega? ' +
                         '')"
                            href="<%=request.getContextPath()%>/AdminServlet" class="btn btn-danger">Regresar</a>
                        <button type="submit" class="btn btn-success pull-right">Registrar</button>
                    </form>
                </div>
                <div class="col-sm-3">
                    <%if(rucExis){%>
                    <div class="alert alert-danger" role="alert">
                        El ruc ingresado ya está registrado.
                    </div>
                    <%}%>
                    <%if(!fotoVal){%>
                    <div class="alert alert-danger" role="alert">
                        No se ha ingresado una fotografia o el formato es invalido.
                    </div>
                    <%}%>
                    <%if(correoExis){%>
                    <div class="alert alert-danger" role="alert">
                        El correo ingresado ya está registrado
                    </div>
                    <%}%>
                </div>
            </div>
        </div>


        </div>

        </div>

    </body>
</html>