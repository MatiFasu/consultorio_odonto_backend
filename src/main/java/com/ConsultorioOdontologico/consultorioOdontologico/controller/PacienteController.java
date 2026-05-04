
package com.ConsultorioOdontologico.consultorioOdontologico.controller;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.PacienteDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.service.IPacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag(name = "Paciente", description = "Endpoints para la gestión de pacientes")
public class PacienteController {
    
    @Autowired
    private IPacienteService pacServ;

    @GetMapping("/paciente/traer")
    @Operation(summary = "Obtener todos los pacientes")
    public List<PacienteDTO> getPacientes() {
        return pacServ.getPacientes();
    }
    
    @GetMapping("/paciente/traer/{id}")
    @Operation(summary = "Buscar un paciente por ID")
    public PacienteDTO getPaciente(@PathVariable Long id) {
        return pacServ.findPaciente(id);
    }
    
    @PostMapping("/paciente/crear")
    @Operation(summary = "Crear un nuevo paciente")
    public ResponseEntity<String> savePaciente(@Valid @RequestBody PacienteDTO pac) {
        pacServ.savePaciente(pac);
        return new ResponseEntity<>("Paciente creado correctamente!", HttpStatus.CREATED);
    }
    
    @DeleteMapping("/paciente/eliminar/{id}")
    @Operation(summary = "Eliminar un paciente")
    public ResponseEntity<String> deletePaciente(@PathVariable Long id) {
        pacServ.deletePaciente(id);
        return ResponseEntity.ok("Paciente eliminado correctamente!");
    }
    
    @PutMapping("/paciente/editar")
    @Operation(summary = "Editar un paciente existente")
    public ResponseEntity<String> editPaciente(@Valid @RequestBody PacienteDTO pac) {
        pacServ.editPaciente(pac);
        return ResponseEntity.ok("Paciente editado correctamente!");
    }
    
}
