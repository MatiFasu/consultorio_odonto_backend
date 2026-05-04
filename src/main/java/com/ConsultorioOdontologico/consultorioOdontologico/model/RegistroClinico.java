
package com.ConsultorioOdontologico.consultorioOdontologico.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@Entity
public class RegistroClinico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(length = 500)
    private String motivoConsulta;

    @Column(columnDefinition = "TEXT")
    private String diagnostico;

    @Column(columnDefinition = "TEXT")
    private String tratamiento;

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "id_odontologo")
    private Odontologo odontologo;

    @OneToMany(mappedBy = "registroClinico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EstadoDiente> odontograma;

    @OneToMany(mappedBy = "registroClinico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MultimediaEstudio> estudios;

    public RegistroClinico() {
        this.fecha = LocalDateTime.now();
    }
}
