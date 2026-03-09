# 🦷 Sistema de Gestión para Consultorio Odontológico

¡Bienvenido! Este es un sistema integral diseñado para modernizar y facilitar la administración de un consultorio odontológico. El proyecto consta de una arquitectura desacoplada con un **Backend en Java (Spring Boot)** y un **Frontend en React (TypeScript)**.

---

## 🚀 Descripción del Proyecto

Este sistema permite gestionar de manera eficiente toda la operación de una clínica dental, desde la administración de pacientes y odontólogos hasta la programación de turnos y el control de horarios. Está diseñado para ser escalable, seguro y fácil de usar, ofreciendo una experiencia fluida tanto para el personal administrativo como para los profesionales de la salud.

---

## ✨ Características Principales

- **🔐 Seguridad y Autenticación:** Control de acceso mediante roles (ADMIN, USER) con contraseñas encriptadas mediante BCrypt.
- **📅 Gestión de Turnos:** Programación, visualización y seguimiento de citas odontológicas.
- **👥 Gestión de Pacientes:** Registro completo de información personal y contacto de pacientes.
- **👨‍⚕️ Gestión de Odontólogos:** Administración de la nómina de profesionales y sus especialidades.
- **🕒 Control de Horarios:** Configuración de rangos horarios de atención por profesional.
- **💼 Gestión de Secretarias:** Administración del personal administrativo del consultorio.
- **📊 Dashboard Informativo:** Vista general del estado del consultorio.

---

## 🛠️ Stack Tecnológico

### **Backend**
- **Lenguaje:** Java 17
- **Framework:** Spring Boot 3.2.2
- **Persistencia:** Spring Data JPA / Hibernate
- **Base de Datos:** MySQL 8.0 (Soporte para H2 en desarrollo)
- **Gestión de Dependencias:** Maven
- **Seguridad:** BCrypt para hashing de contraseñas
- **Contenedores:** Docker & Docker Compose
- **Arquitectura:** Monolítica

### **Frontend**
[GITHUB FRONTEND](https://github.com/MatiFasu/consultorio_odonto_frontend)
- **Framework:** React 19
- **Build Tool:** Vite
- **Lenguaje:** TypeScript
- **Estilos:** Tailwind CSS v4.0
- **Enrutamiento:** React Router Dom v7
- **Iconografía:** Lucide React
- **Cliente HTTP:** Axios

---

## ⚙️ Instalación y Configuración

### Prerrequisitos
- JDK 17 o superior.
- Node.js (v18+) y npm.
- MySQL Server (si no se usa Docker).
- Docker & Docker Compose (opcional para despliegue rápido).

### 🖥️ Configuración del Backend
1. Navega a la carpeta del backend: `cd consultorioOdontologico`
2. Configura las credenciales de la base de datos en `src/main/resources/application.properties`.
3. Instala las dependencias y compila: `./mvnw clean install`
4. Ejecuta la aplicación: `./mvnw spring-boot:run`

*Nota: El sistema crea automáticamente un usuario administrador por defecto (`admin` / `admin`) al iniciar por primera vez.*

### 🎨 Configuración del Frontend
1. Navega a la carpeta del frontend: `cd consultorio_odontologico`
2. Instala las dependencias: `npm install`
3. Inicia el servidor de desarrollo: `npm run dev`

---

## 🐳 Despliegue con Docker

Para levantar el Backend y la Base de Datos MySQL de forma automática, ejecuta en la raíz del proyecto:

```bash
docker-compose up -d
```

Esto levantará:
- **MySQL:** Puerto `3307` (mapeado al 3306 interno).
- **Backend API:** Puerto `8080`.

