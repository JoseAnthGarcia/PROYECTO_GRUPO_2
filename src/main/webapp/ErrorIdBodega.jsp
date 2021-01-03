<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Error</title>
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

        </style>
    </head>
    <header>
        <div class="collapse bg-dark" id="navbarHeader">
            <div class="container">

            </div>
        </div>
        <div class="navbar navbar-dark bg-dark box-shadow">
            <div class="container d-flex justify-content-between">
                <a href="#" class="navbar-brand d-flex align-items-center">
                    <strong>Mi Marca.com</strong>
                </a>
            </div>
        </div>
    </header>
    <body>
    <div class="jumbotron jumbotron-fluid">
        <div class="container">
            <h1>Registro inválido</h1>
            <h3>No puedes establecer la contraseña de otra bodega.</h3>

        </div>
    </div>



    </body>
</html>