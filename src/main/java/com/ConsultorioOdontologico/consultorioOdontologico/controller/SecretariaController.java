package com.ConsultorioOdontologico.consultorioOdontologico.controller;

import com.ConsultorioOdontologico.consultorioOdontologico.model.Secretaria;
import com.ConsultorioOdontologico.consultorioOdontologico.service.ISecretariaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class SecretariaController {
    
    @Autowired
    private ISecretariaService secreServ;

    @GetMapping("/secretaria/traer")
    public List<Secretaria> getSecretarias() {
        return secreServ.getSecretarias();
    }
    
    @GetMapping("/secretaria/traer/{id}")
    public Secretaria getSecretaria(@PathVariable Long id) {
        return secreServ.findSecretaria(id);
    }
    
    @PostMapping("/secretaria/crear")
    public String saveSecretaria(@RequestBody Secretaria s) {
        secreServ.saveSecretaria(s);
        return "Secretaria creado correctamente!";
    }
    
    @DeleteMapping("/secretaria/borrar/{id}")
    public String deleteSecretaria(@PathVariable Long id) {
        secreServ.deleteSecretaria(id);
        return "Secretaria borrado correctamente!";
    }
    
    @PutMapping("/secretaria/editar")
    public String editSecretaria(@RequestBody Secretaria s) {
        secreServ.editSecretaria(s);
        return "Secretaria editado correctamente!";
    }
}
