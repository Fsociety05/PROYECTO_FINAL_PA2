/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hn.uth.pa2.repositorios;

import hn.uth.pa2.modelos.ProyectoEvaluacion;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Licona
 */
public interface ProyectoEvaluacionRepositorio extends CrudRepository<ProyectoEvaluacion, Long>{
    
    @Query(value = "SELECT * FROM proyecto_evaluacion WHERE id_proyecto = ?1", nativeQuery = true)
    List<ProyectoEvaluacion> getEvaluacioenesPorProyecto(Long idProyecto);
    
    @Query(value = "SELECT * FROM proyecto_evaluacion WHERE id_proyecto = ?1 AND id_plantilla = ?2", nativeQuery = true)
    List<ProyectoEvaluacion> getEvaluacioenesPorProyectoAndPlantila(Long idProyecto, Long idPlantilla);
    
    
    
}
