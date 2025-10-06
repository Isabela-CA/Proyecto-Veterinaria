package com.happyfeet.Repository.Interfaz;

import com.happyfeet.model.entities.Veterinario;

import java.util.List;

public interface IVeterinarioDAO {

    // Cambiado el tipo de retorno de boolean a Veterinario
    Veterinario buscarVeterinarioPorId(int id);

    // Cambiado el nombre del método y el argumento para ser consistente con VeterinarioDAO.java
    boolean agregarVeterinario(Veterinario veterinario);

    // Método corregido
    boolean modificarVeterinario(Veterinario veterinario);

    List<Veterinario> listarVeterinario();

    List<Veterinario> listarVeterinariosActivos();

    // Corregido el nombre del parámetro
    List<Veterinario> listarVeterinariosPorEspecialidad(String especialidad);

    // Se eliminan los métodos boolean modificar(), boolean agregar(IVeterinarioDAO), etc.

}