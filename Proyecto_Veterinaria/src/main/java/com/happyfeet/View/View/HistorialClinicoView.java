package com.happyfeet.View;

import com.happyfeet.Controller.HistorialClinicoController;
import com.happyfeet.model.entities.HistorialClinico;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class HistorialClinicoView {
    private HistorialClinicoController historialController;
    private Scanner scanner = new Scanner(System.in);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public HistorialClinicoView(HistorialClinicoController historialController) {
        this.historialController = historialController;
    }

    public void mostrarMenu() {
        System.out.println("=== Gestión de Historial Clínico ===");
        System.out.println("1. Consultar Historial por Mascota");
        System.out.println("2. Agregar Evento Médico");
        System.out.println("3. Registrar Vacunación");
        System.out.println("4. Registrar Consulta General");
        System.out.println("5. Ver Todos los Historiales");
        System.out.println("6. Buscar Historial por ID");
        System.out.println("7. Actualizar Historial");
        System.out.println("8. Eliminar Historial");
        System.out.println("0. Salir");

        try {
            int opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1 -> consultarHistorialPorMascota();
                case 2 -> agregarEventoMedico();
                case 3 -> registrarVacunacion();
                case 4 -> registrarConsultaGeneral();
                case 5 -> listarTodosLosHistoriales();
                case 6 -> buscarHistorialPorId();
                case 7 -> actualizarHistorial();
                default -> System.out.println("Saliendo...");
            }
        } catch (Exception e) {
            System.out.println("Error: Ingrese un número válido");
            scanner.nextLine(); // limpiar buffer en caso de error
        }
    }

    private void consultarHistorialPorMascota() {
        System.out.println("=== Consultar Historial por Mascota ===");

        try {
            System.out.println("Ingrese ID de la mascota: ");
            int mascotaId = scanner.nextInt();

            List<HistorialClinico> historiales = historialController.consultarHistorialPorMascota(mascotaId);

            if (historiales.isEmpty()) {
                System.out.println("No se encontró historial para la mascota con ID: " + mascotaId);
            } else {
                System.out.println("\n--- Historial Clínico de la Mascota " + mascotaId + " ---");
                System.out.println("Total de eventos médicos: " + historiales.size());
                System.out.println();

                for (HistorialClinico historial : historiales) {
                    mostrarDetalleHistorial(historial);
                    System.out.println("─".repeat(80));
                }
            }
        } catch (Exception e) {
            System.out.println("Error: Ingrese un ID válido");
            scanner.nextLine();
        }
    }

    private void agregarEventoMedico() {
        System.out.println("=== Agregar Evento Médico ===");

        try {
            System.out.println("Ingrese ID de la mascota: ");
            int mascotaId = scanner.nextInt();

            System.out.println("Ingrese ID del veterinario: ");
            int veterinarioId = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Ingrese fecha del evento (yyyy-MM-dd) o presione Enter para hoy: ");
            String fechaStr = scanner.nextLine();
            LocalDate fechaEvento = fechaStr.trim().isEmpty() ?
                    LocalDate.now() : LocalDate.parse(fechaStr, formatter);

            System.out.println("Seleccione tipo de evento:");
            System.out.println("1. Vacunación");
            System.out.println("2. Consulta General");
            System.out.println("3. Cirugía");
            System.out.println("4. Control de Rutina");
            System.out.println("5. Emergencia");
            System.out.println("6. Otro");

            int tipoEvento = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Ingrese descripción del evento: ");
            String descripcion = scanner.nextLine();

            System.out.println("Ingrese diagnóstico: ");
            String diagnostico = scanner.nextLine();

            System.out.println("Ingrese tratamiento recomendado: ");
            String tratamiento = scanner.nextLine();

            System.out.println("Ingrese ID del producto (opcional, 0 para omitir): ");
            int productoId = scanner.nextInt();

            System.out.println("Ingrese cantidad utilizada (0 si no aplica): ");
            int cantidad = scanner.nextInt();

            HistorialClinico historial = new HistorialClinico(
                    mascotaId, veterinarioId, fechaEvento, tipoEvento,
                    descripcion, diagnostico, tratamiento,
                    productoId > 0 ? productoId : null, cantidad
            );

            if (historialController.agregarEventoMedico(historial)) {
                System.out.println("✓ Evento médico registrado correctamente");
            } else {
                System.out.println("✗ Error al registrar el evento médico");
            }

        } catch (DateTimeParseException e) {
            System.out.println("Error: Formato de fecha inválido. Use yyyy-MM-dd");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void registrarVacunacion() {
        System.out.println("=== Registrar Vacunación ===");

        try {
            System.out.println("Ingrese ID de la mascota: ");
            int mascotaId = scanner.nextInt();

            System.out.println("Ingrese ID del veterinario: ");
            int veterinarioId = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Ingrese nombre de la vacuna: ");
            String vacuna = scanner.nextLine();

            System.out.println("Ingrese número de lote: ");
            String lote = scanner.nextLine();

            if (historialController.registrarVacunacion(mascotaId, veterinarioId, vacuna, lote)) {
                System.out.println("✓ Vacunación registrada correctamente");
                System.out.println("Recordatorio: Programar próxima dosis según calendario de vacunación");
            } else {
                System.out.println("✗ Error al registrar la vacunación");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void registrarConsultaGeneral() {
        System.out.println("=== Registrar Consulta General ===");

        try {
            System.out.println("Ingrese ID de la mascota: ");
            int mascotaId = scanner.nextInt();

            System.out.println("Ingrese ID del veterinario: ");
            int veterinarioId = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Ingrese motivo de la consulta: ");
            String motivo = scanner.nextLine();

            System.out.println("Ingrese diagnóstico: ");
            String diagnostico = scanner.nextLine();

            System.out.println("Ingrese tratamiento recomendado: ");
            String tratamiento = scanner.nextLine();

            if (historialController.registrarConsultaGeneral(mascotaId, veterinarioId, motivo, diagnostico, tratamiento)) {
                System.out.println("✓ Consulta registrada correctamente");
            } else {
                System.out.println("✗ Error al registrar la consulta");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listarTodosLosHistoriales() {
        System.out.println("=== Todos los Historiales Clínicos ===");

        List<HistorialClinico> historiales = historialController.listarTodosLosHistoriales();

        if (historiales.isEmpty()) {
            System.out.println("No hay historiales médicos registrados");
        } else {
            System.out.println("Total de historiales: " + historiales.size());
            System.out.println();

            // Vista resumida para listas grandes
            if (historiales.size() > 10) {
                System.out.println("Mostrando vista resumida (más de 10 registros):");
                for (HistorialClinico historial : historiales) {
                    mostrarResumenHistorial(historial);
                }
            } else {
                for (HistorialClinico historial : historiales) {
                    mostrarDetalleHistorial(historial);
                    System.out.println("─".repeat(80));
                }
            }
        }
    }

    private void buscarHistorialPorId() {
        System.out.println("=== Buscar Historial por ID ===");

        try {
            System.out.println("Ingrese ID del historial: ");
            int id = scanner.nextInt();

            HistorialClinico historial = historialController.obtenerHistorial(id);
            if (historial != null) {
                System.out.println("\n--- Historial Encontrado ---");
                mostrarDetalleHistorial(historial);
            } else {
                System.out.println("No se encontró historial con ID: " + id);
            }
        } catch (Exception e) {
            System.out.println("Error: Ingrese un ID válido");
            scanner.nextLine();
        }
    }

    private void actualizarHistorial() {
        System.out.println("=== Actualizar Historial ===");

        try {
            System.out.println("Ingrese ID del historial a actualizar: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            HistorialClinico historial = historialController.obtenerHistorial(id);
            if (historial != null) {
                System.out.println("Historial actual:");
                mostrarDetalleHistorial(historial);

                System.out.println("\n--- Actualizar Datos ---");
                System.out.println("Ingrese nueva descripción (Enter para mantener actual): ");
                System.out.println("Actual: " + historial.getDescripcion());
                String nuevaDescripcion = scanner.nextLine();
                if (!nuevaDescripcion.trim().isEmpty()) {
                    historial.setDescripcion(nuevaDescripcion);
                }

                System.out.println("Ingrese nuevo diagnóstico (Enter para mantener actual): ");
                System.out.println("Actual: " + historial.getDiagnostico());
                String nuevoDiagnostico = scanner.nextLine();
                if (!nuevoDiagnostico.trim().isEmpty()) {
                    historial.setDiagnostico(nuevoDiagnostico);
                }

                System.out.println("Ingrese nuevo tratamiento (Enter para mantener actual): ");
                System.out.println("Actual: " + historial.getTratamiento_recomendado());
                String nuevoTratamiento = scanner.nextLine();
                if (!nuevoTratamiento.trim().isEmpty()) {
                    historial.setTratamiento_recomendado(nuevoTratamiento);
                }

                if (historialController.actualizarHistorial(historial)) {
                    System.out.println("✓ Historial actualizado correctamente");
                } else {
                    System.out.println("✗ Error al actualizar el historial");
                }
            } else {
                System.out.println("Historial no encontrado con ID: " + id);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void mostrarDetalleHistorial(HistorialClinico historial) {
        System.out.println("ID: " + historial.getId());
        System.out.println("Mascota ID: " + historial.getMascota_id());
        System.out.println("Veterinario ID: " + historial.getVeterinario_id());
        System.out.println("Fecha: " + historial.getFecha_evento());
        System.out.println("Tipo de evento: " + obtenerNombreTipoEvento(historial.getEvento_tipo_id()));
        System.out.println("Descripción: " + historial.getDescripcion());
        System.out.println("Diagnóstico: " + historial.getDiagnostico());
        System.out.println("Tratamiento: " + historial.getTratamiento_recomendado());

        if (historial.getProducto_id() != null) {
            System.out.println("Producto ID: " + historial.getProducto_id());
            System.out.println("Cantidad utilizada: " + historial.getCantidad_utilizada());
        }
        System.out.println();
    }

    private void mostrarResumenHistorial(HistorialClinico historial) {
        System.out.printf("ID: %d | Mascota: %d | Fecha: %s | Tipo: %s | Descripción: %.50s%n",
                historial.getId(),
                historial.getMascota_id(),
                historial.getFecha_evento(),
                obtenerNombreTipoEvento(historial.getEvento_tipo_id()),
                historial.getDescripcion()
        );
    }

    private String obtenerNombreTipoEvento(int tipoId) {
        return switch (tipoId) {
            case 1 -> "Vacunación";
            case 2 -> "Consulta General";
            case 3 -> "Cirugía";
            case 4 -> "Control de Rutina";
            case 5 -> "Emergencia";
            default -> "Otro";
        };
    }
}
