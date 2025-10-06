package com.happyfeet.Service;

import com.happyfeet.Repository.Interfaz.IInventarioDAO;
import com.happyfeet.Repository.InventarioDAO;
import com.happyfeet.model.entities.Inventario;

import java.math.BigDecimal;
import java.util.List;

public class InventarioService {
    private final IInventarioDAO inventarioDAO;

    // Constantes
    public static final String[] UNIDADES_MEDIDA = {
            "unidad", "caja", "frasco", "tableta", "cápsula",
            "ml", "litro", "gramo", "kilogramo", "ampolla", "vial"
    };

    public static final int DIAS_ALERTA_VENCIMIENTO = 30;

    // Constructores
    public InventarioService() {
        this.inventarioDAO = new InventarioDAO();
    }

    public InventarioService(IInventarioDAO inventarioDAO) {
        this.inventarioDAO = inventarioDAO;
    }

    // ==================== OPERACIONES CRUD ====================

    public Inventario obtenerProducto(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID debe ser mayor a 0");
        }
        return inventarioDAO.buscarProductoPorId(id);
    }

    public List<Inventario> listarTodosLosProductos() {
        return inventarioDAO.listarProductos();
    }

    public List<Inventario> listarProductosActivos() {
        return inventarioDAO.listarProductosActivos();
    }

    public boolean agregarProducto(Inventario producto) {
        // Validaciones de negocio
        validarProducto(producto);

        // Validar que el precio de venta sea mayor que el de compra (si existe)
        if (producto.getPrecio_compra() != null &&
                producto.getPrecio_venta().compareTo(producto.getPrecio_compra()) <= 0) {
            throw new IllegalArgumentException("El precio de venta debe ser mayor al precio de compra");
        }

        return inventarioDAO.agregarProducto(producto);
    }

    public boolean actualizarProducto(Inventario producto) {
        if (producto.getId() <= 0) {
            throw new IllegalArgumentException("ID de producto inválido");
        }

        // Verificar que el producto exista
        Inventario productoExistente = inventarioDAO.buscarProductoPorId(producto.getId());
        if (productoExistente == null) {
            throw new IllegalArgumentException("El producto con ID " + producto.getId() + " no existe");
        }

        validarProducto(producto);
        return inventarioDAO.modificarProducto(producto);
    }

    public boolean eliminarProducto(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID debe ser mayor a 0");
        }

        Inventario producto = inventarioDAO.buscarProductoPorId(id);
        if (producto == null) {
            throw new IllegalArgumentException("El producto no existe");
        }

        return inventarioDAO.eliminarProducto(id);
    }

    public boolean desactivarProducto(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID debe ser mayor a 0");
        }

        Inventario producto = inventarioDAO.buscarProductoPorId(id);
        if (producto == null) {
            throw new IllegalArgumentException("El producto no existe");
        }

        producto.setActivo(false);
        return inventarioDAO.modificarProducto(producto);
    }

    // ==================== BÚSQUEDAS Y FILTROS ====================

    public List<Inventario> buscarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        return inventarioDAO.buscarPorNombre(nombre.trim());
    }

    public List<Inventario> listarPorTipo(int tipoId) {
        if (tipoId <= 0) {
            throw new IllegalArgumentException("ID de tipo inválido");
        }
        return inventarioDAO.listarPorTipo(tipoId);
    }

    public List<Inventario> listarPorProveedor(int proveedorId) {
        if (proveedorId <= 0) {
            throw new IllegalArgumentException("ID de proveedor inválido");
        }
        return inventarioDAO.listarPorProveedor(proveedorId);
    }

    public List<Inventario> listarProductosQueRequierenReceta() {
        return inventarioDAO.listarProductosQueRequierenReceta();
    }

    public List<Inventario> listarPorRangoPrecios(double precioMin, double precioMax) {
        if (precioMin < 0 || precioMax < 0) {
            throw new IllegalArgumentException("Los precios no pueden ser negativos");
        }
        if (precioMin > precioMax) {
            throw new IllegalArgumentException("El precio mínimo no puede ser mayor al máximo");
        }
        return inventarioDAO.listarPorRangoPrecios(precioMin, precioMax);
    }

    // ==================== GESTIÓN DE STOCK ====================

    public List<Inventario> obtenerAlertasStock() {
        return inventarioDAO.listarProductosConStockBajo();
    }

    public List<Inventario> obtenerProductosVencidos() {
        return inventarioDAO.listarProductosVencidos();
    }

    public List<Inventario> obtenerProductosProximosAVencer(int dias) {
        if (dias <= 0) {
            throw new IllegalArgumentException("Los días deben ser mayores a 0");
        }
        return inventarioDAO.listarProductosProximosAVencer(dias);
    }

    public boolean actualizarStock(int productoId, int nuevaCantidad) {
        if (productoId <= 0) {
            throw new IllegalArgumentException("ID de producto inválido");
        }
        if (nuevaCantidad < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        }

        Inventario producto = inventarioDAO.buscarProductoPorId(productoId);
        if (producto == null) {
            throw new IllegalArgumentException("El producto no existe");
        }

        return inventarioDAO.actualizarStock(productoId, nuevaCantidad);
    }

    public boolean registrarEntrada(int productoId, int cantidad) {
        if (productoId <= 0) {
            throw new IllegalArgumentException("ID de producto inválido");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }

        Inventario producto = inventarioDAO.buscarProductoPorId(productoId);
        if (producto == null) {
            throw new IllegalArgumentException("El producto no existe");
        }

        return inventarioDAO.ajustarStock(productoId, cantidad, true);
    }

    public boolean registrarSalida(int productoId, int cantidad) {
        if (productoId <= 0) {
            throw new IllegalArgumentException("ID de producto inválido");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }

        Inventario producto = inventarioDAO.buscarProductoPorId(productoId);
        if (producto == null) {
            throw new IllegalArgumentException("El producto no existe");
        }

        // Verificar que hay suficiente stock
        if (producto.getCantidad_stock() < cantidad) {
            throw new IllegalArgumentException(
                    "Stock insuficiente. Disponible: " + producto.getCantidad_stock() +
                            ", Requerido: " + cantidad
            );
        }

        return inventarioDAO.ajustarStock(productoId, cantidad, false);
    }

    public boolean verificarDisponibilidad(int productoId, int cantidadRequerida) {
        if (productoId <= 0 || cantidadRequerida <= 0) {
            return false;
        }

        Inventario producto = inventarioDAO.buscarProductoPorId(productoId);
        if (producto == null || !producto.isActivo()) {
            return false;
        }

        return producto.getCantidad_stock() >= cantidadRequerida;
    }

    // ==================== REPORTES ====================

    public ResumenInventario generarResumen() {
        List<Inventario> todosLosProductos = inventarioDAO.listarProductos();
        List<Inventario> productosActivos = inventarioDAO.listarProductosActivos();
        List<Inventario> stockBajo = inventarioDAO.listarProductosConStockBajo();
        List<Inventario> vencidos = inventarioDAO.listarProductosVencidos();
        List<Inventario> proximosVencer = inventarioDAO.listarProductosProximosAVencer(DIAS_ALERTA_VENCIMIENTO);

        BigDecimal valorTotalInventario = todosLosProductos.stream()
                .filter(p -> p.getPrecio_venta() != null)
                .map(p -> p.getPrecio_venta().multiply(BigDecimal.valueOf(p.getCantidad_stock())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new ResumenInventario(
                todosLosProductos.size(),
                productosActivos.size(),
                stockBajo.size(),
                vencidos.size(),
                proximosVencer.size(),
                valorTotalInventario
        );
    }

    // ==================== VALIDACIONES ====================

    private void validarProducto(Inventario producto) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }

        if (producto.getNombre_producto() == null || producto.getNombre_producto().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio");
        }

        if (producto.getProducto_tipo_id() <= 0) {
            throw new IllegalArgumentException("El tipo de producto es obligatorio");
        }

        if (producto.getCantidad_stock() < 0) {
            throw new IllegalArgumentException("La cantidad en stock no puede ser negativa");
        }

        if (producto.getStock_minimo() < 0) {
            throw new IllegalArgumentException("El stock mínimo no puede ser negativo");
        }

        if (producto.getUnidad_medida() == null || producto.getUnidad_medida().trim().isEmpty()) {
            throw new IllegalArgumentException("La unidad de medida es obligatoria");
        }

        if (producto.getPrecio_venta() == null || producto.getPrecio_venta().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio de venta debe ser mayor a 0");
        }
    }

    // ==================== CLASE INTERNA PARA RESUMEN ====================

    public static class ResumenInventario {
        private final int totalProductos;
        private final int productosActivos;
        private final int productosStockBajo;
        private final int productosVencidos;
        private final int productosProximosVencer;
        private final BigDecimal valorTotalInventario;

        public ResumenInventario(int totalProductos, int productosActivos, int productosStockBajo,
                                 int productosVencidos, int productosProximosVencer,
                                 BigDecimal valorTotalInventario) {
            this.totalProductos = totalProductos;
            this.productosActivos = productosActivos;
            this.productosStockBajo = productosStockBajo;
            this.productosVencidos = productosVencidos;
            this.productosProximosVencer = productosProximosVencer;
            this.valorTotalInventario = valorTotalInventario;
        }

        @Override
        public String toString() {
            return String.format("""
                ╔═══════════════════════════════════════════════════════════╗
                ║              RESUMEN DE INVENTARIO                        ║
                ╠═══════════════════════════════════════════════════════════╣
                ║ Total de productos:           %-27d ║
                ║ Productos activos:            %-27d ║
                ║ Productos inactivos:          %-27d ║
                ╠═══════════════════════════════════════════════════════════╣
                ║ Productos con stock bajo:     %-27d ║
                ║ Productos vencidos:           %-27d ║
                ║ Próximos a vencer (30 días):  %-27d ║
                ╠═══════════════════════════════════════════════════════════╣
                ║ Valor total del inventario:   $%-26.2f ║
                ╚═══════════════════════════════════════════════════════════╝
                """,
                    totalProductos,
                    productosActivos,
                    totalProductos - productosActivos,
                    productosStockBajo,
                    productosVencidos,
                    productosProximosVencer,
                    valorTotalInventario
            );
        }

        // Getters
        public int getTotalProductos() { return totalProductos; }
        public int getProductosActivos() { return productosActivos; }
        public int getProductosStockBajo() { return productosStockBajo; }
        public int getProductosVencidos() { return productosVencidos; }
        public int getProductosProximosVencer() { return productosProximosVencer; }
        public BigDecimal getValorTotalInventario() { return valorTotalInventario; }
    }
}