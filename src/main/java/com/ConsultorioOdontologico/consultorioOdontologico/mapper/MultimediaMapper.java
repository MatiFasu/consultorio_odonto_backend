package com.ConsultorioOdontologico.consultorioOdontologico.mapper;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.MultimediaEstudioDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.model.MultimediaEstudio;
import org.springframework.stereotype.Component;

@Component
public class MultimediaMapper {

    public MultimediaEstudioDTO toDTO(MultimediaEstudio entity) {
        if (entity == null) return null;
        MultimediaEstudioDTO dto = new MultimediaEstudioDTO();
        dto.setId(entity.getId());
        dto.setNombreArchivo(entity.getNombreArchivo());
        dto.setTipoContenido(entity.getTipoContenido());
        dto.setUrlArchivo(entity.getUrlArchivo());
        dto.setFechaCarga(entity.getFechaCarga());
        return dto;
    }

    public MultimediaEstudio toEntity(MultimediaEstudioDTO dto) {
        if (dto == null) return null;
        MultimediaEstudio entity = new MultimediaEstudio();
        entity.setId(dto.getId());
        entity.setNombreArchivo(dto.getNombreArchivo());
        entity.setTipoContenido(dto.getTipoContenido());
        entity.setUrlArchivo(dto.getUrlArchivo());
        entity.setFechaCarga(dto.getFechaCarga());
        return entity;
    }
}
