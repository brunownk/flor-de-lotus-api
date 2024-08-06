package vet.flordelotus.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vet.flordelotus.api.domain.repository.UserRepository;

//Classe de servico no qual executará algum serviço no projeto, no caso, será o de autenticação/login
@Service
public class AutenticacaoService implements UserDetailsService {

    //Autowired = Injecao de dependencias
    @Autowired
    private UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //esse metodo realizara a consulta no DB
        return repository.findByLogin(username);
    }
}
