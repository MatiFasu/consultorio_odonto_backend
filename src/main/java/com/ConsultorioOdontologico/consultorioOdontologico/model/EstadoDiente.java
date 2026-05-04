
package com.ConsultorioOdontologico.consultorioOdontologico.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class EstadoDiente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int numeroDiente; // 11-48 (Notación FDI)
    private String posicion; // "superior", "inferior", "izquierda", "derecha", "centro"
    private String estado; // "caries", "ausente", "protesis", "sano", etc.
    
    @ManyToOne
    @JoinColumn(name = "id_registro_clinico")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private RegistroClinico registroClinico;
}
