
package com.ConsultorioOdontologico.consultorioOdontologico.service;

import com.ConsultorioOdontologico.consultorioOdontologico.model.Odontologo;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.IOdontologoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OdontologoService implements IOdontologoService{
    
    @Autowired
    private IOdontologoRepository odontoRepo;

    @Override
    public List<Odontologo> getOdontologos() {
        return odontoRepo.findAll();
    }

    @Override
    public void saveOdontologo(Odontologo o) {
        odontoRepo.save(o);
    }

    @Override
    public void deleteOdontologo(Long id) {
        odontoRepo.deleteById(id);
    }

    @Override
    public Odontologo findOdontologo(Long id) {
        return odontoRepo.findById(id).orElse(null);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public void editOdontologo(Odontologo o) {
        // Obtenemos el ID de Persona (se hereda como 'id' en el modelo)
        Long idPersona = o.getId();
        Odontologo existing = odontoRepo.findById(idPersona).orElse(null);
        
        if (existing != null) {
            // Actualizar campos si vienen en el objeto o
            if (o.getNombre() != null) existing.setNombre(o.getNombre());
            if (o.getApellido() != null) existing.setApellido(o.getApellido());
            if (o.getDni() != null) existing.setDni(o.getDni());
            if (o.getTelefono() != null) existing.setTelefono(o.getTelefono());
            if (o.getDireccion() != null) existing.setDireccion(o.getDireccion());
            if (o.getFecha_nac() != null) existing.setFecha_nac(o.getFecha_nac());
            if (o.getEspecialidad() != null) existing.setEspecialidad(o.getEspecialidad());
            if (o.getUnHorario() != null) existing.setUnHorario(o.getUnHorario());
            if (o.getUnUsuario() != null) existing.setUnUsuario(o.getUnUsuario());
            
            odontoRepo.save(existing);
        } else {
            // Si por alguna razón no existe, lo guardamos como nuevo
            odontoRepo.save(o);
        }
    }

    @Override
    public Odontologo findByUserId(Long userId) {
        return odontoRepo.findByUsuarioId(userId).orElse(null);
    }
}
