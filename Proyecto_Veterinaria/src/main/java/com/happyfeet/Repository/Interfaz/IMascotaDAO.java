package com.happyfeet.Repository.Interfaz;

import com.happyfeet.model.entities.Mascota;

import java.util.List;

public interface IMascotaDAO {
    List<Mascota> listarMacota();
    Mascota buscarMascotaPorId(int id);  // Cambiado para retornar Mascota
    boolean agregarMascota(Mascota mascota);
    boolean modificarMascota(Mascota mascota);
    List<Mascota> listarMascotasPorDueno(int duenoId);  // Para la relación
    boolean transferirMascota(int mascotaId, int nuevoDuenoId);
}
