package com.happyfeet.Repository;

import com.happyfeet.Repository.Interfaz.IInventarioDAO;
import com.happyfeet.config.DatabaseConfig;
import com.happyfeet.model.entities.Inventario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventarioDAO implements IInventarioDAO {
    @Override
    public List<Inventario> listarInventario() {
        List<Inventario> inventario = new ArrayList<>();
        String sql = "SELECT * FROM inventario ORDER BY nombre_producto";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                var producto = mapResultSetToInventario(rs);
                inventario.add(producto);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar el inventario: " + e.getMessage());
            e.printStackTrace();
        }
        return inventario;
    }

    @Override
    public boolean buscarInventario(Inventario inventario) {
        PreparedStatement ps;
        ResultSet rs;
        var con = DatabaseConfig.getConexion();
        var sql = "SELECT * FROM inventario WHERE id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, inventario.getId());
            rs = ps.executeQuery();
            if (rs.next()) {
                mapResultSetToInventario(rs, inventario);
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error al buscar el producto por id: " + e.getMessage());
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar conexion: " + e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean agregarInventario(Inventario inventario) {
        String sql = "INSERT INTO inventario(nombre_producto, producto_tipo_id, descripcion, " +
                "fabricante, lote, cantidad_stock, stock_minimo, fecha_vencimiento, precio_venta) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            setInventarioParameters(ps, inventario);

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al agregar el producto al inventario: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean modificarInventario(Inventario inventario) {
        String sql = "UPDATE inventario SET nombre_producto = ?, producto_tipo_id = ?, descripcion = ?, " +
                "fabricante = ?, lote = ?, cantidad_stock = ?, stock_minimo = ?, " +
                "fecha_vencimiento = ?, precio_venta = ? WHERE id = ?";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            setInventarioParameters(ps, inventario);
            ps.setInt(10, inventario.getId());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al modificar el producto del inventario: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Métodos adicionales
    public List<Inventario> listarProductosBajoStock() {
        List<Inventario> productos = new ArrayList<>();
        String sql = "SELECT * FROM inventario WHERE cantidad_stock <= stock_minimo ORDER BY cantidad_stock ASC";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                var producto = mapResultSetToInventario(rs);
                productos.add(producto);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar productos con stock bajo: " + e.getMessage());
            e.printStackTrace();
        }
        return productos;
    }

    public List<Inventario> listarProductosPorVencer(int dias) {
        List<Inventario> productos = new ArrayList<>();
        String sql = "SELECT * FROM inventario WHERE fecha_vencimiento BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL ? DAY) ORDER BY fecha_vencimiento ASC";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, dias);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                var producto = mapResultSetToInventario(rs);
                productos.add(producto);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar productos por vencer: " + e.getMessage());
            e.printStackTrace();
        }
        return productos;
    }

    public List<Inventario> listarProductosPorTipo(int tipoId) {
        List<Inventario> productos = new ArrayList<>();
        String sql = "SELECT * FROM inventario WHERE producto_tipo_id = ? ORDER BY nombre_producto";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, tipoId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                var producto = mapResultSetToInventario(rs);
                productos.add(producto);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar productos por tipo: " + e.getMessage());
            e.printStackTrace();
        }
        return productos;
    }

    public boolean actualizarStock(int productoId, int cantidad) {
        String sql = "UPDATE inventario SET cantidad_stock = ? WHERE id = ?";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cantidad);
            ps.setInt(2, productoId);

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar el stock: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Métodos auxiliares para mapeo
    private Inventario mapResultSetToInventario(ResultSet rs) throws SQLException {
        Inventario inventario = new Inventario();
        mapResultSetToInventario(rs, inventario);
        return inventario;
    }

    private void mapResultSetToInventario(ResultSet rs, Inventario inventario) throws SQLException {
        inventario.setId(rs.getInt("id"));
        inventario.setNombre_producto(rs.getString("nombre_producto"));
        inventario.setProducto_tipo_id(rs.getInt("producto_tipo_id"));
        inventario.setDescripcion(rs.getString("descripcion"));
        inventario.setFabricante(rs.getString("fabricante"));
        inventario.setLote(rs.getString("lote"));
        inventario.setCantidad_stock(rs.getInt("cantidad_stock"));
        inventario.setStock_minimo(rs.getInt("stock_minimo"));

        Date fechaVencimiento = rs.getDate("fecha_vencimiento");
        if (fechaVencimiento != null) {
            inventario.setFecha_vencimiento(fechaVencimiento.toLocalDate());
        }

        inventario.setPrecio_venta(rs.getBigDecimal("precio_venta"));
    }

    private void setInventarioParameters(PreparedStatement ps, Inventario inventario) throws SQLException {
        ps.setString(1, inventario.getNombre_producto());
        ps.setInt(2, inventario.getProducto_tipo_id());
        ps.setString(3, inventario.getDescripcion());
        ps.setString(4, inventario.getFabricante());
        ps.setString(5, inventario.getLote());
        ps.setInt(6, inventario.getCantidad_stock());
        ps.setInt(7, inventario.getStock_minimo());

        if (inventario.getFecha_vencimiento() != null) {
            ps.setDate(8, Date.valueOf(inventario.getFecha_vencimiento()));
        } else {
            ps.setNull(8, Types.DATE);
        }

        ps.setBigDecimal(9, inventario.getPrecio_venta());
    }

    public static void main(String[] args) {
        InventarioDAO inventarioDao = new InventarioDAO();

        //System.out.println("*** Listar INVENTARIO ***");
        //var inventario = inventarioDao.listarInventario();
        //inventario.forEach(System.out::println);

        //System.out.println("\n*** Productos con STOCK BAJO ***");
        //var productosBajoStock = inventarioDao.listarProductosBajoStock();
        //productosBajoStock.forEach(System.out::println);

        System.out.println("\n*** Productos por VENCER (30 días) ***");
        var productosPorVencer = inventarioDao.listarProductosPorVencer(30);
        productosPorVencer.forEach(System.out::println);

    }
}
