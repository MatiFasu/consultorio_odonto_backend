package com.ConsultorioOdontologico.consultorioOdontologico.repository;

import com.ConsultorioOdontologico.consultorioOdontologico.model.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IOdontologoRepository extends JpaRepository<Odontologo, Long>{
    
    // Buscar un odontólogo asociado a un ID de usuario específico
    @Query("SELECT o FROM Odontologo o WHERE o.unUsuario.id_usuario = :userId")
    java.util.Optional<Odontologo> findByUsuarioId(@org.springframework.data.repository.query.Param("userId") Long userId);
}
