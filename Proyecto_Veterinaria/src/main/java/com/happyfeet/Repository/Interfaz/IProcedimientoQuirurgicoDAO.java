package com.happyfeet.Repository.Interfaz;

import com.happyfeet.model.entities.ProcedimientoQuirurgico;
import java.util.List;

public interface IProcedimientoQuirurgicoDAO {
    List<ProcedimientoQuirurgico> listarProcedimientosQuirurgicos();
    boolean buscarProcedimientoQuirurgico(ProcedimientoQuirurgico procedimiento);
    boolean agregarProcedimientoQuirurgico(ProcedimientoQuirurgico procedimiento);
    boolean modificarProcedimientoQuirurgico(ProcedimientoQuirurgico procedimiento);
    boolean eliminarProcedimientoQuirurgico(ProcedimientoQuirurgico procedimiento);
}
