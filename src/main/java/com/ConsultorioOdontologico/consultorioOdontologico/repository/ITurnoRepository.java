
package com.ConsultorioOdontologico.consultorioOdontologico.repository;

import com.ConsultorioOdontologico.consultorioOdontologico.model.Turno;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ITurnoRepository extends JpaRepository<Turno, Long>{
    
    // Buscar todos los turnos de un odontólogo específico por su ID de persona/odontólogo
    @Query("SELECT t FROM Turno t WHERE t.odonto.id = :odontoId")
    List<Turno> findByOdontoId(@Param("odontoId") Long odontoId);
    
    // Buscar turnos de un odontólogo a partir de una fecha específica (para próximos turnos)
    @Query("SELECT t FROM Turno t WHERE t.odonto.id = :odontoId AND t.fecha_turno >= :fecha")
    List<Turno> findByOdontoIdAndFechaTurnoGreaterThanEqual(@Param("odontoId") Long odontoId, @Param("fecha") LocalDate fecha);

    // Verificar si ya existe un turno agendado para un odontólogo a una hora específica
    @Query("SELECT COUNT(t) > 0 FROM Turno t WHERE t.odonto.id = :odontoId AND t.fecha_turno = :fecha AND t.hora_turno = :hora")
    boolean existsByOdontoIdAndFechaTurnoAndHoraTurno(@Param("odontoId") Long odontoId, @Param("fecha") LocalDate fecha, @Param("hora") String hora);
}
