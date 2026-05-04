package com.ConsultorioOdontologico.consultorioOdontologico.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MultimediaEstudioDTO {
    private Long id;
    private String nombreArchivo;
    private String tipoContenido;
    private String urlArchivo;
    private LocalDateTime fechaCarga;
}
