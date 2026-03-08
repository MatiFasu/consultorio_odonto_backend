
package com.ConsultorioOdontologico.consultorioOdontologico.controller;

import com.ConsultorioOdontologico.consultorioOdontologico.model.Turno;
import com.ConsultorioOdontologico.consultorioOdontologico.service.ITurnoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TurnoController {
    
    @Autowired
    private ITurnoService turnoServ;
    
    @GetMapping("/turno/traer")
    public List<Turno> getTurnos() {
        return turnoServ.getTurnos();
    }
    
    @GetMapping("/turno/traer/{id}")
    public Turno getTurno(@PathVariable Long id) {
        return turnoServ.findTurno(id);
    }
    
    @GetMapping("/turno/odontologo/{id}")
    public List<Turno> getTurnosByOdontologo(@PathVariable Long id) {
        return turnoServ.getTurnosByOdontologo(id);
    }
    
    @GetMapping("/turno/odontologo/{id}/proximos")
    public List<Turno> getProximosTurnosByOdontologo(@PathVariable Long id) {
        return turnoServ.getProximosTurnosByOdontologo(id);
    }
    
    @PostMapping("/turno/crear")
    public String saveTurno(@RequestBody Turno t) {
        turnoServ.saveTurno(t);
        return "Turno creado correctamente!";
    }
    
    @DeleteMapping("/turno/borrar/{id}")
    public String deleteTurno(@PathVariable Long id) {
        turnoServ.deleteTurno(id);
        return "Turno borrado correctamente!";
    }
    
    @PutMapping("/turno/editar")
    public String editTurno(@RequestBody Turno t) {
        turnoServ.editTurno(t);
        return "Turno editado correctamente!";
    }
    
}
