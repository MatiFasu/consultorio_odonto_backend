package com.ConsultorioOdontologico.consultorioOdontologico.mapper;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.OdontologoDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Horario;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Odontologo;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class OdontologoMapper {

    public OdontologoDTO toDTO(Odontologo entity) {
        if (entity == null) return null;
        
        OdontologoDTO dto = new OdontologoDTO();
        dto.setId(entity.getId());
        dto.setDni(entity.getDni());
        dto.setNombre(entity.getNombre());
        dto.setApellido(entity.getApellido());
        dto.setTelefono(entity.getTelefono());
        dto.setDireccion(entity.getDireccion());
        dto.setFecha_nac(entity.getFecha_nac());
        dto.setEspecialidad(entity.getEspecialidad());
        
        if (entity.getUnUsuario() != null) {
            dto.setIdUsuario(entity.getUnUsuario().getId());
            dto.setNombreUsuario(entity.getUnUsuario().getUsuario());
        }
        
        if (entity.getUnHorario() != null) {
            dto.setIdHorario(entity.getUnHorario().getId());
            dto.setHorarioInicio(entity.getUnHorario().getHorario_inicio());
            dto.setHorarioFinal(entity.getUnHorario().getHorario_final());
        }
        
        return dto;
    }

    public Odontologo toEntity(OdontologoDTO dto, Usuario usuario, Horario horario) {
        if (dto == null) return null;
        
        Odontologo entity = new Odontologo();
        entity.setId(dto.getId());
        entity.setDni(dto.getDni());
        entity.setNombre(dto.getNombre());
        entity.setApellido(dto.getApellido());
        entity.setTelefono(dto.getTelefono());
        entity.setDireccion(dto.getDireccion());
        entity.setFecha_nac(dto.getFecha_nac());
        entity.setEspecialidad(dto.getEspecialidad());
        entity.setUnUsuario(usuario);
        entity.setUnHorario(horario);
        
        return entity;
    }

    public void updateEntityFromDTO(OdontologoDTO dto, Odontologo entity, Usuario usuario, Horario horario) {
        if (dto == null || entity == null) return;
        
        entity.setNombre(dto.getNombre());
        entity.setApellido(dto.getApellido());
        entity.setDni(dto.getDni());
        entity.setTelefono(dto.getTelefono());
        entity.setDireccion(dto.getDireccion());
        entity.setFecha_nac(dto.getFecha_nac());
        entity.setEspecialidad(dto.getEspecialidad());
        entity.setUnUsuario(usuario);
        entity.setUnHorario(horario);
    }
}
