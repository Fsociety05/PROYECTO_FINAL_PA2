/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hn.uth.pa2.controladores;

import hn.uth.pa2.modelos.Rol;
import hn.uth.pa2.modelos.Usuario;
import hn.uth.pa2.servicios.RolServicio;
import hn.uth.pa2.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Licona
 */
@Controller
public class ControladorGeneral {

    private boolean logiado_por_primera_vez = true;

    /**
     * **************************************************************
     */
    @Autowired
    private RolServicio servicioRol;

    @Autowired
    private UsuarioServicio servicioUsuario;

    @RequestMapping("/")
    public String index(Model model) {
        setParametro(model, "usuario", new Usuario());

        if (logiado_por_primera_vez) {
            llenandoTablas();
            logiado_por_primera_vez = false;
        }

        return "index";
    }

    @PostMapping("/menu_inicio")
    public String menu(Usuario userLogin, Model model, RedirectAttributes attribute) {
        
        boolean encontrado = false;
        for (Usuario object : servicioUsuario.getTodos()) {
            if (object.getName_usuario().equals(userLogin.getName_usuario())) {
                encontrado = true;
                if (object.getContrasenia().equals(userLogin.getContrasenia())) {
                    
                    setParametro(model, "usuarioLogiado", object);
                    return "Paginas/menu_principal";
                }
            }
        }
        
        if(!encontrado){
            attribute.addFlashAttribute("error", "Usuario no encontrado");
        }else{
            attribute.addFlashAttribute("error", "Contraseña incorrecta");
        }

        
        return "redirect:/";

    }

    /*Llenando las tablas por primera ves*/
    private void llenandoTablas() {

        /**
         * **********ROLES********************
         */
        Rol rolTemp = new Rol();
        rolTemp.setNombre("ADMINISTRADOR");
        rolTemp.setDescripcion("...");
        servicioRol.guardar(rolTemp);

        Rol rolTemp2 = new Rol();
        rolTemp2.setNombre("CONSULTA");
        rolTemp2.setDescripcion("...");
        servicioRol.guardar(rolTemp2);

        /*Usuario Admin*/
        Usuario usuarioTemp = new Usuario();
        usuarioTemp.setName_usuario("ADMIN");
        usuarioTemp.setContrasenia("ADMIN");
        usuarioTemp.setRol(rolTemp);
        servicioUsuario.guardar(usuarioTemp);
    }

    public void setParametro(Model model, String atributo, Object valor) {
        model.addAttribute(atributo, valor);
    }
}
