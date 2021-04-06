/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hn.uth.pa2.controladores;

import hn.uth.pa2.modelos.BitacoraCoordinadores;
import hn.uth.pa2.modelos.ProyectoCoordinadores;
import hn.uth.pa2.modelos.Proyectos;
import hn.uth.pa2.modelos.TipoCoordinadores;
import hn.uth.pa2.modelos.Usuario;
import hn.uth.pa2.servicios.BitacoraCoordinadoresServicio;
import hn.uth.pa2.servicios.DepartamentoServicio;
import hn.uth.pa2.servicios.ProyectoCoordinadoresServicios;
import hn.uth.pa2.servicios.ProyectoServicios;
import hn.uth.pa2.servicios.TipoCoordinadoresServicio;
import hn.uth.pa2.servicios.UsuarioServicio;
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
    private boolean banderinProyectoCoord = true;
    private Long idProyecto;

    @Autowired
    private ProyectoServicios servicio;

    @Autowired
    private DepartamentoServicio servicioDepartamento;

    @Autowired
    private UsuarioServicio servicioUsuario;

    @Autowired
    private TipoCoordinadoresServicio servicioCoordinador;

    @Autowired
    private ProyectoCoordinadoresServicios servicioProyectoCoord;

    @Autowired
    private BitacoraCoordinadoresServicio servicioBitacoraCoordinador;

    @RequestMapping("/registrarProyecto")
    public String irFormulario(Model model) {
        setParametro(model, "proyecto", new Proyectos());
        setParametro(model, "listaDepartamentos", servicioDepartamento.getTodos());
        setParametro(model, "listaUsuario", servicioUsuario.getUsuariosConsulta("consulta"));
        setParametro(model, "listaCoordinadorP", servicioCoordinador.getTipoCoordinador("Coordinador Profesional"));
        setParametro(model, "listaCoordinadorT", servicioCoordinador.getTipoCoordinador("Coordinador Tecnico"));
        setParametro(model, "listaCoordinadorG", servicioCoordinador.getTipoCoordinador("Coordinador General"));
        this.banderin = true;
        return "paginas/proyecto/form-proyecto";
    }

    @RequestMapping("/mantenimientoProyecto")
    public String irServicios(Model model) {
        try {
            setParametro(model, "listaProyecto", servicio.getTodos());
            setParametro(model, "listaDepartamentos", servicioDepartamento.getTodos());
            setParametro(model, "listaUsuario", servicioUsuario.getUsuariosConsulta("consulta"));
            setParametro(model, "listaCoordinadorP", servicioCoordinador.getTipoCoordinador("Coordinador Profesional"));
            setParametro(model, "listaCoordinadorT", servicioCoordinador.getTipoCoordinador("Coordinador Tecnico"));
            setParametro(model, "listaCoordinadorG", servicioCoordinador.getTipoCoordinador("Coordinador General"));
            this.banderin = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "paginas/proyecto/mantenimiento-proyecto";
    }

    @RequestMapping("/mantenimientoProyectoCoord")
    public String irCoordinadoresProyecto(Model model) {
        setParametro(model, "listaProyecto", servicio.getTodos());
        return "paginas/proyecto/mant-proyecto-coordinadores";
    }

    @GetMapping("/actualizarProyecto/{id}")
    public String irActualizar(@PathVariable("id") Long id, Model modelo, RedirectAttributes atributo) {
        setParametro(modelo, "proyecto", servicio.getValor(id));
        setParametro(modelo, "listaDepartamentos", servicioDepartamento.getTodos());
        this.banderin = false;
        return "paginas/proyecto/form-proyecto";
    }

    @GetMapping("/agregarCordinadores/{id}")
    public String agregarCoordinadores(@PathVariable("id") Long id, Model modelo, RedirectAttributes atributo, Proyectos entidad) {
        for (ProyectoCoordinadores item : servicioProyectoCoord.getTodos()) {
            if (item.getIdProyecto().getIdProyecto().equals(id)) {
                atributo.addFlashAttribute("error", "El proyecto ya tiene agregado sus 3 coordinadores");
                return "redirect:/mantenimientoProyectoCoord";
            }
        }
        if (servicioCoordinador.getTodos().size() == 0) {
            atributo.addFlashAttribute("error", "Error el sistema aun no tiene coordinadores");
            return "redirect:/mantenimientoProyectoCoord";
        }
        this.idProyecto = id;
        this.banderinProyectoCoord = true;
        setParametro(modelo, "proyecto", new Proyectos());
        setParametro(modelo, "listaUsuario", servicioUsuario.getUsuariosConsulta("consulta"));
        setParametro(modelo, "listaCoordinadorP", servicioCoordinador.getTipoCoordinador("Coordinador Profesional"));
        setParametro(modelo, "listaCoordinadorT", servicioCoordinador.getTipoCoordinador("Coordinador Tecnico"));
        setParametro(modelo, "listaCoordinadorG", servicioCoordinador.getTipoCoordinador("Coordinador General"));
        return "paginas/proyecto/form-proyecto-coordinadores";
    }

    @PostMapping("/guardarProyecto")
    public String guardar(Proyectos proyecto, Model model, RedirectAttributes atributo) {
        try {
            if (proyecto.getIdDepartamento() == null) {
                atributo.addFlashAttribute("error", "Error el departamento esta vacio");
                return "redirect:/registrarProyecto";
            }
            servicio.guardar(proyecto);
            if (banderin) {
                atributo.addFlashAttribute("success", "Guardado Correctamente");
            } else {
                atributo.addFlashAttribute("success", "Actualizado Correctamente");
            }
            this.banderin = true;
        } catch (Exception e) {
            System.out.println("ERROR AQUI: " + e.getMessage());
        }
        return "redirect:/registrarProyecto";
    }

    @PostMapping("/guardarCoordinadores")
    public String guardarCoordinadores(Proyectos proyecto, Model model, RedirectAttributes atributo) {
        try {
            proyecto.setIdProyecto(this.idProyecto);
            if (proyecto.getIdProyecto() == null) {
                atributo.addFlashAttribute("error", "Selecciona un proyecto antes de guardar");
                return "redirect:/formProyectoCoordinadores";
            }
            if (proyecto.getIdUsuario1() == 0 || proyecto.getIdUsuario2() == 0 || proyecto.getIdUsuario3() == 0) {
                atributo.addFlashAttribute("error", "Error la opcion del coordinador esta vacia");
                return "redirect:/formProyectoCoordinadores";
            }
            //----------------------------------Validando y despues guardando
            if (proyecto.getIdUsuario1().equals(proyecto.getIdUsuario2())) {
                atributo.addFlashAttribute("error", "El coordinador profesional debe ser diferente del coordinador tenico");
                if (this.banderinProyectoCoord) {
                    return "redirect:/formProyectoCoordinadores";
                } else {
                    return "redirect:/formCoordinadoresActualizar";
                }
            } else {
                if (proyecto.getIdUsuario1().equals(proyecto.getIdUsuario3())) {
                    atributo.addFlashAttribute("error", "El coordinador profesional debe ser diferente del coordinador general");
                    if (this.banderinProyectoCoord) {
                        return "redirect:/formProyectoCoordinadores";
                    } else {
                        return "redirect:/formCoordinadoresActualizar";
                    }
                }
                if (proyecto.getIdUsuario3().equals(proyecto.getIdUsuario2())) {
                    atributo.addFlashAttribute("error", "El coordinador general debe ser diferente del coordinador tecnico");
                    if (this.banderinProyectoCoord) {
                        return "redirect:/formProyectoCoordinadores";
                    } else {
                        return "redirect:/formCoordinadoresActualizar";
                    }
                }
                if (!banderinProyectoCoord) {
                    servicioProyectoCoord.eliminarProyectoCoordinadores(proyecto.getIdProyecto());
                    this.banderin = false;
                }
                //------------------------------------------------------------ Guardando el primero
                ProyectoCoordinadores proyectoSup = new ProyectoCoordinadores();
                TipoCoordinadores coordinador = new TipoCoordinadores(proyecto.getIdCoordinadorProfesional());
                Usuario usuario = new Usuario(proyecto.getIdUsuario1());
                proyectoSup.setIdProyecto(proyecto);
                proyectoSup.setIdTipoCoordinador(coordinador);
                proyectoSup.setIdUsuario(usuario);

                //------------------------------------------------------------ Guardando el segundo
                ProyectoCoordinadores proyectoSup2 = new ProyectoCoordinadores();
                TipoCoordinadores coordinador2 = new TipoCoordinadores(proyecto.getIdCoordinadorTecnico());
                Usuario usuario2 = new Usuario(proyecto.getIdUsuario2());
                proyectoSup2.setIdProyecto(proyecto);
                proyectoSup2.setIdTipoCoordinador(coordinador2);
                proyectoSup2.setIdUsuario(usuario2);

                //------------------------------------------------------------ Guardando el tercero
                ProyectoCoordinadores proyectoSup3 = new ProyectoCoordinadores();
                TipoCoordinadores coordinador3 = new TipoCoordinadores(proyecto.getIdCoordinadorGeneral());
                Usuario usuario3 = new Usuario(proyecto.getIdUsuario3());
                proyectoSup3.setIdProyecto(proyecto);
                proyectoSup3.setIdTipoCoordinador(coordinador3);
                proyectoSup3.setIdUsuario(usuario3);

                //-----------------------------------------
                servicioProyectoCoord.guardar(proyectoSup);
                servicioProyectoCoord.guardar(proyectoSup2);
                servicioProyectoCoord.guardar(proyectoSup3);
                if (banderinProyectoCoord) {
                    atributo.addFlashAttribute("success", "Guardado Correctamente");
                } else {
                    java.util.Date d = new java.util.Date();
                    java.sql.Date date2 = new java.sql.Date(d.getTime());
                    servicioBitacoraCoordinador.guardar(new BitacoraCoordinadores(date2, proyecto.getJustificacion()));
                    atributo.addFlashAttribute("success", "Actualizado Correctamente");
                }
            }

            this.banderin = true;
            this.idProyecto = null;
        } catch (Exception e) {
            System.out.println("ERROR AQUI: " + e.getMessage());
        }
        return "redirect:/formProyectoCoordinadores";
    }

    @RequestMapping("/formProyectoCoordinadores")
    public String irFormularioProyectoCoord(Model modelo) {
        this.banderinProyectoCoord = true;
        setParametro(modelo, "proyecto", new Proyectos());
        setParametro(modelo, "listaUsuario", servicioUsuario.getUsuariosConsulta("consulta"));
        setParametro(modelo, "listaCoordinadorP", servicioCoordinador.getTipoCoordinador("Coordinador Profesional"));
        setParametro(modelo, "listaCoordinadorT", servicioCoordinador.getTipoCoordinador("Coordinador Tecnico"));
        setParametro(modelo, "listaCoordinadorG", servicioCoordinador.getTipoCoordinador("Coordinador General"));
        return "paginas/proyecto/form-proyecto-coordinadores";
    }

    @RequestMapping("/formCoordinadoresActualizar")
    public String irFormularioCoordActualizar(Model modelo) {
        setParametro(modelo, "proyecto", new Proyectos());
        modelo.addAttribute("editMode", "true");
        this.banderinProyectoCoord = false;
        setParametro(modelo, "listaUsuario", servicioUsuario.getUsuariosConsulta("consulta"));
        setParametro(modelo, "listaCoordinadorP", servicioCoordinador.getTipoCoordinador("Coordinador Profesional"));
        setParametro(modelo, "listaCoordinadorT", servicioCoordinador.getTipoCoordinador("Coordinador Tecnico"));
        setParametro(modelo, "listaCoordinadorG", servicioCoordinador.getTipoCoordinador("Coordinador General"));
        return "paginas/proyecto/form-proyecto-coordinadores";
    }

    @GetMapping("/actualizarCordinadores/{id}")
    public String actualizarCoordinadores(@PathVariable("id") Long id, Model modelo, RedirectAttributes atributo) {
        this.idProyecto = id;
        this.banderinProyectoCoord = false;
        if (servicioCoordinador.getTodos().size() == 0) {
            atributo.addFlashAttribute("error", "Error el sistema aun no tiene coordinadores");
            return "redirect:/mantenimientoProyectoCoord";
        }
        modelo.addAttribute("editMode", "true");
        setParametro(modelo, "proyecto", new Proyectos());
        setParametro(modelo, "listaUsuario", servicioUsuario.getUsuariosConsulta("consulta"));
        setParametro(modelo, "listaCoordinadorP", servicioCoordinador.getTipoCoordinador("Coordinador Profesional"));
        setParametro(modelo, "listaCoordinadorT", servicioCoordinador.getTipoCoordinador("Coordinador Tecnico"));
        setParametro(modelo, "listaCoordinadorG", servicioCoordinador.getTipoCoordinador("Coordinador General"));
        return "paginas/proyecto/form-proyecto-coordinadores";
    }

    public void setParametro(Model model, String atributo, Object valor) {
        model.addAttribute(atributo, valor);
    }
}
