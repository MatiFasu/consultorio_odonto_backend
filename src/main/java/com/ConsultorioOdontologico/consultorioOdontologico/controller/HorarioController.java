    
package com.ConsultorioOdontologico.consultorioOdontologico.controller;


import com.ConsultorioOdontologico.consultorioOdontologico.model.Horario;
import com.ConsultorioOdontologico.consultorioOdontologico.service.IHorarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class HorarioController {
    
    @Autowired
    private IHorarioService horarioServ;
    
    /*@CrossOrigin("http://127.0.0.1:5500")*/
    @GetMapping("/horario/traer")
    public List<Horario> getHorarios() {
        return horarioServ.getHorarios();
    }
    
    @GetMapping("/horario/traer/{id}")
    public Horario getHorario(@PathVariable Long id) {
        return horarioServ.findHorario(id);
    }
    
    @PostMapping("/horario/crear")
    public Long saveHorario(@RequestBody Horario h) {
        // Guardar el nuevo usuario en la base de datos
        Horario horarioGuardado = horarioServ.saveHorario(h);
        
        // Obtener el ID del usuario guardado
        Long idHorario = horarioGuardado.getId_horario();
        
        // Devolver el ID en la respuesta
        return idHorario;
    }
    
    @DeleteMapping("/horario/borrar/{id}")
    public String deleteHorario(@PathVariable Long id) {
        horarioServ.deleteHorario(id);
        return "Horario borrado correctamente!";
    }
    
    @PutMapping("/horario/editar")
    public String editHorario(@RequestBody Horario h) {
        horarioServ.editHorario(h);
        return "Horario editado correctamente!";
    }
    
}
