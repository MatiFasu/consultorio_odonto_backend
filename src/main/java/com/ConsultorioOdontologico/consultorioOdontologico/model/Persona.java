package com.ConsultorioOdontologico.consultorioOdontologico.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter
@Audited
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El DNI es obligatorio")
    @Size(min = 7, max = 12, message = "El DNI debe tener entre 7 y 12 caracteres")
    @jakarta.persistence.Column(unique = true, nullable = false)
    private String dni;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;
    
    private String telefono;
    private String direccion;
    
    private LocalDate fecha_nac;

    public Persona() {
    }

    public Persona(Long id, String dni, String nombre, String apellido, String telefono, String direccion, LocalDate fecha_nac) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.direccion = direccion;
        this.fecha_nac = fecha_nac;
    }
}
