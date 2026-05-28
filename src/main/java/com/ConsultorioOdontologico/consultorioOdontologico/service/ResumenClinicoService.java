package com.ConsultorioOdontologico.consultorioOdontologico.service;

import com.ConsultorioOdontologico.consultorioOdontologico.model.RegistroClinico;
import com.ConsultorioOdontologico.consultorioOdontologico.repository.IRegistroClinicoRepository;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ResumenClinicoService {

    @Autowired
    private IRegistroClinicoRepository registroRepository;

    private final ChatModel chatModel;

    @Autowired
    public ResumenClinicoService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String generarResumen(Long pacienteId) {
        List<RegistroClinico> registros = registroRepository.findByPacienteIdOrderByFechaDesc(pacienteId);

        if (registros.isEmpty()) {
            return "No hay registros clínicos para este paciente.";
        }

        String historialTexto = registros.stream()
                .map(r -> String.format("Fecha: %s, Motivo: %s, Diagnóstico: %s, Tratamiento: %s, Obs: %s",
                        r.getFecha(), r.getMotivoConsulta(), r.getDiagnostico(), r.getTratamiento(), r.getObservaciones()))
                .collect(Collectors.joining("\n---\n"));

        String template = """
                Eres un asistente dental experto. Basado en el siguiente historial clínico de un paciente, 
                genera un resumen ejecutivo muy breve (máximo 4 párrafos) para el odontólogo.
                
                Enfócate en:
                1. Antecedentes relevantes.
                2. Estado actual de los tratamientos.
                3. Recomendaciones o alertas para la próxima consulta.
                
                HISTORIAL:
                {historial}
                
                RESUMEN:
                """;

        PromptTemplate promptTemplate = new PromptTemplate(template);
        
        // Configuramos opciones compatibles con Groq (via OpenAI client)
        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .withModel("llama-3.3-70b-versatile")
                .withTemperature(0.7f)
                .build();

        try {
            // Verificar si la clave de API es nula, vacía o es la clave de prueba por defecto
            String apiKey = System.getenv("GROQ_API_KEY");
            if (apiKey == null || apiKey.trim().isEmpty() || apiKey.contains("dummy")) {
                return generarResumenLocalFallback(registros, "Clave API de Groq no configurada (Modo de Demostración)");
            }

            Prompt prompt = new Prompt(promptTemplate.createMessage(Map.of("historial", historialTexto)), options);
            ChatResponse response = chatModel.call(prompt);
            return response.getResult().getOutput().getContent();
        } catch (Exception e) {
            return generarResumenLocalFallback(registros, "Servicio de IA no disponible temporalmente (" + e.getMessage() + ")");
        }
    }

    private String generarResumenLocalFallback(List<RegistroClinico> registros, String motivoFallback) {
        StringBuilder sb = new StringBuilder();
        sb.append("💡 **[Resumen Clínico Determinado Localmente - ").append(motivoFallback).append("]**\n\n");
        sb.append("Se ha generado una síntesis clínica estructurada basada en los ").append(registros.size()).append(" registros de su historial:\n\n");
        
        // 1. ANTECEDENTES Y EVOLUCIÓN
        sb.append("### 1. Antecedentes y Evolución\n");
        java.time.LocalDate fechaInicio = registros.get(registros.size() - 1).getFecha().toLocalDate();
        java.time.LocalDate fechaFin = registros.get(0).getFecha().toLocalDate();
        sb.append("- **Periodo Clínico:** Desde el ").append(fechaInicio)
          .append(" hasta el ").append(fechaFin).append(".\n");
        
        RegistroClinico masReciente = registros.get(0);
        sb.append("- **Motivo de Consulta más Reciente:** *\"").append(masReciente.getMotivoConsulta()).append("\"*.\n\n");
        
        // 2. ESTADO ACTUAL DE LOS TRATAMIENTOS
        sb.append("### 2. Estado Actual de los Tratamientos\n");
        sb.append("- **Último Diagnóstico registrado:** ").append(masReciente.getDiagnostico()).append("\n");
        sb.append("- **Tratamiento Efectuado:** ").append(masReciente.getTratamiento()).append("\n");
        if (masReciente.getObservaciones() != null && !masReciente.getObservaciones().trim().isEmpty()) {
            sb.append("- **Observaciones Clínicas:** ").append(masReciente.getObservaciones()).append("\n");
        }
        sb.append("\n");

        // 3. RECOMENDACIONES PARA LA PRÓXIMA CONSULTA
        sb.append("### 3. Alertas y Recomendaciones\n");
        java.time.LocalDate fechaUltima = masReciente.getFecha().toLocalDate();
        sb.append("- **Control Evolutivo:** Evaluar la evolución del tratamiento dental aplicado el día ").append(fechaUltima).append(".\n");
        sb.append("- **Continuidad:** Revisar la respuesta a *\"").append(masReciente.getTratamiento()).append("\"* en el odontograma y ajustar según sintomatología del paciente.\n");
        sb.append("- **Activación de Groq:** Para activar el procesamiento semántico avanzado con IA, configure una variable de entorno `GROQ_API_KEY` válida en su host.");
        
        return sb.toString();
    }
}
