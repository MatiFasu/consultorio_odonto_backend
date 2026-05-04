package com.ConsultorioOdontologico.consultorioOdontologico.service;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.TurnoDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Turno;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Paciente;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Odontologo;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.ITurnoRepository;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.IPacienteRepository;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.IOdontologoRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TurnoService implements ITurnoService {
    
    @Autowired
    private ITurnoRepository turnoRepo;
    
    @Autowired
    private IPacienteRepository pacienteRepo;
    
    @Autowired
    private IOdontologoRepository odontoRepo;

    @Override
    public List<TurnoDTO> getTurnos() {
        return turnoRepo.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void saveTurno(TurnoDTO tDTO) {
        // 1. Validar y recuperar Paciente real de la BD
        if (tDTO.getIdPaciente() == null) {
            throw new RuntimeException("Error: El ID del paciente es obligatorio.");
        }
        Paciente p = pacienteRepo.findById(tDTO.getIdPaciente())
                .orElseThrow(() -> new RuntimeException("Error: No se encontró al paciente con ID: " + tDTO.getIdPaciente()));

        // 2. Validar y recuperar Odontologo real de la BD
        if (tDTO.getIdOdontologo() == null) {
            throw new RuntimeException("Error: El ID del odontólogo es obligatorio.");
        }
        Odontologo o = odontoRepo.findById(tDTO.getIdOdontologo())
                .orElseThrow(() -> new RuntimeException("Error: No se encontró al odontólogo con ID: " + tDTO.getIdOdontologo()));
        
        // 3. VALIDACIÓN DE HORARIO (No permitir turnos en el pasado)
        java.time.LocalDate hoy = java.time.LocalDate.now();
        if (tDTO.getFecha_turno().isBefore(hoy)) {
            throw new RuntimeException("Error: No se pueden agendar turnos en fechas pasadas.");
        }
        
        if (tDTO.getFecha_turno().isEqual(hoy)) {
            java.time.LocalTime ahora = java.time.LocalTime.now();
            java.time.LocalTime horaTurno = java.time.LocalTime.parse(tDTO.getHora_turno());
            if (horaTurno.isBefore(ahora)) {
                throw new RuntimeException("Error: No se puede agendar un turno para una hora que ya pasó (" + tDTO.getHora_turno() + ").");
            }
        }

        // 4. VALIDACIÓN DE HORARIO LABORAL
        if (o.getUnHorario() != null) {
            String horaTurno = tDTO.getHora_turno(); 
            String inicio = o.getUnHorario().getHorario_inicio();
            String fin = o.getUnHorario().getHorario_final();
            
            if (horaTurno != null && inicio != null && fin != null) {
                if (horaTurno.compareTo(inicio) < 0 || horaTurno.compareTo(fin) >= 0) {
                    throw new RuntimeException("Error: El odontólogo Dr. " + o.getApellido() + 
                        " no trabaja en ese horario. Su jornada es de " + inicio + " a " + fin);
                }
            }
        }

        // 5. VALIDACIÓN DE SUPERPOSICIÓN
        List<Turno> turnosEnEseHorario = turnoRepo.findByFechaTurno(tDTO.getFecha_turno());
        boolean yaExiste = turnosEnEseHorario.stream()
            .anyMatch(existente -> 
                existente.getOdonto().getId().equals(o.getId()) && 
                existente.getHora_turno().equals(tDTO.getHora_turno()) &&
                (tDTO.getId_turno() == null || !existente.getId_turno().equals(tDTO.getId_turno()))
            );

        if (yaExiste) {
            throw new RuntimeException("Error: El Dr. " + o.getApellido() + 
                " ya tiene un turno agendado para el " + tDTO.getFecha_turno() + " a las " + tDTO.getHora_turno());
        }
        
        // 6. Convertir y Guardar
        Turno t = convertToEntity(tDTO);
        t.setPacien(p);
        t.setOdonto(o);
        turnoRepo.save(t);
    }

    @Override
    public void deleteTurno(Long id) {
        turnoRepo.deleteById(id);
    }

    @Override
    public TurnoDTO findTurno(Long id) {
        Turno t = turnoRepo.findById(id).orElse(null);
        return (t != null) ? convertToDTO(t) : null;
    }

    @Override
    @Transactional
    public void editTurno(TurnoDTO t) {
        this.saveTurno(t);
    }

    @Override
    public List<TurnoDTO> getTurnosByOdontologo(Long odontoId) {
        return turnoRepo.findByOdontoId(odontoId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TurnoDTO> getProximosTurnosByOdontologo(Long odontoId) {
        return turnoRepo.findByOdontoIdAndFechaTurnoGreaterThanEqual(odontoId, java.time.LocalDate.now()).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Mappers
    private TurnoDTO convertToDTO(Turno t) {
        TurnoDTO dto = new TurnoDTO();
        dto.setId_turno(t.getId_turno());
        dto.setFecha_turno(t.getFecha_turno());
        dto.setHora_turno(t.getHora_turno());
        dto.setAfeccion(t.getAfeccion());
        if (t.getPacien() != null) {
            dto.setIdPaciente(t.getPacien().getId());
            dto.setNombrePaciente(t.getPacien().getNombre() + " " + t.getPacien().getApellido());
            dto.setTelefonoPaciente(t.getPacien().getTelefono());
        }
        if (t.getOdonto() != null) {
            dto.setIdOdontologo(t.getOdonto().getId());
            dto.setNombreOdontologo(t.getOdonto().getNombre() + " " + t.getOdonto().getApellido());
            dto.setTelefonoOdontologo(t.getOdonto().getTelefono());
        }
        return dto;
    }

    private Turno convertToEntity(TurnoDTO dto) {
        Turno t = new Turno();
        t.setId_turno(dto.getId_turno());
        t.setFecha_turno(dto.getFecha_turno());
        t.setHora_turno(dto.getHora_turno());
        t.setAfeccion(dto.getAfeccion());
        // El paciente y odontólogo se cargan en el saveTurno por ID
        return t;
    }
}
