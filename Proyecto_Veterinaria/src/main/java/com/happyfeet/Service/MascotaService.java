package com.happyfeet.Service;

import com.happyfeet.Repository.Interfaz.IDuenosDAO;
import com.happyfeet.Repository.Interfaz.IMascotaDAO;
import com.happyfeet.model.entities.Mascota;

import java.util.List;

public class MascotaService {
    private IMascotaDAO mascotaDAO;
    private IDuenosDAO duenosDAO;

    public MascotaService(IMascotaDAO mascotaDAO, IDuenosDAO duenosDAO) {
        this.mascotaDAO = mascotaDAO;
        this.duenosDAO = duenosDAO;
    }

    public boolean agregarMascota(Mascota mascota) {
        // Validar que el dueño existe
        if (duenosDAO.buscarDuenosPorId(mascota.getDueno_id()) == null) {
            throw new IllegalArgumentException("El dueño especificado no existe");
        }

        // Validaciones de negocio
        if (mascota.getNombre() == null || mascota.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la mascota no puede estar vacío");
        }

        return mascotaDAO.agregarMascota(mascota);
    }

    public Mascota obtenerMascota(int id) {
        return mascotaDAO.buscarMascotaPorId(id);
    }

    public List<Mascota> listarMascotas() {
        return mascotaDAO.listarMacota();
    }

    public List<Mascota> listarMascotasPorDueno(int duenoId) {
        return mascotaDAO.listarMascotasPorDueno(duenoId);
    }

    public boolean actualizarMascota(Mascota mascota) {
        if (mascota.getId() <= 0) {
            throw new IllegalArgumentException("ID de mascota inválido");
        }

        // Validar que el dueño existe
        if (duenosDAO.buscarDuenosPorId(mascota.getDueno_id()) == null) {
            throw new IllegalArgumentException("El dueño especificado no existe");
        }

        return mascotaDAO.modificarMascota(mascota);
    }

    public boolean transferirMascota(int mascotaId, int nuevoDuenoId) {
        // Validar que la mascota existe
        Mascota mascota = mascotaDAO.buscarMascotaPorId(mascotaId);
        if (mascota == null) {
            throw new IllegalArgumentException("La mascota especificada no existe");
        }

        // Validar que el nuevo dueño existe
        if (duenosDAO.buscarDuenosPorId(nuevoDuenoId) == null) {
            throw new IllegalArgumentException("El nuevo dueño especificado no existe");
        }

        return mascotaDAO.transferirMascota(mascotaId, nuevoDuenoId);
    }
}
