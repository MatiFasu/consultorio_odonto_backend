
package com.ConsultorioOdontologico.consultorioOdontologico.controller;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.OdontologoDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.service.IOdontologoService;
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
@Tag(name = "Odontologo", description = "Endpoints para la gestión de odontólogos")
public class OdontologoController {
    
    @Autowired
    private IOdontologoService odontoService;
    
    @GetMapping("/odontologo/traer")
    @Operation(summary = "Obtener todos los odontólogos")
    public List<OdontologoDTO> getOdontologos() {
        return odontoService.getOdontologos();
    }
    
    @GetMapping("/odontologo/traer/{id_odon}")
    @Operation(summary = "Buscar un odontólogo por ID")
    public OdontologoDTO getOdontologo(@PathVariable Long id_odon) {
        return odontoService.findOdontologo(id_odon);
    }
    
    @GetMapping("/odontologo/usuario/{userId}")
    @Operation(summary = "Buscar un odontólogo por el ID de su usuario")
    public OdontologoDTO getOdontologoByUsuario(@PathVariable Long userId) {
        return odontoService.findByUserId(userId);
    }
    
    @PostMapping("/odontologo/crear")
    @Operation(summary = "Registrar un nuevo odontólogo")
    public ResponseEntity<String> saveOdontologo(@Valid @RequestBody OdontologoDTO odonto) {
        odontoService.saveOdontologo(odonto);
        return new ResponseEntity<>("Odontologo creado correctamente!", HttpStatus.CREATED);
    }
    
    @DeleteMapping("/odontologo/eliminar/{id_odonto}")
    @Operation(summary = "Eliminar un odontólogo")
    public ResponseEntity<String> deleteOdontologo(@PathVariable Long id_odonto) {
        odontoService.deleteOdontologo(id_odonto);
        return ResponseEntity.ok("Odontologo eliminado correctamente!");
    }
    
    @PutMapping("/odontologo/editar")
    @Operation(summary = "Editar datos de un odontólogo")
    public ResponseEntity<String> editOdontologo(@Valid @RequestBody OdontologoDTO odonto) {
        odontoService.editOdontologo(odonto);
        return ResponseEntity.ok("Odontologo editado correctamente!");
    }
}
