package com.ConsultorioOdontologico.consultorioOdontologico.mapper;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.ItemPresupuestoDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.model.ItemPresupuesto;
import org.springframework.stereotype.Component;

@Component
public class ItemPresupuestoMapper {

    public ItemPresupuestoDTO toDTO(ItemPresupuesto entity) {
        if (entity == null) return null;
        ItemPresupuestoDTO dto = new ItemPresupuestoDTO();
        dto.setId(entity.getId());
        dto.setDescripcion(entity.getDescripcion());
        dto.setCosto(entity.getCosto());
        return dto;
    }

    public ItemPresupuesto toEntity(ItemPresupuestoDTO dto) {
        if (dto == null) return null;
        ItemPresupuesto entity = new ItemPresupuesto();
        entity.setId(dto.getId());
        entity.setDescripcion(dto.getDescripcion());
        entity.setCosto(dto.getCosto());
        return entity;
    }
}
