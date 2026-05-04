package com.ConsultorioOdontologico.consultorioOdontologico.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistroClinicoDTO {
    private Long id;
    private LocalDateTime fecha;
    private String motivoConsulta;
    private String diagnostico;
    private String tratamiento;
    private String observaciones;
    private Long idPaciente;
    private Long idOdontologo;
    private String nombrePaciente;
    private String nombreOdontologo;
    private List<EstadoDienteDTO> odontograma;
    private List<MultimediaEstudioDTO> estudios;
}
