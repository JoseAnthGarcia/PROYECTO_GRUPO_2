package servlets.Bodega;

import daos.BodegaDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ImagenesBodegaServlet")
public class ImagenesBodegaServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        if (request.getParameter("idProducto")!=null){
            //Se debe recibir el id de lo que se queire mostrar
            int id=Integer.parseInt(request.getParameter("idProducto"));
            //una vez obtenido, se ejecuta el metodo listarImg de la personaDao
            BodegaDao bodegaDao = new BodegaDao();
            bodegaDao.listarImg(id, response);
        }
        if(request.getParameter("idBodega")!=null){
            int id=Integer.parseInt(request.getParameter("idBodega"));
            BodegaDao bodegaDao = new BodegaDao();
            bodegaDao.listarImgBodega(id, response);
        }

    }
}
