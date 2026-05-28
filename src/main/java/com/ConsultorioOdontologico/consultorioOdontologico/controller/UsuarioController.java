package com.ConsultorioOdontologico.consultorioOdontologico.controller;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.LoginDto;
import com.ConsultorioOdontologico.consultorioOdontologico.dto.UsuarioDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
@Tag(name = "Usuario", description = "Endpoints para la gestión de usuarios y autenticación")
public class UsuarioController {
   
    private final UsuarioService usuServ;
    
    @GetMapping("/traer")
    @Operation(summary = "Obtener todos los usuarios")
    public List<UsuarioDTO> getUsuarios() {
        return usuServ.getUsuario();
    }
    
    @GetMapping("/traer/{id}")
    @Operation(summary = "Buscar un usuario por ID")
    public UsuarioDTO getUsuario(@PathVariable Long id) {
        return usuServ.findUsuario(id);
    }
    
    @PostMapping("/crear")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Crear un nuevo usuario")
    public ResponseEntity<Long> saveUsuario(@Valid @RequestBody UsuarioDTO u) {
        UsuarioDTO usuarioGuardado = usuServ.saveUsuario(u);
        return new ResponseEntity<>(usuarioGuardado.getId(), HttpStatus.CREATED);
    }
    
    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión y obtener token JWT")
    public ResponseEntity<?> login(@RequestBody LoginDto l) {
        Map<String, Object> response = usuServ.login(l);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }
    }
    
    @DeleteMapping("/borrar/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Eliminar un usuario")
    public ResponseEntity<String> deleteUsuario(@PathVariable Long id) {
        usuServ.deleteUsuario(id);
        return ResponseEntity.ok("Usuario borrado correctamente!");
    }
    
    @PutMapping("/editar")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Editar datos de un usuario")
    public ResponseEntity<String> editUsuario(@Valid @RequestBody UsuarioDTO u) {
        usuServ.editUsuario(u);
        return ResponseEntity.ok("Usuario editado correctamente!");
    }
}
