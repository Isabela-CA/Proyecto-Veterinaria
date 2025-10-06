package com.happyfeet.View;

import com.happyfeet.Controller.ConsultaMedicaController;
import com.happyfeet.model.entities.ConsultaMedica;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ConsultaMedicaView {
        private ConsultaMedicaController controller;
        private Scanner scanner;
        private final DateTimeFormatter formatoFechaHora = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        private final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        public ConsultaMedicaView() {
            this.controller = new ConsultaMedicaController();
            this.scanner = new Scanner(System.in);
        }

        public ConsultaMedicaView(ConsultaMedicaController controller, Scanner scanner) {
            this.controller = controller;
            this.scanner = scanner;
        }

        /**
         * Menú principal de Consultas Médicas
         */
        public void mostrarMenuConsultaMedica() {
            int opcion;
            do {
                System.out.println("\n═══════════════════════════════════════════════════════════");
                System.out.println("           GESTIÓN DE CONSULTAS MÉDICAS");
                System.out.println("═══════════════════════════════════════════════════════════");
                System.out.println("1.  Registrar nueva consulta médica");
                System.out.println("2.  Buscar consulta por ID");
                System.out.println("3.  Listar todas las consultas");
                System.out.println("4.  Listar consultas por mascota");
                System.out.println("5.  Listar consultas por veterinario");
                System.out.println("6.  Listar consultas por fecha");
                System.out.println("7.  Listar consultas por cita");
                System.out.println("8.  Actualizar consulta médica");
                System.out.println("9.  Ver estadísticas de mascota");
                System.out.println("10. Ver detalle completo de consulta");
                System.out.println("0.  Volver al menú anterior");
                System.out.println("═══════════════════════════════════════════════════════════");
                System.out.print("Seleccione una opción: ");

                try {
                    opcion = scanner.nextInt();
                    scanner.nextLine(); // Limpiar buffer

                    switch (opcion) {
                        case 1 -> registrarConsulta();
                        case 2 -> buscarConsultaPorId();
                        case 3 -> listarTodasConsultas();
                        case 4 -> listarConsultasPorMascota();
                        case 5 -> listarConsultasPorVeterinario();
                        case 6 -> listarConsultasPorFecha();
                        case 7 -> listarConsultasPorCita();
                        case 8 -> actualizarConsulta();
                        case 9 -> verEstadisticasMascota();
                        case 10 -> verDetalleConsulta();
                        case 0 -> System.out.println("\n✓ Volviendo al menú anterior...");
                        default -> System.out.println("\n✗ Opción inválida. Intente nuevamente.");
                    }
                } catch (Exception e) {
                    System.out.println("\n✗ Error: Ingrese un número válido.");
                    scanner.nextLine(); // Limpiar buffer en caso de error
                    opcion = -1; // Continuar el bucle
                }

                if (opcion != 0) {
                    pausar();
                }
            } while (opcion != 0);
        }

        /**
         * Registra una nueva consulta médica
         */
        private void registrarConsulta() {
            mostrarEncabezado("REGISTRAR NUEVA CONSULTA MÉDICA");

            try {
                System.out.print("ID de la mascota: ");
                int mascotaId = leerEntero();

                System.out.print("ID del veterinario: ");
                int veterinarioId = leerEntero();

                System.out.print("ID de la cita (0 si no tiene cita asociada): ");
                int citaId = leerEntero();

                System.out.print("Fecha y hora (yyyy-MM-dd HH:mm) [Enter para usar fecha actual]: ");
                scanner.nextLine(); // Limpiar buffer
                String fechaHoraStr = scanner.nextLine().trim();
                LocalDateTime fechaHora = fechaHoraStr.isEmpty() ?
                        LocalDateTime.now() : LocalDateTime.parse(fechaHoraStr, formatoFechaHora);

                System.out.print("Motivo de la consulta: ");
                String motivo = scanner.nextLine().trim();

                System.out.print("Síntomas: ");
                String sintomas = scanner.nextLine().trim();

                System.out.print("Diagnóstico: ");
                String diagnostico = scanner.nextLine().trim();

                System.out.print("Recomendaciones: ");
                String recomendaciones = scanner.nextLine().trim();

                System.out.print("Observaciones: ");
                String observaciones = scanner.nextLine().trim();

                System.out.print("Peso registrado (kg): ");
                double peso = leerDouble();

                System.out.print("Temperatura (°C): ");
                double temperatura = leerDouble();

                ConsultaMedica consulta = new ConsultaMedica(
                        mascotaId, veterinarioId, citaId, fechaHora,
                        motivo, sintomas, diagnostico, recomendaciones,
                        observaciones, peso, temperatura
                );

                if (controller.agregarConsultaMedica(consulta)) {
                    System.out.println("\n✓ Consulta médica registrada exitosamente");
                } else {
                    System.out.println("\n✗ Error al registrar la consulta médica");
                }

            } catch (DateTimeParseException e) {
                System.out.println("\n✗ Formato de fecha inválido. Use: yyyy-MM-dd HH:mm");
            } catch (Exception e) {
                System.out.println("\n✗ Error al registrar consulta: " + e.getMessage());
            }
        }

        /**
         * Busca y muestra una consulta por ID
         */
        private void buscarConsultaPorId() {
            mostrarEncabezado("BUSCAR CONSULTA POR ID");

            System.out.print("Ingrese el ID de la consulta: ");
            int id = leerEntero();

            ConsultaMedica consulta = controller.obtenerConsultaMedica(id);

            if (consulta != null) {
                mostrarDetalleConsulta(consulta);
            } else {
                System.out.println("\n✗ No se encontró consulta con ID: " + id);
            }
        }

        /**
         * Lista todas las consultas médicas
         */
        private void listarTodasConsultas() {
            mostrarEncabezado("LISTADO DE TODAS LAS CONSULTAS MÉDICAS");

            List<ConsultaMedica> consultas = controller.listarConsultasMedicas();

            if (consultas.isEmpty()) {
                System.out.println("No hay consultas médicas registradas.");
            } else {
                System.out.println("Total de consultas: " + consultas.size());
                System.out.println("─".repeat(120));
                mostrarTablaConsultas(consultas);
            }
        }

        /**
         * Lista consultas de una mascota específica
         */
        private void listarConsultasPorMascota() {
            mostrarEncabezado("CONSULTAS POR MASCOTA");

            System.out.print("Ingrese el ID de la mascota: ");
            int mascotaId = leerEntero();

            List<ConsultaMedica> consultas = controller.listarConsultasPorMascota(mascotaId);

            if (consultas.isEmpty()) {
                System.out.println("\nNo se encontraron consultas para la mascota con ID: " + mascotaId);
            } else {
                System.out.println("\nConsultas encontradas: " + consultas.size());
                System.out.println("─".repeat(120));
                mostrarTablaConsultas(consultas);
            }
        }

        /**
         * Lista consultas de un veterinario específico
         */
        private void listarConsultasPorVeterinario() {
            mostrarEncabezado("CONSULTAS POR VETERINARIO");

            System.out.print("Ingrese el ID del veterinario: ");
            int veterinarioId = leerEntero();

            List<ConsultaMedica> consultas = controller.listarConsultasPorVeterinario(veterinarioId);

            if (consultas.isEmpty()) {
                System.out.println("\nNo se encontraron consultas para el veterinario con ID: " + veterinarioId);
            } else {
                System.out.println("\nConsultas encontradas: " + consultas.size());
                System.out.println("─".repeat(120));
                mostrarTablaConsultas(consultas);
            }
        }

        /**
         * Lista consultas en un rango de fechas
         */
        private void listarConsultasPorFecha() {
            mostrarEncabezado("CONSULTAS POR RANGO DE FECHAS");

            try {
                scanner.nextLine(); // Limpiar buffer

                System.out.print("Fecha de inicio (yyyy-MM-dd): ");
                String fechaInicioStr = scanner.nextLine().trim();
                LocalDate fechaInicio = LocalDate.parse(fechaInicioStr, formatoFecha);

                System.out.print("Fecha de fin (yyyy-MM-dd): ");
                String fechaFinStr = scanner.nextLine().trim();
                LocalDate fechaFin = LocalDate.parse(fechaFinStr, formatoFecha);

                List<ConsultaMedica> consultas = controller.listarConsultasPorFecha(fechaInicio, fechaFin);

                if (consultas.isEmpty()) {
                    System.out.println("\nNo se encontraron consultas en el rango especificado.");
                } else {
                    System.out.println("\nConsultas encontradas: " + consultas.size());
                    System.out.println("─".repeat(120));
                    mostrarTablaConsultas(consultas);
                }

            } catch (DateTimeParseException e) {
                System.out.println("\n✗ Formato de fecha inválido. Use: yyyy-MM-dd");
            }
        }

        /**
         * Lista consultas asociadas a una cita
         */
        private void listarConsultasPorCita() {
            mostrarEncabezado("CONSULTAS POR CITA");

            System.out.print("Ingrese el ID de la cita: ");
            int citaId = leerEntero();

            List<ConsultaMedica> consultas = controller.listarConsultasPorCita(citaId);

            if (consultas.isEmpty()) {
                System.out.println("\nNo se encontraron consultas para la cita con ID: " + citaId);
            } else {
                System.out.println("\nConsultas encontradas: " + consultas.size());
                System.out.println("─".repeat(120));
                mostrarTablaConsultas(consultas);
            }
        }

        /**
         * Actualiza una consulta médica existente
         */
        private void actualizarConsulta() {
            mostrarEncabezado("ACTUALIZAR CONSULTA MÉDICA");

            System.out.print("Ingrese el ID de la consulta a actualizar: ");
            int id = leerEntero();

            ConsultaMedica consultaExistente = controller.obtenerConsultaMedica(id);

            if (consultaExistente == null) {
                System.out.println("\n✗ No se encontró consulta con ID: " + id);
                return;
            }

            System.out.println("\nDatos actuales:");
            mostrarDetalleConsulta(consultaExistente);

            try {
                scanner.nextLine(); // Limpiar buffer

                System.out.println("\n--- Ingrese los nuevos datos (Enter para mantener el valor actual) ---");

                System.out.print("Motivo [" + consultaExistente.getMotivo() + "]: ");
                String motivo = scanner.nextLine().trim();
                if (!motivo.isEmpty()) consultaExistente.setMotivo(motivo);

                System.out.print("Síntomas [" + consultaExistente.getSintomas() + "]: ");
                String sintomas = scanner.nextLine().trim();
                if (!sintomas.isEmpty()) consultaExistente.setSintomas(sintomas);

                System.out.print("Diagnóstico [" + consultaExistente.getDiagnostico() + "]: ");
                String diagnostico = scanner.nextLine().trim();
                if (!diagnostico.isEmpty()) consultaExistente.setDiagnostico(diagnostico);

                System.out.print("Recomendaciones [" + consultaExistente.getRecomendaciones() + "]: ");
                String recomendaciones = scanner.nextLine().trim();
                if (!recomendaciones.isEmpty()) consultaExistente.setRecomendaciones(recomendaciones);

                System.out.print("Observaciones [" + consultaExistente.getObservaciones() + "]: ");
                String observaciones = scanner.nextLine().trim();
                if (!observaciones.isEmpty()) consultaExistente.setObservaciones(observaciones);

                System.out.print("Peso (kg) [" + consultaExistente.getPeso_registrado() + "]: ");
                String pesoStr = scanner.nextLine().trim();
                if (!pesoStr.isEmpty()) consultaExistente.setPeso_registrado(Double.parseDouble(pesoStr));

                System.out.print("Temperatura (°C) [" + consultaExistente.getTemperatura() + "]: ");
                String tempStr = scanner.nextLine().trim();
                if (!tempStr.isEmpty()) consultaExistente.setTemperatura(Double.parseDouble(tempStr));

                if (controller.actualizarConsultaMedica(consultaExistente)) {
                    System.out.println("\n✓ Consulta actualizada exitosamente");
                } else {
                    System.out.println("\n✗ Error al actualizar la consulta");
                }

            } catch (NumberFormatException e) {
                System.out.println("\n✗ Error: Formato numérico inválido");
            } catch (Exception e) {
                System.out.println("\n✗ Error al actualizar: " + e.getMessage());
            }
        }

        /**
         * Muestra estadísticas de consultas de una mascota
         */
        private void verEstadisticasMascota() {
            mostrarEncabezado("ESTADÍSTICAS DE CONSULTAS POR MASCOTA");

            System.out.print("Ingrese el ID de la mascota: ");
            int mascotaId = leerEntero();

            var estadisticas = controller.obtenerEstadisticasPorMascota(mascotaId);

            if (estadisticas != null) {
                System.out.println("\n┌" + "─".repeat(50) + "┐");
                System.out.println("│ ESTADÍSTICAS DE CONSULTAS - MASCOTA ID: " + mascotaId);
                System.out.println("├" + "─".repeat(50) + "┤");
                System.out.printf("│ Total de consultas: %28d │%n", estadisticas.getTotalConsultas());
                System.out.printf("│ Peso promedio: %30.2f kg │%n", estadisticas.getPesoPromedio());
                System.out.printf("│ Temperatura promedio: %24.2f°C │%n", estadisticas.getTemperaturaPromedio());
                System.out.println("└" + "─".repeat(50) + "┘");
            } else {
                System.out.println("\n✗ No se pudieron obtener las estadísticas");
            }
        }

        /**
         * Muestra el detalle completo de una consulta
         */
        private void verDetalleConsulta() {
            mostrarEncabezado("DETALLE COMPLETO DE CONSULTA");

            System.out.print("Ingrese el ID de la consulta: ");
            int id = leerEntero();

            ConsultaMedica consulta = controller.obtenerConsultaMedica(id);

            if (consulta != null) {
                mostrarDetalleConsulta(consulta);
            } else {
                System.out.println("\n✗ No se encontró consulta con ID: " + id);
            }
        }

        // ==================== MÉTODOS DE VISUALIZACIÓN ====================

        /**
         * Muestra una tabla con las consultas
         */
        private void mostrarTablaConsultas(List<ConsultaMedica> consultas) {
            System.out.printf("%-5s %-12s %-12s %-8s %-17s %-30s %-12s %-12s%n",
                    "ID", "Mascota ID", "Vet ID", "Cita ID", "Fecha y Hora", "Motivo", "Peso (kg)", "Temp (°C)");
            System.out.println("─".repeat(120));

            for (ConsultaMedica consulta : consultas) {
                System.out.printf("%-5d %-12d %-12d %-8s %-17s %-30s %-12.2f %-12.2f%n",
                        consulta.getId(),
                        consulta.getMascota_id(),
                        consulta.getVeterinario_id(),
                        consulta.getCita_id() > 0 ? consulta.getCita_id() : "N/A",
                        consulta.getFecha_hora().format(formatoFechaHora),
                        truncarTexto(consulta.getMotivo(), 30),
                        consulta.getPeso_registrado(),
                        consulta.getTemperatura()
                );
            }
        }

        /**
         * Muestra el detalle completo de una consulta
         */
        private void mostrarDetalleConsulta(ConsultaMedica consulta) {
            System.out.println("\n╔" + "═".repeat(60) + "╗");
            System.out.println("║ DETALLE DE CONSULTA MÉDICA");
            System.out.println("╠" + "═".repeat(60) + "╣");
            System.out.printf("║ ID: %-55d ║%n", consulta.getId());
            System.out.printf("║ Mascota ID: %-47d ║%n", consulta.getMascota_id());
            System.out.printf("║ Veterinario ID: %-43d ║%n", consulta.getVeterinario_id());
            System.out.printf("║ Cita ID: %-50s ║%n",
                    consulta.getCita_id() > 0 ? consulta.getCita_id() : "Sin cita asociada");
            System.out.printf("║ Fecha y Hora: %-45s ║%n",
                    consulta.getFecha_hora().format(formatoFechaHora));
            System.out.println("╠" + "═".repeat(60) + "╣");
            System.out.printf("║ Motivo: %-51s ║%n", truncarTexto(consulta.getMotivo(), 51));
            System.out.printf("║ Síntomas: %-49s ║%n",
                    truncarTexto(consulta.getSintomas() != null ? consulta.getSintomas() : "N/A", 49));
            System.out.printf("║ Diagnóstico: %-46s ║%n",
                    truncarTexto(consulta.getDiagnostico() != null ? consulta.getDiagnostico() : "N/A", 46));
            System.out.println("╠" + "═".repeat(60) + "╣");
            System.out.printf("║ Peso registrado: %-38.2f kg ║%n", consulta.getPeso_registrado());
            System.out.printf("║ Temperatura: %-42.2f°C ║%n", consulta.getTemperatura());
            System.out.println("╠" + "═".repeat(60) + "╣");
            System.out.printf("║ Recomendaciones: %-42s ║%n",
                    truncarTexto(consulta.getRecomendaciones() != null ? consulta.getRecomendaciones() : "N/A", 42));
            System.out.printf("║ Observaciones: %-44s ║%n",
                    truncarTexto(consulta.getObservaciones() != null ? consulta.getObservaciones() : "N/A", 44));
            System.out.println("╚" + "═".repeat(60) + "╝");
        }

        // ==================== MÉTODOS AUXILIARES ====================

        private void mostrarEncabezado(String titulo) {
            System.out.println("\n" + "═".repeat(60));
            System.out.println("  " + titulo);
            System.out.println("═".repeat(60));
        }

        private int leerEntero() {
            while (!scanner.hasNextInt()) {
                System.out.print("✗ Entrada inválida. Ingrese un número: ");
                scanner.next();
            }
            return scanner.nextInt();
        }

        private double leerDouble() {
            while (!scanner.hasNextDouble()) {
                System.out.print("✗ Entrada inválida. Ingrese un número: ");
                scanner.next();
            }
            return scanner.nextDouble();
        }

        private String truncarTexto(String texto, int longitud) {
            if (texto == null) return "N/A";
            return texto.length() > longitud ? texto.substring(0, longitud - 3) + "..." : texto;
        }

        private void pausar() {
            System.out.print("\nPresione Enter para continuar...");
            try {
                scanner.nextLine();
            } catch (Exception e) {
                // Ignorar errores
            }
        }
}
