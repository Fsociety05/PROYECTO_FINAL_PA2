/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hn.uth.pa2.controladores;

import hn.uth.pa2.modelos.ProyectoCoordinadores;
import hn.uth.pa2.servicios.PlantillaServicio;
import hn.uth.pa2.servicios.ProyectoCoordinadoresServicios;
import hn.uth.pa2.servicios.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    @GetMapping("/calificar/{id}")
    public String irFormulario(@PathVariable("id") Long id, Model model, RedirectAttributes atributo) throws Exception {
        //setParametro(model, "supervisiones", new Supervisiones());
        //setParametro(model, "proyectoSupervisiones", new ProyectoSupervisiones());
        
        Long idUsuario = servicioUsuario.getLoggedUser().getId_usuario();
        
        List<ProyectoCoordinadores>tempProCord = null;
        
        for (ProyectoCoordinadores proyectoCoordinadores : servicioProyectosCoordinadores.seleccionarProyectoCoordinador(idUsuario)) {
            if(proyectoCoordinadores.getIdProyecto().getIdProyecto()==id){
                setParametro(model, "proyecto", proyectoCoordinadores.getIdProyecto());
                setParametro(model, "tipoCoordinador", proyectoCoordinadores.getIdTipoCoordinador());
                
                if(proyectoCoordinadores.getIdTipoCoordinador().getNombre().equalsIgnoreCase("Coordinador Tecnico")){
                    setParametro(model, "plantilla", proyectoCoordinadores.getIdProyecto().getIdPlantillaTecnico());
                }
                
                if(proyectoCoordinadores.getIdTipoCoordinador().getNombre().equalsIgnoreCase("Coordinador General")){
                    setParametro(model, "plantilla", proyectoCoordinadores.getIdProyecto().getIdPlantillaGeneral());
                    
                   
                     
                }
                
                if(proyectoCoordinadores.getIdTipoCoordinador().getNombre().equalsIgnoreCase("Coordinador Profesional")){
                    setParametro(model, "plantilla", proyectoCoordinadores.getIdProyecto().getIdPlantillaProfesional());
                     
                }
                
                break;
            }
        }
        
        return "paginas/calificacion/form_calificacion";
    }
    
    public void setParametro(Model model, String atributo, Object valor) {
        model.addAttribute(atributo, valor);
    }
}
