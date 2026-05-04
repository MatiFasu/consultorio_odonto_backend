
package com.ConsultorioOdontologico.consultorioOdontologico.service;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.OdontologoDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Odontologo;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.IHorarioRepository;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.IOdontologoRepository;
import java.util.List;
import java.util.stream.Collectors;

import com.ConsultorioOdontologico.consultorioOdontologico.repository.ITurnoRepository;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OdontologoService implements IOdontologoService{
    
    @Autowired
    private IOdontologoRepository odontoRepo;

    @Autowired
    private ITurnoRepository turnoRepo;

    @Autowired
    private IUsuarioRepository usuarioRepo;

    @Autowired
    private IHorarioRepository horarioRepo;

    @Override
    public List<OdontologoDTO> getOdontologos() {
        return odontoRepo.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public void saveOdontologo(OdontologoDTO oDTO) {
        Odontologo o = convertToEntity(oDTO);
        odontoRepo.save(o);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public void editOdontologo(OdontologoDTO oDTO) {
        if (oDTO == null || oDTO.getId() == null) return;
        
        Odontologo existing = odontoRepo.findById(oDTO.getId()).orElse(null);
        if (existing == null) return;

        existing.setNombre(oDTO.getNombre());
        existing.setApellido(oDTO.getApellido());
        existing.setDni(oDTO.getDni());
        existing.setTelefono(oDTO.getTelefono());
        existing.setDireccion(oDTO.getDireccion());
        existing.setFecha_nac(oDTO.getFecha_nac());
        existing.setEspecialidad(oDTO.getEspecialidad());

        if (oDTO.getIdUsuario() != null) {
            existing.setUnUsuario(usuarioRepo.findById(oDTO.getIdUsuario()).orElse(null));
        } else {
            existing.setUnUsuario(null);
        }

        if (oDTO.getIdHorario() != null) {
            existing.setUnHorario(horarioRepo.findById(oDTO.getIdHorario()).orElse(null));
        } else {
            existing.setUnHorario(null);
        }

        odontoRepo.save(existing);
    }

    @Override
    public OdontologoDTO findByUserId(Long userId) {
        Odontologo o = odontoRepo.findByUsuarioId(userId).orElse(null);
        return (o != null) ? convertToDTO(o) : null;
    }

    @Override
    public void deleteOdontologo(Long id) {
        odontoRepo.deleteById(id);
    }

    @Override
    public OdontologoDTO findOdontologo(Long id) {
        Odontologo o = odontoRepo.findById(id).orElse(null);
        return (o != null) ? convertToDTO(o) : null;
    }

    private OdontologoDTO convertToDTO(Odontologo o) {
        OdontologoDTO dto = new OdontologoDTO();
        dto.setId(o.getId());
        dto.setDni(o.getDni());
        dto.setNombre(o.getNombre());
        dto.setApellido(o.getApellido());
        dto.setTelefono(o.getTelefono());
        dto.setDireccion(o.getDireccion());
        dto.setFecha_nac(o.getFecha_nac());
        dto.setEspecialidad(o.getEspecialidad());
        if (o.getUnUsuario() != null) {
            dto.setIdUsuario(o.getUnUsuario().getId_usuario());
            dto.setNombreUsuario(o.getUnUsuario().getUsuario());
        }
        if (o.getUnHorario() != null) {
            dto.setIdHorario(o.getUnHorario().getId_horario());
            dto.setHorarioInicio(o.getUnHorario().getHorario_inicio());
            dto.setHorarioFinal(o.getUnHorario().getHorario_final());
        }
        return dto;
    }

    private Odontologo convertToEntity(OdontologoDTO dto) {
        Odontologo o = new Odontologo();
        o.setId(dto.getId());
        o.setDni(dto.getDni());
        o.setNombre(dto.getNombre());
        o.setApellido(dto.getApellido());
        o.setTelefono(dto.getTelefono());
        o.setDireccion(dto.getDireccion());
        o.setFecha_nac(dto.getFecha_nac());
        o.setEspecialidad(dto.getEspecialidad());
        if (dto.getIdUsuario() != null) {
            o.setUnUsuario(usuarioRepo.findById(dto.getIdUsuario()).orElse(null));
        }
        if (dto.getIdHorario() != null) {
            o.setUnHorario(horarioRepo.findById(dto.getIdHorario()).orElse(null));
        }
        return o;
    }
}
