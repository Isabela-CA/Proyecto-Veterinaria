package com.happyfeet.Service;

import com.happyfeet.Repository.Interfaz.IHistorialClinicoDAO;
import com.happyfeet.Repository.Interfaz.IMascotaDAO;
import com.happyfeet.Repository.Interfaz.IVeterinarioDAO;
import com.happyfeet.model.entities.HistorialClinico;
import com.happyfeet.model.entities.Mascota;

import java.time.LocalDate;
import java.util.List;

public class HistorialClinicoService {
    private IHistorialClinicoDAO historialDAO;
    private IMascotaDAO mascotaDAO;
    private IVeterinarioDAO veterinarioDAO;

    public HistorialClinicoService(IHistorialClinicoDAO historialDAO,
                                   IMascotaDAO mascotaDAO,
                                   IVeterinarioDAO veterinarioDAO) {
        this.historialDAO = historialDAO;
        this.mascotaDAO = mascotaDAO;
        this.veterinarioDAO = veterinarioDAO;
    }

    public boolean agregarEventoMedico(HistorialClinico historial) {
        // Validaciones de integridad referencial
        validarRelaciones(historial);

        // Validaciones de negocio
        validarDatosHistorial(historial);

        // Establecer fecha actual si no se especifica
        if (historial.getFecha_evento() == null) {
            historial.setFecha_evento(LocalDate.now());
        }

        return historialDAO.agregarHistorialClinico(historial);
    }

    public List<HistorialClinico> consultarHistorialPorMascota(int mascotaId) {
        // Validar que la mascota existe
        Mascota mascota = mascotaDAO.buscarMascotaPorId(mascotaId);
        if (mascota == null) {
            throw new IllegalArgumentException("La mascota especificada no existe");
        }

        return historialDAO.listarHistorialPorMascota(mascotaId);
    }

    public HistorialClinico obtenerHistorial(int id) {
        return historialDAO.buscarHistorialClinicoPorId(id);
    }

    public List<HistorialClinico> listarTodosLosHistoriales() {
        return historialDAO.listarHistorialClinico();
    }

    public boolean actualizarHistorial(HistorialClinico historial) {
        if (historial.getId() <= 0) {
            throw new IllegalArgumentException("ID de historial inválido");
        }

        validarRelaciones(historial);
        validarDatosHistorial(historial);

        return historialDAO.modificarHistorialClinico(historial);
    }

    // Método específico para eventos médicos comunes
    public boolean registrarVacunacion(int mascotaId, int veterinarioId, String vacuna, String lote) {
        HistorialClinico historial = new HistorialClinico();
        historial.setMascota_id(mascotaId);
        historial.setVeterinario_id(veterinarioId);
        historial.setFecha_evento(LocalDate.now());
        historial.setEvento_tipo_id(1); // Asumiendo 1 = Vacunación
        historial.setDescripcion("Vacunación: " + vacuna);
        historial.setDiagnostico("Prevención");
        historial.setTratamiento_recomendado("Lote: " + lote + ". Próxima dosis según calendario.");

        return agregarEventoMedico(historial);
    }

    public boolean registrarConsultaGeneral(int mascotaId, int veterinarioId, String motivo,
                                            String diagnostico, String tratamiento) {
        HistorialClinico historial = new HistorialClinico();
        historial.setMascota_id(mascotaId);
        historial.setVeterinario_id(veterinarioId);
        historial.setFecha_evento(LocalDate.now());
        historial.setEvento_tipo_id(2); // Asumiendo 2 = Consulta General
        historial.setDescripcion(motivo);
        historial.setDiagnostico(diagnostico);
        historial.setTratamiento_recomendado(tratamiento);

        return agregarEventoMedico(historial);
    }

    // Validaciones privadas
    private void validarRelaciones(HistorialClinico historial) {
        // Validar que la mascota existe
        if (mascotaDAO.buscarMascotaPorId(historial.getMascota_id()) == null) {
            throw new IllegalArgumentException("La mascota especificada no existe (ID: " + historial.getMascota_id() + ")");
        }

        // Validar que el veterinario existe (si tienes esa entidad)
        // Comentado porque no sabemos si existe IVeterinarioDAO implementado
        /*
        if (veterinarioDAO.buscarVeterinarioPorId(historial.getVeterinario_id()) == null) {
            throw new IllegalArgumentException("El veterinario especificado no existe (ID: " + historial.getVeterinario_id() + ")");
        }
        */
    }

    private void validarDatosHistorial(HistorialClinico historial) {
        if (historial.getDescripcion() == null || historial.getDescripcion().trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción del evento médico no puede estar vacía");
        }

        if (historial.getFecha_evento() != null && historial.getFecha_evento().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha del evento no puede ser futura");
        }

        if (historial.getEvento_tipo_id() <= 0) {
            throw new IllegalArgumentException("Debe especificar un tipo de evento válido");
        }

        if (historial.getMascota_id() <= 0) {
            throw new IllegalArgumentException("El ID de mascota debe ser válido");
        }

        if (historial.getVeterinario_id() <= 0) {
            throw new IllegalArgumentException("El ID de veterinario debe ser válido");
        }
    }
}
