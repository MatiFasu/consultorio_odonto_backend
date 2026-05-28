package com.ConsultorioOdontologico.consultorioOdontologico.controller;

import com.ConsultorioOdontologico.consultorioOdontologico.repository.IPacienteRepository;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.IOdontologoRepository;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.ITurnoRepository;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Odontologo;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Turno;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard", description = "Endpoints para el panel de control del sistema")
public class DashboardController {

    private final IPacienteRepository pacienteRepo;
    private final IOdontologoRepository odontoRepo;
    private final ITurnoRepository turnoRepo;

    @GetMapping("/stats")
    @Operation(summary = "Obtener estadísticas agregadas para el dashboard")
    public ResponseEntity<Map<String, Object>> getStats(@RequestParam(required = false) Long odontologoId) {
        Map<String, Object> stats = new HashMap<>();
        
        long totalPacientes = pacienteRepo.count();
        long totalOdontologos = odontoRepo.count();
        
        LocalDate hoy = LocalDate.now();
        List<Turno> turnosDeHoy = turnoRepo.findByFechaTurno(hoy);
        long turnosHoy = turnosDeHoy.size();
        
        long misTurnosHoy = 0;
        if (odontologoId != null) {
            misTurnosHoy = turnosDeHoy.stream()
                .filter(t -> t.getOdonto() != null && t.getOdonto().getId().equals(odontologoId))
                .count();
        }
        
        stats.put("pacientes", totalPacientes);
        stats.put("odontologos", totalOdontologos);
        stats.put("turnosHoy", turnosHoy);
        stats.put("misTurnosHoy", misTurnosHoy);

        // Actividad semanal: contar distribución de turnos por día de la semana (0 = Sunday, 1 = Monday, etc.)
        List<Turno> todosLosTurnos = new ArrayList<>();
        if (odontologoId != null) {
            todosLosTurnos = turnoRepo.findByOdontoId(odontologoId);
        } else {
            todosLosTurnos = turnoRepo.findAll();
        }
        
        int[] weekStats = new int[7];
        for (Turno t : todosLosTurnos) {
            int dayOfWeek = t.getFecha_turno().getDayOfWeek().getValue(); // 1 = Monday, 7 = Sunday
            // Convertir a formato del cliente (0 = Sunday, 1 = Monday, ..., 6 = Saturday)
            int index = dayOfWeek == 7 ? 0 : dayOfWeek;
            weekStats[index]++;
        }
        
        List<Integer> weeklyActivityList = new ArrayList<>();
        for (int count : weekStats) {
            weeklyActivityList.add(count);
        }
        stats.put("weeklyActivity", weeklyActivityList);

        // Alertas de administración (odontólogos sin horario o sin teléfono)
        Map<String, Object> alertas = new HashMap<>();
        List<Odontologo> todosOdontologos = odontoRepo.findAll();
        
        List<Map<String, Object>> sinHorario = todosOdontologos.stream()
            .filter(o -> o.getUnHorario() == null)
            .map(o -> {
                Map<String, Object> map = new HashMap<>();
                map.put("id", o.getId());
                map.put("nombre", o.getNombre());
                map.put("apellido", o.getApellido());
                return map;
            })
            .collect(Collectors.toList());

        List<Map<String, Object>> sinTel = todosOdontologos.stream()
            .filter(o -> o.getTelefono() == null || o.getTelefono().trim().length() < 5)
            .map(o -> {
                Map<String, Object> map = new HashMap<>();
                map.put("id", o.getId());
                map.put("nombre", o.getNombre());
                map.put("apellido", o.getApellido());
                return map;
            })
            .collect(Collectors.toList());
            
        alertas.put("sinHorario", sinHorario);
        alertas.put("sinTel", sinTel);
        stats.put("alertasAdmin", alertas);

        return ResponseEntity.ok(stats);
    }
}
