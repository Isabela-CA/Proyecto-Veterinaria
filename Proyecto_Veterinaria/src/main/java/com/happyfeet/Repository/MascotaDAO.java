package com.happyfeet.Repository;

import com.happyfeet.Repository.Interfaz.IMascotaDAO;
import com.happyfeet.config.DatabaseConfig;
import com.happyfeet.model.entities.Mascota;
import com.happyfeet.model.enums.SexoMascota;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MascotaDAO implements IMascotaDAO {
        @Override
        public List<Mascota> listarMacota() {
            List<Mascota> mascotas = new ArrayList<>();
            String sql;
            sql = "SELECT * FROM mascotas ORDER BY fecha_registro asc";

            try (Connection con = DatabaseConfig.getConexion();
                 PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    mascotas.add(mapearMascota(rs));
                }
            } catch (SQLException e) {
                System.out.println("Error al listar las mascotas: " + e.getMessage());
                e.printStackTrace();
            }
            return mascotas;
        }

        @Override
        public Mascota buscarMascotaPorId(int id) {
            String sql;
            sql = "SELECT * FROM mascotas WHERE id = ?";

            try (Connection con = DatabaseConfig.getConexion();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, id);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return mapearMascota(rs);
                    }
                }
            } catch (SQLException e) {
                System.out.println("Error al buscar mascota por id: " + e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

    @Override
        public boolean agregarMascota(Mascota mascota) {
            String sql;
            sql = "INSERT INTO mascotas(dueno_id, nombre, raza_id, fecha_nacimiento, sexo, peso_actual, microchip, tatuaje, url_foto, alergias, condiciones_preexistentes) " +
                    "VALUES(?, ?, ?, ?, ?, ?,?,?, ?, ?,?)";

            try (Connection con = DatabaseConfig.getConexion();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, mascota.getDueno_id());
                ps.setString(2, mascota.getNombre());
                ps.setInt(3, mascota.getRaza_id());
                ps.setDate(4, Date.valueOf(mascota.getFecha_nacimiento()));
                ps.setString(5, mascota.getSexo().toString());
                ps.setDouble(6, mascota.getPeso_actual());
                ps.setString(7, mascota.getMicrochip());
                ps.setString(8, mascota.getTatuaje());
                ps.setString(9, mascota.getUrl_foto());
                ps.setString(10, mascota.getAlergias());
                ps.setString(11, mascota.getCondiciones_preexistentes());

                int filasAfectadas = ps.executeUpdate();
                return filasAfectadas > 0;

            } catch (SQLException e) {
                System.out.println("Error al agregar la mascota: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }

        @Override
        public boolean modificarMascota(Mascota mascota) {
            String sql;
            sql = "UPDATE mascotas SET dueno_id = ?, nombre = ?, raza_id = ?, fecha_nacimiento = ?, sexo = ?,peso_actual = ?, microchip =?, tatuaje =?, url_foto =?, alergias =?, condiciones_preexistentes =? WHERE id = ?";

            try (Connection con = DatabaseConfig.getConexion();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, mascota.getDueno_id());
                ps.setString(2, mascota.getNombre());
                ps.setInt(3, mascota.getRaza_id());
                ps.setDate(4, Date.valueOf(mascota.getFecha_nacimiento()));
                ps.setString(5, mascota.getSexo().toString());
                ps.setDouble(6, mascota.getPeso_actual());
                ps.setString(7, mascota.getMicrochip());
                ps.setString(8, mascota.getTatuaje());
                ps.setString(9, mascota.getUrl_foto());
                ps.setString(10, mascota.getAlergias());
                ps.setString(11, mascota.getCondiciones_preexistentes());
                ps.setInt(12, mascota.getId());

                int filasAfectadas = ps.executeUpdate();
                return filasAfectadas > 0;

            } catch (SQLException e) {
                System.out.println("Error al modificar la mascota: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }

        @Override
        public List<Mascota> listarMascotasPorDueno(int duenoId) {
            List<Mascota> mascotas = new ArrayList<>();
            String sql;
            sql = "SELECT * FROM mascotas WHERE dueno_id = ? ORDER BY fecha_registro";

            try (Connection con = DatabaseConfig.getConexion();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, duenoId);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        mascotas.add(mapearMascota(rs));
                    }
                }
            } catch (SQLException e) {
                System.out.println("Error al listar mascotas por dueno: " + e.getMessage());
                e.printStackTrace();
            }
            return mascotas;
        }

        @Override
        public boolean transferirMascota(int mascotaId, int nuevoDuenoId) {
            String sql;
            sql = "UPDATE mascotas SET dueno_id = ? WHERE id = ?";

            try (Connection con = DatabaseConfig.getConexion();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, nuevoDuenoId);
                ps.setInt(2, mascotaId);

                int filasAfectadas = ps.executeUpdate();
                return filasAfectadas > 0;

            } catch (SQLException e) {
                System.out.println("Error al transferir mascota: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }

        private Mascota mapearMascota(ResultSet rs) throws SQLException {
            Mascota mascota = new Mascota();
            mascota.setId(rs.getInt("id"));
            mascota.setDueno_id(rs.getInt("dueno_id"));
            mascota.setNombre(rs.getString("nombre"));
            mascota.setRaza_id(rs.getInt("raza_id"));
            mascota.setFecha_nacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
            mascota.setSexo(SexoMascota.Sexo.fromString(rs.getString("sexo")));
            mascota.setPeso_actual(rs.getDouble("peso_actual"));
            mascota.setMicrochip(rs.getString("microchip"));
            mascota.setTatuaje(rs.getString("tatuaje"));
            mascota.setUrl_foto(rs.getString  ("url_foto"));
            mascota.setAlergias(rs.getString("alergias"));
            mascota.setCondiciones_preexistentes(rs.getString("condiciones_preexistentes"));
            return mascota;
        }
    }


