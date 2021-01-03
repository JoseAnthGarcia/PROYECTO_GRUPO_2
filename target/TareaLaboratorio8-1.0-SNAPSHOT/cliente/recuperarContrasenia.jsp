<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="usuarioP" scope="session" type="beans.UsuarioBean" />
<%
    //boolean contrasenia = request.getAttribute("contrasenia") == null ? true : (Boolean) request.getAttribute("contrasenia");
    boolean contRecu = request.getAttribute("contRecu") == null ? true : (Boolean) request.getAttribute("contRecu");
    boolean contRecuEmpty1 = request.getAttribute("contRecuEmpty1") == null ? true : (Boolean) request.getAttribute("contRecuEmpty1");
    boolean contRecuEmpty2 = request.getAttribute("contRecuEmpty2") == null ? true : (Boolean) request.getAttribute("contRecuEmpty2");
    boolean contraTrim = request.getAttribute("contraTrim") == null ? true : (Boolean) request.getAttribute("contraTrim");
    boolean contraSecu1 = request.getAttribute("contraSecu1") == null ? true : (Boolean) request.getAttribute("contraSecu1");
    boolean contraSecu2 = request.getAttribute("contraSecu2") == null ? true : (Boolean) request.getAttribute("contraSecu2");


%>
<html>
<head>
    <jsp:include page="/bootstrapRepository.jsp"/>
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
        .margen{
            margin-top: 2%;
        }
        .container-fluid{
            text-align: center;
            padding: 3% 5% ;
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


    <title>Recuperar Contraseña</title>
</head>
<body>
<div class="collapse bg-dark" id="navbarHeader">
    <div class="container">

    </div>
</div>
<div class="navbar navbar-dark bg-dark box-shadow">
    <div class="container d-flex justify-content-between">
        <a href="#" class="navbar-brand d-flex align-items-center">
            <strong>MiMarca.com</strong>
        </a>
        <!-- a href="#"><img src="imagenes/sigout.png" height="30px"/></a-->
    </div>
</div>
<div class='container-fluid' style="margin-top: 30px">
    <div class="row">
        <div class="col-sm-3">
        </div>
        <div class="col-sm-6">
            <h1 class="margen">Restablecer su contraseña</h1>
            <h4 align="left" style="margin-top: 30px">Usuario : <%=usuarioP.getNombre()%> <%=usuarioP.getApellido()%> </h4>
            <form class="margen"  method="POST" action="<%=request.getContextPath()%>/LoginServlet?accion=validarContraRecu" style="margin-top: 90px">
                <!-- Puesto para solucionar un problema sin alterar el codigo de otro colaborador-->
                <div class="form-group row">
                    <label for="inputPassword1" class="col-sm-2 col-form-label">Nueva Contraseña:</label>
                    <div class="col-sm-10">
                        <input type="password" class="form-control <%=(contRecuEmpty1 && contraSecu1 && contraTrim)?"":"is-invalid"%>"
                               aria-describedby="inputPasswordFeedback"
                               name="contraseniaR" id="inputPassword1" <%=request.getParameter("contraseniaR")==null?"":"value='"+request.getParameter("contraseniaR")+"'"%> >
                        <div id="inputPasswordFeedback" class="invalid-feedback">
                            La contraseña debe tener longitud mínima de 8 caracteres, no incluir espacios vacios e incluir letras y números.
                        </div>
                    </div>
                </div>
                <p class="margen"></p>
                <div class="form-group row">
                    <label for="inputPassword2" class="col-sm-2 col-form-label">Confirmar Contraseña:</label>
                    <div class="col-sm-10">
                        <input type="password" class="form-control <%=(contRecuEmpty2 && contraSecu2)?"":"is-invalid"%>"
                               aria-describedby="inputPasswordBFeedback"
                               name="contrasenia2R" id="inputPassword2" <%=request.getParameter("contrasenia2R")==null?"":"value='"+request.getParameter("contrasenia2R")+"'"%> >
                        <div id="inputPasswordBFeedback" class="invalid-feedback">
                            La contraseña debe tener longitud mínima de 8 caracteres, no incluir espacios vacios e incluir letras y números.
                        </div>
                    </div>
                </div>
                <p class="margen"></p>
                <div align="left">
                    <a href="<%=request.getContextPath()%>/LoginServlet" class="btn btn-danger mt-3">Regresar</a>
                    <button type="submit" class="btn btn-success pull-right mt-3">Confirmar</button>
                </div>
            </form>
        </div>
        <div class="col-sm-3">
            <%if(!contRecu){%>
            <div class="alert alert-danger" role="alert">
                Los campos de la  nueva contraseña no coinciden!
            </div>
            <%}%>
        </div>
    </div>
</div>

</body>
</html>
