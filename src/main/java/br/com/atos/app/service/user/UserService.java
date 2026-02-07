package br.com.atos.app.service.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.atos.app.dto.user.UserRequestDTO;
import br.com.atos.app.dto.user.UserResponseDTO;
import br.com.atos.app.exception.BussinesException;
import br.com.atos.app.model.user.User;
import br.com.atos.app.repository.user.UserRepository;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    private String encryptPassword(String password){
        String hash = encoder.encode(password);
        return hash;
    }

    public UserResponseDTO criarUsuario(UserRequestDTO usuario){
        if(userRepository.existsByName(usuario.name())){
            throw new IllegalArgumentException("Usuário já existe.");
        }

        User novoUsuario = new User();
        novoUsuario.setName(usuario.name());
        novoUsuario.setPassword(encryptPassword(usuario.password()));

        User usuarioSalvo = userRepository.save(novoUsuario);

        return new UserResponseDTO(
            usuarioSalvo.getId(),
            usuarioSalvo.getName()
        );
    }

    public List<UserResponseDTO> listarUsuarios(){
        List<User> usuarios = userRepository.findAll();
        List<UserResponseDTO> lista = new ArrayList<>();
        
        for(User user : usuarios){
            UserResponseDTO usuario = new UserResponseDTO(
                user.getId(),
                user.getName()
            );
            lista.add(usuario);
        }

        return lista;
    }

    public UserResponseDTO listarUsuarioPorId(Long id){
        User usuarioEncontrado = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        UserResponseDTO usuario = new UserResponseDTO(
            usuarioEncontrado.getId(),
            usuarioEncontrado.getName()
        );
        return usuario;
    }

    public UserResponseDTO atualizarUsuario(Long id, UserRequestDTO user){
        User usuarioEncontrado = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        usuarioEncontrado.setName(user.name());
        usuarioEncontrado.setPassword(encryptPassword(user.password()));
        userRepository.save(usuarioEncontrado);
        UserResponseDTO usuarioAtualizado = new UserResponseDTO(
            usuarioEncontrado.getId(),
            usuarioEncontrado.getName()
        );
        return usuarioAtualizado;
    }

    public void deletarUsuario(Long id){
        if(id == null){
            throw new BussinesException("Identificador inválido.");
        }
        if(!userRepository.existsById(id)){
            throw new BussinesException("Usuário não encontrado");
        }else {
            userRepository.deleteById(id);
        }
    }
}
