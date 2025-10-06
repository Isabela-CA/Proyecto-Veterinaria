package com.happyfeet.Repository;

import com.happyfeet.Repository.Interfaz.IConsultaMedicaDAO;
import com.happyfeet.config.DatabaseConfig;
import com.happyfeet.model.entities.ConsultaMedica;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ConsultaMedicaDAO implements IConsultaMedicaDAO {

        @Override
        public List<ConsultaMedica> listarConsultaMedica() {
            List<ConsultaMedica> consultas = new ArrayList<>();
            String sql = "SELECT * FROM consultas_medicas ORDER BY fecha_hora DESC";

            try (Connection con = DatabaseConfig.getConexion();
                 PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    consultas.add(mapearConsultaMedica(rs));
                }
            } catch (SQLException e) {
                System.out.println("Error al listar consultas médicas: " + e.getMessage());
                e.printStackTrace();
            }
            return consultas;
        }

        @Override
        public ConsultaMedica buscarConsultaMedicaPorId(int id) {
            String sql = "SELECT * FROM consultas_medicas WHERE id = ?";

            try (Connection con = DatabaseConfig.getConexion();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    return mapearConsultaMedica(rs);
                }
            } catch (SQLException e) {
                System.out.println("Error al buscar consulta médica por ID: " + e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public boolean agregarConsultaMedica(ConsultaMedica consultaMedica) {
            String sql = "INSERT INTO consultas_medicas(mascota_id, veterinario_id, cita_id, fecha_hora, " +
                    "motivo, sintomas, diagnostico, recomendaciones, observaciones, peso_registrado, temperatura) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection con = DatabaseConfig.getConexion();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, consultaMedica.getMascota_id());
                ps.setInt(2, consultaMedica.getVeterinario_id());

                // Manejar cita_id como NULL si es 0
                if (consultaMedica.getCita_id() > 0) {
                    ps.setInt(3, consultaMedica.getCita_id());
                } else {
                    ps.setNull(3, Types.INTEGER);
                }

                ps.setTimestamp(4, Timestamp.valueOf(consultaMedica.getFecha_hora()));
                ps.setString(5, consultaMedica.getMotivo());
                ps.setString(6, consultaMedica.getSintomas());
                ps.setString(7, consultaMedica.getDiagnostico());
                ps.setString(8, consultaMedica.getRecomendaciones());
                ps.setString(9, consultaMedica.getObservaciones());
                ps.setDouble(10, consultaMedica.getPeso_registrado());
                ps.setDouble(11, consultaMedica.getTemperatura());

                return ps.executeUpdate() > 0;
            } catch (SQLException e) {
                System.out.println("Error al agregar consulta médica: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }

        @Override
        public boolean modificarConsultaMedica(ConsultaMedica consultaMedica) {
            String sql = "UPDATE consultas_medicas SET mascota_id = ?, veterinario_id = ?, cita_id = ?, " +
                    "fecha_hora = ?, motivo = ?, sintomas = ?, diagnostico = ?, recomendaciones = ?, " +
                    "observaciones = ?, peso_registrado = ?, temperatura = ? WHERE id = ?";

            try (Connection con = DatabaseConfig.getConexion();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, consultaMedica.getMascota_id());
                ps.setInt(2, consultaMedica.getVeterinario_id());

                // Manejar cita_id como NULL si es 0
                if (consultaMedica.getCita_id() > 0) {
                    ps.setInt(3, consultaMedica.getCita_id());
                } else {
                    ps.setNull(3, Types.INTEGER);
                }

                ps.setTimestamp(4, Timestamp.valueOf(consultaMedica.getFecha_hora()));
                ps.setString(5, consultaMedica.getMotivo());
                ps.setString(6, consultaMedica.getSintomas());
                ps.setString(7, consultaMedica.getDiagnostico());
                ps.setString(8, consultaMedica.getRecomendaciones());
                ps.setString(9, consultaMedica.getObservaciones());
                ps.setDouble(10, consultaMedica.getPeso_registrado());
                ps.setDouble(11, consultaMedica.getTemperatura());
                ps.setInt(12, consultaMedica.getId());

                return ps.executeUpdate() > 0;
            } catch (SQLException e) {
                System.out.println("Error al modificar consulta médica: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }

        @Override
        public List<ConsultaMedica> listarConsultasPorMascota(int mascotaId) {
            List<ConsultaMedica> consultas = new ArrayList<>();
            String sql = "SELECT * FROM consultas_medicas WHERE mascota_id = ? ORDER BY fecha_hora DESC";

            try (Connection con = DatabaseConfig.getConexion();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, mascotaId);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    consultas.add(mapearConsultaMedica(rs));
                }
            } catch (SQLException e) {
                System.out.println("Error al listar consultas por mascota: " + e.getMessage());
                e.printStackTrace();
            }
            return consultas;
        }

        @Override
        public List<ConsultaMedica> listarConsultasPorVeterinario(int veterinarioId) {
            List<ConsultaMedica> consultas = new ArrayList<>();
            String sql = "SELECT * FROM consultas_medicas WHERE veterinario_id = ? ORDER BY fecha_hora DESC";

            try (Connection con = DatabaseConfig.getConexion();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, veterinarioId);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    consultas.add(mapearConsultaMedica(rs));
                }
            } catch (SQLException e) {
                System.out.println("Error al listar consultas por veterinario: " + e.getMessage());
                e.printStackTrace();
            }
            return consultas;
        }

        @Override
        public List<ConsultaMedica> listarConsultasPorFecha(LocalDate fechaInicio, LocalDate fechaFin) {
            List<ConsultaMedica> consultas = new ArrayList<>();
            String sql = "SELECT * FROM consultas_medicas WHERE DATE(fecha_hora) BETWEEN ? AND ? " +
                    "ORDER BY fecha_hora DESC";

            try (Connection con = DatabaseConfig.getConexion();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setDate(1, Date.valueOf(fechaInicio));
                ps.setDate(2, Date.valueOf(fechaFin));
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    consultas.add(mapearConsultaMedica(rs));
                }
            } catch (SQLException e) {
                System.out.println("Error al listar consultas por fecha: " + e.getMessage());
                e.printStackTrace();
            }
            return consultas;
        }

        @Override
        public List<ConsultaMedica> listarConsultasPorCita(int citaId) {
            List<ConsultaMedica> consultas = new ArrayList<>();
            String sql = "SELECT * FROM consultas_medicas WHERE cita_id = ? ORDER BY fecha_hora DESC";

            try (Connection con = DatabaseConfig.getConexion();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, citaId);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    consultas.add(mapearConsultaMedica(rs));
                }
            } catch (SQLException e) {
                System.out.println("Error al listar consultas por cita: " + e.getMessage());
                e.printStackTrace();
            }
            return consultas;
        }

        /**
         * Mapea un ResultSet a un objeto ConsultaMedica
         * @param rs ResultSet de la consulta SQL
         * @return Objeto ConsultaMedica mapeado
         */
        private ConsultaMedica mapearConsultaMedica(ResultSet rs) throws SQLException {
            ConsultaMedica consulta = new ConsultaMedica();

            consulta.setId(rs.getInt("id"));
            consulta.setMascota_id(rs.getInt("mascota_id"));
            consulta.setVeterinario_id(rs.getInt("veterinario_id"));
            consulta.setCita_id(rs.getInt("cita_id"));
            consulta.setFecha_hora(rs.getTimestamp("fecha_hora").toLocalDateTime());
            consulta.setMotivo(rs.getString("motivo"));
            consulta.setSintomas(rs.getString("sintomas"));
            consulta.setDiagnostico(rs.getString("diagnostico"));
            consulta.setRecomendaciones(rs.getString("recomendaciones"));
            consulta.setObservaciones(rs.getString("observaciones"));
            consulta.setPeso_registrado(rs.getDouble("peso_registrado"));
            consulta.setTemperatura(rs.getDouble("temperatura"));

            return consulta;
        }
}
