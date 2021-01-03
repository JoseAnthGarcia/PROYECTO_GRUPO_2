package daos;

import beans.*;
import dtos.DetallesPedidoDto;
import dtos.ProductoCantDto;
import dtos.ProductosClienteDTO;
import servlets.Emails;

import javax.mail.MessagingException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;


public class UsuarioDao extends BaseDao {

    public ArrayList<DistritoBean> obtenerDistritos() {

        ArrayList<DistritoBean> listaDistritos = new ArrayList<>();

        String sql = "SELECT * FROM distrito;";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql);) {

            while (rs.next()) {
                DistritoBean distrito = new DistritoBean();
                distrito.setId(rs.getInt(1));
                distrito.setNombre(rs.getString(2));
                listaDistritos.add(distrito);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return listaDistritos;
    }

    public void regitrarNuevoUsuario(String nombres, String apellidos,
                                     String dni, String correo,
                                     String contrasenia, int idDistrito) {
        String sql = "INSERT INTO usuario(nombreUsuario, apellido, dni, correo, idDistrito,contraseniaHashed)\n" +
                "VALUES (?, ?, ?, ?, ?, sha2(?,256));";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setString(1, nombres);
            pstmt.setString(2, apellidos);
            pstmt.setString(3, dni);
            pstmt.setString(4, correo);
            pstmt.setInt(5, idDistrito);
            pstmt.setString(6, contrasenia);


            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public DistritoBean buscarDistrito(String idDistrito) {

        DistritoBean distrito = null;

        String sql = "SELECT * FROM distrito WHERE idDistrito = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, Integer.parseInt(idDistrito));

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    distrito = new DistritoBean();
                    distrito.setId(rs.getInt(1));
                    distrito.setNombre(rs.getString(2));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return distrito;
    }

    public boolean buscarCorreo(String correo) {
        boolean encontrado = false;

        String sql = "SELECT * FROM usuario WHERE correo = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, correo);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    encontrado = true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return encontrado;
    }

    public boolean buscarDni(String dni) {
        boolean encontrado = false;

        String sql = "SELECT * FROM usuario WHERE dni = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, dni);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    encontrado = true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return encontrado;
    }

    /*Para la parte de editar*/
    public UsuarioBean obtenerUsuario(int usuarioId) {


        String sql = "select u.idUsuario, u.nombreUsuario, u.apellido, u.dni, u.correo,u.idDistrito, d.nombreDistrito, u.contraseniaHashed \n" +
                "from usuario u\n" +
                "inner join distrito d on u.idDistrito=d.idDistrito\n" +
                "where idUsuario=? and rol='Usuario';";
        UsuarioBean usuarioBean = new UsuarioBean();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, usuarioId);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {

                    DistritoBean distritoBean = new DistritoBean();
                    usuarioBean.setIdUsuario(rs.getInt(1));
                    usuarioBean.setNombre(rs.getString(2));
                    usuarioBean.setApellido(rs.getString(3));
                    usuarioBean.setDni(rs.getString(4));
                    usuarioBean.setCorreo(rs.getString(5));
                    distritoBean.setId(rs.getInt(6));
                    distritoBean.setNombre(rs.getString(7));
                    usuarioBean.setDistrito(distritoBean);

                    //se agrego contraseniaHashed - ATENCIÓN!!!
                    usuarioBean
                            .setContraseniaHashed(rs.getString(8));



                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return usuarioBean;
    }

    public void actualizarUsuario(String nombres, String apellidos,
                                  int idDistrito, int idUsuario, UsuarioBean usuarioBean) {

        String sql = "UPDATE usuario SET nombreUsuario = ?, apellido = ?, idDistrito = ? WHERE idUsuario = ?";


        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setString(1, nombres);
            pstmt.setString(2, apellidos);
            pstmt.setInt(3, idDistrito);
            pstmt.setInt(4, idUsuario);
            usuarioBean.setNombre(nombres);
            usuarioBean.setApellido(apellidos);
            usuarioBean.getDistrito().setId(idDistrito);
            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String sql2 = "SELECT nombreDistrito FROM distrito where idDistrito = ?;";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql2);) {
            pstmt.setInt(1, idDistrito);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {

                    usuarioBean.getDistrito().setNombre(rs.getString(1));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public boolean compararContrasenia(int idUsuario, String inputContra){
        boolean iguales = false;
        String sql = "select * from usuario where idUsuario=? and contraseniaHashed=sha2(?, 256);";
        UsuarioBean usuarioBean = new UsuarioBean();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, idUsuario);
            pstmt.setString(2, inputContra);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    iguales = true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return iguales;
    }

    // actualizarContra actualizado para incluir contraseniaHashed ATENCION!!!!
    public void actualizarContra(int usuarioID, String contraseniaNew) {
        String sql = "UPDATE usuario SET contraseniaHashed = sha2(?,256) WHERE idUsuario = ?";


        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setString(1, contraseniaNew);
            pstmt.setInt(2, usuarioID);

            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    //Agregado por EM Usado para recuperar contrasenia
    public UsuarioBean obtenerUsuarioPorCorreo(String correo) {


        String sql = "select idUsuario, contraseniaHashed " +
                "from usuario  where correo=? and rol='Usuario';";
        UsuarioBean usuarioBean = new UsuarioBean();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, correo);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    usuarioBean.setIdUsuario(rs.getInt(1));
                    //depende de como lo llamen ---- posiblemente corregir
                    usuarioBean.setContraseniaHashed(rs.getString(2));
                    usuarioBean.setCorreo(correo);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return usuarioBean;
    }

    //correo para recuperar contraseña
    //link aun no planteado
    public int enviarCorreoLinkContra(int id, String contraHashed, String correo, int puerto,String proyecto){
        int envioExitoso = 1;
        String subject = "Reestablecimiento de contraseña -MiMarca-";
        String content = "El link para restablecer su contraseña es : \n" +
                "http://localhost:"+puerto+proyecto+"/LoginServlet?accion=recuContra&contraHashed=" +contraHashed+ "&id="+id+
                "\n" +
                "Atentamente,\n" +
                "El equipo de MiMarca.com ";
        Emails email = new Emails();
        try {
            email.enviar(correo,subject,content);

        } catch (MessagingException e) {
            e.printStackTrace();
            envioExitoso = 2;
        }
        return envioExitoso;
    }

    //Parte de realizarUnPedido:
    public int calcularCantPagQuery(String query, int cantPorPag) {

        String sql = query;  // numero de paginas
        int cantPag = 0;
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql);) {

            while (rs.next()) {
                cantPag++;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return (int) Math.ceil((double) cantPag / cantPorPag);
    }


    public static ArrayList<ProductoBean> listarProductosBodega(int idBodega, int pagina, int cantPorPag) {

        ArrayList<ProductoBean> listaProductos = new ArrayList<>();

        int limit = (pagina - 1) * cantPorPag;

        String sql = "SELECT * FROM producto WHERE idBodega=? and stock <> 0 and estado <> 'Eliminado' \n" +
                " order by nombreProducto limit ?,?;";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, idBodega);
            pstmt.setInt(2, limit);
            pstmt.setInt(3, cantPorPag);

            try (ResultSet rs = pstmt.executeQuery();) {
                while (rs.next()) {
                    ProductoBean producto = new ProductoBean();
                    producto.setId(rs.getInt(1));
                    producto.setNombreProducto(rs.getString("nombreProducto"));
                    producto.setPrecioProducto(rs.getBigDecimal("precioUnitario"));
                    producto.setStock(rs.getInt("stock"));
                    listaProductos.add(producto);

                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return listaProductos;
    }

    public ArrayList<ProductoBean> buscarProducto(int idBodega, String textoBuscar) {

        ArrayList<ProductoBean> listaProductos = new ArrayList<>();

        String sql = "select * from producto where idBodega =? and estado<> 'Eliminado' and lower(nombreProducto) like ?;";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, idBodega);
            pstmt.setString(2, "%"+ textoBuscar + "%");

            try (ResultSet rs = pstmt.executeQuery();) {

                while (rs.next()) {
                    ProductoBean productoBean = new ProductoBean();

                    productoBean.setId(rs.getInt("idProducto"));
                    /*productoBean.setNombreFoto(rs.getString(2));
                    productoBean.setRutaFoto(rs.getString(3));*/
                    productoBean.setNombreProducto(rs.getString("nombreProducto"));
                    productoBean.setPrecioProducto(rs.getBigDecimal("precioUnitario"));
                    productoBean.setStock(rs.getInt("stock"));
                    if(productoBean.getStock()!=0) {
                        listaProductos.add(productoBean);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listaProductos;
    }

    public UsuarioBean validarUsuarioPassword(String user, String password){
        String sql = "select* from usuario where correo=? and contraseniaHashed=sha2(?,256) and rol='Usuario';";
        UsuarioBean usuarioBean = null;
        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);){

            pstmt.setString(1,user);
            pstmt.setString(2,password);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    int usuarioId = rs.getInt(1);
                    usuarioBean = this.obtenerUsuario(usuarioId);
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return usuarioBean;
    }

    //Realizar pedido----------------------------
    public ArrayList<BodegaBean> listarBodegas(int pag){
        ArrayList<BodegaBean> listaBodegas = new ArrayList<>();
        int cantPag = 8;
        String sql = "select *  from bodega b\n" +
                "inner join distrito d on b.idDistrito=d.idDistrito\n" +
                "where b.estado = \"Activo\"\n" +
                "order by d.idDistrito, b.nombreBodega ASC limit ?,?;";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, (pag-1)*cantPag);
            pstmt.setInt(2, cantPag);
            try(ResultSet rs = pstmt.executeQuery()){
                while(rs.next()){
                    BodegaBean bodega = new BodegaBean();
                    bodega.setIdBodega(rs.getInt("b.idBodega"));
                    bodega.setNombreBodega(rs.getString("b.nombreBodega"));
                    bodega.setDireccionBodega(rs.getString("b.direccion"));

                    DistritoBean distrito = new DistritoBean();
                    distrito.setId(rs.getInt("d.idDistrito"));
                    distrito.setNombre(rs.getString("d.nombreDistrito"));
                    bodega.setDistrito(distrito);

                    listaBodegas.add(bodega);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return listaBodegas;
    }

    public ArrayList<BodegaBean> listarBodegasDistrito(int idUsuario){
        ArrayList<BodegaBean> listaBodegas = new ArrayList<>();
        String sql = "select * from bodega b\n" +
                "inner join distrito d on b.idDistrito=d.idDistrito\n" +
                "where b.idDistrito = (SELECT idDistrito FROM usuario WHERE idUsuario = ?) and estado = \"Activo\"\n" +
                "order by nombreBodega;";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, idUsuario);
            try(ResultSet rs = pstmt.executeQuery()){
                while(rs.next()){

                    BodegaBean bodega = new BodegaBean();
                    bodega.setIdBodega(rs.getInt("b.idBodega"));
                    bodega.setNombreBodega(rs.getString("b.nombreBodega"));
                    bodega.setDireccionBodega(rs.getString("b.direccion"));

                    DistritoBean distrito = new DistritoBean();
                    distrito.setId(rs.getInt("d.idDistrito"));
                    distrito.setNombre(rs.getString("d.nombreDistrito"));
                    bodega.setDistrito(distrito);

                    listaBodegas.add(bodega);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return listaBodegas;
    }

    public static int calcularCantPagListarBodegas(){

        String sql = "select ceil(count(*)/8)\n" +
                "from (select b.idBodega, b.foto,nombreBodega,  b.direccion  from bodega b\n" +
                "where b.estado = \"Activo\") t;";  // numero de paginas

        int cantPag = 0;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            try(ResultSet rs = pstmt.executeQuery()){
                rs.next();
                cantPag = rs.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cantPag;
    }

    public BodegaBean obtenerBodega(int idBodega){
        BodegaBean bodega = null;
        String sql = "SELECT * FROM bodega b\n" +
                "inner join distrito d on d.idDistrito=b.idDistrito\n" +
                "WHERE idBodega=?;";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, idBodega);
            try(ResultSet rs = pstmt.executeQuery()){
                rs.next();
                bodega = new BodegaBean();
                bodega.setIdBodega(rs.getInt("b.idBodega"));
                bodega.setNombreBodega(rs.getString("b.nombreBodega"));
                bodega.setEstadoBodega(rs.getString("b.estado"));
                bodega.setDireccionBodega(rs.getString("b.direccion"));

                DistritoBean distrito = new DistritoBean();
                distrito.setId(rs.getInt("d.idDistrito"));
                distrito.setNombre(rs.getString("d.nombreDistrito"));
                bodega.setDistrito(distrito);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return bodega;
    }

    //ojo: ya el producto esta como existente, pues sino no se muestra en la lista
    public ProductoBean obtenerProducto(int idProducto){
        ProductoBean producto = null;
        String sql = "SELECT * FROM producto WHERE idProducto=?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, idProducto);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    producto = new ProductoBean();
                    producto.setId(rs.getInt("idProducto"));
                    producto.setNombreProducto(rs.getString("nombreProducto"));
                    producto.setDescripcion(rs.getString("descripcion"));
                    producto.setPrecioProducto(rs.getBigDecimal("precioUnitario"));
                    producto.setStock(rs.getInt("stock"));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return producto;
    }

    public boolean verificarProductoBodega(int idProducto, int idBodega){
        boolean existe = false;
        String sql = "SELECT * FROM producto WHERE idProducto=? and idBodega=? and estado='Existente';";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, idProducto);
            pstmt.setInt(2, idBodega);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    existe = true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return existe;
    }

    public String generarCodigoPedido(){
        String codigo = "";
        do{ //bucle para no repetir codigos generados
            // Los caracteres de interés en un array de char.
            char[] chars = "0123456789".toCharArray();
            // Longitud del array de char.
            int charsLength = chars.length;
            // Instanciamos la clase Random
            Random random = new Random();
            // Un StringBuffer para componer la cadena aleatoria de forma eficiente
            StringBuffer buffer = new StringBuffer();
            // Bucle para elegir una cadena de 10 caracteres al azar
            for (int i = 0; i < 9; i++) {
                // Añadimos al buffer un caracter al azar del array
                buffer.append(chars[random.nextInt(charsLength)]);
            }
            codigo = buffer.toString();
        }while(obtenerPedido(codigo)!=null);

        // Y solo nos queda hacer algo con la cadena
        //System.out.println(buffer.toString());
        return codigo;
    }

    public int crearPedido(PedidoBean pedido){
        int idPedido =-1;
        String sql = "insert into pedido (codigo, fecha_registro,\n" +
                "fecha_recojo,idUsuario,idBodega, totalApagar)\n" +
                "values(?,now(),?,?,?,?);";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {
            pstmt.setString(1, pedido.getCodigo());
            pstmt.setString(2, pedido.getFecha_recojo());
            pstmt.setInt(3,pedido.getUsuario().getIdUsuario());
            pstmt.setInt(4,pedido.getBodegaBean().getIdBodega());
            pstmt.setBigDecimal(5, pedido.getTotalApagar());
            pstmt.executeUpdate();
            try(ResultSet rsKey = pstmt.getGeneratedKeys()){
                if(rsKey.next()){
                    idPedido = rsKey.getInt(1);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return idPedido;
    }

    public void ingresarProductosApedido(int idPedido, ArrayList<ProductoCantDto> listaProductos){
        for(ProductoCantDto productoPedido: listaProductos){
            //Ingreso los productos:
            String sql = "insert into pedido_has_producto (idPedido,idProducto, Cantidad, precioUnitario)\n" +
                    "values(?,?, ?, ?);";
            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql);) {
                pstmt.setInt(1, idPedido);
                pstmt.setInt(2,productoPedido.getProducto().getId());
                pstmt.setInt(3,productoPedido.getCant());
                pstmt.setBigDecimal(4, productoPedido.getProducto().getPrecioProducto());
                pstmt.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            //TODO: validar stock maximo
            //Actualizo el stock:
            ProductoBean producto = obtenerProducto(productoPedido.getProducto().getId());
            int newStock = producto.getStock() - productoPedido.getCant();
            String sql1 = "update producto set stock = ?\n" +
                    "where idProducto = ?;";

            try (Connection conn1 = getConnection();
                 PreparedStatement pstmt1 = conn1.prepareStatement(sql1);) {
                pstmt1.setInt(1, newStock);
                pstmt1.setInt(2, productoPedido.getProducto().getId());
                pstmt1.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    //Listar pedidos: -----------------------------------
    public int calcularCantPagPedidos(int usuarioId){
        int cantPorPag = 5;
        String sql = "select ceil(count(codigo)/?) from pedido where idUsuario=?;";

        int cantPags = 0;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, cantPorPag);
            pstmt.setInt(2, usuarioId);
            try(ResultSet rs = pstmt.executeQuery()){
                rs.next();
                cantPags = rs.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return cantPags;
    }

    public ArrayList<PedidoBean> listarPedidosCliente (int pagina , int usuarioId){

        int cantPorPag = 5;
        ArrayList<PedidoBean> listaPedidos=  new ArrayList<>();

        int limit = (pagina-1)*cantPorPag;
        String sql= "select idPedido, codigo, estado, totalApagar,fecha_registro, fecha_recojo\n" +
                "from pedido\n" +
                "where idUsuario=? order by estado desc, fecha_registro desc\n" +
                "limit ?,5;";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, usuarioId);
            pstmt.setInt(2, limit);

            try(ResultSet rs = pstmt.executeQuery();){
                while(rs.next()){
                    PedidoBean pedidosClienteBean = new PedidoBean();
                    pedidosClienteBean.setId(rs.getInt("idPedido"));
                    pedidosClienteBean.setCodigo(rs.getString("codigo"));
                    pedidosClienteBean.setEstado(rs.getString("estado"));
                    pedidosClienteBean.setTotalApagar(rs.getBigDecimal("totalApagar"));
                    pedidosClienteBean.setFecha_registro(rs.getString("fecha_registro"));
                    pedidosClienteBean.setFecha_recojo(rs.getString("fecha_recojo"));
                    listaPedidos.add(pedidosClienteBean);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return listaPedidos;
    }

    public DetallesPedidoDto  detallesPedido (int idPedido){

        DetallesPedidoDto detallesPedidoDto = null;

        //obtengo pedido:
        String sql1 = "select p. codigo, b.nombreBodega, p.fecha_registro, p.fecha_recojo, \n" +
                "\t\tDATE_SUB(p.fecha_recojo, INTERVAL 1 HOUR) as \"fecha_limite\" \n" +
                "from pedido p \n" +
                "inner join bodega b on p.idBodega=b.idBodega\n" +
                "where p.idPedido= ?;";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql1);) {
            pstmt.setInt(1, idPedido);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    detallesPedidoDto = new DetallesPedidoDto();
                    PedidoBean pedido = new PedidoBean();
                    pedido.setCodigo(rs.getString("codigo"));
                    pedido.setFecha_registro(rs.getString("fecha_registro"));
                    pedido.setFecha_recojo(rs.getString("fecha_recojo"));
                    BodegaBean bodega = new BodegaBean();
                    bodega.setNombreBodega(rs.getString("nombreBodega"));
                    pedido.setBodegaBean(bodega);
                    detallesPedidoDto.setPedido(pedido);
                    detallesPedidoDto.setFechaLimitCancel(rs.getString("fecha_limite"));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if(detallesPedidoDto != null){
            String sql2 = "select pr.idProducto, pr.nombreProducto , ph.cantidad, pr.precioUnitario, (ph.cantidad*pr.precioUnitario)\n" +
                    "from pedido pe\n" +
                    "inner join pedido_has_producto ph on pe.idPedido=ph.idPedido\n" +
                    "inner join producto pr on ph.idProducto=pr.idProducto\n" +
                    "where pe.idPedido=?;";

            ArrayList<ProductoCantDto> listProdCant = new ArrayList<>();

            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql2);) {
                pstmt.setInt(1, idPedido);
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

            detallesPedidoDto.setListaProductCant(listProdCant);
        }

        return detallesPedidoDto;
    }

    public ArrayList<PedidoHasProductoBean> obtenerDetallesPedido(String codigoPedido){

        ArrayList<PedidoHasProductoBean> listaDetalles = new ArrayList<>();

        String sql1 = "select * from pedido_has_producto where idPedido=?;";
        PedidoBean pedido = obtenerPedido(codigoPedido);

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql1);) {
            pstmt.setInt(1, pedido.getId());
            try(ResultSet rs = pstmt.executeQuery()){
                while(rs.next()){
                    PedidoHasProductoBean php = new PedidoHasProductoBean();

                    php.setPedido(pedido);

                    ProductoBean producto = obtenerProducto(rs.getInt("idProducto"));
                    php.setProducto(producto);

                    php.setCantidad(rs.getInt("cantidad"));
                    php.setPrecioUnitario(rs.getBigDecimal("precioUnitario"));

                    listaDetalles.add(php);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return listaDetalles;
    }

    public String obtenerFechaMax(String codigoPedido){

        String sql1 = "SELECT DATE_SUB(fecha_recojo, INTERVAL 1 HOUR) FROM pedido WHERE codigo=?;";
        String fecha = "";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql1);) {
            pstmt.setString(1, codigoPedido);
            try(ResultSet rs = pstmt.executeQuery()){
                rs.next();
                fecha = rs.getString(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return fecha;
    }

    public void actualizarTotalApagar(){
        //obtengo pedidos:
        String sql1 = "SELECT * FROM pedido;";
        ArrayList<PedidoBean> listaPedidos = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql1);
             ResultSet rs = pstmt.executeQuery()) {
                while(rs.next()){
                    PedidoBean pedido = new PedidoBean();
                    pedido.setCodigo(rs.getString("codigo"));
                    listaPedidos.add(pedido);
                }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        for(PedidoBean pedido : listaPedidos){
            String sql2 = "select sum(ph.cantidad*pr.precioUnitario) as `Costo Total`\n" +
                    "from pedido pe\n" +
                    "inner join pedido_has_producto ph on pe.idPedido=ph.idPedido\n" +
                    "inner join producto pr on ph.idProducto=pr.idProducto\n" +
                    "where pe.codigo=?;";

            BigDecimal costo = BigDecimal.valueOf(-1);
            try (Connection conn1 = getConnection();
                 PreparedStatement pstmt = conn1.prepareStatement(sql2);) {
                pstmt.setString(1, pedido.getCodigo());
                try(ResultSet rs = pstmt.executeQuery()){
                    rs.next();
                    costo=rs.getBigDecimal(1);
                }
            } catch (SQLException throwables1) {
                throwables1.printStackTrace();
            }

            String sql3 = "UPDATE pedido SET totalApagar = ? WHERE codigo = ?;";
            try (Connection conn2 = getConnection();
                 PreparedStatement pstmt = conn2.prepareStatement(sql3);) {
                pstmt.setBigDecimal(1, costo);
                pstmt.setString(2, pedido.getCodigo());
                pstmt.executeUpdate();
            } catch (SQLException throwables2) {
                throwables2.printStackTrace();
            }

        }

    }

    public int calcularCantPagListarProductos(){


        String sql = "select ceil(count(p.idProducto)/8) \n" +
                "from producto p\n" +
                "inner join bodega b on p.idBodega=b.idBodega where p.estado <> 'Eliminado';";

        int cantPag = 0;
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql);) {

            rs.next();
            cantPag = rs.getInt(1);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cantPag;
    }

    public ArrayList<ProductosClienteDTO> listarProductos(int pag) {

        ArrayList<ProductosClienteDTO> listaProductos = new ArrayList<>();

        int cantPag = 8;
        String sql = "select p.idProducto, p.foto, p.nombreProducto, p.precioUnitario, b.nombreBodega\n" +
                "from producto p\n" +
                "inner join bodega b on p.idBodega =b.idBodega where p.estado <> 'Eliminado' \n" +
                "order by p.idBodega, p.nombreProducto limit ?,?;";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, (pag-1)*cantPag);
            pstmt.setInt(2, cantPag);

            try (ResultSet rs = pstmt.executeQuery();) {
                while (rs.next()) {
                    ProductosClienteDTO producto = new ProductosClienteDTO();
                    producto.setIdProducto(rs.getInt(1));
                    //producto.setFoto((InputStream) rs.getBlob("foto"));
                    producto.setNombreProducto(rs.getString("nombreProducto"));
                    producto.setPrecio(rs.getBigDecimal("precioUnitario"));
                    producto.setBodega(rs.getString("nombreBodega"));

                    listaProductos.add(producto);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return listaProductos;
    }

    public PedidoBean obtenerPedido(String codigo){
        PedidoBean pedido = null;
        String sql = "SELECT * FROM pedido WHERE codigo=?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, codigo);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    pedido = new PedidoBean();
                    pedido.setId(rs.getInt("idPedido"));
                    pedido.setCodigo(rs.getString("codigo"));
                    pedido.setEstado(rs.getString("estado"));
                    pedido.setFecha_registro(rs.getString("fecha_registro"));
                    pedido.setFecha_recojo(rs.getString("fecha_recojo"));
                    pedido.setTotalApagar(rs.getBigDecimal("totalApagar"));

                    BodegaBean bodega = obtenerBodega(rs.getInt("idBodega"));
                    pedido.setBodegaBean(bodega);

                    UsuarioBean usuario = new UsuarioBean();
                    usuario.setIdUsuario(rs.getInt("idUsuario"));
                    pedido.setUsuario(usuario);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return pedido;
    }

    public void cancelarPedido(String codigo){

        String sql = "update pedido set estado= \"Cancelado\" where codigo=?;";

        //cancelamos pedido
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, codigo);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //actualizamos el stock
        ArrayList<PedidoHasProductoBean> detallesPedido = obtenerDetallesPedido(codigo);

        for (PedidoHasProductoBean php : detallesPedido){
            //Actualizo el stock:
            ProductoBean producto = php.getProducto();
            int newStock = producto.getStock() + php.getCantidad();
            String sql1 = "update producto set stock = ?\n" +
                    "where idProducto = ?;";

            try (Connection conn1 = getConnection();
                 PreparedStatement pstmt1 = conn1.prepareStatement(sql1);) {
                pstmt1.setInt(1, newStock);
                pstmt1.setInt(2, producto.getId());
                pstmt1.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

    public boolean verificarHoraPedido(String codigo){
        boolean aTiempo = true;

        String sql;

        sql = "select (TIMEDIFF(fecha_recojo, now())) from pedido where codigo=?;";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, codigo);
            try(ResultSet rs = pstmt.executeQuery();){
                rs.next();
                String tiempo = rs.getString(1);
                String[] array = tiempo.split(":");
                if(Integer.parseInt(array[0])==0){   //Si rs < 60 minutos -> aTiempo = false
                    aTiempo=false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return aTiempo;
    }


}
