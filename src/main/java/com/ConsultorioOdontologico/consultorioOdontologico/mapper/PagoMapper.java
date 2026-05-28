package com.ConsultorioOdontologico.consultorioOdontologico.mapper;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.PagoDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Pago;
import org.springframework.stereotype.Component;

@Component
public class PagoMapper {

    public PagoDTO toDTO(Pago entity) {
        if (entity == null) return null;
        
        PagoDTO dto = new PagoDTO();
        dto.setId(entity.getId());
        dto.setFecha(entity.getFecha());
        dto.setMonto(entity.getMonto());
        dto.setMetodoPago(entity.getMetodoPago());
        dto.setNotas(entity.getNotas());
        dto.setTransaccionId(entity.getTransaccionId());
        
        if (entity.getPaciente() != null) {
            dto.setIdPaciente(entity.getPaciente().getId());
            dto.setNombrePaciente(entity.getPaciente().getNombre() + " " + entity.getPaciente().getApellido());
        }
        
        if (entity.getPresupuesto() != null) {
            dto.setIdPresupuesto(entity.getPresupuesto().getId());
        }
        
        return dto;
    }

    public Pago toEntity(PagoDTO dto) {
        if (dto == null) return null;
        
        Pago entity = new Pago();
        entity.setId(dto.getId());
        entity.setFecha(dto.getFecha());
        entity.setMonto(dto.getMonto());
        entity.setMetodoPago(dto.getMetodoPago());
        entity.setNotas(dto.getNotas());
        entity.setTransaccionId(dto.getTransaccionId());
        
        return entity;
    }
}
