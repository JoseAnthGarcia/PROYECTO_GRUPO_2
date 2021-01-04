package servlets.Cliente;

import beans.*;
import daos.UsuarioDao;
import dtos.ProductoCantDto;
import dtos.ProductosClienteDTO;
import servlets.Emails;


import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

@WebServlet(name = "UsuarioServlet", urlPatterns = {"/UsuarioServlet"})
public class UsuarioServlet extends HttpServlet {

    public boolean validarDni(String dni) {
        boolean resultado = true;
        try {
            int dni_int = Integer.parseInt(dni);
            if (dni_int < 0 || dni_int > 100000000) {
                resultado = false;
            }
        } catch (NumberFormatException e) {
            resultado = false;
        }
        if (dni.equalsIgnoreCase("")) {
            resultado = false;
        }
        return resultado;
    }

    public boolean validarString(String input) {
        boolean resultado = true;
        boolean resultado2 = true;
        if (input.equalsIgnoreCase("") || input.trim().equalsIgnoreCase("")) {
            resultado = false;

        }
        try {
            int numero = Integer.parseInt(input);
            resultado2 = false;
        } catch (NumberFormatException e) {
            resultado2 = true;
        }
        boolean resultadoFinal = resultado && resultado2;


        return resultadoFinal;
    }

    public boolean validarNumero(String input) {
        boolean resultado = true;
        try {
            int valor = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            resultado = false;
        }
        return resultado;
    }

    public boolean validarCorreo(String input) {
        boolean resultado = true;

        // Patrón para validar el email
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        // El email a validar
        String email = input;

        Matcher mather = pattern.matcher(email);

        if (mather.find() == false) {
            resultado = false;
        }

        return resultado;
    }

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
        RequestDispatcher view2;
        UsuarioBean usuarioBean = (UsuarioBean) session.getAttribute("usuario");
        String accion = request.getParameter("accion") == null ?
                "nada" : request.getParameter("accion");

        UsuarioDao usuarioDao = new UsuarioDao();

        String nombres;
        String apellidos;
        String idDistrito;


        boolean nombresB;
        boolean apellidosB;
        boolean distritoBoolean;


        ArrayList<DistritoBean> listaDistritos = usuarioDao.obtenerDistritos();
        request.setAttribute("listaDistritos", listaDistritos);

        /*int usuarioId = usuarioBean.getIdUsuario();

        UsuarioBean bUsuario = usuarioDao.obtenerUsuario(usuarioId);
        request.setAttribute("usuario", bUsuario);*/
        ArrayList<DistritoBean> listaDistritos2 = usuarioDao.obtenerDistritos();
        request.setAttribute("listaDistritos2", listaDistritos2);

