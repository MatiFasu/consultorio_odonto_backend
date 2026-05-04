
package com.ConsultorioOdontologico.consultorioOdontologico.controller;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.PagoDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.dto.PresupuestoDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.service.IFacturacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/facturacion")
@Tag(name = "Facturación", description = "Gestión de presupuestos, pagos y saldos")
public class FacturacionController {

    @Autowired
    private IFacturacionService factuServ;

    // --- PRESUPUESTOS ---
    @GetMapping("/presupuestos/paciente/{pacienteId}")
    @Operation(summary = "Obtener presupuestos de un paciente")
    public List<PresupuestoDTO> getPresupuestos(@PathVariable Long pacienteId) {
        return factuServ.getPresupuestosPorPaciente(pacienteId);
    }

    @PostMapping("/presupuestos/crear")
    @Operation(summary = "Crear o actualizar un presupuesto")
    public PresupuestoDTO savePresupuesto(@RequestBody PresupuestoDTO p) {
        return factuServ.savePresupuesto(p);
    }

    @DeleteMapping("/presupuestos/eliminar/{id}")
    @Operation(summary = "Eliminar un presupuesto")
    public ResponseEntity<String> deletePresupuesto(@PathVariable Long id) {
        factuServ.deletePresupuesto(id);
        return ResponseEntity.ok("Presupuesto eliminado");
    }

    // --- PAGOS ---
    @GetMapping("/pagos/paciente/{pacienteId}")
    @Operation(summary = "Obtener pagos de un paciente")
    public List<PagoDTO> getPagos(@PathVariable Long pacienteId) {
        return factuServ.getPagosPorPaciente(pacienteId);
    }

    @PostMapping("/pagos/registrar")
    @Operation(summary = "Registrar un nuevo pago")
    public PagoDTO savePago(@RequestBody PagoDTO p) {
        return factuServ.savePago(p);
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
    public Map<String, Double> getEstadoCuenta(@PathVariable Long pacienteId) {
        return factuServ.getEstadoCuenta(pacienteId);
    }
}
