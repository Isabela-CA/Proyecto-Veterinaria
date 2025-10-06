package com.happyfeet.View;

import com.happyfeet.Controller.VeterinarioController;
import com.happyfeet.model.entities.Veterinario;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class VeterinarioView {

    private final VeterinarioController veterinarioController;
    private final Scanner scanner;
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public VeterinarioView(VeterinarioController veterinarioController) {
        this.veterinarioController = new VeterinarioController();
        this.scanner = new Scanner(System.in);
    }

    public void MenuVeterinarios() {
        int opcion;
        do {
            System.out.println("""
                
                ═══════════════════════════════════════
                === Gestión de Veterinarios ===
                ═══════════════════════════════════════
                1. Registrar Veterinario
                2. Listar Veterinarios
                3. Buscar Veterinario por ID
                4. Listar Veterinarios Activos
                5. Actualizar Veterinario
                6. Cambiar Estado Activo
                0. Volver al menú principal
                ═══════════════════════════════════════
                """);

            try {
                System.out.print("Seleccione una opción: ");
                opcion = scanner.nextInt();
                scanner.nextLine(); // limpiar buffer

                switch (opcion) {
                    case 1 -> registrarVeterinario();
                    case 2 -> listarVeterinarios();
                    case 3 -> buscarVeterinario();
                    case 4 -> listarVeterinariosActivos();
                    case 5 -> actualizarVeterinario();
                    case 6 -> cambiarEstadoActivo();
                    case 0 -> System.out.println("\n✓ Volviendo al menú principal...");
                    default -> System.out.println("\n✗ Opción inválida. Intente nuevamente.");
                }
            } catch (Exception e) {
                System.out.println("\n✗ Error: Ingrese un número válido.");
                scanner.nextLine();
                opcion = -1; // Continuar el bucle
            }

            if (opcion != 0) {
                pausar();
            }
        } while (opcion != 0);
    }

    private void registrarVeterinario() {
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("=== Registrar Nuevo Veterinario ===");
        System.out.println("═══════════════════════════════════════\n");

        try {
            System.out.print("Ingrese nombre completo: ");
            String nombreCompleto = scanner.nextLine();

            System.out.print("Ingrese documento de identidad: ");
            String documentoIdentidad = scanner.nextLine();

            System.out.print("Ingrese licencia profesional: ");
            String licenciaProfesional = scanner.nextLine();

            System.out.print("Ingrese especialidad: ");
            String especialidad = scanner.nextLine();

            System.out.print("Ingrese teléfono: ");
            String telefono = scanner.nextLine();

            System.out.print("Ingrese email: ");
            String email = scanner.nextLine();

            System.out.print("Ingrese fecha de contratación (yyyy-MM-dd): ");
            String fechaStr = scanner.nextLine();
            var fechaContratacion = LocalDate.parse(fechaStr, DATE_FORMATTER);

            var veterinario = new Veterinario(nombreCompleto, documentoIdentidad,
                    licenciaProfesional, especialidad, telefono, email,
                    fechaContratacion, 1);

            if (veterinarioController.registrar(veterinario)) {
                System.out.println("\n✓ Veterinario registrado correctamente");
            } else {
                System.out.println("\n✗ Error al registrar el veterinario");
            }
        } catch (DateTimeParseException e) {
            System.out.println("\n✗ Error: Formato de fecha inválido. Use yyyy-MM-dd");
        } catch (IllegalArgumentException e) {
            System.out.println("\n✗ Error de validación: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\n✗ Error inesperado: " + e.getMessage());
        }
    }

    private void listarVeterinarios() {
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("=== Lista de Veterinarios ===");
        System.out.println("═══════════════════════════════════════\n");

        var veterinarios = veterinarioController.listarTodos();

        if (veterinarios.isEmpty()) {
            System.out.println("No hay veterinarios registrados.");
        } else {
            System.out.println("Total: " + veterinarios.size() + " veterinarios\n");
            System.out.println("─".repeat(80));
            veterinarios.forEach(v -> System.out.println(
                    "ID: %-4d | %-30s | Doc: %-15s | %-20s | %s".formatted(
                            v.getId(),
                            v.getNombre_completo(),
                            v.getDocumento_identidad(),
                            v.getEspecialidad(),
                            v.getActivo() == 1 ? "✓ ACTIVO" : "✗ INACTIVO"
                    )
            ));
            System.out.println("─".repeat(80));
        }
    }

    private void buscarVeterinario() {
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("=== Buscar Veterinario por ID ===");
        System.out.println("═══════════════════════════════════════\n");

        try {
            System.out.print("Ingrese ID del veterinario: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            var veterinario = veterinarioController.buscarPorId(id);
            if (veterinario != null) {
                System.out.println("\n✓ Veterinario encontrado:");
                mostrarDetalleVeterinario(veterinario);
            } else {
                System.out.println("\n✗ No se encontró el veterinario con ID: " + id);
            }
        } catch (Exception e) {
            System.out.println("\n✗ Error: Ingrese un ID válido.");
            scanner.nextLine();
        }
    }

    private void listarVeterinariosActivos() {
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("=== Veterinarios Activos ===");
        System.out.println("═══════════════════════════════════════\n");

        try {
            var veterinarios = veterinarioController.listarActivos();
            if (veterinarios.isEmpty()) {
                System.out.println("No hay veterinarios activos.");
            } else {
                System.out.println("Total: " + veterinarios.size() + " veterinarios activos\n");
                System.out.println("─".repeat(80));
                veterinarios.forEach(v -> System.out.println(
                        "- %-30s | Doc: %-15s | Especialidad: %s".formatted(
                                v.getNombre_completo(),
                                v.getDocumento_identidad(),
                                v.getEspecialidad()
                        )
                ));
                System.out.println("─".repeat(80));
            }
        } catch (Exception e) {
            System.out.println("\n✗ Error: " + e.getMessage());
        }
    }

    private void actualizarVeterinario() {
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("=== Actualizar Veterinario ===");
        System.out.println("═══════════════════════════════════════\n");

        try {
            System.out.print("Ingrese ID del veterinario a modificar: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            var veterinario = veterinarioController.buscarPorId(id);
            if (veterinario != null) {
                System.out.println("\nVeterinario actual:");
                mostrarDetalleVeterinario(veterinario);

                System.out.println("\n--- Actualizar Datos (Enter para mantener valor actual) ---\n");

                System.out.print("Nuevo nombre [%s]: ".formatted(veterinario.getNombre_completo()));
                String nombre = scanner.nextLine();
                if (!nombre.isBlank()) {
                    veterinario.setNombre_completo(nombre);
                }

                System.out.print("Nueva especialidad [%s]: ".formatted(veterinario.getEspecialidad()));
                String especialidad = scanner.nextLine();
                if (!especialidad.isBlank()) {
                    veterinario.setEspecialidad(especialidad);
                }

                System.out.print("Nuevo teléfono [%s]: ".formatted(veterinario.getTelefono()));
                String telefono = scanner.nextLine();
                if (!telefono.isBlank()) {
                    veterinario.setTelefono(telefono);
                }

                System.out.print("Nuevo email [%s]: ".formatted(veterinario.getEmail()));
                String email = scanner.nextLine();
                if (!email.isBlank()) {
                    veterinario.setEmail(email);
                }

                if (veterinarioController.modificar(veterinario)) {
                    System.out.println("\n✓ Veterinario actualizado correctamente.");
                } else {
                    System.out.println("\n✗ Error al actualizar el veterinario.");
                }
            } else {
                System.out.println("\n✗ Veterinario no encontrado.");
            }
        } catch (Exception e) {
            System.out.println("\n✗ Error: " + e.getMessage());
            scanner.nextLine();
        }
    }

    private void cambiarEstadoActivo() {
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("=== Cambiar Estado Activo ===");
        System.out.println("═══════════════════════════════════════\n");

        try {
            System.out.print("Ingrese ID del veterinario: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            var veterinario = veterinarioController.buscarPorId(id);
            if (veterinario != null) {
                System.out.println("\nVeterinario: " + veterinario.getNombre_completo());
                System.out.println("Estado actual: " + (veterinario.getActivo() == 1 ? "✓ ACTIVO" : "✗ INACTIVO"));
                System.out.print("\n¿Cambiar a ACTIVO? (S/N): ");
                String respuesta = scanner.nextLine();
                boolean nuevoEstado = respuesta.equalsIgnoreCase("S");

                if (veterinarioController.cambiarEstadoActivo(id, nuevoEstado)) {
                    System.out.println("\n✓ Estado cambiado correctamente a: " +
                            (nuevoEstado ? "ACTIVO" : "INACTIVO"));
                } else {
                    System.out.println("\n✗ Error al cambiar el estado.");
                }
            } else {
                System.out.println("\n✗ Veterinario no encontrado.");
            }
        } catch (Exception e) {
            System.out.println("\n✗ Error: " + e.getMessage());
            scanner.nextLine();
        }
    }

    private void mostrarDetalleVeterinario(Veterinario veterinario) {
        System.out.println("\n╔" + "═".repeat(60) + "╗");
        System.out.println("║    INFORMACIÓN DEL VETERINARIO");
        System.out.println("╠" + "═".repeat(60) + "╣");
        System.out.printf("║ ID: %-55d ║%n", veterinario.getId());
        System.out.printf("║ Nombre: %-51s ║%n", truncar(veterinario.getNombre_completo(), 51));
        System.out.printf("║ Documento: %-48s ║%n", veterinario.getDocumento_identidad());
        System.out.printf("║ Licencia: %-49s ║%n", veterinario.getLicencia_profesional());
        System.out.printf("║ Especialidad: %-45s ║%n",
                truncar(veterinario.getEspecialidad(), 45));
        System.out.printf("║ Teléfono: %-49s ║%n", veterinario.getTelefono());
        System.out.printf("║ Email: %-52s ║%n", truncar(veterinario.getEmail(), 52));
        System.out.printf("║ Fecha Contratación: %-39s ║%n",
                veterinario.getFecha_contratacion());
        System.out.printf("║ Estado: %-51s ║%n",
                veterinario.getActivo() == 1 ? "✓ ACTIVO" : "✗ INACTIVO");
        System.out.println("╚" + "═".repeat(60) + "╝");
    }

    private String truncar(String texto, int maxLength) {
        if (texto == null) return "N/A";
        if (texto.length() <= maxLength) return texto;
        return texto.substring(0, maxLength - 3) + "...";
    }

    private void pausar() {
        System.out.print("\nPresione Enter para continuar...");
        try {
            scanner.nextLine();
        } catch (Exception e) {
            // Ignorar errores
        }
    }
}