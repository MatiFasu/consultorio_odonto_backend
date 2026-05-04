package com.ConsultorioOdontologico.consultorioOdontologico.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagoDTO {
    private Long id;
    private LocalDateTime fecha;
    private Double monto;
    private String metodoPago;
    private String notas;
    private String transaccionId;
    private Long idPaciente;
    private Long idPresupuesto;
    private String nombrePaciente;
}
