package br.com.atos.app.service.user;

import org.springframework.stereotype.Service;

import br.com.atos.app.repository.user.UserRepository;

@Service
public class UserService {
    
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
}
