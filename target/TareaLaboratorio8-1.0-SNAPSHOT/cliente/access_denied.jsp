
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/includes/utf8Cod.jsp"/>
    <jsp:include page="/bootstrapRepository.jsp"/>
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
        /* Darker background on mouse-over */
        .btn:hover {
            background-color: #767676;
        }

    </style>
    <title>Página no encontrada</title>
</head>
<body>
<div class="jumbotron jumbotron-fluid">
    <div class="container">
        <h1 class="display-4">Página no encontrada</h1>
        <p class="lead">Inicie sesión para poder acceder al recurso.</p>
    </div>
</div>
<div class="row">
    <a href="<%=request.getContextPath()%>/LoginServlet" class="btn btn-outline-danger primero" style="position: absolute; right: 15%">Iniciar sesión</a>
</div>

</body>

</body>
</html>