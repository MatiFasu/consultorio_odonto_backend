
package com.ConsultorioOdontologico.consultorioOdontologico.controller;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.RegistroClinicoDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.service.IRegistroClinicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registro-clinico")
@Tag(name = "Registro Clinico", description = "Endpoints para la historia clínica de los pacientes")
public class RegistroClinicoController {

    @Autowired
    private IRegistroClinicoService registroServ;

    @GetMapping("/paciente/{pacienteId}")
    @Operation(summary = "Obtener historial clínico de un paciente")
    public List<RegistroClinicoDTO> getHistorial(@PathVariable Long pacienteId) {
        return registroServ.getHistorialPorPaciente(pacienteId);
    }

    @PostMapping("/crear")
    @Operation(summary = "Crear o actualizar un registro en la historia clínica")
    public ResponseEntity<RegistroClinicoDTO> saveRegistro(@RequestBody RegistroClinicoDTO registro) {
        return new ResponseEntity<>(registroServ.saveRegistro(registro), HttpStatus.CREATED);
    }

    @DeleteMapping("/eliminar/{id}")
    @Operation(summary = "Eliminar un registro clínico")
    public ResponseEntity<String> deleteRegistro(@PathVariable Long id) {
        registroServ.deleteRegistro(id);
        return ResponseEntity.ok("Registro eliminado correctamente");
    }
}
