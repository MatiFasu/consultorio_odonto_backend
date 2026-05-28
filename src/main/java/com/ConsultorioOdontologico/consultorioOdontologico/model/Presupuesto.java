
package com.ConsultorioOdontologico.consultorioOdontologico.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@Entity
@Audited
public class Presupuesto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;
    private String estado; // PENDIENTE, APROBADO, RECHAZADO, FINALIZADO
    private Double total;

    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;

    @OneToMany(mappedBy = "presupuesto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPresupuesto> items;

    public Presupuesto() {
        this.fecha = LocalDateTime.now();
        this.estado = "PENDIENTE";
        this.total = 0.0;
    }
}
