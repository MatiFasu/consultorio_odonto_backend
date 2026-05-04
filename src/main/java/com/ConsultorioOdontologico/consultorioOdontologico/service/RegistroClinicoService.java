
package com.ConsultorioOdontologico.consultorioOdontologico.service;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.EstadoDienteDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.dto.MultimediaEstudioDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.dto.RegistroClinicoDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.model.EstadoDiente;
import com.ConsultorioOdontologico.consultorioOdontologico.model.MultimediaEstudio;
import com.ConsultorioOdontologico.consultorioOdontologico.model.RegistroClinico;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.IOdontologoRepository;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.IPacienteRepository;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.IRegistroClinicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegistroClinicoService implements IRegistroClinicoService {

    @Autowired
    private IRegistroClinicoRepository registroRepo;

    @Autowired
    private IPacienteRepository pacRepo;

    @Autowired
    private IOdontologoRepository odontoRepo;

    @Override
    public List<RegistroClinicoDTO> getHistorialPorPaciente(Long pacienteId) {
        return registroRepo.findByPacienteIdOrderByFechaDesc(pacienteId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public RegistroClinicoDTO saveRegistro(RegistroClinicoDTO dto) {
        RegistroClinico registro = convertToEntity(dto);

        if (registro.getId() != null) {
            RegistroClinico existing = registroRepo.findById(registro.getId()).orElse(null);
            if (existing != null) {
                existing.setMotivoConsulta(registro.getMotivoConsulta());
                existing.setDiagnostico(registro.getDiagnostico());
                existing.setTratamiento(registro.getTratamiento());
                existing.setObservaciones(registro.getObservaciones());
                
                existing.getOdontograma().clear();
                if (registro.getOdontograma() != null) {
                    final RegistroClinico finalExisting = existing;
                    registro.getOdontograma().forEach(diente -> {
                        diente.setRegistroClinico(finalExisting);
                        finalExisting.getOdontograma().add(diente);
                    });
                }
                
                return convertToDTO(registroRepo.save(existing));
            }
        }
        
        return convertToDTO(registroRepo.save(registro));
    }

    @Override
    public void deleteRegistro(Long id) {
        registroRepo.deleteById(id);
    }

    @Override
    public RegistroClinicoDTO findRegistro(Long id) {
        RegistroClinico r = registroRepo.findById(id).orElse(null);
        return (r != null) ? convertToDTO(r) : null;
    }

    private RegistroClinicoDTO convertToDTO(RegistroClinico r) {
        RegistroClinicoDTO dto = new RegistroClinicoDTO();
        dto.setId(r.getId());
        dto.setFecha(r.getFecha());
        dto.setMotivoConsulta(r.getMotivoConsulta());
        dto.setDiagnostico(r.getDiagnostico());
        dto.setTratamiento(r.getTratamiento());
        dto.setObservaciones(r.getObservaciones());
        if (r.getPaciente() != null) {
            dto.setIdPaciente(r.getPaciente().getId());
            dto.setNombrePaciente(r.getPaciente().getNombre() + " " + r.getPaciente().getApellido());
        }
        if (r.getOdontologo() != null) {
            dto.setIdOdontologo(r.getOdontologo().getId());
            dto.setNombreOdontologo(r.getOdontologo().getNombre() + " " + r.getOdontologo().getApellido());
        }
        if (r.getOdontograma() != null) {
            dto.setOdontograma(r.getOdontograma().stream().map(d -> {
                EstadoDienteDTO dDto = new EstadoDienteDTO();
                dDto.setId(d.getId());
                dDto.setNumeroDiente(d.getNumeroDiente());
                dDto.setPosicion(d.getPosicion());
                dDto.setEstado(d.getEstado());
                return dDto;
            }).collect(Collectors.toList()));
        }
        if (r.getEstudios() != null) {
            dto.setEstudios(r.getEstudios().stream().map(e -> {
                MultimediaEstudioDTO eDto = new MultimediaEstudioDTO();
                eDto.setId(e.getId());
                eDto.setNombreArchivo(e.getNombreArchivo());
                eDto.setTipoContenido(e.getTipoContenido());
                eDto.setUrlArchivo(e.getUrlArchivo());
                eDto.setFechaCarga(e.getFechaCarga());
                return eDto;
            }).collect(Collectors.toList()));
        }
        return dto;
    }

    private RegistroClinico convertToEntity(RegistroClinicoDTO dto) {
        RegistroClinico r = new RegistroClinico();
        r.setId(dto.getId());
        r.setFecha(dto.getFecha() != null ? dto.getFecha() : java.time.LocalDateTime.now());
        r.setMotivoConsulta(dto.getMotivoConsulta());
        r.setDiagnostico(dto.getDiagnostico());
        r.setTratamiento(dto.getTratamiento());
        r.setObservaciones(dto.getObservaciones());
        if (dto.getIdPaciente() != null) {
            r.setPaciente(pacRepo.findById(dto.getIdPaciente()).orElse(null));
        }
        if (dto.getIdOdontologo() != null) {
            r.setOdontologo(odontoRepo.findById(dto.getIdOdontologo()).orElse(null));
        }
        if (dto.getOdontograma() != null) {
            List<EstadoDiente> lista = dto.getOdontograma().stream().map(dDto -> {
                EstadoDiente d = new EstadoDiente();
                d.setId(dDto.getId());
                d.setNumeroDiente(dDto.getNumeroDiente());
                d.setPosicion(dDto.getPosicion());
                d.setEstado(dDto.getEstado());
                d.setRegistroClinico(r);
                return d;
            }).collect(Collectors.toList());
            r.setOdontograma(lista);
        }
        if (dto.getEstudios() != null) {
            List<MultimediaEstudio> lista = dto.getEstudios().stream().map(eDto -> {
                MultimediaEstudio e = new MultimediaEstudio();
                e.setId(eDto.getId());
                e.setNombreArchivo(eDto.getNombreArchivo());
                e.setTipoContenido(eDto.getTipoContenido());
                e.setUrlArchivo(eDto.getUrlArchivo());
                e.setFechaCarga(eDto.getFechaCarga());
                e.setRegistroClinico(r);
                return e;
            }).collect(Collectors.toList());
            r.setEstudios(lista);
        }
        return r;
    }
}
