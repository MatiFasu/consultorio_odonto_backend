package com.ConsultorioOdontologico.consultorioOdontologico.mapper;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.RegistroClinicoDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.model.RegistroClinico;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RegistroClinicoMapper {

    private final EstadoDienteMapper estadoDienteMapper;
    private final MultimediaMapper multimediaMapper;

    public RegistroClinicoDTO toDTO(RegistroClinico entity) {
        if (entity == null) return null;
        
        RegistroClinicoDTO dto = new RegistroClinicoDTO();
        dto.setId(entity.getId());
        dto.setFecha(entity.getFecha());
        dto.setMotivoConsulta(entity.getMotivoConsulta());
        dto.setDiagnostico(entity.getDiagnostico());
        dto.setTratamiento(entity.getTratamiento());
        dto.setObservaciones(entity.getObservaciones());
        
        if (entity.getPaciente() != null) {
            dto.setIdPaciente(entity.getPaciente().getId());
            dto.setNombrePaciente(entity.getPaciente().getNombre() + " " + entity.getPaciente().getApellido());
        }
        
        if (entity.getOdontologo() != null) {
            dto.setIdOdontologo(entity.getOdontologo().getId());
            dto.setNombreOdontologo(entity.getOdontologo().getNombre() + " " + entity.getOdontologo().getApellido());
        }
        
        if (entity.getOdontograma() != null) {
            dto.setOdontograma(entity.getOdontograma().stream()
                    .map(estadoDienteMapper::toDTO)
                    .collect(Collectors.toList()));
        }
        
        if (entity.getEstudios() != null) {
            dto.setEstudios(entity.getEstudios().stream()
                    .map(multimediaMapper::toDTO)
                    .collect(Collectors.toList()));
        }
        
        return dto;
    }

    public RegistroClinico toEntity(RegistroClinicoDTO dto) {
        if (dto == null) return null;
        
        RegistroClinico entity = new RegistroClinico();
        entity.setId(dto.getId());
        entity.setFecha(dto.getFecha());
        entity.setMotivoConsulta(dto.getMotivoConsulta());
        entity.setDiagnostico(dto.getDiagnostico());
        entity.setTratamiento(dto.getTratamiento());
        entity.setObservaciones(dto.getObservaciones());
        
        if (dto.getOdontograma() != null) {
            entity.setOdontograma(dto.getOdontograma().stream()
                    .map(d -> {
                        var ed = estadoDienteMapper.toEntity(d);
                        ed.setRegistroClinico(entity);
                        return ed;
                    })
                    .collect(Collectors.toList()));
        } else {
            entity.setOdontograma(new ArrayList<>());
        }
        
        return entity;
    }

    public void updateEntityFromDTO(RegistroClinicoDTO dto, RegistroClinico entity) {
        if (dto == null || entity == null) return;
        
        entity.setMotivoConsulta(dto.getMotivoConsulta());
        entity.setDiagnostico(dto.getDiagnostico());
        entity.setTratamiento(dto.getTratamiento());
        entity.setObservaciones(dto.getObservaciones());
        
        // El odontograma suele ser inmutable por registro (se crea uno nuevo para cada sesión)
        // Pero si permitimos editar el registro actual:
        if (dto.getOdontograma() != null) {
            entity.getOdontograma().clear();
            entity.getOdontograma().addAll(dto.getOdontograma().stream()
                    .map(d -> {
                        var ed = estadoDienteMapper.toEntity(d);
                        ed.setRegistroClinico(entity);
                        return ed;
                    })
                    .collect(Collectors.toList()));
        }
    }
}