        if (usuarioBean != null && usuarioBean.getIdUsuario() > 0 && !(accion.equals("agregar"))) {
            int usuarioId = usuarioBean.getIdUsuario();

            UsuarioBean bUsuario = usuarioDao.obtenerUsuario(usuarioId);
            request.setAttribute("usuario", bUsuario);

            switch (accion) {
                case "nada":
                    //manda indice
                    break;

                case "actualizar":
                    nombres = request.getParameter("nombres");
                    apellidos = request.getParameter("apellidos");
                    idDistrito = request.getParameter("idDistrito");

                    nombresB = validarString(nombres);
                    apellidosB = validarString(apellidos);
                    distritoBoolean = validarNumero(idDistrito);

                    String cambiar = request.getParameter("cambiar") == null ?
                            "nada" : request.getParameter("cambiar");
                    request.setAttribute("cambiar", cambiar);

                    if (nombresB && apellidosB && distritoBoolean) {

                        int idDistritoInt = Integer.parseInt(idDistrito);

                        boolean distritoSelected = false;
                        if (usuarioDao.buscarDistrito(idDistrito) != null) {
                            distritoSelected = true;
                        }

                        if (distritoSelected && idDistritoInt != 0) {
                            usuarioDao.actualizarUsuario(nombres, apellidos, idDistritoInt, usuarioId, usuarioBean);
                            response.sendRedirect(request.getContextPath() + "/UsuarioServlet?accion=miPerfil");
                        } else {

                            request.setAttribute("distritoSelected", distritoSelected);
                            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/cliente/editarUsuario.jsp");
                            requestDispatcher.forward(request, response);

                        }


                    } else {
                        request.setAttribute("nombresB", nombresB);
                        request.setAttribute("apellidosB", apellidosB);
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/cliente/editarUsuario.jsp");
                        requestDispatcher.forward(request, response);
                    }
                    break;

                case "validarContra":
                    //obtener usuarioID , o antes ya lo tengo para usar
                    //String contraseniaA = request.getParameter("contrasenia");
                    String contrasenia2A = request.getParameter("contrasenia2A");
                    String contraseniaBB = request.getParameter("contraseniaB");
                    String contrasenia2BB = request.getParameter("contrasenia2B");


                    boolean contAntIguales = false;
                    if (usuarioDao.compararContrasenia(bUsuario.getIdUsuario(), contrasenia2A)) {
                        contAntIguales = true;
                    }

                    boolean contIguales = false;
                    if (contraseniaBB.equals(contrasenia2BB)) {
                        contIguales = true;
                    }

                    boolean contraTrim = false;
                    if (contraseniaBB == contraseniaBB.trim() && contrasenia2BB == contrasenia2BB.trim()) {
                        contraTrim = true;
                    }

                    boolean contraSegura = false;
                    if(validarContrasenia(contraseniaBB) && validarContrasenia(contrasenia2BB)){
                        contraSegura = true;
                    }

                    boolean contraRedu = false;
                    if(usuarioDao.compararContrasenia(bUsuario.getIdUsuario(), contraseniaBB)){
                        contraRedu = true;
                    }

                    if (contAntIguales && contIguales && !contrasenia2BB.equals("") && contraTrim && contraSegura && !contraRedu) {
                        //if (contAntIguales && contIguales) {
                        usuarioDao.actualizarContra(usuarioId, contraseniaBB); //ojo con usuarioId
                        request.getSession().setAttribute("contraseniaActualizada", true);
                        response.sendRedirect(request.getContextPath() + "/UsuarioServlet?accion=miPerfil");
                    } else {
                        request.setAttribute("contraseniaB", !contraseniaBB.equals(""));
                        request.setAttribute("contrasenia2B", !contrasenia2BB.equals(""));
                        request.setAttribute("contrasenia2A", !contrasenia2A.equals(""));
                        request.setAttribute("contIguales", contIguales);
                        request.setAttribute("contAntIguales", contAntIguales);
                        request.setAttribute("contraTrim", (contraTrim && !contraseniaBB.equals("") && !contrasenia2BB.equals("")));
                        request.setAttribute("contraSecu1",validarContrasenia(contraseniaBB));
                        request.setAttribute("contraSecu2", validarContrasenia(contrasenia2BB));
                        request.setAttribute("contraRedu",contraRedu);

                        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/clien" +
                                "te/cambioContrasenia.jsp");
                        requestDispatcher.forward(request, response);
                    }
                    break;
                case "buscar":
                    int idBodega = ((BodegaBean) request.getSession().getAttribute("bodegaEscogida")).getIdBodega();

                    String textoBuscar = request.getParameter("textoBuscar");

                    request.setAttribute("listaProductos", usuarioDao.buscarProducto(idBodega, textoBuscar));
                    request.setAttribute("busqueda", textoBuscar);
                    RequestDispatcher view = request.getRequestDispatcher("cliente/realizarUnPedido.jsp");
                    view.forward(request, response);
                    break;
                case "escogerBodegaCercana":
                    HttpSession session6 = request.getSession();
                    if (request.getParameter("idBodega") != null) {
                        String idBodega3 = request.getParameter("idBodega");
                        int idBodegaInt2;
                        try {
                            idBodegaInt2 = Integer.parseInt(idBodega3);
                        } catch (NumberFormatException e) {
                            idBodegaInt2 = -1;
                        }

                        if (idBodegaInt2 != -1 && usuarioDao.obtenerBodega(idBodegaInt2) != null) {
                            if (session6.getAttribute("bodegaEscogida") != null) {
                                session6.removeAttribute("carrito");
                                session6.removeAttribute("bodegaEscogida");
                            }
                            session6.setAttribute("bodegaEscogida", usuarioDao.obtenerBodega(idBodegaInt2));
                            response.sendRedirect(request.getContextPath() + "/UsuarioServlet?accion=realizarPedido");
                        } else {
                            response.sendRedirect(request.getContextPath() + "/UsuarioServlet?accion=manipulacionUsuario");
                        }
                    } else {
                        session6.setAttribute("noBodegaCercanaEscogida", true);
                        response.sendRedirect(request.getContextPath() + "/UsuarioServlet?accion=escogerBodega1");
                    }

                    break;
                case "escogerBodega":
                    HttpSession session3 = request.getSession();
                    if (request.getParameter("idBodega") != null) {
                        String idBodega2 = request.getParameter("idBodega");
                        int idBodegaInt;
                        try {
                            idBodegaInt = Integer.parseInt(idBodega2);
                        } catch (NumberFormatException e) {
                            idBodegaInt = -1;
                        }

                        if (idBodegaInt != -1 && usuarioDao.obtenerBodega(idBodegaInt) != null) {
                            if (session3.getAttribute("bodegaEscogida") != null) {
                                session3.removeAttribute("carrito");
                                session3.removeAttribute("bodegaEscogida");
                            }
                            session3.setAttribute("bodegaEscogida", usuarioDao.obtenerBodega(idBodegaInt));
                            response.sendRedirect(request.getContextPath() + "/UsuarioServlet?accion=realizarPedido");
                        } else {
                            response.sendRedirect(request.getContextPath() + "/UsuarioServlet?accion=manipulacionUsuario");
                        }
                    } else {
                        session3.setAttribute("noBodegaEscogida", true);
                        response.sendRedirect(request.getContextPath() + "/UsuarioServlet?accion=escogerBodega2");
                    }
                    break;

                case "generarPedido":
                    HttpSession session2 = request.getSession();
                    HashMap<Integer, ProductoBean> listaProductos = (HashMap<Integer, ProductoBean>) session2.getAttribute("carrito");
                    ArrayList<ProductoCantDto> listaProductosSelecCant = new ArrayList<>();

                    boolean errorNumber = false;
                    boolean errorGeneral = false;
                    for (Map.Entry<Integer, ProductoBean> entry : listaProductos.entrySet()) {
                        int idProducto = entry.getKey();
                        //Obtengo el producto:
                        ProductoBean producto = usuarioDao.obtenerProducto(idProducto);

                        if(request.getParameter(String.valueOf(idProducto))==null){
                            errorGeneral = true;
                            break;
                        }else{
                            String cant = request.getParameter(String.valueOf(idProducto));

                            try {
                                int cantInt = Integer.parseInt(cant);
                                if (cantInt < 1 || cantInt > producto.getStock()) {
                                    errorNumber = true;
                                    break;
                                }
                            } catch (NumberFormatException e) {
                                if (cant.equals("")) {
                                    errorNumber = true;
                                } else {
                                    errorGeneral = true;
                                }
                                break;
                            }

                            ProductoCantDto productoCant = new ProductoCantDto();
                            productoCant.setProducto(usuarioDao.obtenerProducto(idProducto));
                            productoCant.setCant(Integer.parseInt(cant));

                            listaProductosSelecCant.add(productoCant);
                        }
                    }

                    if (errorNumber || errorGeneral) {
                        if (errorNumber) {
                            //response.sendRedirect(request.getContextPath()+"/UsuarioServlet?accion=verCarrito");
                            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/cliente/carrito.jsp");
                            requestDispatcher.forward(request, response);
                            } else if (errorGeneral) {
                            session2.removeAttribute("bodegaEscogida");
                            session2.removeAttribute("listaProductosSelecCant");
                            session2.removeAttribute("codigoPedido");
                            session2.removeAttribute("precioTotal");
                            session2.removeAttribute("carrito");
                            response.sendRedirect(request.getContextPath() + "/UsuarioServlet");
                        }
                    } else {
                        //no es necesario guardarlo en sesion, ya que se mandara como formulario al
                        //confirmar pedido
                        String codigoPedido = usuarioDao.generarCodigoPedido();
                        request.setAttribute("codigoPedido", codigoPedido);
                        if (session.getAttribute("codigoPedido") != null) {
                            session.removeAttribute("codigoPedido");
                        }
                        session.setAttribute("codigoPedido", codigoPedido);

                        //creo que se debe guardar en la sesion, pues luego se necesitara
                        if (session.getAttribute("listaProductosSelecCant") != null) {
                            session.removeAttribute("listaProductosSelecCant");
                        }
                        session.setAttribute("listaProductosSelecCant", listaProductosSelecCant);

                        //calculamos el precio total:
                        BigDecimal precioTotal = new BigDecimal("0");
                        for (ProductoCantDto producto : listaProductosSelecCant) {
                            BigDecimal cantProducto = new BigDecimal(producto.getCant());
                            BigDecimal costo = producto.getProducto().getPrecioProducto().multiply(cantProducto);
                            precioTotal = precioTotal.add(costo);
                        }
                        request.setAttribute("precioTotal", precioTotal);
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher("cliente/confirmarPedido.jsp");
                        requestDispatcher.forward(request, response);
                    }
                    break;
                case "confirmarPedido":
                    HttpSession session1 = request.getSession();
                    String codigo = request.getParameter("codigo");
                    BodegaBean bodegaEscogida2 = (BodegaBean) session1.getAttribute("bodegaEscogida");
                    BodegaBean bodega = usuarioDao.obtenerBodega(bodegaEscogida2.getIdBodega());
                    if(bodega.getEstadoBodega().equals("Bloqueado")){
                        request.getSession().setAttribute("bodegaBoqueada", true);
                        response.sendRedirect(request.getContextPath() + "/UsuarioServlet?accion=escogerBodega1");
                    }else {
                        if (codigo.equals(session1.getAttribute("codigoPedido"))) {

                            ArrayList<ProductoCantDto> listaProductosSelecCant1 = (ArrayList<ProductoCantDto>) session.getAttribute("listaProductosSelecCant");
                            //calculamos el precio total:
                            boolean errorStock = false;
                            BigDecimal precioTotal = new BigDecimal("0");
                            for (ProductoCantDto producto : listaProductosSelecCant1) {
                                if (producto.getCant() > usuarioDao.obtenerProducto(producto.getProducto().getId()).getStock()) {
                                    errorStock = true;
                                    break;
                                }
                                BigDecimal cantProducto = new BigDecimal(producto.getCant());
                                BigDecimal costo = producto.getProducto().getPrecioProducto().multiply(cantProducto);
                                precioTotal = precioTotal.add(costo);
                            }

                            if (!errorStock) {
                                //validamos fecha
                                String fecha = request.getParameter("fecha");
                                System.out.println(fecha);
                                if (!fecha.equals("")) {
                                    int pos = 10;
                                    String nuevaFecha = fecha.substring(0, pos) + ' ' + fecha.substring(pos + 1);
                                    Date objDate = new Date(); // Sistema actual La fecha y la hora se asignan a objDate

                                    System.out.println("obj: " + objDate);//Hora y fecha actual
                                    String strDateFormat = "yyyy-MM-dd HH:mm";
                                    SimpleDateFormat dateFormat = new SimpleDateFormat(strDateFormat);
                                    String now = dateFormat.format(objDate);
                                    System.out.println("Ahora: " + now);
                                    System.out.println("Fecha con espacio: " + nuevaFecha);
                                    //System.out.println(dateFormat.format(objDate));
                                    //SimpleDateFormat objSDF = new SimpleDateFormat("dd-mm-yyyy  HH: mm: ss ");
                                    int comparacion = 10;
                                    try {
                                        Date fechaParse = dateFormat.parse(nuevaFecha);
                                        Date ahora = dateFormat.parse(now);
                                        comparacion = fechaParse.compareTo(ahora);
                                        System.out.println(comparacion);

                                    } catch (ParseException ex) {
                                        System.out.println("no entro");
                                    }

                                    //creo el pedido:
                                    if (comparacion == 1 || comparacion == 0 || fecha.equals("")) {
                                        PedidoBean pedido = new PedidoBean();
                                        pedido.setCodigo(codigo);
                                        pedido.setFecha_recojo(fecha);
                                        pedido.setTotalApagar(precioTotal);
                                        BodegaBean bodegaEscogida = (BodegaBean) session1.getAttribute("bodegaEscogida");
                                        pedido.setBodegaBean(bodegaEscogida);
                                        UsuarioBean usuario = (UsuarioBean) session1.getAttribute("usuario");
                                        pedido.setUsuario(usuario);
                                        int idPedido = usuarioDao.crearPedido(pedido);

                                        //relleno el pedido:
                                        usuarioDao.ingresarProductosApedido(idPedido, listaProductosSelecCant1);

                                        //Se elimina los atributos:
                                        session1.removeAttribute("bodegaEscogida");
                                        session1.removeAttribute("listaProductosSelecCant");
                                        session1.removeAttribute("codigoPedido");
                                        session1.removeAttribute("precioTotal");
                                        session1.removeAttribute("carrito");

                                        request.setAttribute("codigo", codigo);
                                        RequestDispatcher requestDispatcher2 = request.getRequestDispatcher("cliente/registroExitoso.jsp");
                                        requestDispatcher2.forward(request, response);
                                    } else {
                                        boolean errorFecha = true;
                                        request.setAttribute("errorFecha", errorFecha);

                                        request.setAttribute("codigoPedido", codigo);
                                        request.setAttribute("precioTotal", precioTotal);
                                        RequestDispatcher requestDispatcher = request.getRequestDispatcher("cliente/confirmarPedido.jsp");
                                        requestDispatcher.forward(request, response);
                                    }
                                } else {
                                    request.setAttribute("errorFecha", true);

                                    request.setAttribute("codigoPedido", codigo);
                                    request.setAttribute("precioTotal", precioTotal);
                                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("cliente/confirmarPedido.jsp");
                                    requestDispatcher.forward(request, response);
                                }
                            } else {
                                request.getSession().setAttribute("errorStock", true);
                                response.sendRedirect(request.getContextPath() + "/UsuarioServlet?accion=realizarPedido");
                            }
                        } else {
                            //error malintensionado
                            view2 = request.getRequestDispatcher("cliente/default.jsp");
                            view2.forward(request, response);
                        }
                    }

                    break;

            }
        } else if (usuarioBean == null && accion.equals("agregar")) {
            nombres = request.getParameter("nombres");
            apellidos = request.getParameter("apellidos");
            idDistrito = request.getParameter("idDistrito");

            nombresB = validarString(nombres);
            apellidosB = validarString(apellidos);
            distritoBoolean = validarNumero(idDistrito);

            String dni = request.getParameter("dni");
            String correo = request.getParameter("correo");
            String contrasenia = request.getParameter("contrasenia");
            String contrasenia2 = request.getParameter("contrasenia2");


            boolean dniB = validarDni(dni);
            boolean correoB = validarCorreo(correo);
            boolean contraseniaB = validarString(contrasenia) && validarContrasenia(contrasenia);
            boolean contrasenia2B = validarString(contrasenia2) && validarContrasenia(contrasenia2);

            if (nombresB && apellidosB && dniB && correoB && contraseniaB
                    && contrasenia2B && distritoBoolean) {

                int idDistritoInt = Integer.parseInt(idDistrito);

                boolean distritoSelected = false;
                if (usuarioDao.buscarDistrito(idDistrito) != null) {
                    distritoSelected = true;
                }

                boolean contIguales = false;
                if (contrasenia.equals(contrasenia2)) {
                    contIguales = true;
                }

                boolean contraTrim = false;
                if (contrasenia == contrasenia.trim() && contrasenia2 == contrasenia2.trim()) {
                    contraTrim = true;
                }

                boolean correoExis = false;
                if (usuarioDao.buscarCorreo(correo)) {
                    correoExis = true;
                }

                boolean dniExis = false;
                if (usuarioDao.buscarDni(dni)) {
                    dniExis = true;
                }

                if (distritoSelected && contIguales && !correoExis && !dniExis && idDistritoInt != 0 && contraTrim) {
                    usuarioDao.regitrarNuevoUsuario(nombres, apellidos, dni, correo, contrasenia, idDistritoInt);
                    //ENVIAR CORREO
                    Emails emails = new Emails();
                    int puerto = request.getLocalPort();
                    String correoAenviar = correo;
                    String asunto = "BIENVENIDO A *MI MARCA* !!!!";
                    String contenido = "Hola " + nombres + ", te has registrado exitosamente en 'MI MARCA'.Para " +
                            "poder empezar a realizar pedidos, ingresa al link :\n" +
                            "http://localhost:"+puerto+request.getContextPath()+"/LoginServlet"+
                    "\n" +
                            "Atentamente,\n" +
                            "El equipo de MiMarca.com ";
                    try {
                        emails.enviarCorreo(correoAenviar, asunto, contenido);
                    } catch (MessagingException e) {
                        System.out.println("Se capturo excepcion en envio de correo Usuario");
                    }

                    request.setAttribute("correo", correo);
                    request.setAttribute("nombres", nombres);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("cliente/envioCorreo.jsp");
                    requestDispatcher.forward(request, response);
                    //response.sendRedirect(request.getContextPath()+"/UsuarioServlet");
                } else {
                    request.setAttribute("contIguales", contIguales);
                    //agragar mas validaciones?
                    request.setAttribute("contraTrim", (contraTrim && !contrasenia.equals("") && !contrasenia2.equals("")));
                    request.setAttribute("correoExis", correoExis);
                    request.setAttribute("dniExis", dniExis);
                    request.setAttribute("distritoSelected", distritoSelected);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("cliente/registroNuevoUsuario.jsp");
                    requestDispatcher.forward(request, response);

                }


            } else {
                request.setAttribute("nombresB", nombresB);
                request.setAttribute("apellidosB", apellidosB);
                request.setAttribute("dniB", dniB);
                request.setAttribute("correoB", correoB);
                request.setAttribute("contraseniaB", contraseniaB);
                request.setAttribute("contrasenia2B", contrasenia2B);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("cliente/registroNuevoUsuario.jsp");
                requestDispatcher.forward(request, response);
            }

        } else {
            view2 = request.getRequestDispatcher("cliente/access_denied.jsp");
            view2.forward(request, response);
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
        RequestDispatcher view2;
        UsuarioBean clienteActual = (UsuarioBean) session.getAttribute("usuario");
        String accion = request.getParameter("accion") == null ?
                "Home" : request.getParameter("accion");

        UsuarioDao usuarioDao = new UsuarioDao();
        if (clienteActual != null && clienteActual.getIdUsuario() > 0
                && !(accion.equals("agregar"))) {



            RequestDispatcher requestDispatcher;

            int usuarioActualId = clienteActual.getIdUsuario();

            switch (accion) {

                case "miPerfil":


                    UsuarioBean usuario = usuarioDao.obtenerUsuario(usuarioActualId);

                    request.setAttribute("usuario", usuario);

                    requestDispatcher = request.getRequestDispatcher("/cliente/miPerfil.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                case "editar":
                    String cambiar = request.getParameter("cambiar") == null ?
                            "nada" : request.getParameter("cambiar");
                    ArrayList<DistritoBean> listaDistritos2 = usuarioDao.obtenerDistritos();
                    request.setAttribute("listaDistritos2", listaDistritos2);

                    UsuarioBean bUsuario = usuarioDao.obtenerUsuario(usuarioActualId);

                    request.setAttribute("cambiar", cambiar);
                    request.setAttribute("usuario", clienteActual);
                    requestDispatcher = request.getRequestDispatcher("/cliente/editarUsuario.jsp");
                    requestDispatcher.forward(request, response);

                    break;

                case "cambioContra":
                    requestDispatcher = request.getRequestDispatcher("/cliente/cambioContrasenia.jsp");
                    requestDispatcher.forward(request, response);
                    break;

                case "escogerBodega1":
                    HttpSession session5 = request.getSession();
                    ArrayList<BodegaBean> listaBodegas2 = usuarioDao.listarBodegasDistrito(((UsuarioBean) session5.getAttribute("usuario")).getIdUsuario());
                    request.setAttribute("listaBodegasDistrito", listaBodegas2);
                    requestDispatcher = request.getRequestDispatcher("/cliente/bodegasCercanas.jsp");
                    requestDispatcher.forward(request, response);
                    break;

                case "escogerBodega2":
                    String pag = request.getParameter("pag") == null ? "1" : request.getParameter("pag");

                    int cantPags = usuarioDao.calcularCantPagListarBodegas();
                    int pagInt = -1;
                    try{
                        pagInt = Integer.parseInt(pag);
                        if(pagInt>cantPags){
                            pagInt = 1;
                        }
                    }catch (NumberFormatException e){
                        pagInt = 1;
                    }


                    ArrayList<BodegaBean> listaBodegas = usuarioDao.listarBodegas(pagInt);
                    request.setAttribute("listaBodegas", listaBodegas);
                    request.setAttribute("paginaAct", pagInt);
                    request.setAttribute("cantPag", cantPags);
                    requestDispatcher = request.getRequestDispatcher("/cliente/bodegasDisponibles.jsp");
                    requestDispatcher.forward(request, response);

                    break;
                case "realizarPedido":
                    HttpSession session1 = request.getSession();
                    if (session1.getAttribute("bodegaEscogida") != null) {
                        if (session1.getAttribute("bodegaEscogida") != null) {

                        }
                        int idBodega = ((BodegaBean) session1.getAttribute("bodegaEscogida")).getIdBodega();
                        int cantPorPagina = 4;
                        //calculamos paginas:
                        String query = "SELECT * FROM producto WHERE stock <> 0 and estado <> 'Eliminado' and idBodega=" + idBodega + ";";
                        int cantPag = usuarioDao.calcularCantPagQuery(query, cantPorPagina);

                        String pag2 = request.getParameter("pag") == null ?
                                "1" : request.getParameter("pag");
                        int paginaAct;
                        try {
                            paginaAct = Integer.parseInt(pag2); //try
                            if (paginaAct > cantPag) {
                                paginaAct = 1;
                            }
                        } catch (NumberFormatException e) {
                            paginaAct = 1;
                        }

                        ArrayList<ProductoBean> listaProductos = usuarioDao.listarProductosBodega(idBodega, paginaAct, cantPorPagina);

                        request.setAttribute("listaProductos", listaProductos);
                        request.setAttribute("cantPag", cantPag);
                        request.setAttribute("paginaAct", paginaAct);

                        requestDispatcher = request.getRequestDispatcher("/cliente/realizarUnPedido.jsp");
                        requestDispatcher.forward(request, response);
                    } else {
                        response.sendRedirect(request.getContextPath() + "/UsuarioServlet?accion=escogerBodega1");
                    }
                    break;

                case "eliminarBodegaEscogida":
                    if (request.getSession().getAttribute("bodegaEscogida") != null) {
                        request.getSession().removeAttribute("bodegaEscogida");
                        if (request.getSession().getAttribute("carrito") != null) {
                            request.getSession().removeAttribute("carrito");
                        }
                        response.sendRedirect(request.getContextPath() + "/UsuarioServlet?accion=escogerBodega1");
                    } else {
                        response.sendRedirect(request.getContextPath() + "/UsuarioServlet?accion=Home");
                    }
                    break;

                case "agregarCarrito":
                    if (session.getAttribute("bodegaEscogida") != null) {
                        boolean noNumber = false;
                        int idProducto = -1;
                        String producto = request.getParameter("productSelect");
                        try {
                            idProducto = Integer.parseInt(request.getParameter("productSelect"));
                        } catch (NumberFormatException e) {
                            noNumber = true;
                        }

                        if (!noNumber && usuarioDao.verificarProductoBodega(idProducto, ((BodegaBean) session.getAttribute("bodegaEscogida")).getIdBodega())) {
                            HttpSession session2 = request.getSession();
                            if (session2.getAttribute("carrito") == null) {
                                HashMap<Integer, ProductoBean> carrito2 = new HashMap<>();
                                session2.setAttribute("carrito", carrito2);
                            }
                            HashMap<Integer, ProductoBean> carrito2 = (HashMap<Integer, ProductoBean>) session2.getAttribute("carrito");
                            ProductoBean producto1 = usuarioDao.obtenerProducto(idProducto);
                            if (carrito2.containsKey(producto1.getId())) {
                                session2.setAttribute("productoExistente", true);

                            } else {
                                session2.setAttribute("productoAgregado", true);
                                carrito2.put(producto1.getId(), producto1);
                                session2.removeAttribute("carrito");
                                session2.setAttribute("carrito", carrito2);
                            }
                            response.sendRedirect(request.getContextPath() + "/UsuarioServlet?accion=realizarPedido");
                        } else {
                            request.getSession().setAttribute("errorAgregarCarrito", true);
                            response.sendRedirect(request.getContextPath() + "/UsuarioServlet?accion=realizarPedido");
                        }
                    } else {
                        response.sendRedirect(request.getContextPath() + "/UsuarioServlet?accion=escogerBodega1");
                    }
                    break;
                case "verCarrito":
                    HttpSession session3 = request.getSession();
                    if (session3.getAttribute("carrito") == null) {
                        HashMap<Integer, ProductoBean> carrito = new HashMap<>();
                        session3.setAttribute("carrito", carrito);
                    }
                    requestDispatcher = request.getRequestDispatcher("/cliente/carrito.jsp");
                    requestDispatcher.forward(request, response);
                    break;

                case "eliminarProductCarrito":
                    int idProducto1 = Integer.parseInt(request.getParameter("productSelect"));
                    HttpSession session4 = request.getSession();
                    ((HashMap<Integer, ProductoBean>) session4.getAttribute("carrito")).remove(idProducto1);
                    response.sendRedirect(request.getContextPath() + "/UsuarioServlet?accion=verCarrito");
                    break;


                case "listar":


                    String pag1 = request.getParameter("pag") == null ?
                            "1" : request.getParameter("pag");


                    int cantPag1 = usuarioDao.calcularCantPagPedidos(usuarioActualId);
                    int paginaAct1 = -1;
                    try {

                        paginaAct1 = Integer.parseInt(pag1); //try
                        if (paginaAct1 > cantPag1) {
                            paginaAct1 = 1;
                        }
                    } catch (NumberFormatException e) {
                        paginaAct1 = 1;
                    }

                    ArrayList<PedidoBean> listaPedidos = usuarioDao.listarPedidosCliente(paginaAct1, usuarioActualId);
                    request.setAttribute("listaPedidos", listaPedidos);
                    request.setAttribute("cantPag", cantPag1);
                    request.setAttribute("paginaAct", paginaAct1);
                    //usuarioDao.actualizarTotalApagar();
                    RequestDispatcher view = request.getRequestDispatcher("/cliente/listarPedidosUsuario.jsp");
                    view.forward(request, response);
                    break;

                case "verDetallesPedido":

                    String codigoPedido = request.getParameter("codigoPedido");

                    if(usuarioDao.obtenerPedido(codigoPedido)!=null){
                        ArrayList<PedidoHasProductoBean> pedidoProductoLista = usuarioDao.obtenerDetallesPedido(codigoPedido);
                        request.setAttribute("pedidoProductoLista", pedidoProductoLista);
                        requestDispatcher = request.getRequestDispatcher("cliente/detallesPedido.jsp");
                        requestDispatcher.forward(request, response);
                    }else{
                        response.sendRedirect(request.getContextPath() + "/UsuarioServlet?accion=listar");
                    }

                    break;

                case "cancelarPedido": //cancelarPedido

                    String codigoPedido2 = request.getParameter("codigoPedido");
                    PedidoBean pedidoBean = usuarioDao.obtenerPedido(codigoPedido2);
                    if(pedidoBean !=null && pedidoBean.getEstado().equals("Pendiente") &&
                            usuarioDao.verificarPedidoUsuario(codigoPedido2,usuarioActualId)){
                        boolean aTiempo =  usuarioDao.verificarHoraPedido(codigoPedido2);
                        if(aTiempo){
                            usuarioDao.cancelarPedido(codigoPedido2);
                            request.getSession().setAttribute("pedidoCancelado", true);
                            response.sendRedirect(request.getContextPath() + "/UsuarioServlet?accion=listar");
                        }else{
                            request.getSession().setAttribute("errorCancelarPedido", true);
                            response.sendRedirect(request.getContextPath() + "/UsuarioServlet?accion=listar");
                        }
                    }else{
                        response.sendRedirect(request.getContextPath() + "/UsuarioServlet?accion=listar");
                    }

                    break;
                case "Home":
                    request.setAttribute("usuario", clienteActual);
                    requestDispatcher = request.getRequestDispatcher("cliente/Home.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                case "productosDisponibles":
                    ProductoBean productoBean = new ProductoBean();
                    String pag2 = request.getParameter("pag2") == null ? "1" : request.getParameter("pag2");
                    int pag2Int;

                    int cantPags2 = usuarioDao.calcularCantPagListarProductos();
                    try{
                         pag2Int = Integer.parseInt(pag2);
                        if(pag2Int>cantPags2 || pag2Int==0){
                            pag2Int=1;
                        }

                    }catch (NumberFormatException e){
                        pag2Int=1;
                    }

                    ArrayList<ProductosClienteDTO> listaProductos = usuarioDao.listarProductos(pag2Int);
                    request.setAttribute("listaProductos", listaProductos);
                    request.setAttribute("paginaAct", pag2Int);
                    request.setAttribute("cantPag", cantPags2);
                    requestDispatcher = request.getRequestDispatcher("/cliente/listarProductos.jsp");
                    requestDispatcher.forward(request, response);


                    break;
                case "detalleProducto":
                    if (session.getAttribute("bodegaEscogida") != null) {
                        boolean noNumber = false;
                        int idProducto = -1;

                        try {
                            idProducto = Integer.parseInt(request.getParameter("productSelect"));
                        } catch (NumberFormatException e) {
                            noNumber = true;
                        }

                        if (!noNumber && usuarioDao.verificarProductoBodega(idProducto, ((BodegaBean) session.getAttribute("bodegaEscogida")).getIdBodega())) {
                            ProductoBean productoBean1 = usuarioDao.obtenerProducto(idProducto);
                            request.setAttribute("vista","realizarPedido");
                            request.setAttribute("producto",productoBean1);
                            requestDispatcher = request.getRequestDispatcher("/cliente/detalleProducto.jsp");

                            requestDispatcher.forward(request, response);

                        } else {
                            request.getSession().setAttribute("errorVerDetalles1", true);
                            response.sendRedirect(request.getContextPath() + "/UsuarioServlet?accion=realizarPedido");
                        }
                    } else {
                        response.sendRedirect(request.getContextPath() + "/UsuarioServlet?accion=escogerBodega1");
                    }

                    break;
                case "detalleProducto2":
                    int idProducto = -1;
                    boolean noNumber = false;
                    try {
                        idProducto = Integer.parseInt(request.getParameter("productSelect"));
                    } catch (NumberFormatException e) {
                        noNumber = true;
                    }
                    if (!noNumber) {
                        ProductoBean productoBean1 = usuarioDao.obtenerProducto(idProducto);
                        if(productoBean1.getEstado().equals("Existente")){
                            request.setAttribute("vista","productosDisponibles");
                            request.setAttribute("producto",productoBean1);
                            requestDispatcher = request.getRequestDispatcher("/cliente/detalleProducto.jsp");
                            requestDispatcher.forward(request, response);
                        }else{
                            response.sendRedirect(request.getContextPath() + "/UsuarioServlet?accion=productosDisponibles");
                        }

                    } else {
                        //accion malintensionado
                        requestDispatcher = request.getRequestDispatcher("/cliente/default.jsp");
                        requestDispatcher.forward(request, response);
                    }
                    break;

                default:
                    requestDispatcher = request.getRequestDispatcher("/cliente/default.jsp");
                    requestDispatcher.forward(request, response);


            }
        } else if (clienteActual == null && accion.equals("agregar")) {
            ArrayList<DistritoBean> listaDistritos = usuarioDao.obtenerDistritos();
            request.setAttribute("listaDistritos", listaDistritos);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("cliente/registroNuevoUsuario.jsp");
            requestDispatcher.forward(request, response);
        } else {
            view2 = request.getRequestDispatcher("cliente/access_denied.jsp");
            view2.forward(request, response);
        }
    }
}
