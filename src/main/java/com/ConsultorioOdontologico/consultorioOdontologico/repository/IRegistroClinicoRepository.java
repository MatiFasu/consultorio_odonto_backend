
package com.ConsultorioOdontologico.consultorioOdontologico.repository;

import com.ConsultorioOdontologico.consultorioOdontologico.model.RegistroClinico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IRegistroClinicoRepository extends JpaRepository<RegistroClinico, Long> {
    List<RegistroClinico> findByPacienteIdOrderByFechaDesc(Long pacienteId);
}
