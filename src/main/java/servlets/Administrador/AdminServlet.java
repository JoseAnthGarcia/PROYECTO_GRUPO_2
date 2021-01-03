package servlets.Administrador;

import beans.BodegaBean;
import beans.DistritoBean;
import beans.PedidoBean;
import beans.UsuarioBean;
import daos.AdminDao;
import daos.BodegaDao;
import servlets.Emails;

import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@MultipartConfig
@WebServlet(name = "AdminServlet",urlPatterns = {"/AdminServlet"} )
public class AdminServlet extends HttpServlet {

    public static boolean isRUCValid(long ruc){
        return isRUCValid(String.valueOf(ruc));
    }

    public static boolean isRUCValid(String ruc){
        if (ruc == null) {
            return false;
        }
        final int[] multipliers = {5,4,3,2,7,6,5,4,3,2};
        final String[] prefixes = getRucPrefixes();
        final int length = multipliers.length + 1;

        if(ruc.length() != length){
            return false;
        }

        boolean isPrefixOk = false;

        for (String prefix : prefixes){
            if(ruc.substring(0,2).equals(prefix)){
                isPrefixOk = true;
                break;
            }
        }

        if(!isPrefixOk){
            return false;
        }

        int sum = 0;

        for(int i = 0; i < multipliers.length; i++){
            final char section = ruc.charAt(i);

            if(!Character.isDigit(section)){
                return false;
            }

            sum += Character.getNumericValue(ruc.charAt(i)) * multipliers[i];
        }

        final int rest = sum % length;
        final String response = String.valueOf(length - rest);

        return response.charAt(response.length() - 1) == ruc.charAt(ruc.length() - 1);
    }

    public static String[] getRucPrefixes(){
        return new String[]{"10", "15", "17", "20"};
    }

