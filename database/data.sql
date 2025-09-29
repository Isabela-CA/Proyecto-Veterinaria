insert into especies (nombre) 
values ('Perro'),('Gato'),('Ave'),('Roedor');

insert into razas (especie_id, nombre) 
values (1, 'Pastor Aleman'),
(2, 'Siames'),
(3, 'Cacatua'),
(4, 'Hámster Sirio');

INSERT INTO producto_tipos (nombre) VALUES 
('Medicamento'), ('Vacuna'), ('Insumo Médico'),('Alimento');

INSERT INTO evento_tipos (nombre) VALUES 
('Consulta'),('Vacunación'),('Cirugía'),('Desparasitación'),('Urgencia'),('Control');

INSERT INTO cita_estados (nombre) VALUES 
('Programada'),('Confirmada'),('En Proceso'),('Finalizada'),('Cancelada'),('No Asistió');

INSERT INTO duenos (nombre_completo, documento_identidad, direccion, telefono, email, contacto_emergencia) VALUES 
('Carlos Rodríguez López', '87654321', 'Av. Principal #89-12, Medellín', '310-7654321', 'carlos.rodriguez@gmail.com', 3001234567),
('Ana Martínez Silva', '11223344', 'Carrera 56 #78-90, Cali', '320-5566778', 'ana.martinez@email.com', 3107654321),
('Pedro Hernández Díaz', 'C55667788', 'Diagonal 34 #12-34, Barranquilla', '615-8899223', 'pedro.hernandez@email.com', NULL),
('Laura García Castro', 'CC-99887766', 'Transversal 78 #56-12, Cartagena', '318-3344556', 'laura.garcia@email.com', 3158899223),
('Javier López Ramírez', 'CC-44332211', 'Calle 90 #67-45, Bucaramanga', '301-7788990', 'javier.lopez@email.com', 3183344556),
('Sofía Torres Méndez', 'CC-66778899', 'Av. Siempre Viva #123, Pereira', '317-1122334', 'sofia.torres@email.com', NULL),
('Diego Sánchez Ruiz', 'CC-22334455', 'Carrera 11 #22-33, Manizales', '319-4455667', 'diego.sanchez@email.com', 3171122334),
('Valentina Díaz Herrera', 'CC-33445566', 'Calle 80 #45-67, Ibagué', '312-6677889', 'valentina.diaz@email.com', 3194455667),
('Miguel Ángel Castro Ortiz', 'CC-77889900', 'Diagonal 25 #38-49, Villavicencio', '314-9900112', 'miguel.castro@email.com', NULL);

