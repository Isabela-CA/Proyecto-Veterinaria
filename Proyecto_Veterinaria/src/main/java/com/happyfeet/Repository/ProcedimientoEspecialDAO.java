package com.happyfeet.Repository;

import com.happyfeet.Repository.Interfaz.IProcedimientoEspecialDAO;
import com.happyfeet.config.DatabaseConfig;
import com.happyfeet.model.entities.ProcedimientoEspecial;
import com.happyfeet.model.enums.EstadoCita;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProcedimientoEspecialDAO implements IProcedimientoEspecialDAO {

    @Override
    public List<ProcedimientoEspecial> listarProcedimientos() {
        List<ProcedimientoEspecial> procedimientos = new ArrayList<>();
        String sql = "SELECT * FROM procedimientos_especiales ORDER BY fecha_hora DESC";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                procedimientos.add(mapearProcedimiento(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar procedimientos: " + e.getMessage());
            e.printStackTrace();
        }

        return procedimientos;
    }

    @Override
    public ProcedimientoEspecial buscarProcedimientoPorId(int id) {
        String sql = "SELECT * FROM procedimientos_especiales WHERE id = ?";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearProcedimiento(rs);
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar procedimiento por ID: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean agregarProcedimiento(ProcedimientoEspecial procedimiento) {
        String sql = "INSERT INTO procedimientos_especiales (mascota_id, veterinario_id, " +
                "tipo_procedimiento, nombre_procedimiento, fecha_hora, duracion_estimada_minutos, " +
                "informacion_preoperatoria, detalle_procedimiento, complicaciones, " +
                "seguimiento_postoperatorio, proximo_control, estado, costo_procedimiento) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            setProcedimientoParameters(ps, procedimiento);

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al agregar procedimiento: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean modificarProcedimiento(ProcedimientoEspecial procedimiento) {
        String sql = "UPDATE procedimientos_especiales SET mascota_id = ?, veterinario_id = ?, " +
                "tipo_procedimiento = ?, nombre_procedimiento = ?, fecha_hora = ?, " +
                "duracion_estimada_minutos = ?, informacion_preoperatoria = ?, detalle_procedimiento = ?, " +
                "complicaciones = ?, seguimiento_postoperatorio = ?, proximo_control = ?, " +
                "estado = ?, costo_procedimiento = ? WHERE id = ?";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            setProcedimientoParameters(ps, procedimiento);
            ps.setInt(14, procedimiento.getId());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al modificar procedimiento: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminarProcedimiento(int id) {
        String sql = "DELETE FROM procedimientos_especiales WHERE id = ?";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar procedimiento: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<ProcedimientoEspecial> listarProcedimientosPorMascota(int mascotaId) {
        List<ProcedimientoEspecial> procedimientos = new ArrayList<>();
        String sql = "SELECT * FROM procedimientos_especiales WHERE mascota_id = ? ORDER BY fecha_hora DESC";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, mascotaId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                procedimientos.add(mapearProcedimiento(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar procedimientos por mascota: " + e.getMessage());
            e.printStackTrace();
        }

        return procedimientos;
    }

    @Override
    public List<ProcedimientoEspecial> listarProcedimientosPorVeterinario(int veterinarioId) {
        List<ProcedimientoEspecial> procedimientos = new ArrayList<>();
        String sql = "SELECT * FROM procedimientos_especiales WHERE veterinario_id = ? ORDER BY fecha_hora DESC";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, veterinarioId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                procedimientos.add(mapearProcedimiento(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar procedimientos por veterinario: " + e.getMessage());
            e.printStackTrace();
        }

        return procedimientos;
    }

    @Override
    public List<ProcedimientoEspecial> listarProcedimientosPorEstado(String estado) {
        List<ProcedimientoEspecial> procedimientos = new ArrayList<>();
        String sql = "SELECT * FROM procedimientos_especiales WHERE estado = ? ORDER BY fecha_hora DESC";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, estado);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                procedimientos.add(mapearProcedimiento(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar procedimientos por estado: " + e.getMessage());
            e.printStackTrace();
        }

        return procedimientos;
    }

    @Override
    public List<ProcedimientoEspecial> listarProcedimientosPorFecha(LocalDate fechaInicio, LocalDate fechaFin) {
        List<ProcedimientoEspecial> procedimientos = new ArrayList<>();
        String sql = "SELECT * FROM procedimientos_especiales " +
                "WHERE DATE(fecha_hora) BETWEEN ? AND ? ORDER BY fecha_hora DESC";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(fechaInicio));
            ps.setDate(2, Date.valueOf(fechaFin));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                procedimientos.add(mapearProcedimiento(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar procedimientos por fecha: " + e.getMessage());
            e.printStackTrace();
        }

        return procedimientos;
    }

    // ==================== MÉTODOS PRIVADOS ====================

    /**
     * Establece los parámetros del PreparedStatement desde un objeto ProcedimientoEspecial
     */
    private void setProcedimientoParameters(PreparedStatement ps, ProcedimientoEspecial procedimiento)
            throws SQLException {
        ps.setInt(1, procedimiento.getMascota_id());
        ps.setInt(2, procedimiento.getVeterinario_id());
        ps.setString(3, procedimiento.getTipo_procedimiento());
        ps.setString(4, procedimiento.getNombre_procedimiento());
        ps.setTimestamp(5, Timestamp.valueOf(procedimiento.getFecha_hora()));

        // Duración estimada (puede ser NULL)
        if (procedimiento.getDuracion_estimada_minutos() != null) {
            ps.setInt(6, procedimiento.getDuracion_estimada_minutos());
        } else {
            ps.setNull(6, Types.INTEGER);
        }

        ps.setString(7, procedimiento.getInformacion_preoperatoria());
        ps.setString(8, procedimiento.getDetalle_procedimiento());
        ps.setString(9, procedimiento.getComplicaciones());
        ps.setString(10, procedimiento.getSeguimiento_postoperatorio());

        // Próximo control (puede ser NULL)
        if (procedimiento.getProximo_control() != null) {
            ps.setDate(11, Date.valueOf(procedimiento.getProximo_control()));
        } else {
            ps.setNull(11, Types.DATE);
        }

        // Convertir enum a String de BD
        String estadoBD = "Programado"; // valor por defecto
        if (procedimiento.getEstado() != null) {
            estadoBD = convertirEnumABD(procedimiento.getEstado());
        }
        ps.setString(12, estadoBD);

        ps.setDouble(13, procedimiento.getCosto_procedimiento());
    }

    /**
     * Mapea un ResultSet a un objeto ProcedimientoEspecial
     */
    private ProcedimientoEspecial mapearProcedimiento(ResultSet rs) throws SQLException {
        ProcedimientoEspecial procedimiento = new ProcedimientoEspecial();

        procedimiento.setId(rs.getInt("id"));
        procedimiento.setMascota_id(rs.getInt("mascota_id"));
        procedimiento.setVeterinario_id(rs.getInt("veterinario_id"));
        procedimiento.setTipo_procedimiento(rs.getString("tipo_procedimiento"));
        procedimiento.setNombre_procedimiento(rs.getString("nombre_procedimiento"));
        procedimiento.setFecha_hora(rs.getTimestamp("fecha_hora").toLocalDateTime());

        // Duración (puede ser NULL)
        Integer duracion = rs.getObject("duracion_estimada_minutos", Integer.class);
        procedimiento.setDuracion_estimada_minutos(duracion);

        procedimiento.setInformacion_preoperatoria(rs.getString("informacion_preoperatoria"));
        procedimiento.setDetalle_procedimiento(rs.getString("detalle_procedimiento"));
        procedimiento.setComplicaciones(rs.getString("complicaciones"));
        procedimiento.setSeguimiento_postoperatorio(rs.getString("seguimiento_postoperatorio"));

        // Próximo control (puede ser NULL)
        Date proximoControl = rs.getDate("proximo_control");
        if (proximoControl != null) {
            procedimiento.setProximo_control(proximoControl.toLocalDate());
        }

        // Convertir String de BD a Enum
        String estadoBD = rs.getString("estado");
        EstadoCita.Estado estadoEnum = convertirBDaEnum(estadoBD);
        procedimiento.setEstado(estadoEnum);

        procedimiento.setCosto_procedimiento(rs.getDouble("costo_procedimiento"));

        return procedimiento;
    }

    /**
     * Convierte el enum EstadoCita.Estado al valor String de la base de datos
     */
    private String convertirEnumABD(EstadoCita.Estado estado) {
        if (estado == null) {
            return "Programado";
        }

        switch (estado) {
            case PROGRAMADA:
                return "Programado";
            case EN_CURSO:
                return "En Proceso";
            case COMPLETADA:
                return "Finalizado";
            case CANCELADA:
                return "Cancelado";
            default:
                return "Programado";
        }
    }

    /**
     * Convierte el valor String de la base de datos al enum EstadoCita.Estado
     */
    private EstadoCita.Estado convertirBDaEnum(String estadoBD) {
        if (estadoBD == null || estadoBD.trim().isEmpty()) {
            return EstadoCita.Estado.PROGRAMADA;
        }

        switch (estadoBD.trim()) {
            case "Programado":
                return EstadoCita.Estado.PROGRAMADA;
            case "En Proceso":
                return EstadoCita.Estado.EN_CURSO;
            case "Finalizado":
                return EstadoCita.Estado.COMPLETADA;
            case "Cancelado":
                return EstadoCita.Estado.CANCELADA;
            default:
                return EstadoCita.Estado.PROGRAMADA;
        }
    }
}