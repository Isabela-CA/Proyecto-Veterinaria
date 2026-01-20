# 🐾 Sistema de Gestión Integral – Veterinaria *Happy Feet*
<img width="299" height="268" alt="happy" src="https://github.com/user-attachments/assets/960e7d35-05f2-476d-9cb8-904f805e4060" />

## 📖 Descripción del Proyecto

La clínica veterinaria *Happy Feet* es reconocida por su trato compasivo y atención médica de calidad.
 Sin embargo, la gestión manual de historiales, citas e inventario generaba problemas graves:

- **Historiales clínicos incompletos**.
- **Fugas de inventario** por falta de control.
- **Agendamiento caótico** con citas solapadas.
- **Facturación lenta y con errores**.

Este sistema integral centraliza la operación de la clínica, desde la ficha del paciente hasta la facturación, asegurando una gestión eficiente y profesional.

------

## ⚙️ Tecnologías Utilizadas

- **Lenguaje**: Java 17
- **Gestor de dependencias**: Maven
- **Base de datos**: MySQL
- **Conexión BD**: JDBC
- **Arquitectura**: MVC (Modelo – Vista – Controlador)
- **Buenas prácticas**: Principios SOLID, Git Flow, patrones de diseño, manejo de excepciones y logs.

------

## 🗂️ Funcionalidades Implementadas

### 🔹 Módulo 1: Gestión de Pacientes

- Registro de **Mascotas** (nombre, especie, raza, sexo, fecha de nacimiento, foto, historial médico).
- Registro de **Dueños** (nombre, documento, contacto, email único, contacto de emergencia).
- Relación mascota–dueño y posibilidad de transferir propiedad.

### 🔹 Módulo 2: Servicios Médicos y Citas

- **Agenda de Citas** con estados (`Programada`, `Finalizada`, `Cancelada`).
- Registro de **consultas médicas** (fecha, veterinario, motivo, diagnóstico, prescripción).
- Registro de **procedimientos especiales** (cirugías y tratamientos complejos).
- Regla de negocio: al recetar un medicamento o insumo, se descuenta automáticamente del inventario.

### 🔹 Módulo 3: Inventario y Farmacia

- Gestión de medicamentos, vacunas e insumos médicos.
- Control de stock con **alertas de bajo nivel** y **productos vencidos**.
- Gestión de proveedores para reabastecimiento.

### 🔹 Módulo 4: Facturación y Reportes

- **Facturación automática** al finalizar consultas o procedimientos.
- Facturas en **texto plano profesional**.
- Reportes gerenciales en consola:
  - Servicios más solicitados.
  - Desempeño de veterinarios.
  - Estado de inventario y productos a vencer.
  - Análisis de facturación por periodo.

### 🔹 Módulo 5 (Opcional): Actividades Especiales

- Registro de **mascotas en adopción** con contrato en texto plano.
- **Jornadas de vacunación** para registro masivo.
- **Club de mascotas frecuentes** con sistema de puntos y beneficios.

------

## 🗃️ Modelo de Base de Datos

El sistema cuenta con un modelo **normalizado** que incluye:

- Tablas de consulta: `especies`, `razas`, `cita_estados`, `producto_tipos`, `evento_tipos`.
- Tablas de negocio: `duenos`, `mascotas`, `historial_medico`, `inventario`, `citas`, `facturas`, `items_factura`.

📌 Scripts incluidos en `/database`:

- `schema.sql` → creación de tablas.
- `data.sql` → datos iniciales.

------

## 🚀 Instalación y Ejecución

### 🔹 Requisitos previos

- JDK 17
- MySQL Server
- Maven

### 🔹 Pasos

1. Clonar el repositorio:

   ```
   git clone <URL_REPOSITORIO>
   cd HappyFeet_Veterinaria
   ```

2. Configurar la base de datos en MySQL:

   ```
   source database/schema.sql;
   source database/data.sql;
   ```

3. Editar la conexión en el archivo de propiedades (`/src/main/resources/db.properties`):

   ```
   db.url=jdbc:mysql://localhost:3306/happyfeet
   db.user=root
   db.password=tu_contraseña
   ```

4. Compilar y ejecutar:

   ```
   mvn clean install
   mvn exec:java
   ```

------

## 🖥️ Guía de Uso

- Ejecutar el proyecto desde `Main.java`.
- El sistema mostrará un menú en consola para acceder a:
  - Gestión de pacientes (Dueños y Mascotas).
  - Agenda de citas y procedimientos médicos.
  - Inventario y farmacia.
  - Facturación y reportes.
  - Módulo de adopción y actividades especiales (opcional).

------

## 👨‍💻 Autores

- [Isabela Carrillo Azain]
