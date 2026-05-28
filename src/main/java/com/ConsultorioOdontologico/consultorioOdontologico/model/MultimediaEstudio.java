
package com.ConsultorioOdontologico.consultorioOdontologico.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import java.time.LocalDateTime;

@Getter @Setter
@Entity
@Audited
public class MultimediaEstudio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreArchivo;
    private String tipoContenido;
    private String urlArchivo;
    private LocalDateTime fechaCarga;

    @ManyToOne
    @JoinColumn(name = "id_registro_clinico")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private RegistroClinico registroClinico;

    public MultimediaEstudio() {
        this.fechaCarga = LocalDateTime.now();
    }
}
