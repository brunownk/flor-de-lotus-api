package vet.flordelotus.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import vet.flordelotus.api.domain.repository.UserRepository;
import vet.flordelotus.api.service.TokenService;

import java.io.IOException;

//Filtro =  é como um segurança que verifica as requisições que chegam na sua API.
//Component = Classe generica, quando nao sabemos qual e
//OncePerRequestFilter garante que vai ser executada apenas uma vez por requisicao
@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository repository;

    //Filterchain = cadeia de filtros, ele chama o proximo filtro
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = recuperarToken(request);
        //se tem o token no cabecalho
        if(tokenJWT != null) {
            //realiza a validacao do token e pega o subject
            var subject = tokenService.getSubject(tokenJWT);

            //falar pro spring que o usuario esta logado
            var user = repository.findByLogin(subject);
            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    //recupera a string do token (o cabecalho), agora as requisicoes estao bloqueadas
    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "").trim(); //trim=apaga os espacos em branco
        } return null;
    }
}
