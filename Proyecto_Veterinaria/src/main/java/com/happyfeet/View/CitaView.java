package com.happyfeet.View;

import com.happyfeet.Controller.CitaController;
import com.happyfeet.model.entities.Cita;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;


public class CitaView {

    private final CitaController citaController;
    private final Scanner scanner;
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public CitaView(CitaController citaController) {
        this.citaController = citaController;
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenuCita() {
        System.out.println("""
            === Gestión de Citas ===
            1. Programar Cita
            2. Listar Citas
            3. Buscar Cita por ID
            4. Cambiar Estado de Cita
            5. Consultar por Mascota
            6. Consultar por Veterinario
            0. Salir
            """);

        try {
            int opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1 -> programarCita();
                case 2 -> listarCitas();
                case 3 -> buscarCita();
                case 4 -> cambiarEstado();
                case 5 -> consultarPorMascota();
                case 6 -> consultarPorVeterinario();
                default -> System.out.println("Saliendo...");
            }
        } catch (Exception e) {
            System.out.println("❌ Error: Ingrese un número válido.");
            scanner.nextLine();
        }
    }

    private void programarCita() {
        System.out.println("=== Programar Nueva Cita ===");

        try {
            System.out.print("Ingrese ID de la mascota: ");
            int mascotaId = scanner.nextInt();

            System.out.print("Ingrese ID del veterinario: ");
            int veterinarioId = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Ingrese fecha y hora (dd/MM/yyyy HH:mm): ");
            String fechaStr = scanner.nextLine();

            System.out.print("Ingrese motivo de la consulta: ");
            String motivo = scanner.nextLine();

            var fechaHora = LocalDateTime.parse(fechaStr, FORMATTER);

            if (citaController.programarCita(mascotaId, veterinarioId, fechaHora, motivo)) {
                System.out.println("✓ Cita programada correctamente.");
            } else {
                System.out.println("❌ Error al programar la cita.");
            }
        } catch (DateTimeParseException e) {
            System.out.println("❌ Error: Formato de fecha incorrecto. Use dd/MM/yyyy HH:mm");
        } catch (Exception e) {
            System.out.println("❌ Error: Ingrese datos válidos.");
            scanner.nextLine();
        }
    }

    private void listarCitas() {
        System.out.println("=== Lista de Citas ===");
        try {
            var citas = (List<Cita>) citaController.consultarCitas();
            if (citas.isEmpty()) {
                System.out.println("No hay citas registradas.");
            } else {
                citas.forEach(cita -> System.out.println("""
                    %d - Mascota: %d - Vet: %d - %s - %s
                    """.formatted(
                        cita.getId(),
                        cita.getMascota_id(),
                        cita.getVeterinario_id(),
                        cita.getFecha_hora().format(FORMATTER),
                        obtenerNombreEstado(cita.getEstado_id())
                )));
            }
        } catch (Exception e) {
            System.out.println("❌ Error al listar las citas: " + e.getMessage());
        }
    }

    private void buscarCita() {
        try {
            System.out.print("Ingrese ID de la cita: ");
            int id = scanner.nextInt();

            var cita = citaController.consultarCitaPorId(id);
            if (cita != null) {
                mostrarDetalleCita(cita);
            } else {
                System.out.println("❌ No se encontró la cita.");
            }
        } catch (Exception e) {
            System.out.println("❌ Error: Ingrese un ID válido.");
            scanner.nextLine();
        }
    }

    private void cambiarEstado() {
        try {
            System.out.print("\nIngrese ID de la cita: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            var cita = citaController.consultarCitaPorId(id);
            if (cita != null) {
                System.out.println("\n=== CITA ENCONTRADA ===");
                mostrarDetalleCita(cita);

                System.out.println("""
                    
                    === SELECCIONE NUEVO ESTADO ===
                    1. Programada
                    2. Confirmada
                    3. En Proceso
                    4. Finalizada
                    5. Cancelada
                    6. No Asistió
                    """);
                System.out.print("Opción: ");

                int nuevoEstado = scanner.nextInt();
                scanner.nextLine();

                if (nuevoEstado >= 1 && nuevoEstado <= 6) {
                    if (citaController.cambiarEstadoCita(id, nuevoEstado)) {
                        System.out.println("✓ Estado actualizado correctamente a: " +
                                obtenerNombreEstado(nuevoEstado));
                    } else {
                        System.out.println("❌ Error al actualizar el estado. " +
                                "Verifique las reglas de transición.");
                    }
                } else {
                    System.out.println("❌ Estado inválido. Seleccione entre 1 y 6.");
                }
            } else {
                System.out.println("❌ Cita no encontrada con ID: " + id);
            }
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            scanner.nextLine();
        }
    }

    private void consultarPorMascota() {
        try {
            System.out.print("Ingrese ID de la mascota: ");
            int mascotaId = scanner.nextInt();

            var citas = citaController.consultarCitasPorMascota(mascotaId);
            if (citas.isEmpty()) {
                System.out.println("No hay citas para esta mascota.");
            } else {
                System.out.println("=== Citas de la Mascota ID: " + mascotaId + " ===");
                citas.forEach(cita -> System.out.println("""
                    %d - %s - %s - %s
                    """.formatted(
                        cita.getId(),
                        cita.getFecha_hora().format(FORMATTER),
                        obtenerNombreEstado(cita.getEstado_id()),
                        cita.getMotivo()
                )));
            }
        } catch (Exception e) {
            System.out.println("❌ Error: Ingrese un ID válido.");
            scanner.nextLine();
        }
    }

    private void consultarPorVeterinario() {
        try {
            System.out.print("Ingrese ID del veterinario: ");
            int veterinarioId = scanner.nextInt();

            var citas = citaController.consultarCitasPorVeterinario(veterinarioId);
            if (citas.isEmpty()) {
                System.out.println("No hay citas para este veterinario.");
            } else {
                System.out.println("=== Citas del Veterinario ID: " + veterinarioId + " ===");
                citas.forEach(cita -> System.out.println("""
                    %d - Mascota: %d - %s - %s
                    """.formatted(
                        cita.getId(),
                        cita.getMascota_id(),
                        cita.getFecha_hora().format(FORMATTER),
                        obtenerNombreEstado(cita.getEstado_id())
                )));
            }
        } catch (Exception e) {
            System.out.println("❌ Error: Ingrese un ID válido.");
            scanner.nextLine();
        }
    }

    private void mostrarDetalleCita(Cita cita) {
        System.out.println("""
            ID: %d
            Mascota ID: %d
            Veterinario ID: %d
            Fecha y Hora: %s
            Motivo: %s
            Estado: %s
            """.formatted(
                cita.getId(),
                cita.getMascota_id(),
                cita.getVeterinario_id(),
                cita.getFecha_hora().format(FORMATTER),
                cita.getMotivo(),
                obtenerNombreEstado(cita.getEstado_id())
        ));
    }

    private String obtenerNombreEstado(int estadoId) {
        return switch (estadoId) {
            case 1 -> "Programada";
            case 2 -> "Confirmada";
            case 3 -> "En Proceso";
            case 4 -> "Finalizada";
            case 5 -> "Cancelada";
            case 6 -> "No Asistió";
            default -> "Desconocido (%d)".formatted(estadoId);
        };
    }
}