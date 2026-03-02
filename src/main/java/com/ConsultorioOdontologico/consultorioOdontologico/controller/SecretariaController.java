
package com.ConsultorioOdontologico.consultorioOdontologico.controller;

import com.ConsultorioOdontologico.consultorioOdontologico.model.Secretaria;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Usuario;
import com.ConsultorioOdontologico.consultorioOdontologico.service.ISecretariaService;
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
public class SecretariaController {
    
    @Autowired
    private ISecretariaService secreServ;
    
    @CrossOrigin("http://127.0.0.1:5500")
    @GetMapping("/secretaria/traer")
    public List<Secretaria> getSecretarias() {
        return secreServ.getSecretarias();
    }
    
    @CrossOrigin("http://127.0.0.1:5500")
    @GetMapping("/secretaria/traer/{id}")
    public Secretaria getSecretaria(@PathVariable Long id) {
        return secreServ.findSecretaria(id);
    }
    
    @CrossOrigin("http://127.0.0.1:5500")
    @PostMapping("/secretaria/crear")
    public String saveSecretaria(@RequestBody Secretaria s) {
        secreServ.saveSecretaria(s);
        return "Secretaria creado correctamente!";
    }
    
    @CrossOrigin("http://127.0.0.1:5500")
    @DeleteMapping("/secretaria/borrar/{id}")
    public String deleteSecretaria(@PathVariable Long id) {
        secreServ.deleteSecretaria(id);
        return "Secretaria borrado correctamente!";
    }
    
    @CrossOrigin("http://127.0.0.1:5500")
    @PutMapping("/secretaria/editar")
    public String editUsuario(@RequestBody Secretaria s) {
        secreServ.editSecretaria(s);
        return "Secretaria editado correctamente!";
    }
    
    
}
