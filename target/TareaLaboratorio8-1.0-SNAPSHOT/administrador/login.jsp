<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Ingreso al sistema</title>
    <jsp:include page="/bootstrapRepository.jsp"/>
    <jsp:include page="/includes/utf8Cod.jsp"/>
    <!-- para los iconos como botones -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link href="resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="resources/css/signin.css" rel="stylesheet">
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

    <h1 class="mb-5 mt-3 ">Ingreso al sistema 'Mi Marca'</h1>
    <div class="row mb-3">

        <div class="col-lg-5 col-md-12 centrar">
            <%//<img src="/imagenes/profile_admin.png" width="408px" height="400px" />%>
            <img src="https://image.flaticon.com/icons/png/512/417/417769.png" width="408px" height="400px" />
        </div>
        <div class="col-lg-7 col-md-12">
            <form class="form-signin" method="POST" action="<%=request.getContextPath()%>/LoginAdmin" style="
    margin-left: 150px;text-align: center;position: relative; top:100px;">
                <div class="row">
                    <div class="col-10">

                        <input type="text" name="inputEmail" class="form-control ingresa" placeholder="Correo" autofocus="">

                    </div>
                    <div class="col-6">

                    </div>

                </div>
                <div class="row mt-3">
                    <div class="col-10">

                        <input type="password" name="inputPassword" class="form-control ingresa" placeholder="Contraseña">

                    </div>


                </div>

                <% if (request.getParameter("error") != null) { %>
                <div class="text-danger mb-2">Error en usuario o contraseña</div>
                <% } %>

                <div class="row">
                    <div class="col-6" style=" position: relative; top: 15px">
                        <button class="btn btn-lg btn-primary btn-block" type="submit">Ingresar</button>
                    </div>

                </div>

            </form>
        </div>


    </div>
    <div class="row">
        <a href ="<%=request.getContextPath()%>/LoginServlet?accion=parteEmpresa" class=btn btn-lg btn-primary btn-block>Regresar</a>
    </div>

</div>


</body>
</html>
