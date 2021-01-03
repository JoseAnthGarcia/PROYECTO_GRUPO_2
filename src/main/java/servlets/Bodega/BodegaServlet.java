package servlets.Bodega;


import beans.*;
import daos.BodegaDao;
import daos.UsuarioDao;
import dtos.PedidosDatosDTO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@MultipartConfig
@WebServlet(name = "BodegaServlet", urlPatterns = {"/BodegaServlet"})
public class BodegaServlet extends HttpServlet {

    public  boolean validarContrasenia(String contrasenia) {
        boolean resultado = true;
        Pattern pattern2 = Pattern
                .compile("(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,}");
        Matcher mather = pattern2.matcher(contrasenia);

        if (mather.find() == false) {
            resultado = false;
        }
        return  resultado;
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        BodegaBean bodegaActual = (BodegaBean) session.getAttribute("bodega");
        int idBodegaActual =  bodegaActual.getIdBodega();

        RequestDispatcher view;

        String accion = request.getParameter("accion") == null ? "listar" : request.getParameter("accion");
        System.out.println("--" + accion + "--");
        switch (accion) {
            case "guardar":

                boolean validStock = true;
                boolean validPrecioUnitario = true;
                boolean validNombreProducto = true;

                String nombreProducto = request.getParameter("nombreProducto");
                String descripcion = request.getParameter("descripcion");

                int cant_caracteres = descripcion.length();
                //System.out.println(cant_caracteres);
                boolean validarDescripcion = true;
                if(cant_caracteres>150){
                    validarDescripcion = false;
                }

                int stock = 0;
                BigDecimal precioUnitario = BigDecimal.valueOf(0);

                Part part = request.getPart("foto");
                boolean fotoVal = true;
                if(part.getSize()==0 || !part.getContentType().contains("image/")){
                    fotoVal = false;
                }

                InputStream inputStream = part.getInputStream();

                // se valida ue el nombre no esté vacio o lleno de espacios
                if (nombreProducto.trim().isEmpty()){
                    validNombreProducto = false;
                }

                // se valida el stock
                try {
                    stock = Integer.parseInt(request.getParameter("stock"));

                    // valida stock negativo
                    if (stock < 0){
                        validStock = false;
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    validStock = false;
                }

                // se valida el precioUnitario
                try {
                    precioUnitario = BigDecimal.valueOf(Double.parseDouble(request.getParameter("precioProducto")));

                    // valida stock negativo
                    if (precioUnitario.compareTo(BigDecimal.valueOf(0)) == -1){
                        validPrecioUnitario = false;
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    validPrecioUnitario = false;
                }

                // si es que los datos son correctos, se guarda el producto
                if (validStock & validPrecioUnitario & validNombreProducto && fotoVal && validarDescripcion) {
                    BodegaDao.crearProducto(nombreProducto, descripcion, stock, precioUnitario, idBodegaActual, inputStream);
                    request.getSession().setAttribute("productoAgregado", true);
                    response.sendRedirect(request.getContextPath() + "/BodegaServlet?accion=listar");
                } else {
                    request.setAttribute("validStock", validStock);
                    request.setAttribute("fotoVal", fotoVal);
                    request.setAttribute("fotoVal", fotoVal);
                    request.setAttribute("validPrecioUnitario", validPrecioUnitario);
                    request.setAttribute("validNombreProducto", validNombreProducto);
                    request.setAttribute("validDescripcion", validarDescripcion);

                    RequestDispatcher dispatcher = request.getRequestDispatcher("/bodega/anadirProducto.jsp");
                    dispatcher.forward(request, response);
                }

                break;

            case "actualizar":

                BodegaDao bodegaDao = new BodegaDao();

                //validar idProductor
                boolean idProductoNumber = true;
                int idProductoInt = -1;
                try {
                    idProductoInt = Integer.parseInt(request.getParameter("idProducto"));
                } catch (NumberFormatException e) {
                    idProductoNumber = false;
                }

                //validar descripcion
                //boolean valdespcr2 = true;
                String descripcion2 = request.getParameter("descripcion");

                //if(descripcion2.trim().equals("")){
                //    valdespcr2 = false;
                //}


                // se valida el stock
                boolean validStock2 = true;
                int stock2 = -1;
                try {
                    stock2 = Integer.parseInt(request.getParameter("stock"));
                    if(stock2 < 0){
                        validStock2 = false;
                    }
                } catch (NumberFormatException e) {
                    validStock2 = false;
                }

                // se valida el precioUnitario
                BigDecimal cero = new BigDecimal(0);

                boolean validPrecioUnitario2 = true;
                BigDecimal precioUnitario2 = BigDecimal.valueOf(-1);
                try {
                    precioUnitario2 = BigDecimal.valueOf(Double.parseDouble(request.getParameter("precioProducto")));
                    if(precioUnitario2.compareTo(cero) == -1){
                        validPrecioUnitario2 = false;
                    }
                } catch (NumberFormatException e) {
                    validPrecioUnitario2 = false;
                }

                Part part1 = request.getPart("foto");

                boolean fotoVal1 = true;
                if(part1.getSize() != 0){
                    if(!part1.getContentType().contains("image/")){
                        fotoVal1 = false;
                    }
                }

                InputStream inputStream1 = part1.getInputStream();

                if(idProductoNumber && bodegaDao.buscarProducto2(idProductoInt, idBodegaActual)!=null){
                    if (validStock2 && validPrecioUnitario2 && fotoVal1) {
                        if(bodegaDao.buscarProducto2(idProductoInt, idBodegaActual)!=null && part1.getSize() == 0){
                            //actualiza
                            bodegaDao.actualizarProducto(idProductoInt, descripcion2, stock2, precioUnitario2);
                            request.getSession().setAttribute("productoActualizado", true);
                            response.sendRedirect(request.getContextPath() + "/BodegaServlet?accion=listar");

                        }else if((bodegaDao.buscarProducto2(idProductoInt, idBodegaActual)!=null) && part1.getContentType().contains("image/")){
                            bodegaDao.actualizarProductoFoto(idProductoInt, descripcion2, stock2, precioUnitario2, inputStream1);
                            request.getSession().setAttribute("productoActualizado", true);
                            response.sendRedirect(request.getContextPath() + "/BodegaServlet?accion=listar");
                        }else{
                            response.sendRedirect(request.getContextPath() + "/BodegaServlet");
                        }
                    } else {
                        request.setAttribute("producto", bodegaDao.buscarProducto2(idProductoInt, idBodegaActual));
                        //request.setAttribute("validDescr", valdespcr2);
                        request.setAttribute("validStock", validStock2);
                        request.setAttribute("validPrecioUnitario", validPrecioUnitario2);
                        if(part1.getSize()!=0){
                            request.setAttribute("esFoto",fotoVal1);
                        }

                        RequestDispatcher dispatcher = request.getRequestDispatcher("/bodega/editarProducto.jsp");
                        dispatcher.forward(request, response);
                    }
                }else{
                    response.sendRedirect(request.getContextPath() + "/BodegaServlet");
                }


                break;

            case "buscar":
                // para la barra de busqueda
                /**
                 * NOTA: Al hacer la primera busqueda simepre se vera en la primera pagina
                 */
                // TODO: extaer la bodega actual
                String textoBuscar = request.getParameter("textoBuscar");

                int cantPag = BodegaDao.calcularCantPag(textoBuscar,idBodegaActual );
                int paginaAct = 1; // primera vista en la pagina 1


                request.setAttribute("cantPag", cantPag);
                request.setAttribute("paginaAct", paginaAct);
                request.setAttribute("productoBusqueda", textoBuscar);

                ArrayList<ProductoBean> listaProductosBuscar = BodegaDao.listarProductoBodega(paginaAct,textoBuscar, idBodegaActual);

                request.setAttribute("listaProductoBodegas", listaProductosBuscar);
                view = request.getRequestDispatcher("/bodega/MiBodegaProductos.jsp");
                view.forward(request, response);
                break;
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        response.addHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.addHeader("Cache-Control", "pre-check=0, post-check=0");
        response.setDateHeader("Expires", 0);


        HttpSession session = request.getSession();
        BodegaBean bodegaActual = (BodegaBean) session.getAttribute("bodega");

        if (bodegaActual != null) {
            int idBodegaActual =  bodegaActual.getIdBodega();


            String accion = request.getParameter("accion") == null ?
                "home" : request.getParameter("accion");


        BodegaDao bodegaDao = new BodegaDao();

        RequestDispatcher view;

        switch (accion) {

            case "home":
                BodegaBean bodega = bodegaDao.obtenerBodega(idBodegaActual);
                request.setAttribute("bodega", bodega);
                view = request.getRequestDispatcher("/bodega/HomeBodega.jsp");
                view.forward(request,response);
                break;

            case "listar":
                // Por defecto se deja en un string vacio, que mostraría todos los productos
                String productoBusqueda = "";

                if (request.getParameter("productoBusqueda") != null && !request.getParameter("productoBusqueda").isEmpty()) {
                    productoBusqueda = request.getParameter("productoBusqueda");
                }

                request.setAttribute("productoBusqueda", productoBusqueda);
                int cantPag = bodegaDao.calcularCantPag(productoBusqueda, idBodegaActual); // TODO: (check)

                String pag = request.getParameter("pag") == null ?
                        "1" : request.getParameter("pag");

                int paginaAct;
                try {
                    paginaAct = Integer.parseInt(pag); //try
                    if (paginaAct > cantPag) {
                        paginaAct = 1;
                    }
                } catch (NumberFormatException e) {
                    paginaAct = 1;
                }


                ArrayList<ProductoBean> listaProductos = bodegaDao.listarProductoBodega(paginaAct, productoBusqueda, idBodegaActual); //TODO: (CHECK) lista los productos de la pagina actual

                request.setAttribute("listaProductoBodegas", listaProductos);
                request.setAttribute("cantPag", cantPag);
                request.setAttribute("paginaAct", paginaAct);
                view = request.getRequestDispatcher("/bodega/MiBodegaProductos.jsp");
                view.forward(request, response);
                break;

            case "agregar":
                view = request.getRequestDispatcher("/bodega/anadirProducto.jsp");
                view.forward(request, response);
                break;

            case "editarProducto":

                boolean idProductoNumber = true;

                int idProductoInt = -1;

                try {
                    idProductoInt = Integer.parseInt(request.getParameter("idProducto"));
                } catch (NumberFormatException e) {
                    idProductoNumber = false;
                }

                if (idProductoNumber) {
                    ProductoBean producto = bodegaDao.buscarProducto2(idProductoInt, idBodegaActual);
                    if (producto == null) {
                        response.sendRedirect(request.getContextPath() + "/BodegaServlet");
                    } else {
                        request.setAttribute("producto", producto);
                        view = request.getRequestDispatcher("/bodega/editarProducto.jsp");
                        view.forward(request, response);

                    }
                } else {
                    response.sendRedirect(request.getContextPath() + "/BodegaServlet");
                }

                break;
            case "eliminar":
                String idProductoString = request.getParameter("idProducto");
                boolean idProductoNumber2 = true;

                int idProductoInt2 = -1;
                try {
                    idProductoInt2 = Integer.parseInt(idProductoString);
                } catch (NumberFormatException e) {
                    idProductoNumber = false;
                }

                if (idProductoNumber2) {
                    if (bodegaDao.buscarProducto(idProductoInt2, idBodegaActual)) {

                        //busco pedidos existentes:
                        ArrayList<PedidoBean> listaPedidos = bodegaDao.buscarPedidoConProducto(idProductoInt2);
                        if (listaPedidos.size() == 0) { //si no existe pedidos:
                            bodegaDao.eliminarProducto(idProductoInt2);
                            request.getSession().setAttribute("productoEliminado", true);
                            response.sendRedirect(request.getContextPath() + "/BodegaServlet?accion=listar");
                        } else {
                            //CORRECCION: flata enviar la lista de productos
                            //request.setAttribute("pedidosConProducto", listaPedidos);
                            request.getSession().setAttribute("pedidosConProducto", listaPedidos);
                            //view = request.getRequestDispatcher("/bodega/MiBodegaProductos.jsp");
                            //view.forward(request, response);
                            response.sendRedirect(request.getContextPath() + "/BodegaServlet?accion=listar");
                        }

                    } else {
                        response.sendRedirect(request.getContextPath() + "/BodegaServlet");
                    }
                } else {
                    response.sendRedirect(request.getContextPath() + "/BodegaServlet");
                }


                break;

            //-------------------------------Listar pedidos---------------------------
            case "listarPedidos":
                String pag1 = request.getParameter("pag") == null ?
                        "1" : request.getParameter("pag");
                int paginaAct1;
                int cantPag1 = bodegaDao.calcularCantPagPedidos(idBodegaActual);
                try{
                    paginaAct1 = Integer.parseInt(pag1);
                    if(paginaAct1>cantPag1){
                        paginaAct1 = 1;
                    }
                }catch(NumberFormatException e){
                    paginaAct1 = 1;
                }

                ArrayList<PedidoBean> listaPedidos = bodegaDao.obtenerListaPedidos(paginaAct1,idBodegaActual);
                request.setAttribute("listaPedidos", listaPedidos);
                request.setAttribute("cantPag", cantPag1);
                request.setAttribute("paginaAct",paginaAct1);
                view = request.getRequestDispatcher("/bodega/listaPedido.jsp");
                view.forward(request,response);
                break;

            case "mostrarPedido":

                String codigoPedido = request.getParameter("codigo");
                UsuarioDao usuarioDao2 = new UsuarioDao();

                if(usuarioDao2.obtenerPedido(codigoPedido)!=null){
                    ArrayList<PedidoHasProductoBean> pedidoProductoLista = usuarioDao2.obtenerDetallesPedido(codigoPedido);
                    request.setAttribute("pedidoProductoLista", pedidoProductoLista);
                    view = request.getRequestDispatcher("bodega/mostrarPedido.jsp");
                    view.forward(request, response);
                }else{
                    response.sendRedirect(request.getContextPath() + "/BodegaServlet?accion=listarPedidos");
                }



                break;
            case "entregarPedido":
                String codigo2 = request.getParameter("codigo");
                if (bodegaDao.obtenerPedidoBodega(codigo2) != null) {
                    bodegaDao.entregarPedido(codigo2);
                    HttpSession session1 = request.getSession();
                    session1.setAttribute("estado", "entregado");
                }
                response.sendRedirect(request.getContextPath() + "/BodegaServlet?accion=listarPedidos");
                break;
            case "cancelarPedido":
                UsuarioDao usuarioDao = new UsuarioDao();
                String codigo3 = request.getParameter("codigo");
                if(usuarioDao.obtenerPedido(codigo3)!=null){
                    if (bodegaDao.obtenerPedidoBodega(codigo3) != null) {
                        HttpSession session1 = request.getSession();

                        boolean valCancelar = bodegaDao.verificarCancelarPedido(codigo3);

                        session1.setAttribute("valCancelar", valCancelar);
                        session1.setAttribute("estado", "cancelado");
                        if(valCancelar){
                            usuarioDao.cancelarPedido(codigo3);
                        }
                        response.sendRedirect(request.getContextPath() + "/BodegaServlet?accion=listarPedidos");
                    }else{
                        response.sendRedirect(request.getContextPath() + "/BodegaServlet?accion=listarPedidos");
                    }
                }else{
                    response.sendRedirect(request.getContextPath() + "/BodegaServlet?accion=listarPedidos");
                }

                break;
        }

    } else {
            RequestDispatcher view2;
        view2 = request.getRequestDispatcher("bodega/access_denied.jsp");
        view2.forward(request, response);
    }

         }
}
