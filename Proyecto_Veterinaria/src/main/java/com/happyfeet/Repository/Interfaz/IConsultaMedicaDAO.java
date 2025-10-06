package com.happyfeet.Repository.Interfaz;

import com.happyfeet.model.entities.ConsultaMedica;

import java.time.LocalDate;
import java.util.List;

public interface IConsultaMedicaDAO {
        List<ConsultaMedica> listarConsultaMedica();
        ConsultaMedica buscarConsultaMedicaPorId(int id);
        boolean agregarConsultaMedica(ConsultaMedica consultaMedica);
        boolean modificarConsultaMedica(ConsultaMedica consultaMedica);
        List<ConsultaMedica> listarConsultasPorMascota(int mascotaId);
        List<ConsultaMedica> listarConsultasPorVeterinario(int veterinarioId);
        List<ConsultaMedica> listarConsultasPorFecha(LocalDate fechaInicio, LocalDate fechaFin);
        List<ConsultaMedica> listarConsultasPorCita(int citaId);
    }

