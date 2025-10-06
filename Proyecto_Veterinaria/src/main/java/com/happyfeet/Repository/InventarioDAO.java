package com.happyfeet.Repository;

import com.happyfeet.Repository.Interfaz.IInventarioDAO;
import com.happyfeet.config.DatabaseConfig;
import com.happyfeet.model.entities.Inventario;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InventarioDAO implements IInventarioDAO {

    @Override
    public List<Inventario> listarProductos() {
        List<Inventario> productos = new ArrayList<>();
        String sql = "SELECT * FROM inventario ORDER BY nombre_producto";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                productos.add(mapearProducto(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar productos: " + e.getMessage());
            e.printStackTrace();
        }
        return productos;
    }

    @Override
    public Inventario buscarProductoPorId(int id) {
        String sql = "SELECT * FROM inventario WHERE id = ?";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearProducto(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar producto por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean agregarProducto(Inventario producto) {
        String sql = "INSERT INTO inventario (nombre_producto, producto_tipo_id, descripcion, " +
                "fabricante, proveedor_id, lote, cantidad_stock, stock_minimo, unidad_medida, " +
                "fecha_vencimiento, precio_compra, precio_venta, requiere_receta, activo) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            setProductoParameters(ps, producto);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    producto.setId(generatedKeys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al agregar producto: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean modificarProducto(Inventario producto) {
        String sql = "UPDATE inventario SET nombre_producto = ?, producto_tipo_id = ?, " +
                "descripcion = ?, fabricante = ?, proveedor_id = ?, lote = ?, cantidad_stock = ?, " +
                "stock_minimo = ?, unidad_medida = ?, fecha_vencimiento = ?, precio_compra = ?, " +
                "precio_venta = ?, requiere_receta = ?, activo = ? WHERE id = ?";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            setProductoParameters(ps, producto);
            ps.setInt(15, producto.getId());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al modificar producto: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean eliminarProducto(int id) {
        String sql = "DELETE FROM inventario WHERE id = ?";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar producto: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Inventario> buscarPorNombre(String nombre) {
        List<Inventario> productos = new ArrayList<>();
        String sql = "SELECT * FROM inventario WHERE nombre_producto LIKE ? ORDER BY nombre_producto";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + nombre + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                productos.add(mapearProducto(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar productos por nombre: " + e.getMessage());
            e.printStackTrace();
        }
        return productos;
    }

    @Override
    public List<Inventario> listarPorTipo(int productoTipoId) {
        List<Inventario> productos = new ArrayList<>();
        String sql = "SELECT * FROM inventario WHERE producto_tipo_id = ? ORDER BY nombre_producto";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, productoTipoId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                productos.add(mapearProducto(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar productos por tipo: " + e.getMessage());
            e.printStackTrace();
        }
        return productos;
    }

    @Override
    public List<Inventario> listarPorProveedor(int proveedorId) {
        List<Inventario> productos = new ArrayList<>();
        String sql = "SELECT * FROM inventario WHERE proveedor_id = ? ORDER BY nombre_producto";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, proveedorId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                productos.add(mapearProducto(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar productos por proveedor: " + e.getMessage());
            e.printStackTrace();
        }
        return productos;
    }

    @Override
    public List<Inventario> listarProductosActivos() {
        List<Inventario> productos = new ArrayList<>();
        String sql = "SELECT * FROM inventario WHERE activo = TRUE ORDER BY nombre_producto";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                productos.add(mapearProducto(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar productos activos: " + e.getMessage());
            e.printStackTrace();
        }
        return productos;
    }

    @Override
    public List<Inventario> listarProductosConStockBajo() {
        List<Inventario> productos = new ArrayList<>();
        String sql = "SELECT * FROM inventario WHERE cantidad_stock <= stock_minimo AND activo = TRUE " +
                "ORDER BY cantidad_stock";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                productos.add(mapearProducto(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar productos con stock bajo: " + e.getMessage());
            e.printStackTrace();
        }
        return productos;
    }

    @Override
    public List<Inventario> listarProductosVencidos() {
        List<Inventario> productos = new ArrayList<>();
        String sql = "SELECT * FROM inventario WHERE fecha_vencimiento < CURDATE() AND activo = TRUE " +
                "ORDER BY fecha_vencimiento";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                productos.add(mapearProducto(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar productos vencidos: " + e.getMessage());
            e.printStackTrace();
        }
        return productos;
    }

    @Override
    public List<Inventario> listarProductosProximosAVencer(int diasAnticipacion) {
        List<Inventario> productos = new ArrayList<>();
        String sql = "SELECT * FROM inventario WHERE fecha_vencimiento BETWEEN CURDATE() AND " +
                "DATE_ADD(CURDATE(), INTERVAL ? DAY) AND activo = TRUE ORDER BY fecha_vencimiento";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, diasAnticipacion);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                productos.add(mapearProducto(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar productos próximos a vencer: " + e.getMessage());
            e.printStackTrace();
        }
        return productos;
    }

    @Override
    public boolean actualizarStock(int productoId, int nuevaCantidad) {
        String sql = "UPDATE inventario SET cantidad_stock = ? WHERE id = ?";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, nuevaCantidad);
            ps.setInt(2, productoId);

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar stock: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean ajustarStock(int productoId, int cantidad, boolean esEntrada) {
        String sql = esEntrada ?
                "UPDATE inventario SET cantidad_stock = cantidad_stock + ? WHERE id = ?" :
                "UPDATE inventario SET cantidad_stock = cantidad_stock - ? WHERE id = ? AND cantidad_stock >= ?";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cantidad);
            ps.setInt(2, productoId);

            if (!esEntrada) {
                ps.setInt(3, cantidad); // Para verificar que hay suficiente stock
            }

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al ajustar stock: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Inventario> listarProductosQueRequierenReceta() {
        List<Inventario> productos = new ArrayList<>();
        String sql = "SELECT * FROM inventario WHERE requiere_receta = TRUE AND activo = TRUE " +
                "ORDER BY nombre_producto";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                productos.add(mapearProducto(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar productos que requieren receta: " + e.getMessage());
            e.printStackTrace();
        }
        return productos;
    }

    @Override
    public List<Inventario> listarPorRangoPrecios(double precioMin, double precioMax) {
        List<Inventario> productos = new ArrayList<>();
        String sql = "SELECT * FROM inventario WHERE precio_venta BETWEEN ? AND ? AND activo = TRUE " +
                "ORDER BY precio_venta";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1, precioMin);
            ps.setDouble(2, precioMax);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                productos.add(mapearProducto(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar productos por rango de precios: " + e.getMessage());
            e.printStackTrace();
        }
        return productos;
    }

    // ==================== MÉTODOS PRIVADOS AUXILIARES ====================

    private void setProductoParameters(PreparedStatement ps, Inventario producto) throws SQLException {
        ps.setString(1, producto.getNombre_producto());
        ps.setInt(2, producto.getProducto_tipo_id());
        ps.setString(3, producto.getDescripcion());
        ps.setString(4, producto.getFabricante());

        if (producto.getProveedor_id() != null) {
            ps.setInt(5, producto.getProveedor_id());
        } else {
            ps.setNull(5, Types.INTEGER);
        }

        ps.setString(6, producto.getLote());
        ps.setInt(7, producto.getCantidad_stock());
        ps.setInt(8, producto.getStock_minimo());
        ps.setString(9, producto.getUnidad_medida());

        if (producto.getFecha_vencimiento() != null) {
            ps.setDate(10, Date.valueOf(producto.getFecha_vencimiento()));
        } else {
            ps.setNull(10, Types.DATE);
        }

        if (producto.getPrecio_compra() != null) {
            ps.setBigDecimal(11, producto.getPrecio_compra());
        } else {
            ps.setNull(11, Types.DECIMAL);
        }

        ps.setBigDecimal(12, producto.getPrecio_venta());
        ps.setBoolean(13, producto.isRequiere_receta());
        ps.setBoolean(14, producto.isActivo());
    }

    private Inventario mapearProducto(ResultSet rs) throws SQLException {
        Inventario producto = new Inventario();

        producto.setId(rs.getInt("id"));
        producto.setNombre_producto(rs.getString("nombre_producto"));
        producto.setProducto_tipo_id(rs.getInt("producto_tipo_id"));
        producto.setDescripcion(rs.getString("descripcion"));
        producto.setFabricante(rs.getString("fabricante"));

        Integer proveedorId = rs.getObject("proveedor_id", Integer.class);
        producto.setProveedor_id(proveedorId);

        producto.setLote(rs.getString("lote"));
        producto.setCantidad_stock(rs.getInt("cantidad_stock"));
        producto.setStock_minimo(rs.getInt("stock_minimo"));
        producto.setUnidad_medida(rs.getString("unidad_medida"));

        Date fechaVenc = rs.getDate("fecha_vencimiento");
        if (fechaVenc != null) {
            producto.setFecha_vencimiento(fechaVenc.toLocalDate());
        }

        producto.setPrecio_compra(rs.getBigDecimal("precio_compra"));
        producto.setPrecio_venta(rs.getBigDecimal("precio_venta"));
        producto.setRequiere_receta(rs.getBoolean("requiere_receta"));
        producto.setActivo(rs.getBoolean("activo"));

        Timestamp fechaReg = rs.getTimestamp("fecha_registro");
        if (fechaReg != null) {
            producto.setFecha_registro(fechaReg.toLocalDateTime());
        }

        return producto;
    }
}