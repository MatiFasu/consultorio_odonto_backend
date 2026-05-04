package com.ConsultorioOdontologico.consultorioOdontologico.service;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.HorarioDTO;
import java.util.List;

public interface IHorarioService {
    public List<HorarioDTO> getHorarios();
    public HorarioDTO saveHorario(HorarioDTO h);
    public void deleteHorario(Long id);
    public HorarioDTO findHorario(Long id);
    public void editHorario(HorarioDTO h);
}

