/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hn.uth.pa2.controladores;

import hn.uth.pa2.modelos.Supervisiones;
import hn.uth.pa2.servicios.SupervisionesServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Buddys
 */
@Controller
public class SupervisionesUIControlador {
    private boolean banderin = true;
    
    @Autowired
    private SupervisionesServicios servicio;

    @RequestMapping("/registrarSupervision")
    public String irFormulario(Model model) {
        setParametro(model, "supervisiones", new Supervisiones());
        return "paginas/form-supervisiones";
    }

    @RequestMapping("/mantenimientoSupervision")
    public String irServicios(Model model) {
        setParametro(model, "listaServicio", servicio.getTodos());
        return "paginas/mantenimiento-servicio";
    }

    @GetMapping("/actualizarSupervision/{id}")
    public String irActualizar(@PathVariable("id") Long id, Model modelo, RedirectAttributes atributo) {
        setParametro(modelo, "supervisiones", servicio.getValor(id));
        this.banderin = false;
        return "paginas/form-supervisiones";
    }
    
    @GetMapping("/coordinadorProfesional/{id}")
    public String irCoordinadorUno(@PathVariable("id") Long id, Model modelo) {
        
        setParametro(modelo, "supervisiones", id);
        return "paginas/form-supervisiones";
    }
    
    @PostMapping("/guardarSupervision")
    public String guardar(Supervisiones supervision, Model model, RedirectAttributes atributo) {
        servicio.guardar(supervision);
        if (banderin) {
            atributo.addFlashAttribute("success", "Guardado Correctamente");
        } else {
            atributo.addFlashAttribute("success", "Actualizado Correctamente");
            this.banderin = true;
        }
        return "redirect:/registrarSupervision";
    }   

    public void setParametro(Model model, String atributo, Object valor) {
        model.addAttribute(atributo, valor);
    }
}
