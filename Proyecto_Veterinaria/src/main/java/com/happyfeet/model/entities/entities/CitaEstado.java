package com.happyfeet.model.entities;
import java.util.Objects;

public class CitaEstado {
        private int id;
        private String nombre;

        public CitaEstado() {
        }

        public CitaEstado(int id) {
            this.id = id;
        }

        public CitaEstado(String nombre) {
            this.nombre = nombre;
        }

        public CitaEstado(int id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        @Override
        public String toString() {
            return "CitaEstado{" +
                    "id=" + id +
                    ", nombre='" + nombre + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CitaEstado that = (CitaEstado) o;
            return id == that.id && Objects.equals(nombre, that.nombre);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, nombre);
        }
    }
