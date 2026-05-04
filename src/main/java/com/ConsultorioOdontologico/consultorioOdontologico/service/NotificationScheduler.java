package com.ConsultorioOdontologico.consultorioOdontologico.service;

import com.ConsultorioOdontologico.consultorioOdontologico.dto.ConfiguracionNotificacionDTO;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Odontologo;
import com.ConsultorioOdontologico.consultorioOdontologico.model.Turno;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.ITurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class NotificationScheduler {

    @Autowired
    private ITurnoRepository turnoRepository;

    @Autowired
    private WhatsAppService whatsAppService;

    @Autowired
    private ConfiguracionNotificacionService configService;

    /**
     * Revisa la configuración cada minuto.
     * Si la hora actual coincide con la configurada y está activa, envía las notificaciones.
     */
    @Scheduled(cron = "0 * * * * *") // Se ejecuta al segundo 0 de cada minuto
    public void verificarYEnviar() {
        ConfiguracionNotificacionDTO config = configService.getConfig
                ();
        
        if (!config.isActiva()) {
            return;
        }

        LocalTime ahora = LocalTime.now().withSecond(0).withNano(0);
        LocalTime horaConfig = config.getHorarioEnvio().withSecond(0).withNano(0);

        // Verificar si es la hora de envío
        if (ahora.equals(horaConfig)) {
            String hoySemana = LocalDate.now().getDayOfWeek().name();
            
            // Verificar si el día de hoy está habilitado en la configuración
            if (config.getDiasEjecucion().contains(hoySemana)) {
                System.out.println(">>> EJECUTANDO ENVÍO PROGRAMADO: " + ahora);
                enviarAgendaDelDia(config.getDiasAnticipacion());
            }
        }
    }

    public String enviarAgendaDelDia(int diasAnticipacion) {
        LocalDate fechaObjetivo = LocalDate.now().plusDays(diasAnticipacion);
        System.out.println(">>> Buscando turnos para la fecha: " + fechaObjetivo);
        
        List<Turno> turnos = turnoRepository.findByFechaTurno(fechaObjetivo);

        System.out.println(">>> Total de turnos encontrados para " + fechaObjetivo + ": " + turnos.size());

        if (turnos.isEmpty()) {
            return "No hay turnos agendados para la fecha " + fechaObjetivo + ". No se enviaron notificaciones.";
        }

        Map<Odontologo, List<Turno>> turnosPorOdonto = turnos.stream()
                .filter(t -> t.getOdonto() != null)
                .collect(Collectors.groupingBy(Turno::getOdonto));

        if (turnosPorOdonto.isEmpty()) {
            return "Se encontraron " + turnos.size() + " turnos, pero ninguno tiene un odontólogo asignado.";
        }

        int enviados = 0;
        int fallidos = 0;
        int sinTelefono = 0;
        StringBuilder resumen = new StringBuilder();
        resumen.append("Proceso finalizado para la fecha ").append(fechaObjetivo).append(":\n");

        for (Map.Entry<Odontologo, List<Turno>> entry : turnosPorOdonto.entrySet()) {
            Odontologo odonto = entry.getKey();
            List<Turno> listaTurnos = entry.getValue();

            if (odonto.getTelefono() != null && !odonto.getTelefono().isEmpty()) {
                System.out.println(">>> Enviando agenda al Dr/a: " + odonto.getApellido() + " al tel: " + odonto.getTelefono());
                String mensaje = armarMensajeAgenda(odonto, listaTurnos, fechaObjetivo);
                try {
                    whatsAppService.enviarMensaje(odonto.getTelefono(), mensaje);
                    enviados++;
                } catch (Exception e) {
                    System.err.println(">>> ERROR enviando a " + odonto.getApellido() + ": " + e.getMessage());
                    fallidos++;
                }
            } else {
                System.out.println(">>> El Dr/a " + odonto.getApellido() + " tiene turnos pero NO tiene teléfono configurado.");
                sinTelefono++;
            }
        }

        resumen.append("- Odontólogos notificados: ").append(enviados).append("\n");
        if (fallidos > 0) {
            resumen.append("- Envíos fallidos (error de Twilio): ").append(fallidos).append("\n");
        }
        if (sinTelefono > 0) {
            resumen.append("- Odontólogos sin teléfono configurado: ").append(sinTelefono).append("\n");
        }
        resumen.append("- Total turnos procesados: ").append(turnos.size());

        return resumen.toString();
    }

    private String armarMensajeAgenda(Odontologo odonto, List<Turno> turnos, LocalDate fecha) {
        StringBuilder sb = new StringBuilder();
        sb.append("Hola Dr/a. ").append(odonto.getApellido()).append(", esta es su agenda para el día ")
          .append(fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).append(":\n\n");

        turnos.sort((t1, t2) -> t1.getHora_turno().compareTo(t2.getHora_turno()));

        for (Turno t : turnos) {
            sb.append("⏰ ").append(t.getHora_turno()).append(" hs\n")
              .append("👤 Paciente: ").append(t.getPacien().getNombre()).append(" ").append(t.getPacien().getApellido()).append("\n")
              .append("📝 Motivo: ").append(t.getAfeccion()).append("\n")
              .append("----------------------------\n");
        }

        sb.append("\n¡Que tenga una excelente jornada! 🦷");
        return sb.toString();
    }
}
