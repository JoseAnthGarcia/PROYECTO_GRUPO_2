<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="usuario" scope="request" type="beans.UsuarioBean"  />

<html>
<head>
    <jsp:include page="/bootstrapRepository.jsp"/>
    <jsp:include page="/includes/utf8Cod.jsp"/>
    <!-- para los iconos como botones -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <title>Mi Marca</title>
    <style>
        body{
            background-color: #f4f4f2;
        }
        .container-fluid{
            padding: 7% 15% 7% 15%;
        }
        .btn-perfil {
            background-color: #343a40;
            border: none;
            color: white;
            padding: 12px 16px;
            font-size: 15px;
            cursor: pointer;
            align-items: center;
            border-radius: 1em;
        }

        /* Darker background on mouse-over */
        .btn-perfil:hover {
            background-color: #767676;
        }
        .btn {
            background-color: #393e46;
            border: none;
            color: white;
            padding: 12px 16px;
            font-size: 15px;
            cursor: pointer;
            align-items: center;
        }

        /* Darker background on mouse-over */
        .btn:hover {
            background-color: #767676;
        }
        .opcion{
            text-align: center;
            width: 179px;
            line-height: 2;



        }
        .productos{

        }
    </style>
</head>

<body>
<jsp:include page="/administrador/headerAdmin.jsp"/>
<div class="container-fluid">
    <div class="row">
        <div class="col-lg-8 col-md-12" >
            <img src="https://img.freepik.com/vector-gratis/concepto-tienda-supermercado-gente-supermercado_40816-713.jpg?size=626&ext=jpg" width="80%" >
        </div>
        <div class="col-lg-4 col-md-12" style=" position: relative; top:20px;">
            <div class="row">
                <div class="col-lg-8 col-md-12" style="text-align: center">
                    <img src="https://www.flaticon.com/svg/static/icons/svg/3135/3135789.svg" width="150px" height=150px">
                </div>
                <div class="col-lg-4 col-md-12" style="text-align: center">
                    <button class="btn-perfil" ><%=usuario.getNombre()+" "+usuario.getApellido()%></button>
                </div>
            </div>
        </div>

    </div>
    <br>
    <br>
    <br>

    <div class="row">
        <div class="col-md-6" style="text-align: center" >
            <a class="opcion" href="<%=request.getContextPath()%>/AdminServlet?accion=registrar">
                <button class="btn opcion">Registrar bodegas
                <br>
                <img src="https://image.flaticon.com/icons/png/512/1698/1698572.png" width="50px" height="50px">
            </button></a>
        </div>
        <div class="col-md-6" style="text-align: center">
            <a class="opcion" href="<%=request.getContextPath()%>/AdminServlet?accion=listar"
            ><button class="btn opcion">Listar bodegas
                <br>
                <img src="https://cdn4.iconfinder.com/data/icons/icoflat3/512/clipboard-512.png" width="50px" height="50px">
            </button></a>
        </div>

    </div>
</div>

</body>
</html>
