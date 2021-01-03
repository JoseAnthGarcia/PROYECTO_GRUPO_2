package beans;

import java.io.InputStream;

public class
BodegaBean {


    private InputStream foto;
    private  int idBodega;
    private long rucBodega;
    private String nombreBodega;
    private String estadoBodega;
    private String direccionBodega;
    private String correoBodega;
    private int idDistrito;
    private DistritoBean distrito;
    private int idAdministrador;

    public DistritoBean getDistrito() {
        return distrito;
    }

    public void setDistrito(DistritoBean distrito) {
        this.distrito = distrito;
    }

    public long getRucBodega() {
        return rucBodega;
    }

    public void setRucBodega(long rucBodega) {
        this.rucBodega = rucBodega;
    }

    public String getNombreBodega() {
        return nombreBodega;
    }

    public void setNombreBodega(String nombreBodega) {
        this.nombreBodega = nombreBodega;
    }

    public String getEstadoBodega() {
        return estadoBodega;
    }

    public void setEstadoBodega(String estadoBodega) {
        this.estadoBodega = estadoBodega;
    }

    public String getDireccionBodega() {
        return direccionBodega;
    }

    public void setDireccionBodega(String direccionBodega) {
        this.direccionBodega = direccionBodega;
    }

    public String getCorreoBodega() {
        return correoBodega;
    }

    public void setCorreoBodega(String correoBodega) {
        this.correoBodega = correoBodega;
    }


    public int getIdDistrito() {
        return idDistrito;
    }

    public void setIdDistrito(int idDistrito) {
        this.idDistrito = idDistrito;
    }

    public int getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(int idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    public int getIdBodega() {
        return idBodega;
    }

    public void setIdBodega(int idBodega) {
        this.idBodega = idBodega;
    }

    public InputStream getFoto() { return foto; }

    public void setFoto(InputStream foto) { this.foto = foto; }
}
