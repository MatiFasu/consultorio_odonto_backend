package com.ConsultorioOdontologico.consultorioOdontologico.service;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.HorarioDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.mapper.HorarioMapper;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Horario;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.IHorarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HorarioService implements IHorarioService {
    
    private final IHorarioRepository horarioRepo;
    private final HorarioMapper horarioMapper;

    @Override
    public List<HorarioDTO> getHorarios() {
        return horarioRepo.findAll().stream()
                .map(horarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public HorarioDTO saveHorario(HorarioDTO hDTO) {
        Horario h = horarioMapper.toEntity(hDTO);
        if (h.getId() != null && h.getId() == 0) {
            h.setId(null);
        }
        Horario hor = horarioRepo.save(h);
        return horarioMapper.toDTO(hor);
    }

    @Override
    public void deleteHorario(Long id) {
        horarioRepo.deleteById(id);
    }

    @Override
    public HorarioDTO findHorario(Long id) {
        return horarioRepo.findById(id)
                .map(horarioMapper::toDTO)
                .orElse(null);
    }

    @Override
    @Transactional
    public void editHorario(HorarioDTO hDTO) {
        if (hDTO == null || hDTO.getId() == null) return;

        horarioRepo.findById(hDTO.getId()).ifPresent(existing -> {
            horarioMapper.updateEntityFromDTO(hDTO, existing);
            horarioRepo.save(existing);
        });
    }
}
