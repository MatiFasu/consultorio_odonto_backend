
package com.ConsultorioOdontologico.consultorioOdontologico.service;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.ResponsableDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Responsable;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.IResponsableRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResponsableService implements IResponsableService{
    
    @Autowired
    private IResponsableRepository responsableRepo;

    @Override
    public List<ResponsableDTO> getResponsables() {
        return responsableRepo.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void saveResponsable(ResponsableDTO rDTO) {
        Responsable r = convertToEntity(rDTO);
        responsableRepo.save(r);
    }

    @Override
    public void deleteResponsable(Long id) {
        responsableRepo.deleteById(id);
    }

    @Override
    public ResponsableDTO findResponsable(Long id) {
        Responsable r = responsableRepo.findById(id).orElse(null);
        return (r != null) ? convertToDTO(r) : null;
    }

    @Override
    public void editResponsable(ResponsableDTO rDTO) {
        if (rDTO == null || rDTO.getId() == null) return;

        Responsable existing = responsableRepo.findById(rDTO.getId()).orElse(null);
        if (existing != null) {
            existing.setNombre(rDTO.getNombre());
            existing.setApellido(rDTO.getApellido());
            existing.setDni(rDTO.getDni());
            existing.setTelefono(rDTO.getTelefono());
            existing.setDireccion(rDTO.getDireccion());
            existing.setFecha_nac(rDTO.getFecha_nac());
            existing.setTipoResponsabilidad(rDTO.getTipoResponsabilidad());
            
            responsableRepo.save(existing);
        }
    }

    private ResponsableDTO convertToDTO(Responsable r) {
        ResponsableDTO dto = new ResponsableDTO();
        dto.setId(r.getId());
        dto.setDni(r.getDni());
        dto.setNombre(r.getNombre());
        dto.setApellido(r.getApellido());
        dto.setTelefono(r.getTelefono());
        dto.setDireccion(r.getDireccion());
        dto.setFecha_nac(r.getFecha_nac());
        dto.setTipoResponsabilidad(r.getTipoResponsabilidad());
        return dto;
    }

    private Responsable convertToEntity(ResponsableDTO dto) {
        Responsable r = new Responsable();
        r.setId(dto.getId());
        r.setDni(dto.getDni());
        r.setNombre(dto.getNombre());
        r.setApellido(dto.getApellido());
        r.setTelefono(dto.getTelefono());
        r.setDireccion(dto.getDireccion());
        r.setFecha_nac(dto.getFecha_nac());
        r.setTipoResponsabilidad(dto.getTipoResponsabilidad());
        return r;
    }
}
