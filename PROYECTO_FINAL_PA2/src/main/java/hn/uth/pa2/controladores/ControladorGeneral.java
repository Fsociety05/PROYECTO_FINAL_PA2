/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hn.uth.pa2.controladores;

import hn.uth.pa2.modelos.Departamento;
import hn.uth.pa2.modelos.Rol;
import hn.uth.pa2.modelos.TipoCoordinadores;
import hn.uth.pa2.modelos.TipoEvaluacion;
import hn.uth.pa2.modelos.TipoPlantilla;
import hn.uth.pa2.modelos.Usuario;
import hn.uth.pa2.servicios.DepartamentoServicio;
import hn.uth.pa2.servicios.RolServicio;
import hn.uth.pa2.servicios.TipoCoordinadoresServicio;
import hn.uth.pa2.servicios.TipoEvaluacionServicio;
import hn.uth.pa2.servicios.TipoPlantillaServicio;
import hn.uth.pa2.servicios.UsuarioServicio;
import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Licona
 */
@Controller
public class ControladorGeneral {

    private boolean logiado_por_primera_vez = true;

    private Usuario usuarioLogueado;
    /**
     * **************************************************************
     */
    @Autowired
    private RolServicio servicioRol;

    @Autowired
    private UsuarioServicio servicioUsuario;

    @Autowired
    private TipoEvaluacionServicio servicioTipoEvaluacion;

    @Autowired
    private DepartamentoServicio servicioDepartamento;

    @Autowired
    private TipoCoordinadoresServicio servicioCoordinador;
    
    @Autowired
    private TipoPlantillaServicio servicioTipoPlantilla;

    @RequestMapping({"/", "/login"})
    public String index(Model model) {

        if (logiado_por_primera_vez) {
//            llenandoTablas();
            //crearUsuario();
            logiado_por_primera_vez = false;
        }
        //llenandoTablas();
        return "index";
    }

    @RequestMapping("/menuInicio")
    public String menuPrincipal() {
        return "paginas/menu_principal";
    }

    public void crearUsuario() {
        HashSet<Rol> rolUser = new HashSet<Rol>();
        rolUser.add(servicioRol.getValor(1L).get());

        /*Usuario Admin*/
        Usuario usuarioTemp = new Usuario();
        usuarioTemp.setUsername("admin");
        usuarioTemp.setContrasenia("admin");
        usuarioTemp.setRoles(rolUser);
        servicioUsuario.guardar(usuarioTemp);
    }

    /*Llenando las tablas por primera ves*/
    private void llenandoTablas() {

        /**
         * **********ROLES********************
         */
        Rol rolTemp = new Rol();
        rolTemp.setNombre("ADMINISTRADOR");
        rolTemp.setDescripcion("ROLE_ADMIN");
        servicioRol.guardar(rolTemp);

        Rol rolTemp2 = new Rol();
        rolTemp2.setNombre("CONSULTA");
        rolTemp2.setDescripcion("ROLE_CONSULTA");
        servicioRol.guardar(rolTemp2);
        
        
        HashSet<Rol> rolUser = new HashSet<Rol>();
        rolUser.add(rolTemp);
        
        
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
        /*Usuario Admin*/
        Usuario usuarioTemp = new Usuario();
        usuarioTemp.setUsername("admin");
        usuarioTemp.setContrasenia(bCryptPasswordEncoder.encode("123"));
        usuarioTemp.setRoles(rolUser);
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


          TipoEvaluacion temE1 = new TipoEvaluacion();
          temE1.setNombre("PROFESIONAL");
          temE1.setDescripcion("...");
          servicioTipoEvaluacion.guardar(temE1);
          
          TipoEvaluacion temE2 = new TipoEvaluacion();
          temE2.setNombre("TECNICO");
          temE2.setDescripcion("...");
          servicioTipoEvaluacion.guardar(temE2);
          
          TipoEvaluacion temE3 = new TipoEvaluacion();
          temE3.setNombre("GENERAL");
          temE3.setDescripcion("...");
          servicioTipoEvaluacion.guardar(temE3);
          
          
          
          TipoPlantilla temP1 = new TipoPlantilla();
          temP1.setNombre("PROFESIONAL");
          temP1.setDescripcion("...");
          servicioTipoPlantilla.guardar(temP1);
          
          TipoPlantilla temP2 = new TipoPlantilla();
          temP2.setNombre("TECNICO");
          temP2.setDescripcion("...");
          servicioTipoPlantilla.guardar(temP2);
          
          TipoPlantilla temP3 = new TipoPlantilla();
          temP3.setNombre("GENERAL");
          temP3.setDescripcion("...");
          servicioTipoPlantilla.guardar(temP3);
          
          
          
          
          
    }

    public void setParametro(Model model, String atributo, Object valor) {
        model.addAttribute(atributo, valor);
    }
}
