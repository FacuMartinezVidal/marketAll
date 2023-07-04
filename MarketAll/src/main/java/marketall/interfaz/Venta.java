package marketall.interfaz;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.ArrayList;

import java.util.List;

public class Venta {
    private Producto producto;
    private String medioPago;

    private static int ultimoCodigoVenta =0;

    private List<Producto> ventas;

    private int cuotas;

    private int codigoVenta;

    private int cantidad;

    public Venta(){
        this.ventas = new ArrayList<>();
    }
    public Venta(Producto producto, String medioPago, int cantidad) {
        this.producto = producto;
        this.medioPago = medioPago;
        this.cantidad = cantidad;
        this.cuotas =0;
    }
    public Venta(Producto producto, String medioPago, int cantidad, int cuotas, int codigoVenta) {
        this.producto = producto;
        this.medioPago = medioPago;
        this.cantidad = cantidad;
        this.codigoVenta = codigoVenta;
        this.cuotas = cuotas;
        if (medioPago.toUpperCase().equals("EFECTIVO") || medioPago.toUpperCase().equals("DEBITO")) {
            while (cuotas != 1){
                throw new IllegalArgumentException("Cantidad de cuotas invalidas");
            }
            this.cuotas=1;

        } else{
            throw new IllegalArgumentException("Las cantidad de cuotas ingresadas no son validas");
        }


        // Resto del código del constructor
    }
    //Constructor para tarjeta de credito
    public Venta(Producto producto, String medioPago, int cantidad, int cuotas) {
        this.producto = producto;
        this.medioPago = medioPago;
        this.cantidad = cantidad;
        //solo se permite ingresar cienta cantidad de cuotas
        while (medioPago.toUpperCase() != "CREDITO" || medioPago.toUpperCase() != "DEBITO" || medioPago.toUpperCase() != "EFECTIVO"){
            throw new IllegalArgumentException("Metodo de pago no valido");
        }
        if ( (cuotas == 2 || cuotas == 3 || cuotas ==6) && medioPago.toUpperCase().equals("CREDITO") ){
            this.cuotas = cuotas;
        } else if (medioPago.toUpperCase().equals("EFECTIVO") || medioPago.toUpperCase().equals("DEBITO")) {
            while (cuotas != 1){
                throw new IllegalArgumentException("Cantidad de cuotas invalidas");
            }
            this.cuotas=1;

        } else{
            throw new IllegalArgumentException("Las cantidad de cuotas ingresadas no son validas");
        }

    }


    public void procesarVenta() {
        if (producto.getCantidadEnStock() > 0) {
            double montoTotal = producto.getPrecioUnitario();

            if (medioPago.toUpperCase().equals("DEBITO")) {
                // No hay descuento, se cobra el valor total de la venta
            } else if (medioPago.toUpperCase().equals("EFECTIVO")) {
                //se resta el 10%, por utilizar efectivo
                montoTotal -= (montoTotal * 0.1);
            } else if (medioPago.toUpperCase().equals("CREDITO")) {
                if (cuotas == 2) {
                    montoTotal += (montoTotal * 0.06);
                } else if (cuotas == 3) {
                    montoTotal += (montoTotal * 0.12);
                } else if (cuotas == 6) {
                    montoTotal += (montoTotal * 0.20);
                }
            }

            setMontoTotal(montoTotal);

            producto.setCantidadEnStock(producto.getCantidadEnStock() - cantidad);
            if (producto.getStockMinimo() == producto.getCantidadEnStock()){
                System.out.println();
                ventas.add(producto);
            }

            // Realizar el registro de la venta y otros procesamientos necesarios
            System.out.println("Venta procesada exitosamente.");
            System.out.println("Monto total: " + montoTotal);
        } else {
            System.out.println("No hay stock suficiente del producto.");
        }

    }

    public List<Producto> getVentas() {
        if (ventas != null){
            return ventas;
        }else{
            Producto productoVacio = new Producto();
            ventas.add(productoVacio);
            return ventas;
        }
    }
    private DoubleProperty montoTotalProperty = new SimpleDoubleProperty();

    public DoubleProperty montoTotalProperty() {
        return montoTotalProperty;
    }

    public double getMontoTotal() {
        return montoTotalProperty.get();
    }

    private void setMontoTotal(double montoTotal) {
        montoTotalProperty.set(montoTotal);
    }
    @Override
    public String toString() {
        return "Codigo de Venta= "+codigoVenta+" Venta producto=" + producto + ", medioPago=" + medioPago;
    }
    public static int generarCodigoVenta() {
        ultimoCodigoVenta++;
        return ultimoCodigoVenta;
    }

}