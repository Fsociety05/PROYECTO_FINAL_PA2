/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hn.uth.pa2.repositorios;

import hn.uth.pa2.modelos.Departamentos;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Buddys
 */
public interface DepartamentoRepositorio extends CrudRepository<Departamentos, Long> {    
    
}
