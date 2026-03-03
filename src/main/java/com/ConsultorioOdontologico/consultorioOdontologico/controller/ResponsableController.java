
package com.ConsultorioOdontologico.consultorioOdontologico.controller;

import com.ConsultorioOdontologico.consultorioOdontologico.model.Responsable;
import com.ConsultorioOdontologico.consultorioOdontologico.service.IResponsableService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ResponsableController {
    
    @Autowired
    private IResponsableService respoServ;
    
    @GetMapping("/responsable/traer")
    public List<Responsable> getResponsables() {
        return respoServ.getResponsables();
    }
    
    @GetMapping("/responsable/traer/{id}")
    public Responsable getResponsable(@PathVariable Long id) {
        return respoServ.findResponsable(id);
    }
    
    @PostMapping("/responsable/crear")
    public String saveResponsable(@RequestBody Responsable r) {
        respoServ.saveResponsable(r);
        return "Responsable creado correctamente!";
    }
    
    @DeleteMapping("/responsable/borrar/{id}")
    public String deleteResponsable(@PathVariable Long id) {
        respoServ.deleteResponsable(id);
        return "Responsable borrado correctamente!";
    }
    
    @PutMapping("/responsable/editar")
    public String editResponsable(@RequestBody Responsable r) {
        respoServ.editResponsable(r);
        return "Responsable editado correctamente!";
    }
    
}
