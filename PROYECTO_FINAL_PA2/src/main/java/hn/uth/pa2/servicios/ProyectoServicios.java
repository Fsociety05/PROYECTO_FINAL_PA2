/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hn.uth.pa2.servicios;

import hn.uth.pa2.modelos.Proyectos;
import hn.uth.pa2.repositorios.ProyectoRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Buddys
 */
@Service
public class ProyectoServicios {
    @Autowired
    private ProyectoRepositorio repositorio;
    
    public Proyectos guardar(Proyectos entidad) {
        return repositorio.save(entidad);
    }

    public void eliminar(Long id) {
        repositorio.deleteById(id);
    }
    
    public Optional<Proyectos> getValor(Long id) {
        return repositorio.findById(id);
    }
    
    public List<Proyectos> getTodos() {
        return (List<Proyectos>) repositorio.findAll();
    }
}
