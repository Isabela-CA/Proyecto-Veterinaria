package com.happyfeet.View;

import com.happyfeet.Controller.InventarioController;
import com.happyfeet.Service.InventarioService;
import com.happyfeet.model.entities.Inventario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

    public class InventarioView {

        private final InventarioController inventarioController;
        private final Scanner scanner = new Scanner(System.in);
        private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        public InventarioView(InventarioController inventarioController) {
            this.inventarioController = inventarioController;
        }

        public void mostrarMenufarmaciainventario() {
            System.out.println("""
                    ╔═══════════════════════════════════════════════════════╗
                    ║         GESTIÓN DE INVENTARIO Y FARMACIA              ║
                    ╠═══════════════════════════════════════════════════════╣
                    ║  1. Registrar Producto                                ║
                    ║  2. Listar Todos los Productos                        ║
                    ║  3. Buscar Producto por ID                            ║
                    ║  4. Buscar por Nombre                                 ║
                    ║  5. Actualizar Producto                               ║
                    ║  6. Eliminar/Desactivar Producto                      ║
                    ║  ─────────────────────────────────────────────────    ║
                    ║  7. Registrar Entrada de Stock                        ║
                    ║  8. Registrar Salida de Stock                         ║
                    ║  9. Actualizar Stock Manualmente                      ║
                    ║  ─────────────────────────────────────────────────    ║
                    ║  10. Alertas de Stock Bajo                            ║
                    ║  11. Productos Vencidos                               ║
                    ║  12. Próximos a Vencer                                ║
                    ║  13. Productos que Requieren Receta                   ║
                    ║  ─────────────────────────────────────────────────    ║
                    ║  14. Generar Resumen de Inventario                    ║
                    ║  0. Volver al menú principal                          ║
                    ╚═══════════════════════════════════════════════════════╝
                    """);

            try {
                System.out.print("Seleccione una opción: ");
                int opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1 -> registrarProducto();
                    case 2 -> listarTodosLosProductos();
                    case 3 -> buscarProductoPorId();
                    case 4 -> buscarPorNombre();
                    case 5 -> actualizarProducto();
                    case 6 -> eliminarProducto();
                    case 7 -> registrarEntrada();
                    case 8 -> registrarSalida();
                    case 9 -> actualizarStockManual();
                    case 10 -> mostrarAlertasStock();
                    case 11 -> mostrarProductosVencidos();
                    case 12 -> mostrarProximosAVencer();
                    case 13 -> mostrarProductosConReceta();
                    case 14 -> generarResumen();
                    case 0 -> System.out.println("Volviendo...");
                    default -> System.out.println("Opción no válida");
                }
            } catch (Exception e) {
                System.out.println("Error: Ingrese un número válido");
                scanner.nextLine();
            }
        }

        private void registrarProducto() {
            System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
            System.out.println("║              REGISTRAR NUEVO PRODUCTO                         ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");

            try {
                Inventario producto = new Inventario();

                // Paso 1: Información básica
                System.out.println("┌─── PASO 1: Información Básica ───────────────────────────────┐");
                System.out.print("│ Nombre del producto: ");
                producto.setNombre_producto(scanner.nextLine());

                System.out.print("│ ID del tipo de producto: ");
                producto.setProducto_tipo_id(scanner.nextInt());
                scanner.nextLine();

                System.out.print("│ Descripción (opcional): ");
                String descripcion = scanner.nextLine().trim();
                if (!descripcion.isEmpty()) {
                    producto.setDescripcion(descripcion);
                }

                System.out.print("│ Fabricante (opcional): ");
                String fabricante = scanner.nextLine().trim();
                if (!fabricante.isEmpty()) {
                    producto.setFabricante(fabricante);
                }
                System.out.println("└───────────────────────────────────────────────────────────────┘\n");

                // Paso 2: Proveedor y lote
                System.out.println("┌─── PASO 2: Proveedor y Lote ─────────────────────────────────┐");
                System.out.print("│ ID del proveedor (0 para omitir): ");
                int proveedorId = scanner.nextInt();
                scanner.nextLine();
                if (proveedorId > 0) {
                    producto.setProveedor_id(proveedorId);
                }

                System.out.print("│ Lote (opcional): ");
                String lote = scanner.nextLine().trim();
                if (!lote.isEmpty()) {
                    producto.setLote(lote);
                }
                System.out.println("└───────────────────────────────────────────────────────────────┘\n");

                // Paso 3: Stock
                System.out.println("┌─── PASO 3: Gestión de Stock ─────────────────────────────────┐");
                System.out.print("│ Cantidad inicial en stock: ");
                producto.setCantidad_stock(scanner.nextInt());

                System.out.print("│ Stock mínimo (alerta): ");
                producto.setStock_minimo(scanner.nextInt());
                scanner.nextLine();

                // Mostrar unidades disponibles
                mostrarUnidadesMedida();
                System.out.print("│ Unidad de medida (Enter para 'unidad'): ");
                String unidad = scanner.nextLine().trim();
                producto.setUnidad_medida(unidad.isEmpty() ? "unidad" : unidad);
                System.out.println("└───────────────────────────────────────────────────────────────┘\n");

                // Paso 4: Fecha de vencimiento
                System.out.println("┌─── PASO 4: Fecha de Vencimiento ─────────────────────────────┐");
                System.out.print("│ Fecha de vencimiento (yyyy-MM-dd) o [Enter] para omitir: ");
                String fechaStr = scanner.nextLine().trim();
                if (!fechaStr.isEmpty()) {
                    try {
                        producto.setFecha_vencimiento(LocalDate.parse(fechaStr, dateFormatter));
                    } catch (DateTimeParseException e) {
                        System.out.println("│ ⚠ Formato inválido. Sin fecha de vencimiento");
                    }
                }
                System.out.println("└───────────────────────────────────────────────────────────────┘\n");

                // Paso 5: Precios
                System.out.println("┌─── PASO 5: Precios ──────────────────────────────────────────┐");
                System.out.print("│ Precio de compra (opcional, 0 para omitir): ");
                double precioCompra = scanner.nextDouble();
                if (precioCompra > 0) {
                    producto.setPrecio_compra(BigDecimal.valueOf(precioCompra));
                }

                System.out.print("│ Precio de venta (obligatorio): ");
                double precioVenta = scanner.nextDouble();
                producto.setPrecio_venta(BigDecimal.valueOf(precioVenta));
                scanner.nextLine();
                System.out.println("└───────────────────────────────────────────────────────────────┘\n");

                // Paso 6: Configuración
                System.out.println("┌─── PASO 6: Configuración ────────────────────────────────────┐");
                System.out.print("│ ¿Requiere receta médica? (S/N): ");
                String requiereReceta = scanner.nextLine().trim().toUpperCase();
                producto.setRequiere_receta(requiereReceta.equals("S") || requiereReceta.equals("SI"));

                System.out.print("│ ¿Producto activo? (S/N, Enter para Sí): ");
                String activo = scanner.nextLine().trim().toUpperCase();
                producto.setActivo(activo.isEmpty() || activo.equals("S") || activo.equals("SI"));
                System.out.println("└───────────────────────────────────────────────────────────────┘\n");

                // Confirmación
                System.out.println("┌─── RESUMEN ──────────────────────────────────────────────────┐");
                System.out.println("│ Nombre: " + producto.getNombre_producto());
                System.out.println("│ Stock: " + producto.getCantidad_stock() + " " + producto.getUnidad_medida());
                System.out.println("│ Precio Venta: $" + producto.getPrecio_venta());
                System.out.println("│ Requiere Receta: " + (producto.isRequiere_receta() ? "Sí" : "No"));
                System.out.println("└───────────────────────────────────────────────────────────────┘");

                System.out.print("\n¿Confirmar registro? (S/N): ");
                String confirmacion = scanner.nextLine().trim().toUpperCase();

                if (confirmacion.equals("S") || confirmacion.equals("SI")) {
                    if (inventarioController.agregarProducto(producto)) {
                        System.out.println("\n✓ Producto registrado exitosamente\n");
                    } else {
                        System.out.println("\n✗ Error al registrar el producto\n");
                    }
                } else {
                    System.out.println("\n⊘ Registro cancelado\n");
                }

            } catch (Exception e) {
                System.out.println("\n✗ Error: " + e.getMessage());
                e.printStackTrace();
                scanner.nextLine();
            }
        }

        private void listarTodosLosProductos() {
            System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
            System.out.println("║                  TODOS LOS PRODUCTOS                          ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");

            List<Inventario> productos = inventarioController.listarTodosLosProductos();

            if (productos.isEmpty()) {
                System.out.println("⊘ No hay productos registrados\n");
            } else {
                System.out.println("Total: " + productos.size() + " productos\n");

                for (Inventario prod : productos) {
                    mostrarResumenProducto(prod);
                    System.out.println("─".repeat(65));
                }
            }
        }

        private void buscarProductoPorId() {
            System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
            System.out.println("║                BUSCAR PRODUCTO POR ID                         ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");

            try {
                System.out.print("ID del producto: ");
                int id = scanner.nextInt();

                Inventario producto = inventarioController.obtenerProducto(id);
                if (producto != null) {
                    System.out.println("\n✓ Producto encontrado:\n");
                    mostrarDetalleProducto(producto);
                } else {
                    System.out.println("\n✗ No se encontró producto con ID: " + id);
                }
            } catch (Exception e) {
                System.out.println("✗ Error: Ingrese un ID válido");
                scanner.nextLine();
            }
        }

        private void buscarPorNombre() {
            System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
            System.out.println("║                BUSCAR PRODUCTOS POR NOMBRE                    ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");

            try {
                scanner.nextLine();
                System.out.print("Nombre a buscar: ");
                String nombre = scanner.nextLine();

                List<Inventario> productos = inventarioController.buscarPorNombre(nombre);

                if (productos.isEmpty()) {
                    System.out.println("\n⊘ No se encontraron productos con ese nombre");
                } else {
                    System.out.println("\nTotal: " + productos.size() + " productos encontrados\n");
                    productos.forEach(prod -> {
                        mostrarResumenProducto(prod);
                        System.out.println("─".repeat(65));
                    });
                }
            } catch (Exception e) {
                System.out.println("✗ Error: " + e.getMessage());
            }
        }

        private void actualizarProducto() {
            System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
            System.out.println("║                  ACTUALIZAR PRODUCTO                          ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");

            try {
                System.out.print("ID del producto a actualizar: ");
                int id = scanner.nextInt();
                scanner.nextLine();

                Inventario producto = inventarioController.obtenerProducto(id);
                if (producto == null) {
                    System.out.println("\n✗ Producto no encontrado");
                    return;
                }

                System.out.println("\nProducto actual:");
                mostrarDetalleProducto(producto);

                System.out.println("\n┌─── ACTUALIZAR (Enter para mantener) ─────────────────────────┐");

                System.out.println("│ Nombre actual: " + producto.getNombre_producto());
                System.out.print("│ Nuevo nombre: ");
                String nuevoNombre = scanner.nextLine();
                if (!nuevoNombre.trim().isEmpty()) {
                    producto.setNombre_producto(nuevoNombre);
                }

                System.out.println("│ Precio venta actual: $" + producto.getPrecio_venta());
                System.out.print("│ Nuevo precio (0 para mantener): ");
                double nuevoPrecio = scanner.nextDouble();
                if (nuevoPrecio > 0) {
                    producto.setPrecio_venta(BigDecimal.valueOf(nuevoPrecio));
                }
                scanner.nextLine();

                System.out.println("│ Stock mínimo actual: " + producto.getStock_minimo());
                System.out.print("│ Nuevo stock mínimo (-1 para mantener): ");
                int nuevoStockMin = scanner.nextInt();
                if (nuevoStockMin >= 0) {
                    producto.setStock_minimo(nuevoStockMin);
                }
                scanner.nextLine();

                System.out.println("└───────────────────────────────────────────────────────────────┘");

                if (inventarioController.actualizarProducto(producto)) {
                    System.out.println("\n✓ Producto actualizado correctamente");
                } else {
                    System.out.println("\n✗ Error al actualizar el producto");
                }

            } catch (Exception e) {
                System.out.println("✗ Error: " + e.getMessage());
                scanner.nextLine();
            }
        }

        private void eliminarProducto() {
            System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
            System.out.println("║              ELIMINAR/DESACTIVAR PRODUCTO                     ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");

            try {
                System.out.print("ID del producto: ");
                int id = scanner.nextInt();
                scanner.nextLine();

                Inventario producto = inventarioController.obtenerProducto(id);
                if (producto == null) {
                    System.out.println("\n✗ Producto no encontrado");
                    return;
                }

                mostrarDetalleProducto(producto);

                System.out.println("\n1. Desactivar (recomendado)");
                System.out.println("2. Eliminar permanentemente");
                System.out.print("Seleccione opción: ");
                int opcion = scanner.nextInt();
                scanner.nextLine();

                System.out.print("\n¿Confirmar? (S/N): ");
                String confirmacion = scanner.nextLine().trim().toUpperCase();

                if (confirmacion.equals("S") || confirmacion.equals("SI")) {
                    boolean exito = false;
                    if (opcion == 1) {
                        exito = inventarioController.desactivarProducto(id);
                        if (exito) {
                            System.out.println("\n✓ Producto desactivado correctamente");
                        }
                    } else if (opcion == 2) {
                        exito = inventarioController.eliminarProducto(id);
                        if (exito) {
                            System.out.println("\n✓ Producto eliminado permanentemente");
                        }
                    }

                    if (!exito) {
                        System.out.println("\n✗ Error en la operación");
                    }
                } else {
                    System.out.println("\n⊘ Operación cancelada");
                }

            } catch (Exception e) {
                System.out.println("✗ Error: " + e.getMessage());
                scanner.nextLine();
            }
        }

        private void registrarEntrada() {
            System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
            System.out.println("║                  REGISTRAR ENTRADA DE STOCK                   ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");

            try {
                System.out.print("ID del producto: ");
                int id = scanner.nextInt();

                Inventario producto = inventarioController.obtenerProducto(id);
                if (producto == null) {
                    System.out.println("\n✗ Producto no encontrado");
                    return;
                }

                System.out.println("\nProducto: " + producto.getNombre_producto());
                System.out.println("Stock actual: " + producto.getCantidad_stock() + " " + producto.getUnidad_medida());

                System.out.print("\nCantidad a ingresar: ");
                int cantidad = scanner.nextInt();

                if (inventarioController.registrarEntrada(id, cantidad)) {
                    int nuevoStock = producto.getCantidad_stock() + cantidad;
                    System.out.println("\n✓ Entrada registrada exitosamente");
                    System.out.println("Nuevo stock: " + nuevoStock + " " + producto.getUnidad_medida());
                } else {
                    System.out.println("\n✗ Error al registrar entrada");
                }

            } catch (Exception e) {
                System.out.println("✗ Error: " + e.getMessage());
                scanner.nextLine();
            }
        }

        private void registrarSalida() {
            System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
            System.out.println("║                   REGISTRAR SALIDA DE STOCK                   ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");

            try {
                System.out.print("ID del producto: ");
                int id = scanner.nextInt();

                Inventario producto = inventarioController.obtenerProducto(id);
                if (producto == null) {
                    System.out.println("\n✗ Producto no encontrado");
                    return;
                }

                System.out.println("\nProducto: " + producto.getNombre_producto());
                System.out.println("Stock actual: " + producto.getCantidad_stock() + " " + producto.getUnidad_medida());

                System.out.print("\nCantidad a descontar: ");
                int cantidad = scanner.nextInt();

                if (inventarioController.registrarSalida(id, cantidad)) {
                    int nuevoStock = producto.getCantidad_stock() - cantidad;
                    System.out.println("\n✓ Salida registrada exitosamente");
                    System.out.println("Nuevo stock: " + nuevoStock + " " + producto.getUnidad_medida());

                    if (nuevoStock <= producto.getStock_minimo()) {
                        System.out.println("⚠ ALERTA: Stock bajo el mínimo!");
                    }
                } else {
                    System.out.println("\n✗ Error al registrar salida");
                }

            } catch (Exception e) {
                System.out.println("✗ Error: " + e.getMessage());
                scanner.nextLine();
            }
        }

        private void actualizarStockManual() {
            System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
            System.out.println("║                ACTUALIZAR STOCK MANUALMENTE                   ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");

            try {
                System.out.print("ID del producto: ");
                int id = scanner.nextInt();

                Inventario producto = inventarioController.obtenerProducto(id);
                if (producto == null) {
                    System.out.println("\n✗ Producto no encontrado");
                    return;
                }

                System.out.println("\nProducto: " + producto.getNombre_producto());
                System.out.println("Stock actual: " + producto.getCantidad_stock() + " " + producto.getUnidad_medida());

                System.out.print("\nNueva cantidad: ");
                int nuevaCantidad = scanner.nextInt();

                System.out.print("¿Confirmar actualización? (S/N): ");
                scanner.nextLine();
                String confirmacion = scanner.nextLine().trim().toUpperCase();

                if (confirmacion.equals("S") || confirmacion.equals("SI")) {
                    if (inventarioController.actualizarStock(id, nuevaCantidad)) {
                        System.out.println("\n✓ Stock actualizado correctamente");
                        System.out.println("Nuevo stock: " + nuevaCantidad + " " + producto.getUnidad_medida());
                    } else {
                        System.out.println("\n✗ Error al actualizar stock");
                    }
                } else {
                    System.out.println("\n⊘ Actualización cancelada");
                }

            } catch (Exception e) {
                System.out.println("✗ Error: " + e.getMessage());
                scanner.nextLine();
            }
        }

        private void mostrarAlertasStock() {
            System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
            System.out.println("║                    ALERTAS DE STOCK BAJO                      ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");

            List<Inventario> productos = inventarioController.obtenerAlertasStock();

            if (productos.isEmpty()) {
                System.out.println("✓ No hay productos con stock bajo\n");
            } else {
                System.out.println("⚠ " + productos.size() + " productos con stock bajo:\n");

                for (Inventario prod : productos) {
                    System.out.printf("ID: %-4d | %-30s | Stock: %d/%d %s%n",
                            prod.getId(),
                            truncar(prod.getNombre_producto(), 30),
                            prod.getCantidad_stock(),
                            prod.getStock_minimo(),
                            prod.getUnidad_medida());
                }
                System.out.println();
            }
        }

        private void mostrarProductosVencidos() {
            System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
            System.out.println("║                      PRODUCTOS VENCIDOS                       ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");

            List<Inventario> productos = inventarioController.obtenerProductosVencidos();

            if (productos.isEmpty()) {
                System.out.println("✓ No hay productos vencidos\n");
            } else {
                System.out.println("⚠ " + productos.size() + " productos vencidos:\n");

                for (Inventario prod : productos) {
                    System.out.printf("ID: %-4d | %-30s | Vencido: %s | Stock: %d%n",
                            prod.getId(),
                            truncar(prod.getNombre_producto(), 30),
                            prod.getFecha_vencimiento(),
                            prod.getCantidad_stock());
                }
                System.out.println();
            }
        }

        private void mostrarProximosAVencer() {
            System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
            System.out.println("║                   PRÓXIMOS A VENCER                           ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");

            System.out.print("Días de anticipación (Enter para 30): ");
            String diasStr = scanner.nextLine().trim();
            int dias = diasStr.isEmpty() ? 30 : Integer.parseInt(diasStr);

            List<Inventario> productos = inventarioController.obtenerProductosProximosAVencer(dias);

            if (productos.isEmpty()) {
                System.out.println("✓ No hay productos próximos a vencer\n");
            } else {
                System.out.println("⚠ " + productos.size() + " productos próximos a vencer:\n");

                for (Inventario prod : productos) {
                    System.out.printf("ID: %-4d | %-30s | Vence: %s | Stock: %d%n",
                            prod.getId(),
                            truncar(prod.getNombre_producto(), 30),
                            prod.getFecha_vencimiento(),
                            prod.getCantidad_stock());
                }
                System.out.println();
            }
        }

        private void mostrarProductosConReceta() {
            System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
            System.out.println("║              PRODUCTOS QUE REQUIEREN RECETA                   ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");

            List<Inventario> productos = inventarioController.listarProductosQueRequierenReceta();

            if (productos.isEmpty()) {
                System.out.println("⊘ No hay productos que requieran receta\n");
            } else {
                System.out.println("Total: " + productos.size() + " productos\n");

                for (Inventario prod : productos) {
                    mostrarResumenProducto(prod);
                    System.out.println("─".repeat(65));
                }
            }
        }

        private void generarResumen() {
            System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
            System.out.println("║                  RESUMEN DE INVENTARIO                        ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");

            InventarioService.ResumenInventario resumen = inventarioController.generarResumen();

            if (resumen != null) {
                System.out.println(resumen.toString());
            } else {
                System.out.println("✗ Error al generar resumen");
            }
        }

        // ==================== MÉTODOS AUXILIARES ====================

        private void mostrarResumenProducto(Inventario prod) {
            String alerta = prod.tieneStockBajo() ? " ⚠" : "";
            String vencido = prod.estaVencido() ? " ⚠ VENCIDO" : "";

            System.out.printf("ID: %-4d | %-35s%s%s%n",
                    prod.getId(),
                    truncar(prod.getNombre_producto(), 35),
                    alerta,
                    vencido);
            System.out.printf("Stock: %d %s | Precio: $%.2f | %s%n",
                    prod.getCantidad_stock(),
                    prod.getUnidad_medida(),
                    prod.getPrecio_venta(),
                    prod.isActivo() ? "Activo" : "Inactivo");
        }

        private void mostrarDetalleProducto(Inventario prod) {
            System.out.println("┌───────────────────────────────────────────────────────────────┐");
            System.out.printf("│ ID: %-58d │%n", prod.getId());
            System.out.printf("│ Nombre: %-54s │%n", truncar(prod.getNombre_producto(), 54));
            System.out.printf("│ Tipo ID: %-53d │%n", prod.getProducto_tipo_id());
            if (prod.getDescripcion() != null) {
                System.out.printf("│ Descripción: %-49s │%n", truncar(prod.getDescripcion(), 49));
            }
            if (prod.getFabricante() != null) {
                System.out.printf("│ Fabricante: %-50s │%n", truncar(prod.getFabricante(), 50));
            }
            System.out.printf("│ Stock: %d %s (Mínimo: %d)%n",
                    prod.getCantidad_stock(),
                    prod.getUnidad_medida(),
                    prod.getStock_minimo());
            if (prod.getFecha_vencimiento() != null) {
                System.out.printf("│ Vencimiento: %-49s │%n", prod.getFecha_vencimiento());
            }
            if (prod.getPrecio_compra() != null) {
                System.out.printf("│ Precio Compra: $%-47.2f │%n", prod.getPrecio_compra());
            }
            System.out.printf("│ Precio Venta: $%-48.2f │%n", prod.getPrecio_venta());
            System.out.printf("│ Requiere Receta: %-45s │%n", prod.isRequiere_receta() ? "Sí" : "No");
            System.out.printf("│ Estado: %-54s │%n", prod.isActivo() ? "Activo" : "Inactivo");
            System.out.println("└───────────────────────────────────────────────────────────────┘");
        }

        private void mostrarUnidadesMedida() {
            String[] unidades = inventarioController.getUnidadesMedida();
            System.out.println("│ Unidades disponibles:");
            for (int i = 0; i < unidades.length; i++) {
                System.out.printf("│   %d. %-10s", i + 1, unidades[i]);
                if ((i + 1) % 3 == 0) System.out.println();
            }
            System.out.println();
        }

        private String truncar(String texto, int maxLength) {
            if (texto == null) return "N/A";
            if (texto.length() <= maxLength) return texto;
            return texto.substring(0, maxLength - 3) + "...";
        }
    }

