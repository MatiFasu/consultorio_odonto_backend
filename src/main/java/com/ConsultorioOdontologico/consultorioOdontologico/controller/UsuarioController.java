package com.ConsultorioOdontologico.consultorioOdontologico.controller;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.LoginDto;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Usuario;
import com.ConsultorioOdontologico.consultorioOdontologico.service.IUsuarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class UsuarioController {
   
    @Autowired
    private IUsuarioService usuServ;
    
    @GetMapping("/usuario/traer")
    public List<Usuario> getUsuarios() {
        return usuServ.getUsuario();
    }
    
    @GetMapping("/usuario/traer/{id}")
    public Usuario getUsuario(@PathVariable Long id) {
        return usuServ.findUsuario(id);
    }
    
    @PostMapping("/usuario/crear")
    public Long saveUsuario(@RequestBody Usuario u) {
        Usuario usuarioGuardado = usuServ.saveUsuario(u);
        return usuarioGuardado.getId_usuario();
    }
    
    @PostMapping("/usuario/login")
    public int login(@RequestBody LoginDto l) {
        return usuServ.validarUsuario(l);
    }
    
    @DeleteMapping("/usuario/borrar/{id}")
    public String deleteUsuario(@PathVariable Long id) {
        usuServ.deleteUsuario(id);
        return "Usuario borrado correctamente!";
    }
    
    @PutMapping("/usuario/editar")
    public String editUsuario(@RequestBody Usuario u) {
        usuServ.editUsuario(u);
        return "Usuario editado correctamente!";
    }
}
