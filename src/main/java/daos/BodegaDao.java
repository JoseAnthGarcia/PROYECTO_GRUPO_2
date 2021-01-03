package daos;

import beans.*;
import dtos.PedidosDatosDTO;
import dtos.ProductoCantDto;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class BodegaDao extends BaseDao{

    public static void crearProducto(String nombreProducto, String descripcion, int stock, BigDecimal precioUnitario, int idBodega, InputStream inputStream){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String sql = "insert into producto (nombreProducto,descripcion,stock,precioUnitario,idBodega, foto) values (\n" +
                " ?, ?, ?, ?, ?, ?);";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setString(1, nombreProducto);
            pstmt.setString(2, descripcion);
            pstmt.setInt(3, stock);
            pstmt.setBigDecimal(4, precioUnitario);
            pstmt.setInt(5, idBodega);
            pstmt.setBlob(6, inputStream);

            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static int calcularCantPag(){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String url = "jdbc:mysql://localhost:3306/mydb?serverTimezone=America/Lima";

        // TODO: idBodega se ha hardcodeado
        String sql = "(count(*)/5) from producto where idBodega=1 AND estado='Existente'";  // numero de paginas

        int cantPag = 0;
        try (Connection conn = DriverManager.getConnection(url, "root", "root");
             Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql);) {

            rs.next();
            cantPag = rs.getInt(1);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cantPag;
    }

    public static int calcularCantPag(String productName, int idBodega) {
        /**
         *  Para barra de busqueda
         */

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String sql = "select * from producto where idBodega=? and lower(nombreProducto) like ? and estado='Existente';";  // numero de paginas

        int cantPag = 0;
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, idBodega);
            pstmt.setString(2, "%"+productName+"%");

            try (ResultSet rs = pstmt.executeQuery();) {
                while (rs.next()) {
                    cantPag++;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return (int) Math.ceil((double) cantPag / 5);
    }


    public static ArrayList<ProductoBean> listarProductoBodega(int pagina){

        ArrayList<ProductoBean> listaProductos = new ArrayList<>();

        int limit = (pagina-1)*5;
        String sql = "select idProducto, nombreFoto, rutaFoto, nombreProducto,descripcion,stock,precioUnitario from producto WHERE idBodega=1 AND estado='Existente' limit ?,5;";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, limit);

            try(ResultSet rs = pstmt.executeQuery();){
                while (rs.next()) {
                    ProductoBean producto = new ProductoBean();
                    producto.setId(rs.getInt(1));
                    /*producto.setNombreFoto(rs.getString(2));
                    producto.setRutaFoto(rs.getString(3));*/
                    producto.setNombreProducto(rs.getString(4));
                    producto.setDescripcion(rs.getString(5));
                    producto.setStock(rs.getInt(6));
                    producto.setPrecioProducto(rs.getBigDecimal(7));
                    listaProductos.add(producto);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return listaProductos;
    }

    public static ArrayList<ProductoBean> listarProductoBodega(int pagina, String productName, int idBodega){
        /**
         *  Para barra de busqueda
         */

        ArrayList<ProductoBean> listaProductos = new ArrayList<>();

        int limit = (pagina-1)*5;

        String sql = "select idProducto, nombreProducto,descripcion,stock,precioUnitario from producto WHERE idBodega=? AND lower(nombreProducto) like ? AND estado='Existente' limit ?,5;";  // numero de paginas

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, idBodega);
            pstmt.setString(2, "%"+productName+"%");
            pstmt.setInt(3, limit);

            try(ResultSet rs = pstmt.executeQuery();){
                while (rs.next()) {
                    ProductoBean producto = new ProductoBean();
                    producto.setId(rs.getInt("idProducto"));
                    producto.setNombreProducto(rs.getString("nombreProducto"));
                    producto.setDescripcion(rs.getString("descripcion"));
                    producto.setStock(rs.getInt("stock"));
                    producto.setPrecioProducto(rs.getBigDecimal("precioUnitario"));
                    listaProductos.add(producto);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return listaProductos;
    }

    //devuelve imagen por ID
    public void listarImg(int id, HttpServletResponse response) {

        String sql = "select foto from producto where idProducto=?;";

        InputStream inputStream = null;
        OutputStream outputStream = null;
        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        response.setContentType("image/*"); //que hace esto?? uu

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            outputStream = response.getOutputStream();

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    inputStream = rs.getBinaryStream("foto");
                }
            }
            bufferedInputStream = new BufferedInputStream(inputStream);
            bufferedOutputStream = new BufferedOutputStream(outputStream);

            int i = 0;
            while ((i = bufferedInputStream.read()) != -1) {
                bufferedOutputStream.write(i);
            }
            bufferedOutputStream.flush();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void listarImgBodega(int id, HttpServletResponse response) {

        String sql = "select foto from bodega where idBodega=?;";

        InputStream inputStream = null;
        OutputStream outputStream = null;
        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        response.setContentType("image/*"); //que hace esto?? uu

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            outputStream = response.getOutputStream();

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    inputStream = rs.getBinaryStream("foto");
                }
            }
            bufferedInputStream = new BufferedInputStream(inputStream);
            bufferedOutputStream = new BufferedOutputStream(outputStream);

            int i = 0;
            while ((i = bufferedInputStream.read()) != -1) {
                bufferedOutputStream.write(i);
            }
            bufferedOutputStream.flush();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }


    public boolean buscarProducto(int idProducto, int idBodega){

        boolean exisProduct = false;

        String sql = "SELECT * FROM producto WHERE idProducto = ? AND idBodega=? AND estado='Existente'";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, idProducto);
            pstmt.setInt(2, idBodega);

            try(ResultSet rs = pstmt.executeQuery();){
                if(rs.next()){
                    exisProduct = true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return exisProduct;
    }

    public ProductoBean buscarProducto2(int idProducto, int idBodega){

        ProductoBean producto = null;

        String sql = "SELECT idProducto, nombreProducto, descripcion, stock, precioUnitario FROM producto WHERE idProducto = ? AND idBodega=? AND estado='Existente'";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, idProducto);
            pstmt.setInt(2, idBodega);

            try(ResultSet rs = pstmt.executeQuery();){
                if(rs.next()){
                    producto = new ProductoBean();
                    producto.setId(rs.getInt("idProducto"));
                    producto.setNombreProducto(rs.getString("nombreProducto"));
                    producto.setDescripcion(rs.getString("descripcion"));
                    producto.setStock(rs.getInt("stock"));
                    producto.setPrecioProducto(rs.getBigDecimal("precioUnitario"));

                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return producto;
    }

    public ArrayList<PedidoBean> buscarPedidoConProducto(int idProducto){

        ArrayList<PedidoBean> listaPedidos = new ArrayList<>();

        String sql = "SELECT pe.codigo FROM producto p\n" +
                "LEFT JOIN pedido_has_producto php ON p.idProducto = php.idProducto\n" +
                "LEFT JOIN pedido pe ON php.idPedido = pe.idPedido\n" +
                "WHERE p.idProducto = ? AND pe.estado = 'Pendiente';";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, idProducto);

            try(ResultSet rs = pstmt.executeQuery();){
                while (rs.next()) {
                    PedidoBean pedido = new PedidoBean();
                    pedido.setCodigo(rs.getString(1));
                    listaPedidos.add(pedido);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return listaPedidos;
    }

    public void eliminarProducto(int idProducto){
        String sql = "UPDATE producto SET estado='Eliminado', stock=0 WHERE idProducto=?;";


        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1,idProducto);
            pstmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //-------------- Para inicio de sesion ------------------

    /*
    public BodegaBean validarUsuarioPassword(String user, String password){

        String sql = "select * from bodega where correo=? and contrasenia=?;";
        BodegaBean bodegaBean = null;
        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);){

            pstmt.setString(1,user);
            pstmt.setString(2,password);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    int idBodega = rs.getInt(1);
                    bodegaBean = this.obtenerBodega(idBodega);
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return bodegaBean;
    }*/

    //ENCRIPTAR CONTRASENIA
    public BodegaBean validarUsuarioPasswordHashed(String correo, String contrasenia) {

        BodegaBean bodegaBean = null;

        String sql = "select * from bodega where correo=? and contraseniaHashed=sha2(?,256)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, correo);
            pstmt.setString(2, contrasenia);

            try (ResultSet rs = pstmt.executeQuery();) {
                if (rs.next()) {
                    int idBodega = rs.getInt(1);
                    bodegaBean = this.obtenerBodega(idBodega);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return bodegaBean;
    }

    public BodegaBean obtenerBodega(int idBodega){
        BodegaBean bodega = null;
        String sql = "SELECT * FROM mydb.bodega b inner join mydb.distrito d on d.idDistrito = b.idDistrito where b.idBodega = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, idBodega);
            try(ResultSet rs = pstmt.executeQuery()){
                rs.next();
                bodega = new BodegaBean();
                bodega.setIdBodega(rs.getInt("idBodega"));
                bodega.setFoto(rs.getAsciiStream("foto"));
                bodega.setNombreBodega(rs.getString("nombreBodega"));
                bodega.setRucBodega(rs.getLong("ruc"));
                DistritoBean distrito = new DistritoBean();
                distrito.setId(rs.getInt("idDistrito"));
                distrito.setNombre(rs.getString("nombreDistrito"));
                bodega.setDistrito(distrito);
                bodega.setDireccionBodega(rs.getString("direccion"));
                bodega.setCorreoBodega(rs.getString("correo"));

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return bodega;
    }

    //-------------------------------Flujo bodega---------------------------

    public void actualizarProducto( int idProducto, String descripcion, int stock, BigDecimal precioUnitario) {

        String sql = "UPDATE producto SET descripcion = ?, stock = ?, precioUnitario = ? WHERE idProducto = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setString(1, descripcion);
            pstmt.setInt(2, stock);
            pstmt.setBigDecimal(3, precioUnitario);
            pstmt.setInt(4, idProducto);

            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void actualizarProductoFoto(int idProducto, String descripcion, int stock, BigDecimal precioUnitario, InputStream inputStream){
        String sql = "UPDATE producto SET descripcion = ?, stock = ?, precioUnitario = ?, foto = ? WHERE idProducto = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setString(1, descripcion);
            pstmt.setInt(2, stock);
            pstmt.setBigDecimal(3, precioUnitario);
            pstmt.setBinaryStream(4,inputStream);
            pstmt.setInt(5, idProducto);

            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //-------------------------------Listar pedidos---------------------------
    public int calcularCantPagPedidos(int idBodegaActual){

        String sql = "select ceil(count(codigo)/5) from pedido where idBodega = ?";

        int cantPag = 0;
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql); ) {

                pstmt.setInt(1,idBodegaActual);
                try(ResultSet rs = pstmt.executeQuery();){
                    rs.next();
                    cantPag = rs.getInt(1);
                }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cantPag;
    }

    public ArrayList<PedidoBean> obtenerListaPedidos(int pagina, int idBodega) {
        int limit = (pagina-1)*5;
        ArrayList<PedidoBean> listaPedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedido \n" +
                "where idBodega = ? ORDER BY estado desc, fecha_registro desc limit ?, 5";

        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idBodega);
            statement.setInt(2, limit);

            try (ResultSet rs = statement.executeQuery();) {
                while (rs.next()) {
                    PedidoBean pedidos = new PedidoBean();
                    pedidos.setId(rs.getInt("idPedido"));
                    pedidos.setCodigo(rs.getString("codigo"));
                    pedidos.setEstado(rs.getString("estado"));
                    pedidos.setTotalApagar(rs.getBigDecimal("totalApagar"));
                    pedidos.setFecha_registro(rs.getString("fecha_registro"));
                    pedidos.setFecha_recojo(rs.getString("fecha_recojo"));
                    listaPedidos.add(pedidos);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaPedidos;
    }

    public PedidosDatosDTO mostrarPedido(String codigo) {

        String sql = "SELECT p.codigo, b.nombreBodega, p.fecha_registro, p.fecha_recojo, DATE_ADD(p.fecha_recojo, INTERVAL 1 DAY) as `fecha limite`, count(php.idProducto) as unidades, t1.`costo total` FROM pedido p \n" +
                "LEFT JOIN bodega b ON p.idBodega = b.idBodega left join pedido_has_producto php ON p.idPedido = php.idPedido \n" +
                "LEFT JOIN (SELECT t.idPedido, sum(t.`Costo por producto`) as `costo total` FROM (SELECT php.idPedido, php.idProducto, (php.cantidad*p.precioUnitario) as `Costo por producto`FROM pedido_has_producto php \n" +
                "LEFT JOIN producto p ON php.idProducto = p.idProducto) t  GROUP BY t.idPedido) t1 ON p.idPedido = t1.idPedido \n" +
                "WHERE codigo = ? GROUP BY p.idPedido";
        PedidosDatosDTO pedidos = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, codigo);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    pedidos = new PedidosDatosDTO();
                    pedidos.setCodigo(rs.getString("codigo"));
                    pedidos.setNombreBodega(rs.getString("nombreBodega"));
                    pedidos.setFecha_registro(rs.getString("fecha_registro"));
                    pedidos.setFecha_recojo(rs.getString("fecha_recojo"));
                    pedidos.setFecha_limite(rs.getString("fecha limite"));
                    pedidos.setUnidades(rs.getInt("unidades"));
                    pedidos.setCosto_total(rs.getBigDecimal("costo total"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(pedidos != null){
            String sql1 = "select pr.idProducto, pr.nombreProducto , ph.cantidad, pr.precioUnitario, (ph.cantidad*pr.precioUnitario)\n" +
                    "from pedido pe\n" +
                    "inner join pedido_has_producto ph on pe.idPedido=ph.idPedido\n" +
                    "inner join producto pr on ph.idProducto=pr.idProducto\n" +
                    "where pe.codigo=?;";

            ArrayList<ProductoCantDto> listProdCant = new ArrayList<>();

            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql1);) {
                pstmt.setString(1, codigo);
                try(ResultSet rs1 = pstmt.executeQuery()){
                    while(rs1.next()){
                        ProductoCantDto productoCantDto = new ProductoCantDto();
                        ProductoBean producto = new ProductoBean();
                        producto.setId(rs1.getInt("pr.idProducto"));
                        producto.setNombreProducto(rs1.getString("pr.nombreProducto"));
                        producto.setPrecioProducto(rs1.getBigDecimal("pr.precioUnitario"));
                        productoCantDto.setProducto(producto);
                        productoCantDto.setCant(rs1.getInt("ph.cantidad"));
                        listProdCant.add(productoCantDto);
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            pedidos.setListaProductCant(listProdCant);
        }

        return pedidos;
    }

    public PedidoBean obtenerPedidoBodega(String codigo) {

        PedidoBean pedidoBean = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String sql = "SELECT * FROM mydb.pedido WHERE codigo = ?";
            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql);) {
                pstmt.setString(1, codigo);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        pedidoBean = new PedidoBean();
                        pedidoBean.setId(rs.getInt(1));
                        pedidoBean.setCodigo(rs.getString(2));
                        pedidoBean.setEstado(rs.getString(3));
                    }
                }

            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return pedidoBean;
    }

    public void entregarPedido(String codigo) {

        try (Connection conn = getConnection();) {
            String sql = "update mydb.pedido set estado = 'Entregado' where codigo = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, codigo);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean verificarCancelarPedido(String codigo){

        boolean esPosible=false;
        String sql = "select * from pedido where codigo = ?  AND (TIMESTAMPDIFF(DAY, fecha_recojo, now()) >= 1);";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, codigo);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    esPosible = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return esPosible;
    }

    public void cancelarPedido(String codigo) {
        try (Connection conn = getConnection();) {
            String sql = "update mydb.pedido set estado = 'Cancelado' where codigo = ?  AND (datediff(now(), fecha_recojo) > 1)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, codigo);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
