
package com.ConsultorioOdontologico.consultorioOdontologico.service;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.TurnoDTO;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ITurnoService {
 
    public List<TurnoDTO> getTurnos();

    public Page<TurnoDTO> getTurnosPaginated(Pageable pageable);

    public Page<TurnoDTO> getTurnosByFechaPaginated(java.time.LocalDate fecha, Pageable pageable);
    
    public void saveTurno(TurnoDTO t);
    
    public void deleteTurno(Long id);
    
    public TurnoDTO findTurno(Long id);
    
    public void editTurno(TurnoDTO t);
    
    public List<TurnoDTO> getTurnosByOdontologo(Long odontoId);
    
    public List<TurnoDTO> getProximosTurnosByOdontologo(Long odontoId);
    
}
