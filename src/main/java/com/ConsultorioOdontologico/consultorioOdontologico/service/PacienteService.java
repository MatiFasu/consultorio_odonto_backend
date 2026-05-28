package com.ConsultorioOdontologico.consultorioOdontologico.service;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.PacienteDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.mapper.PacienteMapper;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Paciente;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Responsable;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.IPacienteRepository;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.IResponsableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PacienteService implements IPacienteService {
    
    private final IPacienteRepository pacRepo;
    private final IResponsableRepository respRepo;
    private final PacienteMapper pacienteMapper;

    @Override
    public List<PacienteDTO> getPacientes() {
        return pacRepo.findAll().stream()
                .map(pacienteMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<PacienteDTO> getPacientesPaginated(Pageable pageable) {
        return pacRepo.findAll(pageable).map(pacienteMapper::toDTO);
    }

    private void validarResponsabilidad(PacienteDTO pDTO) {
        if (pDTO.getFecha_nac() != null) {
            LocalDate fechaNacimiento = pDTO.getFecha_nac();
            int edad = Period.between(fechaNacimiento, LocalDate.now()).getYears();
            if (edad < 18 && pDTO.getIdResponsable() == null) {
                throw new IllegalArgumentException("El paciente es menor de edad y debe tener un Responsable asignado.");
            }
        }
    }

    @Override
    @Transactional
    public PacienteDTO savePaciente(PacienteDTO pDTO) {
        validarResponsabilidad(pDTO);
        
        Responsable resp = null;
        if (pDTO.getIdResponsable() != null) {
            resp = respRepo.findById(pDTO.getIdResponsable())
                    .orElseThrow(() -> new IllegalArgumentException("Responsable no encontrado con ID: " + pDTO.getIdResponsable()));
        }

        Paciente p = pacienteMapper.toEntity(pDTO, resp);
        Paciente saved = pacRepo.save(p);
        return pacienteMapper.toDTO(saved);
    }

    @Override
    public void deletePaciente(Long id) {
        pacRepo.deleteById(id);
    }

    @Override
    public PacienteDTO findPaciente(Long id) {
        return pacRepo.findById(id)
                .map(pacienteMapper::toDTO)
                .orElse(null);
    }

    @Override
    @Transactional
    public void editPaciente(PacienteDTO pDTO) {
        if (pDTO == null || pDTO.getId() == null) return;

        validarResponsabilidad(pDTO);

        pacRepo.findById(pDTO.getId()).ifPresent(existing -> {
            Responsable resp = null;
            if (pDTO.getIdResponsable() != null) {
                resp = respRepo.findById(pDTO.getIdResponsable())
                        .orElseThrow(() -> new IllegalArgumentException("Responsable no encontrado con ID: " + pDTO.getIdResponsable()));
            }
            pacienteMapper.updateEntityFromDTO(pDTO, existing, resp);
            pacRepo.save(existing);
        });
    }
}
