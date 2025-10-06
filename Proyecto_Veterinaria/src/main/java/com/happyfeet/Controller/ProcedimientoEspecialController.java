package com.happyfeet.Controller;

import com.happyfeet.Service.ProcedimientoEspecialService;
import com.happyfeet.model.entities.ProcedimientoEspecial;

import java.time.LocalDate;
import java.util.List;

public class ProcedimientoEspecialController {

    private final ProcedimientoEspecialService procedimientoService;

    // Constructor con inyección de dependencias
    public ProcedimientoEspecialController(ProcedimientoEspecialService procedimientoService) {
        this.procedimientoService = procedimientoService;
    }

    // Constructor sin parámetros
    public ProcedimientoEspecialController() {
        this.procedimientoService = new ProcedimientoEspecialService();
    }

    /**
     * Registra un nuevo procedimiento
     */
    public boolean agregarProcedimiento(ProcedimientoEspecial procedimiento) {
        try {
            return procedimientoService.agregarProcedimiento(procedimiento);
        } catch (IllegalArgumentException e) {
            System.err.println("Error de validación: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Error al agregar procedimiento: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtiene un procedimiento por ID
     */
    public ProcedimientoEspecial obtenerProcedimiento(int id) {
        try {
            return procedimientoService.obtenerProcedimiento(id);
        } catch (Exception e) {
            System.err.println("Error al obtener procedimiento: " + e.getMessage());
            return null;
        }
    }

    /**
     * Lista todos los procedimientos
     */
    public List<ProcedimientoEspecial> listarTodosProcedimientos() {
        try {
            return procedimientoService.listarTodosProcedimientos();
        } catch (Exception e) {
            System.err.println("Error al listar procedimientos: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }

    /**
     * Lista procedimientos por mascota
     */
    public List<ProcedimientoEspecial> listarProcedimientosPorMascota(int mascotaId) {
        try {
            return procedimientoService.listarProcedimientosPorMascota(mascotaId);
        } catch (IllegalArgumentException e) {
            System.err.println("Error de validación: " + e.getMessage());
            return List.of();
        } catch (Exception e) {
            System.err.println("Error al listar procedimientos por mascota: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }

    /**
     * Lista procedimientos por veterinario
     */
    public List<ProcedimientoEspecial> listarProcedimientosPorVeterinario(int veterinarioId) {
        try {
            return procedimientoService.listarProcedimientosPorVeterinario(veterinarioId);
        } catch (IllegalArgumentException e) {
            System.err.println("Error de validación: " + e.getMessage());
            return List.of();
        } catch (Exception e) {
            System.err.println("Error al listar procedimientos por veterinario: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }

    /**
     * Lista procedimientos por estado
     */
    public List<ProcedimientoEspecial> listarProcedimientosPorEstado(String estado) {
        try {
            return procedimientoService.listarProcedimientosPorEstado(estado);
        } catch (IllegalArgumentException e) {
            System.err.println("Error de validación: " + e.getMessage());
            return List.of();
        } catch (Exception e) {
            System.err.println("Error al listar procedimientos por estado: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }

    /**
     * Lista procedimientos por rango de fechas
     */
    public List<ProcedimientoEspecial> listarProcedimientosPorFecha(LocalDate fechaInicio, LocalDate fechaFin) {
        try {
            return procedimientoService.listarProcedimientosPorFecha(fechaInicio, fechaFin);
        } catch (IllegalArgumentException e) {
            System.err.println("Error de validación: " + e.getMessage());
            return List.of();
        } catch (Exception e) {
            System.err.println("Error al listar procedimientos por fecha: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }

    /**
     * Actualiza un procedimiento
     */
    public boolean actualizarProcedimiento(ProcedimientoEspecial procedimiento) {
        try {
            return procedimientoService.actualizarProcedimiento(procedimiento);
        } catch (IllegalArgumentException e) {
            System.err.println("Error de validación: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Error al actualizar procedimiento: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Actualiza seguimiento postoperatorio
     */
    public boolean actualizarSeguimiento(int procedimientoId, String seguimiento,
                                         String complicaciones, LocalDate proximoControl) {
        try {
            return procedimientoService.actualizarSeguimiento(procedimientoId, seguimiento,
                    complicaciones, proximoControl);
        } catch (IllegalArgumentException e) {
            System.err.println("Error de validación: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Error al actualizar seguimiento: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Cambia el estado de un procedimiento
     */
    public boolean cambiarEstado(int procedimientoId, String nuevoEstado) {
        try {
            return procedimientoService.cambiarEstado(procedimientoId, nuevoEstado);
        } catch (IllegalArgumentException e) {
            System.err.println("Error de validación: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Error al cambiar estado: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina un procedimiento
     */
    public boolean eliminarProcedimiento(int id) {
        try {
            return procedimientoService.eliminarProcedimiento(id);
        } catch (IllegalArgumentException e) {
            System.err.println("Error de validación: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Error al eliminar procedimiento: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Genera resumen estadístico
     */
    public ProcedimientoEspecialService.ResumenProcedimientos generarResumen(LocalDate fechaInicio,
                                                                             LocalDate fechaFin) {
        try {
            return procedimientoService.generarResumen(fechaInicio, fechaFin);
        } catch (Exception e) {
            System.err.println("Error al generar resumen: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Obtiene tipos de procedimiento disponibles
     */
    public String[] getTiposProcedimiento() {
        return ProcedimientoEspecialService.TIPOS_PROCEDIMIENTO;
    }

    /**
     * Obtiene estados válidos
     */
    public String[] getEstadosValidos() {
        return new String[]{
                ProcedimientoEspecialService.ESTADO_PROGRAMADO,
                ProcedimientoEspecialService.ESTADO_EN_PROCESO,
                ProcedimientoEspecialService.ESTADO_FINALIZADO,
                ProcedimientoEspecialService.ESTADO_CANCELADO
        };
    }
}