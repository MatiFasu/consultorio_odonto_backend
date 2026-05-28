package com.ConsultorioOdontologico.consultorioOdontologico.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

@Getter @Setter
@Entity
@Audited
public class Turno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @jakarta.persistence.Column(name = "id")
    private Long id;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_turno;
    
    private String hora_turno;
    private String afeccion;
    
    @ManyToOne
    @JoinColumn(name="id_odontologo")
    private Odontologo odonto;
    
    @ManyToOne
    @JoinColumn(name="id_paciente")
    private Paciente pacien;
    
    public Turno() {
    }

    public Turno(Long id, LocalDate fecha_turno, String hora_turno, String afeccion) {
        this.id = id;
        this.fecha_turno = fecha_turno;
        this.hora_turno = hora_turno;
        this.afeccion = afeccion;
    }
}
