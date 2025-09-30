package com.happyfeet.Service;

import com.happyfeet.Repository.Interfaz.IDuenosDAO;
import com.happyfeet.Repository.Interfaz.IMascotaDAO;
import com.happyfeet.model.entities.Mascota;

import java.util.List;

public class MascotaService {

    private final IMascotaDAO mascotaDAO;
    private final IDuenosDAO duenosDAO;

    public MascotaService(IMascotaDAO mascotaDAO, IDuenosDAO duenosDAO) {
        this.mascotaDAO = mascotaDAO;
        this.duenosDAO = duenosDAO;
    }

    /**
     * Agrega una nueva mascota con validaciones
     */
    public boolean agregarMascota(Mascota mascota) {
        validarDuenoExiste(mascota.getDueno_id());
        validarNombreMascota(mascota.getNombre());

        return mascotaDAO.agregarMascota(mascota);
    }

    /**
     * Obtiene una mascota por su ID
     */
    public Mascota obtenerMascota(int id) {
        validarIdPositivo(id, "mascota");
        return mascotaDAO.buscarMascotaPorId(id);
    }

    /**
     * Lista todas las mascotas registradas
     */
    public List<Mascota> listarMascotas() {
        return mascotaDAO.listarMacota();
    }

    /**
     * Lista todas las mascotas de un dueño específico
     */
    public List<Mascota> listarMascotasPorDueno(int duenoId) {
        validarIdPositivo(duenoId, "dueño");
        return mascotaDAO.listarMascotasPorDueno(duenoId);
    }

    /**
     * Actualiza los datos de una mascota
     */
    public boolean actualizarMascota(Mascota mascota) {
        validarIdPositivo(mascota.getId(), "mascota");
        validarDuenoExiste(mascota.getDueno_id());
        validarNombreMascota(mascota.getNombre());

        return mascotaDAO.modificarMascota(mascota);
    }

    /**
     * Transfiere una mascota a un nuevo dueño
     */
    public boolean transferirMascota(int mascotaId, int nuevoDuenoId) {
        var mascota = mascotaDAO.buscarMascotaPorId(mascotaId);
        if (mascota == null) {
            throw new IllegalArgumentException(
                    "La mascota especificada no existe (ID: %d)".formatted(mascotaId)
            );
        }

        validarDuenoExiste(nuevoDuenoId);

        return mascotaDAO.transferirMascota(mascotaId, nuevoDuenoId);
    }

    /**
     * Busca mascotas por nombre (búsqueda parcial)
     */
    public List<Mascota> buscarMascotasPorNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre de búsqueda no puede estar vacío"
            );
        }

        return listarMascotas().stream()
                .filter(m -> m.getNombre()
                        .toLowerCase()
                        .contains(nombre.toLowerCase()))
                .toList();
    }

    /**
     * Obtiene estadísticas de mascotas por dueño
     */
    public ResumenMascotas obtenerResumenPorDueno(int duenoId) {
        var mascotas = listarMascotasPorDueno(duenoId);

        long totalMachos = mascotas.stream()
                .filter(m -> "MACHO".equalsIgnoreCase(m.getSexo().toString()))
                .count();

        long totalHembras = mascotas.size() - totalMachos;

        return new ResumenMascotas(
                mascotas.size(),
                (int) totalMachos,
                (int) totalHembras
        );
    }

    /**
     * Verifica si una mascota pertenece a un dueño específico
     */
    public boolean perteneceADueno(int mascotaId, int duenoId) {
        var mascota = obtenerMascota(mascotaId);
        return mascota != null && mascota.getDueno_id() == duenoId;
    }

    // Métodos privados de validación

    private void validarDuenoExiste(int duenoId) {
        if (duenosDAO.buscarDuenosPorId(duenoId) == null) {
            throw new IllegalArgumentException(
                    "El dueño especificado no existe (ID: %d)".formatted(duenoId)
            );
        }
    }

    private void validarNombreMascota(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre de la mascota no puede estar vacío"
            );
        }
    }

    private void validarIdPositivo(int id, String entidad) {
        if (id <= 0) {
            throw new IllegalArgumentException(
                    "ID de %s inválido".formatted(entidad)
            );
        }
    }

    public record ResumenMascotas(
            int totalMascotas,
            int totalMachos,
            int totalHembras
    ) {
        @Override
        public String toString() {
            return """
                Total de mascotas: %d
                Machos: %d
                Hembras: %d
                """.formatted(totalMascotas, totalMachos, totalHembras);
        }
    }
}