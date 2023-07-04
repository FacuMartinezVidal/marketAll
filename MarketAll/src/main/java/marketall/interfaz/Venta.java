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


    //Constructor para tarjeta de credito
    public Venta(Producto producto, String medioPago, int cantidad, int cuotas,int codigoVenta) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.medioPago = medioPago;
        this.codigoVenta = codigoVenta;

        //solo se permite ingresar cienta cantidad de cuotas
        if ((medioPago.toUpperCase().equals("CREDITO"))) {
            if ((cuotas == 2 || cuotas == 3 || cuotas ==6)){
                this.cuotas = cuotas;
            } else{
                throw new IllegalArgumentException("Cantidad de cuotas invalidas");
            }
        } else if ((medioPago.toUpperCase().equals("DEBITO")) || (medioPago.toUpperCase().equals("EFECTIVO")) ){
            if (cuotas == 1){
                this.cuotas = 1;
            }else{
                throw new IllegalArgumentException("Cantidad de cuotas invalidas");
            }
        }else {
            throw new IllegalArgumentException("Metodo de pago invalido");
        }


    }




    public void procesarVenta() {
        if (producto.getCantidadEnStock() > 0 && cantidad < producto.getCantidadEnStock()) {
            double montoTotal = (producto.getPrecioUnitario() * cantidad);

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

                ventas.add(producto);
            }

            // Realizar el registro de la venta y otros procesamientos necesarios
            System.out.println("Venta procesada exitosamente.");
            System.out.println("Monto total: " + montoTotal);
        } else {
            throw new IllegalArgumentException("No hay stock suficiente");
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
        return "-Codigo de Venta: "+codigoVenta + producto + " | Medio de pago: " + medioPago + " | Cant Vendida: " + cantidad + " | Cantidad de cutoas: " + cuotas;
    }
    public static int generarCodigoVenta() {
        ultimoCodigoVenta++;
        return ultimoCodigoVenta;
    }



}