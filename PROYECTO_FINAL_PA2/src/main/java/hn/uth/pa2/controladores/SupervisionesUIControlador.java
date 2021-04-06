/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hn.uth.pa2.controladores;

import hn.uth.pa2.modelos.ProyectoSupervisiones;
import hn.uth.pa2.modelos.Proyectos;
import hn.uth.pa2.modelos.Supervisiones;
import hn.uth.pa2.modelos.TipoCoordinadores;
import hn.uth.pa2.servicios.ProyectoSupervisionesServ;
import hn.uth.pa2.servicios.SupervisionesServicios;
import hn.uth.pa2.servicios.TipoCoordinadoresServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    private Long idProyecto = null;
    TipoCoordinadores coordinador = new TipoCoordinadores();

    @Autowired
    private SupervisionesServicios servicio;

    @Autowired
    private TipoCoordinadoresServicio servicioTipoCoordinadores;

    @Autowired
    private ProyectoSupervisionesServ servicioProyectoSuperv;

    @RequestMapping("/registrarSupervision")
    public String irFormulario(Model model) {
        setParametro(model, "supervisiones", new Supervisiones());
        setParametro(model, "proyectoSupervisiones", new ProyectoSupervisiones());
        return "paginas/supervision/form-supervisiones";
    }

    @GetMapping("/actualizarSupervision/{id}")
    public String irActualizar(@PathVariable("id") Long id, Model modelo, RedirectAttributes atributo) {
        setParametro(modelo, "supervisiones", servicio.getValor(id));
        setParametro(modelo, "proyectoSupervisiones", new ProyectoSupervisiones());
        return "paginas/supervision/form-supervisiones";
    }

    @GetMapping("/coordinadorProfesional/{id}")
    public String irCoordinadorProfesional(@PathVariable("id") Long id, Model modelo) {
        for (TipoCoordinadores object : servicioTipoCoordinadores.getTodos()) {
            if (object.getNombre().equalsIgnoreCase("Coordinador Profesional")) {
                this.coordinador.setIdTipoCoordinador(object.getIdTipoCoordinador());
            }
        }
        this.idProyecto = id;
        setParametro(modelo, "supervisiones", new Supervisiones());
        setParametro(modelo, "proyectoSupervisiones", new ProyectoSupervisiones());
        return "paginas/supervision/form-supervisiones";
    }

    @GetMapping("/coordinadorTecnico/{id}")
    public String idCoordinadorTecnico(@PathVariable("id") Long id, Model modelo) {
        for (TipoCoordinadores object : servicioTipoCoordinadores.getTodos()) {
            if (object.getNombre().equalsIgnoreCase("Coordinador Tecnico")) {
                this.coordinador.setIdTipoCoordinador(object.getIdTipoCoordinador());
            }
        }
        this.idProyecto = id;
        setParametro(modelo, "supervisiones", new Supervisiones());
        setParametro(modelo, "proyectoSupervisiones", new ProyectoSupervisiones());
        return "paginas/supervision/form-supervisiones";
    }

    @PostMapping("/guardarSupervision")
    public String guardar(Supervisiones supervision, Model model, RedirectAttributes atributo) {
        if (this.idProyecto != null) {
            ProyectoSupervisiones proyectoSup = new ProyectoSupervisiones();
            Proyectos proyecto = new Proyectos(this.idProyecto);
            proyectoSup.setIdProyecto(proyecto);
            proyectoSup.setIdTipoCoordinador(this.coordinador);

            int contador = 1;
            for (ProyectoSupervisiones item : servicioProyectoSuperv.getTodos()) {
                if (item.getIdProyecto().getIdProyecto() == this.idProyecto && item.getIdTipoCoordinador().getIdTipoCoordinador() == this.coordinador.getIdTipoCoordinador()) {
                    contador++;
                }
            }
            if (contador <= 3) {
                servicio.guardar(supervision);
                proyectoSup.setIdSupervision(supervision);
                servicioProyectoSuperv.guardar(proyectoSup);
                if (banderin) {
                    atributo.addFlashAttribute("success", "Guardado Correctamente");
                }
            } else {
                atributo.addFlashAttribute("error", "El coordinador alcanzo las tres supervisiones, no se puede insertar otra supervision");
            }
            this.idProyecto = null;
        } else {
            this.banderin = false;
            servicio.guardar(supervision);
            if (banderin == false) {
                atributo.addFlashAttribute("success", "Actualizado Correctamente");
                this.banderin = true;
            }
        }
        return "redirect:/registrarSupervision";
    }

    @GetMapping("/tituloProyecto")
    public String getValorBusqueda(Model model) {
        model.addAttribute("valorTitulo", new ProyectoSupervisiones());
        model.addAttribute("listaServicio", servicioProyectoSuperv.getTodos());
        return "paginas/supervision/mantenimiento-servicio";
    }

    @GetMapping("/busqueda")
    public String getBuscarTitulo(Model model, @ModelAttribute("valorTitulo") ProyectoSupervisiones entidad) {
        if (entidad.getIdProyecto().getTitulo().equals("")) {
            model.addAttribute("listaServicio", servicioProyectoSuperv.getTodos());
        } else {
            model.addAttribute("listaServicio", servicioProyectoSuperv.getResultadoBusqueda(entidad.getIdProyecto().getTitulo()));
        }
        return "paginas/supervision/mantenimiento-servicio";
    }

    public void setParametro(Model model, String atributo, Object valor) {
        model.addAttribute(atributo, valor);
    }
}
