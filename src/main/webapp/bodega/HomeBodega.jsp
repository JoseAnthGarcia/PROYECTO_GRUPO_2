<%@ page import="beans.BodegaBean" %><%--
  Created by IntelliJ IDEA.
  User: lizbe
  Date: 13/12/2020
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
   BodegaBean bodega = (BodegaBean) request.getAttribute("bodega");
%>
<!doctype html>
<html lang="en">
<head>
    <title>Bodega: <%=bodega.getNombreBodega()%></title>
    <jsp:include page="../bootstrapRepository.jsp"/>
    <jsp:include page="/includes/utf8Cod.jsp"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
        .btn {
            background-color: #d6d2c4;
            border: none;
            color: black;
            padding: 12px 16px;
            font-size: 15px;
            cursor: pointer;
        }
        /* Darker background on mouse-over */
        .btn:hover {
            background-color: #f05454;
        }
        .margen{
            margin-top: 2%;
        }
        .container-fluid{
            text-align: center;
            padding: 3% 15% ;
        }
        .page-item .page-link {
            color: #767676;
            border-color: #767676;
        }
        .page-item.active .page-link {
            border-color: #767676;
            background-color: #767676;
        }
    </style>
</head>
<body>
<header>
    <jsp:include page="includes/headerBodega.jsp" />
</header>

<div class=" Container" style="margin-top: 30px; margin-left: 5%; margin-right: 5%">
    <div class="row">
        <div class="col-lg-6 col-md-12">
            <div class="row" style="margin-top: 60px;">
                <div class="col-lg-2 col-md-12">
                </div>
                <div class="col-lg-8 col-md-12">
                    <img src="<%=request.getContextPath()%>/ImagenServlet?idBodega=<%=bodega.getIdBodega()%>" width="400px" class="img-thumbnail"/>
                </div>
                <div class="col-lg-2 col-md-12">
                </div>
            </div>
        </div>
        <div class="col-lg-6 col-md-12">

            <div class="jumbotron">
                <div class="container">
                    <h1 class="display-3"><%=bodega.getNombreBodega()%> </h1>
                </div>
            </div>


                <table class="table">
                    <tr>
                        <h4>RUC: <%=bodega.getRucBodega()%></h4>
                    </tr>
                    <tr>
                        <h4>Dirección: <%=bodega.getDireccionBodega()%> </h4>
                    </tr>
                    <tr>
                        <h4>Distrito: <%=bodega.getDistrito().getNombre()%> </h4>
                    </tr>
                    <tr>
                        <h4>Correo: <%=bodega.getCorreoBodega()%> </h4>
                    </tr>
                </table>

        </div>
    </div>

</div>


</body>
</html>
