package br.com.atos.app.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.atos.app.model.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByName(String name);
}
