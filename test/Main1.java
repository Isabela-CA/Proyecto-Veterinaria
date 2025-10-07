package com.happyfeet.test;

import com.happyfeet.View.*;
import com.happyfeet.Controller.*;
import com.happyfeet.Repository.*;
import com.happyfeet.Repository.Interfaz.*;
import com.happyfeet.Service.*;

import java.util.Scanner;

public class Main1 {

    private static final Scanner input = new Scanner(System.in);

    // DAOs - usando interfaces
    private static final IVeterinarioDAO veterinarioDAO = new VeterinarioDAO();
    private static final IDuenosDAO duenosDAO = new DuenosDAO();
    private static final IMascotaDAO mascotaDAO = new MascotaDAO();
    private static final ICitaDAO citaDAO = new CitaDAO();
    private static final IInventarioDAO inventarioDAO = new InventarioDAO();

    // Services
    private static final VeterinarioService veterinarioService = new VeterinarioService(veterinarioDAO);
    private static final DuenoService duenoService = new DuenoService(duenosDAO);
    private static final MascotaService mascotaService = new MascotaService(mascotaDAO, duenosDAO);
    private static final CitaService citaService = new CitaService(
            (CitaDAO) citaDAO,
            (MascotaDAO) mascotaDAO,
            (VeterinarioDAO) veterinarioDAO
    );
    private static final InventarioService inventarioService = new InventarioService(inventarioDAO);

    // Controladores
    private static final VeterinarioController veterinarioController = new VeterinarioController(veterinarioService);
    private static final DuenosController duenosController = new DuenosController();
    private static final MascotaController mascotaController = new MascotaController();
    private static final CitaController citaController = new CitaController();
    private static final ConsultaMedicaController consultaMedicaController = new ConsultaMedicaController();
    private static final HistorialClinicoController historialController = new HistorialClinicoController();
    private static final ProcedimientoEspecialController procedimientoController =
            new ProcedimientoEspecialController();
    private static final InventarioController inventarioController = new InventarioController(inventarioService);

    // Views
    private static final VeterinarioView veterinarioView = new VeterinarioView(veterinarioController);
    private static final ConsultaMedicaView consultaMedicaView = new ConsultaMedicaView(consultaMedicaController, input);
    private static final DuenoView duenoView = new DuenoView(duenosController);
    private static final MascotaView mascotaView = new MascotaView(mascotaController);
    private static final CitaView citaView = new CitaView(citaController);
    private static final HistorialClinicoView historialView = new HistorialClinicoView(historialController);
    private static final ProcedimientoEspecialView procedimientoView =
            new ProcedimientoEspecialView(procedimientoController);
    private static final InventarioView inventarioView = new InventarioView(inventarioController);

    public static void main(String[] args) {
        System.out.println("""
                ╔══════════════════════════════════════════════════════╗
                ║   SISTEMA DE GESTIÓN VETERINARIA HAPPY FEET        ║
                ╚══════════════════════════════════════════════════════╝
                Bienvenido al sistema de gestión veterinaria
                """);

        boolean continuar = true;

        while (continuar) {
            continuar = mostrarMenuPrincipal();
        }

        System.out.println("¡Gracias por usar el sistema veterinario Happy Feet!");
        input.close();
    }

    static boolean mostrarMenuPrincipal() {
        System.out.println("""
                
                ══════════════════════════════════════════════════════
                ═══ MENÚ PRINCIPAL VETERINARIA HAPPY FEET ═══
                ══════════════════════════════════════════════════════
                1. Gestión de Dueños
                2. Gestión de Mascotas
                3. Gestión de citas y consultas medicas 
                4. Procedimientos Especiales
                5. Gestión veterinario
                6. Inventario y farmacia
                0. Salir del Sistema
                ══════════════════════════════════════════════════════
                """);
        System.out.print("Seleccione una opción: ");

        try {
            int opcion = input.nextInt();
            input.nextLine(); // limpiar buffer

            return switch (opcion) {
                case 1 -> {
                    mostrarSubmenuDuenos();
                    yield true;
                }
                case 2 -> {
                    mostrarSubmenuMascotas();
                    yield true;
                }
                case 3 -> {
                    mostrarSubmenuCitasYConsultas();
                    yield true;
                }
                case 4 -> {
                    mostrarSubmenuProcedimientosEspeciales();
                    yield true;
                }
                case 5 -> {
                    mostrarMenuVeterinario();
                    yield true;
                }
                case 6 -> {
                    mostrarSubmenuFarmaciaInventario();
                    yield true;
                }
                case 0 -> {
                    System.out.println("""
                            
                            ¡Gracias por usar el sistema veterinario Happy Feet!
                            Cerrando sistema...
                            """);
                    yield false;
                }
                default -> {
                    System.out.println("Opción inválida. Por favor seleccione una opción válida (0-6).");
                    yield true;
                }
            };
        } catch (Exception e) {
            System.out.println("Error: Ingrese un número válido");
            input.nextLine(); // limpiar buffer en caso de error
            return true;
        }
    }

