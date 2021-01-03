package dtos;

import beans.PedidoBean;
import dtos.ProductoCantDto;

import java.util.ArrayList;

public class DetallesPedidoDto {
    private PedidoBean pedido;
    private String fechaLimitCancel;
    private ArrayList<ProductoCantDto> listaProductCant;

    public PedidoBean getPedido() {
        return pedido;
    }

    public void setPedido(PedidoBean pedido) {
        this.pedido = pedido;
    }

    public String getFechaLimitCancel() {
        return fechaLimitCancel;
    }

    public void setFechaLimitCancel(String fechaLimitCancel) {
        this.fechaLimitCancel = fechaLimitCancel;
    }

    public ArrayList<ProductoCantDto> getListaProductCant() {
        return listaProductCant;
    }

    public void setListaProductCant(ArrayList<ProductoCantDto> listaProductCant) {
        this.listaProductCant = listaProductCant;
    }
}
