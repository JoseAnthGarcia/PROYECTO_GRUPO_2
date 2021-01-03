package servlets.Cliente;



import beans.UsuarioBean;
import daos.UsuarioDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "LoginServlet", urlPatterns ={"/LoginServlet", "/"} )
public class LoginServlet extends HttpServlet {

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

        String accion = request.getParameter("accion") == null ?
                "login" : request.getParameter("accion");

        UsuarioDao usuarioDao = new UsuarioDao();
        switch(accion){
            case "login":
                String inputEmail = request.getParameter("inputEmail");
                String inputPassword = request.getParameter("inputPassword");

                UsuarioBean usuario = usuarioDao.validarUsuarioPassword(inputEmail,inputPassword);

                if(usuario!=null){
                    HttpSession session =request.getSession();
                    session.setAttribute("usuario",usuario);

                    response.sendRedirect(request.getContextPath()+"/UsuarioServlet?accion=Home");
                }else{
                    response.sendRedirect(request.getContextPath()+"/LoginServlet?error");
                }
                break;

            case "comprobarCorreo":
                String correoC = request.getParameter("correo");

                boolean correoBoo = validarCorreo(correoC);

                boolean correoNoEx = true;
                UsuarioBean usuarioRecu = usuarioDao.obtenerUsuarioPorCorreo(correoC);
                if(usuarioRecu.getIdUsuario()==0){
                    correoNoEx = false;
                }

                if(correoNoEx && correoBoo){
                    HttpSession sessionN = request.getSession();
                    //String ip = request.getLocalAddr();
                    int puerto = request.getLocalPort();
                    String proyecto = request.getContextPath();
                    int correoEnviado = usuarioDao.enviarCorreoLinkContra(usuarioRecu.getIdUsuario(),usuarioRecu.getContraseniaHashed(),usuarioRecu.getCorreo(),puerto,proyecto);
                    //queda pendiente mostrar mensaje de correo exitoso
                    sessionN.setAttribute("correoEnviado",correoEnviado);

                    //enviar a login mas mensaje de correo enviado exitosamente o problema al enviar correo
                    response.sendRedirect(request.getContextPath()+"/LoginServlet?accion=login");

                    //RequestDispatcher requestDispatcher = request.getRequestDispatcher("cliente/login.jsp");
                    //requestDispatcher.forward(request,response);
                }else{
                    request.setAttribute("correoBoo",correoBoo);
                    request.setAttribute("correoNoEx",correoNoEx);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("cliente/olvideContrasenia.jsp");
                    requestDispatcher.forward(request,response);
                }
                break;
            case "validarContraRecu":
                String contraseniaR = request.getParameter("contraseniaR");
                String contrasenia2R = request.getParameter("contrasenia2R");
                HttpSession sessionP = request.getSession();
                UsuarioBean usuarioP = (UsuarioBean) sessionP.getAttribute("usuarioP");
                int idUsuarioRecu = usuarioP.getIdUsuario();

                boolean contRecu=false;
                if(contraseniaR.equals(contrasenia2R)){
                    contRecu=true;
                }

                boolean contRecuEmpty=false;
                if(!contraseniaR.equals("")) {
                    contRecuEmpty = true;
                }

                boolean contraTrim= false;
                if(contraseniaR==contraseniaR.trim() && contrasenia2R==contrasenia2R.trim()){
                    contraTrim = true;
                }

                boolean contraSegura = false;
                if(validarContrasenia(contraseniaR) && validarContrasenia(contrasenia2R)){
                    contraSegura = true;
                }

                System.out.println(contRecu +":"+contRecuEmpty);
                if(contRecu && contRecuEmpty && contraTrim && contraSegura){
                    System.out.println("Soy libre");
                    System.out.println(idUsuarioRecu);
                    System.out.println(contrasenia2R+"malos pasos");
                    usuarioDao.actualizarContra(idUsuarioRecu, contraseniaR); //ojo con usuarioId
                    sessionP.setAttribute("cambioContra",true);
                    //sessionP.invalidate();
                    //Se termina la operacion y te manda a la pagina del Login
                    //pendiente mandar confirmacion de nueva contraseña exitosa
                    response.sendRedirect(request.getContextPath()+"/LoginServlet");
                }else{
                    //enviar otra vex id??'
                    //enviar contRecu
                    request.setAttribute("contRecu",contraseniaR.equals(contrasenia2R));
                    request.setAttribute("contRecuEmpty1",!contraseniaR.equals(""));
                    request.setAttribute("contRecuEmpty2",!contrasenia2R.equals(""));
                    request.setAttribute("contraTrim",(contraTrim && !contraseniaR.equals("") && !contrasenia2R.equals("")));
                    request.setAttribute("contraSecu1",validarContrasenia(contraseniaR));
                    request.setAttribute("contraSecu2",validarContrasenia(contrasenia2R));
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("cliente/recuperarContrasenia.jsp");
                    requestDispatcher.forward(request,response);
                }

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
        UsuarioDao usuarioDao = new UsuarioDao();
        UsuarioBean usuarioBean = (UsuarioBean) session.getAttribute("usuario");
        String accion = request.getParameter("accion") == null ?
                "login" : request.getParameter("accion");

        switch(accion) {
            case "olvideContra":
                RequestDispatcher requestDispatcher0 = request.getRequestDispatcher("cliente/olvideContrasenia.jsp");
                requestDispatcher0.forward(request, response);
                break;

            case "recuContra":
                session.invalidate();
                if (request.getParameter("id") != null && request.getParameter("contraHashed") != null) {
                    int idusuario;
                    try {
                        String contraHashed = request.getParameter("contraHashed");
                        idusuario = Integer.parseInt(request.getParameter("id"));
                        UsuarioBean usuarioP = usuarioDao.obtenerUsuario(idusuario);
                        System.out.println(idusuario);
                        System.out.println(contraHashed);
                        System.out.println("------------------------");
                        System.out.println(usuarioP.getIdUsuario());
                        System.out.println(usuarioP.getContraseniaHashed());

                        if (usuarioP.getContraseniaHashed() != null) {
                            if (usuarioP.getIdUsuario() == idusuario && usuarioP.getContraseniaHashed().equals(contraHashed)) {
                                HttpSession session0 = request.getSession();
                                session0.setAttribute("usuarioP", usuarioP);
                                //request.setAttribute("usuario",usuarioP);
                                System.out.println("Llego al paraiso");
                                RequestDispatcher requestDispatcher1 = request.getRequestDispatcher("cliente/recuperarContrasenia.jsp");
                                requestDispatcher1.forward(request, response); //podria lanzar error
                            } else {
                                //Hasta que exista el login ---Podria enviarse un mensaje de error al login tambien

                                RequestDispatcher requestDispatcher1 = request.getRequestDispatcher("cliente/access_denied.jsp");
                                requestDispatcher1.forward(request, response);
                            }
                        } else {
                            //Hasta que exista el login ---Podria enviarse un mensaje de error al login tambien

                            //response.sendRedirect(request.getContextPath() + "/LoginServlet");
                            RequestDispatcher requestDispatcher1 = request.getRequestDispatcher("cliente/access_denied.jsp");
                            requestDispatcher1.forward(request, response);

                        }
                    } catch (NumberFormatException e) {
                        //Hasta que exista el login ---Podria enviarse un mensaje de error al login tambien
                        //response.sendRedirect("LoginServlet");
                        //System.out.println("cayo en el catch");
                        //response.sendRedirect(request.getContextPath() + "/LoginServlet");
                        RequestDispatcher requestDispatcher1 = request.getRequestDispatcher("cliente/access_denied.jsp");
                        requestDispatcher1.forward(request, response);
                    }
                }else{
                    RequestDispatcher requestDispatcher1 = request.getRequestDispatcher("cliente/access_denied.jsp");
                    requestDispatcher1.forward(request, response);
                }
                break;
            case "parteEmpresa":
                RequestDispatcher requestDispatcher2 = request.getRequestDispatcher("cliente/parteEmpresa.jsp");
                requestDispatcher2.forward(request,response);
                break;

            default:
                if (usuarioBean == null && accion.equals("login")) {

                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("cliente/login.jsp");
                    requestDispatcher.forward(request, response);

                } else if (accion.equals("logout")) {
                    session.invalidate();
                    response.sendRedirect(request.getContextPath() + "/LoginServlet?accion=login");
                } else if (usuarioBean != null && accion.equals("login")) {
                    response.sendRedirect(request.getContextPath() + "/UsuarioServlet?accion=Home");
               }else {
                    view2 = request.getRequestDispatcher("cliente/access_denied.jsp");
                    view2.forward(request, response);
                }
                break;

            }
        }

    }

