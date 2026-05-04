package com.ConsultorioOdontologico.consultorioOdontologico.controller;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.LoginDto;
import com.ConsultorioOdontologico.consultorioOdontologico.dto.UsuarioDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.service.UsuarioService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class UsuarioController {
   
    @Autowired
    private UsuarioService usuServ;
    
    @GetMapping("/usuario/traer")
    public List<UsuarioDTO> getUsuarios() {
        return usuServ.getUsuario();
    }
    
    @GetMapping("/usuario/traer/{id}")
    public UsuarioDTO getUsuario(@PathVariable Long id) {
        return usuServ.findUsuario(id);
    }
    
    @PostMapping("/usuario/crear")
    public Long saveUsuario(@RequestBody UsuarioDTO u) {
        UsuarioDTO usuarioGuardado = usuServ.saveUsuario(u);
        return usuarioGuardado.getId_usuario();
    }
    
    @PostMapping("/usuario/login")
    public ResponseEntity<?> login(@RequestBody LoginDto l) {
        Map<String, Object> response = usuServ.login(l);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }
    }
    
    @DeleteMapping("/usuario/borrar/{id}")
    public String deleteUsuario(@PathVariable Long id) {
        usuServ.deleteUsuario(id);
        return "Usuario borrado correctamente!";
    }
    
    @PutMapping("/usuario/editar")
    public String editUsuario(@RequestBody UsuarioDTO u) {
        usuServ.editUsuario(u);
        return "Usuario editado correctamente!";
    }
}
