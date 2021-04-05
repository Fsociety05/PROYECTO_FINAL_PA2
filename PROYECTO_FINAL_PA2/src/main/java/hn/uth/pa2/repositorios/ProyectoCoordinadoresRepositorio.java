/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hn.uth.pa2.repositorios;

import hn.uth.pa2.modelos.ProyectoCoordinadores;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Buddys
 */
public interface ProyectoCoordinadoresRepositorio extends CrudRepository<ProyectoCoordinadores, Long> {
    
    @Modifying
    @Transactional
    @Query(value = "delete from PROYECTOS_COORDINADORES where ID_PROYECTO =  ?1", nativeQuery = true)
    public void eliminarProyectoCoordinador(Long idProyecto);
    
    @Modifying
    @Transactional
    @Query(value = "SELECT * from PROYECTOS_COORDINADORES where ID_USUARIO =  ?1", nativeQuery = true)
    public List<ProyectoCoordinadores> selectProyectoCoordinador(Long idUsuario);

    @Modifying
    @Transactional
    @Query(value = "UPDATE PROYECTOS_COORDINADORES SET ID_PROYECTO = ?3, ID_TIPO_COORDINADOR = ?1, ID_USUARIO = ?2 WHERE ID_PROYECTO = ?3", nativeQuery = true)
    public void actualizarProyectoCoordinador(Long idCoordinador, Long idUsuario, Long idProyecto);
}
