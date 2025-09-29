package com.happyfeet.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConfig {
    public static Connection getConexion() {
        Connection conexion = null;
        var baseDatos = "veterinaria_happy_feet";
        var url = "jdbc:mysql://localhost:3306/" + baseDatos;
        var usuario = "root";
        var password = "1102";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(url, usuario, password);
        } catch (Exception e) {
            System.out.println("Error al conectarnos a la BD: "+ e.getMessage());
            e.printStackTrace();
        }
        return conexion;
    }

    public static void main(String[] args) {
        var conexion = DatabaseConfig.getConexion();
        if(conexion != null){
            System.out.println("Conexion exitosa: "+ conexion);
        } else{
            System.out.println("Error al conectarse");
        }
    }
}
