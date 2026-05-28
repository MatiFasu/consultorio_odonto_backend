package com.ConsultorioOdontologico.consultorioOdontologico.service;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.RegistroClinicoDTO;
import java.util.List;

public interface IRegistroClinicoService {
    public List<RegistroClinicoDTO> getRegistros();
    public List<RegistroClinicoDTO> getRegistrosByPaciente(Long pacienteId);
    public void saveRegistro(RegistroClinicoDTO registro);
    public void deleteRegistro(Long id);
    public RegistroClinicoDTO findRegistro(Long id);
    public void editRegistro(RegistroClinicoDTO registro);
}
