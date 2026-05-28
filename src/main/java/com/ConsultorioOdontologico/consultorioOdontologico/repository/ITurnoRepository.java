
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

    // Verificar si ya existe otro turno agendado para un odontólogo a una hora específica, excluyendo el ID actual (para edición)
    @Query("SELECT COUNT(t) > 0 FROM Turno t WHERE t.odonto.id = :odontoId AND t.fecha_turno = :fecha AND t.hora_turno = :hora AND t.id <> :id")
    boolean existsByOdontoIdAndFechaTurnoAndHoraTurnoAndIdNot(@Param("odontoId") Long odontoId, @Param("fecha") LocalDate fecha, @Param("hora") String hora, @Param("id") Long id);

    // Buscar todos los turnos para una fecha específica
    @Query("SELECT t FROM Turno t WHERE t.fecha_turno = :fecha")
    List<Turno> findByFechaTurno(@Param("fecha") LocalDate fecha);

    @Query("SELECT t FROM Turno t WHERE t.fecha_turno = :fecha")
    org.springframework.data.domain.Page<Turno> findByFechaTurno(@Param("fecha") LocalDate fecha, org.springframework.data.domain.Pageable pageable);
}
