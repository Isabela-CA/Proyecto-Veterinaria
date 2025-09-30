package com.happyfeet.Repository;


import com.happyfeet.Repository.Interfaz.ICitaDAO;
import com.happyfeet.config.DatabaseConfig;
import com.happyfeet.model.entities.Cita;
import com.happyfeet.model.enums.EstadoCita;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CitaDAO implements ICitaDAO {

    @Override
    public List<Cita> listarCitas() {
        List<Cita> citas = new ArrayList<>();
        String sql;
        sql = "SELECT * FROM citas ORDER BY fecha_hora DESC";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) citas.add(mapearCita(rs));
        } catch (SQLException e) {
            System.out.println("Error al listar citas: " + e.getMessage());
        }
        return citas;
    }

    @Override
    public boolean agregarCita(Cita cita) {
        String sql = "INSERT INTO citas(mascota_id, veterinario_id, fecha_hora, motivo, estado_id) VALUES(?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cita.getMascota_id());
            ps.setInt(2, cita.getVeterinario_id());
            ps.setTimestamp(3, Timestamp.valueOf(cita.getFecha_hora()));
            ps.setString(4, cita.getMotivo());
            ps.setInt(5, cita.getEstado_id());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al agregar cita: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean modificarCita(Cita cita) {
        String sql = "UPDATE citas SET mascota_id = ?, veterinario_id = ?, fecha_hora = ?, motivo = ?, estado_id = ? WHERE id = ?";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cita.getMascota_id());
            ps.setInt(2, cita.getVeterinario_id());
            ps.setTimestamp(3, Timestamp.valueOf(cita.getFecha_hora()));
            ps.setString(4, cita.getMotivo());
            ps.setInt(5, cita.getEstado_id());
            ps.setInt(6, cita.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al modificar cita: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Cita> listarCitasPorMascota(int mascotaId) {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT * FROM citas WHERE mascota_id = ? ORDER BY fecha_hora DESC";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, mascotaId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                citas.add(mapearCita(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar citas por mascota: " + e.getMessage());
        }
        return citas;
    }

    @Override
    public List<Cita> listarCitasPorVeterinario(int veterinarioId) {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT * FROM citas WHERE veterinario_id = ? ORDER BY fecha_hora DESC";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, veterinarioId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                citas.add(mapearCita(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar citas por veterinario: " + e.getMessage());
        }
        return citas;
    }

    @Override
    public boolean buscarCita(Cita cita) {
        Cita encontrada = buscarCitaPorId(cita.getId());
        if (encontrada != null) {
            cita.setMascota_id(encontrada.getMascota_id());
            cita.setVeterinario_id(encontrada.getVeterinario_id());
            cita.setFecha_hora(encontrada.getFecha_hora());
            cita.setMotivo(encontrada.getMotivo());
            cita.setEstado_id(encontrada.getEstado_id());
            return true;
        }
        return false;
    }

    // Método auxiliar
    private Cita mapearCita(ResultSet rs) throws SQLException {
        Cita cita = new Cita();
        cita.setId(rs.getInt("id"));
        cita.setMascota_id(rs.getInt("mascota_id"));
        cita.setVeterinario_id(rs.getInt("veterinario_id"));
        cita.setFecha_hora(rs.getTimestamp("fecha_hora").toLocalDateTime());
        cita.setMotivo(rs.getString("motivo"));
        cita.setEstado_id(rs.getInt("estado_id"));
        return cita;
    }

    @Override
    public Cita buscarCitaPorId(int id) {
        String sql = "SELECT * FROM citas WHERE id = ?" ;
        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Cita cita = new Cita();
                cita.setId(rs.getInt("id"));
                cita.setMascota_id(rs.getInt("mascota_id"));
                cita.setVeterinario_id(rs.getInt("veterinario_id"));
                cita.setFecha_hora(rs.getTimestamp("fecha_hora").toLocalDateTime());
                cita.setMotivo(rs.getString("motivo"));
                cita.setEstado_id(rs.getInt("estado_id"));
                return cita;
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar cita por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    public boolean cambiarEstadoCita(int citaId, EstadoCita.Estado nuevoEstado) {
        String sql = "UPDATE citas SET estado_id = ? WHERE id = ?";
        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, nuevoEstado.ordinal() + 1); // Asumiendo que los estados empiezan en 1
            ps.setInt(2, citaId);

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al cambiar estado de cita: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Cita> listarCitasPorFecha(LocalDate fecha) {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT * FROM citas WHERE DATE(fecha_hora) = ? ORDER BY fecha_hora ASC";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(fecha));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Cita cita = new Cita();
                cita.setId(rs.getInt("id"));
                cita.setMascota_id(rs.getInt("mascota_id"));
                cita.setVeterinario_id(rs.getInt("veterinario_id"));
                cita.setFecha_hora(rs.getTimestamp("fecha_hora").toLocalDateTime());
                cita.setMotivo(rs.getString("motivo"));
                cita.setEstado_id(rs.getInt("estado_id"));
                citas.add(cita);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar citas por fecha: " + e.getMessage());
            e.printStackTrace();
        }
        return citas;
    }


    public List<Cita> listarCitasPorEstado(EstadoCita.Estado estado) {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT * FROM citas WHERE estado_id = ? ORDER BY fecha_hora DESC";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, estado.ordinal() + 1);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Cita cita = new Cita();
                cita.setId(rs.getInt("id"));
                cita.setMascota_id(rs.getInt("mascota_id"));
                cita.setVeterinario_id(rs.getInt("veterinario_id"));
                cita.setFecha_hora(rs.getTimestamp("fecha_hora").toLocalDateTime());
                cita.setMotivo(rs.getString("motivo"));
                cita.setEstado_id(rs.getInt("estado_id"));
                citas.add(cita);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar citas por estado: " + e.getMessage());
            e.printStackTrace();
        }
        return citas;
    }

    public List<Cita> listarCitasVeterinarioPorFecha(int veterinarioId, LocalDate fecha) {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT * FROM citas WHERE veterinario_id = ? AND DATE(fecha_hora) = ? ORDER BY fecha_hora ASC";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, veterinarioId);
            ps.setDate(2, Date.valueOf(fecha));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Cita cita = new Cita();
                cita.setId(rs.getInt("id"));
                cita.setMascota_id(rs.getInt("mascota_id"));
                cita.setVeterinario_id(rs.getInt("veterinario_id"));
                cita.setFecha_hora(rs.getTimestamp("fecha_hora").toLocalDateTime());
                cita.setMotivo(rs.getString("motivo"));
                cita.setEstado_id(rs.getInt("estado_id"));
                citas.add(cita);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar citas por veterinario y fecha: " + e.getMessage());
            e.printStackTrace();
        }
        return citas;
    }

    public boolean verificarDisponibilidadVeterinario(int veterinarioId, LocalDate fecha, LocalTime hora) {
        String sql = "SELECT * FROM citas WHERE veterinario_id = ? AND DATE(fecha_hora) = ? AND TIME(fecha_hora) = ? AND estado_id NOT IN (4, 5)";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, veterinarioId);
            ps.setDate(2, Date.valueOf(String.valueOf(fecha)));
            ps.setTime(3, Time.valueOf(String.valueOf(hora)));

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0; // Disponible si no hay citas
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar disponibilidad: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }


    public List<Cita> listarCitasPendientes() {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT * FROM citas WHERE estado_id = 1 ORDER BY fecha_hora ASC";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cita cita = new Cita();
                cita.setId(rs.getInt("id"));
                cita.setMascota_id(rs.getInt("mascota_id"));
                cita.setVeterinario_id(rs.getInt("veterinario_id"));
                cita.setFecha_hora(rs.getTimestamp("fecha_hora").toLocalDateTime());
                cita.setMotivo(rs.getString("motivo"));
                cita.setEstado_id(rs.getInt("estado_id"));
                citas.add(cita);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar citas pendientes: " + e.getMessage());
            e.printStackTrace();
        }
        return citas;
    }
}
