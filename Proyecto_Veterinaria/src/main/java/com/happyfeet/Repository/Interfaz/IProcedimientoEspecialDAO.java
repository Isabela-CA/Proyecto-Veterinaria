package com.happyfeet.Repository.Interfaz;

import com.happyfeet.model.entities.ProcedimientoEspecial;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface para el acceso a datos de Procedimientos Especiales
 */
public interface IProcedimientoEspecialDAO {
    List<ProcedimientoEspecial> listarProcedimientos();
    ProcedimientoEspecial buscarProcedimientoPorId(int id);
    boolean agregarProcedimiento(ProcedimientoEspecial procedimiento);
    boolean modificarProcedimiento(ProcedimientoEspecial procedimiento);
    boolean eliminarProcedimiento(int id);
    List<ProcedimientoEspecial> listarProcedimientosPorMascota(int mascotaId);
    List<ProcedimientoEspecial> listarProcedimientosPorVeterinario(int veterinarioId);
    List<ProcedimientoEspecial> listarProcedimientosPorEstado(String estado);
    List<ProcedimientoEspecial> listarProcedimientosPorFecha(LocalDate fechaInicio, LocalDate fechaFin);
}