<%@ page import="beans.DistritoBean" %>
<%--
  Created by IntelliJ IDEA.
  User: Katherine
  Date: 17/11/2020
  Time: 16:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="usuario" scope="request" type="beans.UsuarioBean" />

<jsp:useBean id="listaDistritos2" scope="request" type="java.util.ArrayList<beans.DistritoBean>"/>
<%
    String cambiar= (String) request.getAttribute("cambiar");
    boolean nombresB = request.getAttribute("nombresB") == null ? true : (Boolean) request.getAttribute("nombresB");
    boolean apellidosB = request.getAttribute("apellidosB") == null ? true : (Boolean) request.getAttribute("apellidosB");
    boolean distritoSelected = request.getAttribute("distritoSelected") == null ? true : (Boolean) request.getAttribute("distritoSelected");

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
        .centrar{
            align-items: center;
            justify-content: center;
        }

    </style>

    <title>Editar mi perfil</title>
</head>
<header>
    <jsp:include page="/cliente/includes/headerClient.jsp"/>
</header>
<div class="container">

    <h1 class="mb-5 mt-5">Editar mi perfil</h1>
    <div class="row">

        <div class="col-lg-6 col-md-12 centrar">
            <img src="https://www.flaticon.com/svg/static/icons/svg/3135/3135715.svg" width="408px" height="400px" />
        </div>
        <div class="col-lg-6 col-md-12 centrar">
            <div class="col-sm-12 mt-5">

                <form method="POST" action="<%=request.getContextPath()%>/UsuarioServlet?accion=actualizar&cambiar=<%=cambiar%>">
                    <div class="form-group row">
                        <label for="inputName" class="col-sm-2 col-form-label">Nombres:</label>
                        <div class="col-sm-10">
                            <% if (cambiar.equals("nombre")){ %>
                            <input type="text"
                                   class="form-control <%=nombresB?"":"is-invalid"%>"
                                   aria-describedby="inputNameFeedback"
                                   name="nombres"
                                   id="inputName" <%=request.getParameter("nombres")==null?"":"value='"+request.getParameter("nombres")+"'"%> value="<%=usuario.getNombre()%>">
                            <div id="inputNameFeedback" class="invalid-feedback">
                                Ingrese datos válidos, por favor.
                            </div>
                            <% } else {  %>
                            <input type="text"
                                   readonly class="form-control-plaintext "
                                   name="nombres"
                                   id="inputName" <%=request.getParameter("nombres")==null?"":"value='"+request.getParameter("nombres")+"'"%> value="<%=usuario.getNombre()%>">
                            <%}%>
                        </div>

                    </div>
                    <div class="form-group row">
                        <label for="inputLastName" class="col-sm-2 col-form-label">Apellidos:</label>
                        <div class="col-sm-10">
                            <% if (cambiar.equals("apellido")){ %>
                            <input type="text" class="form-control <%=apellidosB?"":"is-invalid"%>"
                                   aria-describedby="inputLastNameFeedback"
                                   name="apellidos"
                                   id="inputLastName" <%=request.getParameter("apellidos")==null?"":"value='"+request.getParameter("apellidos")+"'"%> value="<%=usuario.getApellido()%>">
                            <div id="inputLastNameFeedback" class="invalid-feedback">
                                Ingrese datos válidos, por favor.
                            </div>
                            <% } else {  %>
                            <input type="text"
                                   readonly class="form-control-plaintext <%=apellidosB?"":"is-invalid"%>"
                                   aria-describedby="inputLastNameFeedback"
                                   name="apellidos"
                                   id="inputLastName" <%=request.getParameter("apellidos")==null?"":"value='"+request.getParameter("apellidos")+"'"%> value="<%=usuario.getApellido()%>">
                            <div id="inputLastNameFeedback" class="invalid-feedback">
                                Ingrese datos válidos, por favor.
                            </div>
                            <%}%>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="staticDNI" class="col-sm-2 col-form-label">DNI</label>
                        <div class="col-sm-10">
                            <input type="text" readonly class="form-control-plaintext" id="staticDNI" value="<%=usuario.getDni()%>">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="staticCorreo" class="col-sm-2 col-form-label">Correo</label>
                        <div class="col-sm-10">
                            <input type="text" readonly class="form-control-plaintext" id="staticCorreo" value="<%=usuario.getCorreo()%>">
                        </div>
                    </div>


                    <div class="form-group row">
                        <label for="selectDistrict" class="col-sm-2 col-form-label">Distrito</label>
                        <div class="col-sm-10">
                            <% if (cambiar.equals("distrito")){ %>
                            <% if (request.getParameter("idDistrito") == null) {%>
                            <select class="form-control" id="selectDistrict" name="idDistrito">
                                    <% }else{ %>
                                <select class="form-control
                            <%=((request.getParameter("idDistrito")).equals("0") || distritoSelected==false)?"is-invalid":""%>"
                                        aria-describedby="idDistritoFeedback" id="selectDistrict" name="idDistrito">
                                    <% } %>
                                    <option  value="0">Elija un distrito</option>
                                    <option selected value="<%=usuario.getDistrito().getId()%>"><%=usuario.getDistrito().getNombre()%></option>
                                    <%for (DistritoBean distrito : listaDistritos2) {%>
                                    <%if(distrito.getId()== usuario.getDistrito().getId()){continue;}else{%>
                                    <option value="<%=distrito.getId()%>"
                                            <%=(request.getParameter("idDistrito")!=null &&
                                                    request.getParameter("idDistrito").equals(String.valueOf(distrito.getId())))?"selected":""%>><%=distrito.getNombre()%>
                                    </option>
                                    <%}%>
                                    <% } %>
                                </select>
                                <div id="idDistritoFeedback" class="invalid-feedback">
                                    Seleccione una opcion valida, por favor.
                                </div><% } else {%>
                                        <input hidden type="text" name="idDistrito" class="form-control-plaintext" value="<%=usuario.getDistrito().getId()%>">
                                        <div class="col-sm-10">
                                            <input type="text" readonly class="form-control-plaintext" value="<%=usuario.getDistrito().getNombre()%>">
                                        </div>
                                            <%}%>
                        </div>


                    </div>
                    <a href="<%=request.getContextPath()%>/UsuarioServlet?accion=miPerfil" class="btn btn-danger mt-3">Regresar</a>
                    <button type="submit" class="btn btn-success pull-right mt-3">Submit</button>
                </form>
            </div>
        </div>
    </div>


</div>

</body>
</html>
