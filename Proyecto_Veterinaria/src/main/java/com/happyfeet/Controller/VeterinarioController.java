package com.happyfeet.Controller;


import com.happyfeet.Service.VeterinarioService;
import com.happyfeet.Repository.VeterinarioDAO;
import com.happyfeet.model.entities.Veterinario;

import java.util.List;

public class VeterinarioController {

    private final VeterinarioService veterinarioService;

    // Constructor con inyección de dependencias
    public VeterinarioController(VeterinarioService veterinarioService) {
        this.veterinarioService = veterinarioService;
    }

    // Constructor sin parámetros (crea su propia instancia)
    public VeterinarioController() {
        VeterinarioDAO veterinarioDAO = new VeterinarioDAO();
        this.veterinarioService = new VeterinarioService(veterinarioDAO);
    }

    public boolean registrar(Veterinario veterinario) {
        try {
            return veterinarioService.agregar(veterinario);
        } catch (Exception e) {
            System.err.println("Error en controller al agregar veterinario: " + e.getMessage());
            return false;
        }
    }

    public Veterinario buscarPorId(int id) {
        return veterinarioService.buscarPorId(id);
    }

    public List<Veterinario> listarTodos() {
        return veterinarioService.listarTodos();
    }

    public List<Veterinario> listarActivos() {
        return veterinarioService.listarActivos();
    }

    public boolean modificar(Veterinario veterinario) {
        try {
            return veterinarioService.modificar(veterinario);
        } catch (Exception e) {
            System.err.println("Error en controller al actualizar veterinario: " + e.getMessage());
            return false;
        }
    }

    public boolean cambiarEstadoActivo(int id, boolean activo) {
        try {
            return veterinarioService.cambiarEstadoActivo(id, activo);
        } catch (Exception e) {
            System.err.println("Error en controller al cambiar estado: " + e.getMessage());
            return false;
        }
    }
}