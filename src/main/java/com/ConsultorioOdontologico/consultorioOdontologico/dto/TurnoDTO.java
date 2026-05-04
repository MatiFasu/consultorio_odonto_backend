package com.ConsultorioOdontologico.consultorioOdontologico.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TurnoDTO {
    private Long id_turno;
    private LocalDate fecha_turno;
    private String hora_turno;
    private String afeccion;
    
    // Identificadores para creación/edición
    private Long idPaciente;
    private Long idOdontologo;
    
    // Información extra para visualización (Response)
    private String nombrePaciente;
    private String telefonoPaciente;
    private String nombreOdontologo;
    private String telefonoOdontologo;
}
