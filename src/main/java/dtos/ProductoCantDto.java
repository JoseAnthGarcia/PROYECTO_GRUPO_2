package dtos;

import beans.ProductoBean;

public class ProductoCantDto {

    public ProductoBean producto;
    public int cant;

    public ProductoBean getProducto() {
        return producto;
    }

    public void setProducto(ProductoBean producto) {
        this.producto = producto;
    }

    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }
}
