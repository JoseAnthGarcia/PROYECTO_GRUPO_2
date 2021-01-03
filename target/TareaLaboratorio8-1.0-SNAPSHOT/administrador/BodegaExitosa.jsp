<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%String ruc = (String) request.getAttribute("ruc");%>
<%String nombreBodega = (String) request.getAttribute("nombreBodega");%>
<%String correo = (String) request.getAttribute("correo");%>
<html>
    <head>
        <title>Correo de confirmación</title>
        <jsp:include page="/bootstrapRepository.jsp"/>
        <jsp:include page="/includes/utf8Cod.jsp"/>

        <!-- para los iconos como botones -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <style>

            .container-fluid{
                padding: 7% 15% ;
            }
            h1{
                font-size: 4rem;
            }

            .btn {
                background-color: #343a40;
                border: none;
                color: white;
                padding: 12px 16px;
                font-size: 15px;
                cursor: pointer;
            }

            .tercero{
                position: absolute;
                right: 15%;
            }

        </style>
    </head>

    <jsp:include page="/administrador/headerAdmin.jsp"/>

    <body>
    <div class="jumbotron jumbotron-fluid">
        <div class="container">
            <h1 class="display-4">Registro exitoso</h1>
            <p class="lead">Se ha iniciado el proceso de registro  de la bodega <%=nombreBodega%> con ruc
                <%=ruc%>, revise su correo  <%=correo%> para establecer su contraseña y culminar con el registro.</p>
        </div>
    </div>


        <div class="row">
            <a href="<%=request.getContextPath()%>/AdminServlet?accion=miPerfil" class="btn btn-outline-success primero" style="position: absolute; right: 15%">Ok</a>
        </div>
    </body>
</html>