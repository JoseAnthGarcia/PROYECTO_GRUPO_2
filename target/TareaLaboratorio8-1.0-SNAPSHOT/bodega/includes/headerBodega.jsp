<div class="navbar navbar-dark bg-dark box-shadow">
    <div class="container d-flex justify-content-between">
        <a class="navbar-brand d-flex align-items-center">
            <strong>MiMarca.com</strong>
        </a>
        <a href="<%=request.getContextPath()%>/BodegaServlet?accion=home" class="navbar-brand d-flex align-items-center">
            <strong>Mi Bodega</strong>
        </a>
        <a href="<%=request.getContextPath()%>/BodegaServlet?accion=listar" class="navbar-brand d-flex align-items-center">
            <strong>Productos</strong>
        </a>
        <a href="<%=request.getContextPath()%>/BodegaServlet?accion=listarPedidos" class="navbar-brand d-flex align-items-center">
            <strong>Pedidos</strong>
        </a>
        <a>
            <div class="card">
                <a href="<%=request.getContextPath()%>/LoginBodega?accion=logout"><img src="https://www.flaticon.com/svg/static/icons/svg/1828/1828479.svg" height="30px"/></a>
            </div>
        </a>
    </div>
</div>