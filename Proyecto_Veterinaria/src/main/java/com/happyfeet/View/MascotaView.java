package com.happyfeet.View;

import com.happyfeet.Controller.MascotaController;
import com.happyfeet.model.entities.Mascota;
import com.happyfeet.model.enums.SexoMascota;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;


public class MascotaView {

    private final MascotaController mascotaController;
    private final Scanner scanner;
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public MascotaView(MascotaController mascotaController) {
        this.mascotaController = mascotaController;
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenuMascotas() {
        System.out.println("""
            === Gestión de Mascotas ===
            1. Registrar Mascota
            2. Listar Mascotas
            3. Buscar Mascota por ID
            4. Listar Mascotas por Dueño
            5. Actualizar Mascota
            6. Transferir Propiedad
            0. Salir
            """);

        try {
            int opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1 -> registrarMascota();
                case 2 -> listarMascotas();
                case 3 -> buscarMascota();
                case 4 -> listarMascotasPorDueno();
                case 5 -> actualizarMascota();
                case 6 -> transferirPropiedad();
                default -> System.out.println("Saliendo...");
            }
        } catch (Exception e) {
            System.out.println("Error: Ingrese un número válido.");
            scanner.nextLine();
        }
    }

    private void registrarMascota() {
        System.out.println("=== Registrar Nueva Mascota ===");

        try {
            System.out.print("Ingrese ID del dueño: ");
            int duenoId = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Ingrese nombre de la mascota: ");
            String nombre = scanner.nextLine();

            System.out.print("Ingrese ID de raza: ");
            int razaId = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Ingrese fecha de nacimiento (yyyy-MM-dd): ");
            String fechaStr = scanner.nextLine();
            var fechaNacimiento = LocalDate.parse(fechaStr, DATE_FORMATTER);

            System.out.print("Ingrese sexo (MACHO/HEMBRA): ");
            String sexoStr = scanner.nextLine();
            var sexo = SexoMascota.Sexo.fromString(sexoStr);

            System.out.print("ingrese el peso actual: ");
            double peso = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("ingrese el microchip: ");
            String microchip = scanner.nextLine();

            System.out.print("ingrese tatuaje: ");
            String tatuaje = scanner.nextLine();

            System.out.print("ingrese url de la foto: ");
            String url_foto = scanner.nextLine();

            System.out.print("ingrese las alergias que tiene la mascota: ");
            String alergias = scanner.nextLine();

            System.out.print("ingrese las condiciones preexistentes: ");
            String condiciones_preexistentes = scanner.nextLine();



            var mascota = new Mascota(duenoId, nombre, razaId, fechaNacimiento, sexo,
                     peso, microchip.isBlank() ? null:microchip , tatuaje.isBlank() ? null:tatuaje ,
                    url_foto.isBlank() ? null : url_foto, alergias.isBlank() ? null : alergias,
                    condiciones_preexistentes.isBlank() ? null : condiciones_preexistentes);

            if (mascotaController.agregarMascota(mascota)) {
                System.out.println("Mascota registrada correctamente");
            } else {
                System.out.println("Error al registrar la mascota");
            }
        } catch (DateTimeParseException e) {
            System.out.println("Error: Formato de fecha inválido. Use yyyy-MM-dd");
        } catch (IllegalArgumentException e) {
            System.out.println("Error de validación: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
            scanner.nextLine(); // Limpiar buffer
        }
    }

    private void listarMascotas() {
        System.out.println("=== Lista de Mascotas ===");
        var mascotas = mascotaController.listarMascotas();

        if (mascotas.isEmpty()) {
            System.out.println("No hay mascotas registradas.");
        } else {
            mascotas.forEach(m -> System.out.println(
                    "%d - %s (Dueño ID: %d) - %s".formatted(
                            m.getId(),
                            m.getNombre(),
                            m.getDueno_id(),
                            m.getSexo(),
                            m.getRaza_id(),
                            m.getPeso_actual(),
                            m.getMicrochip(),
                            m.getTatuaje(),
                            m.getAlergias(),
                            m.getCondiciones_preexistentes()
                    )
            ));
        }
    }

    private void buscarMascota() {
        try {
            System.out.print("Ingrese ID de la mascota: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            var mascota = mascotaController.obtenerMascota(id);
            if (mascota != null) {
                mostrarDetalleMascota(mascota);
            } else {
                System.out.println("No se encontró la mascota.");
            }
        } catch (Exception e) {
            System.out.println("Error: Ingrese un ID válido.");
            scanner.nextLine();
        }
    }

    private void listarMascotasPorDueno() {
        try {
            System.out.print("Ingrese ID del dueño: ");
            int duenoId = scanner.nextInt();
            scanner.nextLine();

            var mascotas = mascotaController.listarMascotasPorDueno(duenoId);
            if (mascotas.isEmpty()) {
                System.out.println("El dueño no tiene mascotas registradas.");
            } else {
                System.out.println("=== Mascotas del dueño %d ===".formatted(duenoId));
                mascotas.forEach(m -> System.out.println(
                        "- %s (%s) - Raza ID: %d".formatted(
                                m.getNombre(),
                                m.getSexo(),
                                m.getRaza_id(),
                                m.getPeso_actual(),
                                m.getMicrochip(),
                                m.getTatuaje(),
                                m.getAlergias(),
                                m.getCondiciones_preexistentes()
                        )
                ));
            }
        } catch (Exception e) {
            System.out.println("Error: Ingrese un ID válido.");
            scanner.nextLine();
        }
    }

    private void actualizarMascota() {
        try {
            System.out.print("Ingrese ID de la mascota a modificar: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            var mascota = mascotaController.obtenerMascota(id);
            if (mascota != null) {
                System.out.println("Mascota actual:");
                mostrarDetalleMascota(mascota);

                System.out.println("\n=== Actualizar Datos ===");
                System.out.println("(Presione Enter para mantener el valor actual)");

                System.out.print("Nuevo nombre [%s]: ".formatted(mascota.getNombre()));
                String nombre = scanner.nextLine();
                if (!nombre.isBlank()) {
                    mascota.setNombre(nombre);
                }

                if (mascotaController.actualizarMascota(mascota)) {
                    System.out.println("Mascota actualizada correctamente.");
                } else {
                    System.out.println("Error al actualizar la mascota.");
                }
            } else {
                System.out.println("Mascota no encontrada.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            scanner.nextLine();
        }
    }

    private void transferirPropiedad() {
        try {
            System.out.print("Ingrese ID de la mascota: ");
            int mascotaId = scanner.nextInt();

            System.out.print("Ingrese ID del nuevo dueño: ");
            int nuevoDuenoId = scanner.nextInt();
            scanner.nextLine();

            if (mascotaController.transferirMascota(mascotaId, nuevoDuenoId)) {
                System.out.println("Propiedad transferida correctamente.");
            } else {
                System.out.println("Error al transferir la propiedad.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            scanner.nextLine();
        }
    }

    private void mostrarDetalleMascota(Mascota mascota) {
        System.out.println("""
        ╔════════════════════════════════════════╗
        ║       INFORMACIÓN DE LA MASCOTA        ║
        ╠════════════════════════════════════════╣
        ║ ID: %d
        ║ Nombre: %s
        ║ Dueño ID: %d
        ║ Raza ID: %d
        ║ Fecha de Nacimiento: %s
        ║ Sexo: %s
        ║ Peso Actual: %.2f kg
        ║ Microchip: %s
        ║ Tatuaje: %s
        ║ URL Foto: %s
        ║ Alergias: %s
        ║ Condiciones Preexistentes: %s
        ╚════════════════════════════════════════╝
        """.formatted(
                mascota.getId(),
                mascota.getNombre(),
                mascota.getDueno_id(),
                mascota.getRaza_id(),
                mascota.getFecha_nacimiento(),
                mascota.getSexo(),
                mascota.getPeso_actual(),
                mascota.getMicrochip() != null ? mascota.getMicrochip() : "No registrado",
                mascota.getTatuaje() != null ? mascota.getTatuaje() : "No registrado",
                mascota.getUrl_foto() != null ? mascota.getUrl_foto() : "Sin foto",
                mascota.getAlergias() != null ? mascota.getAlergias() : "Ninguna",
                mascota.getCondiciones_preexistentes() != null ? mascota.getCondiciones_preexistentes() : "Ninguna"
        ));
    }
}