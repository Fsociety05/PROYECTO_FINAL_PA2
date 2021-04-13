/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hn.uth.pa2.repositorios;

import hn.uth.pa2.modelos.Proyectos;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Buddys
 */
public interface ProyectoRepositorio extends CrudRepository<Proyectos, Long> {    
    
    @Query(value = "SELECT * FROM PROYECTOS P WHERE UPPER(P.TITULO) LIKE %?1%", nativeQuery = true)
    List<Proyectos> buscarProyecto(String titulo);
}
