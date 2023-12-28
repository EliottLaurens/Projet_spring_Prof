package fr.limayrac.ProjetSpring.security;

import fr.limayrac.ProjetSpring.security.CustomLoginSuccessHandler;
import fr.limayrac.ProjetSpring.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomLoginSuccessHandler successHandler;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").permitAll() // Permet l'accès à tout le monde à la page d'accueil
                .antMatchers("/professeur/**").hasRole("PROFESSOR") // Seuls les utilisateurs ayant le rôle PROFESSOR peuvent accéder aux pages sous /professeur/
                .antMatchers("/professor/home").hasRole("PROFESSOR")
                .antMatchers("/admin/**").hasRole("ADMIN") // Seuls les utilisateurs ayant le rôle ADMIN peuvent accéder aux pages sous /admin/
                .antMatchers("/admin/home").hasRole("ADMIN")
                .and()
                .formLogin()
                .loginPage("/login") // Spécifie la page de login personnalisée
                .successHandler(successHandler)
                .permitAll() // Permet à tout le monde d'accéder au formulaire de login
                .and()
                .logout()
                .logoutSuccessUrl("/login?logout")
                .permitAll(); // Permet à tout le monde de se déconnecter
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//
//
//        auth.inMemoryAuthentication()
//                .passwordEncoder(encoder)
//                .withUser("professeur")
//                .password(encoder.encode("prof"))
//                .roles("PROFESSOR")
//                .and()
//                .withUser("admin")
//                .password(encoder.encode("admin"))
//                .roles("ADMIN");
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
