/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.happyfeet.test;

import com.happyfeet.Controller.HistorialClinicoController;
import com.happyfeet.model.entities.HistorialClinico;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author camper
 */
public class historialview {
   
    private HistorialClinicoController historialController;
    private Scanner scanner = new Scanner(System.in);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public historialview(HistorialClinicoController historialController) {
        this.historialController = historialController;
    }

    public void mostrarMenu() {
        System.out.println("""
                ╔════════════════════════════════════════╗
                ║    GESTIÓN DE HISTORIAL CLÍNICO        ║
                ╠════════════════════════════════════════╣
                ║ 1. imprimir historial clinico          ║
                ╚════════════════════════════════════════╝
                """);

        try {
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1 -> buscarHistorialPorId();
                default -> System.out.println("Opción no válida");
            }
        } catch (Exception e) {
            System.out.println("Error: Ingrese un número válido");
            scanner.nextLine(); // limpiar buffer en caso de error
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

