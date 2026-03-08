package com.ConsultorioOdontologico.consultorioOdontologico.service;

import com.ConsultorioOdontologico.consultorioOdontologico.model.Turno;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Paciente;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Odontologo;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.ITurnoRepository;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.IPacienteRepository;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.IOdontologoRepository;
import java.util.List;
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
    public List<Turno> getTurnos() {
        return turnoRepo.findAll();
    }

    @Override
    @Transactional
    public void saveTurno(Turno t) {
        // Recuperar paciente y odontólogo para asegurar que existan y tengan datos completos
        if (t.getPacien() != null && (t.getPacien().getId() != null)) {
            Paciente p = pacienteRepo.findById(t.getPacien().getId()).orElse(null);
            t.setPacien(p);
        }
        
        if (t.getOdonto() != null && (t.getOdonto().getId() != null)) {
            Odontologo o = odontoRepo.findById(t.getOdonto().getId()).orElse(null);
            
            // VALIDACIÓN DE HORARIO LABORAL
            if (o != null && o.getUnHorario() != null) {
                String horaTurno = t.getHora_turno(); // Formato esperado "HH:mm"
                String inicio = o.getUnHorario().getHorario_inicio();
                String fin = o.getUnHorario().getHorario_final();
                
                if (horaTurno != null && inicio != null && fin != null) {
                    if (horaTurno.compareTo(inicio) < 0 || horaTurno.compareTo(fin) >= 0) {
                        throw new RuntimeException("Error: El odontólogo Dr. " + o.getApellido() + 
                            " no trabaja en ese horario. Su jornada es de " + inicio + " a " + fin);
                    }
                }
            }

            // VALIDACIÓN DE SUPERPOSICIÓN (No dos pacientes a la misma hora)
            if (o != null) {
                boolean yaExiste = turnoRepo.existsByOdontoIdAndFechaTurnoAndHoraTurno(
                    o.getId(), t.getFecha_turno(), t.getHora_turno()
                );
                if (yaExiste) {
                    throw new RuntimeException("Error: El Dr. " + o.getApellido() + 
                        " ya tiene un turno agendado para el " + t.getFecha_turno() + " a las " + t.getHora_turno());
                }
            }
            
            t.setOdonto(o);
        }
        
        if (t.getPacien() != null && t.getOdonto() != null) {
            turnoRepo.save(t);
        } else {
            throw new RuntimeException("Error: No se pudo vincular el paciente o el odontólogo. Verifica los IDs recibidos.");
        }
    }

    @Override
    public void deleteTurno(Long id) {
        turnoRepo.deleteById(id);
    }

    @Override
    public Turno findTurno(Long id) {
        return turnoRepo.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void editTurno(Turno t) {
        this.saveTurno(t);
    }

    @Override
    public List<Turno> getTurnosByOdontologo(Long odontoId) {
        return turnoRepo.findByOdontoId(odontoId);
    }

    @Override
    public List<Turno> getProximosTurnosByOdontologo(Long odontoId) {
        return turnoRepo.findByOdontoIdAndFechaTurnoGreaterThanEqual(odontoId, java.time.LocalDate.now());
    }
}
