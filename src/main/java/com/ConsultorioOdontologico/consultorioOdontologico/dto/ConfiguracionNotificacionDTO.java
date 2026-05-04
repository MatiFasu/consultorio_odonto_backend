package com.ConsultorioOdontologico.consultorioOdontologico.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConfiguracionNotificacionDTO {
    private Long id;
    private boolean activa;
    private LocalTime horarioEnvio;
    private int diasAnticipacion;
    private String diasEjecucion;
}
