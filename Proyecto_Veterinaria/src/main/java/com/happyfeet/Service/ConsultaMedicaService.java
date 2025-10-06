package com.happyfeet.Service;

import com.happyfeet.Repository.ConsultaMedicaDAO;
import com.happyfeet.Repository.Interfaz.IConsultaMedicaDAO;
import com.happyfeet.Repository.Interfaz.IMascotaDAO;
import com.happyfeet.Repository.Interfaz.IVeterinarioDAO;
import com.happyfeet.Repository.MascotaDAO;
import com.happyfeet.Repository.VeterinarioDAO;
import com.happyfeet.model.entities.ConsultaMedica;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ConsultaMedicaService {
        private final IConsultaMedicaDAO consultaMedicaDAO;
        private final IMascotaDAO mascotaDAO;
        private final IVeterinarioDAO veterinarioDAO;

        // Constructor con inyección de dependencias (para testing y flexibilidad)
        public ConsultaMedicaService(IConsultaMedicaDAO consultaMedicaDAO,
                                     IMascotaDAO mascotaDAO,
                                     IVeterinarioDAO veterinarioDAO) {
            this.consultaMedicaDAO = consultaMedicaDAO;
            this.mascotaDAO = mascotaDAO;
            this.veterinarioDAO = veterinarioDAO;
        }

        // Constructor sin parámetros (crea sus propias instancias)
        public ConsultaMedicaService() {
            this.consultaMedicaDAO = new ConsultaMedicaDAO();
            this.mascotaDAO = new MascotaDAO();
            this.veterinarioDAO = new VeterinarioDAO();
        }

        /**
         * Registra una nueva consulta médica con validaciones
         */
        public boolean agregarConsultaMedica(ConsultaMedica consultaMedica) {
            validarDatosConsulta(consultaMedica);
            validarRelaciones(consultaMedica);

            // Si no tiene fecha, usar fecha y hora actual
            if (consultaMedica.getFecha_hora() == null) {
                consultaMedica.setFecha_hora(LocalDateTime.now());
            }

            // Normalizar campos opcionales vacíos a NULL
            normalizarCamposOpcionales(consultaMedica);

            return consultaMedicaDAO.agregarConsultaMedica(consultaMedica);
        }

        /**
         * Obtiene una consulta médica por su ID
         */
        public ConsultaMedica obtenerConsultaMedica(int id) {
            validarIdPositivo(id, "consulta médica");
            return consultaMedicaDAO.buscarConsultaMedicaPorId(id);
        }

        /**
         * Lista todas las consultas médicas
         */
        public List<ConsultaMedica> listarConsultasMedicas() {
            return consultaMedicaDAO.listarConsultaMedica();
        }

        /**
         * Lista consultas de una mascota específica
         */
        public List<ConsultaMedica> listarConsultasPorMascota(int mascotaId) {
            validarIdPositivo(mascotaId, "mascota");
            validarMascotaExiste(mascotaId);
            return consultaMedicaDAO.listarConsultasPorMascota(mascotaId);
        }

        /**
         * Lista consultas de un veterinario específico
         */
        public List<ConsultaMedica> listarConsultasPorVeterinario(int veterinarioId) {
            validarIdPositivo(veterinarioId, "veterinario");
            validarVeterinarioExiste(veterinarioId);
            return consultaMedicaDAO.listarConsultasPorVeterinario(veterinarioId);
        }

        /**
         * Lista consultas en un rango de fechas
         */
        public List<ConsultaMedica> listarConsultasPorFecha(LocalDate fechaInicio, LocalDate fechaFin) {
            if (fechaInicio == null || fechaFin == null) {
                throw new IllegalArgumentException("Las fechas no pueden ser nulas");
            }
            if (fechaInicio.isAfter(fechaFin)) {
                throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la fecha de fin");
            }
            return consultaMedicaDAO.listarConsultasPorFecha(fechaInicio, fechaFin);
        }

        /**
         * Lista consultas relacionadas con una cita
         */
        public List<ConsultaMedica> listarConsultasPorCita(int citaId) {
            validarIdPositivo(citaId, "cita");
            return consultaMedicaDAO.listarConsultasPorCita(citaId);
        }

        /**
         * Actualiza una consulta médica existente
         */
        public boolean actualizarConsultaMedica(ConsultaMedica consultaMedica) {
            validarIdPositivo(consultaMedica.getId(), "consulta médica");
            validarDatosConsulta(consultaMedica);
            validarRelaciones(consultaMedica);
            normalizarCamposOpcionales(consultaMedica);

            return consultaMedicaDAO.modificarConsultaMedica(consultaMedica);
        }

        /**
         * Obtiene estadísticas de consultas por mascota
         */
        public EstadisticasConsulta obtenerEstadisticasPorMascota(int mascotaId) {
            List<ConsultaMedica> consultas = listarConsultasPorMascota(mascotaId);

            double pesoPromedio = consultas.stream()
                    .mapToDouble(ConsultaMedica::getPeso_registrado)
                    .filter(peso -> peso > 0)
                    .average()
                    .orElse(0.0);

            double temperaturaPromedio = consultas.stream()
                    .mapToDouble(ConsultaMedica::getTemperatura)
                    .filter(temp -> temp > 0)
                    .average()
                    .orElse(0.0);

            return new EstadisticasConsulta(
                    consultas.size(),
                    pesoPromedio,
                    temperaturaPromedio
            );
        }

        // ==================== MÉTODOS PRIVADOS DE VALIDACIÓN ====================

        private void validarDatosConsulta(ConsultaMedica consulta) {
            if (consulta.getMotivo() == null || consulta.getMotivo().isBlank()) {
                throw new IllegalArgumentException("El motivo de la consulta no puede estar vacío");
            }

            if (consulta.getFecha_hora() != null && consulta.getFecha_hora().isAfter(LocalDateTime.now())) {
                throw new IllegalArgumentException("La fecha de la consulta no puede ser futura");
            }

            if (consulta.getPeso_registrado() < 0) {
                throw new IllegalArgumentException("El peso registrado no puede ser negativo");
            }

            if (consulta.getTemperatura() < 0 || consulta.getTemperatura() > 50) {
                throw new IllegalArgumentException("La temperatura debe estar en un rango válido (0-50°C)");
            }
        }

        private void validarRelaciones(ConsultaMedica consulta) {
            validarMascotaExiste(consulta.getMascota_id());
            validarVeterinarioExiste(consulta.getVeterinario_id());
        }

        private void validarMascotaExiste(int mascotaId) {
            if (mascotaDAO.buscarMascotaPorId(mascotaId) == null) {
                throw new IllegalArgumentException(
                        String.format("La mascota especificada no existe (ID: %d)", mascotaId)
                );
            }
        }

        private void validarVeterinarioExiste(int veterinarioId) {
            if (veterinarioDAO.buscarVeterinarioPorId(veterinarioId) == null) {
                throw new IllegalArgumentException(
                        String.format("El veterinario especificado no existe (ID: %d)", veterinarioId)
                );
            }
        }

        private void validarIdPositivo(int id, String entidad) {
            if (id <= 0) {
                throw new IllegalArgumentException(
                        String.format("ID de %s inválido", entidad)
                );
            }
        }

        private void normalizarCamposOpcionales(ConsultaMedica consulta) {
            if (consulta.getSintomas() != null && consulta.getSintomas().isBlank()) {
                consulta.setSintomas(null);
            }
            if (consulta.getDiagnostico() != null && consulta.getDiagnostico().isBlank()) {
                consulta.setDiagnostico(null);
            }
            if (consulta.getRecomendaciones() != null && consulta.getRecomendaciones().isBlank()) {
                consulta.setRecomendaciones(null);
            }
            if (consulta.getObservaciones() != null && consulta.getObservaciones().isBlank()) {
                consulta.setObservaciones(null);
            }
        }

        // ==================== CLASE INTERNA PARA ESTADÍSTICAS ====================

        /**
         * Clase interna para encapsular estadísticas de consultas
         * Aplica el patrón Value Object
         */
        public static class EstadisticasConsulta {
            private final int totalConsultas;
            private final double pesoPromedio;
            private final double temperaturaPromedio;

            public EstadisticasConsulta(int totalConsultas, double pesoPromedio, double temperaturaPromedio) {
                this.totalConsultas = totalConsultas;
                this.pesoPromedio = pesoPromedio;
                this.temperaturaPromedio = temperaturaPromedio;
            }

            public int getTotalConsultas() {
                return totalConsultas;
            }

            public double getPesoPromedio() {
                return pesoPromedio;
            }

            public double getTemperaturaPromedio() {
                return temperaturaPromedio;
            }

            @Override
            public String toString() {
                return String.format(
                        "Total de consultas: %d%nPeso promedio: %.2f kg%nTemperatura promedio: %.2f°C",
                        totalConsultas, pesoPromedio, temperaturaPromedio
                );
            }
        }
}
