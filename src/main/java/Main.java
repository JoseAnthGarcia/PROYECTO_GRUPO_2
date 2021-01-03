import beans.BodegaBean;
import beans.ProductoBean;
import daos.BodegaDao;

public class Main {
    public static void main(String[] args) {

        for(ProductoBean productoBean : BodegaDao.listarProductoBodega(2,"", 26)){
            System.out.println(productoBean.getNombreProducto());
        }
    }
}
