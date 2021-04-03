/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hn.uth.pa2.controladores;

import hn.uth.pa2.modelos.Departamento;
import hn.uth.pa2.modelos.Rol;
import hn.uth.pa2.modelos.TipoCoordinadores;
import hn.uth.pa2.modelos.Usuario;
import hn.uth.pa2.servicios.DepartamentoServicio;
import hn.uth.pa2.servicios.RolServicio;
import hn.uth.pa2.servicios.TipoCoordinadoresServicio;
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

    @Autowired
    private TipoCoordinadoresServicio servicioCoordinador;

    @Autowired
    private DepartamentoServicio servicioDepartamento;

    @RequestMapping("/")
    public String index(Model model) {
        setParametro(model, "usuario", new Usuario());

        if (logiado_por_primera_vez) {
            //llenandoTablas();
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
                    return "paginas/menu_principal";
                }
            }
        }

        if (!encontrado) {
            attribute.addFlashAttribute("error", "Usuario no encontrado");
        } else {
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
        
        Usuario usuarioTemp2 = new Usuario();
        usuarioTemp2.setName_usuario("ADMIN");
        usuarioTemp2.setContrasenia("ADMIN");
        usuarioTemp2.setRol(rolTemp);
        servicioUsuario.guardar(usuarioTemp);

        TipoCoordinadores coordinador = new TipoCoordinadores();
        coordinador.setNombre("Coordinador Profesional");
        coordinador.setDescripcion("AAA");
        servicioCoordinador.guardar(coordinador);

        TipoCoordinadores coordinador2 = new TipoCoordinadores();
        coordinador2.setNombre("Coordinador Tecnico");
        coordinador2.setDescripcion("AAA");
        servicioCoordinador.guardar(coordinador2);

        TipoCoordinadores coordinador3 = new TipoCoordinadores();
        coordinador3.setNombre("Coordinador General");
        coordinador3.setDescripcion("AAA");
        servicioCoordinador.guardar(coordinador3);

        Departamento departamento = new Departamento();
        departamento.setNombre("Marketing");
        departamento.setDescripcion("AAA");
        servicioDepartamento.guardar(departamento);
        
        Departamento departamento2 = new Departamento();
        departamento2.setNombre("Contabilidad");
        departamento2.setDescripcion("AAA");
        servicioDepartamento.guardar(departamento2);
        
        Departamento departamento3 = new Departamento();
        departamento3.setNombre("Recursos Humanos");
        departamento3.setDescripcion("AAA");
        servicioDepartamento.guardar(departamento3);
    }

    public void setParametro(Model model, String atributo, Object valor) {
        model.addAttribute(atributo, valor);
    }
}
