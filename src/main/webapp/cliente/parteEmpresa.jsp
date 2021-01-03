<%--
  Created by IntelliJ IDEA.
  User: Katherine
  Date: 14/12/2020
  Time: 16:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Soy parte de la empresa</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <jsp:include page="/bootstrapRepository.jsp"/>
    <jsp:include page="/includes/utf8Cod.jsp"/>
    <!-- para los iconos como botones -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

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
<body>
<div class="container-fluid">
    <h1 class="mb-5">Soy parte de la empresa</h1>
    <div class="row">
        <div class="col-lg-6 col-md-12">
            <img src="https://www.flaticon.com/svg/static/icons/svg/2082/2082875.svg" width="250px" height="250px">
            <br>

            <a href="<%=request.getContextPath()%>/LoginAdmin" class="mx-auto button btn btn-primary mt-5">Área Administración</a>
        </div>
        <div class="col-lg-6 col-md-12">
            <img src="https://www.flaticon.com/svg/static/icons/svg/869/869636.svg" width="250px" height="250px">
            <br>
            <a href="<%=request.getContextPath()%>/LoginBodega" class="mx-auto button btn btn-primary mt-5">Área Bodega</a>
        </div>
    </div>
    <div class="row">
        <div>
            <a href="<%=request.getContextPath()%>/LoginServlet" class="button btn btn-primary mt-5">Regresar</a>
        </div>

    </div>


</div>

</body>
</html>
