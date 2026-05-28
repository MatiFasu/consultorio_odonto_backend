
package com.ConsultorioOdontologico.consultorioOdontologico.model;

import jakarta.persistence.Entity;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

@Getter @Setter
@Entity
@Audited
public class Responsable extends Persona{
    private String tipoResponsabilidad;

    public Responsable() {
    }

    public Responsable(String tipoResponsabilidad, Long id, String dni, String nombre, String apellido, String telefono, String direccion, LocalDate fecha_nac) {
        super(id, dni, nombre, apellido, telefono, direccion, fecha_nac);
        this.tipoResponsabilidad = tipoResponsabilidad;
    }
}
