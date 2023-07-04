package marketall.interfaz;

import java.util.ArrayList;
import java.util.List;

public class CatalogoProductos {
    private List<Producto> productos;

    //constructor
    public CatalogoProductos() {
        this.productos = new ArrayList<>();
    }

    //Manejo de catalogo (ABM)
    public void agregarProducto(Producto producto) {
        for (Producto p : productos) {
            if (p.getCodigo() == producto.getCodigo()) {
                throw new IllegalArgumentException("El código de producto ya está en uso");
            }
        }

        productos.add(producto);
    }
    public Producto obtenerProducto(int codigo) {
        if (productos.isEmpty() || codigo < 1 || codigo > productos.size()) {
            return null; // Retorna null si la lista está vacía o el código está fuera de rango
        }

        int indice = codigo - 1;
        Producto productoActual = productos.get(indice);
        return productoActual;

    }
    public boolean eliminarProducto(int codigo) {
        for (int i = 0; i < productos.size(); i++) {
            Producto producto = productos.get(i);
            if (producto.getCodigo() == codigo) {
                productos.remove(i);
                return true;
            }else{
                return false;
            }
        }
        return false;
    }
    public List<Producto> obtenerProductos() {
        return productos;
    }
}
