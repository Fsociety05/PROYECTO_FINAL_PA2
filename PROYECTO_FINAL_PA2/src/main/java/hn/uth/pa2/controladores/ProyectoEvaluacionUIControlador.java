/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hn.uth.pa2.controladores;

import hn.uth.pa2.modelos.ProyectoCoordinadores;
import hn.uth.pa2.modelos.ProyectoEvaluacion;
import hn.uth.pa2.servicios.PlantillaServicio;
import hn.uth.pa2.servicios.ProyectoCoordinadoresServicios;
import hn.uth.pa2.servicios.ProyectoEvaluacionServicio;
import hn.uth.pa2.servicios.UsuarioServicio;
import java.util.List;
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
 * @author Licona
 */
@Controller
public class ProyectoEvaluacionUIControlador {

    @Autowired
    private UsuarioServicio servicioUsuario;

    @Autowired
    private ProyectoCoordinadoresServicios servicioProyectosCoordinadores;

    @Autowired
    private PlantillaServicio servicioPlantilla;

    @Autowired
    private ProyectoEvaluacionServicio servicioProyectoEvaluacion;

    @GetMapping("/calificar/{id}")
    public String irFormulario(@PathVariable("id") Long id, Model model, RedirectAttributes atributo) throws Exception {
        //setParametro(model, "supervisiones", new Supervisiones());
        //setParametro(model, "proyectoSupervisiones", new ProyectoSupervisiones());

        Long idUsuario = servicioUsuario.getLoggedUser().getId_usuario();

        List<ProyectoCoordinadores> tempProCord = null;

        List<ProyectoEvaluacion> tempProyectoEvaluaciones = null;

        for (ProyectoCoordinadores proyectoCoordinadores : servicioProyectosCoordinadores.seleccionarProyectoCoordinador(idUsuario)) {
            if (proyectoCoordinadores.getIdProyecto().getIdProyecto() == id) {

//                setParametro(model, "proyecto", proyectoCoordinadores.getIdProyecto());
//                setParametro(model, "tipoCoordinador", proyectoCoordinadores.getIdTipoCoordinador());
//                
//                if(proyectoCoordinadores.getIdTipoCoordinador().getNombre().equalsIgnoreCase("Coordinador Tecnico")){
//                    setParametro(model, "plantilla", proyectoCoordinadores.getIdProyecto().getIdPlantillaTecnico());
//                }
//                
//                if(proyectoCoordinadores.getIdTipoCoordinador().getNombre().equalsIgnoreCase("Coordinador General")){
//                    setParametro(model, "plantilla", proyectoCoordinadores.getIdProyecto().getIdPlantillaGeneral());
//                    
//                   
//                     
//                }
//                
//                if(proyectoCoordinadores.getIdTipoCoordinador().getNombre().equalsIgnoreCase("Coordinador Profesional")){
//                    setParametro(model, "plantilla", proyectoCoordinadores.getIdProyecto().getIdPlantillaProfesional());
//                     
//                }
                setParametro(model, "proyecto", proyectoCoordinadores.getIdProyecto());
                setParametro(model, "tipoCoordinador", proyectoCoordinadores.getIdTipoCoordinador());

                if (proyectoCoordinadores.getIdTipoCoordinador().getNombre().equalsIgnoreCase("Coordinador Tecnico")) {
                    setParametro(model, "plantilla", proyectoCoordinadores.getIdProyecto().getIdPlantillaTecnico());

                    setParametro(model, "listaProyectoEvaluacion", getProjectoEvaluacionPorPlantilla(proyectoCoordinadores.getIdProyecto().getIdProyecto(),
                            proyectoCoordinadores.getIdProyecto().getIdPlantillaTecnico().getIdPlantilla()));

                    setParametro(model, "contadorMax", contarTotales(proyectoCoordinadores.getIdProyecto().getIdProyecto(),
                            proyectoCoordinadores.getIdProyecto().getIdPlantillaTecnico().getIdPlantilla())[0]);

                    setParametro(model, "contadorMin", contarTotales(proyectoCoordinadores.getIdProyecto().getIdProyecto(),
                            proyectoCoordinadores.getIdProyecto().getIdPlantillaTecnico().getIdPlantilla())[1]);

                    setParametro(model, "contadorCalificacion", contarTotales(proyectoCoordinadores.getIdProyecto().getIdProyecto(),
                            proyectoCoordinadores.getIdProyecto().getIdPlantillaTecnico().getIdPlantilla())[2]);
                }

                if (proyectoCoordinadores.getIdTipoCoordinador().getNombre().equalsIgnoreCase("Coordinador General")) {
                    setParametro(model, "plantilla", proyectoCoordinadores.getIdProyecto().getIdPlantillaGeneral());

                    setParametro(model, "listaProyectoEvaluacion", getProjectoEvaluacionPorPlantilla(proyectoCoordinadores.getIdProyecto().getIdProyecto(),
                            proyectoCoordinadores.getIdProyecto().getIdPlantillaGeneral().getIdPlantilla()));

                    setParametro(model, "contadorMax", contarTotales(proyectoCoordinadores.getIdProyecto().getIdProyecto(),
                            proyectoCoordinadores.getIdProyecto().getIdPlantillaGeneral().getIdPlantilla())[0]);

                    setParametro(model, "contadorMin", contarTotales(proyectoCoordinadores.getIdProyecto().getIdProyecto(),
                            proyectoCoordinadores.getIdProyecto().getIdPlantillaGeneral().getIdPlantilla())[1]);

                    setParametro(model, "contadorCalificacion", contarTotales(proyectoCoordinadores.getIdProyecto().getIdProyecto(),
                            proyectoCoordinadores.getIdProyecto().getIdPlantillaGeneral().getIdPlantilla())[2]);
                    
                    System.out.println("Entro");
                }

                if (proyectoCoordinadores.getIdTipoCoordinador().getNombre().equalsIgnoreCase("Coordinador Profesional")) {
                    setParametro(model, "plantilla", proyectoCoordinadores.getIdProyecto().getIdPlantillaProfesional());

                    setParametro(model, "listaProyectoEvaluacion", getProjectoEvaluacionPorPlantilla(proyectoCoordinadores.getIdProyecto().getIdProyecto(),
                            proyectoCoordinadores.getIdProyecto().getIdPlantillaProfesional().getIdPlantilla()));

                    setParametro(model, "contadorMax", contarTotales(proyectoCoordinadores.getIdProyecto().getIdProyecto(),
                            proyectoCoordinadores.getIdProyecto().getIdPlantillaProfesional().getIdPlantilla())[0]);

                    setParametro(model, "contadorMin", contarTotales(proyectoCoordinadores.getIdProyecto().getIdProyecto(),
                            proyectoCoordinadores.getIdProyecto().getIdPlantillaProfesional().getIdPlantilla())[1]);

                    setParametro(model, "contadorCalificacion", contarTotales(proyectoCoordinadores.getIdProyecto().getIdProyecto(),
                            proyectoCoordinadores.getIdProyecto().getIdPlantillaProfesional().getIdPlantilla())[2]);
                }

                break;
            }
        }

        atributo.addFlashAttribute("error", "Hola");

        setParametro(model, "usuario", servicioUsuario.getLoggedUser().getId_usuario());

        return "paginas/calificacion/form_calificacion";
    }

