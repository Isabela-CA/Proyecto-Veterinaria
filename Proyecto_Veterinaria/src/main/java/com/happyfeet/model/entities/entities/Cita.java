package com.happyfeet.model.entities;

import java.time.LocalDateTime;

public class Cita {
        private int id;
        private int mascota_id;
        private int veterinario_id;
        private LocalDateTime fecha_hora;
        private String motivo;
        private int estado_id;

        public Cita() {
        }

        public Cita(int id) {
            this.id = id;
        }

        public Cita(int mascota_id, int veterinario_id, LocalDateTime fecha_hora, String motivo, int estado_id) {
            this.mascota_id = mascota_id;
            this.veterinario_id = veterinario_id;
            this.fecha_hora = fecha_hora;
            this.motivo = motivo;
            this.estado_id = estado_id;
        }

        public Cita(int id, int mascota_id, int veterinario_id, LocalDateTime fecha_hora, String motivo, int estado_id) {
            this(mascota_id, veterinario_id, fecha_hora, motivo, estado_id);
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMascota_id() {
            return mascota_id;
        }

        public void setMascota_id(int mascota_id) {
            this.mascota_id = mascota_id;
        }

        public int getVeterinario_id() {
            return veterinario_id;
        }

        public void setVeterinario_id(int veterinario_id) {
            this.veterinario_id = veterinario_id;
        }

        public LocalDateTime getFecha_hora() {
            return fecha_hora;
        }

        public void setFecha_hora(LocalDateTime fecha_hora) {
            this.fecha_hora = fecha_hora;
        }

        public String getMotivo() {
            return motivo;
        }

        public void setMotivo(String motivo) {
            this.motivo = motivo;
        }

        public int getEstado_id() {
            return estado_id;
        }

        public void setEstado_id(int estado_id) {
            this.estado_id = estado_id;
        }

        @Override
        public String toString() {
            return "Cita{" +
                    "id=" + id +
                    ", mascota_id=" + mascota_id +
                    ", veterinario_id=" + veterinario_id +
                    ", fecha_hora=" + fecha_hora +
                    ", motivo='" + motivo + '\'' +
                    ", estado_id=" + estado_id +
                    '}';
        }
}
