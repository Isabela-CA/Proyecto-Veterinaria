package com.happyfeet.Repository.Interfaz;

import com.happyfeet.model.entities.Inventario;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface para el acceso a datos de Inventario
 */
public interface IInventarioDAO {

    // Operaciones CRUD básicas
    List<Inventario> listarProductos();
    Inventario buscarProductoPorId(int id);
    boolean agregarProducto(Inventario producto);
    boolean modificarProducto(Inventario producto);
    boolean eliminarProducto(int id);

    // Búsquedas específicas
    List<Inventario> buscarPorNombre(String nombre);
    List<Inventario> listarPorTipo(int productoTipoId);
    List<Inventario> listarPorProveedor(int proveedorId);
    List<Inventario> listarProductosActivos();

    // Gestión de stock
    List<Inventario> listarProductosConStockBajo();
    List<Inventario> listarProductosVencidos();
    List<Inventario> listarProductosProximosAVencer(int diasAnticipacion);
    boolean actualizarStock(int productoId, int nuevaCantidad);
    boolean ajustarStock(int productoId, int cantidad, boolean esEntrada);

    // Filtros especiales
    List<Inventario> listarProductosQueRequierenReceta();
    List<Inventario> listarPorRangoPrecios(double precioMin, double precioMax);
}