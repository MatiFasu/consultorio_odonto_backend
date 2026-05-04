package com.ConsultorioOdontologico.consultorioOdontologico.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PacienteDTO {
    private Long id;
    private String dni;
    private String nombre;
    private String apellido;
    private String telefono;
    private String direccion;
    private Date fecha_nac;
    private boolean tiene_OS;
    private String tipoSangre;
    private Long idResponsable; // Simplificamos enviando solo el ID del responsable
}
