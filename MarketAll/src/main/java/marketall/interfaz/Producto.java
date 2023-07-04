package marketall.interfaz;

public class Producto {
    private int codigo;
    private String descripcion;
    private double precioUnitario;
    private int cantidadEnStock;
    private int stockMinimo;

    //Constructor
    public Producto(int codigo, String descripcion, double precioUnitario, int cantidadEnStock, int stockMinimo) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precioUnitario = precioUnitario;
        this.cantidadEnStock = cantidadEnStock;
        this.stockMinimo = stockMinimo;
    }

    public Producto (){
        this(0,"Sin Ventas", 0.0, 0,0);
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    //getters and setters
    public int getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public int getCantidadEnStock() {
        return cantidadEnStock;
    }

    public void setCantidadEnStock(int cantidadEnStock) {
        this.cantidadEnStock = cantidadEnStock;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    @Override
    public String toString() {
        return "Producto codigo=" + codigo + ", descripcion=" + descripcion + ", precioUnitario=" + precioUnitario;
    }
}
