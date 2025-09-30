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

    public void mostrarMenuProcedimientos() {
        System.out.println("=== Gestión de Procedimientos Quirúrgicos ===");
        System.out.println("1. Registrar Procedimiento Quirúrgico");
        System.out.println("2. Listar Todos los Procedimientos");
        System.out.println("3. Buscar Procedimiento por ID");
        System.out.println("4. Consultar Procedimientos por Mascota");
        System.out.println("5. Consultar Procedimientos por Veterinario");
        System.out.println("6. Actualizar Seguimiento Postoperatorio");
        System.out.println("7. Modificar Procedimiento");
        System.out.println("8. Generar Reporte por Período");
        System.out.println("9. Ver Tipos de Procedimiento");
        System.out.println("0. Salir");

        try {
            int opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1 -> registrarProcedimiento();
                case 2 -> listarTodosLosProcedimientos();
                case 3 -> buscarProcedimientoPorId();
                case 4 -> consultarProcedimientosPorMascota();
                case 5 -> consultarProcedimientosPorVeterinario();
                case 6 -> actualizarSeguimientoPostoperatorio();
                case 7 -> modificarProcedimiento();
                case 8 -> mostrarTiposProcedimiento();
                default -> System.out.println("Saliendo...");
            }
        } catch (Exception e) {
            System.out.println("Error: Ingrese un número válido");
            scanner.nextLine(); // limpiar buffer en caso de error
        }
    }

    private void registrarProcedimiento() {
        System.out.println("=== Registrar Procedimiento Quirúrgico ===");

        try {
            System.out.println("Ingrese ID de la mascota: ");
            int mascotaId = scanner.nextInt();

            System.out.println("Ingrese ID del veterinario: ");
            int veterinarioId = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Ingrese ID de la cita (opcional, 0 para omitir): ");
            int citaIdInput = scanner.nextInt();
            Integer citaId = citaIdInput > 0 ? citaIdInput : null;
            scanner.nextLine();

            System.out.println("Ingrese fecha y hora del procedimiento (yyyy-MM-dd HH:mm) o presione Enter para ahora: ");
            String fechaStr = scanner.nextLine();
            LocalDateTime fechaProcedimiento = fechaStr.trim().isEmpty() ?
                    LocalDateTime.now() : LocalDateTime.parse(fechaStr, formatter);

            // Mostrar tipos disponibles
            mostrarTiposProcedimiento();
            System.out.println("Ingrese tipo de procedimiento: ");
            String tipoProcedimiento = scanner.nextLine();

            System.out.println("Ingrese diagnóstico: ");
            String diagnostico = scanner.nextLine();

            System.out.println("Ingrese descripción del procedimiento: ");
            String descripcion = scanner.nextLine();

            System.out.println("Ingrese anestesia utilizada: ");
            String anestesia = scanner.nextLine();

            System.out.println("Ingrese medicación prescrita: ");
            String medicacion = scanner.nextLine();

            System.out.println("Ingrese cuidados recomendados: ");
            String cuidados = scanner.nextLine();

            System.out.println("Ingrese resultado inicial (opcional): ");
            String resultado = scanner.nextLine();
            if (resultado.trim().isEmpty()) {
                resultado = ProcedimientoQuirurgicoService.RESULTADO_EN_RECUPERACION;
            }

            boolean exito = procedimientoController.registrarProcedimiento(
                    mascotaId, veterinarioId, citaId, fechaProcedimiento,
                    tipoProcedimiento, diagnostico, descripcion,
                    anestesia, medicacion, cuidados, resultado
            );

            if (exito) {
                System.out.println("✓ Procedimiento quirúrgico registrado exitosamente");
            } else {
                System.out.println("✗ Error al registrar el procedimiento quirúrgico");
            }

        } catch (DateTimeParseException e) {
            System.out.println("Error: Formato de fecha inválido. Use yyyy-MM-dd HH:mm");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listarTodosLosProcedimientos() {
        System.out.println("=== Todos los Procedimientos Quirúrgicos ===");

        List<ProcedimientoQuirurgico> procedimientos = procedimientoController.listarTodosLosProcedimientos();

        if (procedimientos.isEmpty()) {
            System.out.println("No hay procedimientos quirúrgicos registrados.");
        } else {
            System.out.println("Total de procedimientos: " + procedimientos.size());
            System.out.println();

            for (ProcedimientoQuirurgico proc : procedimientos) {
                mostrarResumenProcedimiento(proc);
                System.out.println("─".repeat(80));
            }
        }
    }

    private void buscarProcedimientoPorId() {
        System.out.println("=== Buscar Procedimiento por ID ===");
        System.out.println("Ingrese ID del procedimiento: ");

        try {
            int id = scanner.nextInt();
            ProcedimientoQuirurgico procedimiento = procedimientoController.buscarProcedimientoPorId(id);

            if (procedimiento != null) {
                mostrarDetalleProcedimiento(procedimiento);
            } else {
                System.out.println("No se encontró procedimiento con ID: " + id);
            }
        } catch (Exception e) {
            System.out.println("Error: Ingrese un ID válido");
            scanner.nextLine();
        }
    }

    private void consultarProcedimientosPorMascota() {
        System.out.println("=== Consultar Procedimientos por Mascota ===");
        System.out.println("Ingrese ID de la mascota: ");

        try {
            int mascotaId = scanner.nextInt();
            List<ProcedimientoQuirurgico> procedimientos = procedimientoController.consultarProcedimientosPorMascota(mascotaId);

            if (procedimientos.isEmpty()) {
                System.out.println("La mascota con ID " + mascotaId + " no tiene procedimientos quirúrgicos registrados.");
            } else {
                System.out.println("\n--- Procedimientos Quirúrgicos de la Mascota " + mascotaId + " ---");
                System.out.println("Total: " + procedimientos.size() + " procedimientos");
                System.out.println();

                for (ProcedimientoQuirurgico proc : procedimientos) {
                    mostrarResumenProcedimiento(proc);
                    System.out.println("─".repeat(60));
                }
            }
        } catch (Exception e) {
            System.out.println("Error: Ingrese un ID válido");
            scanner.nextLine();
        }
    }

    private void consultarProcedimientosPorVeterinario() {
        System.out.println("=== Consultar Procedimientos por Veterinario ===");
        System.out.println("Ingrese ID del veterinario: ");

        try {
            int veterinarioId = scanner.nextInt();
            List<ProcedimientoQuirurgico> procedimientos = procedimientoController.consultarProcedimientosPorVeterinario(veterinarioId);

            if (procedimientos.isEmpty()) {
                System.out.println("El veterinario con ID " + veterinarioId + " no tiene procedimientos quirúrgicos registrados.");
            } else {
                System.out.println("\n--- Procedimientos Quirúrgicos del Veterinario " + veterinarioId + " ---");
                System.out.println("Total: " + procedimientos.size() + " procedimientos");
                System.out.println();

                for (ProcedimientoQuirurgico proc : procedimientos) {
                    mostrarResumenProcedimiento(proc);
                    System.out.println("─".repeat(60));
                }
            }
        } catch (Exception e) {
            System.out.println("Error: Ingrese un ID válido");
            scanner.nextLine();
        }
    }

    private void actualizarSeguimientoPostoperatorio() {
        System.out.println("=== Actualizar Seguimiento Postoperatorio ===");

        try {
            System.out.println("Ingrese ID del procedimiento: ");
            int procedimientoId = scanner.nextInt();
            scanner.nextLine();

            // Mostrar procedimiento actual
            ProcedimientoQuirurgico proc = procedimientoController.buscarProcedimientoPorId(procedimientoId);
            if (proc == null) {
                System.out.println("No se encontró el procedimiento con ID: " + procedimientoId);
                return;
            }

            System.out.println("Procedimiento actual:");
            mostrarDetalleProcedimiento(proc);

            System.out.println("\n--- Actualizar Seguimiento ---");
            System.out.println("Estados disponibles:");
            System.out.println("1. " + ProcedimientoQuirurgicoService.RESULTADO_EXITOSO);
            System.out.println("2. " + ProcedimientoQuirurgicoService.RESULTADO_COMPLICACIONES);
            System.out.println("3. " + ProcedimientoQuirurgicoService.RESULTADO_EN_RECUPERACION);
            System.out.println("4. " + ProcedimientoQuirurgicoService.RESULTADO_REQUIERE_SEGUIMIENTO);

            System.out.println("Ingrese nuevo resultado (actual: " + proc.getResultado() + "): ");
            String nuevoResultado = scanner.nextLine();
            if (nuevoResultado.trim().isEmpty()) {
                nuevoResultado = proc.getResultado();
            }

            System.out.println("Ingrese cuidados actualizados (Enter para mantener actual): ");
            String cuidadosActualizados = scanner.nextLine();

            System.out.println("Ingrese medicación actualizada (Enter para mantener actual): ");
            String medicacionActualizada = scanner.nextLine();

            boolean exito = procedimientoController.actualizarSeguimiento(
                    procedimientoId, nuevoResultado, cuidadosActualizados, medicacionActualizada
            );

            if (exito) {
                System.out.println("✓ Seguimiento postoperatorio actualizado correctamente");
            } else {
                System.out.println("✗ Error al actualizar el seguimiento");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void modificarProcedimiento() {
        System.out.println("=== Modificar Procedimiento ===");

        try {
            System.out.println("Ingrese ID del procedimiento a modificar: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            ProcedimientoQuirurgico procedimiento = procedimientoController.buscarProcedimientoPorId(id);
            if (procedimiento == null) {
                System.out.println("No se encontró procedimiento con ID: " + id);
                return;
            }

            System.out.println("Procedimiento actual:");
            mostrarDetalleProcedimiento(procedimiento);

            System.out.println("\n--- Modificar Datos ---");
            System.out.println("Ingrese nueva descripción (actual: " + procedimiento.getDescripcion_procedimiento() + "): ");
            String nuevaDescripcion = scanner.nextLine();
            if (!nuevaDescripcion.trim().isEmpty()) {
                procedimiento.setDescripcion_procedimiento(nuevaDescripcion);
            }

            System.out.println("Ingrese nuevo diagnóstico (actual: " + procedimiento.getDiagnostico() + "): ");
            String nuevoDiagnostico = scanner.nextLine();
            if (!nuevoDiagnostico.trim().isEmpty()) {
                procedimiento.setDiagnostico(nuevoDiagnostico);
            }

            System.out.println("Ingrese nueva medicación (actual: " + procedimiento.getMedicacion_prescrita() + "): ");
            String nuevaMedicacion = scanner.nextLine();
            if (!nuevaMedicacion.trim().isEmpty()) {
                procedimiento.setMedicacion_prescrita(nuevaMedicacion);
            }

            boolean exito = procedimientoController.modificarProcedimiento(procedimiento);
            if (exito) {
                System.out.println("✓ Procedimiento modificado correctamente");
            } else {
                System.out.println("✗ Error al modificar el procedimiento");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void mostrarTiposProcedimiento() {
        System.out.println("\n--- Tipos de Procedimiento Disponibles ---");
        String[] tipos = procedimientoController.getTiposProcedimiento();
        for (int i = 0; i < tipos.length; i++) {
            System.out.println((i + 1) + ". " + tipos[i]);
        }
        System.out.println();
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
        System.out.println("═".repeat(60));
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
        System.out.println("═".repeat(60));
    }
}