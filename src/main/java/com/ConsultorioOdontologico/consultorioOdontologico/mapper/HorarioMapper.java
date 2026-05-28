package com.ConsultorioOdontologico.consultorioOdontologico.mapper;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.HorarioDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Horario;
import org.springframework.stereotype.Component;

@Component
public class HorarioMapper {

    public HorarioDTO toDTO(Horario horario) {
        if (horario == null) return null;
        HorarioDTO dto = new HorarioDTO();
        dto.setId(horario.getId());
        dto.setHorario_inicio(horario.getHorario_inicio());
        dto.setHorario_final(horario.getHorario_final());
        return dto;
    }

    public Horario toEntity(HorarioDTO dto) {
        if (dto == null) return null;
        Horario horario = new Horario();
        horario.setId(dto.getId());
        horario.setHorario_inicio(dto.getHorario_inicio());
        horario.setHorario_final(dto.getHorario_final());
        return horario;
    }

    public void updateEntityFromDTO(HorarioDTO dto, Horario entity) {
        if (dto == null || entity == null) return;

        entity.setHorario_inicio(dto.getHorario_inicio());
        entity.setHorario_final(dto.getHorario_final());
    }
}
