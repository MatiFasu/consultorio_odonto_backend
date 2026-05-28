package com.ConsultorioOdontologico.consultorioOdontologico.mapper;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.TurnoDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Turno;
import org.springframework.stereotype.Component;

@Component
public class TurnoMapper {

    public TurnoDTO toDTO(Turno turno) {
        if (turno == null) return null;

        TurnoDTO dto = new TurnoDTO();
        dto.setId(turno.getId());
        dto.setFecha_turno(turno.getFecha_turno());
        dto.setHora_turno(turno.getHora_turno());
        dto.setAfeccion(turno.getAfeccion());

        if (turno.getPacien() != null) {
            dto.setIdPaciente(turno.getPacien().getId());
            dto.setNombrePaciente(turno.getPacien().getNombre() + " " + turno.getPacien().getApellido());
            dto.setTelefonoPaciente(turno.getPacien().getTelefono());
        }

        if (turno.getOdonto() != null) {
            dto.setIdOdontologo(turno.getOdonto().getId());
            dto.setNombreOdontologo(turno.getOdonto().getNombre() + " " + turno.getOdonto().getApellido());
            dto.setTelefonoOdontologo(turno.getOdonto().getTelefono());
        }

        return dto;
    }

    public Turno toEntity(TurnoDTO dto) {
        if (dto == null) return null;

        Turno turno = new Turno();
        turno.setId(dto.getId());
        turno.setFecha_turno(dto.getFecha_turno());
        turno.setHora_turno(dto.getHora_turno());
        turno.setAfeccion(dto.getAfeccion());
        return turno;
    }

    public void updateEntityFromDTO(TurnoDTO dto, Turno entity) {
        if (dto == null || entity == null) return;

        entity.setFecha_turno(dto.getFecha_turno());
        entity.setHora_turno(dto.getHora_turno());
        entity.setAfeccion(dto.getAfeccion());
    }
}
