package com.happyfeet.Repository;

import com.happyfeet.Repository.Interfaz.IVeterinarioDAO;
import com.happyfeet.config.DatabaseConfig;
import com.happyfeet.model.entities.Veterinario;
import com.happyfeet.model.enums.EspecialidadVeterinario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeterinarioDAO implements IVeterinarioDAO {

    @Override
    public List<Veterinario> listarVeterinario() {
        List<Veterinario> veterinarios = new ArrayList<>();
        String sql = "SELECT * FROM veterinarios ORDER BY nombre_completo";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                veterinarios.add(mapearVeterinario(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar los veterinarios: " + e.getMessage());
            e.printStackTrace();
        }
        return veterinarios;
    }

    @Override
    public Veterinario buscarVeterinarioPorId(int id) {
        String sql = "SELECT * FROM veterinarios WHERE id = ?";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearVeterinario(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar veterinario por id: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean agregarVeterinario(Veterinario veterinario) {
        String sql = "INSERT INTO veterinarios(documento_identidad, nombre_completo, especialidad, telefono, email, fecha_contratacion, activo) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, veterinario.getDocumento_identidad());
            ps.setString(2, veterinario.getNombre_completo());
            ps.setString(3, veterinario.getEspecialidad().toString());
            ps.setString(4, veterinario.getTelefono());
            ps.setString(5, veterinario.getEmail());
            ps.setDate(6, Date.valueOf(veterinario.getFetch_contradiction()));
            ps.setInt(7, veterinario.getActivo());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al agregar el veterinario: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean modificarVeterinario(Veterinario veterinario) {
        String sql = "UPDATE veterinarios SET documento_identidad = ?, nombre_completo = ?, especialidad = ?, " +
                "telefono = ?, email = ?, fecha_contratacion = ?, activo = ? WHERE id = ?";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, veterinario.getDocumento_identidad());
            ps.setString(2, veterinario.getNombre_completo());
            ps.setString(3, veterinario.getEspecialidad().toString());
            ps.setString(4, veterinario.getTelefono());
            ps.setString(5, veterinario.getEmail());
            ps.setDate(6, Date.valueOf(veterinario.getFetch_contradiction()));
            ps.setInt(7, veterinario.getActivo());
            ps.setInt(8, veterinario.getId());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al modificar el veterinario: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Veterinario> listarVeterinariosActivos() {
        List<Veterinario> veterinarios = new ArrayList<>();
        String sql = "SELECT * FROM veterinarios WHERE activo = 1 ORDER BY nombre_completo";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                veterinarios.add(mapearVeterinario(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar veterinarios activos: " + e.getMessage());
            e.printStackTrace();
        }
        return veterinarios;
    }

    @Override
    public List<Veterinario> listarVeterinariosPorEspecialidad(EspecialidadVeterinario.Especialidad especialidad) {
        List<Veterinario> veterinarios = new ArrayList<>();
        String sql = "SELECT * FROM veterinarios WHERE especialidad = ? AND activo = 1 ORDER BY nombre_completo";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, especialidad.toString());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    veterinarios.add(mapearVeterinario(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar veterinarios por especialidad: " + e.getMessage());
            e.printStackTrace();
        }
        return veterinarios;
    }

    // Método auxiliar para mapear ResultSet a Veterinario
    private Veterinario mapearVeterinario(ResultSet rs) throws SQLException {
        var veterinario = new Veterinario();
        veterinario.setId(rs.getInt("id"));
        veterinario.setDocumento_identidad(rs.getString("documento_identidad"));
        veterinario.setNombre_completo(rs.getString("nombre_completo"));
        veterinario.setEspecialidad(EspecialidadVeterinario.Especialidad.fromString(rs.getString("especialidad")));
        veterinario.setTelefono(rs.getString("telefono"));
        veterinario.setEmail(rs.getString("email"));
        veterinario.setFetch_contradiction(String.valueOf(rs.getDate("fecha_contratacion").toLocalDate()));
        veterinario.setActivo(rs.getInt("activo"));
        return veterinario;
    }
}
