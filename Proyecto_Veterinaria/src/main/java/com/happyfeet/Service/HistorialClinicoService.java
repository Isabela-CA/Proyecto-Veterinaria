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

    // Tipos de eventos médicos como constantes
    public static final int TIPO_VACUNACION = 1;
    public static final int TIPO_CONSULTA_GENERAL = 2;
    public static final int TIPO_CIRUGIA = 3;
    public static final int TIPO_CONTROL_RUTINA = 4;
    public static final int TIPO_EMERGENCIA = 5;
    public static final int TIPO_OTRO = 6;

    public HistorialClinicoService(IHistorialClinicoDAO historialDAO,
                                   IMascotaDAO mascotaDAO,
                                   IVeterinarioDAO veterinarioDAO) {
        this.historialDAO = historialDAO;
        this.mascotaDAO = mascotaDAO;
        this.veterinarioDAO = veterinarioDAO;
    }

    /**
     * Agrega un nuevo evento médico al historial
     */
    public boolean agregarEventoMedico(HistorialClinico historial) {
        validarRelaciones(historial);
        validarDatosHistorial(historial);

        // Establecer fecha actual si no se especifica
        if (historial.getFecha_evento() == null) {
            historial.setFecha_evento(LocalDate.now());
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
     * Obtiene un registro específico del historial
     */
    public HistorialClinico obtenerHistorial(int id) {
        return historialDAO.buscarHistorialClinicoPorId(id);
    }

    /**
     * Lista todos los historiales clínicos
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

    /**
     * Registra una vacunación (método especializado)
     */
    public boolean registrarVacunacion(int mascotaId, int veterinarioId,
                                       String vacuna, String lote) {
        var historial = new HistorialClinico();
        historial.setMascota_id(mascotaId);
        historial.setVeterinario_id(veterinarioId);
        historial.setFecha_evento(LocalDate.now());
        historial.setEvento_tipo_id(TIPO_VACUNACION);
        historial.setDescripcion("Vacunación: " + vacuna);
        historial.setDiagnostico("Prevención");
        historial.setTratamiento_recomendado(
                "Lote: %s. Próxima dosis según calendario.".formatted(lote)
        );

        return agregarEventoMedico(historial);
    }

    /**
     * Registra una consulta general (método especializado)
     */
    public boolean registrarConsultaGeneral(int mascotaId, int veterinarioId,
                                            String motivo, String diagnostico,
                                            String tratamiento) {
        var historial = new HistorialClinico();
        historial.setMascota_id(mascotaId);
        historial.setVeterinario_id(veterinarioId);
        historial.setFecha_evento(LocalDate.now());
        historial.setEvento_tipo_id(TIPO_CONSULTA_GENERAL);
        historial.setDescripcion(motivo);
        historial.setDiagnostico(diagnostico);
        historial.setTratamiento_recomendado(tratamiento);

        return agregarEventoMedico(historial);
    }

    /**
     * Obtiene el historial filtrado por tipo de evento
     */
    public List<HistorialClinico> consultarPorTipoEvento(int mascotaId, int tipoEvento) {
        return consultarHistorialPorMascota(mascotaId).stream()
                .filter(h -> h.getEvento_tipo_id() == tipoEvento)
                .toList();
    }

    /**
     * Obtiene el historial en un rango de fechas
     */
    public List<HistorialClinico> consultarPorRangoFechas(int mascotaId,
                                                          LocalDate fechaInicio,
                                                          LocalDate fechaFin) {
        return consultarHistorialPorMascota(mascotaId).stream()
                .filter(h -> !h.getFecha_evento().isBefore(fechaInicio) &&
                        !h.getFecha_evento().isAfter(fechaFin))
                .toList();
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
        if (veterinarioDAO != null) {
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

    /**
     * Obtiene el nombre del tipo de evento
     */
    public static String obtenerNombreTipoEvento(int tipoId) {
        return switch (tipoId) {
            case TIPO_VACUNACION -> "Vacunación";
            case TIPO_CONSULTA_GENERAL -> "Consulta General";
            case TIPO_CIRUGIA -> "Cirugía";
            case TIPO_CONTROL_RUTINA -> "Control de Rutina";
            case TIPO_EMERGENCIA -> "Emergencia";
            case TIPO_OTRO -> "Otro";
            default -> "Tipo Desconocido";
        };
    }
}