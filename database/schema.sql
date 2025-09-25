create database Veterinaria_Happy_Feet;
use Veterinaria_Happy_Feet;
-- =========== TABLAS DE CONSULTA (LOOKUP TABLES) ===========

CREATE TABLE especies (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) UNIQUE NOT NULL -- Ej: 'Perro', 'Gato', 'Ave'
) AUTO_INCREMENT = 1;

CREATE TABLE razas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    especie_id INT,
    nombre VARCHAR(100) NOT NULL,
    FOREIGN KEY (especie_id) REFERENCES especies(id)
) AUTO_INCREMENT = 10;


CREATE TABLE producto_tipos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre ENUM('Medicamento', 'Vacuna', 'Insumo Médico', 'Alimento') UNIQUE NOT NULL
)AUTO_INCREMENT = 1;

CREATE TABLE evento_tipos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre ENUM('Consulta', 'Vacunación', 'Cirugía', 'Desparasitación', 'Urgencia', 'Control') UNIQUE NOT NULL
)AUTO_INCREMENT = 1;

CREATE TABLE cita_estados (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre ENUM('Programada', 'Confirmada', 'En Proceso', 'Finalizada', 'Cancelada', 
    'No Asistió') UNIQUE NOT NULL
)AUTO_INCREMENT = 1;

-- ----------------  TABLAS OPERACIONALES DEL NEGOCIO ----------------

CREATE TABLE duenos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_completo VARCHAR(255) NOT NULL,
    documento_identidad VARCHAR(20) UNIQUE NOT NULL,
    direccion VARCHAR(255),
    telefono VARCHAR(20),
    email VARCHAR(100) UNIQUE NOT NULL,
    contacto_emergencia VARCHAR(20)
)AUTO_INCREMENT = 1;

CREATE TABLE mascotas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    dueno_id INT,
    nombre VARCHAR(100) NOT NULL,
    raza_id INT,
    fecha_nacimiento DATE,
    sexo ENUM('Macho', 'Hembra'),
    url_foto VARCHAR(255),
    FOREIGN KEY (dueno_id) REFERENCES duenos(id),
    FOREIGN KEY (raza_id) REFERENCES razas(id)
)AUTO_INCREMENT = 1;

CREATE TABLE inventario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_producto VARCHAR(255) NOT NULL,
    producto_tipo_id INT,
    descripcion TEXT,
    fabricante VARCHAR(100),
    lote VARCHAR(50),
    cantidad_stock INT NOT NULL,
    stock_minimo INT NOT NULL,
    fecha_vencimiento DATE,
    precio_venta DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (producto_tipo_id) REFERENCES producto_tipos(id)
)AUTO_INCREMENT = 1;

CREATE TABLE veterinarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    documento_identidad VARCHAR(20) UNIQUE NOT NULL,
    nombre_completo VARCHAR(255) NOT NULL,
    especialidad ENUM('General', 'Cirugía', 'Dermatología', 'Cardiología', 'Oftalmología', 'Odontología') NOT NULL,
    telefono VARCHAR(20),
    email VARCHAR(100) UNIQUE NOT NULL,
    fecha_contratacion DATE NOT NULL,
    activo BOOLEAN DEFAULT TRUE
)AUTO_INCREMENT = 1;

CREATE TABLE citas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    mascota_id INT,
    veterinario_id int NOT NULL,
    fecha_hora DATETIME NOT NULL,
    motivo VARCHAR(255),
    estado_id INT,
    FOREIGN KEY (mascota_id) REFERENCES mascotas(id),
    FOREIGN KEY (veterinario_id) REFERENCES veterinarios(id),
    FOREIGN KEY (estado_id) REFERENCES cita_estados(id)
)AUTO_INCREMENT = 100;

CREATE TABLE historial_medico (
    id INT AUTO_INCREMENT PRIMARY KEY,
    mascota_id INT,
	veterinario_id Int NOT NULL,
    fecha_evento DATE NOT NULL,
    evento_tipo_id INT,
    descripcion TEXT,
    diagnostico TEXT,
    tratamiento_recomendado TEXT,
    FOREIGN KEY (mascota_id) REFERENCES mascotas(id),
    FOREIGN KEY (veterinario_id) REFERENCES veterinarios(id),
    FOREIGN KEY (evento_tipo_id) REFERENCES evento_tipos(id)
)AUTO_INCREMENT = 150;
 
