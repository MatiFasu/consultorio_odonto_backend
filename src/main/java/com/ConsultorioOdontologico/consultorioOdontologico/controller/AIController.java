package com.ConsultorioOdontologico.consultorioOdontologico.controller;

import com.ConsultorioOdontologico.consultorioOdontologico.service.ResumenClinicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    @Autowired
    private ResumenClinicoService resumenService;

    @GetMapping("/resumen-clinico/{pacienteId}")
    public ResponseEntity<String> getResumenClinico(@PathVariable Long pacienteId) {
        try {
            String resumen = resumenService.generarResumen(pacienteId);
            return ResponseEntity.ok(resumen);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al generar resumen con IA: " + e.getMessage());
        }
    }
}
