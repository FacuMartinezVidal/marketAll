package marketall.interfaz;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {
    private CatalogoProductos catalogo;

    private Venta venta;
    private ListView<String> productList;
    private ListView<String> ventasList;

    @Override
    public void start(Stage primaryStage) {
        venta = new Venta();

        // Crear el catálogo de productos
        catalogo = new CatalogoProductos();


        // Crear contenedor principal y configuración
        VBox root = new VBox();
        root.setPadding(new Insets(10));
        root.setSpacing(10);

        // Crear lista de productos
        productList = new ListView<>();
        updateProductList();

        // Crear formulario para agregar productos
        TextField codigoField = new TextField();
        codigoField.setPromptText("Código");
        TextField descripcionField = new TextField();
        descripcionField.setPromptText("Descripción");
        TextField precioField = new TextField();
        precioField.setPromptText("Precio Unitario");
        TextField stockField = new TextField();
        stockField.setPromptText("Cantidad en Stock");
        TextField stockMinField = new TextField();
        stockMinField.setPromptText("Stock Mínimo");

        Button agregarButton = new Button("Agregar");
        agregarButton.setOnAction(event -> {
            try{
                // Obtener valores del formulario
                int codigo = Integer.parseInt(codigoField.getText());
                String descripcion = descripcionField.getText();
                double precio = Double.parseDouble(precioField.getText());
                int stock = Integer.parseInt(stockField.getText());
                int stockMin = Integer.parseInt(stockMinField.getText());

                // Crear nuevo producto
                Producto producto = new Producto(codigo, descripcion, precio, stock, stockMin);

                // Agregar producto al catálogo
                catalogo.agregarProducto(producto);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Éxito");
                alert.setHeaderText(null);
                alert.setContentText("Producto agregado correctamente");
                alert.showAndWait();

                // Actualizar lista de productos
                updateProductList();

                // Limpiar formulario
                codigoField.clear();
                descripcionField.clear();
                precioField.clear();
                stockField.clear();
                stockMinField.clear();
            }catch (IllegalArgumentException ex){
                // Mostrar mensaje de error en la interfaz
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }

        });

        // Crear formulario para modificar productos
        TextField codigoModificarField = new TextField();
        codigoModificarField.setPromptText("Código");
        TextField nuevaDescripcionField = new TextField();
        nuevaDescripcionField.setPromptText("Nueva descripción");
        TextField nuevoPrecioField = new TextField();
        nuevoPrecioField.setPromptText("Nuevo precio");
        TextField nuevoStockField = new TextField();
        nuevoStockField.setPromptText("Nuevo stock");
        TextField nuevoStockMinField = new TextField();
        nuevoStockMinField.setPromptText("Nuevo stock mínimo");

        Button modificarButton = new Button("Modificar");
        modificarButton.setOnAction(event -> {
            // Obtener valores del formulario
            int codigo = Integer.parseInt(codigoModificarField.getText());
            String nuevaDescripcion = nuevaDescripcionField.getText();
            double nuevoPrecio = Double.parseDouble(nuevoPrecioField.getText());
            int nuevoStock = Integer.parseInt(nuevoStockField.getText());
            int nuevoStockMin = Integer.parseInt(nuevoStockMinField.getText());

            // Obtener el producto a modificar
            Producto producto = catalogo.obtenerProducto(codigo);

            if (producto != null) {
                // Modificar el producto
                producto.setDescripcion(nuevaDescripcion);
                producto.setPrecioUnitario(nuevoPrecio);
                producto.setCantidadEnStock(nuevoStock);
                producto.setStockMinimo(nuevoStockMin);

                // Actualizar lista de productos
                updateProductList();

                // Limpiar formulario
                codigoModificarField.clear();
                nuevaDescripcionField.clear();
                nuevoPrecioField.clear();
                nuevoStockField.clear();
                nuevoStockMinField.clear();
            } else {
                // Mostrar mensaje de error si no se encontró el producto
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Producto no encontrado");
                alert.setContentText("No se encontró un producto con el código especificado.");
                alert.showAndWait();
            }
        });

        // Crear formulario para eliminar productos
        TextField codigoEliminarField = new TextField();
        codigoEliminarField.setPromptText("Código");

        Button eliminarButton = new Button("Eliminar");
        eliminarButton.setOnAction(event -> {
            // Obtener código del producto a eliminar
            int codigo = Integer.parseInt(codigoEliminarField.getText());

            // Eliminar producto del catálogo
            boolean eliminado = catalogo.eliminarProducto(codigo);

            if (eliminado) {
                // Actualizar lista de productos
                updateProductList();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Éxito");
                alert.setHeaderText(null);
                alert.setContentText("Producto eliminado correctamente");
                alert.showAndWait();
                // Limpiar formulario
                codigoEliminarField.clear();
            } else {
                // Mostrar mensaje de error si no se encontró el producto
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Producto no encontrado");
                alert.setContentText("No se encontró un producto con el código especificado.");
                alert.showAndWait();
            }
        });

        // Crear botón para registrar venta
        Button registrarVentaButton = new Button("Registrar Venta");
        registrarVentaButton.setOnAction(event -> showRegistroVentaStage());

        // Crear lista de ventas realizadas
        ventasList = new ListView<>();
        ventasList.setPrefHeight(200);

        // Agregar componentes al contenedor principal
        root.getChildren().addAll(productList, codigoField, descripcionField, precioField,
                stockField, stockMinField, agregarButton, codigoModificarField, nuevaDescripcionField,
                nuevoPrecioField, nuevoStockField, nuevoStockMinField, modificarButton, codigoEliminarField,
                eliminarButton, ventasList, registrarVentaButton);

        // Crear escena y mostrar ventana principal
        Scene scene = new Scene(root, 1000, 1000);
        primaryStage.setScene(scene);
        primaryStage.setTitle("MarketAll");
        primaryStage.show();
    }

    private void updateProductList() {
        List<String> productNames = new ArrayList<>();
        for (Producto producto : catalogo.obtenerProductos()) {
            productNames.add("-Producto: " + producto.getDescripcion() + " | Codigo: " + producto.getCodigo() + " | Precio Unitario: " + producto.getPrecioUnitario() + " | Cant. Stock: " + producto.getCantidadEnStock() + " | Stock Minimo: " + producto.getStockMinimo());
        }
        productList.getItems().setAll(productNames);

        for (Producto producto : venta.getVentas()) {
            ventasList.getItems().add(venta.toString()); // Agregar la representación de la venta
        }



    }

    private void showRegistroVentaStage() {
        int codigoVenta = venta.generarCodigoVenta();
        Stage stage = new Stage();
        stage.setTitle("Registro de Venta");

        // Crear formulario para registrar una venta
        TextField codigoField = new TextField();
        codigoField.setPromptText("Código del producto");

        TextField medioPagoField = new TextField();
        medioPagoField.setPromptText("Medio de pago (Efectivo, Debito, Credito)");


        TextField cantidadField = new TextField();
        cantidadField.setPromptText("Cantidad de unidades vendidas");

        TextField cuotasField = new TextField();
        cuotasField.setPromptText("Cantidad de cuotas ");

        Button registrarButton = new Button("Registrar Venta");
        registrarButton.setOnAction(event -> {
            // Obtener valores del formulario
            int codigo = Integer.parseInt(codigoField.getText());
            String medioPago = medioPagoField.getText();
            int cantidad = Integer.parseInt(cantidadField.getText());
            int cuotas = 0;



            if (!cuotasField.getText().isEmpty()) {
                cuotas = Integer.parseInt(cuotasField.getText());
            }

            // Buscar producto por código
            Producto producto = catalogo.obtenerProducto(codigo);

            if (producto != null) {
                try {
                    // Crear venta
                    Venta venta;
                    venta = new Venta(producto,medioPago,cantidad,cuotas,codigoVenta);


                    // Procesar venta
                    venta.procesarVenta();
                    updateProductList();

                    // Agregar venta a la lista de ventas realizadas
                    ventasList.getItems().add(venta.toString() + " | Monto Total: " + venta.getMontoTotal());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Éxito");
                    alert.setHeaderText(null);
                    alert.setContentText("Venta procesada correctamente");
                    alert.showAndWait();


                } catch (IllegalArgumentException e) {
                    // Mostrar mensaje de error si la cantidad de cuotas no es válida
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                } catch (IndexOutOfBoundsException e){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
            } else {
                // Mostrar mensaje de error si no se encontró el producto
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Producto no encontrado");
                alert.setContentText("No se encontró un producto con el código especificado.");
                alert.showAndWait();
                codigoField.clear();
            }

            // Limpiar formulario

            codigoField.clear();
            cantidadField.clear();
            medioPagoField.clear();
            cuotasField.clear();
        });

        // Crear contenedor y configuración
        VBox root = new VBox();
        root.setPadding(new Insets(10));
        root.setSpacing(10);
        root.getChildren().addAll(codigoField, medioPagoField,cantidadField, cuotasField, registrarButton);

        // Crear escena y mostrar ventana
        Scene scene = new Scene(root, 700, 200);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
