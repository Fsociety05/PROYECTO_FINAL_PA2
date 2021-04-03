/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hn.uth.pa2.repositorios;

import hn.uth.pa2.modelos.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Licona
 */
public interface UsuarioRepositorio extends CrudRepository<Usuario, Long>{
    @Query(value = "SELECT * FROM USUARIO U ,ROL R WHERE U.ID_ROL = R.ID_ROL AND UPPER(R.NOMBRE) = UPPER(?1)", nativeQuery = true)
    Iterable<Usuario> listarUsuarioConsulta(String nombreRol);
}
