package br.com.atos.app.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.atos.app.dto.user.UserRequestDTO;
import br.com.atos.app.dto.user.UserResponseDTO;
import br.com.atos.app.service.user.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO user){
        UserResponseDTO newUser =  userService.criarUsuario(user);
        return ResponseEntity.status(201).body(newUser);
    }
}
