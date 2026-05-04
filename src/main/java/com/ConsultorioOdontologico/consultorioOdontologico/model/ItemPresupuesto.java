
package com.ConsultorioOdontologico.consultorioOdontologico.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class ItemPresupuesto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;
    private Double costo;

    @ManyToOne
    @JoinColumn(name = "id_presupuesto")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Presupuesto presupuesto;
}
