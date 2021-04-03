/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hn.uth.pa2.controladores;

import hn.uth.pa2.modelos.Usuario;
import hn.uth.pa2.servicios.DepartamentoServicio;
import hn.uth.pa2.servicios.RolServicio;
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
 * @author Licona
 */
@Controller
public class UsuarioUIControlador {

    private boolean estado_editando = false;

    //----------------------------------------------------------------------------------------------------------------
    @Autowired
    private UsuarioServicio servicioUsuario;

    @Autowired
    private RolServicio servicioRol;

    @Autowired
    private DepartamentoServicio servicioDepartamento;

    @RequestMapping("/mantenimientoUsuario")
    public String mantenimientoUsuario(Model model) {
        
        System.out.println("Este es el id del user logiado " + ControladorGeneral.id_usuario);
        
        
        setParametro(model, "lista_usuarios", servicioUsuario.getTodos());
        setParametro(model, "lista_roles", servicioRol.getTodos());
        setParametro(model, "lista_departamentos", servicioDepartamento.getTodos());
        return "Paginas/Usuario/mantenimiento_usuario";

    }

    @GetMapping("/crear_usuario")
    public String irCrear(Model model) {

        setParametro(model, "usuario", new Usuario());
        setParametro(model, "lista_roles", servicioRol.getTodos());
        setParametro(model, "lista_departamentos", servicioDepartamento.getTodos());
        return "Paginas/Usuario/form_usuario";
    }

    @PostMapping("/guardar_usuario")
    public String guardar(Usuario entidad, Model model, RedirectAttributes attribute) {

        if (entidad.getDepartamento() == null || entidad.getRol() == null) {
            attribute.addFlashAttribute("error", "No se puede guardar debido a falta de datos");
            estado_editando = false;
            return "redirect:/crear_usuario";
        }

        for (Usuario todo : servicioUsuario.getTodos()) {

            if (estado_editando) {
                if (entidad.getIdentidad().equals(todo.getIdentidad()) && !todo.getId_usuario().equals(entidad.getId_usuario())) {
                    attribute.addFlashAttribute("error", "Identidad ya existente");
                    estado_editando = false;
                    return "redirect:/mantenimientoUsuario";
                }

                if (entidad.getName_usuario().equals(todo.getName_usuario()) && !todo.getId_usuario().equals(entidad.getId_usuario())) {
                    attribute.addFlashAttribute("error", "Nombre de usuario ya existente");
                    estado_editando = false;
                    return "redirect:/mantenimientoUsuario";
                }

                if (entidad.getCorreo().equals(todo.getCorreo()) && !todo.getId_usuario().equals(entidad.getId_usuario())) {
                    attribute.addFlashAttribute("error", "Correo ya existente");
                    estado_editando = false;
                    return "redirect:/mantenimientoUsuario";
                }
            } else {
                if (entidad.getIdentidad().equals(todo.getIdentidad())) {
                    attribute.addFlashAttribute("error", "Identidad ya existente");
                    estado_editando = false;
                    return "redirect:/crear_usuario";

                }

                if (entidad.getName_usuario().equals(todo.getName_usuario())) {
                    attribute.addFlashAttribute("error", "Nombre de usuario ya existente");
                    estado_editando = false;
                    return "redirect:/crear_usuario";
                }

                if (entidad.getCorreo().equals(todo.getCorreo())) {
                    attribute.addFlashAttribute("error", "Correo ya existente");
                    estado_editando = false;
                    return "redirect:/crear_usuario";
                }
            }

        }

        servicioUsuario.guardar(entidad);
        estado_editando = false;
        attribute.addFlashAttribute("success", "Guardado correctamente");
        return "redirect:/crear_usuario";
    }

    @GetMapping("/actualizar_usuario/{id}")
    public String irActualizar(@PathVariable("id") Long id, Model model, RedirectAttributes attribute) {
        
        if (servicioUsuario.getValor(id).get().getName_usuario().equals("ADMIN")) {
            attribute.addFlashAttribute("error", "No se puede Editado este usuario");
            return "redirect:/mantenimientoUsuario";
        }
        
        
        setParametro(model, "usuario", servicioUsuario.getValor(id));
        setParametro(model, "lista_roles", servicioRol.getTodos());
        setParametro(model, "lista_departamentos", servicioDepartamento.getTodos());

        estado_editando = true;

        return "Paginas/Usuario/form_usuario";
    }

    @GetMapping("eliminar_usuario/{id}")
    public String eliminar(@PathVariable("id") Long id, Model modelo, RedirectAttributes attribute) {

        if (servicioUsuario.getValor(id).get().getName_usuario().equals("ADMIN")) {
            attribute.addFlashAttribute("error", "No se puede Eliminar este usuario");
            return "redirect:/mantenimientoUsuario";
        }

        servicioUsuario.eliminar(id);
        return "redirect:/mantenimientoUsuario";
    }

    public void setParametro(Model model, String atributo, Object valor) {
        model.addAttribute(atributo, valor);
    }
}
