/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hn.uth.pa2.modelos;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Buddys
 */
@Data
@NoArgsConstructor
@Entity
public class Usuarios implements Serializable{
    private static final long serialVersionUID = -6833167247955613395L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JoinColumn(name = "id_supervision")
    private Long idUsuario;
    private String identidad;
    private String usuario;
    private String nombres;
    private String apellidos;
    
    @JoinColumn(name = "correo_electronico")
    private String correoElectronico;
    
    @ManyToOne
    @JoinColumn(name = "id_departamento")
    private Departamentos idDeparamento;
    
    @ManyToOne
    @JoinColumn(name = "id_rol")
    private Roles idRol;
}
