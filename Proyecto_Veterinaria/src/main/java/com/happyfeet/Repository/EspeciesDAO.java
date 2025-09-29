package com.happyfeet.Repository;

import com.happyfeet.Repository.Interfaz.IEspecies;
import com.happyfeet.config.DatabaseConfig;
import com.happyfeet.model.entities.Especies;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EspeciesDAO implements IEspecies {

    @Override
    public List<Especies> listarEspecies() {
        List<Especies> especies = new ArrayList<>();
        String sql = "SELECT * FROM especies ORDER BY id";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                var especie = new Especies();
                especie.setId(rs.getInt("id"));
                especie.setNombre(rs.getString("nombre"));
                especies.add(especie);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar las especies: " + e.getMessage());
            e.printStackTrace();
        }
        return especies;
    }

    @Override
    public boolean buscarEspecies(Especies especies) {
        PreparedStatement ps;
        ResultSet rs;
        var con = DatabaseConfig.getConexion();
        var sql = "SELECT * FROM especies WHERE id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, especies.getId());
            rs = ps.executeQuery();
            if (rs.next()) {
                especies.setId(rs.getInt("id"));
                especies.setNombre(rs.getString("nombre"));
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error al buscar la especie por id: " + e.getMessage());
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
    public boolean agregarEspecies(Especies especies) {
        String sql = "INSERT INTO especies(nombre) VALUES(?)";
        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, especies.getNombre());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al agregar la especie: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean modificarEspecies(Especies especies) {
        String sql = "UPDATE especies SET nombre = ? WHERE id = ?";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, especies.getNombre());
            ps.setInt(2, especies.getId());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al modificar la especie: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        EspeciesDAO especiesDao = new EspeciesDAO();


        System.out.println("*** Listar ESPECIES ***");
        var especies = especiesDao.listarEspecies();
        especies.forEach(System.out::println);
    }
}