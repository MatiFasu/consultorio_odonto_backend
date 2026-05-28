
package com.ConsultorioOdontologico.consultorioOdontologico.service;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.PacienteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;


public interface IPacienteService {
    
    public List<PacienteDTO> getPacientes();

    public Page<PacienteDTO> getPacientesPaginated(Pageable pageable);
    
    public PacienteDTO savePaciente(PacienteDTO pac);
    
    public void deletePaciente(Long id);
    
    public PacienteDTO findPaciente(Long id);
    
    public void editPaciente(PacienteDTO p);
    
}
