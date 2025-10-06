package com.happyfeet.Controller;

import com.happyfeet.Service.ConsultaMedicaService;
import com.happyfeet.model.entities.ConsultaMedica;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public class ConsultaMedicaController {

        private final ConsultaMedicaService consultaMedicaService;

        /**
         * Constructor con inyección de dependencias (Patrón Dependency Injection)
         */
        public ConsultaMedicaController(ConsultaMedicaService consultaMedicaService) {
            this.consultaMedicaService = consultaMedicaService;
        }

        /**
         * Constructor sin parámetros (crea su propia instancia del servicio)
         */
        public ConsultaMedicaController() {
            this.consultaMedicaService = new ConsultaMedicaService();
        }

        /**
         * Agrega una nueva consulta médica con manejo de errores
         */
        public boolean agregarConsultaMedica(ConsultaMedica consultaMedica) {
            try {
                return consultaMedicaService.agregarConsultaMedica(consultaMedica);
            } catch (IllegalArgumentException e) {
                System.err.println("Error de validación: " + e.getMessage());
                return false;
            } catch (Exception e) {
                System.err.println("Error en controller al agregar consulta: " + e.getMessage());
                return false;
            }
        }

        /**
         * Obtiene una consulta médica por su ID
         */
        public ConsultaMedica obtenerConsultaMedica(int id) {
            try {
                return consultaMedicaService.obtenerConsultaMedica(id);
            } catch (Exception e) {
                System.err.println("Error al obtener consulta: " + e.getMessage());
                return null;
            }
        }

        /**
         * Lista todas las consultas médicas registradas
         */
        public List<ConsultaMedica> listarConsultasMedicas() {
            try {
                return consultaMedicaService.listarConsultasMedicas();
            } catch (Exception e) {
                System.err.println("Error al listar consultas: " + e.getMessage());
                return List.of();
            }
        }

        /**
         * Actualiza una consulta médica existente
         */
        public boolean actualizarConsultaMedica(ConsultaMedica consultaMedica) {
            try {
                return consultaMedicaService.actualizarConsultaMedica(consultaMedica);
            } catch (IllegalArgumentException e) {
                System.err.println("Error de validación: " + e.getMessage());
                return false;
            } catch (Exception e) {
                System.err.println("Error en controller al actualizar consulta: " + e.getMessage());
                return false;
            }
        }

        /**
         * Lista consultas de una mascota específica
         */
        public List<ConsultaMedica> listarConsultasPorMascota(int mascotaId) {
            try {
                return consultaMedicaService.listarConsultasPorMascota(mascotaId);
            } catch (Exception e) {
                System.err.println("Error al listar consultas por mascota: " + e.getMessage());
                return List.of();
            }
        }

        /**
         * Lista consultas de un veterinario específico
         */
        public List<ConsultaMedica> listarConsultasPorVeterinario(int veterinarioId) {
            try {
                return consultaMedicaService.listarConsultasPorVeterinario(veterinarioId);
            } catch (Exception e) {
                System.err.println("Error al listar consultas por veterinario: " + e.getMessage());
                return List.of();
            }
        }

        /**
         * Lista consultas en un rango de fechas
         */
        public List<ConsultaMedica> listarConsultasPorFecha(LocalDate fechaInicio, LocalDate fechaFin) {
            try {
                return consultaMedicaService.listarConsultasPorFecha(fechaInicio, fechaFin);
            } catch (Exception e) {
                System.err.println("Error al listar consultas por fecha: " + e.getMessage());
                return List.of();
            }
        }

        /**
         * Lista consultas relacionadas con una cita
         */
        public List<ConsultaMedica> listarConsultasPorCita(int citaId) {
            try {
                return consultaMedicaService.listarConsultasPorCita(citaId);
            } catch (Exception e) {
                System.err.println("Error al listar consultas por cita: " + e.getMessage());
                return List.of();
            }
        }

        /**
         * Obtiene estadísticas de consultas por mascota
         */
        public ConsultaMedicaService.EstadisticasConsulta obtenerEstadisticasPorMascota(int mascotaId) {
            try {
                return consultaMedicaService.obtenerEstadisticasPorMascota(mascotaId);
            } catch (Exception e) {
                System.err.println("Error al obtener estadísticas: " + e.getMessage());
                return null;
            }
        }
}
