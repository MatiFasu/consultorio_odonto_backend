
package com.ConsultorioOdontologico.consultorioOdontologico.service;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.PacienteDTO;
import java.util.List;


public interface IPacienteService {
    
    public List<PacienteDTO> getPacientes();
    
    public void savePaciente(PacienteDTO p);
    
    public void deletePaciente(Long id);
    
    public PacienteDTO findPaciente(Long id);
    
    public void editPaciente(PacienteDTO p);
    
}
