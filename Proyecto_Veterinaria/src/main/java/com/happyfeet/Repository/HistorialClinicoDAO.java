package com.happyfeet.Repository;

import com.happyfeet.Repository.Interfaz.IHistorialClinicoDAO;
import com.happyfeet.config.DatabaseConfig;
import com.happyfeet.model.entities.HistorialClinico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class HistorialClinicoDAO implements IHistorialClinicoDAO {

    @Override
    public List<HistorialClinico> listarHistorialClinico() {
        List<HistorialClinico> historiales = new ArrayList<>();
        String sql = "SELECT * FROM historial_medico ORDER BY fecha_evento DESC, id DESC";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                historiales.add(mapearHistorial(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar el historial clínico: " + e.getMessage());
            e.printStackTrace();
        }
        return historiales;
    }

    @Override
    public HistorialClinico buscarHistorialClinicoPorId(int id) {
        String sql = "SELECT * FROM historial_medico WHERE id = ?";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearHistorial(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar historial clínico por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean agregarHistorialClinico(HistorialClinico historialClinico) {
        String sql = "INSERT INTO historial_medico(mascota_id, veterinario_id, fecha_evento, evento_tipo_id, " +
                "descripcion, diagnostico, tratamiento_recomendado, producto_id, cantidad_utilizada) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, historialClinico.getMascota_id());
            ps.setInt(2, historialClinico.getVeterinario_id());
            ps.setDate(3, Date.valueOf(historialClinico.getFecha_evento()));
            ps.setInt(4, historialClinico.getEvento_tipo_id());
            ps.setString(5, historialClinico.getDescripcion());
            ps.setString(6, historialClinico.getDiagnostico());
            ps.setString(7, historialClinico.getTratamiento_recomendado());

            if (historialClinico.getProducto_id() != null) {
                ps.setInt(8, historialClinico.getProducto_id());
            } else {
                ps.setNull(8, Types.INTEGER);
            }

            ps.setInt(9, historialClinico.getCantidad_utilizada());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al agregar el historial clínico: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean modificarHistorialClinico(HistorialClinico historialClinico) {
        String sql = "UPDATE historial_medico SET mascota_id = ?, veterinario_id = ?, fecha_evento = ?, " +
                "evento_tipo_id = ?, descripcion = ?, diagnostico = ?, tratamiento_recomendado = ?, " +
                "producto_id = ?, cantidad_utilizada = ? WHERE id = ?";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, historialClinico.getMascota_id());
            ps.setInt(2, historialClinico.getVeterinario_id());
            ps.setDate(3, Date.valueOf(historialClinico.getFecha_evento()));
            ps.setInt(4, historialClinico.getEvento_tipo_id());
            ps.setString(5, historialClinico.getDescripcion());
            ps.setString(6, historialClinico.getDiagnostico());
            ps.setString(7, historialClinico.getTratamiento_recomendado());

            if (historialClinico.getProducto_id() != null) {
                ps.setInt(8, historialClinico.getProducto_id());
            } else {
                ps.setNull(8, Types.INTEGER);
            }

            ps.setInt(9, historialClinico.getCantidad_utilizada());
            ps.setInt(10, historialClinico.getId());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al modificar el historial clínico: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<HistorialClinico> listarHistorialPorMascota(int mascotaId) {
        List<HistorialClinico> historiales = new ArrayList<>();
        String sql = "SELECT * FROM historial_medico WHERE mascota_id = ? ORDER BY fecha_evento DESC, id DESC";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, mascotaId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                historiales.add(mapearHistorial(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar el historial por mascota: " + e.getMessage());
            e.printStackTrace();
        }
        return historiales;
    }

    // Método auxiliar para mapear ResultSet a HistorialClinico
    private HistorialClinico mapearHistorial(ResultSet rs) throws SQLException {
        HistorialClinico historial = new HistorialClinico();
        historial.setId(rs.getInt("id"));
        historial.setMascota_id(rs.getInt("mascota_id"));
        historial.setVeterinario_id(rs.getInt("veterinario_id"));
        historial.setFecha_evento(rs.getDate("fecha_evento").toLocalDate());
        historial.setEvento_tipo_id(rs.getInt("evento_tipo_id"));
        historial.setDescripcion(rs.getString("descripcion"));
        historial.setDiagnostico(rs.getString("diagnostico"));
        historial.setTratamiento_recomendado(rs.getString("tratamiento_recomendado"));

        int productoId = rs.getInt("producto_id");
        if (!rs.wasNull()) {
            historial.setProducto_id(productoId);
        }

        historial.setCantidad_utilizada(rs.getInt("cantidad_utilizada"));
        return historial;
    }
}


