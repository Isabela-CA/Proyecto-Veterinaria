package com.happyfeet.model.enums;

public class EspecialidadVeterinario {
    public enum Especialidad {
        GENERAL("General"),
        CIRUGIA("Cirugia"),
        DERMATOLOGIA("Dermatologia"),
        CARDIOLOGIA("Cardiologia"),
        OFTALMOLOGIA("Oftalmologia"),
        ODONTOLOGIA("Odontologia");

        private final String valor;

        Especialidad(String valor) {
            this.valor = valor;
        }

        @Override
        public String toString() {
            return valor;
        }

        public static Especialidad fromString(String texto) {
            if (texto != null) {
                for (Especialidad especialidad : Especialidad.values()) {
                    if (especialidad.valor.equalsIgnoreCase(texto.trim())) {
                        return especialidad;
                    }
                }
            }
            throw new IllegalArgumentException("No se encontró la especialidad: " + texto);
        }
    }
}