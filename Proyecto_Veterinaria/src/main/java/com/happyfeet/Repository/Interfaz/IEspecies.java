package com.happyfeet.Repository.Interfaz;

import com.happyfeet.model.entities.Especies;
import com.happyfeet.model.entities.Mascota;

import java.util.List;

public interface IEspecies {
    List<Especies> listarEspecies();

    boolean buscarEspecies(Especies especies);
    boolean agregarEspecies(Especies especies);
    boolean modificarEspecies(Especies especies);

}
