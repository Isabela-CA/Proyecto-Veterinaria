package com.happyfeet.View;

import com.happyfeet.Controller.ProcedimientoEspecialController;
import com.happyfeet.Service.ProcedimientoEspecialService;
import com.happyfeet.model.entities.ProcedimientoEspecial;
import com.happyfeet.model.enums.EstadoCita;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ProcedimientoEspecialView {

    private final ProcedimientoEspecialController procedimientoController;
    private final Scanner scanner = new Scanner(System.in);
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ProcedimientoEspecialView(ProcedimientoEspecialController procedimientoController) {
        this.procedimientoController = procedimientoController;
    }

    public void mostrarMenu() {
        System.out.println("""
                ╔════════════════════════════════════════╗
                ║  GESTIÓN DE PROCEDIMIENTOS ESPECIALES  ║
                ╠════════════════════════════════════════╣
                ║ 1. Registrar Procedimiento             ║
                ║ 2. Listar Todos los Procedimientos     ║
                ║ 3. Buscar Procedimiento por ID         ║
                ║ 4. Consultar por Mascota               ║
                ║ 5. Consultar por Veterinario           ║
                ║ 6. Consultar por Estado                ║
                ║ 7. Actualizar Procedimiento            ║
                ║ 8. Actualizar Seguimiento              ║
                ║ 9. Cambiar Estado                      ║
                ║ 10. Generar Resumen                    ║
                ║ 0. Volver al menú principal            ║
                ╚════════════════════════════════════════╝
                """);

        try {
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> registrarProcedimiento();
                case 2 -> listarTodosProcedimientos();
                case 3 -> buscarProcedimientoPorId();
                case 4 -> consultarPorMascota();
                case 5 -> consultarPorVeterinario();
                case 6 -> consultarPorEstado();
                case 7 -> actualizarProcedimiento();
                case 8 -> actualizarSeguimiento();
                case 9 -> cambiarEstado();
                case 10 -> generarResumen();
                case 0 -> System.out.println("Volviendo...");
                default -> System.out.println("Opción no válida");
            }
        } catch (Exception e) {
            System.out.println("Error: Ingrese un número válido");
            scanner.nextLine();
        }
    }

    private void registrarProcedimiento() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║           REGISTRAR NUEVO PROCEDIMIENTO ESPECIAL             ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");

        try {
            ProcedimientoEspecial procedimiento = new ProcedimientoEspecial();

            // Paso 1: IDs
            System.out.println("┌─── PASO 1: Identificación ───────────────────────────────────┐");
            System.out.print("│ ID de la mascota: ");
            procedimiento.setMascota_id(scanner.nextInt());

            System.out.print("│ ID del veterinario: ");
            procedimiento.setVeterinario_id(scanner.nextInt());
            scanner.nextLine();
            System.out.println("└───────────────────────────────────────────────────────────────┘\n");

            // Paso 2: Tipo y nombre
            System.out.println("┌─── PASO 2: Tipo de Procedimiento ────────────────────────────┐");
            mostrarTiposProcedimiento();
            System.out.print("│ Tipo de procedimiento: ");
            procedimiento.setTipo_procedimiento(scanner.nextLine());

            System.out.print("│ Nombre del procedimiento: ");
            procedimiento.setNombre_procedimiento(scanner.nextLine());
            System.out.println("└───────────────────────────────────────────────────────────────┘\n");

            // Paso 3: Fecha y duración
            System.out.println("┌─── PASO 3: Fecha y Duración ─────────────────────────────────┐");
            System.out.print("│ Fecha y hora (yyyy-MM-dd HH:mm) o [Enter] para ahora: ");
            String fechaStr = scanner.nextLine().trim();

            if (fechaStr.isEmpty()) {
                procedimiento.setFecha_hora(LocalDateTime.now());
                System.out.println("│ ✓ Usando fecha actual");
            } else {
                try {
                    procedimiento.setFecha_hora(LocalDateTime.parse(fechaStr, dateTimeFormatter));
                } catch (DateTimeParseException e) {
                    System.out.println("│ ⚠ Formato inválido. Usando fecha actual");
                    procedimiento.setFecha_hora(LocalDateTime.now());
                }
            }

            System.out.print("│ Duración estimada en minutos (0 para omitir): ");
            int duracion = scanner.nextInt();
            scanner.nextLine();
            if (duracion > 0) {
                procedimiento.setDuracion_estimada_minutos(duracion);
            }
            System.out.println("└───────────────────────────────────────────────────────────────┘\n");

            // Paso 4: Detalles del procedimiento
            System.out.println("┌─── PASO 4: Detalles del Procedimiento ───────────────────────┐");
            System.out.print("│ Información preoperatoria (opcional): ");
            String infoPreop = scanner.nextLine().trim();
            if (!infoPreop.isEmpty()) {
                procedimiento.setInformacion_preoperatoria(infoPreop);
            }

            System.out.print("│ Detalle del procedimiento (obligatorio): ");
            String detalle = scanner.nextLine().trim();
            if (detalle.isEmpty()) {
                System.out.println("│ ✗ ERROR: El detalle es obligatorio");
                return;
            }
            procedimiento.setDetalle_procedimiento(detalle);
            System.out.println("└───────────────────────────────────────────────────────────────┘\n");

            // Paso 5: Seguimiento
            System.out.println("┌─── PASO 5: Seguimiento (Opcional) ───────────────────────────┐");
            System.out.print("│ Complicaciones: ");
            String complicaciones = scanner.nextLine().trim();
            if (!complicaciones.isEmpty()) {
                procedimiento.setComplicaciones(complicaciones);
            }

            System.out.print("│ Seguimiento postoperatorio: ");
            String seguimiento = scanner.nextLine().trim();
            if (!seguimiento.isEmpty()) {
                procedimiento.setSeguimiento_postoperatorio(seguimiento);
            }

            System.out.print("│ Próximo control (yyyy-MM-dd) o [Enter] para omitir: ");
            String proximoStr = scanner.nextLine().trim();
            if (!proximoStr.isEmpty()) {
                try {
                    procedimiento.setProximo_control(LocalDate.parse(proximoStr, dateFormatter));
                } catch (DateTimeParseException e) {
                    System.out.println("│ ⚠ Formato inválido. Sin fecha de control");
                }
            }
            System.out.println("└───────────────────────────────────────────────────────────────┘\n");

            // Paso 6: Estado y costo
            System.out.println("┌─── PASO 6: Estado y Costo ───────────────────────────────────┐");
            mostrarEstadosValidos();
            System.out.print("│ Estado (Enter para 'Programado'): ");
            int estado = scanner.nextInt();



            if (!(estado > 0)) {
                procedimiento.setEstado(EstadoCita.Estado.valueOf(String.valueOf(estado)));
            }

            System.out.print("│ Costo del procedimiento: $");
            procedimiento.setCosto_procedimiento(scanner.nextDouble());
            scanner.nextLine();
            System.out.println("└───────────────────────────────────────────────────────────────┘\n");

            // Confirmación
            System.out.println("┌─── RESUMEN ───────────────────────────────────────────────────┐");
            System.out.println("│ Mascota ID: " + procedimiento.getMascota_id());
            System.out.println("│ Veterinario ID: " + procedimiento.getVeterinario_id());
            System.out.println("│ Tipo: " + procedimiento.getTipo_procedimiento());
            System.out.println("│ Nombre: " + procedimiento.getNombre_procedimiento());
            System.out.println("│ Fecha: " + procedimiento.getFecha_hora().format(dateTimeFormatter));
            System.out.println("│ Costo: $" + procedimiento.getCosto_procedimiento());
            System.out.println("└───────────────────────────────────────────────────────────────┘");

            System.out.print("\n¿Confirmar registro? (S/N): ");
            String confirmacion = scanner.nextLine().trim().toUpperCase();

            if (confirmacion.equals("S") || confirmacion.equals("SI")) {
                if (procedimientoController.agregarProcedimiento(procedimiento)) {
                    System.out.println("\n✓ Procedimiento registrado exitosamente\n");
                } else {
                    System.out.println("\n✗ Error al registrar el procedimiento\n");
                }
            } else {
                System.out.println("\n⊘ Registro cancelado\n");
            }

        } catch (Exception e) {
            System.out.println("\n✗ Error: " + e.getMessage());
            scanner.nextLine();
        }
    }

    private void listarTodosProcedimientos() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║            TODOS LOS PROCEDIMIENTOS ESPECIALES                  ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════==╝\n");

        List<ProcedimientoEspecial> procedimientos = procedimientoController.listarTodosProcedimientos();

        if (procedimientos.isEmpty()) {
            System.out.println("⊘ No hay procedimientos registrados\n");
        } else {
            System.out.println("Total: " + procedimientos.size() + " procedimientos\n");

            for (ProcedimientoEspecial proc : procedimientos) {
                mostrarResumenProcedimiento(proc);
                System.out.println("─".repeat(65));
            }
        }
    }

    private void buscarProcedimientoPorId() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║              BUSCAR PROCEDIMIENTO POR ID                      ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");

        try {
            System.out.print("ID del procedimiento: ");
            int id = scanner.nextInt();

            ProcedimientoEspecial procedimiento = procedimientoController.obtenerProcedimiento(id);
            if (procedimiento != null) {
                System.out.println("\n✓ Procedimiento encontrado:\n");
                mostrarDetalleProcedimiento(procedimiento);
            } else {
                System.out.println("\n✗ No se encontró procedimiento con ID: " + id);
            }
        } catch (Exception e) {
            System.out.println("✗ Error: Ingrese un ID válido");
            scanner.nextLine();
        }
    }

    private void consultarPorMascota() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║          CONSULTAR PROCEDIMIENTOS POR MASCOTA                 ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");

        try {
            System.out.print("ID de la mascota: ");
            int mascotaId = scanner.nextInt();

            List<ProcedimientoEspecial> procedimientos =
                    procedimientoController.listarProcedimientosPorMascota(mascotaId);

            if (procedimientos.isEmpty()) {
                System.out.println("\n⊘ No hay procedimientos para la mascota ID: " + mascotaId);
            } else {
                System.out.println("\nTotal: " + procedimientos.size() + " procedimientos\n");
                procedimientos.forEach(proc -> {
                    mostrarResumenProcedimiento(proc);
                    System.out.println("─".repeat(65));
                });
            }
        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
            scanner.nextLine();
        }
    }

    private void consultarPorVeterinario() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║        CONSULTAR PROCEDIMIENTOS POR VETERINARIO               ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");

        try {
            System.out.print("ID del veterinario: ");
            int veterinarioId = scanner.nextInt();

            List<ProcedimientoEspecial> procedimientos =
                    procedimientoController.listarProcedimientosPorVeterinario(veterinarioId);

            if (procedimientos.isEmpty()) {
                System.out.println("\n⊘ No hay procedimientos para el veterinario ID: " + veterinarioId);
            } else {
                System.out.println("\nTotal: " + procedimientos.size() + " procedimientos\n");
                procedimientos.forEach(proc -> {
                    mostrarResumenProcedimiento(proc);
                    System.out.println("─".repeat(65));
                });
            }
        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
            scanner.nextLine();
        }
    }

    private void consultarPorEstado() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║          CONSULTAR PROCEDIMIENTOS POR ESTADO                  ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");

        try {
            mostrarEstadosValidos();
            System.out.print("Estado a consultar: ");
            scanner.nextLine();
            String estado = scanner.nextLine();

            List<ProcedimientoEspecial> procedimientos =
                    procedimientoController.listarProcedimientosPorEstado(estado);

            if (procedimientos.isEmpty()) {
                System.out.println("\n⊘ No hay procedimientos con estado: " + estado);
            } else {
                System.out.println("\nTotal: " + procedimientos.size() + " procedimientos\n");
                procedimientos.forEach(proc -> {
                    mostrarResumenProcedimiento(proc);
                    System.out.println("─".repeat(65));
                });
            }
        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    private void actualizarProcedimiento() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║              ACTUALIZAR PROCEDIMIENTO                         ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");

        try {
            System.out.print("ID del procedimiento a actualizar: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            ProcedimientoEspecial procedimiento = procedimientoController.obtenerProcedimiento(id);
            if (procedimiento == null) {
                System.out.println("\n✗ Procedimiento no encontrado");
                return;
            }

            System.out.println("\nProcedimiento actual:");
            mostrarDetalleProcedimiento(procedimiento);

            System.out.println("\n┌─── ACTUALIZAR (Enter para mantener) ─────────────────────────┐");

            System.out.println("│ Detalle actual: " + procedimiento.getDetalle_procedimiento());
            System.out.print("│ Nuevo detalle: ");
            String nuevoDetalle = scanner.nextLine();
            if (!nuevoDetalle.trim().isEmpty()) {
                procedimiento.setDetalle_procedimiento(nuevoDetalle);
            }

            System.out.println("│ Seguimiento actual: " + procedimiento.getSeguimiento_postoperatorio());
            System.out.print("│ Nuevo seguimiento: ");
            String nuevoSeguimiento = scanner.nextLine();
            if (!nuevoSeguimiento.trim().isEmpty()) {
                procedimiento.setSeguimiento_postoperatorio(nuevoSeguimiento);
            }

            System.out.print("│ Costo actual: $" + procedimiento.getCosto_procedimiento());
            System.out.print("\n│ Nuevo costo (0 para mantener): ");
            double nuevoCosto = scanner.nextDouble();
            if (nuevoCosto > 0) {
                procedimiento.setCosto_procedimiento(nuevoCosto);
            }
            System.out.println("└───────────────────────────────────────────────────────────────┘");

            if (procedimientoController.actualizarProcedimiento(procedimiento)) {
                System.out.println("\n✓ Procedimiento actualizado correctamente");
            } else {
                System.out.println("\n✗ Error al actualizar el procedimiento");
            }

        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
            scanner.nextLine();
        }
    }

    private void actualizarSeguimiento() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║           ACTUALIZAR SEGUIMIENTO POSTOPERATORIO               ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");

        try {
            System.out.print("ID del procedimiento: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            ProcedimientoEspecial proc = procedimientoController.obtenerProcedimiento(id);
            if (proc == null) {
                System.out.println("\n✗ Procedimiento no encontrado");
                return;
            }

            System.out.print("Seguimiento postoperatorio: ");
            String seguimiento = scanner.nextLine();

            System.out.print("Complicaciones (opcional): ");
            String complicaciones = scanner.nextLine();

            System.out.print("Fecha próximo control (yyyy-MM-dd) o Enter: ");
            String fechaStr = scanner.nextLine().trim();
            LocalDate proximoControl = null;
            if (!fechaStr.isEmpty()) {
                try {
                    proximoControl = LocalDate.parse(fechaStr, dateFormatter);
                } catch (DateTimeParseException e) {
                    System.out.println("⚠ Formato inválido. Sin fecha");
                }
            }

            if (procedimientoController.actualizarSeguimiento(id, seguimiento, complicaciones, proximoControl)) {
                System.out.println("\n✓ Seguimiento actualizado correctamente");
            } else {
                System.out.println("\n✗ Error al actualizar seguimiento");
            }

        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
            scanner.nextLine();
        }
    }

    private void cambiarEstado() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                    CAMBIAR ESTADO                             ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");

        try {
            System.out.print("ID del procedimiento: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            ProcedimientoEspecial proc = procedimientoController.obtenerProcedimiento(id);
            if (proc == null) {
                System.out.println("\n✗ Procedimiento no encontrado");
                return;
            }

            System.out.println("Estado actual: " + proc.getEstado());
            mostrarEstadosValidos();
            System.out.print("Nuevo estado: ");
            String nuevoEstado = scanner.nextLine();

            if (procedimientoController.cambiarEstado(id, nuevoEstado)) {
                System.out.println("\n✓ Estado actualizado correctamente");
            } else {
                System.out.println("\n✗ Error al actualizar estado");
            }

        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
            scanner.nextLine();
        }
    }

    private void generarResumen() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                  GENERAR RESUMEN ESTADÍSTICO                  ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");

        try {
            System.out.print("Fecha inicio (yyyy-MM-dd): ");
            LocalDate fechaInicio = LocalDate.parse(scanner.nextLine(), dateFormatter);

            System.out.print("Fecha fin (yyyy-MM-dd): ");
            LocalDate fechaFin = LocalDate.parse(scanner.nextLine(), dateFormatter);

            ProcedimientoEspecialService.ResumenProcedimientos resumen =
                    procedimientoController.generarResumen(fechaInicio, fechaFin);

            if (resumen != null) {
                System.out.println("\n┌─── RESUMEN ───────────────────────────────────────────────────┐");
                System.out.println("│ Período: " + fechaInicio + " a " + fechaFin);
                System.out.println("│ " + resumen.toString());
                System.out.println("└───────────────────────────────────────────────────────────────┘\n");
            } else {
                System.out.println("\n✗ Error al generar resumen");
            }

        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
            scanner.nextLine();
        }
    }

    // Métodos auxiliares de visualización

    private void mostrarResumenProcedimiento(ProcedimientoEspecial proc) {
        System.out.printf("ID: %-4d | Mascota: %-4d | Veterinario: %-4d%n",
                proc.getId(), proc.getMascota_id(), proc.getVeterinario_id());
        System.out.printf("Tipo: %-20s | Estado: %s%n",
                proc.getTipo_procedimiento(), proc.getEstado());
        System.out.printf("Fecha: %-16s | Costo: $%.2f%n",
                proc.getFecha_hora().format(dateTimeFormatter), proc.getCosto_procedimiento());
        System.out.println("Nombre: " + proc.getNombre_procedimiento());
    }

    private void mostrarDetalleProcedimiento(ProcedimientoEspecial proc) {
        System.out.println("┌─────────────────────────────────────────────────────────────────┐");
        System.out.printf("│ ID: %-60d │%n", proc.getId());
        System.out.printf("│ Mascota ID: %-52d │%n", proc.getMascota_id());
        System.out.printf("│ Veterinario ID: %-48d │%n", proc.getVeterinario_id());
        System.out.printf("│ Tipo: %-58s │%n", proc.getTipo_procedimiento());
        System.out.printf("│ Nombre: %-56s │%n", truncar(proc.getNombre_procedimiento(), 56));
        System.out.printf("│ Fecha: %-57s │%n", proc.getFecha_hora().format(dateTimeFormatter));
        System.out.printf("│ Duración: %-40s min │%n",
                proc.getDuracion_estimada_minutos() != null ? proc.getDuracion_estimada_minutos() : "N/A");
        System.out.printf("│ Estado: %-56s │%n", proc.getEstado());
        System.out.printf("│ Costo: $%-55.2f │%n", proc.getCosto_procedimiento());
        System.out.println("├─────────────────────────────────────────────────────────────────┤");
        System.out.printf("│ Detalle: %-55s │%n", truncar(proc.getDetalle_procedimiento(), 55));
        if (proc.getInformacion_preoperatoria() != null) {
            System.out.printf("│ Info Preop: %-52s │%n", truncar(proc.getInformacion_preoperatoria(), 52));
        }
        if (proc.getComplicaciones() != null) {
            System.out.printf("│ Complicaciones: %-48s │%n", truncar(proc.getComplicaciones(), 48));
        }
        if (proc.getSeguimiento_postoperatorio() != null) {
            System.out.printf("│ Seguimiento: %-51s │%n", truncar(proc.getSeguimiento_postoperatorio(), 51));
        }
        if (proc.getProximo_control() != null) {
            System.out.printf("│ Próximo Control: %-47s │%n", proc.getProximo_control());
        }
        System.out.println("└─────────────────────────────────────────────────────────────────┘");
    }

    private void mostrarTiposProcedimiento() {
        String[] tipos = procedimientoController.getTiposProcedimiento();
        System.out.println("│ Tipos disponibles:");
        for (int i = 0; i < tipos.length; i++) {
            System.out.printf("│   %d. %s%n", i + 1, tipos[i]);
        }
    }

    private void mostrarEstadosValidos() {
        String[] estados = procedimientoController.getEstadosValidos();
        System.out.println("│ Estados válidos:");
        for (String estado : estados) {
            System.out.println("│   - " + estado);
        }
    }

    private String truncar(String texto, int maxLength) {
        if (texto == null) return "N/A";
        if (texto.length() <= maxLength) return texto;
        return texto.substring(0, maxLength - 3) + "...";
    }
}