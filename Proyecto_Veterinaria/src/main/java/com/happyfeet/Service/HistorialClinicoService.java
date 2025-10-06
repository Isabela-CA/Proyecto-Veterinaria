package com.happyfeet.Service;

import com.happyfeet.Repository.Interfaz.IHistorialClinicoDAO;
import com.happyfeet.Repository.Interfaz.IMascotaDAO;
import com.happyfeet.Repository.Interfaz.IVeterinarioDAO;
import com.happyfeet.model.entities.HistorialClinico;

import java.time.LocalDate;
import java.util.List;

public class HistorialClinicoService {

    private final IHistorialClinicoDAO historialDAO;
    private final IMascotaDAO mascotaDAO;
    private final IVeterinarioDAO veterinarioDAO;

    // Constructor con inyección de dependencias
    public HistorialClinicoService(IHistorialClinicoDAO historialDAO,
                                   IMascotaDAO mascotaDAO,
                                   IVeterinarioDAO veterinarioDAO) {
        this.historialDAO = historialDAO;
        this.mascotaDAO = mascotaDAO;
        this.veterinarioDAO = veterinarioDAO;
    }

    /**
     * Registra un nuevo historial clínico completo
     * Este es el método principal para crear registros de historial
     */
    public boolean agregarHistorialClinico(HistorialClinico historial) {
        // Validaciones
        validarRelaciones(historial);
        validarDatosHistorial(historial);

        // Si no tiene fecha, usar fecha actual
        if (historial.getFecha_evento() == null) {
            historial.setFecha_evento(LocalDate.now());
        }

        // Normalizar campos de texto vacíos a NULL
        if (historial.getDiagnostico() != null && historial.getDiagnostico().isBlank()) {
            historial.setDiagnostico(null);
        }
        if (historial.getTratamiento_recomendado() != null && historial.getTratamiento_recomendado().isBlank()) {
            historial.setTratamiento_recomendado(null);
        }

        return historialDAO.agregarHistorialClinico(historial);
    }

    /**
     * Consulta el historial completo de una mascota
     */
    public List<HistorialClinico> consultarHistorialPorMascota(int mascotaId) {
        var mascota = mascotaDAO.buscarMascotaPorId(mascotaId);
        if (mascota == null) {
            throw new IllegalArgumentException(
                    "La mascota especificada no existe (ID: %d)".formatted(mascotaId)
            );
        }

        return historialDAO.listarHistorialPorMascota(mascotaId);
    }

    /**
     * Obtiene un historial específico por ID
     */
    public HistorialClinico buscarHistorialClinicoPorId(int id) {
        return historialDAO.buscarHistorialClinicoPorId(id);
    }

    /**
     * Lista todos los historiales
     */
    public List<HistorialClinico> listarTodosLosHistoriales() {
        return historialDAO.listarHistorialClinico();
    }

    /**
     * Actualiza un registro del historial
     */
    public boolean actualizarHistorial(HistorialClinico historial) {
        if (historial.getId() <= 0) {
            throw new IllegalArgumentException("ID de historial inválido");
        }

        validarRelaciones(historial);
        validarDatosHistorial(historial);

        return historialDAO.modificarHistorialClinico(historial);
    }


    // Métodos de validación privados

    private void validarRelaciones(HistorialClinico historial) {
        var mascota = mascotaDAO.buscarMascotaPorId(historial.getMascota_id());
        if (mascota == null) {
            throw new IllegalArgumentException(
                    "La mascota especificada no existe (ID: %d)"
                            .formatted(historial.getMascota_id())
            );
        }

        // Validación de veterinario si es necesario
        if (historial.getVeterinario_id() > 0 && veterinarioDAO != null) {
            var veterinario = veterinarioDAO.buscarVeterinarioPorId(
                    historial.getVeterinario_id()
            );
            if (veterinario == null) {
                throw new IllegalArgumentException(
                        "El veterinario especificado no existe (ID: %d)"
                                .formatted(historial.getVeterinario_id())
                );
            }
        }
    }

    private void validarDatosHistorial(HistorialClinico historial) {
        if (historial.getDescripcion() == null || historial.getDescripcion().isBlank()) {
            throw new IllegalArgumentException(
                    "La descripción del evento médico no puede estar vacía"
            );
        }

        if (historial.getFecha_evento() != null &&
                historial.getFecha_evento().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "La fecha del evento no puede ser futura"
            );
        }

        if (historial.getEvento_tipo_id() <= 0) {
            throw new IllegalArgumentException(
                    "Debe especificar un tipo de evento válido"
            );
        }

        if (historial.getMascota_id() <= 0) {
            throw new IllegalArgumentException(
                    "El ID de mascota debe ser válido"
            );
        }

        if (historial.getVeterinario_id() <= 0) {
            throw new IllegalArgumentException(
                    "El ID de veterinario debe ser válido"
            );
        }
    }
}