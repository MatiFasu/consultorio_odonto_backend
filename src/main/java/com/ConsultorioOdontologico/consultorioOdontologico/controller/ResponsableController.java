
package com.ConsultorioOdontologico.consultorioOdontologico.controller;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.ResponsableDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.service.IResponsableService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Responsable", description = "Endpoints para la gestión de responsables")
public class ResponsableController {
    
    private final IResponsableService respoServ;
    
    @GetMapping("/responsable/traer")
    @Operation(summary = "Obtener todos los responsables")
    public List<ResponsableDTO> getResponsables() {
        return respoServ.getResponsables();
    }
    
    @GetMapping("/responsable/traer/{id}")
    @Operation(summary = "Buscar un responsable por ID")
    public ResponsableDTO getResponsable(@PathVariable Long id) {
        return respoServ.findResponsable(id);
    }
    
    @PostMapping("/responsable/crear")
    @Operation(summary = "Crear un nuevo responsable")
    public ResponseEntity<String> saveResponsable(@Valid @RequestBody ResponsableDTO r) {
        respoServ.saveResponsable(r);
        return new ResponseEntity<>("Responsable creado correctamente!", HttpStatus.CREATED);
    }
    
    @DeleteMapping("/responsable/borrar/{id}")
    @Operation(summary = "Eliminar un responsable")
    public ResponseEntity<String> deleteResponsable(@PathVariable Long id) {
        respoServ.deleteResponsable(id);
        return ResponseEntity.ok("Responsable borrado correctamente!");
    }
    
    @PutMapping("/responsable/editar")
    @Operation(summary = "Editar un responsable existente")
    public ResponseEntity<String> editResponsable(@Valid @RequestBody ResponsableDTO r) {
        respoServ.editResponsable(r);
        return ResponseEntity.ok("Responsable editado correctamente!");
    }
    
}

