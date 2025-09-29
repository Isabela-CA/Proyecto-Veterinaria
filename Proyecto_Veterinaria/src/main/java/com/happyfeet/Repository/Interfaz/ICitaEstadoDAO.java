package com.happyfeet.Repository.Interfaz;

import com.happyfeet.model.entities.CitaEstado;

import java.util.List;

public interface ICitaEstadoDAO {
        List<CitaEstado> listarCitaEstados();
        boolean buscarCitaEstado(CitaEstado citaEstado);
        boolean agregarCitaEstado(CitaEstado citaEstado);
        boolean modificarCitaEstado(CitaEstado citaEstado);
    }

