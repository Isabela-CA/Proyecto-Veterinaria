package com.happyfeet.Repository.Interfaz;

import com.happyfeet.model.entities.Cita;
import com.happyfeet.model.enums.EstadoCita;

import java.time.LocalDate;
import java.util.List;

public interface ICitaDAO {
        List<Cita> listarCitas();
        Cita buscarCitaPorId(int id);
        boolean agregarCita(Cita cita);
        boolean modificarCita(Cita cita);
        boolean buscarCita(Cita cita);
        List<Cita> listarCitasPorMascota(int mascotaId);
        List<Cita> listarCitasPorVeterinario(int veterinarioId);
        List<Cita> listarCitasPorFecha(LocalDate fecha);
    }
