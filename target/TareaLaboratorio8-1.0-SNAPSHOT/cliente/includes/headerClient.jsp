<div class="collapse bg-dark" id="navbarHeader">
    <div class="container">

    </div>
</div>
<div class="navbar navbar-dark bg-dark box-shadow">
    <div class="container d-flex justify-content-between">
        <a href="<%=request.getContextPath()%>/UsuarioServlet?accion=Home" class="navbar-brand d-flex align-items-center">
            <strong>MiMarca.com</strong>
        </a>
        <a href="<%=request.getContextPath()%>/UsuarioServlet?accion=productosDisponibles" class="navbar-brand d-flex align-items-center">
            <strong>Productos disponibles</strong>
        </a>
        <a href="<%=request.getContextPath()%>/UsuarioServlet?accion=escogerBodega1" class="navbar-brand d-flex align-items-center">
            <strong>Realizar un pedido</strong>
        </a>
        <a href="<%=request.getContextPath()%>/UsuarioServlet?accion=listar" class="navbar-brand d-flex align-items-center">
            <strong>Mis pedidos</strong>
        </a>
        <a>
            <div class="card">
                <a href="<%=request.getContextPath()%>/LoginServlet?accion=logout"><img src="https://www.flaticon.com/svg/static/icons/svg/1828/1828479.svg" height="30px"/></a>
            </div>
        </a>

    </div>
</div>