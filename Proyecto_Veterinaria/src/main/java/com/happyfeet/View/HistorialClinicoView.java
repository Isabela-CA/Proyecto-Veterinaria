package com.happyfeet.View;

import com.happyfeet.Controller.HistorialClinicoController;
import com.happyfeet.model.entities.HistorialClinico;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
    public class HistorialClinicoView {
        private HistorialClinicoController historialController;
        private Scanner scanner = new Scanner(System.in);
        private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        public HistorialClinicoView(HistorialClinicoController historialController) {
            this.historialController = historialController;
        }

        /**
         * Menú principal de gestión de historial clínico con ciclo continuo
         */
        public void mostrarMenu() {
            boolean continuar = true;

            while (continuar) {
                System.out.println("\n" + "=".repeat(50));
                System.out.println("=== GESTIÓN DE HISTORIAL CLÍNICO ===");
                System.out.println("=".repeat(50));
                System.out.println("1. Consultar Historial por Mascota");
                System.out.println("2. Agregar Evento Médico");
                System.out.println("3. Registrar Vacunación");
                System.out.println("4. Registrar Consulta General");
                System.out.println("5. Ver Todos los Historiales");
                System.out.println("6. Buscar Historial por ID");
                System.out.println("7. Actualizar Historial");
                System.out.println("0. Volver al Menú Principal");
                System.out.println("=".repeat(50));
                System.out.print("Seleccione una opción: ");

                try {
                    int opcion = scanner.nextInt();
                    scanner.nextLine(); // limpiar buffer

                    switch (opcion) {
                        case 1 -> {
                            consultarHistorialPorMascota();
                            pausarYContinuar();
                        }
                        case 2 -> {
                            agregarEventoMedico();
                            pausarYContinuar();
                        }
                        case 3 -> {
                            registrarVacunacion();
                            pausarYContinuar();
                        }
                        case 4 -> {
                            registrarConsultaGeneral();
                            pausarYContinuar();
                        }
                        case 5 -> {
                            listarTodosLosHistoriales();
                            pausarYContinuar();
                        }
                        case 6 -> {
                            buscarHistorialPorId();
                            pausarYContinuar();
                        }
                        case 7 -> {
                            actualizarHistorial();
                            pausarYContinuar();
                        }
                        case 0 -> {
                            System.out.println("Regresando al menú principal...");
                            continuar = false;
                        }
                        default -> {
                            System.out.println("Opción inválida. Por favor seleccione entre 0-7.");
                            pausarYContinuar();
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error: Ingrese un número válido");
                    scanner.nextLine(); // limpiar buffer en caso de error
                    pausarYContinuar();
                }
            }
        }

        /**
         * OPCIÓN 1: Consultar historial clínico por mascota
         */
        private void consultarHistorialPorMascota() {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("=== CONSULTAR HISTORIAL POR MASCOTA ===");
            System.out.println("=".repeat(50));

            try {
                System.out.print("Ingrese ID de la mascota: ");
                int mascotaId = scanner.nextInt();
                scanner.nextLine();

                if (mascotaId <= 0) {
                    System.out.println("El ID de la mascota debe ser un número positivo.");
                    return;
                }

                List<HistorialClinico> historiales = historialController.consultarHistorialPorMascota(mascotaId);

                if (historiales.isEmpty()) {
                    System.out.println("No se encontró historial para la mascota con ID: " + mascotaId);
                } else {
                    System.out.println("\n--- Historial Clínico de la Mascota " + mascotaId + " ---");
                    System.out.println("Total de eventos médicos: " + historiales.size());
                    System.out.println();

                    for (HistorialClinico historial : historiales) {
                        mostrarDetalleHistorial(historial);
                        System.out.println("-".repeat(80));
                    }
                }
            } catch (Exception e) {
                System.out.println("Error: Ingrese un ID válido");
                scanner.nextLine();
            }
        }

        /**
         * OPCIÓN 2: Agregar evento médico completo
         */
        private void agregarEventoMedico() {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("=== AGREGAR EVENTO MÉDICO ===");
            System.out.println("=".repeat(50));

            try {
                System.out.print("Ingrese ID de la mascota: ");
                int mascotaId = scanner.nextInt();

                if (mascotaId <= 0) {
                    System.out.println("El ID de la mascota debe ser un número positivo.");
                    return;
                }

                System.out.print("Ingrese ID del veterinario: ");
                int veterinarioId = scanner.nextInt();
                scanner.nextLine();

                if (veterinarioId <= 0) {
                    System.out.println("El ID del veterinario debe ser un número positivo.");
                    return;
                }

                System.out.print("Ingrese fecha del evento (yyyy-MM-dd) o presione Enter para hoy: ");
                String fechaStr = scanner.nextLine().trim();
                LocalDate fechaEvento;

                if (fechaStr.isEmpty()) {
                    fechaEvento = LocalDate.now();
                    System.out.println("Usando fecha actual: " + fechaEvento);
                } else {
                    try {
                        fechaEvento = LocalDate.parse(fechaStr, formatter);

                        if (fechaEvento.isAfter(LocalDate.now())) {
                            System.out.println("La fecha del evento no puede ser futura.");
                            return;
                        }
                    } catch (DateTimeParseException e) {
                        System.out.println("Formato de fecha inválido. Use yyyy-MM-dd (ejemplo: 2025-09-28)");
                        return;
                    }
                }

                System.out.println("\nSeleccione tipo de evento:");
                System.out.println("1. Vacunación");
                System.out.println("2. Consulta General");
                System.out.println("3. Cirugía");
                System.out.println("4. Control de Rutina");
                System.out.println("5. Emergencia");
                System.out.println("6. Otro");
                System.out.print("Opción: ");
                int tipoEvento = scanner.nextInt();
                scanner.nextLine();

                if (tipoEvento < 1 || tipoEvento > 6) {
                    System.out.println("Tipo de evento inválido.");
                    return;
                }

                System.out.print("Ingrese descripción del evento: ");
                String descripcion = scanner.nextLine().trim();

                if (descripcion.isEmpty()) {
                    System.out.println("La descripción no puede estar vacía.");
                    return;
                }

                System.out.print("Ingrese diagnóstico: ");
                String diagnostico = scanner.nextLine().trim();

                if (diagnostico.isEmpty()) {
                    System.out.println("El diagnóstico no puede estar vacío.");
                    return;
                }

                System.out.print("Ingrese tratamiento recomendado: ");
                String tratamiento = scanner.nextLine().trim();

                if (tratamiento.isEmpty()) {
                    System.out.println("El tratamiento no puede estar vacío.");
                    return;
                }

                System.out.print("Ingrese ID del producto (opcional, 0 para omitir): ");
                int productoId = scanner.nextInt();

                System.out.print("Ingrese cantidad utilizada (0 si no aplica): ");
                int cantidad = scanner.nextInt();
                scanner.nextLine();

                // Confirmar datos
                System.out.println("\n--- Confirmación de Datos ---");
                System.out.println("Mascota ID: " + mascotaId);
                System.out.println("Veterinario ID: " + veterinarioId);
                System.out.println("Fecha: " + fechaEvento);
                System.out.println("Tipo: " + obtenerNombreTipoEvento(tipoEvento));
                System.out.println("Descripción: " + descripcion);
                System.out.print("\n¿Confirma el registro? (S/N): ");
                String confirmacion = scanner.nextLine().trim().toUpperCase();

                if (confirmacion.equals("S") || confirmacion.equals("SI")) {
                    HistorialClinico historial = new HistorialClinico(
                            mascotaId, veterinarioId, fechaEvento, tipoEvento,
                            descripcion, diagnostico, tratamiento,
                            productoId > 0 ? productoId : null, cantidad
                    );

                    if (historialController.agregarEventoMedico(historial)) {
                        System.out.println("\n¡Evento médico registrado correctamente!");
                    } else {
                        System.out.println("\nError al registrar el evento médico. Verifique los datos.");
                    }
                } else {
                    System.out.println("Registro cancelado.");
                }

            } catch (DateTimeParseException e) {
                System.out.println("Error: Formato de fecha inválido. Use yyyy-MM-dd");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine();
            }
        }

        /**
         * OPCIÓN 3: Registrar vacunación (método simplificado)
         */
        private void registrarVacunacion() {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("=== REGISTRAR VACUNACIÓN ===");
            System.out.println("=".repeat(50));

            try {
                System.out.print("Ingrese ID de la mascota: ");
                int mascotaId = scanner.nextInt();

                if (mascotaId <= 0) {
                    System.out.println("El ID de la mascota debe ser un número positivo.");
                    return;
                }

                System.out.print("Ingrese ID del veterinario: ");
                int veterinarioId = scanner.nextInt();
                scanner.nextLine();

                if (veterinarioId <= 0) {
                    System.out.println("El ID del veterinario debe ser un número positivo.");
                    return;
                }

                System.out.print("Ingrese nombre de la vacuna: ");
                String vacuna = scanner.nextLine().trim();

                if (vacuna.isEmpty()) {
                    System.out.println("El nombre de la vacuna no puede estar vacío.");
                    return;
                }

                System.out.print("Ingrese número de lote: ");
                String lote = scanner.nextLine().trim();

                if (lote.isEmpty()) {
                    System.out.println("El número de lote no puede estar vacío.");
                    return;
                }

                if (historialController.registrarVacunacion(mascotaId, veterinarioId, vacuna, lote)) {
                    System.out.println("\n¡Vacunación registrada correctamente!");
                    System.out.println("Recordatorio: Programar próxima dosis según calendario de vacunación");
                } else {
                    System.out.println("\nError al registrar la vacunación. Verifique los datos.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine();
            }
        }

        /**
         * OPCIÓN 4: Registrar consulta general (método simplificado)
         */
        private void registrarConsultaGeneral() {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("=== REGISTRAR CONSULTA GENERAL ===");
            System.out.println("=".repeat(50));

            try {
                System.out.print("Ingrese ID de la mascota: ");
                int mascotaId = scanner.nextInt();

                if (mascotaId <= 0) {
                    System.out.println("El ID de la mascota debe ser un número positivo.");
                    return;
                }

                System.out.print("Ingrese ID del veterinario: ");
                int veterinarioId = scanner.nextInt();
                scanner.nextLine();

                if (veterinarioId <= 0) {
                    System.out.println("El ID del veterinario debe ser un número positivo.");
                    return;
                }

                System.out.print("Ingrese motivo de la consulta: ");
                String motivo = scanner.nextLine().trim();

                if (motivo.isEmpty()) {
                    System.out.println("El motivo no puede estar vacío.");
                    return;
                }

                System.out.print("Ingrese diagnóstico: ");
                String diagnostico = scanner.nextLine().trim();

                if (diagnostico.isEmpty()) {
                    System.out.println("El diagnóstico no puede estar vacío.");
                    return;
                }

                System.out.print("Ingrese tratamiento recomendado: ");
                String tratamiento = scanner.nextLine().trim();

                if (tratamiento.isEmpty()) {
                    System.out.println("El tratamiento no puede estar vacío.");
                    return;
                }

                if (historialController.registrarConsultaGeneral(mascotaId, veterinarioId, motivo, diagnostico, tratamiento)) {
                    System.out.println("\n¡Consulta registrada correctamente!");
                } else {
                    System.out.println("\nError al registrar la consulta. Verifique los datos.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine();
            }
        }

        /**
         * OPCIÓN 5: Listar todos los historiales
         */
        private void listarTodosLosHistoriales() {
            System.out.println("\n" + "=".repeat(80));
            System.out.println("=== TODOS LOS HISTORIALES CLÍNICOS ===");
            System.out.println("=".repeat(80));

            List<HistorialClinico> historiales = historialController.listarTodosLosHistoriales();

            if (historiales.isEmpty()) {
                System.out.println("No hay historiales médicos registrados");
            } else {
                System.out.println("Total de historiales: " + historiales.size());
                System.out.println();

                // Vista resumida para listas grandes
                if (historiales.size() > 10) {
                    System.out.println("Mostrando vista resumida (más de 10 registros):");
                    System.out.println(String.format("%-5s %-12s %-15s %-20s %-30s",
                            "ID", "Mascota", "Fecha", "Tipo", "Descripción"));
                    System.out.println("-".repeat(80));

                    for (HistorialClinico historial : historiales) {
                        mostrarResumenHistorial(historial);
                    }

                    System.out.println("-".repeat(80));
                    System.out.println("\nUse 'Buscar Historial por ID' para ver detalles completos.");
                } else {
                    for (HistorialClinico historial : historiales) {
                        mostrarDetalleHistorial(historial);
                        System.out.println("-".repeat(80));
                    }
                }
            }
        }

        /**
         * OPCIÓN 6: Buscar historial por ID
         */
        private void buscarHistorialPorId() {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("=== BUSCAR HISTORIAL POR ID ===");
            System.out.println("=".repeat(50));

            try {
                System.out.print("Ingrese ID del historial: ");
                int id = scanner.nextInt();
                scanner.nextLine();

                if (id <= 0) {
                    System.out.println("El ID debe ser un número positivo.");
                    return;
                }

                HistorialClinico historial = historialController.obtenerHistorial(id);

                if (historial != null) {
                    System.out.println("\n--- Historial Encontrado ---");
                    mostrarDetalleHistorial(historial);
                } else {
                    System.out.println("No se encontró historial con ID: " + id);
                }
            } catch (Exception e) {
                System.out.println("Error: Ingrese un ID válido");
                scanner.nextLine();
            }
        }

        /**
         * OPCIÓN 7: Actualizar historial existente
         */
        private void actualizarHistorial() {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("=== ACTUALIZAR HISTORIAL ===");
            System.out.println("=".repeat(50));

            try {
                System.out.print("Ingrese ID del historial a actualizar: ");
                int id = scanner.nextInt();
                scanner.nextLine();

                if (id <= 0) {
                    System.out.println("El ID debe ser un número positivo.");
                    return;
                }

                HistorialClinico historial = historialController.obtenerHistorial(id);

                if (historial == null) {
                    System.out.println("Historial no encontrado con ID: " + id);
                    return;
                }

                System.out.println("\n--- Historial Actual ---");
                mostrarDetalleHistorial(historial);

                System.out.println("\n--- Actualizar Datos ---");
                System.out.println("Ingrese nueva descripción (Enter para mantener actual):");
                System.out.println("Actual: " + historial.getDescripcion());
                System.out.print("> ");
                String nuevaDescripcion = scanner.nextLine().trim();

                if (!nuevaDescripcion.isEmpty()) {
                    historial.setDescripcion(nuevaDescripcion);
                }

                System.out.println("\nIngrese nuevo diagnóstico (Enter para mantener actual):");
                System.out.println("Actual: " + historial.getDiagnostico());
                System.out.print("> ");
                String nuevoDiagnostico = scanner.nextLine().trim();

                if (!nuevoDiagnostico.isEmpty()) {
                    historial.setDiagnostico(nuevoDiagnostico);
                }

                System.out.println("\nIngrese nuevo tratamiento (Enter para mantener actual):");
                System.out.println("Actual: " + historial.getTratamiento_recomendado());
                System.out.print("> ");
                String nuevoTratamiento = scanner.nextLine().trim();

                if (!nuevoTratamiento.isEmpty()) {
                    historial.setTratamiento_recomendado(nuevoTratamiento);
                }

                System.out.print("\n¿Confirma los cambios? (S/N): ");
                String confirmacion = scanner.nextLine().trim().toUpperCase();

                if (confirmacion.equals("S") || confirmacion.equals("SI")) {
                    if (historialController.actualizarHistorial(historial)) {
                        System.out.println("\n¡Historial actualizado correctamente!");
                        mostrarDetalleHistorial(historial);
                    } else {
                        System.out.println("\nError al actualizar el historial.");
                    }
                } else {
                    System.out.println("Actualización cancelada.");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine();
            }
        }

        /**
         * Muestra el detalle completo de un historial clínico
         */
        private void mostrarDetalleHistorial(HistorialClinico historial) {
            System.out.println("\n--- Detalle del Historial ---");
            System.out.println("ID: " + historial.getId());
            System.out.println("Mascota ID: " + historial.getMascota_id());
            System.out.println("Veterinario ID: " + historial.getVeterinario_id());
            System.out.println("Fecha del evento: " + historial.getFecha_evento());
            System.out.println("Tipo de evento: " + obtenerNombreTipoEvento(historial.getEvento_tipo_id()));
            System.out.println("Descripción: " + historial.getDescripcion());
            System.out.println("Diagnóstico: " + historial.getDiagnostico());
            System.out.println("Tratamiento: " + historial.getTratamiento_recomendado());

            if (historial.getProducto_id() != null) {
                System.out.println("Producto ID: " + historial.getProducto_id());
                System.out.println("Cantidad utilizada: " + historial.getCantidad_utilizada());
            }
        }

        /**
         * Muestra un resumen compacto del historial
         */
        private void mostrarResumenHistorial(HistorialClinico historial) {
            String descripcionCorta = historial.getDescripcion().length() > 30 ?
                    historial.getDescripcion().substring(0, 27) + "..." : historial.getDescripcion();

            System.out.printf("%-5d %-12d %-15s %-20s %-30s%n",
                    historial.getId(),
                    historial.getMascota_id(),
                    historial.getFecha_evento(),
                    obtenerNombreTipoEvento(historial.getEvento_tipo_id()),
                    descripcionCorta
            );
        }

        /**
         * Obtiene el nombre del tipo de evento médico
         */
        private String obtenerNombreTipoEvento(int tipoId) {
            return switch (tipoId) {
                case 1 -> "Vacunación";
                case 2 -> "Consulta General";
                case 3 -> "Cirugía";
                case 4 -> "Control de Rutina";
                case 5 -> "Emergencia";
                case 6 -> "Otro";
                default -> "Desconocido";
            };
        }

        /**
         * Pausa la ejecución hasta que el usuario presione Enter
         */
        private void pausarYContinuar() {
            System.out.println("\nPresione Enter para continuar...");
            try {
                scanner.nextLine();
            } catch (Exception e) {
                // Ignorar errores
            }
        }
    }

