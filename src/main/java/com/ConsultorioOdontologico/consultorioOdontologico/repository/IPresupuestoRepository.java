
package com.ConsultorioOdontologico.consultorioOdontologico.repository;

import com.ConsultorioOdontologico.consultorioOdontologico.model.Presupuesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IPresupuestoRepository extends JpaRepository<Presupuesto, Long> {
    List<Presupuesto> findByPacienteIdOrderByFechaDesc(Long pacienteId);
}
