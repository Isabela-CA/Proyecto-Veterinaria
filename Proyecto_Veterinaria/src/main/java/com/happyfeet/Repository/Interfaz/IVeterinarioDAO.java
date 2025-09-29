package com.happyfeet.Repository.Interfaz;

import com.happyfeet.model.entities.Veterinario;
import com.happyfeet.model.enums.EspecialidadVeterinario;

import java.util.List;

public interface IVeterinarioDAO {
    List<Veterinario> listarVeterinario();
    Veterinario buscarVeterinarioPorId(int id);
    boolean agregarVeterinario(Veterinario veterinario);
    boolean modificarVeterinario(Veterinario veterinario);
    List<Veterinario> listarVeterinariosActivos();
    List<Veterinario> listarVeterinariosPorEspecialidad(EspecialidadVeterinario.Especialidad especialidad);

}
