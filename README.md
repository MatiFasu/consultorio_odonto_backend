# 🦷 Ecosistema de Clínica Dental: Optimizando la Gestión Sanitaria

La gestión clínica eficaz suele verse obstaculizada por datos fragmentados, altas tasas de ausentismo de pacientes y cuellos de botella administrativos. Este proyecto ofrece una solución escalable y segura diseñada para cerrar la brecha entre la eficiencia administrativa y la atención profesional.

---

## 🎯 El Problema Central: Ineficiencia Clínica

Las clínicas modernas luchan con la **coordinación manual**. Cuando la gestión de turnos se maneja de forma aislada y los datos de los pacientes están descentralizados, esto genera:

1. **Pérdida de ingresos:** Las citas perdidas (ausentismo) impactan directamente en el resultado económico.
2. **Riesgos de seguridad:** Los datos médicos sensibles requieren una protección robusta que va más allá del simple almacenamiento de contraseñas.
3. **Retrasos operativos:** La falta de sincronización en tiempo real entre recepción y médicos ralentiza el flujo de atención a pacientes.

---

## 🏗️ Decisiones Arquitectónicas y Fundamentos

### 1. Arquitectura REST Desacoplada (Spring Boot y React)

- **Decisión:** Dividir el sistema en un backend Java sin estado y un frontend basado en TypeScript.
- **Fundamento:** Esto garantiza que el sistema pueda escalar horizontalmente. El backend permanece como "única fuente de verdad", permitiendo futuras integraciones con aplicaciones móviles o conexiones con laboratorios externos sin reescribir la lógica de negocio principal.

### 2. Seguridad Sin Estado con JWT y BCrypt

- **Decisión:** Implementar JWT para la gestión de sesiones y BCrypt para el hashing criptográfico.
- **Fundamento:** En un entorno sanitario, la integridad de los datos es innegociable. JWT permite una comunicación segura entre orígenes sin la sobrecarga de las sesiones del lado del servidor, mientras que BCrypt garantiza que, incluso ante una brecha de seguridad, las credenciales de los pacientes permanezcan protegidas.

### 3. Motor de Notificaciones Automatizadas (Integración con Twilio)

- **Decisión:** Construir un servicio de notificaciones programadas para enviar agendas diarias a los médicos y recordatorios a los pacientes.
- **Impacto:** Esto no es solo una "funcionalidad"; es un **optimizador de ingresos**. Al automatizar los recordatorios, reducimos de forma proactiva la tasa de ausentismo, asegurando una alta utilización del tiempo profesional.

### 4. Mapeo Manual de DTOs vs. Automappers

- **Decisión:** Mapeo controlado entre Entidades y DTOs dentro de la capa de servicios.
- **Fundamento:** Dada la sensibilidad de los datos médicos, el mapeo manual ofrece un control explícito sobre qué información sale de la base de datos. Esto previene la exposición accidental de campos internos de las entidades (como IDs o registros de auditoría) hacia el frontend.

---

## 📈 Impacto en el Negocio

- **Reducción del ausentismo:** Los recordatorios automáticos por WhatsApp mantienen la agenda de la clínica completa y predecible.
- **Atención basada en datos:** Los registros clínicos centralizados permiten a los médicos acceder al historial del paciente en segundos, mejorando la calidad del diagnóstico.
- **Agilidad administrativa:** La facturación y la gestión de turnos en tiempo real transforma al personal de "ingreso de datos" a "atención al paciente".

---

## 🛠️ Configuración de Ingeniería

### Requisitos del Entorno

El sistema está construido sobre **Java 17** y **MySQL 8.0**, priorizando la estabilidad y el soporte a largo plazo (LTS).

### Estrategia de Despliegue

Utilizamos **Docker Compose** para estandarizar los entornos de desarrollo y producción, eliminando los problemas de "funciona en mi máquina" y simplificando el pipeline de CI/CD.

```bash
docker-compose up -d
```
