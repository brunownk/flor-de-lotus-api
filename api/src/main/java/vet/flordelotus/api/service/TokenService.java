package vet.flordelotus.api.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import vet.flordelotus.api.domain.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

//Classe que utiliza da biblioteca Auth0 para gerar o token
@Service
public class TokenService {

    //Value = @Value! Ele é como um "ponteiro" que o Spring usa para pegar informações de fora do seu código e colocálas dentro da sua aplicação.
    //para nao colocar diretamente no codigo, configuramos o secret no yml
    // ${JWT_SECRET:12345678}, no caso o primeiro seria uma variavel ambiente e caso nao existir
    // sera usado o 12345678 como default
    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(User usuario){
        try {
            //HS256 = Algoritmo para fazer a assinatura digital
            //secret = chave secreta que você define para proteger seus tokens JWT.
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create() //Da pra colocar outros with como o withClaim que pode mostrar o id
                    .withIssuer("API Vet.florlotus") //Quem emitiu o token
                    .withSubject(usuario.getLogin()) //Identifica a qual usuario o token pertence
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritmo);
        } catch (JWTCreationException exception){
            // Invalid Signing configuration / Couldn't convert Claims.
            throw new RuntimeException("Erro ao gerar token jwt", exception);
        }
    }

    //devolver o usuario que esta armazenado dentro do token e valida
    public String getSubject(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("API Vet.florlotus")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }
    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
