    
package com.ConsultorioOdontologico.consultorioOdontologico.controller;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.HorarioDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.service.IHorarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@Tag(name = "Horario", description = "Endpoints para la gestión de horarios")
public class HorarioController {
    
    @Autowired
    private IHorarioService horarioServ;

    @GetMapping("/horario/traer")
    @Operation(summary = "Obtener todos los horarios")
    public List<HorarioDTO> getHorarios() {
        return horarioServ.getHorarios();
    }
    
    @GetMapping("/horario/traer/{id}")
    @Operation(summary = "Buscar un horario por ID")
    public HorarioDTO getHorario(@PathVariable Long id) {
        return horarioServ.findHorario(id);
    }
    
    @PostMapping("/horario/crear")
    @Operation(summary = "Crear un nuevo horario")
    public ResponseEntity<Long> saveHorario(@RequestBody HorarioDTO h) {
        HorarioDTO horarioGuardado = horarioServ.saveHorario(h);
        return new ResponseEntity<>(horarioGuardado.getId_horario(), HttpStatus.CREATED);
    }
    
    @DeleteMapping("/horario/borrar/{id}")
    @Operation(summary = "Eliminar un horario")
    public ResponseEntity<String> deleteHorario(@PathVariable Long id) {
        horarioServ.deleteHorario(id);
        return ResponseEntity.ok("Horario borrado correctamente!");
    }
    
    @PutMapping("/horario/editar")
    @Operation(summary = "Editar un horario existente")
    public ResponseEntity<String> editHorario(@RequestBody HorarioDTO h) {
        horarioServ.editHorario(h);
        return ResponseEntity.ok("Horario editado correctamente!");
    }
    
}
