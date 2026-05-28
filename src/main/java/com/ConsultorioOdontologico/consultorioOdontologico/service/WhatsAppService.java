package com.ConsultorioOdontologico.consultorioOdontologico.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import jakarta.annotation.PostConstruct;

@Service
@Slf4j
public class WhatsAppService {

    @Value("${application.security.twilio.account-sid:}")
    private String accountSid;

    @Value("${application.security.twilio.auth-token:}")
    private String authToken;

    @Value("${application.security.twilio.from-number:whatsapp:+14155238886}")
    private String fromNumber;

    @Value("${application.security.twilio.content-sid:HXb5b62575e6e4ff6129ad7c8efe1f983e}")
    private String contentSid;

    @PostConstruct
    public void init() {
        if (accountSid != null && !accountSid.isEmpty() && authToken != null && !authToken.isEmpty()) {
            Twilio.init(accountSid, authToken);
            log.info("Twilio SDK inicializado correctamente.");
        } else {
            log.warn("Twilio SDK no inicializado: Faltan credenciales (twilio.account-sid o twilio.auth-token).");
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
            Message message = Message.creator(new PhoneNumber(formattedTo), new PhoneNumber(fromNumber), mensaje).create();
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

            Message.creator(new PhoneNumber(formattedTo), new PhoneNumber(fromNumber), "")
                .setContentSid(contentSid)
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