INSERT INTO mascotas (dueno_id, nombre, raza_id, fecha_nacimiento, sexo, url_foto) VALUES 
(41, 'Max', 10, '2025-03-15', 'Macho', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTQbOl2_ExwSUO-tRMGf2t55t7ZHTYJUGJyuQ&s'),
(74, 'Luna', 11, '2025-07-20', 'Hembra', NULL),
(75, 'Mimi', 12, '2025-01-30', 'Hembra', NULL),
(77, 'Nala', 13, '2025-12-12', 'Macho', 'https://content.elmueble.com/medio/2022/06/09/hamster_ebe1fa86_1280x847.jpg'),
(75, 'Simba', 10, '2024-04-20', 'Macho', NULL),
(76, 'Lola', 12, '2024-03-08', 'Hembra', NULL),
(77, 'Thor', 13, '2014-12-25', 'Macho', NULL),
(78, 'Bella', 11, '2024-08-15', 'Hembra', NULL),
(79, 'Jack', 11, '2024-10-10', 'Macho', NULL),
(80, 'Mía', 12, '2024-01-05', 'Hembra', NULL),
(82, 'Rex', 10, '2024-06-18', 'Macho', NULL);

INSERT INTO inventario (nombre_producto, producto_tipo_id, descripcion, fabricante, lote, cantidad_stock, stock_minimo, fecha_vencimiento, precio_venta) VALUES 
('Amoxicilina 500mg', 1, 'Antibiótico de amplio espectro', 'Pfizer', 'LOT-AMX-2024A', 50, 10, '2025-12-31', 25.00),
('Ivermectina 1%', 1, 'Antiparasitario interno y externo', 'Bayer', 'LOT-IVM-2024B', 30, 5, '2025-10-15', 18.50),
('Carprofeno 50mg', 1, 'Antiinflamatorio para dolor articular', 'Zoetis', 'LOT-CARP-2024C', 25, 8, '2025-11-30', 32.75),
('Dexametasona 0.5mg', 1, 'Corticoesteroide antiinflamatorio', 'Virbac', 'LOT-DEX-2024D', 40, 12, '2025-09-20', 15.80),
('Vacuna Antirrábica', 2, 'Vacuna contra la rabia', 'Merial', 'LOT-RAB-2024E', 100, 20, '2024-12-31', 45.00),
('Vacuna Polivalente', 2, 'Vacuna múltiple para perros', 'Zoetis', 'LOT-POLI-2024F', 80, 15, '2024-11-30', 60.00),
('Vacuna Triple Felina', 2, 'Vacuna para gatos contra panleucopenia, calicivirus y rinotraqueitis', 'Bayer', 'LOT-TRFEL-2024G', 60, 10, '2024-10-15', 55.50),
('Jeringa 3ml', 3, 'Jeringa desechable estéril', 'BD', 'LOT-JER-2026A', 200, 50, '2026-05-31', 0.80),
('Gasas Estériles 10x10', 3, 'Paquete de 10 gasas estériles', 'Curitas', 'LOT-GAS-2026B', 150, 30, '2026-08-15', 5.25),
('Guantes Latex Talla M', 3, 'Caja de 100 guantes estériles', 'Ansell', 'LOT-GUA-2026C', 20, 5, '2026-12-31', 12.00),
('Sutura Nylon 3-0', 3, 'Hilo de sutura no absorbible', 'Ethicon', 'LOT-SUT-2026D', 50, 15, '2026-03-20', 8.75),
('Royal Canin Medium Adult', 4, 'Alimento para perros medianos adultos', 'Royal Canin', 'LOT-RC-2025H', 30, 8, '2025-06-30', 85.00),
('Hills Science Diet Gato Adulto', 4, 'Alimento balanceado para gatos adultos', 'Hills', 'LOT-HILLS-2025I', 25, 6, '2025-07-15', 92.50),
('Purina ProPlan Puppy', 4, 'Alimento para cachorros', 'Purina', 'LOT-PURINA-2025J', 40, 10, '2025-08-20', 78.00),
('Eukanuba Razas Pequeñas', 4, 'Alimento específico para razas pequeñas', 'Eukanuba', 'LOT-EUK-2025K', 20, 5, '2025-09-10', 88.00);

INSERT INTO veterinarios (documento_identidad, nombre_completo, especialidad, telefono, email, fecha_contratacion, activo) VALUES 
('VET-001', 'Dra. María Alejandra González', 'General', '300-1112233', 'maria.gonzalez@happyfeet.com', '2022-01-15', TRUE),
('VET-002', 'Dr. Carlos Eduardo Rodríguez', 'Cirugía', '300-4455667', 'carlos.rodriguez@happyfeet.com', '2022-03-20', TRUE),
('VET-003', 'Dra. Ana Patricia López', 'Dermatología', '300-7788990', 'ana.lopez@happyfeet.com', '2023-01-10', TRUE),
('VET-004', 'Dr. Javier Andrés Martínez', 'Cardiología', '300-1122334', 'javier.martinez@happyfeet.com', '2023-06-01', TRUE),
('VET-005', 'Dra. Sofia Isabel Ramírez', 'Oftalmología', '300-5566778', 'sofia.ramirez@happyfeet.com', '2024-02-14', TRUE),
('VET-006', 'Dr. Miguel Ángel Torres', 'Odontología', '300-9900112', 'miguel.torres@happyfeet.com', '2024-03-10', TRUE);


INSERT INTO citas (mascota_id, veterinario_id, fecha_hora, motivo, estado_id) VALUES 
-- Citas programadas
(5, 1, '2025-09-25 09:00:00', 'Consulta de rutina anual', 1),
-- Citas confirmadas
(7, 4, '2025-09-26 14:00:00', 'Control cardíaco', 2),
-- Citas en proceso
(8, 5, '2025-09-24 15:30:00', 'Problemas oculares', 3),
-- Citas finalizadas
(5, 2, '2025-09-22 09:00:00', 'Consulta por vómitos', 4),
-- Citas canceladas
(6, 3, '2025-09-27 16:00:00', 'Control dermatológico', 5);

INSERT INTO historial_medico (mascota_id, veterinario_id, fecha_evento, evento_tipo_id, descripcion, diagnostico, tratamiento_recomendado, producto_id, cantidad_utilizada) VALUES 
-- Consultas - usando medicamentos básicos para chequeos
(5, 1, '2025-09-23', 1, 'Control anual de salud. Mascota en buen estado general', 'Salud óptima', 'Vacunación anual pendiente. Mantener dieta actual', 1, 1), -- Amoxicilina como preventivo

-- Vacunaciones - usando vacunas del inventario
(6, 1, '2025-09-20', 2, 'Aplicación de vacuna polivalente y antirrábica', 'Vacunación completa', 'Próxima vacunación en 1 año', 5, 1), -- Vacuna Antirrábica
(6, 1, '2025-09-20', 2, 'Aplicación de vacuna polivalente', 'Vacunación completa', 'Próxima vacunación en 1 año', 6, 1), -- Vacuna Polivalente

-- Cirugías - usando material quirúrgico
(7, 2, '2025-08-15', 3, 'Esterilización rutinaria. Procedimiento sin complicaciones', 'Recuperación post-quirúrgica normal', 'Reposo por 10 días. Control de sutura', 11, 3), -- Sutura Nylon 3-0
(7, 2, '2025-08-15', 3, 'Material para cirugía', 'Recuperación post-quirúrgica normal', 'Reposo por 10 días', 9, 5), -- Gasas Estériles

-- Desparasitaciones - usando antiparasitario
(8, 1, '2025-09-10', 4, 'Desparasitación interna y externa', 'Prevención parasitaria', 'Repetir en 3 meses', 2, 1), -- Ivermectina 1%

-- Urgencias - usando antiinflamatorio para alergia
(5, 3, '2025-09-05', 5, 'Consulta urgente por alergia cutánea severa', 'Dermatitis alérgica', 'Antihistamínicos por 7 días. Dieta hipoalergénica', 4, 2), -- Dexametasona

-- Controles cardíacos - usando antiinflamatorio para el corazón
(6, 4, '2025-08-30', 6, 'Control cardíaco rutinario', 'Soplo cardíaco grado I', 'Control cada 6 meses. Restringir ejercicio intenso', 3, 1), -- Carprofeno

-- Control oftalmológico - usando antiinflamatorio ocular
(8, 5, '2025-09-12', 6, 'Control oftalmológico anual', 'Catarata incipiente', 'Control en 1 año. Suplementos oculares', 4, 1); -- 


INSERT INTO facturas (dueno_id, fecha_emision, total) VALUES 
(79, '2025-09-23 11:45:00', 85.50),
(80, '2025-09-20 12:30:00', 150.25),
(81, '2025-09-18 16:20:00', 45.00),
(82, '2025-09-15 10:15:00', 220.75),
(74, '2025-09-10 14:40:00', 95.00);

INSERT INTO items_factura (factura_id, producto_id, servicio_descripcion, cantidad, precio_unitario, subtotal) VALUES 
(200, NULL, 'Consulta general', 1, 30.00, 30.00),
(200, 5, 'Vacuna Antirrábica', 1, 45.00, 45.00),
(201, NULL, 'Cirugía de esterilización', 1, 120.00, 120.00),
(201, 1, 'Amoxicilina 500mg', 2, 25.00, 50.00),
(201, 9, 'Gasas Estériles', 1, 5.25, 5.25),
(201, 10, 'Guantes Latex', 1, 12.00, 12.00),
(202, NULL, 'Consulta dermatológico', 1, 35.00, 35.00),
(202, 2, 'Ivermectina 1%', 1, 18.50, 18.50),
(203, 3, 'Carprofeno 50mg', 2, 32.75, 65.50),
(204, 4, 'Dexametasona 0.5mg', 1, 15.80, 15.80);


INSERT INTO procedimientos_quirurgicos (mascota_id, veterinario_id, fecha_procedimiento,tipo_procedimiento, diagnostico, 
    descripcion_procedimiento, anestesia_utilizada, duracion_minutos, medicacion_prescrita, cuidados_recomendados, resultado) 
    VALUES (
     19, 2, '2025-01-15 10:00:00',
    'Esterilización', 'Control poblacional - paciente sana',
    'Ovariohisterectomía rutinaria sin complicaciones',
    'Isoflurano', 'Analgésico por 3 días, antibiótico por 5 días',
    'Reposo 10 días, control de herida, evitar lamido', 'EXITOSO'
);
-- =========== MÓDULO 5: ACTIVIDADES ESPECIALES ===========

-- 1. Mascotas en adopción
INSERT INTO mascotas_adopcion (mascota_id, fecha_ingreso, descripcion, estado, observaciones, fecha_adopcion, adoptante_id) VALUES 
(19, '2025-08-10', 'Labrador mezcla, 2 años, juguetón y bueno con niños', 'DISPONIBLE', 'Esterilizado y vacunado', NULL, NULL),
(17, '2025-05-01', 'Gato común europeo, 1 año, tranquilo y cariñoso', 'DISPONIBLE', 'Desparasitado, busca hogar tranquilo', NULL, NULL),
(18, '2025-07-15', 'Cachorro mestizo, 4 meses, energía alta', 'EN_PROCESO', 'En evaluación con familia candidata', NULL, NULL),
(20, '2025-06-20', 'Perra adulta, 3 años, calmada y obediente', 'ADOPTADA', 'Excelente adaptación', '2025-08-05', 78),
(21, '2025-05-30', 'Gato siamés, 2 años, requiere atención especial', 'ADOPTADA', 'Adoptado por familia experimentada', '2025-07-12', 81);

-- 2. Club de mascotas frecuentes
INSERT INTO club_mascotas_frecuentes (dueno_id, fecha_registro, puntos_acumulados, nivel, beneficios_activos) VALUES 
(41, '2025-05-15', 450, 'PLATA', TRUE),
(74, '2025-08-20', 780, 'ORO', TRUE),
(75, '2025-01-10', 120, 'BRONCE', TRUE),
(76, '2024-11-05', 950, 'DIAMANTE', TRUE),
(77, '2024-03-22', 65, 'BRONCE', TRUE),
(80, '2024-12-18', 320, 'PLATA', TRUE);

-- 3. Jornadas de vacunación
INSERT INTO jornadas_vacunacion (fecha_jornada, ubicacion, descripcion, total_mascotas_atendidas) VALUES 
('2025-05-15', 'Parque Central', 'Jornada de vacunación masiva contra la rabia', 85),
('2025-06-20', 'Plaza de Mercado', 'Campaña de vacunación y desparasitación', 120),
('2025-07-05', 'Colegio Nacional', 'Jornada de prevención para mascotas', 100);

-- 4. Registros de jornada
INSERT INTO registros_jornada (jornada_id, mascota_id, fecha_registro, vacuna_aplicada, lote_vacuna, observaciones) VALUES 
(1, 16, '2025-05-15', 'Vacuna Antirrábica', 'LOT-RAB-2024A', 'Mascota en buen estado'),
(1, 5, '2025-06-20', 'Vacuna Polivalente', 'LOT-POLI-2024B', 'Requiere control en 1 año'),
(1, 6, '2025-05-15', 'Vacuna Antirrábica', 'LOT-RAB-2024A', 'Sin reacciones adversas'),
(2, 8, '2025-05-15', 'Vacuna Antirrábica', 'LOT-RAB-2024B', 'Primera vacunación'),
(2, 17, '2025-06-20', 'Vacuna Polivalente', 'LOT-POLI-2024C', 'Refuerzo anual'),
(3, 19, '2025-05-15', 'Vacuna Antirrábica', 'LOT-RAB-2024C', 'Programada');

-- 5. Sistema de puntos
INSERT INTO puntos_club (club_id, factura_id, puntos_ganados, fecha_ganancia, fecha_vencimiento, estado) VALUES 
(7, 200, 8, '2024-09-23', '2025-12-23', 'ACTIVO'),
(8, 201, 15, '2024-09-20', '2026-03-20', 'ACTIVO'),
(9, 202, 4, '2024-09-18', '2026-03-18', 'ACTIVO'),
(10, 203, 22, '2024-09-15', '2026-03-15', 'ACTIVO'),
(11, 204, 9, '2024-09-10', '2026-03-10', 'USADO'),
(12, 200, 6, '2024-03-22', '2024-12-22', 'ACTIVO');

-- 6. Beneficios del club
INSERT INTO beneficios_club (nivel, nombre_beneficio, descripcion, puntos_requeridos, descuento_porcentaje, servicio_gratuito, activo) VALUES 
('BRONCE', 'Descuento Básico', '5% de descuento en consultas generales', 100, 5.00, NULL, TRUE),
('BRONCE', 'Baño Gratis', 'Baño profesional gratuito cada 3 meses', 150, NULL, 'Baño Profesional', TRUE),
('PLATA', 'Descuento Plata', '10% de descuento en todos los servicios', 300, 10.00, NULL, TRUE),
('PLATA', 'Desparasitación Gratuita', 'Desparasitación interna y externa gratuita', 250, NULL, 'Desparasitación', TRUE),
('ORO', 'Consulta Gratuita', 'Una consulta general gratuita cada 6 meses', 600, NULL, 'Consulta General', TRUE),
('ORO', 'Descuento Oro', '15% de descuento en procedimientos', 500, 15.00, NULL, TRUE),
('DIAMANTE', 'Vacuna Gratuita', 'Una vacuna gratuita al año', 1000, NULL, 'Vacunación', TRUE),
('DIAMANTE', 'Descuento Diamante', '20% de descuento permanente', 800, 20.00, NULL, TRUE),
('DIAMANTE', 'Chequeo Completo', 'Chequeo general anual gratuito', 1200, NULL, 'Chequeo Anual', TRUE);