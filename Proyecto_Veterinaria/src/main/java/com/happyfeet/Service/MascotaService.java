package com.happyfeet.Service;

import com.happyfeet.Repository.DuenosDAO;
import com.happyfeet.Repository.Interfaz.IDuenosDAO;
import com.happyfeet.Repository.Interfaz.IMascotaDAO;
import com.happyfeet.Repository.MascotaDAO;
import com.happyfeet.model.entities.Mascota;

import java.util.List;

public class MascotaService {

        private final IMascotaDAO mascotaDAO;
        private final IDuenosDAO duenosDAO;

        // Constructor con inyección de dependencias
        public MascotaService(IMascotaDAO mascotaDAO, IDuenosDAO duenosDAO) {
            this.mascotaDAO = mascotaDAO;
            this.duenosDAO = duenosDAO;
        }

        // Constructor sin parámetros (crea sus propias instancias)
        public MascotaService() {
            this.mascotaDAO = new MascotaDAO();
            this.duenosDAO = new DuenosDAO();
        }

        /**
         * Agrega una nueva mascota con validaciones
         */
        public boolean agregarMascota(Mascota mascota) {
            validarDuenoExiste(mascota.getDueno_id());
            validarNombreMascota(mascota.getNombre());

            return mascotaDAO.agregarMascota(mascota);
        }

        public Mascota obtenerMascota(int id) {
            validarIdPositivo(id, "mascota");
            return mascotaDAO.buscarMascotaPorId(id);
        }

        /**
         * Lista todas las mascotas registradas
         */
        public List<Mascota> listarMascotas() {
            return mascotaDAO.listarMacota();
        }

        /**
         * Lista todas las mascotas de un dueno especifico
         */
        public List<Mascota> listarMascotasPorDueno(int duenoId) {
            validarIdPositivo(duenoId, "dueno");
            return mascotaDAO.listarMascotasPorDueno(duenoId);
        }

        /**
         * Actualiza los datos de una mascota
         */
        public boolean actualizarMascota(Mascota mascota) {
            validarIdPositivo(mascota.getId(), "mascota");
            validarDuenoExiste(mascota.getDueno_id());
            validarNombreMascota(mascota.getNombre());

            return mascotaDAO.modificarMascota(mascota);
        }

        /**
         * Transfiere una mascota a un nuevo dueno
         */
        public boolean transferirMascota(int mascotaId, int nuevoDuenoId) {
            Mascota mascota = mascotaDAO.buscarMascotaPorId(mascotaId);
            if (mascota == null) {
                throw new IllegalArgumentException(
                        String.format("La mascota especificada no existe (ID: %d)", mascotaId)
                );
            }

            validarDuenoExiste(nuevoDuenoId);

            return mascotaDAO.transferirMascota(mascotaId, nuevoDuenoId);
        }


        /**
         * Obtiene estadisticas de mascotas por dueno
         */
        public ResumenMascotas obtenerResumenPorDueno(int duenoId) {
            List<Mascota> mascotas = listarMascotasPorDueno(duenoId);

            long totalMachos = mascotas.stream()
                    .filter(m -> "MACHO".equalsIgnoreCase(m.getSexo().toString()))
                    .count();

            long totalHembras = mascotas.size() - totalMachos;

            return new ResumenMascotas(
                    mascotas.size(),
                    (int) totalMachos,
                    (int) totalHembras
            );
        }

        /**
         * Verifica si una mascota pertenece a un dueno especifico
         */
        public boolean perteneceADueno(int mascotaId, int duenoId) {
            Mascota mascota = obtenerMascota(mascotaId);
            return mascota != null && mascota.getDueno_id() == duenoId;
        }

        // Metodos privados de validacion

        private void validarDuenoExiste(int duenoId) {
            if (duenosDAO.buscarDuenosPorId(duenoId) == null) {
                throw new IllegalArgumentException(
                        String.format("El dueno especificado no existe (ID: %d)", duenoId)
                );
            }
        }

        private void validarNombreMascota(String nombre) {
            if (nombre == null || nombre.isBlank()) {
                throw new IllegalArgumentException(
                        "El nombre de la mascota no puede estar vacio"
                );
            }
        }

        private void validarIdPositivo(int id, String entidad) {
            if (id <= 0) {
                throw new IllegalArgumentException(
                        String.format("ID de %s invalido", entidad)
                );
            }
        }

        // Clase interna para resumen de mascotas
        public static class ResumenMascotas {
            private final int totalMascotas;
            private final int totalMachos;
            private final int totalHembras;

            public ResumenMascotas(int totalMascotas, int totalMachos, int totalHembras) {
                this.totalMascotas = totalMascotas;
                this.totalMachos = totalMachos;
                this.totalHembras = totalHembras;
            }

            public int getTotalMascotas() {
                return totalMascotas;
            }

            public int getTotalMachos() {
                return totalMachos;
            }

            public int getTotalHembras() {
                return totalHembras;
            }

            @Override
            public String toString() {
                return String.format("Total de mascotas: %d%nMachos: %d%nHembras: %d%n",
                        totalMascotas, totalMachos, totalHembras);
            }
        }
}