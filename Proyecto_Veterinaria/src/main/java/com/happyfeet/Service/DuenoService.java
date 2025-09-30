package com.happyfeet.Service;

import com.happyfeet.Repository.DuenosDAO;
import com.happyfeet.Repository.Interfaz.IDuenosDAO;
import com.happyfeet.model.entities.Duenos;

import java.util.List;
// Service Layer
public class DuenoService {
    private IDuenosDAO duenosDAO;

    public DuenoService() {
        this.duenosDAO = new DuenosDAO();
    }

    // Constructor para inyección de d
    // ependencias (útil para testing)
    public DuenoService(IDuenosDAO duenosDAO) {
        this.duenosDAO = duenosDAO;
    }

    /**
     * Agrega un nuevo dueño con validaciones
     */
    public boolean agregarDuenos(Duenos dueno) {
        // Validaciones básicas
        if (dueno == null) {
            throw new IllegalArgumentException("El dueño no puede ser null");
        }

        if (dueno.getNombre_completo() == null || dueno.getNombre_completo().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre completo del dueño es obligatorio");
        }

        if (dueno.getDocumento_identidad() == null || dueno.getDocumento_identidad().trim().isEmpty()) {
            throw new IllegalArgumentException("El documento de identidad es obligatorio");
        }

        // Validación de email si existe
        if (dueno.getEmail() != null && !dueno.getEmail().trim().isEmpty()) {
                throw new IllegalArgumentException("El formato del email no es válido");
        }

        return duenosDAO.agregarDuenos(dueno);
    }

    /**
     * Actualiza un dueño existente
     */
    public boolean actualizarDuenos(Duenos dueno) {
        if (dueno == null) {
            throw new IllegalArgumentException("El dueño no puede ser null");
        }

        if (dueno.getId() <= 0) {
            throw new IllegalArgumentException("ID de dueño inválido");
        }

        // Validar que el dueño existe
        Duenos duenoExistente = duenosDAO.buscarDuenosPorId(dueno.getId());
        if (duenoExistente == null) {
            throw new IllegalArgumentException("No existe un dueño con el ID: " + dueno.getId());
        }

        return duenosDAO.modificarDuenos(dueno);
    }

    /**
     * Busca un dueño por su ID
     */
    public Duenos buscarDuenosPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID de dueño inválido");
        }

        return duenosDAO.buscarDuenosPorId(id);
    }

    /**
     * Lista todos los dueños
     */
    public List<Duenos> listarTodosDuenos() {
        return duenosDAO.listarDuenos();
    }
}