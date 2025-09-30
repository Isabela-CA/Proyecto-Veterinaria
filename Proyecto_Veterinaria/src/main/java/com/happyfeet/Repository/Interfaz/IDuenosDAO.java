package com.happyfeet.Repository.Interfaz;

import com.happyfeet.model.entities.Duenos;

import java.util.List;
// Repository
public interface IDuenosDAO  {
    List<Duenos> listarDuenos();
    Duenos buscarDuenosPorId(int id);
    boolean agregarDuenos(Duenos duenos);
    boolean modificarDuenos(Duenos duenos);
}
