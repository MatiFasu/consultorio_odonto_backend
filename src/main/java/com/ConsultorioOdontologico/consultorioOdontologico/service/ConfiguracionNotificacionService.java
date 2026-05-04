package com.ConsultorioOdontologico.consultorioOdontologico.service;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.ConfiguracionNotificacionDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.model.ConfiguracionNotificacion;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.IConfiguracionNotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class ConfiguracionNotificacionService {

    @Autowired
    private IConfiguracionNotificacionRepository configRepo;

    public ConfiguracionNotificacionDTO getConfig() {
        ConfiguracionNotificacion config = configRepo.findById(1L).orElseGet(() -> {
            // Configuración por defecto si no existe
            ConfiguracionNotificacion defaultRepo = new ConfiguracionNotificacion(
                1L, true, LocalTime.of(8, 0), 0, "MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY"
            );
            return configRepo.save(defaultRepo);
        });
        return convertToDTO(config);
    }

    public ConfiguracionNotificacionDTO updateConfig(ConfiguracionNotificacionDTO dto) {
        ConfiguracionNotificacion config = convertToEntity(dto);
        config.setId(1L); // Aseguramos que siempre sea el mismo registro
        return convertToDTO(configRepo.save(config));
    }

    private ConfiguracionNotificacionDTO convertToDTO(ConfiguracionNotificacion c) {
        ConfiguracionNotificacionDTO dto = new ConfiguracionNotificacionDTO();
        dto.setId(c.getId());
        dto.setActiva(c.isActiva());
        dto.setHorarioEnvio(c.getHorarioEnvio());
        dto.setDiasAnticipacion(c.getDiasAnticipacion());
        dto.setDiasEjecucion(c.getDiasEjecucion());
        return dto;
    }

    private ConfiguracionNotificacion convertToEntity(ConfiguracionNotificacionDTO dto) {
        ConfiguracionNotificacion c = new ConfiguracionNotificacion();
        c.setId(dto.getId());
        c.setActiva(dto.isActiva());
        c.setHorarioEnvio(dto.getHorarioEnvio());
        c.setDiasAnticipacion(dto.getDiasAnticipacion());
        c.setDiasEjecucion(dto.getDiasEjecucion());
        return c;
    }
}
