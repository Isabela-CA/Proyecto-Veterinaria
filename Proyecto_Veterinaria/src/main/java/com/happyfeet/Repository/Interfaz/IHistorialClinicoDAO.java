package com.happyfeet.Repository.Interfaz;

import com.happyfeet.model.entities.HistorialClinico;

import java.util.List;

public interface IHistorialClinicoDAO {
    List<HistorialClinico> listarHistorialClinico();
    HistorialClinico buscarHistorialClinicoPorId(int id);  // Cambiar tipo de retorno
    boolean agregarHistorialClinico(HistorialClinico historialClinico);
    boolean modificarHistorialClinico(HistorialClinico historialClinico);
    List<HistorialClinico> listarHistorialPorMascota(int mascotaId);  // Agregar
}
