package com.happyfeet.Service;

import com.happyfeet.Repository.CitaDAO;
import com.happyfeet.Repository.MascotaDAO;
import com.happyfeet.Repository.VeterinarioDAO;
import com.happyfeet.model.entities.Cita;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class CitaService {

    private final CitaDAO citaDAO;
    private final MascotaDAO mascotaDAO;
    private final VeterinarioDAO veterinarioDAO;

    // Record para resultado de validación (Java 17)
    public record ResultadoValidacion(boolean esValido, String mensaje) {
        public static ResultadoValidacion valido() {
            return new ResultadoValidacion(true, "");
        }

        public static ResultadoValidacion invalido(String mensaje) {
            return new ResultadoValidacion(false, mensaje);
        }
    }

    // Constantes de estado
    public static final int ESTADO_PROGRAMADA = 1;
    public static final int ESTADO_CONFIRMADA = 2;
    public static final int ESTADO_EN_PROCESO = 3;
    public static final int ESTADO_FINALIZADA = 4;
    public static final int ESTADO_CANCELADA = 5;
    public static final int ESTADO_NO_ASISTIO = 6;

    public CitaService() {
        this.citaDAO = null;
        this.mascotaDAO = new MascotaDAO();
        this.veterinarioDAO = new VeterinarioDAO();
    }

    public CitaService(CitaDAO citaDAO, MascotaDAO mascotaDAO, VeterinarioDAO veterinarioDAO) {
        this.citaDAO = citaDAO;
        this.mascotaDAO = mascotaDAO;
        this.veterinarioDAO = veterinarioDAO;
    }

    private boolean verificarDAOInicializado() {
        if (citaDAO == null) {
            System.out.println("Error: CitaDAO no está inicializado.");
            return false;
        }
        return true;
    }

    /**
     * Valida los datos de una cita antes de programarla
     */
    private ResultadoValidacion validarDatosCita(int mascotaId, int veterinarioId,
                                                 LocalDateTime fechaHora, String motivo) {
        // Validar que la mascota existe
        try {
            var mascota = mascotaDAO.buscarMascotaPorId(mascotaId);
            if (mascota == null) {
                return ResultadoValidacion.invalido("La mascota con ID " + mascotaId + " no existe.");
            }
        } catch (Exception e) {
            return ResultadoValidacion.invalido("Error al verificar la mascota: " + e.getMessage());
        }

        // Validar que el veterinario existe
        try {
            var veterinario = veterinarioDAO.buscarVeterinarioPorId(veterinarioId);
            if (veterinario == null) {
                return ResultadoValidacion.invalido("El veterinario con ID " + veterinarioId + " no existe.");
            }
            if (veterinario.getActivo() == 0) {
                return ResultadoValidacion.invalido("El veterinario no está activo.");
            }
        } catch (Exception e) {
            return ResultadoValidacion.invalido("Error al verificar el veterinario: " + e.getMessage());
        }

        // Validar fecha y hora
        if (fechaHora == null) {
            return ResultadoValidacion.invalido("La fecha y hora no pueden ser nulas.");
        }

        if (fechaHora.isBefore(LocalDateTime.now())) {
            return ResultadoValidacion.invalido("No se pueden programar citas en el pasado.");
        }

        // Validar disponibilidad del veterinario
        if (!verificarDisponibilidadVeterinario(veterinarioId, fechaHora)) {
            return ResultadoValidacion.invalido(
                    "El veterinario no está disponible en la fecha y hora solicitadas.");
        }

        // Validar motivo
        if (motivo == null || motivo.trim().isEmpty()) {
            return ResultadoValidacion.invalido("El motivo de la cita no puede estar vacío.");
        }

        if (motivo.trim().length() < 5) {
            return ResultadoValidacion.invalido("El motivo debe tener al menos 5 caracteres.");
        }

        return ResultadoValidacion.valido();
    }

    public List<Cita> listarTodasLasCitas() {
        if (!verificarDAOInicializado()) {
            return List.of();
        }
        try {
            return citaDAO.listarCitas();
        } catch (Exception e) {
            System.out.println("Error al listar citas: " + e.getMessage());
            return List.of();
        }
    }

    public boolean programarCita(int mascotaId, int veterinarioId,
                                 LocalDateTime fechaHora, String motivo) {
        if (!verificarDAOInicializado()) {
            return false;
        }

        try {
            var validacion = validarDatosCita(mascotaId, veterinarioId, fechaHora, motivo);
            if (!validacion.esValido()) {
                System.out.println("Error: " + validacion.mensaje());
                return false;
            }

            var nuevaCita = new Cita(mascotaId, veterinarioId, fechaHora,
                    motivo.trim(), ESTADO_PROGRAMADA);
            boolean resultado = citaDAO.agregarCita(nuevaCita);

            if (resultado) {
                System.out.println("✓ Cita programada exitosamente.");
            }
            return resultado;

        } catch (Exception e) {
            System.out.println("Error al programar la cita: " + e.getMessage());
            return false;
        }
    }

    public List<Cita> consultarCitasPorMascota(int mascotaId) {
        if (!verificarDAOInicializado()) {
            return List.of();
        }
        try {
            return citaDAO.listarCitasPorMascota(mascotaId);
        } catch (Exception e) {
            System.out.println("Error al consultar citas por mascota: " + e.getMessage());
            return List.of();
        }
    }

    public List<Cita> consultarCitasPorVeterinario(int veterinarioId) {
        if (!verificarDAOInicializado()) {
            return List.of();
        }
        try {
            return citaDAO.listarCitasPorVeterinario(veterinarioId);
        } catch (Exception e) {
            System.out.println("Error al consultar citas por veterinario: " + e.getMessage());
            return List.of();
        }
    }

    public List<Cita> consultarCitasPorFecha(LocalDateTime fecha) {
        if (!verificarDAOInicializado()) {
            return List.of();
        }
        try {
            return citaDAO.listarCitasPorFecha(LocalDate.from(fecha));
        } catch (Exception e) {
            System.out.println("Error al consultar citas por fecha: " + e.getMessage());
            return List.of();
        }
    }

    public Cita consultarCitaPorId(int citaId) {
        if (!verificarDAOInicializado()) {
            return null;
        }
        try {
            return citaDAO.buscarCitaPorId(citaId);
        } catch (Exception e) {
            System.out.println("Error al consultar cita por ID: " + e.getMessage());
            return null;
        }
    }

    public boolean cambiarEstadoCita(int citaId, int nuevoEstado) {
        if (!verificarDAOInicializado()) {
            return false;
        }

        try {
            var cita = consultarCitaPorId(citaId);
            if (cita == null) {
                System.out.println("Error: La cita con ID " + citaId + " no existe.");
                return false;
            }

            if (!esTransicionValidaEstado(cita.getEstado_id(), nuevoEstado)) {
                System.out.println("""
                        Error: Transición de estado no válida.
                        Estado actual: %s
                        Estado solicitado: %s
                        """.formatted(
                        obtenerNombreEstado(cita.getEstado_id()),
                        obtenerNombreEstado(nuevoEstado)
                ));
                return false;
            }

            cita.setEstado_id(nuevoEstado);
            boolean resultado = citaDAO.modificarCita(cita);

            if (resultado) {
                System.out.println("✓ Estado de la cita actualizado exitosamente.");
            }
            return resultado;

        } catch (Exception e) {
            System.out.println("Error al cambiar el estado de la cita: " + e.getMessage());
            return false;
        }
    }

    public boolean cancelarCita(int citaId) {
        return cambiarEstadoCita(citaId, ESTADO_CANCELADA);
    }

    public boolean confirmarCita(int citaId) {
        return cambiarEstadoCita(citaId, ESTADO_CONFIRMADA);
    }

    public boolean iniciarCita(int citaId) {
        return cambiarEstadoCita(citaId, ESTADO_EN_PROCESO);
    }

    public boolean completarCita(int citaId) {
        return cambiarEstadoCita(citaId, ESTADO_FINALIZADA);
    }

    public boolean marcarNoAsistio(int citaId) {
        return cambiarEstadoCita(citaId, ESTADO_NO_ASISTIO);
    }

    private boolean verificarDisponibilidadVeterinario(int veterinarioId, LocalDateTime fechaHora) {
        try {
            var citasVeterinario = citaDAO.listarCitasPorVeterinario(veterinarioId);

            return citasVeterinario.stream()
                    .noneMatch(cita -> cita.getFecha_hora().equals(fechaHora) &&
                            cita.getEstado_id() != ESTADO_CANCELADA &&
                            cita.getEstado_id() != ESTADO_NO_ASISTIO);
        } catch (Exception e) {
            System.out.println("Error al verificar disponibilidad: " + e.getMessage());
            return false;
        }
    }

    private boolean esTransicionValidaEstado(int estadoActual, int nuevoEstado) {
        return switch (estadoActual) {
            case ESTADO_PROGRAMADA -> nuevoEstado == ESTADO_CONFIRMADA ||
                    nuevoEstado == ESTADO_EN_PROCESO ||
                    nuevoEstado == ESTADO_CANCELADA ||
                    nuevoEstado == ESTADO_NO_ASISTIO;

            case ESTADO_CONFIRMADA -> nuevoEstado == ESTADO_EN_PROCESO ||
                    nuevoEstado == ESTADO_CANCELADA ||
                    nuevoEstado == ESTADO_NO_ASISTIO;

            case ESTADO_EN_PROCESO -> nuevoEstado == ESTADO_FINALIZADA ||
                    nuevoEstado == ESTADO_CANCELADA;

            case ESTADO_FINALIZADA, ESTADO_CANCELADA, ESTADO_NO_ASISTIO -> false;

            default -> false;
        };
    }

    public String obtenerNombreEstado(int estadoId) {
        return switch (estadoId) {
            case ESTADO_PROGRAMADA -> "Programada";
            case ESTADO_CONFIRMADA -> "Confirmada";
            case ESTADO_EN_PROCESO -> "En Proceso";
            case ESTADO_FINALIZADA -> "Finalizada";
            case ESTADO_CANCELADA -> "Cancelada";
            case ESTADO_NO_ASISTIO -> "No Asistió";
            default -> "Estado Desconocido";
        };
    }
}