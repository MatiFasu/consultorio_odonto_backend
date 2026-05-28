package com.ConsultorioOdontologico.consultorioOdontologico.mapper;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.ResponsableDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Responsable;
import org.springframework.stereotype.Component;

@Component
public class ResponsableMapper {

    public ResponsableDTO toDTO(Responsable responsable) {
        if (responsable == null) return null;

        ResponsableDTO dto = new ResponsableDTO();
        dto.setId(responsable.getId());
        dto.setDni(responsable.getDni());
        dto.setNombre(responsable.getNombre());
        dto.setApellido(responsable.getApellido());
        dto.setTelefono(responsable.getTelefono());
        dto.setDireccion(responsable.getDireccion());
        dto.setFecha_nac(responsable.getFecha_nac());
        dto.setTipoResponsabilidad(responsable.getTipoResponsabilidad());
        return dto;
    }

    public Responsable toEntity(ResponsableDTO dto) {
        if (dto == null) return null;

        Responsable responsable = new Responsable();
        responsable.setId(dto.getId());
        responsable.setDni(dto.getDni());
        responsable.setNombre(dto.getNombre());
        responsable.setApellido(dto.getApellido());
        responsable.setTelefono(dto.getTelefono());
        responsable.setDireccion(dto.getDireccion());
        responsable.setFecha_nac(dto.getFecha_nac());
        responsable.setTipoResponsabilidad(dto.getTipoResponsabilidad());
        return responsable;
    }

    public void updateEntityFromDTO(ResponsableDTO dto, Responsable entity) {
        if (dto == null || entity == null) return;

        entity.setNombre(dto.getNombre());
        entity.setApellido(dto.getApellido());
        entity.setDni(dto.getDni());
        entity.setTelefono(dto.getTelefono());
        entity.setDireccion(dto.getDireccion());
        entity.setFecha_nac(dto.getFecha_nac());
        entity.setTipoResponsabilidad(dto.getTipoResponsabilidad());
    }
}
