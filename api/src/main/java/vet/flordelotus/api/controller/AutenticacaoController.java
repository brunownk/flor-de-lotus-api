package vet.flordelotus.api.controller;

import jakarta.validation.Valid;
import vet.flordelotus.api.domain.dto.DadosAutenticacao;
import vet.flordelotus.api.domain.dto.DadosTokenJWT;
import vet.flordelotus.api.domain.entity.User;
import vet.flordelotus.api.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//controller responsável por disparar o processo de autenticação
@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    //realiza a autenticacao inves de chamar o SecurityConfigurations diretamente
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
        //UsernamePasswordAuthenticationToken = dto do proprio spring
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.password());
        var authentication = manager.authenticate(authenticationToken);

        var tokenJWT = tokenService.gerarToken((User) authentication.getPrincipal());
        //se for logado com sucesso, o DTO devolve o token no json
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}
