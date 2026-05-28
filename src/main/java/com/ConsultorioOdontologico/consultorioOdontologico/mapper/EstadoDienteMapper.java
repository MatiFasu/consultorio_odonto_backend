package com.ConsultorioOdontologico.consultorioOdontologico.mapper;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.EstadoDienteDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.model.EstadoDiente;
import org.springframework.stereotype.Component;

@Component
public class EstadoDienteMapper {

    public EstadoDienteDTO toDTO(EstadoDiente entity) {
        if (entity == null) return null;
        EstadoDienteDTO dto = new EstadoDienteDTO();
        dto.setId(entity.getId());
        dto.setNumeroDiente(entity.getNumeroDiente());
        dto.setPosicion(entity.getPosicion());
        dto.setEstado(entity.getEstado());
        return dto;
    }

    public EstadoDiente toEntity(EstadoDienteDTO dto) {
        if (dto == null) return null;
        EstadoDiente entity = new EstadoDiente();
        entity.setId(dto.getId());
        entity.setNumeroDiente(dto.getNumeroDiente());
        entity.setPosicion(dto.getPosicion());
        entity.setEstado(dto.getEstado());
        return entity;
    }
}
