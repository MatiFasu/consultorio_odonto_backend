package com.ConsultorioOdontologico.consultorioOdontologico.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long id;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 4, max = 20, message = "El usuario debe tener entre 4 y 20 caracteres")
    private String usuario;

    @NotBlank(message = "El rol es obligatorio")
    private String rol;
    
    // No incluimos contrasenia por seguridad en el Response, pero se necesita en el Request
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String contrasenia; 
}
