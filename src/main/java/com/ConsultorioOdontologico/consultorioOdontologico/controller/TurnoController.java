
package com.ConsultorioOdontologico.consultorioOdontologico.controller;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.TurnoDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.service.ITurnoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Turno", description = "Endpoints para la gestión de turnos/citas")
public class TurnoController {
    
    @Autowired
    private ITurnoService turnoServ;
    
    @GetMapping("/turno/traer")
    @Operation(summary = "Obtener todos los turnos")
    public List<TurnoDTO> getTurnos() {
        return turnoServ.getTurnos();
    }
    
    @GetMapping("/turno/traer/{id}")
    @Operation(summary = "Buscar un turno por ID")
    public TurnoDTO getTurno(@PathVariable Long id) {
        return turnoServ.findTurno(id);
    }
    
    @GetMapping("/turno/odontologo/{id}")
    @Operation(summary = "Obtener turnos de un odontólogo específico")
    public List<TurnoDTO> getTurnosByOdontologo(@PathVariable Long id) {
        return turnoServ.getTurnosByOdontologo(id);
    }
    
    @GetMapping("/turno/odontologo/{id}/proximos")
    @Operation(summary = "Obtener próximos turnos de un odontólogo")
    public List<TurnoDTO> getProximosTurnosByOdontologo(@PathVariable Long id) {
        return turnoServ.getProximosTurnosByOdontologo(id);
    }
    
    @PostMapping("/turno/crear")
    @Operation(summary = "Agendar un nuevo turno")
    public ResponseEntity<String> saveTurno(@Valid @RequestBody TurnoDTO t) {
        turnoServ.saveTurno(t);
        return new ResponseEntity<>("Turno creado correctamente!", HttpStatus.CREATED);
    }
    
    @DeleteMapping("/turno/borrar/{id}")
    @Operation(summary = "Cancelar/Eliminar un turno")
    public ResponseEntity<String> deleteTurno(@PathVariable Long id) {
        turnoServ.deleteTurno(id);
        return ResponseEntity.ok("Turno borrado correctamente!");
    }
    
    @PutMapping("/turno/editar")
    @Operation(summary = "Modificar un turno existente")
    public ResponseEntity<String> editTurno(@Valid @RequestBody TurnoDTO t) {
        turnoServ.editTurno(t);
        return ResponseEntity.ok("Turno editado correctamente!");
    }
}
