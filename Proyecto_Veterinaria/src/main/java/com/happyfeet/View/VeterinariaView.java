package com.happyfeet.View;

import com.happyfeet.Controller.*;

import java.util.Scanner;

public class VeterinariaView {
    private static Scanner input = new Scanner(System.in);

    // Controladores
    private static DuenosController duenosController = new DuenosController();
    private static MascotaController mascotaController = new MascotaController();
    private static CitaController citaController = new CitaController();
    private static HistorialClinicoController historialController = new HistorialClinicoController();
    private static ProcedimientoQuirurgicoController procedimientoController = new ProcedimientoQuirurgicoController();

    // Views
    private static DuenoView duenoView = new DuenoView(duenosController);
    private static MascotaView mascotaView = new MascotaView(mascotaController);
    private static CitaView citaView = new CitaView(citaController);
    private static HistorialClinicoView historialView = new HistorialClinicoView(historialController);
    private static ProcedimientoQuirurgicoView procedimientoView = new ProcedimientoQuirurgicoView(procedimientoController);

    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE GESTIÓN VETERINARIA HAPPY FEET ===");
        System.out.println("Bienvenido al sistema de gestión veterinaria");

        boolean continuar = true;

        while (continuar) {
            continuar = mostrarMenuPrincipal();
        }

        System.out.println("¡Gracias por usar el sistema veterinario Happy Feet!");
        input.close();
    }

    private static boolean mostrarMenuPrincipal() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("=== MENÚ PRINCIPAL VETERINARIA HAPPY FEET ===");
        System.out.println("=".repeat(50));
        System.out.println("1. Gestión de Dueños");
        System.out.println("2. Gestión de Mascotas");
        System.out.println("3. Gestión de Citas");
        System.out.println("4. Gestión de Historial Clínico");
        System.out.println("5. Procedimientos Quirúrgicos");
        System.out.println("0. Salir del Sistema");
        System.out.println("=".repeat(50));
        System.out.print("Seleccione una opción: ");

        try {
            int opcion = input.nextInt();
            input.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1 -> {
                    mostrarSubmenuDuenos();
                    return true;
                }
                case 2 -> {
                    mostrarSubmenuMascotas();
                    return true;
                }
                case 3 -> {
                    mostrarSubmenuCitas();
                    return true;
                }
                case 4 -> {
                    mostrarSubmenuHistorialClinico();
                    return true;
                }
                case 5 -> {
                    mostrarSubmenuProcedimientosQuirurgicos();
                    return true;
                }
                case 0 -> {
                    System.out.println("\n¡Gracias por usar el sistema veterinario Happy Feet!");
                    System.out.println("Cerrando sistema...");
                    return false;
                }
                default -> {
                    System.out.println(" Opción inválida. Por favor seleccione una opción válida (0-5).");
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println(" Error: Ingrese un número válido");
            input.nextLine(); // limpiar buffer en caso de error
            return true;
        }
    }

    private static void mostrarSubmenuDuenos() {
        boolean volverAlMenuPrincipal = false;

        while (!volverAlMenuPrincipal) {
            System.out.println("\n" + "=".repeat(40));
            System.out.println("=== GESTIÓN DE DUEÑOS ===");
            System.out.println("=".repeat(40));
            System.out.println("1. Registrar Dueño");
            System.out.println("2. Listar Dueños");
            System.out.println("3. Buscar Dueño por ID");
            System.out.println("4. Actualizar Dueño");
            System.out.println("0. Volver al Menú Principal");
            System.out.println("=".repeat(40));
            System.out.print("Seleccione una opción: ");

            try {
                int opcion = input.nextInt();
                input.nextLine(); // limpiar buffer

                switch (opcion) {
                    case 1 -> duenoView.registrarDueno();
                    case 2 -> duenoView.listarDuenos();
                    case 3 -> duenoView.buscarDueno();
                    case 4 -> duenoView.actualizarDueno();
                    case 0 -> {
                        System.out.println("Regresando al menú principal...");
                        volverAlMenuPrincipal = true;
                    }
                    default -> System.out.println("Opción inválida. Intente nuevamente.");
                }
            } catch (Exception e) {
                System.out.println("Error: Ingrese un número válido");
                input.nextLine(); // limpiar buffer en caso de error
            }

            if (!volverAlMenuPrincipal) {
                pausarYContinuar();
            }
        }
    }

    private static void mostrarSubmenuMascotas() {
        boolean volverAlMenuPrincipal = false;

        while (!volverAlMenuPrincipal) {
            System.out.println("\n" + "=".repeat(40));
            System.out.println("=== GESTIÓN DE MASCOTAS ===");
            System.out.println("=".repeat(40));

            try {
                mascotaView.mostrarMenuMascotas();
                volverAlMenuPrincipal = true; // El menú de mascotas maneja su propio flujo
            } catch (Exception e) {
                System.out.println(" Error en el menú de mascotas: " + e.getMessage());
                pausarYContinuar();
                volverAlMenuPrincipal = true;
            }
        }
    }

    private static void mostrarSubmenuCitas() {
        boolean volverAlMenuPrincipal = false;

        while (!volverAlMenuPrincipal) {
            System.out.println("\n" + "=".repeat(40));
            System.out.println("=== GESTIÓN DE CITAS ===");
            System.out.println("=".repeat(40));

            try {
                citaView.mostrarMenuCita();
                volverAlMenuPrincipal = true; // El menú de citas maneja su propio flujo
            } catch (Exception e) {
                System.out.println(" Error en el menú de citas: " + e.getMessage());
                pausarYContinuar();
                volverAlMenuPrincipal = true;
            }
        }
    }

    private static void mostrarSubmenuHistorialClinico() {
        boolean volverAlMenuPrincipal = false;

        while (!volverAlMenuPrincipal) {
            System.out.println("\n" + "=".repeat(40));
            System.out.println("=== GESTIÓN DE HISTORIAL CLÍNICO ===");
            System.out.println("=".repeat(40));

            try {
                historialView.mostrarMenu();
                volverAlMenuPrincipal = true; // El menú de historial maneja su propio flujo
            } catch (Exception e) {
                System.out.println(" Error en el menú de historial clínico: " + e.getMessage());
                pausarYContinuar();
                volverAlMenuPrincipal = true;
            }
        }
    }

    private static void mostrarSubmenuProcedimientosQuirurgicos() {
        boolean volverAlMenuPrincipal = false;

        while (!volverAlMenuPrincipal) {
            System.out.println("\n" + "=".repeat(40));
            System.out.println("=== PROCEDIMIENTOS QUIRÚRGICOS ===");
            System.out.println("=".repeat(40));

            try {
                procedimientoView.mostrarMenuProcedimientos();
                volverAlMenuPrincipal = true; // El menú de procedimientos maneja su propio flujo
            } catch (Exception e) {
                System.out.println(" Error en el menú de procedimientos quirúrgicos: " + e.getMessage());
                pausarYContinuar();
                volverAlMenuPrincipal = true;
            }
        }
    }

    private static void pausarYContinuar() {
        System.out.println("\nPresione Enter para continuar...");
        try {
            input.nextLine();
        } catch (Exception e) {

        }
    }

    // Método para mostrar información
    private static void mostrarInformacionSistema() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("=== INFORMACIÓN DEL SISTEMA ===");
        System.out.println("Sistema de Gestión Veterinaria Happy Feet");
        System.out.println("Versión: 1.0");
        System.out.println("Desarrollado para gestión integral de clínicas veterinarias");
        System.out.println("=".repeat(60));
    }
    }