    @GetMapping("/calificar_criterio/{id}")
    public String irCalificar(@PathVariable("id") Long id, Model model, RedirectAttributes atributo) throws Exception {

        setParametro(model, "evaluacionCriterio", servicioProyectoEvaluacion.getValor(id).get());
        atributo.addFlashAttribute("error", "Correo ya existente");

        return "paginas/calificacion/form_nota";
    }

    public void setParametro(Model model, String atributo, Object valor) {
        model.addAttribute(atributo, valor);
    }

    public List<ProyectoEvaluacion> getProjectoEvaluacionPorPlantilla(Long idProyecto, Long idPlantilla) {

        return servicioProyectoEvaluacion.getPorIdProyectoAndPlantilla(idProyecto, idPlantilla);
    }

    public int[] contarTotales(Long idProyecto, Long idPlantilla) {

        int temp[] = new int[3];
        temp[0] = 0;
        temp[1] = 0;
        temp[2] = 0;

        for (ProyectoEvaluacion item : getProjectoEvaluacionPorPlantilla(idProyecto, idPlantilla)) {
            temp[0] += item.getIdCriterio().getPunt_maximo();
            temp[1] += item.getIdCriterio().getPunt_minimo();
            temp[2] += item.getCalificacion();
        }

        return temp;
    }

    @PostMapping("/guardar_evaluacionCriterio")
    public String guardar(ProyectoEvaluacion entidad, Model model, RedirectAttributes attribute) throws Exception {

        entidad.setIdUsuario(servicioUsuario.getLoggedUser());

        java.util.Date d = new java.util.Date();
        java.sql.Date date2 = new java.sql.Date(d.getTime());

        entidad.setFecha(date2);
//        
//        
        servicioProyectoEvaluacion.guardar(entidad);
//        

        //setParametro(model, "evaluacionCriterio", entidad);
        //irFormulario(entidad.getIdProyecto().getIdProyecto(), model, attribute);
        //return "redirect:/misProyectos";
        //setParametro(model, "evaluacionCriterio", servicioProyectoEvaluacion.getValor(id).get());
        setParametro(model, "evaluacionCriterio", entidad);

        attribute.addFlashAttribute("success", "Guardado correctamente");

        return "redirect:/calificar_criterio/" + entidad.getId() + "";
    }
}
