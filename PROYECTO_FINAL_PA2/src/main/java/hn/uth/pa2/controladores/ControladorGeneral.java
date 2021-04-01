/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hn.uth.pa2.controladores;

import hn.uth.pa2.modelos.Rol;
import hn.uth.pa2.servicios.RolServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Licona
 */

@Controller
public class ControladorGeneral {
    

    
    @RequestMapping("/") 
    public String index(Model model) {
        //setParametro(model, "lista", servicio.getTodos());
       
        
        return "index";
    }
}
