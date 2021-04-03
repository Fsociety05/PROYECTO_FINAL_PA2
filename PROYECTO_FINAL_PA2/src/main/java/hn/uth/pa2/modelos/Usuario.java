/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hn.uth.pa2.modelos;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Licona
 */
@Data
@NoArgsConstructor
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_usuario;
    private String nombres;
    private String apellidos;
    private String identidad;
    private String correo;
    private String name_usuario;
    private String contrasenia;

    //Rol
    // @OneToOne(cascade = CascadeType.ALL) // para que los cambios que se hagan afenten a este
    @JoinColumn(name = "id_rol", unique = false)
    @OneToOne(fetch = FetchType.LAZY)
    private Rol rol;

    //Departamento
    @JoinColumn(name = "id_departamento", unique = false)
    //@OneToOne(cascade = CascadeType.ALL)
     @OneToOne(fetch = FetchType.LAZY)
    private Departamento departamento;
}
