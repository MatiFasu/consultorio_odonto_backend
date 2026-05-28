package com.ConsultorioOdontologico.consultorioOdontologico.mapper;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.PresupuestoDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Presupuesto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PresupuestoMapper {

    private final ItemPresupuestoMapper itemMapper;

    public PresupuestoDTO toDTO(Presupuesto entity) {
        if (entity == null) return null;
        
        PresupuestoDTO dto = new PresupuestoDTO();
        dto.setId(entity.getId());
        dto.setFecha(entity.getFecha());
        dto.setEstado(entity.getEstado());
        dto.setTotal(entity.getTotal());
        
        if (entity.getPaciente() != null) {
            dto.setIdPaciente(entity.getPaciente().getId());
            dto.setNombrePaciente(entity.getPaciente().getNombre() + " " + entity.getPaciente().getApellido());
        }
        
        if (entity.getItems() != null) {
            dto.setItems(entity.getItems().stream()
                    .map(itemMapper::toDTO)
                    .collect(Collectors.toList()));
        }
        
        return dto;
    }

    public Presupuesto toEntity(PresupuestoDTO dto) {
        if (dto == null) return null;
        
        Presupuesto entity = new Presupuesto();
        entity.setId(dto.getId());
        entity.setFecha(dto.getFecha());
        entity.setEstado(dto.getEstado() != null ? dto.getEstado() : "PENDIENTE");
        entity.setTotal(dto.getTotal());
        
        if (dto.getItems() != null) {
            entity.setItems(dto.getItems().stream()
                    .map(i -> {
                        var item = itemMapper.toEntity(i);
                        item.setPresupuesto(entity);
                        return item;
                    })
                    .collect(Collectors.toList()));
        } else {
            entity.setItems(new ArrayList<>());
        }
        
        return entity;
    }
}
