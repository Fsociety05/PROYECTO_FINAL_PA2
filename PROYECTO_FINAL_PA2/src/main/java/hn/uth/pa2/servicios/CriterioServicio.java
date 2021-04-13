/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hn.uth.pa2.servicios;

import hn.uth.pa2.modelos.Criterio;
import hn.uth.pa2.repositorios.CriterioRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Licona
 */
@Service
public class CriterioServicio {
    
    @Autowired //Inyeccion para una nueva instancia, lo hace de forma implicita
    private CriterioRepositorio repositorio;
    
    public Criterio guardar(Criterio entidad) {
        return repositorio.save(entidad);
    }
    
    public void eliminar(Long id){
        repositorio.deleteById(id);
    }
    
    //Optional verifica si el metodo nos esta devolviendo algo es como un booleano
    public Optional<Criterio> getValor(Long id) {//Buscar 
        return repositorio.findById(id);
    }
    
    public List<Criterio> getTodos() {
        return (List<Criterio>) repositorio.findAll();
    }
    
    public List<Criterio> getCriteriosPorTipo(Long id) {
        return (List<Criterio>) repositorio.getCriteriosPorTipo(id);
    }
}
