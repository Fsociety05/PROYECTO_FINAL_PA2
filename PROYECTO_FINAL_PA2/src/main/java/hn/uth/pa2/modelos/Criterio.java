/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hn.uth.pa2.modelos;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
public class Criterio implements Serializable {

    private static final long serialVersionUID = -6833167247955613395L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JoinColumn(name = "id_criterio")
    private Long idCriterio;
    private String descripcion;
    private int punt_maximo;
    private int punt_minimo;
    
    
    @JoinColumn(name = "id_evaluacion", unique = false)
    @OneToOne(fetch = FetchType.LAZY)
    private TipoEvaluacion tipoEvaluacion;
}
