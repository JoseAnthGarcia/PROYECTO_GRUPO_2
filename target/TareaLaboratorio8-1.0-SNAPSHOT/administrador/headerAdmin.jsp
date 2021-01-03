<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="navbar navbar-dark bg-dark box-shadow">
    <div class="container d-flex justify-content-between">

        <a href="<%=request.getContextPath()%>/AdminServlet?accion=miPerfil" class="navbar-brand d-flex align-items-center">
            <strong>Administración</strong>
        </a>
        <a href="<%=request.getContextPath()%>/AdminServlet?accion=registrar" class="navbar-brand d-flex align-items-center">
            <strong>Registrar bodega</strong>
        </a>
        <a href="<%=request.getContextPath()%>/AdminServlet?accion=listar" class="navbar-brand d-flex align-items-center">
            <strong>Lista de bodegas</strong>
        </a>
        <div class="card">
            <a href="<%=request.getContextPath()%>/LoginAdmin?accion=logout"><img src="https://www.flaticon.com/svg/static/icons/svg/1828/1828479.svg" height="30px"/></a>
        </div>
    </div>
</div>
