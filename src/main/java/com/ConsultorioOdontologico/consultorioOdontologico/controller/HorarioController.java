    
package com.ConsultorioOdontologico.consultorioOdontologico.controller;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.HorarioDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.service.IHorarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Horario", description = "Endpoints para la gestión de horarios")
public class HorarioController {

    private final IHorarioService horServ;

    @GetMapping("/horario/traer")
    @Operation(summary = "Obtener todos los horarios")
    public List<HorarioDTO> getHorarios() {
        return horServ.getHorarios();
    }

    @GetMapping("/horario/traer/{id}")
    @Operation(summary = "Buscar un horario por ID")
    public HorarioDTO getHorario(@PathVariable Long id) {
        return horServ.findHorario(id);
    }

    @PostMapping("/horario/crear")
    @Operation(summary = "Crear un nuevo horario")
    public ResponseEntity<HorarioDTO> saveHorario(@Valid @RequestBody HorarioDTO hor) {
        return new ResponseEntity<>(horServ.saveHorario(hor), HttpStatus.CREATED);
    }

    @DeleteMapping("/horario/eliminar/{id}")
    @Operation(summary = "Eliminar un horario")
    public ResponseEntity<String> deleteHorario(@PathVariable Long id) {
        horServ.deleteHorario(id);
        return ResponseEntity.ok("Horario eliminado correctamente!");
    }

    @PutMapping("/horario/editar")
    @Operation(summary = "Editar un horario existente")
    public ResponseEntity<String> editHorario(@Valid @RequestBody HorarioDTO hor) {
        horServ.editHorario(hor);
        return ResponseEntity.ok("Horario editado correctamente!");
    }
}

