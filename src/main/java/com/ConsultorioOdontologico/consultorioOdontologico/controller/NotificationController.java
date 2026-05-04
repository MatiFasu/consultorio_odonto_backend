package com.ConsultorioOdontologico.consultorioOdontologico.controller;

import com.ConsultorioOdontologico.consultorioOdontologico.service.NotificationScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationScheduler notificationScheduler;

    @Autowired
    private com.ConsultorioOdontologico.consultorioOdontologico.service.ConfiguracionNotificacionService configService;

    @GetMapping("/test")
    public String testNotifications() {
        try {
            int dias = configService.getConfig().getDiasAnticipacion();
            String resumen = notificationScheduler.enviarAgendaDelDia(dias);
            return resumen;
        } catch (Exception e) {
            return "Error al disparar notificaciones: " + e.getMessage();
        }
    }

    @Autowired
    private com.ConsultorioOdontologico.consultorioOdontologico.service.WhatsAppService whatsAppService;

    @GetMapping("/direct-test")
    public String directTest(String to) {
        if (to == null || to.isEmpty()) {
            return "Por favor, proporciona un número de teléfono en el parámetro 'to'. Ej: /api/notifications/direct-test?to=+123456789";
        }
        try {
            whatsAppService.enviarMensaje(to, "¡Hola! Esta es una prueba directa desde tu sistema de Consultorio Odontológico. Si recibís esto, Twilio está bien configurado. 🦷✨");
            return "Mensaje de prueba enviado a " + to + ". Verificá tu celular.";
        } catch (Exception e) {
            return "Error al enviar mensaje: " + e.getMessage();
        }
    }
}
