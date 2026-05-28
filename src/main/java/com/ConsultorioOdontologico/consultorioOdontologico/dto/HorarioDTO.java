package com.ConsultorioOdontologico.consultorioOdontologico.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class HorarioDTO {
    private Long id;
    @NotBlank(message = "El horario de inicio es obligatorio")
    private String horario_inicio;

    @NotBlank(message = "El horario final es obligatorio")
    private String horario_final;
}

