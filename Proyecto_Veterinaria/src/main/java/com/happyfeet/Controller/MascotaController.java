package com.happyfeet.Controller;

import com.happyfeet.Repository.DuenosDAO;
import com.happyfeet.Repository.MascotaDAO;
import com.happyfeet.Service.MascotaService;
import com.happyfeet.model.entities.Mascota;

import java.util.List;

public class MascotaController {
        private MascotaService mascotaService;

        public MascotaController() {
            // Inicializar el servicio con las dependencias necesarias
            this.mascotaService = new MascotaService(new MascotaDAO(), new DuenosDAO());
        }

        // Constructor para inyección de dependencias
        public MascotaController(MascotaService mascotaService) {
            this.mascotaService = mascotaService;
        }

        public boolean agregarMascota(Mascota mascota) {
            try {
                return mascotaService.agregarMascota(mascota);
            } catch (IllegalArgumentException e) {
                System.err.println("Error de validación: " + e.getMessage());
                return false;
            } catch (Exception e) {
                System.err.println("Error en controller al agregar mascota: " + e.getMessage());
                return false;
            }
        }

        public Mascota obtenerMascota(int id) {
            try {
                return mascotaService.obtenerMascota(id);
            } catch (Exception e) {
                System.err.println("Error en controller al obtener mascota: " + e.getMessage());
                return null;
            }
        }

        public List<Mascota> listarMascotas() {
            try {
                return mascotaService.listarMascotas();
            } catch (Exception e) {
                System.err.println("Error en controller al listar mascotas: " + e.getMessage());
                return List.of();
            }
        }

        public List<Mascota> listarMascotasPorDueno(int duenoId) {
            try {
                return mascotaService.listarMascotasPorDueno(duenoId);
            } catch (Exception e) {
                System.err.println("Error en controller al listar mascotas por dueño: " + e.getMessage());
                return List.of();
            }
        }

        public boolean actualizarMascota(Mascota mascota) {
            try {
                return mascotaService.actualizarMascota(mascota);
            } catch (IllegalArgumentException e) {
                System.err.println("Error de validación: " + e.getMessage());
                return false;
            } catch (Exception e) {
                System.err.println("Error en controller al actualizar mascota: " + e.getMessage());
                return false;
            }
        }

        public boolean transferirMascota(int mascotaId, int nuevoDuenoId) {
            try {
                return mascotaService.transferirMascota(mascotaId, nuevoDuenoId);
            } catch (IllegalArgumentException e) {
                System.err.println("Error de validación: " + e.getMessage());
                return false;
            } catch (Exception e) {
                System.err.println("Error en controller al transferir mascota: " + e.getMessage());
                return false;
            }
        }
    }
