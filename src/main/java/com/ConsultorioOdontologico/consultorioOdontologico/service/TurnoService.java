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
        // El campo en Persona.java es 'id', no 'id_persona'
        if (t.getPacien() != null && t.getPacien().getId() != null) {
            Paciente p = pacienteRepo.findById(t.getPacien().getId()).orElse(null);
            t.setPacien(p);
        }
        
        if (t.getOdonto() != null && t.getOdonto().getId() != null) {
            Odontologo o = odontoRepo.findById(t.getOdonto().getId()).orElse(null);
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
}
