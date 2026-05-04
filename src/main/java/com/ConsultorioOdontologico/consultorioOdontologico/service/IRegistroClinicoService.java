
package com.ConsultorioOdontologico.consultorioOdontologico.service;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.RegistroClinicoDTO;
import java.util.List;

public interface IRegistroClinicoService {
    public List<RegistroClinicoDTO> getHistorialPorPaciente(Long pacienteId);
    public RegistroClinicoDTO saveRegistro(RegistroClinicoDTO registro);
    public void deleteRegistro(Long id);
    public RegistroClinicoDTO findRegistro(Long id);
}
