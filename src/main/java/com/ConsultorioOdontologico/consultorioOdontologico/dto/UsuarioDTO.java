package com.ConsultorioOdontologico.consultorioOdontologico.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long id_usuario;
    private String usuario;
    private String rol;
    // No incluimos contrasenia por seguridad
    private String contrasenia; // Solo para cuando se envía desde el cliente (Request)
}
