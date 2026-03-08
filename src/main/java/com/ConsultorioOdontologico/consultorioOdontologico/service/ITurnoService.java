
package com.ConsultorioOdontologico.consultorioOdontologico.service;

import com.ConsultorioOdontologico.consultorioOdontologico.model.Turno;
import java.time.LocalDate;
import java.util.List;


public interface ITurnoService {
 
    public List<Turno> getTurnos();
    
    public void saveTurno(Turno t);
    
    public void deleteTurno(Long id);
    
    public Turno findTurno(Long id);
    
    public void editTurno(Turno t);
    
    public List<Turno> getTurnosByOdontologo(Long odontoId);
    
    public List<Turno> getProximosTurnosByOdontologo(Long odontoId);
    
}
