<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="listaBodegasDistrito" type="java.util.ArrayList<beans.BodegaBean>" scope="request"/>
<!DOCTYPE html>
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
            padding: 3% 15% ;
        }
        .page-item .page-link {
            color: #343a40;
            border-color: #343a40;
        }
        .page-item.active .page-link {
            border-color: #343a40;
            background-color: #343a40;
        }
        .primero{
            position: absolute;
            left: 15%;
        }

        .tercero{
            position: absolute;
            right: 15%;
        }
    </style>

    <title>Bodegas más cercanas</title>
</head>
<body>

<!-- todo:  corregir el espaciado entre Mi Bodega, Pedidos y Productos -->
<header>
    <jsp:include page="/cliente/includes/headerClient.jsp"/>
</header>
<div class="container" style="margin-top: 20px">
    <div class="row">
        <div class="col-sm-6">
            <h1>Estas son tus bodegas más cercanas:</h1>
        </div>
        <%if(request.getSession().getAttribute("noBodegaCercanaEscogida")!=null){%>
        <div class="col-sm-6">
            <div class="alert alert-danger" role="alert">
                Seleccione alguna bodega.
            </div>
        </div>
        <%request.getSession().removeAttribute("noBodegaCercanaEscogida");
        }%>
        <%if(session.getAttribute("errorRealizarPedido")!=null){%>
        <div class="alert alert-danger" role="alert">
            Ocurrió un problema al intentar realizar el pedido.
        </div>
        <%session.removeAttribute("errorRealizarPedido");
        }%>
    </div>
</div>


<div class="container" style="margin-top: 30px">
    <!-- Presentacion de productos -->
    <form method="post" action="<%=request.getContextPath()%>/UsuarioServlet?accion=escogerBodegaCercana">
        <%if(listaBodegasDistrito.size()!=0){%>
        <%
            int cant = 0;
            for(int i=0; i<(int) Math.ceil((double) listaBodegasDistrito.size() / 4); i++){
        %>
        <div class="row">
            <%
                int min = i*4;
                int max = (i+1)*4;
                for(int j=min; j<max; j++){
                    if(cant < listaBodegasDistrito.size()){
            %>
            <div class="col-sm-3"> <!-- Probar medidas "sm-3"? -->
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="idBodega" id="exampleRadios1" value="<%=listaBodegasDistrito.get(j).getIdBodega()%>">
                    <label class="form-check-label" for="exampleRadios1">
                        Seleccionar
                    </label>
                </div>
                <img src="<%=request.getContextPath()%>/ImagenServlet?idBodega=<%=listaBodegasDistrito.get(j).getIdBodega()%>" width="150px" height="150px"  class="img-thumbnail">
                <p class="mb-1"><b>Bodega: </b><%=listaBodegasDistrito.get(j).getNombreBodega()%> - <%=listaBodegasDistrito.get(j).getDistrito().getNombre()%></p>
                <p class="mb-0"><b>direccion: </b> <%=listaBodegasDistrito.get(j).getDireccionBodega()%> </p>
            </div>
            <% } else{ %>
            <div class="col-sm-3"> <!-- Probar medidas "sm-3"? -->
            </div>
            <%}%>
            <% cant++;
            } %>
        </div>
        <% } %>
        <%}else{%>
        <div class="row">
            <div class="alert alert-secondary" role="alert">
                No hay bodegas cercanas en su distrito.
            </div>
        </div>
        <%}%>

        <div class="row mt-5 mb-5">
            <a href="<%=request.getContextPath()%>/UsuarioServlet?accion=Home" class="btn btn-outline-danger primero">Volver</a>

            <a href="<%=request.getContextPath()%>/UsuarioServlet?accion=escogerBodega2" class="btn btn-outline-success mx-auto">Escoger otra bodega</a>
            <%=listaBodegasDistrito.size()==0?"":"<button type='submit' class='btn btn-outline-success tercero'>Escoger bodega</button>"%>
            <%//<button type="submit" class="btn btn-outline-success">Escoger bodega</button>%>
        </div>
    </form>


</div>





</div>


</body>
</html>