package com.happyfeet.Controller;


import com.happyfeet.Service.InventarioService;
import com.happyfeet.model.entities.Inventario;

import java.util.List;

public class InventarioController {

        private final InventarioService inventarioService;

        // Constructores
        public InventarioController() {
            this.inventarioService = new InventarioService();
        }

        public InventarioController(InventarioService inventarioService) {
            this.inventarioService = inventarioService;
        }

        // ==================== OPERACIONES CRUD ====================

        public boolean agregarProducto(Inventario producto) {
            try {
                return inventarioService.agregarProducto(producto);
            } catch (IllegalArgumentException e) {
                System.err.println("Error de validación: " + e.getMessage());
                return false;
            } catch (Exception e) {
                System.err.println("Error al agregar producto: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }

        public Inventario obtenerProducto(int id) {
            try {
                return inventarioService.obtenerProducto(id);
            } catch (Exception e) {
                System.err.println("Error al obtener producto: " + e.getMessage());
                return null;
            }
        }

        public List<Inventario> listarTodosLosProductos() {
            try {
                return inventarioService.listarTodosLosProductos();
            } catch (Exception e) {
                System.err.println("Error al listar productos: " + e.getMessage());
                e.printStackTrace();
                return List.of();
            }
        }

        public List<Inventario> listarProductosActivos() {
            try {
                return inventarioService.listarProductosActivos();
            } catch (Exception e) {
                System.err.println("Error al listar productos activos: " + e.getMessage());
                return List.of();
            }
        }

        public boolean actualizarProducto(Inventario producto) {
            try {
                return inventarioService.actualizarProducto(producto);
            } catch (IllegalArgumentException e) {
                System.err.println("Error de validación: " + e.getMessage());
                return false;
            } catch (Exception e) {
                System.err.println("Error al actualizar producto: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }

        public boolean eliminarProducto(int id) {
            try {
                return inventarioService.eliminarProducto(id);
            } catch (IllegalArgumentException e) {
                System.err.println("Error de validación: " + e.getMessage());
                return false;
            } catch (Exception e) {
                System.err.println("Error al eliminar producto: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }

        public boolean desactivarProducto(int id) {
            try {
                return inventarioService.desactivarProducto(id);
            } catch (Exception e) {
                System.err.println("Error al desactivar producto: " + e.getMessage());
                return false;
            }
        }

        // ==================== BÚSQUEDAS Y FILTROS ====================

        public List<Inventario> buscarPorNombre(String nombre) {
            try {
                return inventarioService.buscarPorNombre(nombre);
            } catch (IllegalArgumentException e) {
                System.err.println("Error de validación: " + e.getMessage());
                return List.of();
            } catch (Exception e) {
                System.err.println("Error al buscar productos: " + e.getMessage());
                return List.of();
            }
        }

        public List<Inventario> listarPorTipo(int tipoId) {
            try {
                return inventarioService.listarPorTipo(tipoId);
            } catch (IllegalArgumentException e) {
                System.err.println("Error de validación: " + e.getMessage());
                return List.of();
            } catch (Exception e) {
                System.err.println("Error al listar por tipo: " + e.getMessage());
                return List.of();
            }
        }

        public List<Inventario> listarPorProveedor(int proveedorId) {
            try {
                return inventarioService.listarPorProveedor(proveedorId);
            } catch (IllegalArgumentException e) {
                System.err.println("Error de validación: " + e.getMessage());
                return List.of();
            } catch (Exception e) {
                System.err.println("Error al listar por proveedor: " + e.getMessage());
                return List.of();
            }
        }

        public List<Inventario> listarProductosQueRequierenReceta() {
            try {
                return inventarioService.listarProductosQueRequierenReceta();
            } catch (Exception e) {
                System.err.println("Error al listar productos con receta: " + e.getMessage());
                return List.of();
            }
        }

        public List<Inventario> listarPorRangoPrecios(double precioMin, double precioMax) {
            try {
                return inventarioService.listarPorRangoPrecios(precioMin, precioMax);
            } catch (IllegalArgumentException e) {
                System.err.println("Error de validación: " + e.getMessage());
                return List.of();
            } catch (Exception e) {
                System.err.println("Error al listar por rango de precios: " + e.getMessage());
                return List.of();
            }
        }

        // ==================== GESTIÓN DE STOCK ====================

        public List<Inventario> obtenerAlertasStock() {
            try {
                return inventarioService.obtenerAlertasStock();
            } catch (Exception e) {
                System.err.println("Error al obtener alertas de stock: " + e.getMessage());
                return List.of();
            }
        }

        public List<Inventario> obtenerProductosVencidos() {
            try {
                return inventarioService.obtenerProductosVencidos();
            } catch (Exception e) {
                System.err.println("Error al obtener productos vencidos: " + e.getMessage());
                return List.of();
            }
        }

        public List<Inventario> obtenerProductosProximosAVencer(int dias) {
            try {
                return inventarioService.obtenerProductosProximosAVencer(dias);
            } catch (Exception e) {
                System.err.println("Error al obtener productos próximos a vencer: " + e.getMessage());
                return List.of();
            }
        }

        public boolean actualizarStock(int productoId, int nuevaCantidad) {
            try {
                return inventarioService.actualizarStock(productoId, nuevaCantidad);
            } catch (IllegalArgumentException e) {
                System.err.println("Error de validación: " + e.getMessage());
                return false;
            } catch (Exception e) {
                System.err.println("Error al actualizar stock: " + e.getMessage());
                return false;
            }
        }

        public boolean registrarEntrada(int productoId, int cantidad) {
            try {
                return inventarioService.registrarEntrada(productoId, cantidad);
            } catch (IllegalArgumentException e) {
                System.err.println("Error de validación: " + e.getMessage());
                return false;
            } catch (Exception e) {
                System.err.println("Error al registrar entrada: " + e.getMessage());
                return false;
            }
        }

        public boolean registrarSalida(int productoId, int cantidad) {
            try {
                return inventarioService.registrarSalida(productoId, cantidad);
            } catch (IllegalArgumentException e) {
                System.err.println("Error de validación: " + e.getMessage());
                return false;
            } catch (Exception e) {
                System.err.println("Error al registrar salida: " + e.getMessage());
                return false;
            }
        }

        public boolean verificarDisponibilidad(int productoId, int cantidadRequerida) {
            try {
                return inventarioService.verificarDisponibilidad(productoId, cantidadRequerida);
            } catch (Exception e) {
                System.err.println("Error al verificar disponibilidad: " + e.getMessage());
                return false;
            }
        }

        // ==================== REPORTES ====================

        public InventarioService.ResumenInventario generarResumen() {
            try {
                return inventarioService.generarResumen();
            } catch (Exception e) {
                System.err.println("Error al generar resumen: " + e.getMessage());
                e.printStackTrace();
                return null;
            }
        }

        // ==================== UTILIDADES ====================

        public String[] getUnidadesMedida() {
            return InventarioService.UNIDADES_MEDIDA;
        }

        public int getDiasAlertaVencimiento() {
            return InventarioService.DIAS_ALERTA_VENCIMIENTO;
        }
    }

