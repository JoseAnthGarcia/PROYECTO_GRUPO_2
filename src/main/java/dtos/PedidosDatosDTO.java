package dtos;

import java.math.BigDecimal;
import java.util.ArrayList;

public class PedidosDatosDTO {
    private String codigo;
    private String nombreBodega;
    private String fecha_registro;
    private String fecha_recojo;
    private String fecha_limite;
    private int unidades;
    private BigDecimal costo_total;
    private ArrayList<ProductoCantDto> listaProductCant;

    public ArrayList<ProductoCantDto> getListaProductCant() {
        return listaProductCant;
    }

    public void setListaProductCant(ArrayList<ProductoCantDto> listaProductCant) {
        this.listaProductCant = listaProductCant;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombreBodega() {
        return nombreBodega;
    }

    public void setNombreBodega(String nombreBodega) {
        this.nombreBodega = nombreBodega;
    }

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

    public String getFecha_limite() {
        return fecha_limite;
    }

    public void setFecha_limite(String fecha_limite) {
        this.fecha_limite = fecha_limite;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    public BigDecimal getCosto_total() {
        return costo_total;
    }

    public void setCosto_total(BigDecimal costo_total) {
        this.costo_total = costo_total;
    }
}
