package com.ConsultorioOdontologico.consultorioOdontologico.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import jakarta.annotation.PostConstruct;

@Service
@Slf4j
public class WhatsAppService {

    // Credenciales obtenidas de variables de entorno para mayor seguridad
    private static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    private static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
    private static final String FROM_NUMBER = System.getenv("TWILIO_FROM_NUMBER") != null ? System.getenv("TWILIO_FROM_NUMBER") : "whatsapp:+14155238886"; 
    private static final String CONTENT_SID = System.getenv("TWILIO_CONTENT_SID") != null ? System.getenv("TWILIO_CONTENT_SID") : "HXb5b62575e6e4ff6129ad7c8efe1f983e";

    @PostConstruct
    public void init() {
        if (ACCOUNT_SID != null && AUTH_TOKEN != null) {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            log.info("Twilio SDK inicializado correctamente.");
        } else {
            log.warn("Twilio SDK no inicializado: Faltan credenciales (ACCOUNT_SID o AUTH_TOKEN).");
        }
    }

    /**
     * Envía un mensaje de texto libre (útil para pruebas y resúmenes diarios).
     */
    public void enviarMensaje(String telefono, String mensaje) throws Exception {
        if (telefono == null || telefono.isEmpty()) {
            log.warn("No se puede enviar mensaje: teléfono ausente.");
            return;
        }
        try {
            String formattedTo = formatNumber(telefono);
            Message message = Message.creator(new PhoneNumber(formattedTo), new PhoneNumber(FROM_NUMBER), mensaje).create();
            log.info("Mensaje enviado a {}. SID: {}, Status: {}", formattedTo, message.getSid(), message.getStatus());
        } catch (Exception e) {
            log.error("Error en enviarMensaje: {}", e.getMessage());
            throw e; // Relanzamos para que el scheduler o controller sepa que falló
        }
    }

    /**
     * Envía una notificación de nuevo turno usando una plantilla de Twilio.
     */
    public void enviarNotificacionTurno(String telefono, String fecha, String hora) {
        if (telefono == null || telefono.isEmpty()) return;

        try {
            String formattedTo = formatNumber(telefono);
            String contentVariables = "{\"1\":\"" + fecha + "\",\"2\":\"" + hora + "\"}";

            Message.creator(new PhoneNumber(formattedTo), new PhoneNumber(FROM_NUMBER), "")
                .setContentSid(CONTENT_SID)
                .setContentVariables(contentVariables)
                .create();

            log.info("Notificación de plantilla enviada a {}", formattedTo);
        } catch (Exception e) {
            log.error("Error en enviarNotificacionTurno: {}", e.getMessage());
        }
    }

    private String formatNumber(String telefono) {
        String clean = telefono.trim();
        if (!clean.startsWith("whatsapp:")) {
            if (!clean.startsWith("+")) clean = "+" + clean;
            clean = "whatsapp:" + clean;
        }
        return clean;
    }
}
