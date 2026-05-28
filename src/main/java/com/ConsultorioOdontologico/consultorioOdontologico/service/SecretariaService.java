
package com.ConsultorioOdontologico.consultorioOdontologico.service;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.SecretariaDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.mapper.SecretariaMapper;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Secretaria;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Usuario;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.ISecretariaRepository;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.IUsuarioRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecretariaService implements ISecretariaService {
    
    private final ISecretariaRepository secreRepo;
    private final IUsuarioRepository usuarioRepo;
    private final SecretariaMapper secretariaMapper;

    @Override
    public List<SecretariaDTO> getSecretarias() {
        return secreRepo.findAll().stream()
                .map(secretariaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public void saveSecretaria(SecretariaDTO sDTO) {
        Usuario user = null;
        if (sDTO.getIdUsuario() != null) {
            user = usuarioRepo.findById(sDTO.getIdUsuario())
                    .orElseThrow(() -> new IllegalArgumentException("El usuario con ID " + sDTO.getIdUsuario() + " no existe"));
        }
        Secretaria s = secretariaMapper.toEntity(sDTO, user);
        secreRepo.save(s);
    }

    @Override
    public void deleteSecretaria(Long id) {
        secreRepo.deleteById(id);
    }

    @Override
    public SecretariaDTO findSecretaria(Long id) {
        return secreRepo.findById(id)
                .map(secretariaMapper::toDTO)
                .orElse(null);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public void editSecretaria(SecretariaDTO sDTO) {
        if (sDTO == null || sDTO.getId() == null) return;
        
        secreRepo.findById(sDTO.getId()).ifPresent(existing -> {
            Usuario user = null;
            if (sDTO.getIdUsuario() != null) {
                user = usuarioRepo.findById(sDTO.getIdUsuario()).orElse(null);
            }
            secretariaMapper.updateEntityFromDTO(sDTO, existing, user);
            secreRepo.save(existing);
        });
    }
}
