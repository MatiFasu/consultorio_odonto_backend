
package com.ConsultorioOdontologico.consultorioOdontologico.service;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.PagoDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.dto.PresupuestoDTO;
import java.util.List;
import java.util.Map;

public interface IFacturacionService {
    // Presupuestos
    public List<PresupuestoDTO> getPresupuestosPorPaciente(Long pacienteId);
    public PresupuestoDTO savePresupuesto(PresupuestoDTO p);
    public void deletePresupuesto(Long id);

    // Pagos
    public List<PagoDTO> getPagosPorPaciente(Long pacienteId);
    public PagoDTO savePago(PagoDTO p);
    public void deletePago(Long id);

    // Saldos
    public Map<String, Double> getEstadoCuenta(Long pacienteId);
}
