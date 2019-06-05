
package mx.edu.ebc.comisiones.config

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.boot.autoconfigure.*
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter


//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebMVCSecurity extends WebSecurityConfigurerAdapter {

  protected void configure(HttpSecurity http) throws Exception {
	  http.csrf().disable(); // Noncompliant
	}
}