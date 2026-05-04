package com.ConsultorioOdontologico.consultorioOdontologico.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PresupuestoDTO {
    private Long id;
    private LocalDateTime fecha;
    private String estado;
    private Double total;
    private Long idPaciente;
    private String nombrePaciente;
    private List<ItemPresupuestoDTO> items;
}
