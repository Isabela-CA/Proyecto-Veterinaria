package com.happyfeet.View;

import com.happyfeet.Controller.HistorialClinicoController;
import com.happyfeet.model.entities.HistorialClinico;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import static com.happyfeet.View.Main.mostrarMenuPrincipal;

public class HistorialClinicoView {
    private HistorialClinicoController historialController;
    private Scanner scanner = new Scanner(System.in);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public HistorialClinicoView(HistorialClinicoController historialController) {
        this.historialController = historialController;
    }

    public void mostrarMenu() {
        System.out.println("""
                ╔════════════════════════════════════════╗
                ║    GESTIÓN DE HISTORIAL CLÍNICO        ║
                ╠════════════════════════════════════════╣
                ║ 1. Registrar Historial Clinico         ║
                ║ 2. Listar Todos Los Historiales        ║
                ║ 3. Actualizar Historial                ║
                ║ 4. Consultar Historial por Mascota     ║
                ║ 5. Buscar Historial por ID             ║
                ║ 0. Volver al menu principal            ║
                ╚════════════════════════════════════════╝
                """);

        try {
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1 -> registraHistorialClinico();
                case 2 -> listarTodosLosHistoriales();
                case 3 -> actualizarHistorial();
                case 4 -> consultarHistorialPorMascota();
                case 5 -> buscarHistorialPorId();
                case 0 -> mostrarMenuPrincipal();
                default -> System.out.println("Opción no válida");
            }
        } catch (Exception e) {
            System.out.println("Error: Ingrese un número válido");
            scanner.nextLine(); // limpiar buffer en caso de error
        }
    }

    private void registraHistorialClinico() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║              REGISTRAR NUEVO EVENTO EN HISTORIAL CLÍNICO                     ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════╝\n");

        try {
            HistorialClinico historial = new HistorialClinico();

            // PASO 1: Identificación
            System.out.println("┌─── PASO 1: Identificación ───────────────────────────────────┐");
            System.out.print("│ Ingrese ID de la mascota: ");
            int mascotaId = scanner.nextInt();
            historial.setMascota_id(mascotaId);

            System.out.print("│ Ingrese ID del veterinario: ");
            int veterinarioId = scanner.nextInt();
            historial.setVeterinario_id(veterinarioId);
            scanner.nextLine(); // Limpiar buffer
            System.out.println("└───────────────────────────────────────────────────────────────┘\n");

            // PASO 2: Fecha del Evento
            System.out.println("┌─── PASO 2: Fecha del Evento ─────────────────────────────────┐");
            System.out.print("│ Fecha (yyyy-MM-dd) o [Enter] para hoy: ");
            String fechaStr = scanner.nextLine().trim();

            if (fechaStr.isEmpty()) {
                historial.setFecha_evento(LocalDate.now());
                System.out.println("│ ✓ Usando fecha actual: " + LocalDate.now());
            } else {
                try {
                    LocalDate fecha = LocalDate.parse(fechaStr, formatter);
                    if (fecha.isAfter(LocalDate.now())) {
                        System.out.println("│ ⚠ La fecha no puede ser futura. Usando fecha actual.");
                        historial.setFecha_evento(LocalDate.now());
                    } else {
                        historial.setFecha_evento(fecha);
                        System.out.println("│ ✓ Fecha registrada: " + fecha);
                    }
                } catch (DateTimeParseException e) {
                    System.out.println("│ ⚠ Formato inválido. Usando fecha actual.");
                    historial.setFecha_evento(LocalDate.now());
                }
            }
            System.out.println("└───────────────────────────────────────────────────────────────┘\n");

            // PASO 3: Tipo de Evento
            System.out.println("┌─── PASO 3: Tipo de Evento Médico ────────────────────────────┐");
            System.out.println("│  1. Vacunación          - Aplicación de vacunas              │");
            System.out.println("│  2. Consulta General    - Chequeo médico rutinario           │");
            System.out.println("│  3. Cirugía             - Procedimiento quirúrgico           │");
            System.out.println("│  4. Desparasitación     - Tratamiento antiparasitario        │");
            System.out.println("│  5. Control de Peso     - Seguimiento de peso                │");
            System.out.println("│  6. Examen de sangre    - Análisis de laboratorio            │");
            System.out.println("│  7. Radiografía         - Estudio de imagen                  │");
            System.out.println("│  8. Emergencia          - Atención de emergencia             │");
            System.out.println("│  9. Limpieza Dental     - Profilaxis dental                  │");
            System.out.println("│  10.Ecografía           - Estudio de ultrasonido             │");
            System.out.println("│  11.Castración          - Cirugía de esterilizació           │");
            System.out.println("│  12.Hospitalización     - Internación de mascota             │");
            System.out.print("│ Seleccione tipo (1-12): ");
            int tipoEvento = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            if (tipoEvento < 1 || tipoEvento > 12) {
                System.out.println("│ ⚠ Opción inválida. Se usará 'Consulta General' por defecto. │");
                tipoEvento = 2;
            }
            historial.setEvento_tipo_id(tipoEvento);
            System.out.println("│ ✓ Tipo seleccionado: " + obtenerNombreTipoEvento(tipoEvento));
            System.out.println("└───────────────────────────────────────────────────────────────┘\n");

            // PASO 4: Descripción
            System.out.println("┌─── PASO 4: Descripción del Evento ───────────────────────────┐");
            System.out.print("│ Descripción (obligatorio): ");
            String descripcion = scanner.nextLine().trim();

            if (descripcion.isEmpty()) {
                System.out.println("│ ✗ ERROR: La descripción no puede estar vacía");
                System.out.println("└───────────────────────────────────────────────────────────────┘");
                return;
            }
            historial.setDescripcion(descripcion);
            System.out.println("│ ✓ Descripción registrada");
            System.out.println("└───────────────────────────────────────────────────────────────┘\n");

            // PASO 5: Diagnóstico
            System.out.println("┌─── PASO 5: Diagnóstico ──────────────────────────────────────┐");
            System.out.print("│ Diagnóstico (opcional, Enter para omitir): ");
            String diagnostico = scanner.nextLine().trim();
            if (!diagnostico.isEmpty()) {
                historial.setDiagnostico(diagnostico);
                System.out.println("│ ✓ Diagnóstico registrado");
            } else {
                System.out.println("│ ⊘ Sin diagnóstico");
            }
            System.out.println("└───────────────────────────────────────────────────────────────┘\n");

            // PASO 6: Tratamiento
            System.out.println("┌─── PASO 6: Tratamiento Recomendado ──────────────────────────┐");
            System.out.print("│ Tratamiento (opcional, Enter para omitir): ");
            String tratamiento = scanner.nextLine().trim();
            if (!tratamiento.isEmpty()) {
                historial.setTratamiento_recomendado(tratamiento);
                System.out.println("│ ✓ Tratamiento registrado");
            } else {
                System.out.println("│ ⊘ Sin tratamiento específico");
            }
            System.out.println("└───────────────────────────────────────────────────────────────┘\n");

            // PASO 7: Referencias Opcionales
            System.out.println("┌─── PASO 7: Referencias (Opcional) ───────────────────────────┐");
            System.out.print("│ ID de consulta médica relacionada (0 para omitir): ");
            int consultaId = scanner.nextInt();
            if (consultaId > 0) {
                historial.setConsulta_id(consultaId);
                System.out.println("│ ✓ Vinculado a consulta ID: " + consultaId);
            }

            System.out.print("│ ID de procedimiento relacionado (0 para omitir): ");
            int procedimientoId = scanner.nextInt();
            if (procedimientoId > 0) {
                historial.setProcedimiento_id(procedimientoId);
                System.out.println("│ ✓ Vinculado a procedimiento ID: " + procedimientoId);
            }
            System.out.println("└───────────────────────────────────────────────────────────────┘\n");

            // Confirmación
            System.out.println("┌─── RESUMEN DEL REGISTRO ─────────────────────────────────────┐");
            System.out.println("│ Mascota ID:    " + mascotaId);
            System.out.println("│ Veterinario:   " + veterinarioId);
            System.out.println("│ Fecha:         " + historial.getFecha_evento());
            System.out.println("│ Tipo:          " + obtenerNombreTipoEvento(tipoEvento));
            System.out.println("│ Descripción:   " + truncarTexto(descripcion, 40));
            System.out.println("└───────────────────────────────────────────────────────────────┘");

            System.out.print("\n¿Confirmar registro? (S/N): ");
            scanner.nextLine(); // Limpiar buffer
            String confirmacion = scanner.nextLine().trim().toUpperCase();

            if (confirmacion.equals("S") || confirmacion.equals("SI")) {
                if (historialController.agregarHistorialClinico(historial)) {
                    System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
                    System.out.println("║              ✓ HISTORIAL REGISTRADO EXITOSAMENTE             ║");
                    System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");
                } else {
                    System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
                    System.out.println("║        ✗ ERROR: No se pudo registrar el historial            ║");
                    System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");
                }
            } else {
                System.out.println("\n⊘ Registro cancelado por el usuario.\n");
            }

        } catch (Exception e) {
            System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
            System.out.println("║                    ✗ ERROR EN EL REGISTRO                    ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════╝");
            System.out.println("Detalles: " + e.getMessage());
            scanner.nextLine();
        }
    }

    private void consultarHistorialPorMascota() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║          CONSULTAR HISTORIAL POR MASCOTA                     ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");

        try {
            System.out.print("Ingrese ID de la mascota: ");
            int mascotaId = scanner.nextInt();

            List<HistorialClinico> historiales = historialController.consultarHistorialPorMascota(mascotaId);

            if (historiales.isEmpty()) {
                System.out.println("\n⊘ No se encontró historial para la mascota con ID: " + mascotaId);
            } else {
                System.out.println("\n┌─────────────────────────────────────────────────────────────┐");
                System.out.println("│ HISTORIAL CLÍNICO - MASCOTA ID: " + mascotaId);
                System.out.println("│ Total de eventos médicos: " + historiales.size());
                System.out.println("└─────────────────────────────────────────────────────────────┘\n");

                for (HistorialClinico historial : historiales) {
                    mostrarDetalleHistorial(historial);
                    System.out.println("─".repeat(65) + "\n");
                }
            }
        } catch (Exception e) {
            System.out.println("✗ Error: Ingrese un ID válido");
            scanner.nextLine();
        }
    }

    private void listarTodosLosHistoriales() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║            TODOS LOS HISTORIALES CLÍNICOS                     ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");

        List<HistorialClinico> historiales = historialController.listarTodosLosHistoriales();

        if (historiales.isEmpty()) {
            System.out.println("⊘ No hay historiales médicos registrados\n");
        } else {
            System.out.println("Total de historiales: " + historiales.size() + "\n");

            if (historiales.size() > 10) {
                System.out.println("Vista resumida (más de 10 registros):\n");
                System.out.printf("%-5s | %-10s | %-12s | %-18s | %-30s%n",
                        "ID", "Mascota", "Fecha", "Tipo", "Descripción");
                System.out.println("─".repeat(90));

                for (HistorialClinico historial : historiales) {
                    mostrarResumenHistorial(historial);
                }
            } else {
                for (HistorialClinico historial : historiales) {
                    mostrarDetalleHistorial(historial);
                    System.out.println("─".repeat(65) + "\n");
                }
            }
        }
    }

    private void buscarHistorialPorId() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║              BUSCAR HISTORIAL POR ID                          ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");

        try {
            System.out.print("Ingrese ID del historial: ");
            int id = scanner.nextInt();

            HistorialClinico historial = historialController.buscarHistorialClinicoPorId(id);
            if (historial != null) {
                System.out.println("\n✓ Historial encontrado:\n");
                mostrarDetalleHistorial(historial);
            } else {
                System.out.println("\n✗ No se encontró historial con ID: " + id);
            }
        } catch (Exception e) {
            System.out.println("✗ Error: Ingrese un ID válido");
            scanner.nextLine();
        }
    }

    private void actualizarHistorial() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                 ACTUALIZAR HISTORIAL                          ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");

        try {
            System.out.print("Ingrese ID del historial a actualizar: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            HistorialClinico historial = historialController.buscarHistorialClinicoPorId(id);
            if (historial != null) {
                System.out.println("\nHistorial actual:");
                mostrarDetalleHistorial(historial);

                System.out.println("\n┌─── ACTUALIZAR DATOS (Enter para mantener actual) ──────────┐");

                System.out.println("│ Descripción actual: " + historial.getDescripcion());
                System.out.print("│ Nueva descripción: ");
                String nuevaDescripcion = scanner.nextLine();
                if (!nuevaDescripcion.trim().isEmpty()) {
                    historial.setDescripcion(nuevaDescripcion);
                }

                System.out.println("│ Diagnóstico actual: " + historial.getDiagnostico());
                System.out.print("│ Nuevo diagnóstico: ");
                String nuevoDiagnostico = scanner.nextLine();
                if (!nuevoDiagnostico.trim().isEmpty()) {
                    historial.setDiagnostico(nuevoDiagnostico);
                }

                System.out.println("│ Tratamiento actual: " + historial.getTratamiento_recomendado());
                System.out.print("│ Nuevo tratamiento: ");
                String nuevoTratamiento = scanner.nextLine();
                if (!nuevoTratamiento.trim().isEmpty()) {
                    historial.setTratamiento_recomendado(nuevoTratamiento);
                }
                System.out.println("└────────────────────────────────────────────────────────────┘");

                if (historialController.actualizarHistorial(historial)) {
                    System.out.println("\n✓ Historial actualizado correctamente");
                } else {
                    System.out.println("\n✗ Error al actualizar el historial");
                }
            } else {
                System.out.println("\n✗ Historial no encontrado con ID: " + id);
            }
        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
            scanner.nextLine();
        }
    }

    private void mostrarDetalleHistorial(HistorialClinico historial) {
        System.out.println("┌─────────────────────────────────────────────────────────────┐");
        System.out.printf("│ ID: %-56d │%n", historial.getId());
        System.out.printf("│ Mascota ID: %-48d │%n", historial.getMascota_id());
        System.out.printf("│ Veterinario ID: %-44d │%n", historial.getVeterinario_id());
        System.out.printf("│ Fecha: %-53s │%n", historial.getFecha_evento());
        System.out.printf("│ Tipo: %-54s │%n", obtenerNombreTipoEvento(historial.getEvento_tipo_id()));
        System.out.println("├─────────────────────────────────────────────────────────────┤");
        System.out.printf("│ Descripción: %-47s │%n", truncarTexto(historial.getDescripcion(), 47));
        System.out.printf("│ Diagnóstico: %-47s │%n", truncarTexto(historial.getDiagnostico(), 47));
        System.out.printf("│ Tratamiento: %-47s │%n", truncarTexto(historial.getTratamiento_recomendado(), 47));

        if (historial.getConsulta_id() > 0) {
            System.out.printf("│ Consulta ID: %-47d │%n", historial.getConsulta_id());
        }
        if (historial.getProcedimiento_id() > 0) {
            System.out.printf("│ Procedimiento ID: %-42d │%n", historial.getProcedimiento_id());
        }
        System.out.println("└─────────────────────────────────────────────────────────────┘");
    }

    private void mostrarResumenHistorial(HistorialClinico historial) {
        System.out.printf("%-5d | %-10d | %-12s | %-18s | %-30s%n",
                historial.getId(),
                historial.getMascota_id(),
                historial.getFecha_evento(),
                obtenerNombreTipoEvento(historial.getEvento_tipo_id()),
                truncarTexto(historial.getDescripcion(), 30)
        );
    }

    private String obtenerNombreTipoEvento(int tipoId) {
        return switch (tipoId) {

            case 1 -> "Vacunación";
            case 2 -> "Consulta General";
            case 3 -> "Cirugía";
            case 4 -> "Desparasitación";
            case 5 -> "Control de Peso";
            case 6 -> "Examen de Sangre";
            case 7 -> "Radiografía";
            case 8 -> "Emergencia";
            case 9 -> "Limpieza Dental";
            case 10 -> "Ecografía";
            case 11 -> "Castración";
            case 12 -> "Hospitalización";
            default -> "Otro (" + tipoId + ")";
        };
    }

    private String truncarTexto(String texto, int maxLength) {
        if (texto == null) return "N/A";
        if (texto.length() <= maxLength) return texto;
        return texto.substring(0, maxLength - 3) + "...";
    }
}