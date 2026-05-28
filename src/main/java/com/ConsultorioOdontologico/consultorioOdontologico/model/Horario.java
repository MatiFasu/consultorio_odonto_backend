
package com.ConsultorioOdontologico.consultorioOdontologico.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

@Getter @Setter
@Entity
@Audited
public class Horario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @jakarta.persistence.Column(name = "id")
    private Long id;
    private String horario_inicio;
    private String horario_final;

    public Horario() {
    }

    public Horario(Long id, String horario_inicio, String horario_final) {
        this.id = id;
        this.horario_inicio = horario_inicio;
        this.horario_final = horario_final;
    }
}
