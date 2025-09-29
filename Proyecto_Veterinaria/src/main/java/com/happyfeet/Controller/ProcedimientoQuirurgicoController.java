package com.happyfeet.Controller;

import com.happyfeet.Service.ProcedimientoQuirurgicoService;
import com.happyfeet.model.entities.ProcedimientoQuirurgico;

import java.time.LocalDateTime;
import java.util.List;

public class ProcedimientoQuirurgicoController {

    private ProcedimientoQuirurgicoService procedimientoService;

    public ProcedimientoQuirurgicoController() {
        // CORREGIDO: Inicializar correctamente el servicio
        this.procedimientoService = new ProcedimientoQuirurgicoService();
    }

    public ProcedimientoQuirurgicoController(ProcedimientoQuirurgicoService procedimientoService) {
        this.procedimientoService = procedimientoService;
    }

    public boolean registrarProcedimiento(int mascotaId, int veterinarioId, Integer citaId,
                                          LocalDateTime fechaProcedimiento, String tipoProcedimiento,
                                          String diagnostico, String descripcion, String anestesia,
                                          String medicacion, String cuidados, String resultado) {
        try {
            return procedimientoService.registrarProcedimiento(mascotaId, veterinarioId, citaId,
                    fechaProcedimiento, tipoProcedimiento, diagnostico, descripcion,
                    anestesia, medicacion, cuidados, resultado);
        } catch (Exception e) {
            System.err.println("Error en controller al registrar procedimiento: " + e.getMessage());
            return false;
        }
    }

    public List<ProcedimientoQuirurgico> listarTodosLosProcedimientos() {
        try {
            return procedimientoService.listarTodosLosProcedimientos();
        } catch (Exception e) {
            System.err.println("Error en controller al listar procedimientos: " + e.getMessage());
            return List.of();
        }
    }

    public ProcedimientoQuirurgico buscarProcedimientoPorId(int id) {
        try {
            return procedimientoService.buscarProcedimientoPorId(id);
        } catch (Exception e) {
            System.err.println("Error en controller al buscar procedimiento: " + e.getMessage());
            return null;
        }
    }

    public boolean modificarProcedimiento(ProcedimientoQuirurgico procedimiento) {
        try {
            return procedimientoService.modificarProcedimiento(procedimiento);
        } catch (Exception e) {
            System.err.println("Error en controller al modificar procedimiento: " + e.getMessage());
            return false;
        }
    }

    public List<ProcedimientoQuirurgico> consultarProcedimientosPorMascota(int mascotaId) {
        try {
            return procedimientoService.consultarProcedimientosPorMascota(mascotaId);
        } catch (Exception e) {
            System.err.println("Error en controller al consultar por mascota: " + e.getMessage());
            return List.of();
        }
    }

    public List<ProcedimientoQuirurgico> consultarProcedimientosPorVeterinario(int veterinarioId) {
        try {
            return procedimientoService.consultarProcedimientosPorVeterinario(veterinarioId);
        } catch (Exception e) {
            System.err.println("Error en controller al consultar por veterinario: " + e.getMessage());
            return List.of();
        }
    }

    public boolean actualizarSeguimiento(int procedimientoId, String nuevoResultado,
                                         String cuidadosActualizados, String medicacionActualizada) {
        try {
            return procedimientoService.actualizarSeguimientoPostoperatorio(procedimientoId,
                    nuevoResultado, cuidadosActualizados, medicacionActualizada);
        } catch (Exception e) {
            System.err.println("Error en controller al actualizar seguimiento: " + e.getMessage());
            return false;
        }
    }

    public void generarReporte(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        try {
            procedimientoService.generarReporteProcedimientos(fechaInicio, fechaFin);
        } catch (Exception e) {
            System.err.println("Error en controller al generar reporte: " + e.getMessage());
        }
    }

    public String[] getTiposProcedimiento() {
        return ProcedimientoQuirurgicoService.TIPOS_PROCEDIMIENTO;
    }
}
