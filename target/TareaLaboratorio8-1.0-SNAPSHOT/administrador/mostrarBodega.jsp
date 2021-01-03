<jsp:useBean id="bodega" scope="request" type="beans.BodegaBean"/>
<html>
<head>
    <jsp:include page="/bootstrapRepository.jsp"/>
    <jsp:include page="/includes/utf8Cod.jsp"/>
    <title>Ver bodega</title>
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
        .container-fluid {
            text-align:left;
            padding: 15px 15%;
        }



    </style>
</head>
<body>
<header>
    <jsp:include page="/administrador/headerAdmin.jsp"/>
    <jsp:include page="/includes/utf8Cod.jsp"/>
</header>
<div class="container" style="margin-top: 30px">
    <div class="row" style="margin: 30px">
        <div class="col-lg-6 col-md-12">
            <div class="row" >
                <div class="col-lg-6 col-md-12">
                </div>
                <div class="col-lg-12 col-md-12">
                    <h1>Nombre de la bodega: <%=bodega.getNombreBodega()%></h1>
                    <h4>RUC: <%=bodega.getRucBodega()%></h4>
                    <h4>Distrito: <%=bodega.getDistrito().getNombre()%></h4>
                    <h4>Dirección: <%=bodega.getDireccionBodega()%></h4>
                    <h4>Correo: <%=bodega.getCorreoBodega()%></h4>
                </div>
                <div class="col-lg-2 col-md-12">
                </div>
            </div>
        </div>
        <div class="col-lg-6 col-md-12">
            <div class="col-lg-2 col-md-12">
            </div>
            <div>
                <img src="<%=request.getContextPath()%>/ImagenServlet?idBodega=<%=bodega.getIdBodega()%>" width="400px" class="img-thumbnail"/>

            </div>

        </div>
    </div>

    <div>
        <div>
            <a class="btn btn-outline-danger" href="<%=request.getContextPath()%>/AdminServlet?accion=listar">Regresar</a>

        </div>

    </div>
</div>

</body>
</html>
