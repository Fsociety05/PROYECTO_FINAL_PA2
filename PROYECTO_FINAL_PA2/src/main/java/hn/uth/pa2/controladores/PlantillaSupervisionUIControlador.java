/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hn.uth.pa2.controladores;

import hn.uth.pa2.modelos.ProyectoSupervisiones;
import hn.uth.pa2.modelos.Supervisiones;
import hn.uth.pa2.modelos.TipoCoordinadores;
import hn.uth.pa2.servicios.ProyectoSupervisionesServ;
import hn.uth.pa2.servicios.TipoCoordinadoresServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Buddys
 */
@Controller
public class PlantillaSupervisionUIControlador {
    TipoCoordinadores coordinador = new TipoCoordinadores();
    
    @Autowired
    private TipoCoordinadoresServicio servicioTipoCoordinadores;
    
    @Autowired
    private ProyectoSupervisionesServ servicio;
    
    @RequestMapping("/plantillaSupervision")
    public String irPlantillaSupervision(){
        return "paginas/plantillas/plantilla-supervision";
    }
    
     @GetMapping("/reporteCoordinadorProfesional/{id}")
    public String irCoordinadorProfesional(@PathVariable("id") Long id, Model modelo) {

        for (TipoCoordinadores object : servicioTipoCoordinadores.getTodos()) {
            if (object.getNombre().equalsIgnoreCase("Coordinador Profesional")) {
                this.coordinador.setIdTipoCoordinador(object.getIdTipoCoordinador());
            }
        }
//         setParametro(modelo, "listaPlantillaSupervision", );
        return "paginas/plantillas/plantilla-supervision";
    }

    @GetMapping("/reporteCoordinadorTecnico/{id}")
    public String idCoordinadorTecnico(@PathVariable("id") Long id, Model modelo) {

        for (TipoCoordinadores object : servicioTipoCoordinadores.getTodos()) {
            if (object.getNombre().equalsIgnoreCase("Coordinador Tecnico")) {
                this.coordinador.setIdTipoCoordinador(object.getIdTipoCoordinador());
            }
        }
        return "paginas/plantillas/plantilla-supervision";
    }
    
    public void setParametro(Model model, String atributo, Object valor) {
        model.addAttribute(atributo, valor);
    }
}
