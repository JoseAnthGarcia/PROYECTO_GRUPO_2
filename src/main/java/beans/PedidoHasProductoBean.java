package beans;

import java.math.BigDecimal;

public class PedidoHasProductoBean {

    private PedidoBean pedido;
    private ProductoBean producto;
    private int cantidad;
    private BigDecimal precioUnitario;

    public PedidoBean getPedido() {
        return pedido;
    }

    public void setPedido(PedidoBean pedido) {
        this.pedido = pedido;
    }

    public ProductoBean getProducto() {
        return producto;
    }

    public void setProducto(ProductoBean producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
}
