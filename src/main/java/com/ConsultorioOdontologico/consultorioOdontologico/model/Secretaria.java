package com.ConsultorioOdontologico.consultorioOdontologico.model;

import jakarta.persistence.Entity;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

@Getter @Setter
@Entity
@Audited
public class Secretaria extends Persona{
    private String sector;
    
    @jakarta.persistence.OneToOne(cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
    @jakarta.persistence.JoinColumn(name = "usuario_id")
    private Usuario unUsuario;

    public Secretaria() {
    }

    public Secretaria(String sector, Usuario unUsuario, Long id, String dni, String nombre, String apellido, String telefono, String direccion, LocalDate fecha_nac) {
        super(id, dni, nombre, apellido, telefono, direccion, fecha_nac);
        this.sector = sector;
        this.unUsuario = unUsuario;
    }
}
