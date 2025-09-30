package com.happyfeet.Repository;

import com.happyfeet.Repository.Interfaz.IProcedimientoQuirurgicoDAO;
import com.happyfeet.config.DatabaseConfig;
import com.happyfeet.model.entities.ProcedimientoQuirurgico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProcedimientoQuirurgicoDAO implements IProcedimientoQuirurgicoDAO {
        @Override
        public List<ProcedimientoQuirurgico> listarProcedimientosQuirurgicos() {
            List<ProcedimientoQuirurgico> procedimientos = new ArrayList<>();
            String query = "SELECT * FROM procedimientos_quirurgicos ORDER BY fecha_procedimiento DESC, id DESC";
            String sql;
            sql = query;

            try (Connection con = DatabaseConfig.getConexion();
                 PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    ProcedimientoQuirurgico procedimiento = mapResultSetToProcedimiento(rs);
                    procedimientos.add(procedimiento);
                }
            } catch (SQLException e) {
                System.out.println("Error al listar los procedimientos quirurgicos: " + e.getMessage());
                e.printStackTrace();
            }
            return procedimientos;
        }

        @Override
        public boolean buscarProcedimientoQuirurgico(ProcedimientoQuirurgico procedimiento) {
            PreparedStatement ps = null;
            ResultSet rs = null;
            Connection con = DatabaseConfig.getConexion();
            String sql = "SELECT * FROM procedimientos_quirurgicos WHERE id = ?";
            try {
                ps = con.prepareStatement(sql);
                ps.setInt(1, procedimiento.getId());
                rs = ps.executeQuery();
                if (rs.next()) {
                    mapResultSetToProcedimiento(rs, procedimiento);
                    return true;
                }
            } catch (Exception e) {
                System.out.println("Error al buscar el procedimiento quirurgico por id: " + e.getMessage());
            } finally {
                try {
                    if (rs != null) rs.close();
                    if (ps != null) ps.close();
                    if (con != null) con.close();
                } catch (Exception e) {
                    System.out.println("Error al cerrar recursos: " + e.getMessage());
                }
            }
            return false;
        }

        @Override
        public boolean agregarProcedimientoQuirurgico(ProcedimientoQuirurgico procedimiento) {
            String sql;
            sql = "INSERT INTO procedimientos_quirurgicos(mascota_id, veterinario_id, cita_id, " +
                    "fecha_procedimiento, tipo_procedimiento, diagnostico, descripcion_procedimiento, " +
                    "anestesia_utilizada, medicacion_prescrita, cuidados_recomendados, resultado) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection con = DatabaseConfig.getConexion();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                setProcedimientoParameters(ps, procedimiento);

                int filasAfectadas = ps.executeUpdate();
                return filasAfectadas > 0;

            } catch (SQLException e) {
                System.out.println("Error al agregar el procedimiento quirurgico: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }

        @Override
        public boolean modificarProcedimientoQuirurgico(ProcedimientoQuirurgico procedimiento) {
            String sql;
            sql = "UPDATE procedimientos_quirurgicos SET mascota_id = ?, veterinario_id = ?, cita_id = ?, " +
                    "fecha_procedimiento = ?, tipo_procedimiento = ?, diagnostico = ?, descripcion_procedimiento = ?, " +
                    "anestesia_utilizada = ?, medicacion_prescrita = ?, cuidados_recomendados = ?, resultado = ? " +
                    "WHERE id = ?";

            try (Connection con = DatabaseConfig.getConexion();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                setProcedimientoParameters(ps, procedimiento);
                ps.setInt(12, procedimiento.getId());

                int filasAfectadas = ps.executeUpdate();
                return filasAfectadas > 0;

            } catch (SQLException e) {
                System.out.println("Error al modificar el procedimiento quirurgico: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }

        @Override
        public boolean eliminarProcedimientoQuirurgico(ProcedimientoQuirurgico procedimiento) {
            String sql;
            sql = "DELETE FROM procedimientos_quirurgicos WHERE id = ?";

            try (Connection con = DatabaseConfig.getConexion();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, procedimiento.getId());

                int filasAfectadas = ps.executeUpdate();
                return filasAfectadas > 0;

            } catch (SQLException e) {
                System.out.println("Error al eliminar el procedimiento quirurgico: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }

        public List<ProcedimientoQuirurgico> listarProcedimientosPorMascota(int mascotaId) {
            List<ProcedimientoQuirurgico> procedimientos = new ArrayList<>();
            String sql;
            sql = "SELECT * FROM procedimientos_quirurgicos WHERE mascota_id = ? ORDER BY fecha_procedimiento DESC";

            try (Connection con = DatabaseConfig.getConexion();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, mascotaId);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    ProcedimientoQuirurgico procedimiento = mapResultSetToProcedimiento(rs);
                    procedimientos.add(procedimiento);
                }
            } catch (SQLException e) {
                System.out.println("Error al listar los procedimientos por mascota: " + e.getMessage());
                e.printStackTrace();
            }
            return procedimientos;
        }

        public List<ProcedimientoQuirurgico> listarProcedimientosPorVeterinario(int veterinarioId) {
            List<ProcedimientoQuirurgico> procedimientos = new ArrayList<>();
            String sql;
            sql = "SELECT * FROM procedimientos_quirurgicos WHERE veterinario_id = ? ORDER BY fecha_procedimiento DESC";

            try (Connection con = DatabaseConfig.getConexion();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, veterinarioId);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    ProcedimientoQuirurgico procedimiento = mapResultSetToProcedimiento(rs);
                    procedimientos.add(procedimiento);
                }
            } catch (SQLException e) {
                System.out.println("Error al listar los procedimientos por veterinario: " + e.getMessage());
                e.printStackTrace();
            }
            return procedimientos;
        }

        private ProcedimientoQuirurgico mapResultSetToProcedimiento(ResultSet rs) throws SQLException {
            ProcedimientoQuirurgico procedimiento = new ProcedimientoQuirurgico();
            mapResultSetToProcedimiento(rs, procedimiento);
            return procedimiento;
        }

        private void mapResultSetToProcedimiento(ResultSet rs, ProcedimientoQuirurgico procedimiento) throws SQLException {
            procedimiento.setId(rs.getInt("id"));
            procedimiento.setMascota_id(rs.getInt("mascota_id"));
            procedimiento.setVeterinario_id(rs.getInt("veterinario_id"));

            int citaId = rs.getInt("cita_id");
            if (!rs.wasNull()) {
                procedimiento.setCita_id(citaId);
            }

            procedimiento.setFecha_procedimiento(rs.getTimestamp("fecha_procedimiento").toLocalDateTime());
            procedimiento.setTipo_procedimiento(rs.getString("tipo_procedimiento"));
            procedimiento.setDiagnostico(rs.getString("diagnostico"));
            procedimiento.setDescripcion_procedimiento(rs.getString("descripcion_procedimiento"));
            procedimiento.setAnestesia_utilizada(rs.getString("anestesia_utilizada"));
            procedimiento.setMedicacion_prescrita(rs.getString("medicacion_prescrita"));
            procedimiento.setCuidados_recomendados(rs.getString("cuidados_recomendados"));
            procedimiento.setResultado(rs.getString("resultado"));
        }

        private void setProcedimientoParameters(PreparedStatement ps, ProcedimientoQuirurgico procedimiento) throws SQLException {
            ps.setInt(1, procedimiento.getMascota_id());
            ps.setInt(2, procedimiento.getVeterinario_id());

            if (procedimiento.getCita_id() != null) {
                ps.setInt(3, procedimiento.getCita_id());
            } else {
                ps.setNull(3, Types.INTEGER);
            }

            ps.setTimestamp(4, Timestamp.valueOf(procedimiento.getFecha_procedimiento()));
            ps.setString(5, procedimiento.getTipo_procedimiento());
            ps.setString(6, procedimiento.getDiagnostico());
            ps.setString(7, procedimiento.getDescripcion_procedimiento());
            ps.setString(8, procedimiento.getAnestesia_utilizada());
            ps.setString(9, procedimiento.getMedicacion_prescrita());
            ps.setString(10, procedimiento.getCuidados_recomendados());
            ps.setString(11, procedimiento.getResultado());
        }
    }
