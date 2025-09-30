package com.happyfeet.Controller;

import com.happyfeet.Service.MascotaService;
import com.happyfeet.model.entities.Mascota;

import java.util.List;

public class MascotaController {
    private final MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    public boolean agregarMascota(Mascota mascota) {
        try {
            return mascotaService.agregarMascota(mascota);
        } catch (Exception e) {
            System.err.println("Error en controller al agregar mascota: " + e.getMessage());
            return false;
        }
    }

    public Mascota obtenerMascota(int id) {
        return mascotaService.obtenerMascota(id);
    }

    public List<Mascota> listarMascotas() {
        return mascotaService.listarMascotas();
    }

    public List<Mascota> listarMascotasPorDueno(int duenoId) {
        return mascotaService.listarMascotasPorDueno(duenoId);
    }

    public boolean actualizarMascota(Mascota mascota) {
        try {
            return mascotaService.actualizarMascota(mascota);
        } catch (Exception e) {
            System.err.println("Error en controller al actualizar mascota: " + e.getMessage());
            return false;
        }
    }


    public boolean transferirMascota(int mascotaId, int nuevoDuenoId) {
        try {
            return mascotaService.transferirMascota(mascotaId, nuevoDuenoId);
        } catch (Exception e) {
            System.err.println("Error en controller al transferir mascota: " + e.getMessage());
            return false;
        }
    }
}

