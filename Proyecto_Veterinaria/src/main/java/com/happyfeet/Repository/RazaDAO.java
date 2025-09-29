package com.happyfeet.Repository;

import com.happyfeet.Repository.Interfaz.IRazaDAO;
import com.happyfeet.config.DatabaseConfig;
import com.happyfeet.model.entities.Raza;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RazaDAO implements IRazaDAO {
    @Override
    public List<Raza> listarRaza() {
        List<Raza> razas = new ArrayList<>();
        String sql = "SELECT * FROM razas ORDER BY id";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                var raza = new Raza();
                raza.setId(rs.getInt("id"));
                raza.setEspecie_id(rs.getInt("especie_id"));
                raza.setNombre(rs.getString("nombre"));
                razas.add(raza);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar las razas: " + e.getMessage());
            e.printStackTrace();
        }
        return razas;
    }

    @Override
    public boolean buscarRaza(Raza raza) {
        PreparedStatement ps;
        ResultSet rs;
        var con = DatabaseConfig.getConexion();
        var sql = "SELECT * FROM razas WHERE id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, raza.getId());
            rs = ps.executeQuery();
            if (rs.next()) {
                raza.setId(rs.getInt("id"));
                raza.setEspecie_id(rs.getInt("especie_id"));
                raza.setNombre(rs.getString("nombre"));
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error al buscar la raza por id: " + e.getMessage());
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar conexion: " + e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean agregarRaza(Raza raza) {
        String sql = "INSERT INTO razas(especie_id, nombre) VALUES(?, ?)";
        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, raza.getEspecie_id());
            ps.setString(2, raza.getNombre());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al agregar la raza: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean modificarRaza(Raza raza) {
        String sql = "UPDATE razas SET especie_id = ?, nombre = ? WHERE id = ?";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, raza.getEspecie_id());
            ps.setString(2, raza.getNombre());
            ps.setInt(3, raza.getId());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al modificar la raza: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    // Método para listar razas por especie
    public List<Raza> listarRazasPorEspecie(int especieId) {
        List<Raza> razas = new ArrayList<>();
        String sql = "SELECT * FROM razas WHERE especie_id = ? ORDER BY nombre";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, especieId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                var raza = new Raza();
                raza.setId(rs.getInt("id"));
                raza.setEspecie_id(rs.getInt("especie_id"));
                raza.setNombre(rs.getString("nombre"));
                razas.add(raza);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar las razas por especie: " + e.getMessage());
            e.printStackTrace();
        }
        return razas;
    }

    public static void main(String[] args) {
        RazaDAO razaDao = new RazaDAO();


        // Ejemplo: Listar razas por especie
        System.out.println("\n*** Listar RAZAS por Especie (ID: 1) ***");
        var razasPorEspecie = razaDao.listarRazasPorEspecie(1);
        razasPorEspecie.forEach(System.out::println);
    }
}
