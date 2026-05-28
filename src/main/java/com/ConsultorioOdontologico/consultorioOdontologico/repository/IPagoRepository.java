package com.ConsultorioOdontologico.consultorioOdontologico.repository;

import com.ConsultorioOdontologico.consultorioOdontologico.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IPagoRepository extends JpaRepository<Pago, Long> {
    List<Pago> findByPacienteIdOrderByFechaDesc(Long pacienteId);
    List<Pago> findByPacienteId(Long pacienteId);
    List<Pago> findByPresupuestoId(Long presupuestoId);
}
