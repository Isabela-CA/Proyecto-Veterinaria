package com.happyfeet.Service;

import com.happyfeet.Repository.ProcedimientoQuirurgicoDAO;
import com.happyfeet.Repository.MascotaDAO;
import com.happyfeet.Repository.VeterinarioDAO;
import com.happyfeet.Repository.CitaDAO;
import com.happyfeet.model.entities.ProcedimientoQuirurgico;
import com.happyfeet.model.entities.Mascota;
import com.happyfeet.model.entities.Veterinario;
import com.happyfeet.model.entities.Cita;
import com.happyfeet.model.enums.EstadoCita;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class ProcedimientoQuirurgicoService {
        private ProcedimientoQuirurgicoDAO procedimientoDAO;
        private MascotaDAO mascotaDAO;
        private VeterinarioDAO veterinarioDAO;
        private CitaDAO citaDAO = new CitaDAO() {
            @Override
            public List<Cita> listarCitas() {
                return List.of();
            }

            @Override
            public Cita buscarCitaPorId(int id) {
                return null;
            }

            @Override
            public boolean agregarCita(Cita cita) {
                return false;
            }

            @Override
            public boolean modificarCita(Cita cita) {
                return false;
            }

            @Override
            public boolean cambiarEstadoCita(int citaId, EstadoCita.Estado nuevoEstado) {
                return false;
            }

            @Override
            public List<Cita> listarCitasPorMascota(int mascotaId) {
                return List.of();
            }

            @Override
            public List<Cita> listarCitasPorVeterinario(int veterinarioId) {
                return List.of();
            }

            @Override
            public List<Cita> listarCitasPorFecha(LocalDate fecha) {
                return List.of();
            }

            @Override
            public List<Cita> listarCitasPorEstado(EstadoCita.Estado estado) {
                return List.of();
            }

            @Override
            public List<Cita> listarCitasVeterinarioPorFecha(int veterinarioId, LocalDate fecha) {
                return List.of();
            }

            @Override
            public boolean verificarDisponibilidadVeterinario(int veterinarioId, LocalDate fecha, LocalTime hora) {
                return false;
            }

            @Override
            public List<Cita> listarCitasPendientes() {
                return List.of();
            }

            @Override
            public boolean buscarCita(Cita cita) {
                return false;
            }
        };

        public ProcedimientoQuirurgicoService() {
            this.procedimientoDAO = new ProcedimientoQuirurgicoDAO();
            this.mascotaDAO = new MascotaDAO();
            this.veterinarioDAO = new VeterinarioDAO();
        }

        // Tipos de procedimiento comunes
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

        // Estados de seguimiento postoperatorio
        public static final String RESULTADO_EXITOSO = "Exitoso";
        public static final String RESULTADO_COMPLICACIONES = "Con complicaciones";
        public static final String RESULTADO_EN_RECUPERACION = "En recuperación";
        public static final String RESULTADO_REQUIERE_SEGUIMIENTO = "Requiere seguimiento";

        /**
         * Lista todos los procedimientos quirúrgicos
         */
        public List<ProcedimientoQuirurgico> listarTodosLosProcedimientos() {
            try {
                return procedimientoDAO.listarProcedimientosQuirurgicos();
            } catch (Exception e) {
                System.out.println("Error al listar procedimientos: " + e.getMessage());
                return List.of(); // Retorna lista vacía en caso de error
            }
        }

        /**
         * Registra un nuevo procedimiento quirúrgico con validaciones
         */
        public boolean registrarProcedimiento(int mascotaId, int veterinarioId, Integer citaId,
                                              LocalDateTime fechaProcedimiento, String tipoProcedimiento,
                                              String diagnostico, String descripcion, String anestesia,
                                              String medicacion, String cuidados, String resultado) {
            try {
                // Validar que la mascota exista
                Mascota mascota = mascotaDAO.buscarMascotaPorId(mascotaId);
                if (mascota == null) {
                    System.out.println("Error: La mascota con ID " + mascotaId + " no existe.");
                    return false;
                }

                // Validar que el veterinario exista y esté activo
                Veterinario veterinario = veterinarioDAO.buscarVeterinarioPorId(veterinarioId);
                if (veterinario == null) {
                    System.out.println("Error: El veterinario con ID " + veterinarioId + " no existe.");
                    return false;
                }

                if (veterinario.getActivo() != 1) {
                    System.out.println("Error: El veterinario no está activo.");
                    return false;
                }

                // Validar especialidad para cirugías complejas
                if (esCirugiaCompleja(tipoProcedimiento)) {
                    String especialidad = veterinario.getEspecialidad() != null ?
                            veterinario.getEspecialidad().toString() : "";
                    if (!especialidad.equalsIgnoreCase("Cirugía") &&
                            !especialidad.equalsIgnoreCase("Cirugia")) {
                        System.out.println("Advertencia: Se recomienda un veterinario especialista en cirugía para este procedimiento.");
                    }
                }

                // Validar cita si se proporciona
                if (citaId != null) {
                    // Buscar la cita completa por ID
                    Cita cita = citaDAO.buscarCitaPorId(citaId);
                    if (cita == null) {
                        System.out.println("Error: La cita con ID " + citaId + " no existe.");
                        return false;
                    }

                    // Verificar que la cita sea de la misma mascota y veterinario
                    if (cita.getMascota_id() != mascotaId || cita.getVeterinario_id() != veterinarioId) {
                        System.out.println("Error: La cita no corresponde a la mascota y veterinario especificados.");
                        return false;
                    }
                }

                // Validar campos obligatorios
                if (!validarCamposObligatorios(tipoProcedimiento, diagnostico, descripcion)) {
                    return false;
                }

                // Validar fecha del procedimiento
                if (fechaProcedimiento.isAfter(LocalDateTime.now())) {
                    System.out.println("Advertencia: El procedimiento está programado para el futuro.");
                }

                // Crear y guardar el procedimiento
                ProcedimientoQuirurgico nuevoProcedimiento = new ProcedimientoQuirurgico(
                        mascotaId, veterinarioId, citaId, fechaProcedimiento,
                        tipoProcedimiento, diagnostico, descripcion,
                        anestesia, medicacion, cuidados, resultado
                );

                boolean resultadoOperacion = procedimientoDAO.agregarProcedimientoQuirurgico(nuevoProcedimiento);

                if (resultadoOperacion) {
                    System.out.println("Procedimiento quirúrgico registrado exitosamente.");
                    // Generar recomendaciones de seguimiento
                    generarRecomendacionesSeguimiento(tipoProcedimiento);
                }

                return resultadoOperacion;

            } catch (Exception e) {
                System.out.println("Error al registrar el procedimiento: " + e.getMessage());
                return false;
            }
        }

        /**
         * Busca un procedimiento por ID
         */
        public ProcedimientoQuirurgico buscarProcedimientoPorId(int id) {
            try {
                ProcedimientoQuirurgico procedimiento = new ProcedimientoQuirurgico(id);
                if (procedimientoDAO.buscarProcedimientoQuirurgico(procedimiento)) {
                    return procedimiento;
                }
            } catch (Exception e) {
                System.out.println("Error al buscar procedimiento: " + e.getMessage());
            }
            return null;
        }

        /**
         * Modifica un procedimiento existente
         */
        public boolean modificarProcedimiento(ProcedimientoQuirurgico procedimiento) {
            try {
                // Verificar que el procedimiento existe
                if (buscarProcedimientoPorId(procedimiento.getId()) == null) {
                    System.out.println("Error: El procedimiento con ID " + procedimiento.getId() + " no existe.");
                    return false;
                }

                // Validar campos obligatorios
                if (!validarCamposObligatorios(procedimiento.getTipo_procedimiento(),
                        procedimiento.getDiagnostico(), procedimiento.getDescripcion_procedimiento())) {
                    return false;
                }

                boolean resultado = procedimientoDAO.modificarProcedimientoQuirurgico(procedimiento);
                if (resultado) {
                    System.out.println("Procedimiento modificado exitosamente.");
                }
                return resultado;

            } catch (Exception e) {
                System.out.println("Error al modificar el procedimiento: " + e.getMessage());
                return false;
            }
        }

        /**
         * Elimina un procedimiento
         */
        public boolean eliminarProcedimiento(int id) {
            try {
                // Verificar que el procedimiento existe antes de eliminarlo
                if (buscarProcedimientoPorId(id) == null) {
                    System.out.println("Error: El procedimiento con ID " + id + " no existe.");
                    return false;
                }

                ProcedimientoQuirurgico procedimiento = new ProcedimientoQuirurgico(id);
                boolean resultado = procedimientoDAO.eliminarProcedimientoQuirurgico(procedimiento);

                if (resultado) {
                    System.out.println("Procedimiento eliminado exitosamente.");
                }
                return resultado;

            } catch (Exception e) {
                System.out.println("Error al eliminar el procedimiento: " + e.getMessage());
                return false;
            }
        }

        /**
         * Consulta procedimientos por mascota
         */
        public List<ProcedimientoQuirurgico> consultarProcedimientosPorMascota(int mascotaId) {
            try {
                // Verificar que la mascota existe
                Mascota mascota = mascotaDAO.buscarMascotaPorId(mascotaId);
                if (mascota == null) {
                    System.out.println("Error: La mascota con ID " + mascotaId + " no existe.");
                    return List.of();
                }

                return procedimientoDAO.listarProcedimientosPorMascota(mascotaId);
            } catch (Exception e) {
                System.out.println("Error al consultar procedimientos por mascota: " + e.getMessage());
                return List.of();
            }
        }

        /**
         * Consulta procedimientos por veterinario
         */
        public List<ProcedimientoQuirurgico> consultarProcedimientosPorVeterinario(int veterinarioId) {
            try {
                // Verificar que el veterinario existe
                Veterinario veterinario = veterinarioDAO.buscarVeterinarioPorId(veterinarioId);
                if (veterinario == null) {
                    System.out.println("Error: El veterinario con ID " + veterinarioId + " no existe.");
                    return List.of();
                }

                return procedimientoDAO.listarProcedimientosPorVeterinario(veterinarioId);
            } catch (Exception e) {
                System.out.println("Error al consultar procedimientos por veterinario: " + e.getMessage());
                return List.of();
            }
        }

        /**
         * Actualiza el resultado/seguimiento de un procedimiento
         */
        public boolean actualizarSeguimientoPostoperatorio(int procedimientoId, String nuevoResultado,
                                                           String cuidadosActualizados, String medicacionActualizada) {
            try {
                ProcedimientoQuirurgico procedimiento = buscarProcedimientoPorId(procedimientoId);
                if (procedimiento == null) {
                    System.out.println("Error: Procedimiento no encontrado.");
                    return false;
                }

                // Actualizar campos si se proporcionan valores válidos
                if (nuevoResultado != null && !nuevoResultado.trim().isEmpty()) {
                    procedimiento.setResultado(nuevoResultado);
                }

                if (cuidadosActualizados != null && !cuidadosActualizados.trim().isEmpty()) {
                    procedimiento.setCuidados_recomendados(cuidadosActualizados);
                }

                if (medicacionActualizada != null && !medicacionActualizada.trim().isEmpty()) {
                    procedimiento.setMedicacion_prescrita(medicacionActualizada);
                }

                boolean resultado = procedimientoDAO.modificarProcedimientoQuirurgico(procedimiento);

                if (resultado) {
                    System.out.println("Seguimiento postoperatorio actualizado exitosamente.");
                }

                return resultado;

            } catch (Exception e) {
                System.out.println("Error al actualizar el seguimiento: " + e.getMessage());
                return false;
            }
        }

        /**
         * Genera reporte de procedimientos por período
         */
        public void generarReporteProcedimientos(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
            try {
                if (fechaInicio.isAfter(fechaFin)) {
                    System.out.println("Error: La fecha de inicio debe ser anterior a la fecha de fin.");
                    return;
                }

                List<ProcedimientoQuirurgico> procedimientos = listarTodosLosProcedimientos();

                var procedimientosFiltrados = procedimientos.stream()
                        .filter(p -> p.getFecha_procedimiento().isAfter(fechaInicio.minusSeconds(1)) &&
                                p.getFecha_procedimiento().isBefore(fechaFin.plusSeconds(1)))
                        .toList();

                System.out.println("=== REPORTE DE PROCEDIMIENTOS QUIRÚRGICOS ===");
                System.out.println("Período: " + fechaInicio.toLocalDate() + " a " + fechaFin.toLocalDate());
                System.out.println("Total de procedimientos: " + procedimientosFiltrados.size());

                if (!procedimientosFiltrados.isEmpty()) {
                    // Estadísticas por tipo
                    System.out.println("\n--- Procedimientos por Tipo ---");
                    procedimientosFiltrados.stream()
                            .collect(java.util.stream.Collectors.groupingBy(
                                    ProcedimientoQuirurgico::getTipo_procedimiento,
                                    java.util.stream.Collectors.counting()))
                            .forEach((tipo, count) -> System.out.println(tipo + ": " + count));

                    // Estadísticas por resultado
                    System.out.println("\n--- Procedimientos por Resultado ---");
                    procedimientosFiltrados.stream()
                            .filter(p -> p.getResultado() != null && !p.getResultado().trim().isEmpty())
                            .collect(java.util.stream.Collectors.groupingBy(
                                    ProcedimientoQuirurgico::getResultado,
                                    java.util.stream.Collectors.counting()))
                            .forEach((resultado, count) -> System.out.println(resultado + ": " + count));
                } else {
                    System.out.println("No se encontraron procedimientos en el período especificado.");
                }

            } catch (Exception e) {
                System.out.println("Error al generar el reporte: " + e.getMessage());
            }
        }

        // Métodos auxiliares privados

        private boolean validarCamposObligatorios(String tipo, String diagnostico, String descripcion) {
            if (tipo == null || tipo.trim().isEmpty()) {
                System.out.println("Error: El tipo de procedimiento es obligatorio.");
                return false;
            }
            if (diagnostico == null || diagnostico.trim().isEmpty()) {
                System.out.println("Error: El diagnóstico es obligatorio.");
                return false;
            }
            if (descripcion == null || descripcion.trim().isEmpty()) {
                System.out.println("Error: La descripción del procedimiento es obligatoria.");
                return false;
            }
            return true;
        }

        private boolean esCirugiaCompleja(String tipoProcedimiento) {
            if (tipoProcedimiento == null) return false;

            String tipo = tipoProcedimiento.toLowerCase();
            return tipo.contains("ortopédica") ||
                    tipo.contains("ortopedica") ||
                    tipo.contains("tumores") ||
                    tipo.contains("abdominal") ||
                    tipo.contains("ocular");
        }

        private void generarRecomendacionesSeguimiento(String tipoProcedimiento) {
            if (tipoProcedimiento == null) return;

            System.out.println("\n--- Recomendaciones de Seguimiento ---");
            String tipo = tipoProcedimiento.toLowerCase();

            if (tipo.contains("esterilización") || tipo.contains("esterilizacion") ||
                    tipo.contains("castración") || tipo.contains("castracion")) {
                System.out.println("• Control postoperatorio en 7-10 días");
                System.out.println("• Mantener herida seca y limpia");
                System.out.println("• Evitar actividad física intensa por 2 semanas");
            } else if (tipo.contains("extracción") || tipo.contains("extraccion") ||
                    tipo.contains("dental")) {
                System.out.println("• Dieta blanda por 3-5 días");
                System.out.println("• Evitar juguetes duros por 1 semana");
                System.out.println("• Control en 5 días");
            } else if (tipo.contains("ortopédica") || tipo.contains("ortopedica")) {
                System.out.println("• Reposo absoluto por 4-6 semanas");
                System.out.println("• Controles radiográficos semanales");
                System.out.println("• Fisioterapia según evolución");
            } else if (tipo.contains("tumores")) {
                System.out.println("• Control histopatológico en 7-10 días");
                System.out.println("• Seguimiento oncológico según resultado");
                System.out.println("• Monitoreo de signos de recidiva");
            } else {
                System.out.println("• Control postoperatorio según indicaciones");
                System.out.println("• Observar signos de infección o complicaciones");
                System.out.println("• Administrar medicación según prescripción");
            }
        }

        /**
         * Valida si un tipo de procedimiento es válido
         */
        public boolean esTipoProcedimientoValido(String tipo) {
            if (tipo == null || tipo.trim().isEmpty()) return false;

            for (String tipoValido : TIPOS_PROCEDIMIENTO) {
                if (tipoValido.equalsIgnoreCase(tipo.trim())) {
                    return true;
                }
            }
            return tipo.trim().equalsIgnoreCase("Otro");
        }

        /**
         * Obtiene los tipos de procedimiento disponibles
         */
        public String[] getTiposProcedimientoDisponibles() {
            return TIPOS_PROCEDIMIENTO.clone();
        }
    }
