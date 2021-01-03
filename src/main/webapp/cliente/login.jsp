<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%
    int correoEnviado = session.getAttribute("correoEnviado") == null ? 0 : (int) session.getAttribute("correoEnviado");
    Boolean cambioContra = session.getAttribute("cambioContra") == null ? false : (Boolean) session.getAttribute("cambioContra");

    //se tiene que hacer por sesion
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="/includes/utf8Cod.jsp"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>¡Bienvenido!</title>
    <jsp:include page="/bootstrapRepository.jsp"/>
    <!-- para los iconos como botones -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <!--  <link href="/resources/css/bootstrap.min.css" rel="stylesheet"> -->


    <style>
        body {
            height: 100%;
        }

        body {
            display: -ms-flexbox;
            display: -webkit-box;
            display: flex;
            -ms-flex-align: center;
            -ms-flex-pack: center;
            -webkit-box-align: center;
            align-items: center;
            -webkit-box-pack: center;
            justify-content: center;
            padding-top: 40px;
            padding-bottom: 40px;
            background-color: #f5f5f5;
        }

        .form-signin {
            width: 100%;
            max-width: 330px;
            padding: 15px;
            margin: 0 auto;
        }
        .form-signin .checkbox {
            font-weight: 400;
        }
        .form-signin .form-control {
            position: relative;
            box-sizing: border-box;
            height: auto;
            padding: 10px;
            font-size: 16px;
        }
        .form-signin .form-control:focus {
            z-index: 2;
        }
        .form-signin input[type="email"] {
            margin-bottom: -1px;
            border-bottom-right-radius: 0;
            border-bottom-left-radius: 0;
        }
        .form-signin input[type="password"] {
            margin-bottom: 10px;
            border-top-left-radius: 0;
            border-top-right-radius: 0;
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

        .margen {
            margin-top: 2%;
        }

        .container-fluid {
            text-align: center;
            padding: 15px 15%;
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
        .ingresa{
            width: 323px;
        }
        .izquierda{
            position: relative;
            left: 70px;
        }

    </style>
</head>

<body >

<div class="container-fluid">

    <h1 class="mb-5 mt-3 ">¡Bienvenido a 'Mi Marca' !</h1>
    <div class="row mb-3">

        <div class="col-lg-5 col-md-12 centrar">
            <img src="https://www.flaticon.com/svg/static/icons/svg/3135/3135715.svg" width="408px" height="400px" />
        </div>
        <div class="col-lg-7 col-md-12 ">
            <form class="form-signin" method="POST" action="<%=request.getContextPath()%>/LoginServlet?accion=login" style="
    margin-left: 150px;text-align: left;position: relative; top:40px;">
            <div class="row">
                <div class="col-10">
                    <div align="center">
                        <%if(correoEnviado==1){%>
                        <div class="alert alert-success" role="alert">
                            Solicitud enviada correctamente, revise su bandeja de entrada.

                        </div>
                        <%}else if(correoEnviado==2){%>
                        <div class="alert alert-danger" role="alert">
                            Ha ocurrido un error al enviar el correo. Intentelo de nuevo.
                        </div>
                        <%}%>
                        <%if(cambioContra){%>
                        <div class="alert alert-success" role="alert">
                            Contraseña restablecida exitosamente.
                        </div>
                        <%}%>
                        <%session.invalidate(); %>
                    </div>

                    <input type="text" name="inputEmail" class="form-control ingresa" placeholder="Correo" autofocus="">


                </div>
                <div class="col-6">

                </div>

            </div>
                <div class="row mt-3">
                    <div class="col-10">

                        <input type="password" name="inputPassword" class="form-control ingresa" placeholder="Contraseña">

                    </div>
                    <div class="col-2 izquierda">
                        <a href="<%=request.getContextPath()%>/LoginServlet?accion=olvideContra">
                            <button type="button" class="btn btn-primary">Olvidé mi contraseña</button></a>
                    </div>

                </div>

                    <% if (request.getParameter("error") != null) { %>
                <div class="text-danger mb-2">Error en usuario o contraseña</div>
                    <% } %>

                <div class="row">
                    <div class="col-6">
                        <button class="btn btn-lg btn-primary btn-block" type="submit">Ingresar</button>
                    </div>
                    <div class="col-6 " style="text-align: center; position: relative; top: 10px">
                        <a href="<%=request.getContextPath()%>/UsuarioServlet?accion=agregar"
                        ><u>Registrarse</u></a>
                    </div>

                </div>
                <div class="row mt-5">
                    <div class="col">
                        <a href="<%=request.getContextPath()%>/LoginServlet?accion=parteEmpresa">
                            <button type="button" class="btn btn-primary" style="position: relative; top: 80px;left: 160px ">Soy parte de la empresa</button></a>

                    </div>
                </div>

            </form>
        </div>
    </div>


</div>


</body>
</html>
