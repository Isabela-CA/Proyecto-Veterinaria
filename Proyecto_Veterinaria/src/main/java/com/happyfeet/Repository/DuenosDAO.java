package com.happyfeet.Repository;

import com.happyfeet.Repository.Interfaz.IDuenosDAO;
import com.happyfeet.config.DatabaseConfig;
import com.happyfeet.model.entities.Duenos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Repository
public class DuenosDAO implements IDuenosDAO {

    @Override
    public List<Duenos> listarDuenos() {
        List<Duenos> duenos = new ArrayList<>();
        String sql = "SELECT * FROM duenos ORDER BY id";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Duenos dueno = new Duenos();
                dueno.setId(rs.getInt("id"));
                dueno.setNombre_completo(rs.getString("nombre_completo"));
                dueno.setDocumento_identidad(rs.getString("documento_identidad"));
                dueno.setDireccion(rs.getString("direccion"));
                dueno.setTelefono(rs.getString("telefono"));
                dueno.setEmail(rs.getString("email"));
                dueno.setContacto_emergencia(rs.getString("contacto_emergencia"));
                duenos.add(dueno);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar los duenos: " + e.getMessage());
            e.printStackTrace();
        }
        return duenos;
    }

    @Override
    public Duenos buscarDuenosPorId(int id) {
        String sql = "SELECT * FROM duenos WHERE id = ?";
        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Duenos dueno = new Duenos();
                    dueno.setId(rs.getInt("id"));
                    dueno.setNombre_completo(rs.getString("nombre_completo"));
                    dueno.setDocumento_identidad(rs.getString("documento_identidad"));
                    dueno.setDireccion(rs.getString("direccion"));
                    dueno.setTelefono(rs.getString("telefono"));
                    dueno.setEmail(rs.getString("email"));
                    dueno.setContacto_emergencia(rs.getString("contacto_emergencia"));
                    return dueno;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar dueno por id: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean agregarDuenos(Duenos duenos) {
        String sql = "INSERT INTO duenos(nombre_completo, documento_identidad, direccion, telefono, email, contacto_emergencia) " +
                "VALUES(?, ?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, duenos.getNombre_completo());
            ps.setString(2, duenos.getDocumento_identidad());
            ps.setString(3, duenos.getDireccion());
            ps.setString(4, duenos.getTelefono());
            ps.setString(5, duenos.getEmail());
            ps.setString(6, duenos.getContacto_emergencia());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al agregar dueno: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean modificarDuenos(Duenos duenos) {
        String sql = "UPDATE duenos SET nombre_completo=?, documento_identidad=?, direccion=?, telefono=?, email=?, contacto_emergencia=? " +
                "WHERE id = ?";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, duenos.getNombre_completo());
            ps.setString(2, duenos.getDocumento_identidad());
            ps.setString(3, duenos.getDireccion());
            ps.setString(4, duenos.getTelefono());
            ps.setString(5, duenos.getEmail());
            ps.setString(6, duenos.getContacto_emergencia());
            ps.setInt(7, duenos.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al modificar dueno: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
