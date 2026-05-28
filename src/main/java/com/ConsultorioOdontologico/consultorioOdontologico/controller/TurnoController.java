package com.ConsultorioOdontologico.consultorioOdontologico.controller;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.TurnoDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.service.ITurnoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Turno", description = "Endpoints para la gestión de turnos")
public class TurnoController {
    
    private final ITurnoService turnoServ;

    @GetMapping("/turno/traer")
    @Operation(summary = "Obtener todos los turnos")
    public List<TurnoDTO> getTurnos() {
        return turnoServ.getTurnos();
    }

    @GetMapping("/turno/traer/paginado")
    @Operation(summary = "Obtener turnos de forma paginada")
    public Page<TurnoDTO> getTurnosPaginated(Pageable pageable) {
        return turnoServ.getTurnosPaginated(pageable);
    }
    
    @GetMapping("/turno/traer/fecha")
    @Operation(summary = "Obtener turnos por fecha de forma paginada")
    public Page<TurnoDTO> getTurnosByFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha, 
            Pageable pageable) {
        return turnoServ.getTurnosByFechaPaginated(fecha, pageable);
    }
    
    @GetMapping("/turno/traer/{id}")
    @Operation(summary = "Buscar un turno por ID")
    public TurnoDTO getTurno(@PathVariable Long id) {
        return turnoServ.findTurno(id);
    }
    
    @PostMapping("/turno/crear")
    @Operation(summary = "Crear un nuevo turno")
    public ResponseEntity<String> saveTurno(@Valid @RequestBody TurnoDTO turno) {
        turnoServ.saveTurno(turno);
        return new ResponseEntity<>("Turno creado correctamente!", HttpStatus.CREATED);
    }
    
    @DeleteMapping("/turno/eliminar/{id}")
    @Operation(summary = "Eliminar un turno")
    public ResponseEntity<String> deleteTurno(@PathVariable Long id) {
        turnoServ.deleteTurno(id);
        return ResponseEntity.ok("Turno eliminado correctamente!");
    }
    
    @PutMapping("/turno/editar")
    @Operation(summary = "Editar un turno existente")
    public ResponseEntity<String> editTurno(@Valid @RequestBody TurnoDTO turno) {
        turnoServ.editTurno(turno);
        return ResponseEntity.ok("Turno editado correctamente!");
    }

    @GetMapping("/turno/odontologo/{id}")
    @Operation(summary = "Obtener todos los turnos de un odontólogo")
    public List<TurnoDTO> getByOdontologo(@PathVariable Long id) {
        return turnoServ.getTurnosByOdontologo(id);
    }

    @GetMapping("/turno/odontologo/{id}/proximos")
    @Operation(summary = "Obtener los próximos turnos de un odontólogo")
    public List<TurnoDTO> getProximosByOdontologo(@PathVariable Long id) {
        return turnoServ.getProximosTurnosByOdontologo(id);
    }
}
