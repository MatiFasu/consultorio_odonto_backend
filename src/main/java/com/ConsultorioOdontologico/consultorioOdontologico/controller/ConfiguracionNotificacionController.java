package com.ConsultorioOdontologico.consultorioOdontologico.controller;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.ConfiguracionNotificacionDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.service.ConfiguracionNotificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/config-notificaciones")
@CrossOrigin(origins = "*")
@Tag(name = "Configuración Notificaciones", description = "Endpoints para la gestión de la configuración de notificaciones")
public class ConfiguracionNotificacionController {

    @Autowired
    private ConfiguracionNotificacionService configService;

    @GetMapping
    @Operation(summary = "Obtener la configuración actual de notificaciones")
    public ConfiguracionNotificacionDTO getConfig() {
        return configService.getConfig();
    }

    @PutMapping
    @Operation(summary = "Actualizar la configuración de notificaciones")
    public ConfiguracionNotificacionDTO updateConfig(@RequestBody ConfiguracionNotificacionDTO config) {
        return configService.updateConfig(config);
    }
}
