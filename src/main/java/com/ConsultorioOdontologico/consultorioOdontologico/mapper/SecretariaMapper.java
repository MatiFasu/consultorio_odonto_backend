package com.ConsultorioOdontologico.consultorioOdontologico.mapper;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.SecretariaDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Secretaria;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class SecretariaMapper {

    public SecretariaDTO toDTO(Secretaria entity) {
        if (entity == null) return null;

        SecretariaDTO dto = new SecretariaDTO();
        dto.setId(entity.getId());
        dto.setDni(entity.getDni());
        dto.setNombre(entity.getNombre());
        dto.setApellido(entity.getApellido());
        dto.setTelefono(entity.getTelefono());
        dto.setDireccion(entity.getDireccion());
        dto.setFecha_nac(entity.getFecha_nac());
        dto.setSector(entity.getSector());

        if (entity.getUnUsuario() != null) {
            dto.setIdUsuario(entity.getUnUsuario().getId());
            dto.setNombreUsuario(entity.getUnUsuario().getUsuario());
        }

        return dto;
    }

    public Secretaria toEntity(SecretariaDTO dto, Usuario usuario) {
        if (dto == null) return null;

        Secretaria entity = new Secretaria();
        entity.setId(dto.getId());
        entity.setDni(dto.getDni());
        entity.setNombre(dto.getNombre());
        entity.setApellido(dto.getApellido());
        entity.setTelefono(dto.getTelefono());
        entity.setDireccion(dto.getDireccion());
        entity.setFecha_nac(dto.getFecha_nac());
        entity.setSector(dto.getSector());
        entity.setUnUsuario(usuario);

        return entity;
    }

    public void updateEntityFromDTO(SecretariaDTO dto, Secretaria entity, Usuario usuario) {
        if (dto == null || entity == null) return;

        entity.setNombre(dto.getNombre());
        entity.setApellido(dto.getApellido());
        entity.setDni(dto.getDni());
        entity.setTelefono(dto.getTelefono());
        entity.setDireccion(dto.getDireccion());
        entity.setFecha_nac(dto.getFecha_nac());
        entity.setSector(dto.getSector());
        entity.setUnUsuario(usuario);
    }
}
