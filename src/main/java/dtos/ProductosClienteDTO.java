package dtos;

import beans.BodegaBean;

import java.io.InputStream;
import java.math.BigDecimal;

public class ProductosClienteDTO {
    private int idProducto;
    private InputStream foto;
    private String nombreProducto;
    private BigDecimal precio;
    private BodegaBean bodega;

    public InputStream getFoto() {
        return foto;
    }

    public void setFoto(InputStream foto) {
        this.foto = foto;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BodegaBean getBodega() {
        return bodega;
    }

    public void setBodega(BodegaBean bodega) {
        this.bodega = bodega;
    }
}
