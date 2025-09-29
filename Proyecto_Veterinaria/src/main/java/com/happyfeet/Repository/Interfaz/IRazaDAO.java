package com.happyfeet.Repository.Interfaz;

import com.happyfeet.model.entities.Raza;

import java.util.List;

public interface IRazaDAO {
    List<Raza> listarRaza();
    boolean buscarRaza(Raza raza);
    boolean agregarRaza(Raza raza);
    boolean modificarRaza(Raza raza);
    List<Raza> listarRazasPorEspecie(int especieId);
}
