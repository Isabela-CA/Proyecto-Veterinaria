package com.happyfeet.View;

import com.happyfeet.Controller.DuenosController;
import com.happyfeet.model.entities.Duenos;

import java.util.Scanner;

public class DuenoView {
    private DuenosController duenoController;
    private Scanner scanner = new Scanner(System.in);

    public DuenoView(DuenosController duenoController) {
        this.duenoController = duenoController;
    }

    public void mostrarMenuDueno() {
        System.out.println("=== Gestión de Dueños ===");
        System.out.println("1. Registrar Dueño");
        System.out.println("2. Listar Dueños");
        System.out.println("3. Buscar Dueño por ID");
        System.out.println("4. Actualizar Dueño");
        System.out.println("0. Salir");

        int opcion = scanner.nextInt();
        scanner.nextLine(); // se limpia el buffer

        switch (opcion) {
            case 1 -> registrarDueno();
            case 2 -> listarDuenos();
            case 3 -> buscarDueno();
            case 4 -> actualizarDueno();
            default -> System.out.println("Saliendo...");
        }
    }

    void registrarDueno() {
        System.out.println("Ingrese nombre completo: ");
        String nombre = scanner.nextLine();
        System.out.println("Ingrese documento: ");
        String documento = scanner.nextLine();
        System.out.println("Ingrese dirección: ");
        String direccion = scanner.nextLine();
        System.out.println("Ingrese teléfono: ");
        String telefono = scanner.nextLine();
        System.out.println("Ingrese email: ");
        String email = scanner.nextLine();
        System.out.println("Ingrese contacto de emergencia: ");
        String contacto = scanner.nextLine();

        Duenos dueno = new Duenos(nombre, documento, direccion, telefono, email, contacto);

        if (duenoController.agregarDuenos(dueno)) {
            System.out.println("El dueño fue registrado correctamente");
        } else {
            System.out.println("Error al registrar el dueño.");
        }
    }

    void listarDuenos() {
        duenoController.listarDuenos().forEach(d ->
                System.out.println(d.getId() + " - " + d.getNombre_completo())
        );
    }

    void buscarDueno() {
        System.out.println("Ingrese ID del dueño:");
        int id = scanner.nextInt();
        Duenos dueno = duenoController.obtenerDueno(id);
        if (dueno != null) {
            System.out.println("Encontrado: " + dueno.getNombre_completo());
        } else {
            System.out.println("No se encontró el dueño.");
        }
    }

    void actualizarDueno() {
        System.out.println("Ingrese ID del dueño a modificar:");
        int id = scanner.nextInt();
        scanner.nextLine();

        Duenos dueno = duenoController.obtenerDueno(id);
        if (dueno != null) {
            System.out.println("Ingrese nuevo nombre (actual: " + dueno.getNombre_completo() + "):");
            String nombre = scanner.nextLine();
            if (!nombre.trim().isEmpty()) {
                dueno.setNombre_completo(nombre);
            }

            if (duenoController.actualizarDueno(dueno)) {
                System.out.println("Dueño actualizado correctamente.");
            } else {
                System.out.println("Error al actualizar el dueño.");
            }
        } else {
            System.out.println("Dueño no encontrado.");
        }
    }
}