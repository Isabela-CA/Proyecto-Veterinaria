package com.happyfeet.Repository;

import com.happyfeet.Repository.Interfaz.ICitaEstadoDAO;
import com.happyfeet.config.DatabaseConfig;
import com.happyfeet.model.entities.CitaEstado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CitaEstadoDAO implements ICitaEstadoDAO {

    @Override
    public List<CitaEstado> listarCitaEstados() {
        List<CitaEstado> estados = new ArrayList<>();
        String sql = "SELECT * FROM cita_estados ORDER BY id";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                var estado = new CitaEstado();
                estado.setId(rs.getInt("id"));
                estado.setNombre(rs.getString("nombre"));
                estados.add(estado);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar los estados de cita: " + e.getMessage());
            e.printStackTrace();
        }
        return estados;
    }

    @Override
    public boolean buscarCitaEstado(CitaEstado citaEstado) {
        PreparedStatement ps;
        ResultSet rs;
        var con = DatabaseConfig.getConexion();
        String sql;
        sql = "SELECT * FROM cita_estados WHERE id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, citaEstado.getId());
            rs = ps.executeQuery();
            if (rs.next()) {
                citaEstado.setId(rs.getInt("id"));
                citaEstado.setNombre(rs.getString("nombre"));
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error al buscar el estado de cita por id: " + e.getMessage());
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
    public boolean agregarCitaEstado(CitaEstado citaEstado) {
        String sql = "INSERT INTO cita_estados(nombre) VALUES(?)";
        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, citaEstado.getNombre());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al agregar el estado de cita: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean modificarCitaEstado(CitaEstado citaEstado) {
        String sql;
        sql = "UPDATE cita_estados SET nombre = ? WHERE id = ?";

        try (Connection con = DatabaseConfig.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, citaEstado.getNombre());
            ps.setInt(2, citaEstado.getId());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al modificar el estado de cita: " + e.getMessage());
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
            return false;
        }
    }

}
