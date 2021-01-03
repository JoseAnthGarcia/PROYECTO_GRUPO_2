<%--
  Created by IntelliJ IDEA.
  User: Katherine
  Date: 17/11/2020
  Time: 14:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="usuario" scope="request" type="beans.UsuarioBean" />
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
        tr{
            height: 70px;
        }
        th{
            height: 70px;
        }

    </style>

    <title>Mi perfil</title>
</head>
<body>
<header>
    <jsp:include page="/cliente/includes/headerClient.jsp"/>
</header>
<div class=" Container">

    <h1 class="mb-5 mt-5">Mi  perfil </h1>
    <%if(session.getAttribute("contraseniaActualizada")!=null){%>
    <div align="center" class="alert alert-success" role="alert">
        La contraseña fue actualizada.
    </div>
    <%session.removeAttribute("contraseniaActualizada");}%>
    <div class="row">

        <div class="col-lg-6 col-md-12 centrar mb-3">
            <img src="https://www.flaticon.com/svg/static/icons/svg/3135/3135715.svg" width="408px" height="400px" />
        </div>
        <div class="col-lg-6 col-md-12 centrar">
            <table class="table">
                <tr>
                    <th>Nombres</th>
                    <td><%=usuario.getNombre()%></td>

                    <td><a href="<%=request.getContextPath()%>/UsuarioServlet?accion=editar&cambiar=nombre"
                           ><button class="btn"><i class="fa fa-edit"></i></button></a></td>
                </tr>
                <tr>
                    <th>Apellidos</th>
                    <td><%=usuario.getApellido()%></td>
                    <td><a href="<%=request.getContextPath()%>/UsuarioServlet?accion=editar&cambiar=apellido"
                           ><button class="btn"><i class="fa fa-edit"></i></button></a></td>
                </tr>
                <tr>
                    <th>DNI</th>
                    <td><%=usuario.getDni()%></td>
                    <td></td>
                </tr>
                <tr>
                    <th>Correo</th>
                    <td><%=usuario.getCorreo()%></td>
                    <td><a href="<%=request.getContextPath()%>/UsuarioServlet?accion=cambioContra"
                           class="btn btn-success">Cambiar Contraseña</a></td>
                </tr>
                <tr>
                    <th>Distrito</th>
                    <td><%=usuario.getDistrito().getNombre()%></td>
                    <td><a href="<%=request.getContextPath()%>/UsuarioServlet?accion=editar&cambiar=distrito"
                           ><button class="btn"><i class="fa fa-edit"></i></button></a></td>
                </tr>


            </table>
            <a href="<%=request.getContextPath()%>/UsuarioServlet?accion=Home" class="btn btn-danger mt-3">Regresar</a>
        </div>
    </div>


</div>


</body>
</html>
