
package com.ConsultorioOdontologico.consultorioOdontologico.service;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.HorarioDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Horario;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.IHorarioRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HorarioService implements IHorarioService {
    
    @Autowired
    private IHorarioRepository horarioRepo;

    @Override
    public List<HorarioDTO> getHorarios() {
        return horarioRepo.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public HorarioDTO saveHorario(HorarioDTO hDTO) {
        Horario h = convertToEntity(hDTO);
        if (h.getId_horario() != null && h.getId_horario() == 0) {
            h.setId_horario(null);
        }
        Horario hor = horarioRepo.save(h);
        return convertToDTO(hor);
    }

    @Override
    public void deleteHorario(Long id) {
        horarioRepo.deleteById(id);
    }

    @Override
    public HorarioDTO findHorario(Long id) {
        Horario h = horarioRepo.findById(id).orElse(null);
        return (h != null) ? convertToDTO(h) : null;
    }

    @Override
    public void editHorario(HorarioDTO hDTO) {
        this.saveHorario(hDTO);
    }

    private HorarioDTO convertToDTO(Horario h) {
        HorarioDTO dto = new HorarioDTO();
        dto.setId_horario(h.getId_horario());
        dto.setHorario_inicio(h.getHorario_inicio());
        dto.setHorario_final(h.getHorario_final());
        return dto;
    }

    private Horario convertToEntity(HorarioDTO dto) {
        Horario h = new Horario();
        h.setId_horario(dto.getId_horario());
        h.setHorario_inicio(dto.getHorario_inicio());
        h.setHorario_final(dto.getHorario_final());
        return h;
    }
}
