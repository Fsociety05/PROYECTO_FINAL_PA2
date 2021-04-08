/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hn.uth.pa2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author Buddys
 */
//Indica que esta clase es de configuracion y necesita ser cargada durante el inicio del server
@Configuration

//Indica que esta clase sobreescribira la implmentacion de seguridad web
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    String[] resources = new String[]{
        "/include/**", "/css/**", "/icons/**", "/imagenes/**", "/js/**", "/layer/**"
    };
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http
        .authorizeRequests()
        .antMatchers(resources).permitAll()//Cualquiera que tenga /css le va a permitir el acceso es decir todo lo de thymeleaf  
        .antMatchers("/","/index").permitAll() //Cualquier puede ingresar a estas paginas
            .anyRequest().authenticated() //Otro request diferente de los anteriores debera estar autenticado
            .and() 
        .formLogin()
            .loginPage("/login") //al formulario de login pueden acceder todos
            .permitAll()
            .defaultSuccessUrl("/menuInicio")//Si se logea exitosamente me redirige a esta URL
            .failureUrl("/login?error=true") //si se falla al logear te devuelve aqui 
            .usernameParameter("username") //Esto pertenece al formulario de login 'name = "username"'
            .passwordParameter("contrasenia") // entonces aqui identifica de donde se le envia todo 'name = "password"'
            .and() 
            .csrf().disable()
        .logout()
            .permitAll() //Que el deslogeo lo puede hacer cualquiera
            .logoutSuccessUrl("/login?logout");
    }
    
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        //Nivel de encriptacion 4, exiten niveles desde 4 - 31
		bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
        return bCryptPasswordEncoder;
    }
    
    @Autowired
    UserDetailsService userDetailsService;
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { 
    	//Especificar el encargado del login y encriptacion del password
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}