    public boolean validarString(String input){
        boolean resultado = true;
        boolean resultado2= true;
        boolean resultado3=true;
        if(input.equalsIgnoreCase("")){
            resultado = false;
        }
        if(input!=input.trim()){
            resultado3=false;
        }
        try{
            int numero= Integer.parseInt(input);
            resultado2=false;
        }catch (NumberFormatException e){
            resultado2=true;
        }
        boolean resultadoFinal= resultado&&resultado2&&resultado3;


        return resultadoFinal;
    }
    public boolean validarCorreo(String input){
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

    public boolean validarNumero(String input){
        boolean resultado = true;
        try{
            int valor = Integer.parseInt(input);
        }catch (NumberFormatException e){
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
        UsuarioBean adminActual = (UsuarioBean) session.getAttribute("admin");


        String accion = (String) request.getParameter("accion") == null ? "listar":
                (String) request.getParameter("accion");
        AdminDao bodegaDao = new AdminDao();




        ArrayList<DistritoBean> listaDistritos = bodegaDao.obtenerDistritos();
        request.setAttribute("listaDistritos", listaDistritos);

        int idBodega ;
        Long rucBodega;


        switch (accion){
            case  "registrar":
                String ruc = request.getParameter("ruc");
                String direccion = request.getParameter("direccion");
                String nombreBodega = request.getParameter("nombreBodega");
                String correo = request.getParameter("correo");
                String idDistrito = request.getParameter("idDistrito");

                Part part = request.getPart("foto");

                boolean fotoVal = true;
                if(part.getSize()==0 || !part.getContentType().contains("image/")){
                    fotoVal = false;
                }

                InputStream inputStream = part.getInputStream();
                boolean rucB = isRUCValid(ruc);
                boolean direccionB = validarString(direccion);
                boolean nombreBodegaB = validarString(nombreBodega);
                boolean distritoB = validarNumero(String.valueOf(idDistrito));
                boolean correoB = validarCorreo(correo);

                BodegaBean b = new BodegaBean();

                if(rucB && direccionB && nombreBodegaB && correoB && distritoB){

                    int idDistritoInt = Integer.parseInt(idDistrito);

                    boolean distritoSelected = false;
                    if(bodegaDao.buscarDistrito(idDistrito)!= null){
                        distritoSelected = true;
                    }
                    boolean rucExis = false;
                    if(bodegaDao.buscarRuc(ruc)){
                        rucExis = true;
                    }
                    boolean correoExis = false;
                    if(bodegaDao.buscarCorreo(correo)){
                        correoExis = true;
                    }

                    if(distritoSelected && !rucExis && !correoExis && idDistritoInt != 0 && fotoVal){

                        b.setFoto(inputStream);
                        b.setNombreBodega(nombreBodega);
                        b.setCorreoBodega(correo);
                        b.setDireccionBodega(direccion);
                        b.setIdDistrito(idDistritoInt);
                        b.setIdAdministrador(adminActual.getIdUsuario());
                        rucBodega= Long.valueOf(ruc);
                        b.setRucBodega(rucBodega);

                        bodegaDao.guardarBodega(b);


                        idBodega = bodegaDao.buscarIdBodega(ruc);

                        request.setAttribute("idBodega",idBodega);
                        request.setAttribute("rucBodega",rucBodega);

                        int puerto= request.getLocalPort();

                        Emails emails = new Emails();
                        String correoAenviar = correo;
                        String asunto = "REGISTRAR CONTRASEÑA";
                        String contenido = "Se ha iniciado el registro de su bodega "+nombreBodega+" con RUC:"+rucBodega+
                                ", para continuar con el registro ingrese al siguiente link y establezca su contraseña:\n" +
                                "http://localhost:"+puerto+request.getContextPath()+"/LoginBodega?accion=actualizarContra&idBodega="
                                +idBodega+"&rucBodega="+rucBodega+"\n" +
                                "Atentamente,\n" +
                                "El equipo de MiMarca.com ";

                        try {
                            emails.enviarCorreo(correoAenviar, asunto, contenido);
                        } catch (MessagingException e) {
                            System.out.println("Se capturo excepcion en envio de correo");
                        }
                        request.setAttribute("ruc",ruc);
                        request.setAttribute("nombreBodega",nombreBodega);
                        request.setAttribute("correo",correo);
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher("administrador/BodegaExitosa.jsp");
                        requestDispatcher.forward(request, response);
                    }else{
                        request.setAttribute("rucExis", rucExis);
                        request.setAttribute("correoExis", correoExis);
                        request.setAttribute("distritoSelected", distritoSelected);
                        request.setAttribute("fotoVal", fotoVal);
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher("administrador/registrarBodega.jsp");
                        requestDispatcher.forward(request, response);
                    }
                }else{
                    request.setAttribute("rucB",rucB);
                    request.setAttribute("direccionB",direccionB);
                    request.setAttribute("nombreBodegaB",nombreBodegaB);
                    request.setAttribute("correoB",correoB);
                    request.setAttribute("fotoVal", fotoVal);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("administrador/registrarBodega.jsp");
                    requestDispatcher.forward(request, response);
                }
                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        UsuarioBean adminActual = (UsuarioBean) session.getAttribute("admin");
        String accion = (String) request.getParameter("accion") == null ? "miPerfil":
                (String) request.getParameter("accion");

        if(adminActual!= null){
            response.addHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.addHeader("Cache-Control", "pre-check=0, post-check=0");
            response.setDateHeader("Expires", 0);
            int idAdminActual = adminActual.getIdUsuario();

            AdminDao bodegaDao = new AdminDao();

            switch (accion){
                case "miPerfil":

                    UsuarioBean usuario = bodegaDao.obtenerUsuario(idAdminActual);
                    request.setAttribute("usuario", usuario);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("administrador/homeAdmin.jsp");
                    requestDispatcher.forward(request,response);
                    break;
                case "listar":
                    String pag = request.getParameter("pag") == null ? "1" : request.getParameter("pag");

                    int cantPag = AdminDao.calcularCantPag(idAdminActual);
                    int paginaAct;
                    try{
                        paginaAct = Integer.parseInt(pag); //try
                        if(paginaAct>cantPag){
                            paginaAct = 1;
                        }
                    }catch(NumberFormatException e){
                        paginaAct = 1;
                    }

                    ArrayList<BodegaBean> listaBodegas = bodegaDao.obtenerListaBodegas(paginaAct,idAdminActual);

                    request.setAttribute("lista", listaBodegas);
                    request.setAttribute("cantPag", cantPag);
                    request.setAttribute("paginaAct",paginaAct);


                    RequestDispatcher view = request.getRequestDispatcher("administrador/listarBodegasAdmin.jsp");
                    view.forward(request,response);
                    break;
                case "registrar":
                    ArrayList<DistritoBean> listaDistritos = bodegaDao.obtenerDistritos();
                    request.setAttribute("listaDistritos", listaDistritos);
                    requestDispatcher = request.getRequestDispatcher("administrador/registrarBodega.jsp");
                    requestDispatcher.forward(request,response);
                    break;
                case "bloquear":
                    String nombreBodega = request.getParameter("nombreB");
                    boolean aTiempo = AdminDao.pedidoPendiente(nombreBodega); //devuelve true si presenta al menos un pedido en estado pendiente

                    ArrayList<PedidoBean> listaPedidos = bodegaDao.buscarBodegaconPedido(nombreBodega);

                    if(!aTiempo){
                        boolean bloqueo = Boolean.parseBoolean(request.getParameter("bloqueo"));
                        request.getSession().setAttribute("accion", bloqueo);
                        AdminDao.actualizarEstadoBodega(nombreBodega,bloqueo);
                        response.sendRedirect(request.getContextPath() +"/AdminServlet?accion=listar");
                    }else{
                        request.getSession().setAttribute("errorBloquearBodega", true);
                        request.getSession().setAttribute("listaPedidosPendiente",listaPedidos);
                        response.sendRedirect(request.getContextPath() +"/AdminServlet?accion=listar");
                    }
                    break;
                case "mostrarBodega":
                    String rucBodega = request.getParameter("ruc");
                    AdminDao adminDao = new AdminDao();
                    if(adminDao.buscarIdBodega(rucBodega)>0){
                        BodegaBean bodega = adminDao.buscarBodega(rucBodega);
                        request.setAttribute("bodega",bodega);
                        view = request.getRequestDispatcher("administrador/mostrarBodega.jsp");
                        view.forward(request, response);
                    }else{
                        response.sendRedirect(request.getContextPath() + "/AdminServlet?accion=listar");
                    }

                    break;
                default:
                    response.sendRedirect(request.getContextPath() +"/AdminServlet?accion=miPerfil");
                    break;
            }
        }else{
            RequestDispatcher view2;
            view2 = request.getRequestDispatcher("administrador/access_denied.jsp");
            view2.forward(request, response);
        }

    }
}
