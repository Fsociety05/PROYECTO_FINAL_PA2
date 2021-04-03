/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hn.uth.pa2.controladores;

import hn.uth.pa2.modelos.Proyectos;
import hn.uth.pa2.servicios.DepartamentoServicio;
import hn.uth.pa2.servicios.ProyectoServicios;
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
public class ProyectoUIControlador {
    private boolean banderin = true;
    
    @Autowired
    private ProyectoServicios servicio;
    
    @Autowired
    private DepartamentoServicio servicioDepartamento;

    @RequestMapping("/registrarProyecto")
    public String irFormulario(Model model) {
        setParametro(model, "proyecto", new Proyectos());
        setParametro(model, "listaDepartamentos", servicioDepartamento.getTodos());
        return "paginas/proyecto/form-proyecto";
    }

    @RequestMapping("/mantenimientoProyecto")
    public String irServicios(Model model) {
        setParametro(model, "listaProyecto", servicio.getTodos());
        return "paginas/proyecto/mantenimiento-proyecto";
    }

    @GetMapping("/actualizarProyecto/{id}")
    public String irActualizar(@PathVariable("id") Long id, Model modelo, RedirectAttributes atributo) {
        setParametro(modelo, "proyecto", servicio.getValor(id));
        setParametro(modelo, "listaDepartamentos", servicioDepartamento.getTodos());
        this.banderin = false;
        return "paginas/proyecto/form-proyecto";
    }
    
    @PostMapping("/guardarProyecto")
    public String guardar(Proyectos proyecto, Model model, RedirectAttributes atributo) {
        servicio.guardar(proyecto);
        if (banderin) {
            atributo.addFlashAttribute("success", "Guardado Correctamente");
        } else {
            atributo.addFlashAttribute("success", "Actualizado Correctamente");
            this.banderin = true;
        }
        return "redirect:/registrarProyecto";
    }
   
    public void setParametro(Model model, String atributo, Object valor) {
        model.addAttribute(atributo, valor);
    }
}
