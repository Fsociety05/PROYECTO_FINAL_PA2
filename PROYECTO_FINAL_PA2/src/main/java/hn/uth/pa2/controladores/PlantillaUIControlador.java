/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hn.uth.pa2.controladores;

import hn.uth.pa2.servicios.PlantillaServicio;
import hn.uth.pa2.servicios.TipoPlantillaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Licona
 */
@Controller
public class PlantillaUIControlador {

    @Autowired
    private PlantillaServicio servicioPlantilla;

    @Autowired
    private TipoPlantillaServicio servicioTipoPlantilla;

    @RequestMapping("/mantenimientoPlantilla")
    public String mantenimientoUsuario(Model model, RedirectAttributes attribute) {

        return "paginas/Plantillas/mantenimiento_plantilla";

    }

    @GetMapping("/crear_plantilla")
    public String irCrear(Model model) throws Exception {
       
        setParametro(model, "listaTipoPlantilla", servicioTipoPlantilla.getTodos());
        
        return "paginas/Plantillas/form_plantilla";
    }
    
    
    @GetMapping("/anadirPlantillaCriterio")
    public String irAnadir(Model model) throws Exception {
       
        
        
        return "paginas/Plantillas/form_plantilla_criterio";
    }

    public void setParametro(Model model, String atributo, Object valor) {
        model.addAttribute(atributo, valor);
    }

}
