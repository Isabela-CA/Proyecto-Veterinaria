package com.happyfeet.View;

import com.happyfeet.Controller.DuenosController;
import com.happyfeet.model.entities.Duenos;

import java.util.Scanner;


public class DuenoView {

    private final DuenosController duenoController;
    private final Scanner scanner;

    public DuenoView(DuenosController duenoController) {
        this.duenoController = duenoController;
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenuDueno() {
        System.out.println("""
            === Gestión de Dueños ===
            1. Registrar Dueño
            2. Listar Dueños
            3. Buscar Dueño por ID
            4. Actualizar Dueño
            0. Salir
            """);

        try {
            int opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1 -> registrarDueno();
                case 2 -> listarDuenos();
                case 3 -> buscarDueno();
                case 4 -> actualizarDueno();
                default -> System.out.println("Saliendo...");
            }
        } catch (Exception e) {
            System.out.println("❌ Error: Ingrese un número válido.");
            scanner.nextLine();
        }
    }

    void registrarDueno() {
        System.out.println("=== Registrar Nuevo Dueño ===");

        try {
            System.out.print("Ingrese nombre completo: ");
            String nombre = scanner.nextLine();

            System.out.print("Ingrese documento: ");
            String documento = scanner.nextLine();

            System.out.print("Ingrese dirección: ");
            String direccion = scanner.nextLine();

            System.out.print("Ingrese teléfono: ");
            String telefono = scanner.nextLine();

            System.out.print("Ingrese email: ");
            String email = scanner.nextLine();

            System.out.print("Ingrese contacto de emergencia: ");
            String contacto = scanner.nextLine();

            var dueno = new Duenos(nombre, documento, direccion, telefono, email, contacto);

            if (duenoController.agregarDuenos(dueno)) {
                System.out.println("✓ El dueño fue registrado correctamente");
            } else {
                System.out.println("❌ Error al registrar el dueño.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Error de validación: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Error inesperado: " + e.getMessage());
        }
    }

    void listarDuenos() {
        System.out.println("=== Lista de Dueños ===");
        var duenos = duenoController.listarDuenos();

        if (duenos.isEmpty()) {
            System.out.println("No hay dueños registrados.");
        } else {
            duenos.forEach(d -> System.out.println(
                    "%d - %s (Doc: %s)".formatted(
                            d.getId(),
                            d.getNombre_completo(),
                            d.getDocumento_identidad()
                    )
            ));
        }
    }

    void buscarDueno() {
        try {
            System.out.print("Ingrese ID del dueño: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            var dueno = duenoController.obtenerDueno(id);
            if (dueno != null) {
                mostrarDetalleDueno(dueno);
            } else {
                System.out.println("❌ No se encontró el dueño.");
            }
        } catch (Exception e) {
            System.out.println("❌ Error: Ingrese un ID válido.");
            scanner.nextLine();
        }
    }

    void actualizarDueno() {
        try {
            System.out.print("Ingrese ID del dueño a modificar: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            var dueno = duenoController.obtenerDueno(id);
            if (dueno != null) {
                System.out.println("Dueño actual:");
                mostrarDetalleDueno(dueno);

                System.out.println("\n=== Actualizar Datos ===");
                System.out.println("(Presione Enter para mantener el valor actual)");

                System.out.print("Nuevo nombre [%s]: ".formatted(dueno.getNombre_completo()));
                String nombre = scanner.nextLine();
                if (!nombre.isBlank()) {
                    dueno.setNombre_completo(nombre);
                }

                System.out.print("Nueva dirección [%s]: ".formatted(dueno.getDireccion()));
                String direccion = scanner.nextLine();
                if (!direccion.isBlank()) {
                    dueno.setDireccion(direccion);
                }

                System.out.print("Nuevo teléfono [%s]: ".formatted(dueno.getTelefono()));
                String telefono = scanner.nextLine();
                if (!telefono.isBlank()) {
                    dueno.setTelefono(telefono);
                }

                System.out.print("Nuevo email [%s]: ".formatted(dueno.getEmail()));
                String email = scanner.nextLine();
                if (!email.isBlank()) {
                    dueno.setEmail(email);
                }

                if (duenoController.actualizarDueno(dueno)) {
                    System.out.println("✓ Dueño actualizado correctamente.");
                } else {
                    System.out.println("❌ Error al actualizar el dueño.");
                }
            } else {
                System.out.println("❌ Dueño no encontrado.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Error de validación: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            scanner.nextLine();
        }
    }

    private void mostrarDetalleDueno(Duenos dueno) {
        System.out.println("""
            ╔════════════════════════════════════════╗
            ║         INFORMACIÓN DEL DUEÑO          ║
            ╠════════════════════════════════════════╣
            ║ ID: %d
            ║ Nombre: %s
            ║ Documento: %s
            ║ Dirección: %s
            ║ Teléfono: %s
            ║ Email: %s
            ║ Contacto Emergencia: %s
            ╚════════════════════════════════════════╝
            """.formatted(
                dueno.getId(),
                dueno.getNombre_completo(),
                dueno.getDocumento_identidad(),
                dueno.getDireccion(),
                dueno.getTelefono(),
                dueno.getEmail(),
                dueno.getContacto_emergencia()
        ));
    }
}