package com.ConsultorioOdontologico.consultorioOdontologico.controller;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.SecretariaDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.service.ISecretariaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/secretaria")
@RequiredArgsConstructor
@Tag(name = "Secretaria", description = "Endpoints para la gestión administrativa")
public class SecretariaController {
    
    private final ISecretariaService secreServ;

    @GetMapping("/traer")
    @Operation(summary = "Obtener todas las secretarias")
    public List<SecretariaDTO> getSecretarias() {
        return secreServ.getSecretarias();
    }
    
    @GetMapping("/traer/{id}")
    @Operation(summary = "Buscar una secretaria por ID")
    public SecretariaDTO getSecretaria(@PathVariable Long id) {
        return secreServ.findSecretaria(id);
    }
    
    @PostMapping("/crear")
    @Operation(summary = "Registrar una nueva secretaria")
    public ResponseEntity<String> saveSecretaria(@Valid @RequestBody SecretariaDTO s) {
        secreServ.saveSecretaria(s);
        return new ResponseEntity<>("Secretaria creada correctamente!", HttpStatus.CREATED);
    }
    
    @DeleteMapping("/borrar/{id}")
    @Operation(summary = "Eliminar una secretaria")
    public ResponseEntity<String> deleteSecretaria(@PathVariable Long id) {
        secreServ.deleteSecretaria(id);
        return ResponseEntity.ok("Secretaria eliminada correctamente!");
    }
    
    @PutMapping("/editar")
    @Operation(summary = "Editar datos de una secretaria")
    public ResponseEntity<String> editSecretaria(@Valid @RequestBody SecretariaDTO s) {
        secreServ.editSecretaria(s);
        return ResponseEntity.ok("Secretaria editada correctamente!");
    }
}
