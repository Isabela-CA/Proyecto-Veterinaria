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
        String sql = "SELECT * FROM historial_medico ORDER BY fecha_evento ASC";

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
            System.out.println("Error al buscar el historial clínico por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean agregarHistorialClinico(HistorialClinico historialClinico) {
        String sql = "INSERT INTO historial_medico(mascota_id, fecha_evento, evento_tipo_id,descripcion, " +
                "diagnostico, tratamiento_recomendado, veterinario_id, consulta_id, procedimiento_id) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, historialClinico.getMascota_id());
            ps.setDate(2, Date.valueOf(historialClinico.getFecha_evento()));
            ps.setInt(3, historialClinico.getEvento_tipo_id());
            ps.setString(4, historialClinico.getDescripcion());
            ps.setString(5, historialClinico.getDiagnostico());
            ps.setString(6, historialClinico.getTratamiento_recomendado());
            ps.setInt(7, historialClinico.getVeterinario_id());
            ps.setInt(8, historialClinico.getConsulta_id());
            ps.setInt(9, historialClinico.getProcedimiento_id());

        return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al agregar el historial clínico: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean modificarHistorialClinico(HistorialClinico historialClinico) {
        String sql = "UPDATE historial_medico SET mascota_id =?, fecha_evento=?, evento_tipo_id=?,descripcion=?" +
                "diagnostico=?, tratamiento_recomendado=?, veterinario_id=?, consulta_id=?, procedimiento_id=? " +
                "WHERE id = ?";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, historialClinico.getMascota_id());
            ps.setDate(2, Date.valueOf(historialClinico.getFecha_evento()));
            ps.setInt(3, historialClinico.getEvento_tipo_id());
            ps.setString(4, historialClinico.getDescripcion());
            ps.setString(5, historialClinico.getDiagnostico());
            ps.setString(6, historialClinico.getTratamiento_recomendado());
            ps.setInt(7, historialClinico.getVeterinario_id());
            ps.setInt(8, historialClinico.getConsulta_id());
            ps.setInt(9, historialClinico.getProcedimiento_id());

            return ps.executeUpdate() > 0;
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

    // Método para mapear ResultSet a HistorialClinico
    private HistorialClinico mapearHistorial(ResultSet rs) throws SQLException {
        var historial = new HistorialClinico();

        historial.setId(rs.getInt("id"));
        historial.setMascota_id(rs.getInt("mascota_id"));
        historial.setFecha_evento(rs.getDate("fecha_evento").toLocalDate());
        historial.setEvento_tipo_id(rs.getInt("evento_tipo_id"));
        historial.setDescripcion(rs.getString("descripcion"));
        historial.setDiagnostico(rs.getString("diagnostico"));
        historial.setTratamiento_recomendado(rs.getString("tratamiento_recomendado"));
        historial.setVeterinario_id(rs.getInt("veterinario_id"));
        historial.setConsulta_id(rs.getInt("Consulta_id"));
        historial.setProcedimiento_id(rs.getInt("Procedimiento_id"));

        return historial;
    }
}