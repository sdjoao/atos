package br.com.atos.app.controller.user;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> listUsers(){
        List<UserResponseDTO> userList = userService.listarUsuarios();
        return ResponseEntity.status(200).body(userList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> listUserById(@PathVariable Long id){
        UserResponseDTO user = userService.listarUsuarioPorId(id);
        return ResponseEntity.status(200).body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserRequestDTO user){
        UserResponseDTO updatedUser = userService.atualizarUsuario(id, user);
        return ResponseEntity.status(200).body(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
