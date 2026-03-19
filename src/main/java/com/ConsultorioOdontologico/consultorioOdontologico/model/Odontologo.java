package com.ConsultorioOdontologico.consultorioOdontologico.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Odontologo extends Persona{
    
    private String especialidad;
    
    @jakarta.persistence.OneToOne(cascade = jakarta.persistence.CascadeType.MERGE)
    @jakarta.persistence.JoinColumn(name = "id_usuario")
    private Usuario unUsuario;
    
    @jakarta.persistence.OneToOne(cascade = jakarta.persistence.CascadeType.MERGE)
    @jakarta.persistence.JoinColumn(name = "id_horario")
    private Horario unHorario;
    
    @OneToMany(mappedBy = "odonto")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<Turno> turnos;
    
    public Odontologo() {
    }

    public Odontologo(String especialidad, Usuario unUsuario, Horario unHorario, List<Turno> turnos, Long id, String dni, String nombre, String apellido, String telefono, String direccion, Date fecha_nac) {
        super(id, dni, nombre, apellido, telefono, direccion, fecha_nac);
        this.especialidad = especialidad;
        this.unUsuario = unUsuario;
        this.unHorario = unHorario;
        this.turnos = turnos;
    }
    
}
