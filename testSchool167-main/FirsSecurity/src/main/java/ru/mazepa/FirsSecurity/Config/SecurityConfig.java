package ru.mazepa.FirsSecurity.Config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PrePostAnnotationSecurityMetadataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.mazepa.FirsSecurity.Services.PersonDetailsService;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {


    private final PersonDetailsService personDetailsService;



    @Autowired
    public SecurityConfig( PersonDetailsService personDetailsService) {

        this.personDetailsService = personDetailsService;


    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http    .authorizeRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // разрешаем доступ к статическим ресурсам
                .requestMatchers(request -> request.getRequestURI().equals("/auth/login")).permitAll() // разрешаем доступ к странице логина
                .requestMatchers(request -> request.getRequestURI().equals("/auth/registration")).permitAll() // разрешаем доступ к странице регистрации
                .requestMatchers(request -> request.getRequestURI().equals("/error")).permitAll()
                .requestMatchers("/static/**").permitAll()
                .anyRequest().hasAnyRole("USER" , "ADMIN")// разрешаем доступ к странице ошибки
                .and()
                .formLogin()
                .loginPage("/auth/login")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/hello", true)
                .failureUrl("/auth/login?error")
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/auth/login")
                .permitAll();


        return http.build();
    }



    void registerProvider(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(personDetailsService);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
      return new BCryptPasswordEncoder();
    }




}