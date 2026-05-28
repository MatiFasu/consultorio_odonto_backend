package com.ConsultorioOdontologico.consultorioOdontologico.controller;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.RegistroClinicoDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.service.IRegistroClinicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registro-clinico")
@RequiredArgsConstructor
@Tag(name = "Registro Clínico", description = "Endpoints para la historia clínica de los pacientes")
public class RegistroClinicoController {

    private final IRegistroClinicoService registroServ;

    @GetMapping("/traer")
    @Operation(summary = "Obtener todos los registros clínicos")
    public List<RegistroClinicoDTO> getRegistros() {
        return registroServ.getRegistros();
    }

    @GetMapping("/paciente/{id}")
    @Operation(summary = "Obtener el historial clínico de un paciente")
    public List<RegistroClinicoDTO> getHistorialByPaciente(@PathVariable Long id) {
        return registroServ.getRegistrosByPaciente(id);
    }

    @GetMapping("/traer/{id}")
    @Operation(summary = "Buscar un registro clínico por ID")
    public RegistroClinicoDTO getRegistro(@PathVariable Long id) {
        return registroServ.findRegistro(id);
    }

    @PostMapping("/crear")
    @Operation(summary = "Crear un nuevo registro clínico")
    public ResponseEntity<RegistroClinicoDTO> saveRegistro(@Valid @RequestBody RegistroClinicoDTO registroDTO) {
        registroServ.saveRegistro(registroDTO);
        // En una implementación real, saveRegistro podría devolver el objeto guardado para obtener el ID
        // Para simplificar esta refactorización, asumimos que el frontend refrescará la lista.
        return new ResponseEntity<>(registroDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/eliminar/{id}")
    @Operation(summary = "Eliminar un registro clínico")
    public ResponseEntity<String> deleteRegistro(@PathVariable Long id) {
        registroServ.deleteRegistro(id);
        return ResponseEntity.ok("Registro clínico eliminado correctamente");
    }

    @PutMapping("/editar")
    @Operation(summary = "Editar un registro clínico existente")
    public ResponseEntity<String> editRegistro(@Valid @RequestBody RegistroClinicoDTO registroDTO) {
        registroServ.editRegistro(registroDTO);
        return ResponseEntity.ok("Registro clínico editado correctamente");
    }
}
