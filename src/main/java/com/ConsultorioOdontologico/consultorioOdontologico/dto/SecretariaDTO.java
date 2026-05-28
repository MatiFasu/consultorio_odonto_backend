package com.ConsultorioOdontologico.consultorioOdontologico.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SecretariaDTO {
    private Long id;

    @NotBlank(message = "El DNI no puede estar vacío")
    @Size(min = 7, max = 12, message = "El DNI debe tener entre 7 y 12 caracteres")
    private String dni;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacío")
    private String apellido;

    @NotBlank(message = "El teléfono no puede estar vacío")
    private String telefono;

    @NotBlank(message = "La dirección no puede estar vacía")
    private String direccion;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe estar en el pasado")
    private LocalDate fecha_nac;

    @NotBlank(message = "El sector o área es obligatorio")
    private String sector;

    private Long idUsuario;
    private String nombreUsuario;
}