    private static void mostrarSubmenuDuenos() {
        boolean volverAlMenuPrincipal = false;

        while (!volverAlMenuPrincipal) {
            System.out.println("""
                    
                    ════════════════════════════════════════
                    ═══ GESTIÓN DE DUEÑOS ═══
                    ════════════════════════════════════════
                    1. Registrar Dueño
                    2. Listar Dueños
                    3. Buscar Dueño por ID
                    4. Actualizar Dueño
                    0. Volver al Menú Principal
                    ════════════════════════════════════════
                    """);
            System.out.print("Seleccione una opción: ");

            try {
                int opcion = input.nextInt();
                input.nextLine(); // limpiar buffer

                volverAlMenuPrincipal = switch (opcion) {
                    case 1 -> {
                        duenoView.registrarDueno();
                        yield false;
                    }
                    case 2 -> {
                        duenoView.listarDuenos();
                        yield false;
                    }
                    case 3 -> {
                        duenoView.buscarDueno();
                        yield false;
                    }
                    case 4 -> {
                        duenoView.actualizarDueno();
                        yield false;
                    }
                    case 0 -> {
                        System.out.println("Regresando al menú principal...");
                        yield true;
                    }
                    default -> {
                        System.out.println("Opción inválida. Intente nuevamente.");
                        yield false;
                    }
                };
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
            System.out.println("""
                    
                    ════════════════════════════════════════
                    ═══ GESTIÓN DE MASCOTAS ═══
                    ════════════════════════════════════════
                    """);

            try {
                mascotaView.mostrarMenuMascotas();
                volverAlMenuPrincipal = true;
            } catch (Exception e) {
                System.out.println("Error en el menú de mascotas: " + e.getMessage());
                pausarYContinuar();
                volverAlMenuPrincipal = true;
            }
        }
    }

    private static boolean mostrarSubmenuCitasYConsultas() {
        System.out.println("""             
                
                ══════════════════════════════════════════════════════
                ═══ Citas y consultas ═══
                ══════════════════════════════════════════════════════
                1. gestion cita
                2. gestion consultas medicas
                0. volver al menu principal
                ══════════════════════════════════════════════════════
                """);
        System.out.print("Seleccione una opción: ");

        try {
            int opcion = input.nextInt();
            input.nextLine(); // limpiar buffer

            return switch (opcion) {
                case 1 -> {
                    mostrarSubmenuCitas();
                    yield true;
                }
                case 2 -> {
                    mostrarSubmenuConsultaMedicaView();
                    yield true;
                }
                case 0 -> {
                    System.out.println("regresando al menu principal..... ");
                    yield true;
                }
                default -> {
                    System.out.println("Opción inválida. Intente nuevamente.");
                    yield true;
                }
            };
        } catch (Exception e) {
            System.out.println("Error: Ingrese un número válido");
            input.nextLine(); // limpiar buffer en caso de error
            return true;
        }
    }

    private static void mostrarSubmenuCitas() {
        boolean volverAlMenuPrincipal = false;

        while (!volverAlMenuPrincipal) {
            System.out.println("""
                
                ════════════════════════════════════════
                ═══ GESTIÓN DE CITAS ═══
                ════════════════════════════════════════
                """);

            try {
                citaView.mostrarMenuCita();
                volverAlMenuPrincipal = true;
            } catch (Exception e) {
                System.out.println("Error en el menú de citas: " + e.getMessage());
                pausarYContinuar();
                volverAlMenuPrincipal = true;
            }
        }
    }

    private static void mostrarSubmenuConsultaMedicaView() {
        boolean volverAlMenuPrincipal = false;

        while (!volverAlMenuPrincipal) {
            System.out.println("""
                
                ════════════════════════════════════════
                ═══ GESTIÓN DE CONSULTA MEDICA ═══
                ════════════════════════════════════════
                """);

            try {
                consultaMedicaView.mostrarMenuConsultaMedica();
                volverAlMenuPrincipal = true;
            } catch (Exception e) {
                System.out.println("Error en el menú de consulta medica: " + e.getMessage());
                pausarYContinuar();
                volverAlMenuPrincipal = true;
            }
        }
    }

    private static boolean mostrarSubmenuProcedimientosEspeciales() {
        System.out.println("""             
                
                ══════════════════════════════════════════════════════
                ═══ PROCEDIMIENTOS ESPECIALES ═══
                ══════════════════════════════════════════════════════
                1. Gestión de Historial Clinico
                2. Gestión de procedimientos Quirurgicos
                0. volver al menu principal
                ══════════════════════════════════════════════════════
                """);
        System.out.print("Seleccione una opción: ");

        try {
            int opcion = input.nextInt();
            input.nextLine(); // limpiar buffer

            return switch (opcion) {
                case 1 -> {
                    mostrarSubmenuHistorialClinico();
                    yield true;
                }
                case 2 -> {
                    mostrarMenuProcedimiento();
                    yield true;
                }
                case 0 -> {
                    System.out.println("regresando al menu principal..... ");
                    yield true;
                }
                default -> {
                    System.out.println("Opción inválida. Intente  nuevamente.");
                    yield true;
                }
            };
        } catch (Exception e) {
            System.out.println("Error: Ingrese un número válido");
            input.nextLine(); // limpiar buffer en caso de error
            return true;
        }
    }

    private static void mostrarSubmenuHistorialClinico() {
        boolean volverAlMenuPrincipal = false;

        while (!volverAlMenuPrincipal) {
            System.out.println("""
                
                ════════════════════════════════════════
                ═══ GESTIÓN DE HISTORIAL CLÍNICO ═══
                ════════════════════════════════════════
                """);

            try {
                historialView.mostrarMenu();
                volverAlMenuPrincipal = true;
            } catch (Exception e) {
                System.out.println("Error en el menú de historial clínico: " + e.getMessage());
                pausarYContinuar();
                volverAlMenuPrincipal = true;
            }
        }
    }

    private static void mostrarMenuProcedimiento() {
        boolean volverAlMenuPrincipal = false;

        while (!volverAlMenuPrincipal) {
            System.out.println("""
                
                ════════════════════════════════════════
                ═══ PROCEDIMIENTOS QUIRÚRGICOS ═══
                ════════════════════════════════════════
                """);

            try {
                procedimientoView.mostrarMenu();
                volverAlMenuPrincipal = true;
            } catch (Exception e) {
                System.out.println("Error en el menú de procedimientos quirúrgicos: " + e.getMessage());
                pausarYContinuar();
                volverAlMenuPrincipal = true;
            }
        }
    }

    private static void mostrarMenuVeterinario() {
        boolean volverAlMenuPrincipal = false;

        while (!volverAlMenuPrincipal) {
            System.out.println("""
                
                ════════════════════════════════════════
                ═══ Gestión veterinario ═══
                ════════════════════════════════════════
                """);

            try {
                veterinarioView.MenuVeterinarios();
                volverAlMenuPrincipal = true;
            } catch (Exception e) {
                System.out.println("Error en el menú de veterinarios: " + e.getMessage());
                pausarYContinuar();
                volverAlMenuPrincipal = true;
            }
        }
    }

    private static void mostrarSubmenuFarmaciaInventario() {
        boolean volverAlMenuPrincipal = false;

        while (!volverAlMenuPrincipal) {
            System.out.println("""
                
                ════════════════════════════════════════
                ═══ GESTIÓN INVENTARIO Y FARMACIA ═══
                ════════════════════════════════════════
                """);

            try {
                inventarioView.mostrarMenufarmaciainventario();
                volverAlMenuPrincipal = true;
            } catch (Exception e) {
                System.out.println("Error en el menú de inventario: " + e.getMessage());
                pausarYContinuar();
                volverAlMenuPrincipal = true;
            }
        }
    }
    
    private static void mostrarSubmenuhistorial() {
        boolean volverAlMenuPrincipal = false;

        while (!volverAlMenuPrincipal) {
            System.out.println("""
                
                ════════════════════════════════════════
                ═══ GESTIÓN HISTORIAL ═══
                ════════════════════════════════════════
                """);

            try {
                historialview.mostrarMenu();
                volverAlMenuPrincipal = true;
            } catch (Exception e) {
                System.out.println("Error en el menú de inventario: " + e.getMessage());
                pausarYContinuar();
                volverAlMenuPrincipal = true;
            }
        }
    }

    private static void pausarYContinuar() {
        System.out.println("\nPresione Enter para continuar...");
        try {
            input.nextLine();
        } catch (Exception ignored) {
            // Ignorar excepción
        }
    }
}