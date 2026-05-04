package com.ConsultorioOdontologico.consultorioOdontologico.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class ConfiguracionNotificacion {
    
    @Id
    private Long id = 1L; // Solo tendremos una configuración global (ID fijo)
    
    private boolean activa;
    private LocalTime horarioEnvio;
    private int diasAnticipacion; // 0 para el mismo día, 1 para el día anterior
    
    // Almacenaremos los días como una cadena separada por comas: "MONDAY,TUESDAY,..."
    private String diasEjecucion; 

    public ConfiguracionNotificacion() {
    }

    public ConfiguracionNotificacion(Long id, boolean activa, LocalTime horarioEnvio, int diasAnticipacion, String diasEjecucion) {
        this.id = id;
        this.activa = activa;
        this.horarioEnvio = horarioEnvio;
        this.diasAnticipacion = diasAnticipacion;
        this.diasEjecucion = diasEjecucion;
    }
}
