
package com.ConsultorioOdontologico.consultorioOdontologico.service;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.SecretariaDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Secretaria;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.ISecretariaRepository;
import java.util.List;
import java.util.stream.Collectors;

import com.ConsultorioOdontologico.consultorioOdontologico.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecretariaService implements ISecretariaService {
    
    @Autowired
    private ISecretariaRepository secreRepo;

    @Autowired
    private IUsuarioRepository usuarioRepo;

    @Override
    public List<SecretariaDTO> getSecretarias() {
        return secreRepo.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public void saveSecretaria(SecretariaDTO sDTO) {
        Secretaria s = convertToEntity(sDTO);
        secreRepo.save(s);
    }

    @Override
    public void deleteSecretaria(Long id) {
        secreRepo.deleteById(id);
    }

    @Override
    public SecretariaDTO findSecretaria(Long id) {
        Secretaria s = secreRepo.findById(id).orElse(null);
        return (s != null) ? convertToDTO(s) : null;
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public void editSecretaria(SecretariaDTO sDTO) {
        if (sDTO == null || sDTO.getId() == null) return;
        
        Secretaria existing = secreRepo.findById(sDTO.getId()).orElse(null);
        if (existing == null) return;

        existing.setNombre(sDTO.getNombre());
        existing.setApellido(sDTO.getApellido());
        existing.setDni(sDTO.getDni());
        existing.setTelefono(sDTO.getTelefono());
        existing.setDireccion(sDTO.getDireccion());
        existing.setFecha_nac(sDTO.getFecha_nac());
        existing.setSector(sDTO.getSector());

        if (sDTO.getIdUsuario() != null) {
            existing.setUnUsuario(usuarioRepo.findById(sDTO.getIdUsuario()).orElse(null));
        } else {
            existing.setUnUsuario(null);
        }
        secreRepo.save(existing);
    }

    private SecretariaDTO convertToDTO(Secretaria s) {
        SecretariaDTO dto = new SecretariaDTO();
        dto.setId(s.getId());
        dto.setDni(s.getDni());
        dto.setNombre(s.getNombre());
        dto.setApellido(s.getApellido());
        dto.setTelefono(s.getTelefono());
        dto.setDireccion(s.getDireccion());
        dto.setFecha_nac(s.getFecha_nac());
        dto.setSector(s.getSector());
        if (s.getUnUsuario() != null) {
            dto.setIdUsuario(s.getUnUsuario().getId_usuario());
            dto.setNombreUsuario(s.getUnUsuario().getUsuario());
        }
        return dto;
    }

    private Secretaria convertToEntity(SecretariaDTO dto) {
        Secretaria s = new Secretaria();
        s.setId(dto.getId());
        s.setDni(dto.getDni());
        s.setNombre(dto.getNombre());
        s.setApellido(dto.getApellido());
        s.setTelefono(dto.getTelefono());
        s.setDireccion(dto.getDireccion());
        s.setFecha_nac(dto.getFecha_nac());
        s.setSector(dto.getSector());
        if (dto.getIdUsuario() != null) {
            s.setUnUsuario(usuarioRepo.findById(dto.getIdUsuario()).orElse(null));
        }
        return s;
    }
}
