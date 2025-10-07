/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.happyfeet.test;

import com.happyfeet.model.entities.HistorialClinico;
import java.util.List;

/**
 *
 * @author camper
 */
public interface IHistorial {
        List<HistorialClinico> listarHistorialPorMascota(int mascotaId);  
            List<HistorialClinico> mostrarResumenHistorial((Historial historial); 

}
