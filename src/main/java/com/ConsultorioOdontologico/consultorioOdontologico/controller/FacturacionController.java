package com.ConsultorioOdontologico.consultorioOdontologico.controller;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.PagoDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.dto.PresupuestoDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.service.IFacturacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/facturacion")
@RequiredArgsConstructor
@Tag(name = "Facturación", description = "Gestión de presupuestos, pagos y saldos")
public class FacturacionController {

    private final IFacturacionService factuServ;

    // --- PRESUPUESTOS ---
    @GetMapping("/presupuestos/traer/paginado")
    @Operation(summary = "Obtener presupuestos de forma paginada")
    public Page<PresupuestoDTO> getPresupuestosPaginated(Pageable pageable) {
        return factuServ.getPresupuestosPaginated(pageable);
    }

    @GetMapping("/presupuestos/paciente/{pacienteId}")
    @Operation(summary = "Obtener presupuestos de un paciente")
    public List<PresupuestoDTO> getPresupuestos(@PathVariable Long pacienteId) {
        return factuServ.getPresupuestosPorPaciente(pacienteId);
    }

    @PostMapping("/presupuestos/crear")
    @Operation(summary = "Crear o actualizar un presupuesto")
    public ResponseEntity<PresupuestoDTO> savePresupuesto(@Valid @RequestBody PresupuestoDTO p) {
        return ResponseEntity.ok(factuServ.savePresupuesto(p));
    }

    @DeleteMapping("/presupuestos/eliminar/{id}")
    @Operation(summary = "Eliminar un presupuesto")
    public ResponseEntity<String> deletePresupuesto(@PathVariable Long id) {
        factuServ.deletePresupuesto(id);
        return ResponseEntity.ok("Presupuesto eliminado");
    }

    // --- PAGOS ---
    @GetMapping("/pagos/traer/paginado")
    @Operation(summary = "Obtener pagos de forma paginada")
    public Page<PagoDTO> getPagosPaginated(Pageable pageable) {
        return factuServ.getPagosPaginated(pageable);
    }

    @GetMapping("/pagos/paciente/{pacienteId}")
    @Operation(summary = "Obtener pagos de un paciente")
    public List<PagoDTO> getPagos(@PathVariable Long pacienteId) {
        return factuServ.getPagosPorPaciente(pacienteId);
    }

    @PostMapping("/pagos/registrar")
    @Operation(summary = "Registrar un nuevo pago")
    public ResponseEntity<PagoDTO> savePago(@Valid @RequestBody PagoDTO p) {
        return ResponseEntity.ok(factuServ.savePago(p));
    }

    @DeleteMapping("/pagos/eliminar/{id}")
    @Operation(summary = "Eliminar un registro de pago")
    public ResponseEntity<String> deletePago(@PathVariable Long id) {
        factuServ.deletePago(id);
        return ResponseEntity.ok("Pago eliminado");
    }

    // --- BALANCE ---
    @GetMapping("/estado-cuenta/{pacienteId}")
    @Operation(summary = "Obtener el balance de deuda/pago de un paciente")
    public ResponseEntity<Map<String, Double>> getEstadoCuenta(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(factuServ.getEstadoCuenta(pacienteId));
    }
}
