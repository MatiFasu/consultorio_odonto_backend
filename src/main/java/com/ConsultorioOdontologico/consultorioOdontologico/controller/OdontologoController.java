package com.ConsultorioOdontologico.consultorioOdontologico.controller;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.OdontologoDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.service.IOdontologoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/odontologo")
@RequiredArgsConstructor
@Tag(name = "Odontologo", description = "Endpoints para la gestión de odontólogos")
public class OdontologoController {
    
    private final IOdontologoService odontoService;
    
    @GetMapping("/traer")
    @Operation(summary = "Obtener todos los odontólogos")
    public List<OdontologoDTO> getOdontologos() {
        return odontoService.getOdontologos();
    }
    
    @GetMapping("/traer/{id}")
    @Operation(summary = "Buscar un odontólogo por ID")
    public OdontologoDTO getOdontologo(@PathVariable Long id) {
        return odontoService.findOdontologo(id);
    }
    
    @GetMapping("/usuario/{userId}")
    @Operation(summary = "Buscar un odontólogo por el ID de su usuario")
    public OdontologoDTO getOdontologoByUsuario(@PathVariable Long userId) {
        return odontoService.findByUserId(userId);
    }
    
    @PostMapping("/crear")
    @Operation(summary = "Registrar un nuevo odontólogo")
    public ResponseEntity<String> saveOdontologo(@Valid @RequestBody OdontologoDTO odonto) {
        odontoService.saveOdontologo(odonto);
        return new ResponseEntity<>("Odontologo creado correctamente!", HttpStatus.CREATED);
    }
    
    @DeleteMapping("/eliminar/{id}")
    @Operation(summary = "Eliminar un odontólogo")
    public ResponseEntity<String> deleteOdontologo(@PathVariable Long id) {
        odontoService.deleteOdontologo(id);
        return ResponseEntity.ok("Odontologo eliminado correctamente!");
    }
    
    @PutMapping("/editar")
    @Operation(summary = "Editar datos de un odontólogo")
    public ResponseEntity<String> editOdontologo(@Valid @RequestBody OdontologoDTO odonto) {
        odontoService.editOdontologo(odonto);
        return ResponseEntity.ok("Odontologo editado correctamente!");
    }
}
