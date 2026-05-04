
package com.ConsultorioOdontologico.consultorioOdontologico.service;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.PacienteDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Paciente;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Responsable;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.IPacienteRepository;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.IResponsableRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PacienteService implements IPacienteService{
    
    @Autowired
    private IPacienteRepository pacRepo;

    @Autowired
    private IResponsableRepository respRepo;

    @Override
    public List<PacienteDTO> getPacientes() {
        List<Paciente> listaPacientes = pacRepo.findAll();
        return listaPacientes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public void savePaciente(PacienteDTO pDTO) {
        Paciente p = convertToEntity(pDTO);
        pacRepo.save(p);
    }

    @Override
    public void deletePaciente(Long id) {
        pacRepo.deleteById(id);
    }

    @Override
    public PacienteDTO findPaciente(Long id) {
        Paciente p = pacRepo.findById(id).orElse(null);
        return (p != null) ? convertToDTO(p) : null;
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public void editPaciente(PacienteDTO pDTO) {
        if (pDTO == null || pDTO.getId() == null) return;

        Paciente existing = pacRepo.findById(pDTO.getId()).orElse(null);
        if (existing != null) {
            existing.setNombre(pDTO.getNombre());
            existing.setApellido(pDTO.getApellido());
            existing.setDni(pDTO.getDni());
            existing.setTelefono(pDTO.getTelefono());
            existing.setDireccion(pDTO.getDireccion());
            existing.setFecha_nac(pDTO.getFecha_nac());
            existing.setTiene_OS(pDTO.isTiene_OS());
            existing.setTipoSangre(pDTO.getTipoSangre());
            
            if (pDTO.getIdResponsable() != null) {
                Responsable resp = respRepo.findById(pDTO.getIdResponsable()).orElse(null);
                existing.setUnResponsable(resp);
            } else {
                existing.setUnResponsable(null);
            }
            
            pacRepo.save(existing);
        }
    }

    // Métodos auxiliares de conversión (Mappers manuales)
    private PacienteDTO convertToDTO(Paciente p) {
        PacienteDTO dto = new PacienteDTO();
        dto.setId(p.getId());
        dto.setDni(p.getDni());
        dto.setNombre(p.getNombre());
        dto.setApellido(p.getApellido());
        dto.setTelefono(p.getTelefono());
        dto.setDireccion(p.getDireccion());
        dto.setFecha_nac(p.getFecha_nac());
        dto.setTiene_OS(p.isTiene_OS());
        dto.setTipoSangre(p.getTipoSangre());
        if (p.getUnResponsable() != null) {
            dto.setIdResponsable(p.getUnResponsable().getId());
        }
        return dto;
    }

    private Paciente convertToEntity(PacienteDTO dto) {
        Paciente p = new Paciente();
        p.setId(dto.getId());
        p.setDni(dto.getDni());
        p.setNombre(dto.getNombre());
        p.setApellido(dto.getApellido());
        p.setTelefono(dto.getTelefono());
        p.setDireccion(dto.getDireccion());
        p.setFecha_nac(dto.getFecha_nac());
        p.setTiene_OS(dto.isTiene_OS());
        p.setTipoSangre(dto.getTipoSangre());
        if (dto.getIdResponsable() != null) {
            Responsable resp = respRepo.findById(dto.getIdResponsable()).orElse(null);
            p.setUnResponsable(resp);
        }
        return p;
    }
    
}
