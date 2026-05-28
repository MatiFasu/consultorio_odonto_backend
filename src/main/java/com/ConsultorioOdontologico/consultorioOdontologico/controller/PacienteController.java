package com.ConsultorioOdontologico.consultorioOdontologico.controller;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.PacienteDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.service.IPacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paciente")
@RequiredArgsConstructor
@Tag(name = "Paciente", description = "Endpoints para la gestión de pacientes")
public class PacienteController {
    
    private final IPacienteService pacServ;

    @GetMapping("/traer")
    @Operation(summary = "Obtener todos los pacientes")
    public List<PacienteDTO> getPacientes() {
        return pacServ.getPacientes();
    }

    @GetMapping("/traer/paginado")
    @Operation(summary = "Obtener pacientes de forma paginada")
    public Page<PacienteDTO> getPacientesPaginated(Pageable pageable) {
        return pacServ.getPacientesPaginated(pageable);
    }
    
    @GetMapping("/traer/{id}")
    @Operation(summary = "Buscar un paciente por ID")
    public PacienteDTO findPaciente(@PathVariable Long id) {
        return pacServ.findPaciente(id);
    }
    
    @PostMapping("/crear")
    @Operation(summary = "Crear un nuevo paciente")
    public ResponseEntity<PacienteDTO> savePaciente(@Valid @RequestBody PacienteDTO pac) {
        PacienteDTO saved = pacServ.savePaciente(pac);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
    
    @DeleteMapping("/eliminar/{id}")
    @Operation(summary = "Eliminar un paciente")
    public ResponseEntity<String> deletePaciente(@PathVariable Long id) {
        pacServ.deletePaciente(id);
        return ResponseEntity.ok("Paciente eliminado correctamente!");
    }
    
    @PutMapping("/editar")
    @Operation(summary = "Editar un paciente existente")
    public ResponseEntity<String> editPaciente(@Valid @RequestBody PacienteDTO pac) {
        pacServ.editPaciente(pac);
        return ResponseEntity.ok("Paciente editado correctamente!");
    }
    
}
