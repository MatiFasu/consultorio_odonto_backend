
package com.ConsultorioOdontologico.consultorioOdontologico.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter @Setter
@Entity
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;
    private Double monto;
    private String metodoPago; // EFECTIVO, TARJETA, TRANSFERENCIA, MERCADO_PAGO
    private String notas;
    private String transaccionId; // ID de MP o número de cupón POS

    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "id_presupuesto")
    private Presupuesto presupuesto;

    public Pago() {
        this.fecha = LocalDateTime.now();
    }
}
