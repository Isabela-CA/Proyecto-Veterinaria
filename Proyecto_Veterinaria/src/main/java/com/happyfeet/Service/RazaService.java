package com.happyfeet.Service;

import com.happyfeet.Repository.Interfaz.IRazaDAO;
import com.happyfeet.Repository.RazaDAO;
import com.happyfeet.model.entities.Raza;

import java.util.List;

    public class RazaService {
        private IRazaDAO razaDAO;

        public RazaService() {
            this.razaDAO = new RazaDAO();
        }

        public RazaService(IRazaDAO razaDAO) {
            this.razaDAO = razaDAO;
        }

        public List<Raza> listarTodasLasRazas() {
            return razaDAO.listarRaza();
        }

        public List<Raza> listarRazasPorEspecie(int especieId) {
            if (especieId <= 0) {
                throw new IllegalArgumentException("ID de especie inválido");
            }

            // Casting necesario porque el método no está en la interfaz
            if (razaDAO instanceof RazaDAO) {
                return ((RazaDAO) razaDAO).listarRazasPorEspecie(especieId);
            }
            throw new UnsupportedOperationException("Método no soportado por la implementación actual");
        }

        public Raza buscarRazaPorId(int id) {
            if (id <= 0) {
                throw new IllegalArgumentException("ID de raza inválido");
            }

            Raza raza = new Raza(id);
            if (razaDAO.buscarRaza(raza)) {
                return raza;
            }
            return null;
        }

        public boolean agregarRaza(Raza raza) {
            if (raza == null) {
                throw new IllegalArgumentException("La raza no puede ser null");
            }

            if (raza.getNombre() == null || raza.getNombre().trim().isEmpty()) {
                throw new IllegalArgumentException("El nombre de la raza es obligatorio");
            }

            if (raza.getEspecie_id() <= 0) {
                throw new IllegalArgumentException("ID de especie inválido");
            }

            return razaDAO.agregarRaza(raza);
        }

        public boolean actualizarRaza(Raza raza) {
            if (raza == null) {
                throw new IllegalArgumentException("La raza no puede ser null");
            }

            if (raza.getId() <= 0) {
                throw new IllegalArgumentException("ID de raza inválido");
            }

            if (raza.getNombre() == null || raza.getNombre().trim().isEmpty()) {
                throw new IllegalArgumentException("El nombre de la raza es obligatorio");
            }

            return razaDAO.modificarRaza(raza);
        }
    }
