
package com.ConsultorioOdontologico.consultorioOdontologico.service;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.ResponsableDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.mapper.ResponsableMapper;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Responsable;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.IResponsableRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResponsableService implements IResponsableService{
    
    private final IResponsableRepository responsableRepo;
    private final ResponsableMapper responsableMapper;

    @Override
    public List<ResponsableDTO> getResponsables() {
        return responsableRepo.findAll().stream()
                .map(responsableMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public void saveResponsable(ResponsableDTO rDTO) {
        Responsable r = responsableMapper.toEntity(rDTO);
        responsableRepo.save(r);
    }

    @Override
    public void deleteResponsable(Long id) {
        responsableRepo.deleteById(id);
    }

    @Override
    public ResponsableDTO findResponsable(Long id) {
        return responsableRepo.findById(id)
                .map(responsableMapper::toDTO)
                .orElse(null);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public void editResponsable(ResponsableDTO rDTO) {
        if (rDTO == null || rDTO.getId() == null) return;

        responsableRepo.findById(rDTO.getId()).ifPresent(existing -> {
            responsableMapper.updateEntityFromDTO(rDTO, existing);
            responsableRepo.save(existing);
        });
    }
}
