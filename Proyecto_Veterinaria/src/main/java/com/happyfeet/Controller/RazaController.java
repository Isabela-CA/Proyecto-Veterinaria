package com.happyfeet.Controller;

import com.happyfeet.Service.RazaService;
import com.happyfeet.model.entities.Raza;

import java.util.List;

public class RazaController {
    private RazaService razaService;

    public RazaController() {
        this.razaService = new RazaService();
    }

    public RazaController(RazaService razaService) {
        this.razaService = razaService;
    }

    public List<Raza> listarTodasLasRazas() {
        try {
            return razaService.listarTodasLasRazas();
        } catch (Exception e) {
            System.err.println("Error en controller al listar razas: " + e.getMessage());
            return List.of();
        }
    }

    public List<Raza> listarRazasPorEspecie(int especieId) {
        try {
            return razaService.listarRazasPorEspecie(especieId);
        } catch (IllegalArgumentException e) {
            System.err.println("Error de validación: " + e.getMessage());
            return List.of();
        } catch (Exception e) {
            System.err.println("Error en controller al listar razas por especie: " + e.getMessage());
            return List.of();
        }
    }

    public Raza buscarRazaPorId(int id) {
        try {
            return razaService.buscarRazaPorId(id);
        } catch (IllegalArgumentException e) {
            System.err.println("Error de validación: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("Error en controller al buscar raza: " + e.getMessage());
            return null;
        }
    }

    public boolean agregarRaza(Raza raza) {
        try {
            return razaService.agregarRaza(raza);
        } catch (IllegalArgumentException e) {
            System.err.println("Error de validación: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Error en controller al agregar raza: " + e.getMessage());
            return false;
        }
    }

}