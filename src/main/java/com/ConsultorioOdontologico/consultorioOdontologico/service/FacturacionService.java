
package com.ConsultorioOdontologico.consultorioOdontologico.service;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.ItemPresupuestoDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.dto.PagoDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.dto.PresupuestoDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.model.ItemPresupuesto;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Pago;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Presupuesto;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.IPagoRepository;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.IPacienteRepository;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.IPresupuestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacturacionService implements IFacturacionService {

    @Autowired
    private IPresupuestoRepository presupuestoRepo;

    @Autowired
    private IPagoRepository pagoRepo;

    @Autowired
    private IPacienteRepository pacRepo;

    @Override
    public List<PresupuestoDTO> getPresupuestosPorPaciente(Long pacienteId) {
        return presupuestoRepo.findByPacienteIdOrderByFechaDesc(pacienteId).stream()
                .map(this::convertPresupuestoToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PresupuestoDTO savePresupuesto(PresupuestoDTO dto) {
        Presupuesto p = convertPresupuestoToEntity(dto);
        if (p.getItems() != null) {
            p.getItems().forEach(item -> item.setPresupuesto(p));
            double total = p.getItems().stream().mapToDouble(i -> i.getCosto()).sum();
            p.setTotal(total);
        }
        return convertPresupuestoToDTO(presupuestoRepo.save(p));
    }

    @Override
    public void deletePresupuesto(Long id) {
        presupuestoRepo.deleteById(id);
    }

    @Override
    public List<PagoDTO> getPagosPorPaciente(Long pacienteId) {
        return pagoRepo.findByPacienteIdOrderByFechaDesc(pacienteId).stream()
                .map(this::convertPagoToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PagoDTO savePago(PagoDTO dto) {
        Pago p = convertPagoToEntity(dto);
        Pago guardado = pagoRepo.save(p);
        
        if (p.getPresupuesto() != null) {
            verificarEstadoPresupuesto(p.getPresupuesto().getId());
        }
        
        return convertPagoToDTO(guardado);
    }

    private void verificarEstadoPresupuesto(Long presupuestoId) {
        presupuestoRepo.findById(presupuestoId).ifPresent(pre -> {
            List<Pago> pagos = pagoRepo.findByPacienteIdOrderByFechaDesc(pre.getPaciente().getId());
            double totalPagadoAlPresupuesto = pagos.stream()
                    .filter(p -> p.getPresupuesto() != null && p.getPresupuesto().getId().equals(presupuestoId))
                    .mapToDouble(Pago::getMonto)
                    .sum();
            
            if (totalPagadoAlPresupuesto >= pre.getTotal()) {
                pre.setEstado("FINALIZADO");
                presupuestoRepo.save(pre);
            } else if (totalPagadoAlPresupuesto > 0) {
                pre.setEstado("APROBADO");
                presupuestoRepo.save(pre);
            }
        });
    }

    @Override
    public void deletePago(Long id) {
        pagoRepo.deleteById(id);
    }

    @Override
    public Map<String, Double> getEstadoCuenta(Long pacienteId) {
        List<Presupuesto> presupuestos = presupuestoRepo.findByPacienteIdOrderByFechaDesc(pacienteId);
        List<Pago> pagos = pagoRepo.findByPacienteIdOrderByFechaDesc(pacienteId);

        double totalPresupuestado = presupuestos.stream()
                .filter(pre -> !pre.getEstado().equals("RECHAZADO"))
                .mapToDouble(Presupuesto::getTotal)
                .sum();
        
        double totalPagado = pagos.stream()
                .mapToDouble(Pago::getMonto)
                .sum();

        Map<String, Double> estado = new HashMap<>();
        estado.put("totalPresupuestado", totalPresupuestado);
        estado.put("totalPagado", totalPagado);
        estado.put("saldoPendiente", totalPresupuestado - totalPagado);

        return estado;
    }

    // Mappers
    private PresupuestoDTO convertPresupuestoToDTO(Presupuesto p) {
        PresupuestoDTO dto = new PresupuestoDTO();
        dto.setId(p.getId());
        dto.setFecha(p.getFecha());
        dto.setEstado(p.getEstado());
        dto.setTotal(p.getTotal());
        if (p.getPaciente() != null) {
            dto.setIdPaciente(p.getPaciente().getId());
            dto.setNombrePaciente(p.getPaciente().getNombre() + " " + p.getPaciente().getApellido());
        }
        if (p.getItems() != null) {
            dto.setItems(p.getItems().stream().map(i -> {
                ItemPresupuestoDTO iDto = new ItemPresupuestoDTO();
                iDto.setId(i.getId());
                iDto.setDescripcion(i.getDescripcion());
                iDto.setCosto(i.getCosto());
                return iDto;
            }).collect(Collectors.toList()));
        }
        return dto;
    }

    private Presupuesto convertPresupuestoToEntity(PresupuestoDTO dto) {
        Presupuesto p = new Presupuesto();
        p.setId(dto.getId());
        p.setFecha(dto.getFecha() != null ? dto.getFecha() : java.time.LocalDateTime.now());
        p.setEstado(dto.getEstado() != null ? dto.getEstado() : "PENDIENTE");
        p.setTotal(dto.getTotal() != null ? dto.getTotal() : 0.0);
        if (dto.getIdPaciente() != null) {
            p.setPaciente(pacRepo.findById(dto.getIdPaciente()).orElse(null));
        }
        if (dto.getItems() != null) {
            p.setItems(dto.getItems().stream().map(iDto -> {
                ItemPresupuesto i = new ItemPresupuesto();
                i.setId(iDto.getId());
                i.setDescripcion(iDto.getDescripcion());
                i.setCosto(iDto.getCosto());
                i.setPresupuesto(p);
                return i;
            }).collect(Collectors.toList()));
        }
        return p;
    }

    private PagoDTO convertPagoToDTO(Pago p) {
        PagoDTO dto = new PagoDTO();
        dto.setId(p.getId());
        dto.setFecha(p.getFecha());
        dto.setMonto(p.getMonto());
        dto.setMetodoPago(p.getMetodoPago());
        dto.setNotas(p.getNotas());
        dto.setTransaccionId(p.getTransaccionId());
        if (p.getPaciente() != null) {
            dto.setIdPaciente(p.getPaciente().getId());
            dto.setNombrePaciente(p.getPaciente().getNombre() + " " + p.getPaciente().getApellido());
        }
        if (p.getPresupuesto() != null) {
            dto.setIdPresupuesto(p.getPresupuesto().getId());
        }
        return dto;
    }

    private Pago convertPagoToEntity(PagoDTO dto) {
        Pago p = new Pago();
        p.setId(dto.getId());
        p.setFecha(dto.getFecha() != null ? dto.getFecha() : java.time.LocalDateTime.now());
        p.setMonto(dto.getMonto());
        p.setMetodoPago(dto.getMetodoPago());
        p.setNotas(dto.getNotas());
        p.setTransaccionId(dto.getTransaccionId());
        if (dto.getIdPaciente() != null) {
            p.setPaciente(pacRepo.findById(dto.getIdPaciente()).orElse(null));
        }
        if (dto.getIdPresupuesto() != null) {
            p.setPresupuesto(presupuestoRepo.findById(dto.getIdPresupuesto()).orElse(null));
        }
        return p;
    }
}
