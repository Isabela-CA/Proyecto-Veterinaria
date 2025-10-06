package com.happyfeet.Service;

import com.happyfeet.Repository.Interfaz.IVeterinarioDAO;
import com.happyfeet.model.entities.Veterinario;
import java.util.List;

// SOLUCIONADO: Se eliminó la implementación de IVeterinarioDAO
public class VeterinarioService {

    // Inyección de dependencia (DAO)
    private final IVeterinarioDAO veterinarioDAO;

    public VeterinarioService(IVeterinarioDAO veterinarioDAO) {
        this.veterinarioDAO = veterinarioDAO;
    }

    public boolean agregar(Veterinario veterinario) {
        // Ejemplo de lógica de negocio: Validar que el email no sea nulo antes de agregar
        if (veterinario.getEmail() == null || veterinario.getEmail().trim().isEmpty()) {
            System.err.println("Error de Servicio: El correo electrónico del veterinario es obligatorio.");
            return false;
        }
        return veterinarioDAO.agregarVeterinario(veterinario);
    }

    public Veterinario buscarPorId(int id) {
        return veterinarioDAO.buscarVeterinarioPorId(id);
    }

    public boolean modificar(Veterinario veterinario) {
        // Lógica de negocio: Validar que el registro exista antes de modificar
        if (veterinarioDAO.buscarVeterinarioPorId(veterinario.getId()) == null) {
            System.err.println("Error de Servicio: No se puede modificar, el veterinario con ID " + veterinario.getId() + " no existe.");
            return false;
        }
        return veterinarioDAO.modificarVeterinario(veterinario);
    }

    public List<Veterinario> listarTodos() {
        return veterinarioDAO.listarVeterinario();
    }

    public List<Veterinario> listarActivos() {
        return veterinarioDAO.listarVeterinariosActivos();
    }

    public List<Veterinario> listarPorEspecialidad(String especialidad) {
        // Lógica de negocio: Estandarizar la especialidad antes de la búsqueda
        return veterinarioDAO.listarVeterinariosPorEspecialidad(especialidad.trim());
    }

    public boolean cambiarEstadoActivo(int id, boolean activo) {
        Veterinario v = veterinarioDAO.buscarVeterinarioPorId(id);

        if (v == null) {
            System.err.println("Error de Servicio: No se encontró el veterinario para cambiar el estado.");
            return false;
        }

        // Se establece el nuevo estado
        v.setActivo(activo ? 1 : 0);

        return veterinarioDAO.modificarVeterinario(v);
    }
}