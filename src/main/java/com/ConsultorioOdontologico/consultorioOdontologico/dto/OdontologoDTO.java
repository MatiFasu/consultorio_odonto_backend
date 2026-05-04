package com.ConsultorioOdontologico.consultorioOdontologico.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class OdontologoDTO {
    private Long id;
    private String dni;
    private String nombre;
    private String apellido;
    private String telefono;
    private String direccion;
    private Date fecha_nac;
    private String especialidad;
    private Long idUsuario;
    private Long idHorario;
    private String horarioInicio;
    private String horarioFinal;
    private String nombreUsuario; // Opcional, para visualización
}
