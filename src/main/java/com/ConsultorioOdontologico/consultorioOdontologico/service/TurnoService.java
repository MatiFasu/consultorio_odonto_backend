package com.ConsultorioOdontologico.consultorioOdontologico.service;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.TurnoDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.mapper.TurnoMapper;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Odontologo;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Paciente;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Turno;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.IOdontologoRepository;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.IPacienteRepository;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.ITurnoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TurnoService implements ITurnoService {
    
    private final ITurnoRepository turnoRepo;
    private final IPacienteRepository pacienteRepo;
    private final IOdontologoRepository odontoRepo;
    private final TurnoMapper turnoMapper;

    @Override
    public List<TurnoDTO> getTurnos() {
        return turnoRepo.findAll().stream()
                .map(turnoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<TurnoDTO> getTurnosPaginated(Pageable pageable) {
        return turnoRepo.findAll(pageable).map(turnoMapper::toDTO);
    }

    @Override
    public Page<TurnoDTO> getTurnosByFechaPaginated(LocalDate fecha, Pageable pageable) {
        return turnoRepo.findByFechaTurno(fecha, pageable).map(turnoMapper::toDTO);
    }

    @Override
    @Transactional
    public void saveTurno(TurnoDTO tDTO) {
        Paciente p = pacienteRepo.findById(tDTO.getIdPaciente())
                .orElseThrow(() -> new IllegalArgumentException("No se encontró al paciente con ID: " + tDTO.getIdPaciente()));

        Odontologo o = odontoRepo.findById(tDTO.getIdOdontologo())
                .orElseThrow(() -> new IllegalArgumentException("No se encontró al odontólogo con ID: " + tDTO.getIdOdontologo()));
        
        validarAgenda(tDTO, o);
        
        Turno t = turnoMapper.toEntity(tDTO);
        t.setPacien(p);
        t.setOdonto(o);
        turnoRepo.save(t);
    }

    private void validarAgenda(TurnoDTO tDTO, Odontologo o) {
        LocalDate hoy = LocalDate.now();
        if (tDTO.getFecha_turno().isBefore(hoy)) {
            throw new IllegalArgumentException("No se pueden agendar turnos en fechas pasadas.");
        }
        
        if (tDTO.getFecha_turno().isEqual(hoy)) {
            LocalTime ahora = LocalTime.now();
            LocalTime horaTurno = LocalTime.parse(tDTO.getHora_turno());
            if (horaTurno.isBefore(ahora)) {
                throw new IllegalArgumentException("No se puede agendar un turno para una hora que ya pasó (" + tDTO.getHora_turno() + ").");
            }
        }

        if (o.getUnHorario() == null) {
            throw new IllegalArgumentException("El Dr. " + o.getApellido() + " no tiene un horario de atención configurado. Configure su horario antes de agendar turnos.");
        }

        LocalTime horaTurno = LocalTime.parse(tDTO.getHora_turno());
        LocalTime inicio = LocalTime.parse(o.getUnHorario().getHorario_inicio());
        LocalTime fin = LocalTime.parse(o.getUnHorario().getHorario_final());
        
        if (horaTurno.isBefore(inicio) || horaTurno.isAfter(fin)) {
            throw new IllegalArgumentException("El Dr. " + o.getApellido() + 
                " no trabaja en ese horario. Su jornada es de " + o.getUnHorario().getHorario_inicio() + " a " + o.getUnHorario().getHorario_final());
        }

        boolean yaExiste;
        if (tDTO.getId() == null) {
            yaExiste = turnoRepo.existsByOdontoIdAndFechaTurnoAndHoraTurno(o.getId(), tDTO.getFecha_turno(), tDTO.getHora_turno());
        } else {
            yaExiste = turnoRepo.existsByOdontoIdAndFechaTurnoAndHoraTurnoAndIdNot(o.getId(), tDTO.getFecha_turno(), tDTO.getHora_turno(), tDTO.getId());
        }

        if (yaExiste) {
            throw new IllegalArgumentException("El Dr. " + o.getApellido() + 
                " ya tiene un turno agendado para el " + tDTO.getFecha_turno() + " a las " + tDTO.getHora_turno());
        }
    }

    @Override
    public void deleteTurno(Long id) {
        turnoRepo.deleteById(id);
    }

    @Override
    public TurnoDTO findTurno(Long id) {
        return turnoRepo.findById(id)
                .map(turnoMapper::toDTO)
                .orElse(null);
    }

    @Override
    @Transactional
    public void editTurno(TurnoDTO tDTO) {
        if (tDTO == null || tDTO.getId() == null) return;

        Turno existing = turnoRepo.findById(tDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Turno no encontrado con ID: " + tDTO.getId()));

        Odontologo o = odontoRepo.findById(tDTO.getIdOdontologo())
                .orElseThrow(() -> new IllegalArgumentException("Odontólogo no encontrado con ID: " + tDTO.getIdOdontologo()));

        validarAgenda(tDTO, o);

        Paciente p = pacienteRepo.findById(tDTO.getIdPaciente())
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado con ID: " + tDTO.getIdPaciente()));

        turnoMapper.updateEntityFromDTO(tDTO, existing);
        existing.setOdonto(o);
        existing.setPacien(p);
        turnoRepo.save(existing);
    }

    @Override
    public List<TurnoDTO> getTurnosByOdontologo(Long odontoId) {
        return turnoRepo.findByOdontoId(odontoId).stream()
                .map(turnoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TurnoDTO> getProximosTurnosByOdontologo(Long odontoId) {
        return turnoRepo.findByOdontoIdAndFechaTurnoGreaterThanEqual(odontoId, LocalDate.now()).stream()
                .map(turnoMapper::toDTO)
                .collect(Collectors.toList());
    }
}
