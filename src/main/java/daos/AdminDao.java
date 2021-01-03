package daos;

import beans.BodegaBean;
import beans.DistritoBean;
import beans.PedidoBean;
import beans.UsuarioBean;

import java.sql.*;
import java.util.ArrayList;

import static daos.BaseDao.getConnection;

public class AdminDao extends BaseDao{

    public UsuarioBean obtenerUsuario(int usuarioId) {


        String sql = "select  nombreUsuario, apellido \n" +
                "from usuario \n" +
                "where idUsuario=?;";
        UsuarioBean usuarioBean = new UsuarioBean();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, usuarioId);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    usuarioBean.setNombre(rs.getString("nombreUsuario"));
                    usuarioBean.setApellido(rs.getString("apellido"));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return usuarioBean;
    }


    public void guardarBodega(BodegaBean b){

        String sql = "INSERT INTO  bodega(ruc,direccion,idDistrito,nombreBodega,correo,idAdministrador,foto) " +
                "VALUES (?,?,?,?,?,?,?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setLong(1,b.getRucBodega());
            pstmt.setString(2,b.getDireccionBodega());
            pstmt.setInt(3,b.getIdDistrito());
            pstmt.setString(4,b.getNombreBodega());
            pstmt.setString(5,b.getCorreoBodega());
            pstmt.setInt(6,b.getIdAdministrador());
            pstmt.setBlob(7,b.getFoto());
            pstmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void contraHasheada(int usuarioID, String contraseniaNew) {
        String sql = "UPDATE bodega SET contraseniaHashed = sha2(?,256) WHERE idBodega = ?";


        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, contraseniaNew);
            pstmt.setInt(2, usuarioID);

            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public UsuarioBean validarUsuarioPassword(String user, String pass){

        String sql ="select * from usuario where correo = ? and contraseniaHashed = sha2(?,256) and rol='Administrador';";
        UsuarioBean administrador = null;
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1,user);
            pstmt.setString(2,pass);

            try(ResultSet rs = pstmt.executeQuery()){

                if(rs.next()){
                    int adminId = rs.getInt("idUsuario");
                    administrador = this.obetenerAdministrador(adminId);
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return administrador;
    }


    public UsuarioBean obetenerAdministrador(int idUsuario){
        UsuarioBean administrador = null;
        String sql = "select * from usuario where idUsuario=?;";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, idUsuario);
            try(ResultSet rs = pstmt.executeQuery()){
                rs.next();
                administrador = new UsuarioBean();
                administrador.setIdUsuario(rs.getInt("idUsuario"));
                administrador.setNombre(rs.getString("nombreUsuario"));
                administrador.setApellido(rs.getString("apellido"));
                administrador.setCorreo(rs.getString("correo"));

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return administrador;
    }


    public static int calcularCantPag(int idAdmin) {

        String sql = "select ceil(count(ruc)/5) from bodega where idAdministrador=?;";

        int cantPag = 0;
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);){

                 pstmt.setInt(1,idAdmin);
                 try(ResultSet rs = pstmt.executeQuery();){


                 rs.next();
                 cantPag = rs.getInt(1);
                 }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cantPag;
    }


    public ArrayList<BodegaBean> obtenerListaBodegas(int pagina,int idAdmin){

        ArrayList<BodegaBean> listaBodegas = new ArrayList<>();

        int limit = (pagina-1)*5;
        String sql = "select * from bodega where idAdministrador=? order by nombreBodega limit ?,5;";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1,idAdmin);
            pstmt.setInt(2, limit);

            try(ResultSet rs = pstmt.executeQuery();){
                while(rs.next()){
                    BodegaBean bodega = new BodegaBean();
                    bodega.setRucBodega(rs.getLong("ruc"));
                    bodega.setNombreBodega(rs.getString("nombreBodega"));
                    bodega.setEstadoBodega(rs.getString("estado"));

                    listaBodegas.add(bodega);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return listaBodegas;

    }
    public BodegaBean buscarBodega(int idBodega){
        BodegaBean bodega= new BodegaBean();
        String sql = "SELECT * FROM bodega WHERE idBodega = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1,idBodega);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    bodega.setNombreBodega(rs.getString("nombreBodega"));
                    bodega.setRucBodega(rs.getLong("ruc"));
                    bodega.setCorreoBodega(rs.getString("correo"));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return bodega;
    }

    public BodegaBean buscarBodega(String ruc){
        BodegaBean bodega= new BodegaBean();
        String sql = "select * from bodega b\n" +
                "inner join distrito d\n" +
                "on b.idDistrito = d.idDistrito \n" +
                "where b.ruc =?;";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1,ruc);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    bodega.setIdBodega(rs.getInt("idBodega"));
                    bodega.setNombreBodega(rs.getString("nombreBodega"));   //
                    bodega.setCorreoBodega(rs.getString("correo")); //
                    bodega.setEstadoBodega(rs.getString("estado"));

                    DistritoBean distritoBean = new DistritoBean();
                    distritoBean.setNombre(rs.getString("nombreDistrito")); //
                    bodega.setDistrito(distritoBean);

                    bodega.setRucBodega(rs.getLong("ruc"));  //
                    bodega.setDireccionBodega(rs.getString("direccion"));   //
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return bodega;
    }


    public static boolean pedidoPendiente(String nombreBodega){    //devuelve true si presenta al menos un pedido en estado pendiente
        boolean pedidoPendiente = false;

        String sql = "select estado from pedido where idBodega=(select idBodega from bodega where nombreBodega = ?);";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombreBodega);

            try (ResultSet rs = pstmt.executeQuery()) {

                while(rs.next()){
                    if(rs.getString(1).toLowerCase().equals("pendiente")){
                        pedidoPendiente = true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pedidoPendiente;
    }

    public static void actualizarEstadoBodega(String nombreBodega,boolean estado){

        String sql;

        //estado=true -> se bloquea la bodega
        sql = estado ? "update bodega set estado = \"Bloqueado\" where nombreBodega = ?;" : "update bodega set estado = \"Activo\" where nombreBodega = ?;";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombreBodega);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public ArrayList<DistritoBean> obtenerDistritos(){

        ArrayList<DistritoBean> listaDistritos = new ArrayList<>();

        String sql = "SELECT * FROM distrito;";
        try (Connection conn = getConnection();Statement stmt = conn.createStatement();
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

    public DistritoBean buscarDistrito(String idDistrito){

        DistritoBean distrito = null;

        String sql = "SELECT * FROM distrito WHERE idDistrito = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql); ) {
            pstmt.setInt(1,Integer.parseInt(idDistrito));

            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
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

    public boolean buscarRuc(String ruc){
        boolean encontrado = false;

        String sql = "SELECT * FROM bodega WHERE ruc = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1,ruc);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    encontrado = true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return encontrado;
    }
    public boolean buscarCorreo(String correo){
        boolean encontrado = false;

        String sql = "SELECT * FROM bodega WHERE correo = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1,correo);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    encontrado = true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return encontrado;
    }
    public int buscarIdBodega(String ruc){
        String sql = "SELECT idBodega FROM bodega WHERE ruc = ?";
        int idBodega=0;
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1,ruc);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    idBodega = rs.getInt(1);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return idBodega;
    }

    public ArrayList<PedidoBean> buscarBodegaconPedido(String nombreBodega){
        ArrayList<PedidoBean> lista = new ArrayList<>();

        String sql = "select * from bodega b\n" +
                "inner join pedido p\n" +
                "on b.idBodega = p.idBodega\n" +
                "where nombreBodega=?;";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombreBodega);

            try (ResultSet rs = pstmt.executeQuery()) {

                while(rs.next()){
                    PedidoBean pedido = new PedidoBean();
                    pedido.setCodigo(rs.getString("codigo"));
                    lista.add(pedido);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }


}
