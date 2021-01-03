package beans;

import java.math.BigDecimal;

public class PedidoBean {

    private int id;
    private String codigo;
    private String estado;
    private String fecha_registro;
    private String fecha_recojo;
    private BigDecimal totalApagar;
    private BodegaBean bodegaBean;
    private UsuarioBean usuario;

    public String getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public String getFecha_recojo() {
        return fecha_recojo;
    }

    public void setFecha_recojo(String fecha_recojo) {
        this.fecha_recojo = fecha_recojo;
    }

    public BigDecimal getTotalApagar() {
        return totalApagar;
    }

    public void setTotalApagar(BigDecimal totalApagar) {
        this.totalApagar = totalApagar;
    }

    public BodegaBean getBodegaBean() {
        return bodegaBean;
    }

    public void setBodegaBean(BodegaBean bodegaBean) {
        this.bodegaBean = bodegaBean;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public UsuarioBean getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioBean usuario) {
        this.usuario = usuario;
    }
}
