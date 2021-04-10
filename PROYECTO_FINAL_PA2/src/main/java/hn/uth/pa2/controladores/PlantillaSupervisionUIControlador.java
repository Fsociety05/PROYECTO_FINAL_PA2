/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hn.uth.pa2.controladores;

import hn.uth.pa2.modelos.ProyectoSupervisiones;
import hn.uth.pa2.modelos.TipoCoordinadores;
import hn.uth.pa2.servicios.ProyectoCoordinadoresServicios;
import hn.uth.pa2.servicios.ProyectoSupervisionesServ;
import hn.uth.pa2.servicios.SupervisionesServicios;
import hn.uth.pa2.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Buddys
 */
@Controller
public class PlantillaSupervisionUIControlador {

    TipoCoordinadores coordinador = new TipoCoordinadores();

    @Autowired
    private ProyectoSupervisionesServ servicio;

    @Autowired
    private UsuarioServicio servicioUsuario;

    @Autowired
    private ProyectoCoordinadoresServicios servicioProyectoCoord;

    @Autowired
    private SupervisionesServicios servicioSupervisiones;

    @RequestMapping("/plantillaSupervision")
    public String irPlantillaSupervision() {
        return "paginas/plantillas/plantilla-supervision";
    }

    @GetMapping("/reporteCoordinadorProfesional/{id}")
    public String irCoordinadorProfesional(@PathVariable("id") Long id, Model modelo, RedirectAttributes attribute) {
        int contador = 0;
        if (servicio.getReporteProyecto(id, "Coordinador Profesional").isEmpty()) {
            attribute.addFlashAttribute("error", "Error - No se ha hecho ninguna supervision");
            return "redirect:/proyectosPropios";
        } else {
            for (ProyectoSupervisiones item : servicio.getReporteProyecto(id, "Coordinador Profesional")) {
                setParametro(modelo, "registro", servicio.getValor(item.getId()).get());
                contador++;
            }
            System.out.println(contador);
            if (contador == 3) {
                setParametro(modelo, "listaReporteSupervision", servicio.getReporteProyecto(id, "Coordinador Profesional"));
            } else {
                attribute.addFlashAttribute("error", "Error - Concluye las tres supervisiones para poder ver el reporte");
                return "redirect:/proyectosPropios";
            }
        }
        return "paginas/plantillas/plantilla-supervision";
    }

    @GetMapping("/reporteCoordinadorTecnico/{id}")
    public String idCoordinadorTecnico(@PathVariable("id") Long id, Model modelo, RedirectAttributes attribute) {
        int contador = 0;
        if (servicio.getReporteProyecto(id, "Coordinador Tecnico").isEmpty()) {
            attribute.addFlashAttribute("error", "Error - No se ha hecho ninguna supervision");
            return "redirect:/proyectosPropios";
        } else {
            for (ProyectoSupervisiones item : servicio.getReporteProyecto(id, "Coordinador Tecnico")) {
                setParametro(modelo, "registro", servicio.getValor(item.getId()).get());
                contador++;
            }
            System.out.println(contador);
            if (contador == 3) {
                setParametro(modelo, "listaReporteSupervision", servicio.getReporteProyecto(id, "Coordinador Tecnico"));
            } else {
                attribute.addFlashAttribute("error", "Error - Concluye las tres supervisiones para poder ver el reporte");
                return "redirect:/proyectosPropios";
            }
        }
        return "paginas/plantillas/plantilla-supervision";
    }

    @RequestMapping("/proyectosPropios")
    public String irMisProyectos(Model model) throws Exception {
        Long idUsuario = servicioUsuario.getLoggedUser().getId_usuario();
        setParametro(model, "listaProyectos", servicioProyectoCoord.seleccionarProyectoCoordinador(idUsuario));
        return "paginas/proyecto/proyectos_usuario";
    }

    public void setParametro(Model model, String atributo, Object valor) {
        model.addAttribute(atributo, valor);
    }
}
