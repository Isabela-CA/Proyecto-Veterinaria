package com.happyfeet.Service;

import com.happyfeet.Repository.Interfaz.IProcedimientoEspecialDAO;
import com.happyfeet.Repository.Interfaz.IMascotaDAO;
import com.happyfeet.Repository.Interfaz.IVeterinarioDAO;
import com.happyfeet.Repository.ProcedimientoEspecialDAO;
import com.happyfeet.Repository.MascotaDAO;
import com.happyfeet.Repository.VeterinarioDAO;
import com.happyfeet.model.entities.ProcedimientoEspecial;
import com.happyfeet.model.entities.Mascota;
import com.happyfeet.model.entities.Veterinario;
import com.happyfeet.model.enums.EstadoCita;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProcedimientoEspecialService {
    private final IProcedimientoEspecialDAO procedimientoDAO;
    private final IMascotaDAO mascotaDAO;
    private final IVeterinarioDAO veterinarioDAO;

    // Tipos de procedimiento disponibles
    public static final String[] TIPOS_PROCEDIMIENTO = {
            "Esterilización",
            "Castración",
            "Extracción dental",
            "Cirugía de tumores",
            "Cirugía ortopédica",
            "Cirugía ocular",
            "Cirugía abdominal",
            "Biopsia",
            "Sutura de heridas",
            "Otro"
    };

    // Estados válidos según la BD
    public static final String ESTADO_PROGRAMADO = "Programado";
    public static final String ESTADO_EN_PROCESO = "En Proceso";
    public static final String ESTADO_FINALIZADO = "Finalizado";
    public static final String ESTADO_CANCELADO = "Cancelado";

    // Constructor con inyección de dependencias
    public ProcedimientoEspecialService(IProcedimientoEspecialDAO procedimientoDAO,
                                        IMascotaDAO mascotaDAO,
                                        IVeterinarioDAO veterinarioDAO) {
        this.procedimientoDAO = procedimientoDAO;
        this.mascotaDAO = mascotaDAO;
        this.veterinarioDAO = veterinarioDAO;
    }

    // Constructor sin parámetros - CORREGIDO
    public ProcedimientoEspecialService() {
        this.procedimientoDAO = new ProcedimientoEspecialDAO(); // AHORA USA LA IMPLEMENTACIÓN REAL
        this.mascotaDAO = new MascotaDAO();
        this.veterinarioDAO = new VeterinarioDAO();
    }

    /**
     * Obtiene un procedimiento por ID
     */
    public ProcedimientoEspecial obtenerProcedimiento(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID debe ser mayor a 0");
        }
        return procedimientoDAO.buscarProcedimientoPorId(id);
    }

    /**
     * Lista todos los procedimientos
     */
    public List<ProcedimientoEspecial> listarTodosProcedimientos() {
        return procedimientoDAO.listarProcedimientos();
    }

    /**
     * Lista procedimientos por mascota
     */
    public List<ProcedimientoEspecial> listarProcedimientosPorMascota(int mascotaId) {
        if (mascotaId <= 0) {
            throw new IllegalArgumentException("ID de mascota inválido");
        }

        Mascota mascota = mascotaDAO.buscarMascotaPorId(mascotaId);
        if (mascota == null) {
            throw new IllegalArgumentException("No existe mascota con ID: " + mascotaId);
        }

        return procedimientoDAO.listarProcedimientosPorMascota(mascotaId);
    }

    /**
     * Lista procedimientos por veterinario
     */
    public List<ProcedimientoEspecial> listarProcedimientosPorVeterinario(int veterinarioId) {
        if (veterinarioId <= 0) {
            throw new IllegalArgumentException("ID de veterinario inválido");
        }

        Veterinario veterinario = veterinarioDAO.buscarVeterinarioPorId(veterinarioId);
        if (veterinario == null) {
            throw new IllegalArgumentException("No existe veterinario con ID: " + veterinarioId);
        }

        return procedimientoDAO.listarProcedimientosPorVeterinario(veterinarioId);
    }

    /**
     * Lista procedimientos por estado
     */
    public List<ProcedimientoEspecial> listarProcedimientosPorEstado(String estado) {
        if (estado == null || estado.trim().isEmpty()) {
            throw new IllegalArgumentException("Estado no puede ser nulo o vacío");
        }

        if (!esEstadoValido(estado)) {
            throw new IllegalArgumentException("Estado inválido: " + estado);
        }

        return procedimientoDAO.listarProcedimientosPorEstado(estado);
    }

    /**
     * Lista procedimientos por rango de fechas
     */
    public List<ProcedimientoEspecial> listarProcedimientosPorFecha(LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio == null || fechaFin == null) {
            throw new IllegalArgumentException("Las fechas no pueden ser nulas");
        }

        if (fechaInicio.isAfter(fechaFin)) {
            throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la fecha fin");
        }

        return procedimientoDAO.listarProcedimientosPorFecha(fechaInicio, fechaFin);
    }

    /**
     * Registra un nuevo procedimiento con validaciones
     */
    public boolean agregarProcedimiento(ProcedimientoEspecial procedimiento) {
        validarDatosProcedimiento(procedimiento);
        validarRelaciones(procedimiento);

        // Si no tiene fecha, usar fecha actual
        if (procedimiento.getFecha_hora() == null) {
            procedimiento.setFecha_hora(LocalDateTime.now());
        }

        // Si no tiene estado, usar PROGRAMADA
        if (procedimiento.getEstado() == null) {
            procedimiento.setEstado(EstadoCita.Estado.PROGRAMADA);
        }

        // Normalizar campos opcionales
        normalizarCamposOpcionales(procedimiento);

        return procedimientoDAO.agregarProcedimiento(procedimiento);
    }

    /**
     * Actualiza un procedimiento existente
     */
    public boolean actualizarProcedimiento(ProcedimientoEspecial procedimiento) {
        if (procedimiento.getId() <= 0) {
            throw new IllegalArgumentException("ID de procedimiento inválido");
        }

        ProcedimientoEspecial existente = procedimientoDAO.buscarProcedimientoPorId(procedimiento.getId());
        if (existente == null) {
            throw new IllegalArgumentException("No existe procedimiento con ID: " + procedimiento.getId());
        }

        validarDatosProcedimiento(procedimiento);
        normalizarCamposOpcionales(procedimiento);

        return procedimientoDAO.modificarProcedimiento(procedimiento);
    }

    /**
     * Actualiza el seguimiento postoperatorio
     */
    public boolean actualizarSeguimiento(int procedimientoId, String seguimiento,
                                         String complicaciones, LocalDate proximoControl) {
        if (procedimientoId <= 0) {
            throw new IllegalArgumentException("ID de procedimiento inválido");
        }

        ProcedimientoEspecial procedimiento = procedimientoDAO.buscarProcedimientoPorId(procedimientoId);
        if (procedimiento == null) {
            throw new IllegalArgumentException("No existe procedimiento con ID: " + procedimientoId);
        }

        if (seguimiento != null && !seguimiento.trim().isEmpty()) {
            procedimiento.setSeguimiento_postoperatorio(seguimiento.trim());
        }

        if (complicaciones != null && !complicaciones.trim().isEmpty()) {
            procedimiento.setComplicaciones(complicaciones.trim());
        }

        if (proximoControl != null) {
            if (proximoControl.isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("La fecha de próximo control no puede ser en el pasado");
            }
            procedimiento.setProximo_control(proximoControl);
        }

        return procedimientoDAO.modificarProcedimiento(procedimiento);
    }

    /**
     * Cambia el estado de un procedimiento
     */
    public boolean cambiarEstado(int procedimientoId, String nuevoEstado) {
        if (procedimientoId <= 0) {
            throw new IllegalArgumentException("ID de procedimiento inválido");
        }

        if (nuevoEstado == null || nuevoEstado.trim().isEmpty()) {
            throw new IllegalArgumentException("Estado no puede ser nulo o vacío");
        }

        if (!esEstadoValido(nuevoEstado)) {
            throw new IllegalArgumentException("Estado inválido: " + nuevoEstado);
        }

        ProcedimientoEspecial procedimiento = procedimientoDAO.buscarProcedimientoPorId(procedimientoId);
        if (procedimiento == null) {
            throw new IllegalArgumentException("No existe procedimiento con ID: " + procedimientoId);
        }

        // Convertir String a EstadoCita.Estado
        EstadoCita.Estado estadoEnum = convertirBDaEnum(nuevoEstado);
        procedimiento.setEstado(estadoEnum);

        return procedimientoDAO.modificarProcedimiento(procedimiento);
    }

    /**
     * Elimina un procedimiento
     */
    public boolean eliminarProcedimiento(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID debe ser mayor a 0");
        }

        ProcedimientoEspecial existente = procedimientoDAO.buscarProcedimientoPorId(id);
        if (existente == null) {
            throw new IllegalArgumentException("No existe procedimiento con ID: " + id);
        }

        return procedimientoDAO.eliminarProcedimiento(id);
    }

    /**
     * Genera resumen estadístico de procedimientos
     */
    public ResumenProcedimientos generarResumen(LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio == null || fechaFin == null) {
            throw new IllegalArgumentException("Las fechas no pueden ser nulas");
        }

        if (fechaInicio.isAfter(fechaFin)) {
            throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la fecha fin");
        }

        List<ProcedimientoEspecial> procedimientos = listarProcedimientosPorFecha(fechaInicio, fechaFin);

        ResumenProcedimientos resumen = new ResumenProcedimientos();
        resumen.totalProcedimientos = procedimientos.size();
        resumen.costoTotal = procedimientos.stream()
                .mapToDouble(ProcedimientoEspecial::getCosto_procedimiento)
                .sum();

        // Procedimientos por tipo
        resumen.procedimientosPorTipo = procedimientos.stream()
                .collect(Collectors.groupingBy(
                        ProcedimientoEspecial::getTipo_procedimiento,
                        Collectors.counting()
                ));

        // Procedimientos por estado
        resumen.procedimientosPorEstado = procedimientos.stream()
                .collect(Collectors.groupingBy(
                        p -> p.getEstado() != null ? p.getEstado().toString() : "Sin estado",
                        Collectors.counting()
                ));

        // Costo promedio
        if (!procedimientos.isEmpty()) {
            resumen.costoPromedio = resumen.costoTotal / procedimientos.size();
        }

        return resumen;
    }

    // ==================== MÉTODOS PRIVADOS ====================

    private void validarDatosProcedimiento(ProcedimientoEspecial procedimiento) {
        if (procedimiento == null) {
            throw new IllegalArgumentException("El procedimiento no puede ser nulo");
        }

        if (procedimiento.getTipo_procedimiento() == null ||
                procedimiento.getTipo_procedimiento().trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de procedimiento es obligatorio");
        }

        if (procedimiento.getNombre_procedimiento() == null ||
                procedimiento.getNombre_procedimiento().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del procedimiento es obligatorio");
        }

        if (procedimiento.getDetalle_procedimiento() == null ||
                procedimiento.getDetalle_procedimiento().trim().isEmpty()) {
            throw new IllegalArgumentException("El detalle del procedimiento es obligatorio");
        }

        if (procedimiento.getFecha_hora() != null &&
                procedimiento.getFecha_hora().isAfter(LocalDateTime.now().plusYears(1))) {
            throw new IllegalArgumentException("La fecha del procedimiento es demasiado lejana");
        }

        if (procedimiento.getCosto_procedimiento() < 0) {
            throw new IllegalArgumentException("El costo no puede ser negativo");
        }
    }

    private void validarRelaciones(ProcedimientoEspecial procedimiento) {
        // Validar mascota
        if (procedimiento.getMascota_id() <= 0) {
            throw new IllegalArgumentException("ID de mascota inválido");
        }

        Mascota mascota = mascotaDAO.buscarMascotaPorId(procedimiento.getMascota_id());
        if (mascota == null) {
            throw new IllegalArgumentException("No existe mascota con ID: " + procedimiento.getMascota_id());
        }

        // Validar veterinario
        if (procedimiento.getVeterinario_id() <= 0) {
            throw new IllegalArgumentException("ID de veterinario inválido");
        }

        Veterinario veterinario = veterinarioDAO.buscarVeterinarioPorId(procedimiento.getVeterinario_id());
        if (veterinario == null) {
            throw new IllegalArgumentException("No existe veterinario con ID: " + procedimiento.getVeterinario_id());
        }

        if (veterinario.getActivo() != 1) {
            throw new IllegalArgumentException("El veterinario no está activo");
        }
    }

    private void normalizarCamposOpcionales(ProcedimientoEspecial procedimiento) {
        if (procedimiento.getInformacion_preoperatoria() != null &&
                procedimiento.getInformacion_preoperatoria().trim().isEmpty()) {
            procedimiento.setInformacion_preoperatoria(null);
        }
        if (procedimiento.getComplicaciones() != null &&
                procedimiento.getComplicaciones().trim().isEmpty()) {
            procedimiento.setComplicaciones(null);
        }
        if (procedimiento.getSeguimiento_postoperatorio() != null &&
                procedimiento.getSeguimiento_postoperatorio().trim().isEmpty()) {
            procedimiento.setSeguimiento_postoperatorio(null);
        }
    }

    private boolean esEstadoValido(String estado) {
        if (estado == null) return false;
        String estadoNormalizado = estado.trim();
        return estadoNormalizado.equals(ESTADO_PROGRAMADO) ||
                estadoNormalizado.equals(ESTADO_EN_PROCESO) ||
                estadoNormalizado.equals(ESTADO_FINALIZADO) ||
                estadoNormalizado.equals(ESTADO_CANCELADO);
    }

    /**
     * Convierte el enum EstadoCita.Estado a valor de base de datos
     */
    private String convertirEnumABD(EstadoCita.Estado estado) {
        if (estado == null) return ESTADO_PROGRAMADO;

        switch (estado) {
            case PROGRAMADA:
                return ESTADO_PROGRAMADO;
            case EN_CURSO:
                return ESTADO_EN_PROCESO;
            case COMPLETADA:
                return ESTADO_FINALIZADO;
            case CANCELADA:
                return ESTADO_CANCELADO;
            default:
                return ESTADO_PROGRAMADO;
        }
    }

    /**
     * Convierte valor de base de datos a enum EstadoCita.Estado
     */
    private EstadoCita.Estado convertirBDaEnum(String estadoBD) {
        if (estadoBD == null || estadoBD.trim().isEmpty()) {
            return EstadoCita.Estado.PROGRAMADA;
        }

        switch (estadoBD.trim()) {
            case "Programado":
                return EstadoCita.Estado.PROGRAMADA;
            case "En Proceso":
                return EstadoCita.Estado.EN_CURSO;
            case "Finalizado":
                return EstadoCita.Estado.COMPLETADA;
            case "Cancelado":
                return EstadoCita.Estado.CANCELADA;
            default:
                return EstadoCita.Estado.PROGRAMADA;
        }
    }

    // ==================== CLASE INTERNA ====================

    /**
     * Clase para encapsular el resumen de procedimientos
     */
    public static class ResumenProcedimientos {
        public int totalProcedimientos;
        public double costoTotal;
        public double costoPromedio;
        public Map<String, Long> procedimientosPorTipo;
        public Map<String, Long> procedimientosPorEstado;

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Total Procedimientos: ").append(totalProcedimientos).append("\n");
            sb.append("Costo Total: $").append(String.format("%.2f", costoTotal)).append("\n");
            sb.append("Costo Promedio: $").append(String.format("%.2f", costoPromedio)).append("\n");

            if (procedimientosPorTipo != null && !procedimientosPorTipo.isEmpty()) {
                sb.append("\nPor Tipo:\n");
                procedimientosPorTipo.forEach((tipo, count) ->
                        sb.append("  - ").append(tipo).append(": ").append(count).append("\n")
                );
            }

            if (procedimientosPorEstado != null && !procedimientosPorEstado.isEmpty()) {
                sb.append("\nPor Estado:\n");
                procedimientosPorEstado.forEach((estado, count) ->
                        sb.append("  - ").append(estado).append(": ").append(count).append("\n")
                );
            }
            return sb.toString();
        }
    }
}