package com.ConsultorioOdontologico.consultorioOdontologico.mapper;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.PacienteDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Paciente;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Responsable;
import org.springframework.stereotype.Component;

@Component
public class PacienteMapper {

    public PacienteDTO toDTO(Paciente paciente) {
        if (paciente == null) return null;
        
        PacienteDTO dto = new PacienteDTO();
        dto.setId(paciente.getId());
        dto.setDni(paciente.getDni());
        dto.setNombre(paciente.getNombre());
        dto.setApellido(paciente.getApellido());
        dto.setTelefono(paciente.getTelefono());
        dto.setDireccion(paciente.getDireccion());
        dto.setFecha_nac(paciente.getFecha_nac()); // Ahora es LocalDate en ambos
        dto.setTiene_OS(paciente.isTiene_OS());
        dto.setTipoSangre(paciente.getTipoSangre());
        if (paciente.getUnResponsable() != null) {
            dto.setIdResponsable(paciente.getUnResponsable().getId());
        }
        return dto;
    }

    public Paciente toEntity(PacienteDTO dto, Responsable responsable) {
        if (dto == null) return null;

        Paciente paciente = new Paciente();
        paciente.setId(dto.getId());
        paciente.setDni(dto.getDni());
        paciente.setNombre(dto.getNombre());
        paciente.setApellido(dto.getApellido());
        paciente.setTelefono(dto.getTelefono());
        paciente.setDireccion(dto.getDireccion());
        paciente.setFecha_nac(dto.getFecha_nac()); // Ahora es LocalDate
        paciente.setTiene_OS(dto.isTiene_OS());
        paciente.setTipoSangre(dto.getTipoSangre());
        paciente.setUnResponsable(responsable);
        return paciente;
    }

    public void updateEntityFromDTO(PacienteDTO dto, Paciente entity, Responsable responsable) {
        if (dto == null || entity == null) return;

        entity.setNombre(dto.getNombre());
        entity.setApellido(dto.getApellido());
        entity.setDni(dto.getDni());
        entity.setTelefono(dto.getTelefono());
        entity.setDireccion(dto.getDireccion());
        entity.setFecha_nac(dto.getFecha_nac()); // Ahora es LocalDate
        entity.setTiene_OS(dto.isTiene_OS());
        entity.setTipoSangre(dto.getTipoSangre());
        entity.setUnResponsable(responsable);
    }
}
