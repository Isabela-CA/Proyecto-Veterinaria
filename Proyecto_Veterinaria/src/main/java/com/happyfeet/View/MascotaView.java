package com.happyfeet.View;

import com.happyfeet.Controller.MascotaController;
import com.happyfeet.Controller.RazaController;
import com.happyfeet.model.entities.Mascota;
import com.happyfeet.model.entities.Raza;
import com.happyfeet.model.enums.SexoMascota;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class MascotaView {

    private MascotaController mascotaController;
    private RazaController razaController;
    private Scanner scanner = new Scanner(System.in);

    public MascotaView(MascotaController mascotaController) {
        this.mascotaController = mascotaController;
        this.razaController = new RazaController();
    }

    /**
     * Menú principal
     */
    public void mostrarMenuMascotas() {
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("=== GESTIÓN DE MASCOTAS ===");
            System.out.println("=".repeat(50));
            System.out.println("1. Registrar Mascota");
            System.out.println("2. Listar Todas las Mascotas");
            System.out.println("3. Buscar Mascota por ID");
            System.out.println("4. Listar Mascotas por Dueño");
            System.out.println("5. Actualizar Mascota");
            System.out.println("6. Transferir Propiedad");
            System.out.println("7. Ver Razas Disponibles");
            System.out.println("0. Volver al Menú Principal");
            System.out.println("=".repeat(50));
            System.out.print("Seleccione una opción: ");

            try {
                int opcion = scanner.nextInt();
                scanner.nextLine(); // limpiar el buffer

                switch (opcion) {
                    case 1 -> {
                        registrarMascota();
                        pausarYContinuar();
                    }
                    case 2 -> {
                        listarMascotas();
                        pausarYContinuar();
                    }
                    case 3 -> {
                        buscarMascota();
                        pausarYContinuar();
                    }
                    case 4 -> {
                        listarMascotasPorDueno();
                        pausarYContinuar();
                    }
                    case 5 -> {
                        actualizarMascota();
                        pausarYContinuar();
                    }
                    case 6 -> {
                        transferirPropiedad();
                        pausarYContinuar();
                    }
                    case 7 -> {
                        verRazasDisponibles();
                        pausarYContinuar();
                    }
                    case 0 -> {
                        System.out.println("✓ Regresando al menú principal...");
                        continuar = false;
                    }
                    default -> {
                        System.out.println("Opción inválida. Por favor seleccione una opción entre 0-7.");
                        pausarYContinuar();
                    }
                }
            } catch (Exception e) {
                System.out.println(" Error: Debe ingresar un número válido.");
                scanner.nextLine(); // limpiar buffer
                pausarYContinuar();
            }
        }
    }


    private void registrarMascota() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("=== REGISTRAR NUEVA MASCOTA ===");
        System.out.println("=".repeat(50));

        try {
            // Solicitar ID del dueño
            System.out.print("Ingrese ID del dueño: ");
            int duenoId = scanner.nextInt();
            scanner.nextLine();

            // Solicitar nombre
            System.out.print("Ingrese nombre de la mascota: ");
            String nombre = scanner.nextLine().trim();

            if (nombre.isEmpty()) {
                System.out.println(" El nombre no puede estar vacío.");
                return;
            }

            // Mostrar especies disponibles
            System.out.println("\n--- Seleccione la especie ---");
            System.out.println("1. Perro");
            System.out.println("2. Gato");
            System.out.println("3. Ave");
            System.out.println("4. Conejo");
            System.out.println("5. Otros");
            System.out.print("Opción: ");
            int especieId = scanner.nextInt();
            scanner.nextLine();

            if (especieId < 1 || especieId > 5) {
                System.out.println("❌ Especie inválida.");
                return;
            }

            // Mostrar razas disponibles para la especie
            System.out.println("\n--- Razas disponibles para esta especie ---");
            List<Raza> razas = razaController.listarRazasPorEspecie(especieId);

            if (razas.isEmpty()) {
                System.out.println("⚠️  No hay razas registradas para esta especie.");
                System.out.println("Por favor, registre una raza primero en el menú de Gestión de Razas.");
                return;
            }

            System.out.println(String.format("%-5s %-30s", "ID", "Nombre"));
            System.out.println("-".repeat(35));
            razas.forEach(r -> System.out.println(String.format("%-5d %-30s", r.getId(), r.getNombre())));

            // Seleccionar raza
            System.out.print("\nIngrese ID de la raza: ");
            int razaId = scanner.nextInt();
            scanner.nextLine();

            // Validar que la raza existe en la lista
            boolean razaValida = razas.stream().anyMatch(r -> r.getId() == razaId);
            if (!razaValida) {
                System.out.println(" ID de raza inválido.");
                return;
            }

            // Solicitar fecha de nacimiento
            System.out.print("Ingrese fecha de nacimiento (yyyy-MM-dd): ");
            String fechaStr = scanner.nextLine();
            LocalDate fechaNacimiento;

            try {
                fechaNacimiento = LocalDate.parse(fechaStr, DateTimeFormatter.ISO_LOCAL_DATE);

                // Validar que la fecha no sea futura
                if (fechaNacimiento.isAfter(LocalDate.now())) {
                    System.out.println(" La fecha de nacimiento no puede ser futura.");
                    return;
                }
            } catch (DateTimeParseException e) {
                System.out.println(" Formato de fecha inválido. Use yyyy-MM-dd (ejemplo: 2020-05-15)");
                return;
            }

            // Solicitar sexo
            System.out.print("Ingrese sexo (MACHO/HEMBRA): ");
            String sexoStr = scanner.nextLine().trim().toUpperCase(); // solo mayúsculas

            if (!sexoStr.equals("MACHO") && !sexoStr.equals("HEMBRA")) {
                System.out.println("Sexo inválido. Debe ser MACHO o HEMBRA.");
                return;
            }

            SexoMascota.Sexo sexo = SexoMascota.Sexo.fromString(sexoStr);

            // Solicitar URL de foto (opcional)
            System.out.print("Ingrese URL de foto (opcional, presione Enter para omitir): ");
            String urlFoto = scanner.nextLine().trim();
            if (urlFoto.isEmpty()) {
                urlFoto = null;
            }

            // Crear y registrar la mascota
            Mascota mascota = new Mascota(duenoId, nombre, razaId, fechaNacimiento, sexo, urlFoto);

            if (mascotaController.agregarMascota(mascota)) {
                System.out.println("\n ¡Mascota registrada correctamente!");
                System.out.println("Nombre: " + nombre);
                System.out.println("Dueño ID: " + duenoId);
            } else {
                System.out.println("Error al registrar la mascota. Verifique los datos.");
            }

        } catch (IllegalArgumentException e) {
            System.out.println(" Error de validación: " + e.getMessage());
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
            scanner.nextLine();
        }
    }

    /**
     * OPCIÓN 2: Listar todas las mascotas registradas
     */
    private void listarMascotas() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("=== LISTA DE TODAS LAS MASCOTAS ===");
        System.out.println("=".repeat(70));

        List<Mascota> mascotas = mascotaController.listarMascotas();

        if (mascotas.isEmpty()) {
            System.out.println("⚠ No hay mascotas registradas en el sistema.");
        } else {
            System.out.println(String.format("%-5s %-20s %-12s %-12s %-15s",
                    "ID", "Nombre", "Dueño ID", "Raza ID", "Sexo"));
            System.out.println("-".repeat(70));

            mascotas.forEach(m -> {
                System.out.println(String.format("%-5d %-20s %-12d %-12d %-15s",
                        m.getId(),
                        m.getNombre().length() > 20 ? m.getNombre().substring(0, 17) + "..." : m.getNombre(),
                        m.getDueno_id(),
                        m.getRaza_id(),
                        m.getSexo()));
            });

            System.out.println("-".repeat(70));
            System.out.println("Total de mascotas registradas: " + mascotas.size());
        }
    }

    /**
     * OPCIÓN 3: Buscar mascota por ID
     */
    private void buscarMascota() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("=== BUSCAR MASCOTA POR ID ===");
        System.out.println("=".repeat(50));

        try {
            System.out.print("Ingrese ID de la mascota: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            if (id <= 0) {
                System.out.println(" El ID debe ser un número positivo.");
                return;
            }

            Mascota mascota = mascotaController.obtenerMascota(id);

            if (mascota != null) {
                mostrarDetalleMascota(mascota);
            } else {
                System.out.println("  No se encontró ninguna mascota con ID: " + id);
            }

        } catch (Exception e) {
            System.out.println("Error: Debe ingresar un número válido.");
            scanner.nextLine();
        }
    }

    /**
     * OPCIÓN 4: Listar mascotas por dueño
     */
    private void listarMascotasPorDueno() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("=== LISTAR MASCOTAS POR DUEÑO ===");
        System.out.println("=".repeat(50));

        try {
            System.out.print("Ingrese ID del dueño: ");
            int duenoId = scanner.nextInt();
            scanner.nextLine();

            if (duenoId <= 0) {
                System.out.println(" El ID del dueño debe ser un número positivo.");
                return;
            }

            List<Mascota> mascotas = mascotaController.listarMascotasPorDueno(duenoId);

            if (mascotas.isEmpty()) {
                System.out.println("  El dueño con ID " + duenoId + " no tiene mascotas registradas.");
            } else {
                System.out.println("\n--- Mascotas del dueño ID: " + duenoId + " ---");
                System.out.println(String.format("%-5s %-20s %-12s %-15s %-15s",
                        "ID", "Nombre", "Raza ID", "Sexo", "F. Nacimiento"));
                System.out.println("-".repeat(70));

                mascotas.forEach(m -> {
                    System.out.println(String.format("%-5d %-20s %-12d %-15s %-15s",
                            m.getId(),
                            m.getNombre().length() > 20 ? m.getNombre().substring(0, 17) + "..." : m.getNombre(),
                            m.getRaza_id(),
                            m.getSexo(),
                            m.getFecha_nacimiento()));
                });

                System.out.println("-".repeat(70));
                System.out.println("Total de mascotas: " + mascotas.size());
            }

        } catch (Exception e) {
            System.out.println(" Error: Debe ingresar un número válido.");
            scanner.nextLine();
        }
    }

    /**
     * OPCIÓN 5: Actualizar información de una mascota
     */
    private void actualizarMascota() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("=== ACTUALIZAR MASCOTA ===");
        System.out.println("=".repeat(50));

        try {
            System.out.print("Ingrese ID de la mascota a modificar: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            if (id <= 0) {
                System.out.println(" El ID debe ser un número positivo.");
                return;
            }

            Mascota mascota = mascotaController.obtenerMascota(id);

            if (mascota == null) {
                System.out.println("  No se encontró la mascota con ID: " + id);
                return;
            }

            // Mostrar datos actuales
            System.out.println("\n--- Datos actuales de la mascota ---");
            mostrarDetalleMascota(mascota);

            // Actualizar nombre
            System.out.println("\n--- Actualizar Información ---");
            System.out.print("Nuevo nombre (actual: " + mascota.getNombre() + ") [Enter para mantener]: ");
            String nuevoNombre = scanner.nextLine().trim();

            if (!nuevoNombre.isEmpty()) {
                mascota.setNombre(nuevoNombre);
                System.out.println("✓ Nombre actualizado");
            }

            // Preguntar si desea cambiar la raza
            System.out.print("\n¿Desea cambiar la raza? (S/N): ");
            String cambiarRaza = scanner.nextLine().trim().toUpperCase();

            if (cambiarRaza.equals("S") || cambiarRaza.equals("SI")) {
                System.out.println("\n--- Seleccione la nueva especie ---");
                System.out.println("1. Perro");
                System.out.println("2. Gato");
                System.out.println("3. Ave");
                System.out.println("4. Conejo");
                System.out.println("5. Otros");
                System.out.print("Opción: ");
                int especieId = scanner.nextInt();
                scanner.nextLine();

                if (especieId >= 1 && especieId <= 5) {
                    System.out.println("\n--- Razas disponibles ---");
                    List<Raza> razas = razaController.listarRazasPorEspecie(especieId);

                    if (razas.isEmpty()) {
                        System.out.println(" No hay razas disponibles para esta especie.");
                    } else {
                        System.out.println(String.format("%-5s %-30s", "ID", "Nombre"));
                        System.out.println("-".repeat(35));
                        razas.forEach(r -> System.out.println(String.format("%-5d %-30s", r.getId(), r.getNombre())));

                        System.out.print("\nIngrese ID de la nueva raza: ");
                        int nuevaRazaId = scanner.nextInt();
                        scanner.nextLine();

                        // Validar que la raza existe
                        boolean razaValida = razas.stream().anyMatch(r -> r.getId() == nuevaRazaId);
                        if (razaValida) {
                            mascota.setRaza_id(nuevaRazaId);
                            System.out.println("✓ Raza actualizada");
                        } else {
                            System.out.println(" ID de raza inválido. Se mantendrá la raza actual.");
                        }
                    }
                } else {
                    System.out.println(" Especie inválida. Se mantendrá la raza actual.");
                }
            }

            // Preguntar si desea actualizar la URL de la foto
            System.out.print("\n¿Desea actualizar la URL de la foto? (S/N): ");
            String cambiarFoto = scanner.nextLine().trim().toUpperCase();

            if (cambiarFoto.equals("S") || cambiarFoto.equals("SI")) {
                System.out.print("Nueva URL de foto [Enter para eliminar]: ");
                String nuevaUrl = scanner.nextLine().trim();
                mascota.setUrl_foto(nuevaUrl.isEmpty() ? null : nuevaUrl);
                System.out.println("✓ URL de foto actualizada");
            }

            // Confirmar actualización
            System.out.print("\n¿Confirma los cambios? (S/N): ");
            String confirmacion = scanner.nextLine().trim().toUpperCase();

            if (confirmacion.equals("S") || confirmacion.equals("SI")) {
                if (mascotaController.actualizarMascota(mascota)) {
                    System.out.println("\n ¡Mascota actualizada correctamente!");
                    mostrarDetalleMascota(mascota);
                } else {
                    System.out.println(" Error al actualizar la mascota en la base de datos.");
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
     * OPCIÓN 6: Transferir propiedad de una mascota a otro dueño
     */
    private void transferirPropiedad() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("=== TRANSFERIR PROPIEDAD DE MASCOTA ===");
        System.out.println("=".repeat(50));

        try {
            System.out.print("Ingrese ID de la mascota a transferir: ");
            int mascotaId = scanner.nextInt();
            scanner.nextLine();

            if (mascotaId <= 0) {
                System.out.println(" El ID debe ser un número positivo.");
                return;
            }

            Mascota mascota = mascotaController.obtenerMascota(mascotaId);

            if (mascota == null) {
                System.out.println("  No se encontró la mascota con ID: " + mascotaId);
                return;
            }

            // Mostrar información de la mascota
            System.out.println("\n--- Información de la mascota ---");
            System.out.println("Nombre: " + mascota.getNombre());
            System.out.println("Dueño actual ID: " + mascota.getDueno_id());
            System.out.println("Raza ID: " + mascota.getRaza_id());

            // Solicitar ID del nuevo dueño
            System.out.print("\nIngrese ID del nuevo dueño: ");
            int nuevoDuenoId = scanner.nextInt();
            scanner.nextLine();

            if (nuevoDuenoId <= 0) {
                System.out.println(" El ID del nuevo dueño debe ser un número positivo.");
                return;
            }

            if (nuevoDuenoId == mascota.getDueno_id()) {
                System.out.println("  El nuevo dueño es el mismo que el actual. No se realizará la transferencia.");
                return;
            }

            // Confirmar transferencia
            System.out.println("\n--- Confirmar Transferencia ---");
            System.out.println("Mascota: " + mascota.getNombre());
            System.out.println("Dueño actual: ID " + mascota.getDueno_id());
            System.out.println("Nuevo dueño: ID " + nuevoDuenoId);
            System.out.print("\n¿Confirma la transferencia? (S/N): ");
            String confirmacion = scanner.nextLine().trim().toUpperCase();

            if (confirmacion.equals("S") || confirmacion.equals("SI")) {
                if (mascotaController.transferirMascota(mascotaId, nuevoDuenoId)) {
                    System.out.println("\n ¡Propiedad transferida correctamente!");
                    System.out.println("La mascota '" + mascota.getNombre() + "' ahora pertenece al dueño ID: " + nuevoDuenoId);
                } else {
                    System.out.println(" Error al transferir la propiedad. Verifique que el nuevo dueño exista.");
                }
            } else {
                System.out.println("  Transferencia cancelada.");
            }

        } catch (Exception e) {
            System.out.println(" Error: Debe ingresar datos válidos.");
            scanner.nextLine();
        }
    }

    /**
     * OPCIÓN 7: Ver razas disponibles por especie
     */
    private void verRazasDisponibles() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("=== VER RAZAS DISPONIBLES ===");
        System.out.println("=".repeat(50));

        try {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Perro");
            System.out.println("2. Gato");
            System.out.println("3. Ave");
            System.out.println("4. Conejo");
            System.out.println("5. Otros");
            System.out.println("0. Ver todas las razas");
            System.out.print("Opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            if (opcion == 0) {
                // Mostrar todas las razas
                List<Raza> todasRazas = razaController.listarTodasLasRazas();

                if (todasRazas.isEmpty()) {
                    System.out.println("  No hay razas registradas en el sistema.");
                } else {
                    System.out.println("\n--- TODAS LAS RAZAS REGISTRADAS ---");
                    System.out.println(String.format("%-5s %-15s %-30s", "ID", "Especie ID", "Nombre de la Raza"));
                    System.out.println("-".repeat(50));

                    todasRazas.forEach(r -> {
                        String nombreEspecie = obtenerNombreEspecie(r.getEspecie_id());
                        System.out.println(String.format("%-5d %-15s %-30s",
                                r.getId(),
                                nombreEspecie,
                                r.getNombre()));
                    });

                    System.out.println("-".repeat(50));
                    System.out.println("Total de razas: " + todasRazas.size());
                }

            } else if (opcion >= 1 && opcion <= 5) {
                // Mostrar razas de una especie específica
                List<Raza> razas = razaController.listarRazasPorEspecie(opcion);

                if (razas.isEmpty()) {
                    System.out.println("  No hay razas registradas para esta especie.");
                } else {
                    String nombreEspecie = obtenerNombreEspecie(opcion);
                    System.out.println("\n--- RAZAS DE " + nombreEspecie.toUpperCase() + " ---");
                    System.out.println(String.format("%-5s %-30s", "ID", "Nombre de la Raza"));
                    System.out.println("-".repeat(35));

                    razas.forEach(r -> {
                        System.out.println(String.format("%-5d %-30s", r.getId(), r.getNombre()));
                    });

                    System.out.println("-".repeat(35));
                    System.out.println("Total de razas: " + razas.size());
                }

            } else {
                System.out.println(" Opción inválida. Debe seleccionar entre 0-5.");
            }

        } catch (Exception e) {
            System.out.println(" Error: Debe ingresar un número válido.");
            scanner.nextLine();
        }
    }

    /**
     * Muestra el detalle completo de una mascota
     */
    private void mostrarDetalleMascota(Mascota mascota) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("--- DETALLE DE LA MASCOTA ---");
        System.out.println("=".repeat(50));
        System.out.println("ID: " + mascota.getId());
        System.out.println("Nombre: " + mascota.getNombre());
        System.out.println("Dueño ID: " + mascota.getDueno_id());
        System.out.println("Raza ID: " + mascota.getRaza_id());

        // Obtener y mostrar información de la raza
        Raza raza = razaController.buscarRazaPorId(mascota.getRaza_id());
        if (raza != null) {
            System.out.println("Raza: " + raza.getNombre());
            System.out.println("Especie: " + obtenerNombreEspecie(raza.getEspecie_id()));
        } else {
            System.out.println("Raza: No disponible");
        }

        System.out.println("Fecha de Nacimiento: " + mascota.getFecha_nacimiento());
        System.out.println("Edad aproximada: " + calcularEdad(mascota.getFecha_nacimiento()));
        System.out.println("Sexo: " + mascota.getSexo());

        if (mascota.getUrl_foto() != null && !mascota.getUrl_foto().isEmpty()) {
            System.out.println("URL Foto: " + mascota.getUrl_foto());
        } else {
            System.out.println("URL Foto: No registrada");
        }

        System.out.println("=".repeat(50));
    }

    /**
     * Pausa la ejecución hasta que el usuario presione Enter
     */
    private void pausarYContinuar() {
        System.out.println("\n▶ Presione Enter para continuar...");
        try {
            scanner.nextLine();
        } catch (Exception e) {
            // Ignorar errores
        }
    }

    /**
     * Obtiene el nombre de la especie según su ID
     */
    private String obtenerNombreEspecie(int especieId) {
        return switch (especieId) {
            case 1 -> "Perro";
            case 2 -> "Gato";
            case 3 -> "Ave";
            case 4 -> "Conejo";
            case 5 -> "Otros";
            default -> "Desconocida";
        };
    }

    /**
     * Calcula la edad aproximada de la mascota
     */
    private String calcularEdad(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) {
            return "No disponible";
        }

        LocalDate ahora = LocalDate.now();
        int años = ahora.getYear() - fechaNacimiento.getYear();
        int meses = ahora.getMonthValue() - fechaNacimiento.getMonthValue();

        if (meses < 0) {
            años--;
            meses += 12;
        }

        if (años > 0) {
            return años + (años == 1 ? " año" : " años") +
                    (meses > 0 ? " y " + meses + (meses == 1 ? " mes" : " meses") : "");
        } else {
            return meses + (meses == 1 ? " mes" : " meses");
        }
    }
}

