package com.ConsultorioOdontologico.consultorioOdontologico.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TurnoDTO {
    private Long id;

    @NotNull(message = "La fecha del turno es obligatoria")
    @FutureOrPresent(message = "La fecha del turno no puede estar en el pasado")
    private LocalDate fecha_turno;

    @NotBlank(message = "La hora del turno es obligatoria")
    private String hora_turno;

    @NotBlank(message = "La afección o motivo es obligatorio")
    private String afeccion;
    
    @NotNull(message = "El ID del paciente es obligatorio")
    private Long idPaciente;

    @NotNull(message = "El ID del odontólogo es obligatorio")
    private Long idOdontologo;
    
    // Información extra para visualización (Response)
    private String nombrePaciente;
    private String telefonoPaciente;
    private String nombreOdontologo;
    private String telefonoOdontologo;
}

