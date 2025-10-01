package com.happyfeet.Controller;

import com.happyfeet.Repository.DuenosDAO;
import com.happyfeet.model.entities.Duenos;

import java.util.List;

    public class DuenosController {
        private final DuenosDAO duenosDAO;

        public DuenosController() {
            this.duenosDAO = new DuenosDAO();
        }

        public boolean agregarDuenos(Duenos dueno) {
            try {
                if (dueno == null) {
                    System.err.println("El dueño no puede ser null");
                    return false;
                }

                if (dueno.getNombre_completo() == null || dueno.getNombre_completo().trim().isEmpty()) {
                    System.err.println("El nombre completo es obligatorio");
                    return false;
                }

                if (dueno.getDocumento_identidad() == null || dueno.getDocumento_identidad().trim().isEmpty()) {
                    System.err.println("El documento de identidad es obligatorio");
                    return false;
                }

                return duenosDAO.agregarDuenos(dueno);
            } catch (Exception e) {
                System.err.println("Error en controller al agregar dueño: " + e.getMessage());
                return false;
            }
        }

        public Duenos obtenerDueno(int id) {
            try {
                if (id <= 0) {
                    System.err.println("ID de dueño inválido");
                    return null;
                }
                return duenosDAO.buscarDuenosPorId(id);
            } catch (Exception e) {
                System.err.println("Error al obtener dueño: " + e.getMessage());
                return null;
            }
        }

        public List<Duenos> listarDuenos() {
            try {
                return duenosDAO.listarDuenos();
            } catch (Exception e) {
                System.err.println("Error al listar dueños: " + e.getMessage());
                return List.of();
            }
        }

        public boolean actualizarDueno(Duenos dueno) {
            try {
                if (dueno == null) {
                    System.err.println("El dueño no puede ser null");
                    return false;
                }

                if (dueno.getId() <= 0) {
                    System.err.println("ID de dueño inválido");
                    return false;
                }

                return duenosDAO.modificarDuenos(dueno);
            } catch (Exception e) {
                System.err.println("Error en controller al actualizar dueño: " + e.getMessage());
                return false;
            }
        }
    }