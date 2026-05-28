package com.ConsultorioOdontologico.consultorioOdontologico.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PacienteDTO {
    private Long id;

    @NotBlank(message = "El DNI no puede estar vacío")
    @Pattern(regexp = "^\\d{7,10}$", message = "El DNI debe tener entre 7 y 10 dígitos numéricos")
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

    private boolean tiene_OS;
    private String tipoSangre;
    private Long idResponsable; 
}
