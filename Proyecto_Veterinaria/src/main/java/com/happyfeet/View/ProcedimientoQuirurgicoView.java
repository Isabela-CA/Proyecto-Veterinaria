package com.happyfeet.View;

import com.happyfeet.Controller.ProcedimientoQuirurgicoController;
import com.happyfeet.Service.ProcedimientoQuirurgicoService;
import com.happyfeet.model.entities.ProcedimientoQuirurgico;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ProcedimientoQuirurgicoView {
    private ProcedimientoQuirurgicoController procedimientoController;
    private Scanner scanner = new Scanner(System.in);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public ProcedimientoQuirurgicoView(ProcedimientoQuirurgicoController procedimientoController) {
        this.procedimientoController = procedimientoController;
    }

    /**
     * Menú principal de gestión de procedimientos quirúrgicos con ciclo continuo
     */
    public void mostrarMenuProcedimientos() {
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n" + "=".repeat(60));
            System.out.println("=== GESTIÓN DE PROCEDIMIENTOS QUIRÚRGICOS ===");
            System.out.println("=".repeat(60));
            System.out.println("1. Registrar Procedimiento Quirúrgico");
            System.out.println("2. Listar Todos los Procedimientos");
            System.out.println("3. Buscar Procedimiento por ID");
            System.out.println("4. Consultar Procedimientos por Mascota");
            System.out.println("5. Consultar Procedimientos por Veterinario");
            System.out.println("6. Actualizar Seguimiento Postoperatorio");
            System.out.println("7. Modificar Procedimiento");
            System.out.println("8. Generar Reporte por Período");
            System.out.println("9. Ver Tipos de Procedimiento");
            System.out.println("0. Volver al Menú Principal");
            System.out.println("=".repeat(60));
            System.out.print("Seleccione una opción: ");

            try {
                int opcion = scanner.nextInt();
                scanner.nextLine(); // limpiar buffer

                switch (opcion) {
                    case 1 -> {
                        registrarProcedimiento();
                        pausarYContinuar();
                    }
                    case 2 -> {
                        listarTodosLosProcedimientos();
                        pausarYContinuar();
                    }
                    case 3 -> {
                        buscarProcedimientoPorId();
                        pausarYContinuar();
                    }
                    case 4 -> {
                        consultarProcedimientosPorMascota();
                        pausarYContinuar();
                    }
                    case 5 -> {
                        consultarProcedimientosPorVeterinario();
                        pausarYContinuar();
                    }
                    case 6 -> {
                        actualizarSeguimientoPostoperatorio();
                        pausarYContinuar();
                    }
                    case 7 -> {
                        modificarProcedimiento();
                        pausarYContinuar();
                    }
                    case 8 -> {
                        generarReportePorPeriodo();
                        pausarYContinuar();
                    }
                    case 9 -> {
                        mostrarTiposProcedimiento();
                        pausarYContinuar();
                    }
                    case 0 -> {
                        System.out.println("✓ Regresando al menú principal...");
                        continuar = false;
                    }
                    default -> {
                        System.out.println(" Opción inválida. Por favor seleccione entre 0-9.");
                        pausarYContinuar();
                    }
                }
            } catch (Exception e) {
                System.out.println(" Error: Ingrese un número válido");
                scanner.nextLine(); // limpiar buffer en caso de error
                pausarYContinuar();
            }
        }
    }

    /**
     * OPCIÓN 1: Registrar un nuevo procedimiento quirúrgico
     */
    private void registrarProcedimiento() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("=== REGISTRAR PROCEDIMIENTO QUIRÚRGICO ===");
        System.out.println("=".repeat(60));

        try {
            System.out.print("Ingrese ID de la mascota: ");
            int mascotaId = scanner.nextInt();

            if (mascotaId <= 0) {
                System.out.println(" El ID de la mascota debe ser un número positivo.");
                return;
            }

            System.out.print("Ingrese ID del veterinario: ");
            int veterinarioId = scanner.nextInt();
            scanner.nextLine();

            if (veterinarioId <= 0) {
                System.out.println(" El ID del veterinario debe ser un número positivo.");
                return;
            }

            System.out.print("Ingrese ID de la cita (opcional, 0 para omitir): ");
            int citaIdInput = scanner.nextInt();
            Integer citaId = citaIdInput > 0 ? citaIdInput : null;
            scanner.nextLine();

            System.out.print("Ingrese fecha y hora (yyyy-MM-dd HH:mm) o presione Enter para ahora: ");
            String fechaStr = scanner.nextLine().trim();
            LocalDateTime fechaProcedimiento;

            if (fechaStr.isEmpty()) {
                fechaProcedimiento = LocalDateTime.now();
                System.out.println("✓ Usando fecha y hora actual: " + fechaProcedimiento.format(formatter));
            } else {
                try {
                    fechaProcedimiento = LocalDateTime.parse(fechaStr, formatter);
                } catch (DateTimeParseException e) {
                    System.out.println(" Formato de fecha inválido. Use yyyy-MM-dd HH:mm");
                    return;
                }
            }

            // Mostrar tipos disponibles
            System.out.println("\n--- Tipos de Procedimiento Disponibles ---");
            mostrarTiposProcedimiento();

            System.out.print("Ingrese tipo de procedimiento: ");
            String tipoProcedimiento = scanner.nextLine().trim();

            if (tipoProcedimiento.isEmpty()) {
                System.out.println(" El tipo de procedimiento es obligatorio.");
                return;
            }

            System.out.print("Ingrese diagnóstico: ");
            String diagnostico = scanner.nextLine().trim();

            if (diagnostico.isEmpty()) {
                System.out.println(" El diagnóstico es obligatorio.");
                return;
            }

            System.out.print("Ingrese descripción del procedimiento: ");
            String descripcion = scanner.nextLine().trim();

            if (descripcion.isEmpty()) {
                System.out.println(" La descripción es obligatoria.");
                return;
            }

            System.out.print("Ingrese anestesia utilizada: ");
            String anestesia = scanner.nextLine().trim();

            System.out.print("Ingrese medicación prescrita: ");
            String medicacion = scanner.nextLine().trim();

            System.out.print("Ingrese cuidados recomendados: ");
            String cuidados = scanner.nextLine().trim();

            System.out.print("Ingrese resultado inicial (Enter para 'En recuperación'): ");
            String resultado = scanner.nextLine().trim();
            if (resultado.isEmpty()) {
                resultado = ProcedimientoQuirurgicoService.RESULTADO_EN_RECUPERACION;
            }

            // Confirmar datos
            System.out.println("\n--- Confirmación de Datos ---");
            System.out.println("Mascota ID: " + mascotaId);
            System.out.println("Veterinario ID: " + veterinarioId);
            System.out.println("Tipo: " + tipoProcedimiento);
            System.out.println("Fecha: " + fechaProcedimiento.format(formatter));
            System.out.print("\n¿Confirma el registro? (S/N): ");
            String confirmacion = scanner.nextLine().trim().toUpperCase();

            if (confirmacion.equals("S") || confirmacion.equals("SI")) {
                boolean exito = procedimientoController.registrarProcedimiento(
                        mascotaId, veterinarioId, citaId, fechaProcedimiento,
                        tipoProcedimiento, diagnostico, descripcion,
                        anestesia, medicacion, cuidados, resultado
                );

                if (exito) {
                    System.out.println("\n Procedimiento quirúrgico registrado exitosamente");
                } else {
                    System.out.println("\n Error al registrar el procedimiento quirúrgico");
                }
            } else {
                System.out.println("  Registro cancelado.");
            }

        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
            scanner.nextLine();
        }
    }

    /**
     * OPCIÓN 2: Listar todos los procedimientos quirúrgicos
     */
    private void listarTodosLosProcedimientos() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("=== TODOS LOS PROCEDIMIENTOS QUIRÚRGICOS ===");
        System.out.println("=".repeat(80));

        List<ProcedimientoQuirurgico> procedimientos = procedimientoController.listarTodosLosProcedimientos();

        if (procedimientos.isEmpty()) {
            System.out.println("No hay procedimientos quirúrgicos registrados.");
        } else {
            System.out.println("Total de procedimientos: " + procedimientos.size());
            System.out.println();

            for (ProcedimientoQuirurgico proc : procedimientos) {
                mostrarResumenProcedimiento(proc);
                System.out.println("-".repeat(80));
            }
        }
    }

    /**
     * OPCIÓN 3: Buscar procedimiento por ID
     */
    private void buscarProcedimientoPorId() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("=== BUSCAR PROCEDIMIENTO POR ID ===");
        System.out.println("=".repeat(60));

        try {
            System.out.print("Ingrese ID del procedimiento: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            if (id <= 0) {
                System.out.println(" El ID debe ser un número positivo.");
                return;
            }

            ProcedimientoQuirurgico procedimiento = procedimientoController.buscarProcedimientoPorId(id);

            if (procedimiento != null) {
                mostrarDetalleProcedimiento(procedimiento);
            } else {
                System.out.println("️  No se encontró procedimiento con ID: " + id);
            }
        } catch (Exception e) {
            System.out.println("Error: Ingrese un ID válido");
            scanner.nextLine();
        }
    }

    /**
     * OPCIÓN 4: Consultar procedimientos por mascota
     */
    private void consultarProcedimientosPorMascota() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("=== CONSULTAR PROCEDIMIENTOS POR MASCOTA ===");
        System.out.println("=".repeat(60));

        try {
            System.out.print("Ingrese ID de la mascota: ");
            int mascotaId = scanner.nextInt();
            scanner.nextLine();

            if (mascotaId <= 0) {
                System.out.println(" El ID debe ser un número positivo.");
                return;
            }

            List<ProcedimientoQuirurgico> procedimientos = procedimientoController.consultarProcedimientosPorMascota(mascotaId);

            if (procedimientos.isEmpty()) {
                System.out.println("  La mascota con ID " + mascotaId + " no tiene procedimientos quirúrgicos registrados.");
            } else {
                System.out.println("\n--- Procedimientos Quirúrgicos de la Mascota " + mascotaId + " ---");
                System.out.println("Total: " + procedimientos.size() + " procedimientos");
                System.out.println();

                for (ProcedimientoQuirurgico proc : procedimientos) {
                    mostrarResumenProcedimiento(proc);
                    System.out.println("-".repeat(60));
                }
            }
        } catch (Exception e) {
            System.out.println(" Error: Ingrese un ID válido");
            scanner.nextLine();
        }
    }

    /**
     * OPCIÓN 5: Consultar procedimientos por veterinario
     */
    private void consultarProcedimientosPorVeterinario() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("=== CONSULTAR PROCEDIMIENTOS POR VETERINARIO ===");
        System.out.println("=".repeat(60));

        try {
            System.out.print("Ingrese ID del veterinario: ");
            int veterinarioId = scanner.nextInt();
            scanner.nextLine();

            if (veterinarioId <= 0) {
                System.out.println(" El ID debe ser un número positivo.");
                return;
            }

            List<ProcedimientoQuirurgico> procedimientos = procedimientoController.consultarProcedimientosPorVeterinario(veterinarioId);

            if (procedimientos.isEmpty()) {
                System.out.println("  El veterinario con ID " + veterinarioId + " no tiene procedimientos quirúrgicos registrados.");
            } else {
                System.out.println("\n--- Procedimientos Quirúrgicos del Veterinario " + veterinarioId + " ---");
                System.out.println("Total: " + procedimientos.size() + " procedimientos");
                System.out.println();

                for (ProcedimientoQuirurgico proc : procedimientos) {
                    mostrarResumenProcedimiento(proc);
                    System.out.println("-".repeat(60));
                }
            }
        } catch (Exception e) {
            System.out.println(" Error: Ingrese un ID válido");
            scanner.nextLine();
        }
    }

    /**
     * OPCIÓN 6: Actualizar seguimiento postoperatorio
     */
    private void actualizarSeguimientoPostoperatorio() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("=== ACTUALIZAR SEGUIMIENTO POSTOPERATORIO ===");
        System.out.println("=".repeat(60));

        try {
            System.out.print("Ingrese ID del procedimiento: ");
            int procedimientoId = scanner.nextInt();
            scanner.nextLine();

            if (procedimientoId <= 0) {
                System.out.println(" El ID debe ser un número positivo.");
                return;
            }

            ProcedimientoQuirurgico proc = procedimientoController.buscarProcedimientoPorId(procedimientoId);
            if (proc == null) {
                System.out.println(" No se encontró el procedimiento con ID: " + procedimientoId);
                return;
            }

            System.out.println("\n--- Procedimiento Actual ---");
            mostrarDetalleProcedimiento(proc);

            System.out.println("\n--- Actualizar Seguimiento ---");
            System.out.println("Estados disponibles:");
            System.out.println("1. " + ProcedimientoQuirurgicoService.RESULTADO_EXITOSO);
            System.out.println("2. " + ProcedimientoQuirurgicoService.RESULTADO_COMPLICACIONES);
            System.out.println("3. " + ProcedimientoQuirurgicoService.RESULTADO_EN_RECUPERACION);
            System.out.println("4. " + ProcedimientoQuirurgicoService.RESULTADO_REQUIERE_SEGUIMIENTO);

            System.out.print("\nIngrese nuevo resultado (actual: " + proc.getResultado() + ") [Enter para mantener]: ");
            String nuevoResultado = scanner.nextLine().trim();
            if (nuevoResultado.isEmpty()) {
                nuevoResultado = proc.getResultado();
            }

            System.out.print("Ingrese cuidados actualizados (Enter para mantener): ");
            String cuidadosActualizados = scanner.nextLine().trim();

            System.out.print("Ingrese medicación actualizada (Enter para mantener): ");
            String medicacionActualizada = scanner.nextLine().trim();

            System.out.print("\n¿Confirma la actualización? (S/N): ");
            String confirmacion = scanner.nextLine().trim().toUpperCase();

            if (confirmacion.equals("S") || confirmacion.equals("SI")) {
                boolean exito = procedimientoController.actualizarSeguimiento(
                        procedimientoId, nuevoResultado, cuidadosActualizados, medicacionActualizada
                );

                if (exito) {
                    System.out.println("\n Seguimiento postoperatorio actualizado correctamente");
                } else {
                    System.out.println("\n Error al actualizar el seguimiento");
                }
            } else {
                System.out.println("  Actualización cancelada.");
            }

        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
            scanner.nextLine();
        }
    }

    /**
     * OPCIÓN 7: Modificar procedimiento existente
     */
    private void modificarProcedimiento() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("=== MODIFICAR PROCEDIMIENTO ===");
        System.out.println("=".repeat(60));

        try {
            System.out.print("Ingrese ID del procedimiento a modificar: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            if (id <= 0) {
                System.out.println("El ID debe ser un número positivo.");
                return;
            }

            ProcedimientoQuirurgico procedimiento = procedimientoController.buscarProcedimientoPorId(id);
            if (procedimiento == null) {
                System.out.println("️  No se encontró procedimiento con ID: " + id);
                return;
            }

            System.out.println("\n--- Procedimiento Actual ---");
            mostrarDetalleProcedimiento(procedimiento);

            System.out.println("\n--- Modificar Datos ---");
            System.out.print("Nueva descripción (Enter para mantener): ");
            System.out.println("Actual: " + procedimiento.getDescripcion_procedimiento());
            System.out.print("> ");
            String nuevaDescripcion = scanner.nextLine().trim();
            if (!nuevaDescripcion.isEmpty()) {
                procedimiento.setDescripcion_procedimiento(nuevaDescripcion);
            }

            System.out.print("\nNuevo diagnóstico (Enter para mantener): ");
            System.out.println("Actual: " + procedimiento.getDiagnostico());
            System.out.print("> ");
            String nuevoDiagnostico = scanner.nextLine().trim();
            if (!nuevoDiagnostico.isEmpty()) {
                procedimiento.setDiagnostico(nuevoDiagnostico);
            }

            System.out.print("\nNueva medicación (Enter para mantener): ");
            System.out.println("Actual: " + procedimiento.getMedicacion_prescrita());
            System.out.print("> ");
            String nuevaMedicacion = scanner.nextLine().trim();
            if (!nuevaMedicacion.isEmpty()) {
                procedimiento.setMedicacion_prescrita(nuevaMedicacion);
            }

            System.out.print("\n¿Confirma los cambios? (S/N): ");
            String confirmacion = scanner.nextLine().trim().toUpperCase();

            if (confirmacion.equals("S") || confirmacion.equals("SI")) {
                boolean exito = procedimientoController.modificarProcedimiento(procedimiento);
                if (exito) {
                    System.out.println("\n Procedimiento modificado correctamente");
                } else {
                    System.out.println("\n Error al modificar el procedimiento");
                }
            } else {
                System.out.println("Modificación cancelada.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            scanner.nextLine();
        }
    }

    /**
     * OPCIÓN 8: Generar reporte por período (CORREGIDO)
     */
    private void generarReportePorPeriodo() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("=== GENERAR REPORTE POR PERÍODO ===");
        System.out.println("=".repeat(60));

        try {
            System.out.print("Ingrese fecha de inicio (yyyy-MM-dd HH:mm): ");
            String fechaInicioStr = scanner.nextLine().trim();

            LocalDateTime fechaInicio;
            try {
                fechaInicio = LocalDateTime.parse(fechaInicioStr, formatter);
            } catch (DateTimeParseException e) {
                System.out.println(" Formato de fecha inválido. Use yyyy-MM-dd HH:mm");
                return;
            }

            System.out.print("Ingrese fecha de fin (yyyy-MM-dd HH:mm): ");
            String fechaFinStr = scanner.nextLine().trim();

            LocalDateTime fechaFin;
            try {
                fechaFin = LocalDateTime.parse(fechaFinStr, formatter);
            } catch (DateTimeParseException e) {
                System.out.println(" Formato de fecha inválido. Use yyyy-MM-dd HH:mm");
                return;
            }

            if (fechaInicio.isAfter(fechaFin)) {
                System.out.println(" La fecha de inicio debe ser anterior a la fecha de fin.");
                return;
            }

            System.out.println("\n--- Generando Reporte ---");
            procedimientoController.generarReporte(fechaInicio, fechaFin);

        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
            scanner.nextLine();
        }
    }


    private void mostrarTiposProcedimiento() {
        System.out.println("\n--- Tipos de Procedimiento Disponibles ---");
        String[] tipos = procedimientoController.getTiposProcedimiento();
        for (int i = 0; i < tipos.length; i++) {
            System.out.println((i + 1) + ". " + tipos[i]);
        }
    }


    private void mostrarResumenProcedimiento(ProcedimientoQuirurgico proc) {
        System.out.println("ID: " + proc.getId() + " | Mascota: " + proc.getMascota_id() +
                " | Veterinario: " + proc.getVeterinario_id());
        System.out.println("Fecha: " + proc.getFecha_procedimiento().format(formatter));
        System.out.println("Tipo: " + proc.getTipo_procedimiento());
        System.out.println("Diagnóstico: " + proc.getDiagnostico());
        System.out.println("Estado: " + proc.getResultado());
    }


    private void mostrarDetalleProcedimiento(ProcedimientoQuirurgico proc) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("--- DETALLE DEL PROCEDIMIENTO ---");
        System.out.println("=".repeat(60));
        System.out.println("ID: " + proc.getId());
        System.out.println("Mascota ID: " + proc.getMascota_id());
        System.out.println("Veterinario ID: " + proc.getVeterinario_id());
        if (proc.getCita_id() != null) {
            System.out.println("Cita ID: " + proc.getCita_id());
        }
        System.out.println("Fecha: " + proc.getFecha_procedimiento().format(formatter));
        System.out.println("Tipo: " + proc.getTipo_procedimiento());
        System.out.println("Diagnóstico: " + proc.getDiagnostico());
        System.out.println("Descripción: " + proc.getDescripcion_procedimiento());
        System.out.println("Anestesia: " + proc.getAnestesia_utilizada());
        System.out.println("Medicación: " + proc.getMedicacion_prescrita());
        System.out.println("Cuidados: " + proc.getCuidados_recomendados());
        System.out.println("Resultado: " + proc.getResultado());
        System.out.println("=".repeat(60));
    }


    private void pausarYContinuar() {
        System.out.println("\nPresione Enter para continuar...");
        try {
            scanner.nextLine();
        } catch (Exception e) {
        }
    }
}