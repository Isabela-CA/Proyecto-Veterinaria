package com.happyfeet.Repository;

import com.happyfeet.Repository.Interfaz.IVeterinarioDAO;
import com.happyfeet.config.DatabaseConfig;
import com.happyfeet.model.entities.Veterinario;

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
            System.out.println("Error al listar veterinarios: " + e.getMessage());
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
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearVeterinario(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar veterinario por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean agregarVeterinario(Veterinario veterinario) {
        String sql = "INSERT INTO veterinarios(nombre_completo, documento_identidad, licencia_profesional, " +
                "especialidad, telefono, email, fecha_contratacion, activo) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, veterinario.getNombre_completo());
            ps.setString(2, veterinario.getDocumento_identidad());
            ps.setString(3, veterinario.getLicencia_profesional());
            ps.setString(4, veterinario.getEspecialidad());  // YA ES STRING
            ps.setString(5, veterinario.getTelefono());
            ps.setString(6, veterinario.getEmail());
            ps.setDate(7, Date.valueOf(veterinario.getFecha_contratacion()));
            ps.setInt(8, veterinario.getActivo());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al agregar veterinario: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean modificarVeterinario(Veterinario veterinario) {
        String sql = "UPDATE veterinarios SET nombre_completo = ?, documento_identidad = ?, " +
                "licencia_profesional = ?, especialidad = ?, telefono = ?, email = ?, " +
                "fecha_contratacion = ?, activo = ? WHERE id = ?";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, veterinario.getNombre_completo());
            ps.setString(2, veterinario.getDocumento_identidad());
            ps.setString(3, veterinario.getLicencia_profesional());
            ps.setString(4, veterinario.getEspecialidad());  // YA ES STRING
            ps.setString(5, veterinario.getTelefono());
            ps.setString(6, veterinario.getEmail());
            ps.setDate(7, Date.valueOf(veterinario.getFecha_contratacion()));
            ps.setInt(8, veterinario.getActivo());
            ps.setInt(9, veterinario.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al modificar veterinario: " + e.getMessage());
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
    public List<Veterinario> listarVeterinariosPorEspecialidad(String especialidad) {
        List<Veterinario> veterinarios = new ArrayList<>();
        String sql = "SELECT * FROM veterinarios WHERE especialidad = ? AND activo = 1 ORDER BY nombre_completo";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, especialidad);  // YA ES STRING
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                veterinarios.add(mapearVeterinario(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar veterinarios por especialidad: " + e.getMessage());
            e.printStackTrace();
        }
        return veterinarios;
    }

    // Método auxiliar para mapear ResultSet a Veterinario
    private Veterinario mapearVeterinario(ResultSet rs) throws SQLException {
        Veterinario veterinario = new Veterinario();

        veterinario.setId(rs.getInt("id"));
        veterinario.setNombre_completo(rs.getString("nombre_completo"));
        veterinario.setDocumento_identidad(rs.getString("documento_identidad"));
        veterinario.setLicencia_profesional(rs.getString("licencia_profesional"));
        veterinario.setEspecialidad(rs.getString("especialidad"));
        veterinario.setTelefono(rs.getString("telefono"));
        veterinario.setEmail(rs.getString("email"));
        veterinario.setFecha_contratacion(rs.getDate("fecha_contratacion").toLocalDate());
        veterinario.setActivo(rs.getInt("activo"));

        return veterinario;
    }
}