
package com.ConsultorioOdontologico.consultorioOdontologico.service;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.TurnoDTO;
import java.util.List;


public interface ITurnoService {
 
    public List<TurnoDTO> getTurnos();
    
    public void saveTurno(TurnoDTO t);
    
    public void deleteTurno(Long id);
    
    public TurnoDTO findTurno(Long id);
    
    public void editTurno(TurnoDTO t);
    
    public List<TurnoDTO> getTurnosByOdontologo(Long odontoId);
    
    public List<TurnoDTO> getProximosTurnosByOdontologo(Long odontoId);
    
}
