package com.ConsultorioOdontologico.consultorioOdontologico.mapper;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.UsuarioDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioDTO toDTO(Usuario entity) {
        if (entity == null) return null;

        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(entity.getId());
        dto.setUsuario(entity.getUsuario());
        dto.setRol(entity.getRol());
        // Contraseña no se mapea al DTO de salida por seguridad
        return dto;
    }

    public Usuario toEntity(UsuarioDTO dto) {
        if (dto == null) return null;

        Usuario entity = new Usuario();
        entity.setId(dto.getId());
        entity.setUsuario(dto.getUsuario());
        entity.setRol(dto.getRol());
        entity.setContrasenia(dto.getContrasenia());
        return entity;
    }

    public void updateEntityFromDTO(UsuarioDTO dto, Usuario entity) {
        if (dto == null || entity == null) return;

        entity.setUsuario(dto.getUsuario());
        entity.setRol(dto.getRol());
        if (dto.getContrasenia() != null && !dto.getContrasenia().isEmpty()) {
            entity.setContrasenia(dto.getContrasenia());
        }
    }
}
