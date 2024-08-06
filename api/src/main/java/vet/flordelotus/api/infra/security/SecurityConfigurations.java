package vet.flordelotus.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//Classe de configuracao
@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    //O token ja protege contra csrf, por isso desabilitou
    //sessionManagement = desabilita o formulario de login padrao do spring, pq eh uma api STATELESS
    //e ele tb n vai mais bloquear as url, a gente que vai configurar td
    @Bean //o @Bean  serve para exibir o retorno desse método, que estamos devolvendo um objeto SecurityFilterChain.
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                //cada requisição é tratada de forma independente, sem guardar informações sobre requisições anteriores.
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .authorizeHttpRequests(req -> {
                    //aqui autoriza entrar sem autenticacao, pq eh o login
                    req.requestMatchers("/login").permitAll();
                    //adicionar documentacao ao projeto
                    req.requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll();

                    //e qualquer outra precisa estar logado
                    req.anyRequest().authenticated();
                })
                //Necessario realizar a ordem em que os fitlros vao rodar, pois o spring roda o dele primeiro o que bloqueou o restante
                //primeiro deve rodar o securityFilter que valida
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    //metodo ensina pro spring como injetar o objeto
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

    //fala para o spring que as senhas sao salvas no banco estao em hash
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
