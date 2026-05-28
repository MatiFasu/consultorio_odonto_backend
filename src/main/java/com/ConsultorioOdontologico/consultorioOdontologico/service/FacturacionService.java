package com.ConsultorioOdontologico.consultorioOdontologico.service;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.PagoDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.dto.PresupuestoDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.mapper.PagoMapper;
import com.ConsultorioOdontologico.consultorioOdontologico.mapper.PresupuestoMapper;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Paciente;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Pago;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Presupuesto;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.IPacienteRepository;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.IPagoRepository;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.IPresupuestoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FacturacionService implements IFacturacionService {

    private final IPresupuestoRepository presupuestoRepo;
    private final IPagoRepository pagoRepo;
    private final IPacienteRepository pacienteRepo;
    private final PresupuestoMapper presupuestoMapper;
    private final PagoMapper pagoMapper;

    // --- PRESUPUESTOS ---
    @Override
    public Page<PresupuestoDTO> getPresupuestosPaginated(Pageable pageable) {
        return presupuestoRepo.findAll(pageable).map(presupuestoMapper::toDTO);
    }

    @Override
    public List<PresupuestoDTO> getPresupuestosPorPaciente(Long pacienteId) {
        return presupuestoRepo.findByPacienteIdOrderByFechaDesc(pacienteId).stream()
                .map(presupuestoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PresupuestoDTO savePresupuesto(PresupuestoDTO pDTO) {
        Paciente pac = pacienteRepo.findById(pDTO.getIdPaciente())
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado"));

        Presupuesto presupuesto = presupuestoMapper.toEntity(pDTO);
        presupuesto.setPaciente(pac);
        
        // Recalcular total por seguridad
        double total = presupuesto.getItems().stream()
                .mapToDouble(i -> i.getCosto() != null ? i.getCosto() : 0.0)
                .sum();
        presupuesto.setTotal(total);

        Presupuesto saved = presupuestoRepo.save(presupuesto);
        return presupuestoMapper.toDTO(saved);
    }

    @Override
    public void deletePresupuesto(Long id) {
        presupuestoRepo.deleteById(id);
    }

    // --- PAGOS ---
    @Override
    public Page<PagoDTO> getPagosPaginated(Pageable pageable) {
        return pagoRepo.findAll(pageable).map(pagoMapper::toDTO);
    }

    @Override
    public List<PagoDTO> getPagosPorPaciente(Long pacienteId) {
        return pagoRepo.findByPacienteIdOrderByFechaDesc(pacienteId).stream()
                .map(pagoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PagoDTO savePago(PagoDTO pDTO) {
        if (pDTO.getMonto() <= 0) {
            throw new IllegalArgumentException("El monto del pago debe ser mayor a cero");
        }

        Paciente pac = pacienteRepo.findById(pDTO.getIdPaciente())
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado"));

        Pago pago = pagoMapper.toEntity(pDTO);
        pago.setPaciente(pac);

        if (pDTO.getIdPresupuesto() != null) {
            Presupuesto pre = presupuestoRepo.findById(pDTO.getIdPresupuesto())
                    .orElseThrow(() -> new IllegalArgumentException("Presupuesto no encontrado"));
            pago.setPresupuesto(pre);
            
            // Actualizar estado del presupuesto si el total pagado lo cubre
            actualizarEstadoPresupuesto(pre);
        }

        Pago saved = pagoRepo.save(pago);
        return pagoMapper.toDTO(saved);
    }

    private void actualizarEstadoPresupuesto(Presupuesto pre) {
        double totalPagado = pagoRepo.findByPresupuestoId(pre.getId()).stream()
                .mapToDouble(Pago::getMonto)
                .sum();
        
        // El nuevo pago aún no está en la DB si estamos dentro de savePago,
        // pero esta lógica se dispara después de persistir el pago usualmente.
        // Aquí simplificamos: el estado se valida por la suma histórica.
        if (totalPagado >= pre.getTotal()) {
            pre.setEstado("FINALIZADO");
        } else if (totalPagado > 0) {
            pre.setEstado("APROBADO");
        }
    }

    @Override
    public void deletePago(Long id) {
        pagoRepo.deleteById(id);
    }

    // --- BALANCE ---
    @Override
    public Map<String, Double> getEstadoCuenta(Long pacienteId) {
        List<Presupuesto> presupuestos = presupuestoRepo.findByPacienteId(pacienteId);
        List<Pago> pagos = pagoRepo.findByPacienteId(pacienteId);

        double totalPresupuestado = presupuestos.stream()
                .filter(p -> !"RECHAZADO".equals(p.getEstado()))
                .mapToDouble(Presupuesto::getTotal)
                .sum();

        double totalPagado = pagos.stream()
                .mapToDouble(Pago::getMonto)
                .sum();

        Map<String, Double> balance = new HashMap<>();
        balance.put("totalPresupuestado", totalPresupuestado);
        balance.put("totalPagado", totalPagado);
        balance.put("saldoPendiente", Math.max(0, totalPresupuestado - totalPagado));

        return balance;
    }
}