CREATE TABLE facturas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    dueno_id INT,
    fecha_emision DATETIME NOT NULL,
    total DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (dueno_id) REFERENCES duenos(id)
)AUTO_INCREMENT = 200;

CREATE TABLE items_factura (
    id INT AUTO_INCREMENT PRIMARY KEY,
    factura_id INT,
    producto_id INT, -- Puede ser un producto del inventario
    servicio_descripcion VARCHAR(255), -- O un servicio
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10, 2) NOT NULL,
    subtotal DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (factura_id) REFERENCES facturas(id),
    FOREIGN KEY (producto_id) REFERENCES inventario(id)
)AUTO_INCREMENT = 1;
-- ----------------------  MODULO 5 ------------------
-- 1. Mascotas en adopción
CREATE TABLE mascotas_adopcion (
id INT AUTO_INCREMENT PRIMARY KEY,
mascota_id INT UNIQUE NOT NULL,
fecha_ingreso DATE NOT NULL,
descripcion TEXT,
estado ENUM('DISPONIBLE', 'EN_PROCESO', 'ADOPTADA') DEFAULT 'DISPONIBLE',
observaciones TEXT,
fecha_adopcion DATE NULL,
adoptante_id INT NULL,
FOREIGN KEY (mascota_id) REFERENCES mascotas(id),
FOREIGN KEY (adoptante_id) REFERENCES duenos(id)
);

-- 2. Club de mascotas frecuentes
CREATE TABLE club_mascotas_frecuentes (
id INT AUTO_INCREMENT PRIMARY KEY,
dueno_id INT UNIQUE NOT NULL,
fecha_registro DATE NOT NULL,
puntos_acumulados INT DEFAULT 0,
nivel ENUM('BRONCE', 'PLATA', 'ORO', 'DIAMANTE') DEFAULT 'BRONCE',
beneficios_activos BOOLEAN DEFAULT TRUE,
FOREIGN KEY (dueno_id) REFERENCES duenos(id)
);

-- 3. Jornadas de vacunación
CREATE TABLE jornadas_vacunacion (
id INT AUTO_INCREMENT PRIMARY KEY,
fecha_jornada DATE NOT NULL,
ubicacion VARCHAR(255) NOT NULL,
descripcion TEXT,
total_mascotas_atendidas INT DEFAULT 0
);

-- 4 detalle de la vacunacion
CREATE TABLE registros_jornada (
id INT AUTO_INCREMENT PRIMARY KEY,
jornada_id INT NOT NULL,
mascota_id INT NOT NULL,
fecha_registro DATE NOT NULL,
vacuna_aplicada VARCHAR(255) NOT NULL,
lote_vacuna VARCHAR(100),
observaciones TEXT,
FOREIGN KEY (jornada_id) REFERENCES jornadas_vacunacion(id),
FOREIGN KEY (mascota_id) REFERENCES mascotas(id)
);

-- 5. Sistema de puntos y beneficios
CREATE TABLE puntos_club (
id INT AUTO_INCREMENT PRIMARY KEY,
club_id INT NOT NULL,
factura_id INT NOT NULL,
puntos_ganados INT NOT NULL,
fecha_ganancia DATE NOT NULL,
fecha_vencimiento DATE NOT NULL,
estado ENUM('ACTIVO', 'USADO', 'VENCIDO') DEFAULT 'ACTIVO',
FOREIGN KEY (club_id) REFERENCES club_mascotas_frecuentes(id),
FOREIGN KEY (factura_id) REFERENCES facturas(id)
);

-- 6 Detalle de posibles descuentos 
CREATE TABLE beneficios_club (
id INT AUTO_INCREMENT PRIMARY KEY,
nivel ENUM('BRONCE', 'PLATA', 'ORO', 'DIAMANTE') NOT NULL,
nombre_beneficio VARCHAR(255) NOT NULL,
descripcion TEXT,
puntos_requeridos INT NOT NULL,
descuento_porcentaje DECIMAL(5,2) NULL,
servicio_gratuito VARCHAR(100) NULL,
activo BOOLEAN DEFAULT TRUE
);