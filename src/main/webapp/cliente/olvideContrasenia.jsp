<%
    boolean correoBoo = request.getAttribute("correoBoo") == null ? true : (Boolean) request.getAttribute("correoBoo");
    boolean correoNoEx = request.getAttribute("correoNoEx") == null ? true : (Boolean) request.getAttribute("correoNoEx");

%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

    <title>Recuperar contraseña</title>
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
<div class='container-fluid' style="margin-top: 20px">
    <div class="row">
        <div class="col-sm-3">
        </div>
        <div class="col-sm-6">
            <h1 class="margen">Restablecer su contraseña</h1>
            <p class="margen"></p>
            <p class="margen"></p>
            <p class="margen"></p>
            <div></div>
            <div></div>
            <form class="margen"  method="POST" action="<%=request.getContextPath()%>/LoginServlet?accion=comprobarCorreo" style="margin-top: 90px">

                <!-- input type="hidden" class="form-control" name="contrasenia" id="contrasenia" value="la contraseña no esta aquí"-->

                <div class="form-group row">
                    <label for="inputEmail" class="col-sm-2 col-form-label">Ingrese su correo:</label>
                    <div class="col-sm-10">
                        <input type="email" class="form-control <%=correoBoo?"":"is-invalid"%>"
                               aria-describedby="inputEmailFeedback"
                               name="correo"
                               id="inputEmail" <%=request.getParameter("correoBoo")==null?"":"value='"+request.getParameter("correoBoo")+"'"%>>
                        <div id="inputEmailFeedback" class="invalid-feedback">
                            Ingrese un correo válido
                        </div>
                    </div>
                </div>
                <p class="margen"></p>
                <div align="left">
                    <a href="<%=request.getContextPath()%>/LoginServlet" class="btn btn-danger mt-3" >Regresar</a>
                    <!--Debe regresar a Login---este aun no disponible -->
                    <button type="submit" class="btn btn-success pull-right mt-3">Confirmar</button>
                </div>

            </form>
        </div>
        <!-- parte muerta-->
        <div class="col-sm-3">
            <%if(!correoNoEx){%>
            <div class="alert alert-danger" role="alert">
                El correo no tiene una cuenta asociada en el sistema
            </div>
            <%}%>
        </div>

        <!-- parte muerta-->
    </div>
</div>


</body>
</html>
