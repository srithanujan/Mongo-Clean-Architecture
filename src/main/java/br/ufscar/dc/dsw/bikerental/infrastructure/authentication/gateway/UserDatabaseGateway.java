package br.ufscar.dc.dsw.bikerental.infrastructure.authentication.gateway;

import br.ufscar.dc.dsw.bikerental.entity.authentication.gateway.UserGateway;
import br.ufscar.dc.dsw.bikerental.entity.authentication.model.User;
import br.ufscar.dc.dsw.bikerental.infrastructure.config.db.repository.UserAuthenticationRepository;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class UserDatabaseGateway implements UserGateway {
    private final UserAuthenticationRepository userAuthenticationRepository;

    public UserDatabaseGateway(UserAuthenticationRepository userAuthenticationRepository) {
        this.userAuthenticationRepository = userAuthenticationRepository;
    }

    @Override
    public User save(User user) {
        return userAuthenticationRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userAuthenticationRepository.findByEmail(email);
    }

    @Override
    public Iterable<User> findAll() {
        return userAuthenticationRepository.findAll();
    }
}