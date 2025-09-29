package com.happyfeet.Repository.Interfaz;

import com.happyfeet.model.entities.Inventario;

import java.util.List;

public interface IInventarioDAO {
    List<Inventario> listarInventario();
    boolean buscarInventario(Inventario inventario);
    boolean agregarInventario(Inventario inventario);
    boolean modificarInventario(Inventario inventario);
}
