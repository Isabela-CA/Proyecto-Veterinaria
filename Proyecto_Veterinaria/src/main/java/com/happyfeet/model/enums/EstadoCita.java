package com.happyfeet.model.enums;

public class EstadoCita {
    public enum Estado {
        PROGRAMADA("Programada"),
        EN_CURSO("En Curso"),
        COMPLETADA("Completada"),
        CANCELADA("Cancelada"),
        NO_ASISTIO("No Asistió");

        private final String valor;

        Estado(String valor) {
            this.valor = valor;
        }

        public String getValor() {
            return valor;
        }

        @Override
        public String toString() {
            return valor;
        }

        public static Estado fromString(String texto) {
            if (texto != null) {
                for (Estado estado : Estado.values()) {
                    if (estado.valor.equalsIgnoreCase(texto.trim())) {
                        return estado;
                    }
                }
            }
            throw new IllegalArgumentException("No se encontró el estado: " + texto);
        }
    }
}
