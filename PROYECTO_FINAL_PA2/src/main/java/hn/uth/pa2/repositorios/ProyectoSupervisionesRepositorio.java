/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hn.uth.pa2.repositorios;

import hn.uth.pa2.modelos.ProyectoSupervisiones;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Buddys
 */
public interface ProyectoSupervisionesRepositorio extends CrudRepository<ProyectoSupervisiones, Long> {
    List<ProyectoSupervisiones> findByIdProyectoTitulo(String titulo);
    
//    @Query(value = "SELECT * FROM PROYECTOS_SUPERVISIONES PS, PROYECTOS P WHERE P.ID_PROYECTO = PS.ID_PROYECTO AND PS.ID_PROYECTO = ?1", nativeQuery = true)
    @Query(value = "SELECT * FROM PROYECTOS_SUPERVISIONES PS, TIPO_COORDINADORES TC WHERE PS.ID_TIPO_COORDINADOR = TC.ID_TIPO_COORDINADOR AND PS.ID_PROYECTO = ?1 AND TC.NOMBRE = ?2", nativeQuery = true)
    List<ProyectoSupervisiones> getReporteSupervision(Long idProyecto, String tipoCoordinador);
}
