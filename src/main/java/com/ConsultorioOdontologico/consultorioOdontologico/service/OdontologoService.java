package com.ConsultorioOdontologico.consultorioOdontologico.service;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.OdontologoDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.mapper.OdontologoMapper;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Horario;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Odontologo;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Usuario;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.IHorarioRepository;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.IOdontologoRepository;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.IUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OdontologoService implements IOdontologoService {
    
    private final IOdontologoRepository odontoRepo;
    private final IUsuarioRepository usuarioRepo;
    private final IHorarioRepository horarioRepo;
    private final OdontologoMapper odontologoMapper;

    @Override
    public List<OdontologoDTO> getOdontologos() {
        return odontoRepo.findAll().stream()
                .map(odontologoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void saveOdontologo(OdontologoDTO oDTO) {
        // Validación de horarios
        if (oDTO.getHorarioInicio() != null && oDTO.getHorarioFinal() != null) {
            if (oDTO.getHorarioInicio().compareTo(oDTO.getHorarioFinal()) >= 0) {
                throw new IllegalArgumentException("El horario de inicio debe ser anterior al horario final");
            }
        }

        Usuario user = null;
        if (oDTO.getIdUsuario() != null) {
            user = usuarioRepo.findById(oDTO.getIdUsuario())
                    .orElseThrow(() -> new IllegalArgumentException("El usuario con ID " + oDTO.getIdUsuario() + " no existe"));
        }
        
        Horario hor = null;
        if (oDTO.getIdHorario() != null) {
            hor = horarioRepo.findById(oDTO.getIdHorario())
                    .orElseThrow(() -> new IllegalArgumentException("El horario con ID " + oDTO.getIdHorario() + " no existe"));
        } else if (oDTO.getHorarioInicio() != null && oDTO.getHorarioFinal() != null) {
            hor = new Horario(null, oDTO.getHorarioInicio(), oDTO.getHorarioFinal());
        }
        
        Odontologo o = odontologoMapper.toEntity(oDTO, user, hor);
        odontoRepo.save(o);
    }

    @Override
    @Transactional
    public void editOdontologo(OdontologoDTO oDTO) {
        if (oDTO == null || oDTO.getId() == null) return;
        
        // Validación de horarios
        if (oDTO.getHorarioInicio() != null && oDTO.getHorarioFinal() != null) {
            if (oDTO.getHorarioInicio().compareTo(oDTO.getHorarioFinal()) >= 0) {
                throw new IllegalArgumentException("El horario de inicio debe ser anterior al horario final");
            }
        }

        odontoRepo.findById(oDTO.getId()).ifPresent(existing -> {
            Usuario user = null;
            if (oDTO.getIdUsuario() != null) {
                user = usuarioRepo.findById(oDTO.getIdUsuario()).orElse(null);
            }
            
            Horario hor = existing.getUnHorario();
            if (hor != null) {
                if (oDTO.getHorarioInicio() != null) hor.setHorario_inicio(oDTO.getHorarioInicio());
                if (oDTO.getHorarioFinal() != null) hor.setHorario_final(oDTO.getHorarioFinal());
            } else if (oDTO.getHorarioInicio() != null && oDTO.getHorarioFinal() != null) {
                hor = new Horario(null, oDTO.getHorarioInicio(), oDTO.getHorarioFinal());
            }
            
            odontologoMapper.updateEntityFromDTO(oDTO, existing, user, hor);
            odontoRepo.save(existing);
        });
    }

    @Override
    public OdontologoDTO findByUserId(Long userId) {
        return odontoRepo.findByUsuarioId(userId)
                .map(odontologoMapper::toDTO)
                .orElse(null);
    }

    @Override
    public void deleteOdontologo(Long id) {
        odontoRepo.deleteById(id);
    }

    @Override
    public OdontologoDTO findOdontologo(Long id) {
        return odontoRepo.findById(id)
                .map(odontologoMapper::toDTO)
                .orElse(null);
    }
}
