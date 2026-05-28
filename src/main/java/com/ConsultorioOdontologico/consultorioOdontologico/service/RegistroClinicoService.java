package com.ConsultorioOdontologico.consultorioOdontologico.service;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.RegistroClinicoDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.mapper.RegistroClinicoMapper;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Odontologo;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Paciente;
import com.ConsultorioOdontologico.consultorioOdontologico.model.RegistroClinico;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.IOdontologoRepository;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.IPacienteRepository;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.IRegistroClinicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegistroClinicoService implements IRegistroClinicoService {

    private final IRegistroClinicoRepository registroRepo;
    private final IPacienteRepository pacienteRepo;
    private final IOdontologoRepository odontoRepo;
    private final RegistroClinicoMapper registroMapper;

    @Override
    public List<RegistroClinicoDTO> getRegistros() {
        return registroRepo.findAll().stream()
                .map(registroMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RegistroClinicoDTO> getRegistrosByPaciente(Long pacienteId) {
        return registroRepo.findByPacienteIdOrderByFechaDesc(pacienteId).stream()
                .map(registroMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void saveRegistro(RegistroClinicoDTO rDTO) {
        Paciente pac = pacienteRepo.findById(rDTO.getIdPaciente())
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado"));
        
        Odontologo odonto = odontoRepo.findById(rDTO.getIdOdontologo())
                .orElseThrow(() -> new IllegalArgumentException("Odontólogo no encontrado"));

        RegistroClinico registro = registroMapper.toEntity(rDTO);
        registro.setPaciente(pac);
        registro.setOdontologo(odonto);
        
        registroRepo.save(registro);
    }

    @Override
    public void deleteRegistro(Long id) {
        registroRepo.deleteById(id);
    }

    @Override
    public RegistroClinicoDTO findRegistro(Long id) {
        return registroRepo.findById(id)
                .map(registroMapper::toDTO)
                .orElse(null);
    }

    @Override
    @Transactional
    public void editRegistro(RegistroClinicoDTO rDTO) {
        if (rDTO == null || rDTO.getId() == null) return;

        RegistroClinico existing = registroRepo.findById(rDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Registro no encontrado"));

        registroMapper.updateEntityFromDTO(rDTO, existing);
        registroRepo.save(existing);
    }
}
